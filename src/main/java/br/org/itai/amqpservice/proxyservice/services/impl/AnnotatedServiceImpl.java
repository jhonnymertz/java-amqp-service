package br.org.itai.amqpservice.proxyservice.services.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.org.itai.amqpservice.connection.interfaces.ConnectionFactory;
import br.org.itai.amqpservice.connection.interfaces.DestinationFactory;
import br.org.itai.amqpservice.convertion.interfaces.Converter;
import br.org.itai.amqpservice.convertion.interfaces.ConverterFactory;
import br.org.itai.amqpservice.convertion.interfaces.ConverterType;
import br.org.itai.amqpservice.proxyservice.annotations.param.Param;
import br.org.itai.amqpservice.proxyservice.annotations.param.config.AddressConfig;
import br.org.itai.amqpservice.proxyservice.annotations.param.config.QueueConfig;
import br.org.itai.amqpservice.proxyservice.annotations.param.config.ServiceConfig;
import br.org.itai.amqpservice.proxyservice.annotations.service.ServiceAddress;
import br.org.itai.amqpservice.proxyservice.annotations.service.ServiceQueue;
import br.org.itai.amqpservice.proxyservice.message.RequestMessage;
import br.org.itai.amqpservice.proxyservice.services.AnnotatedService;
import br.org.itai.amqpservice.proxyservice.services.MessengerService;
import br.org.itai.amqpservice.proxyservice.util.ParametizerStringUtils;

public class AnnotatedServiceImpl implements AnnotatedService,
		InvocationHandler {

	protected transient Logger logger = LoggerFactory
			.getLogger(this.getClass());

	private ServiceAddress serviceAddress;
	private ServiceQueue serviceQueue;

	private MessengerService messenger = new MessengerServiceImpl();

	private Class<?> clazz;

	private ConnectionFactory cf;

	private DestinationFactory qf;

	private ConverterFactory cvf;

	public AnnotatedServiceImpl(Class<?> clazz, Annotation serviceAnnotation,
			Annotation queueAnnotation, ConnectionFactory cf,
			DestinationFactory qf, ConverterFactory cvf) throws Exception {
		this.clazz = clazz;
		this.cf = cf;
		this.qf = qf;
		this.cvf = cvf;
		this.serviceQueue = (ServiceQueue) queueAnnotation;
		this.serviceAddress = (ServiceAddress) serviceAnnotation;
	}

	public Object invoke(Object proxy, Method m, Object[] args)
			throws Throwable {
		
		if(!Arrays.asList(clazz.getDeclaredMethods()).contains(m))
			return Object.class.getMethod(m.getName()).invoke(clazz);

		try {

			// check if address is right annotated
			if (serviceAddress == null
					&& m.getAnnotation(ServiceAddress.class) == null)
				throw new IllegalStateException("There must be a "
						+ ServiceAddress.class.getCanonicalName()
						+ " annotation on class or method "
						+ clazz.getCanonicalName() + "#" + m.getName());

			// check if queue is right annotated
			if (serviceQueue == null
					&& m.getAnnotation(ServiceQueue.class) == null)
				throw new IllegalStateException("There must be a "
						+ ServiceQueue.class.getCanonicalName()
						+ " annotation on class or method "
						+ clazz.getCanonicalName() + "#" + m.getName());

			RequestMessage message = getMessage(m, args);
			String address = getAddress(m, args);
			String queue = getQueue(m, args);

			return request(message, address, queue, m.getReturnType());

		} catch (Exception e) {
			throw e;
		}
	}

	public String getAddress(Method m, Object[] args) throws Exception {

		Annotation[][] parameterAnnotations = m.getParameterAnnotations();
		Map<String, Object> addressConfigParams = new HashMap<String, Object>();

		// if will be use global or local address config
		String urlPattern = (m.getAnnotation(ServiceAddress.class) != null) ? m
				.getAnnotation(ServiceAddress.class).value() : serviceAddress
				.value();

		for (int i = 0; i < args.length; i++) {
			for (Annotation a : parameterAnnotations[i]) {
				
				// filling url with local address config
				if (a instanceof AddressConfig) {
					String paramName = ((AddressConfig) a).value();

					if (!ParametizerStringUtils.hasParam(urlPattern, paramName))
						throw new IllegalArgumentException(urlPattern
								+ " should have " + paramName);

					addressConfigParams.put(((AddressConfig) a).value(),
							args[i].toString());
				}
			}
		}

		String address = ParametizerStringUtils.format(urlPattern,
				addressConfigParams);

		if (!ParametizerStringUtils.isCompleteFilled(address, 0))
			throw new IllegalArgumentException(urlPattern
					+ " should have all params specified.");
		return address;
	}

	public RequestMessage getMessage(Method m, Object[] args) throws Exception {
		RequestMessage message = new RequestMessage();
		
		
		if(m.getAnnotation(ServiceConfig.class) != null){
			ServiceConfig serviceConfig = m.getAnnotation(ServiceConfig.class);
			if(serviceConfig.enable())
				message.setService(serviceConfig.name());
//			if(!serviceConfig.groupParams())
//				if(serviceConfig.enable())
//					message.addParameter(serviceConfig.name(), m.getName());
		}
		else message.setService(m.getName());

		Annotation[][] parameterAnnotations = m.getParameterAnnotations();

		for (int i = 0; i < args.length; i++) {
			for (Annotation an : parameterAnnotations[i]) {
				// getting params
				if (an instanceof Param) {
					message.addParameter(((Param) an).value(),
							args[i].toString());
				}
			}
		}
		return message;
	}

	public String getQueue(Method m, Object[] args) throws Exception {

		Annotation[][] parameterAnnotations = m.getParameterAnnotations();
		Map<String, Object> queueConfigParams = new HashMap<String, Object>();

		// if will be use global or local queue config
		String description = (m.getAnnotation(ServiceQueue.class) != null) ? m
				.getAnnotation(ServiceQueue.class).value() : serviceQueue
				.value();

		for (int i = 0; i < args.length; i++) {
			for (Annotation a : parameterAnnotations[i]) {
				// filling queue with local queue config
				if (a instanceof QueueConfig) {
					String paramName = ((QueueConfig) a).value();

					if (!ParametizerStringUtils
							.hasParam(description, paramName))
						throw new IllegalArgumentException(description
								+ " should have " + paramName);

					queueConfigParams.put(((QueueConfig) a).value(),
							args[i].toString());
				}
			}
		}

		String queue = ParametizerStringUtils.format(description,
				queueConfigParams);

		// TODO: verify if all parameters were filled / improve it
		if (!ParametizerStringUtils.isCompleteFilled(queue, 1))
			throw new IllegalArgumentException(description
					+ " should have all params specified.");
		return queue;
	}

	public <T> T request(RequestMessage message, String address, String queue,
			Class<T> returnType) throws Exception {

		// QPID send and receive logic
		Session session = cf.getSession(address);

		Destination destination = qf.createQueue(queue);

		Converter c = cvf.createConverter(ConverterType.JSON);

		String jsonMessage = c.marshall(message);

		logger.debug("Enviando mensagem {} para o endereço {} na fila {}",
				jsonMessage, address, queue);

		String messageReceived = messenger.requestTo(jsonMessage, session,
				destination);

		logger.debug("Mensagem {} recebida.", messageReceived);

		return c.unmarshall(messageReceived, returnType);
	}
}
