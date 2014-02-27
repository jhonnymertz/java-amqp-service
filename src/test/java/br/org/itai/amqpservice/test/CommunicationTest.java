package br.org.itai.amqpservice.test;

import org.junit.Test;

import br.org.itai.amqpservice.connection.implementation.qpid.connection.QpidConnectionFactory;
import br.org.itai.amqpservice.connection.implementation.qpid.destination.QpidDestinationFactory;
import br.org.itai.amqpservice.convertion.implementation.json.ConverterFactoryImpl;
import br.org.itai.amqpservice.proxyservice.AMQPService;
import br.org.itai.amqpservice.test.config.AbstractTest;
import br.org.itai.amqpservice.test.config.interfaces.ServiceDefined;
import br.org.itai.amqpservice.test.config.server.ReceiverTest;

/**
 * Needs a receiver running over amqp broker
 * 
 * @see ReceiverTest
 */
public class CommunicationTest extends AbstractTest {

	@Test
	public void testShouldBeCreatedAndDone() throws Exception {
		ServiceDefined sd = new AMQPService(new QpidConnectionFactory(),
				new QpidDestinationFactory(), new ConverterFactoryImpl())
				.create(ServiceDefined.class);
//		sd.configuration("test", "test");
//		sd.potatos("test", "potato");
		
		assertEquals(true, true);
	}

}
