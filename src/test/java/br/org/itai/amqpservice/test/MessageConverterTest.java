package br.org.itai.amqpservice.test;

import java.util.Date;

import org.junit.Test;

import br.org.itai.amqpservice.convertion.implementation.json.ConverterFactoryImpl;
import br.org.itai.amqpservice.convertion.interfaces.ConverterType;
import br.org.itai.amqpservice.proxyservice.message.RequestMessage;
import br.org.itai.amqpservice.test.config.AbstractTest;
import br.org.itai.amqpservice.test.config.interfaces.ServiceDefined;

public class MessageConverterTest extends AbstractTest {

	@Test
	public void testShouldConvertAllTypesToMessage() throws Exception {

		RequestMessage message = mockHandler().getMessage(
				ServiceDefined.class.getDeclaredMethod("values", String.class,
						Integer.class, boolean.class, long.class, Date.class),
				new Object[] { "string", 123, true, 200L, new Date(1230) });

		String convertedMessage = new ConverterFactoryImpl().createConverter(
				ConverterType.JSON).marshall(message);

		assertTrue(convertedMessage
				.contains("\"args\":{\"int\":\"123\",\"string\":\"string\",\"boolean\":\"true\",\"date\":\"Wed Dec 31 21:00:01 BRT 1969\",\"long\":\"200\"}")
				&& convertedMessage.contains("\"service\":\"values\""));

	}
}
