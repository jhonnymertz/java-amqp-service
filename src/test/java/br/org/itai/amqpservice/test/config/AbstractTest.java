package br.org.itai.amqpservice.test.config;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.org.itai.amqpservice.connection.implementation.qpid.connection.QpidConnectionFactory;
import br.org.itai.amqpservice.connection.implementation.qpid.destination.QpidDestinationFactory;
import br.org.itai.amqpservice.convertion.implementation.json.ConverterFactoryImpl;
import br.org.itai.amqpservice.proxy.annotations.service.ServiceAddress;
import br.org.itai.amqpservice.proxy.annotations.service.ServiceQueue;
import br.org.itai.amqpservice.proxy.services.impl.AnnotatedServiceImpl;
import br.org.itai.amqpservice.test.config.interfaces.ServiceDefined;

public abstract class AbstractTest extends TestCase {

	protected transient Logger logger = LoggerFactory
			.getLogger(this.getClass());

	// mock a handler to test
	public AnnotatedServiceImpl mockHandler() throws Exception {
		return new AnnotatedServiceImpl(ServiceDefined.class,
				ServiceDefined.class.getAnnotation(ServiceAddress.class),
				ServiceDefined.class.getAnnotation(ServiceQueue.class),
				new QpidConnectionFactory(), new QpidDestinationFactory(),
				new ConverterFactoryImpl());
	}
}
