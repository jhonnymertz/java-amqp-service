package br.org.itai.amqpservice.proxyservice;

import java.lang.reflect.Proxy;

import br.org.itai.amqpservice.connection.interfaces.ConnectionFactory;
import br.org.itai.amqpservice.connection.interfaces.DestinationFactory;
import br.org.itai.amqpservice.convertion.interfaces.ConverterFactory;
import br.org.itai.amqpservice.proxyservice.annotations.service.ServiceAddress;
import br.org.itai.amqpservice.proxyservice.annotations.service.ServiceQueue;
import br.org.itai.amqpservice.proxyservice.services.impl.AnnotatedServiceImpl;

public class AMQPService {

	private ConnectionFactory connectionFactory;
	private DestinationFactory destinationFactory;
	private ConverterFactory converterFactory;

	public AMQPService(ConnectionFactory cf, DestinationFactory qf,
			ConverterFactory cvf) {
		this.connectionFactory = cf;
		this.destinationFactory = qf;
		this.converterFactory = cvf;
	}

	public <T> T create(Class<T> clazz) throws Exception {

		// create proxy to the interface
		@SuppressWarnings("unchecked")
		T object = (T) Proxy.newProxyInstance(
				clazz.getClassLoader(),
				new Class[] { clazz },
				new AnnotatedServiceImpl(clazz, clazz
						.getAnnotation(ServiceAddress.class), clazz
						.getAnnotation(ServiceQueue.class), connectionFactory,
						destinationFactory, converterFactory));

		return object;
	}
}
