package br.org.itai.amqpservice.test;

import org.junit.Test;

import br.org.itai.amqpservice.connection.implementation.qpid.connection.QpidConnectionFactory;
import br.org.itai.amqpservice.connection.implementation.qpid.destination.QpidDestinationFactory;
import br.org.itai.amqpservice.convertion.implementation.json.ConverterFactoryImpl;
import br.org.itai.amqpservice.proxy.AMQPService;
import br.org.itai.amqpservice.test.config.AbstractTest;
import br.org.itai.amqpservice.test.config.interfaces.ServiceDefined;
import br.org.itai.amqpservice.test.config.interfaces.ServiceUndefined;

public class AddressAnnotationsTest extends AbstractTest {

	@Test(expected = IllegalArgumentException.class)
	public void testShouldBeAnnotatedThrowException() throws Exception {

		ServiceUndefined su = new AMQPService(new QpidConnectionFactory(),
				new QpidDestinationFactory(), new ConverterFactoryImpl())
				.create(ServiceUndefined.class);
		su.service();
	}

	public void testFormatAddress() throws Exception {

		String address = mockHandler().getAddress(
				ServiceDefined.class.getDeclaredMethod("manualTrigger",
						String.class, Integer.class, String.class,
						String.class, String.class, Boolean.class,
						Boolean.class),
				new Object[] { "myhost", 123, "queue", "user", "pass", true,
						true });

		assertEquals(
				"amqp://user:pass@myhost/queue?brokerlist='tcp://myhost:123'",
				address);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testShouldHaveAllParamsDefinedOnUrlThrowException()
			throws Exception {

		mockHandler().getAddress(
				ServiceDefined.class.getDeclaredMethod("status", String.class),
				new Object[] { "work" });
	}

	@Test
	public void testShouldOverrideUrlWithLocalConfig() throws Exception {

		String address = mockHandler().getAddress(
				ServiceDefined.class.getDeclaredMethod("configuration",
						String.class, String.class),
				new Object[] { "myqueue", "config" });

		assertEquals(
				"amqp://guest:guest@clientId/myqueue?brokerlist='tcp://localhost:5672'",
				address);
	}

}
