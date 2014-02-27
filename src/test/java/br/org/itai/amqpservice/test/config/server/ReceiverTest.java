package br.org.itai.amqpservice.test.config.server;

import java.net.URISyntaxException;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.qpid.AMQException;
import org.apache.qpid.client.AMQAnyDestination;
import org.apache.qpid.client.AMQConnection;

import br.org.itai.amqpservice.proxyservice.util.JMSMessageUtils;

public class ReceiverTest {

	public static void main(String[] args) throws JMSException, AMQException,
			URISyntaxException {
		@SuppressWarnings("resource")
		Connection connection = new AMQConnection(
				"amqp://user:user@192.168.1.185/default?brokerlist='tcp://192.168.1.185:5672'");

		// Connection connection = new AMQConnection("localhost", 5672, "guest",
		// "guest", "clientId", "/test");
		connection.start();

		Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);
		Destination queue = new AMQAnyDestination(
				"ADDR:umr192.168.1.29.configuration; {create: always}");
		MessageConsumer consumer = session.createConsumer(queue);

		while (true) {
			System.out.println("Waiting Message");
			Message m = consumer.receive();

			System.out.println("Message received");
			System.out.println(JMSMessageUtils.bodyAsString(m));

			MessageProducer mp = session.createProducer(m.getJMSReplyTo());

			System.out.println("Sending response...");
			mp.send(((org.apache.qpid.jms.Session) session)
					.createTextMessage("{\"status\":\"ok\"}"));
		}
	}
}
