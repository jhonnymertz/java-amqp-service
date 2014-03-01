package br.org.itai.amqpservice.proxy.services.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TemporaryQueue;
import javax.jms.TextMessage;

import org.apache.qpid.AMQException;
import org.apache.qpid.url.URLSyntaxException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.org.itai.amqpservice.proxy.services.MessengerService;
import br.org.itai.amqpservice.proxy.util.JMSMessageUtils;

public class MessengerServiceImpl implements MessengerService {

	protected transient Logger logger = LoggerFactory
			.getLogger(this.getClass());
	
	public String requestTo(String message, Session session,
			Destination destination) throws URLSyntaxException, AMQException,
			JMSException, URISyntaxException, JsonGenerationException,
			JsonMappingException, IOException {

		// send
		MessageProducer producer = session.createProducer(destination);

		TextMessage m = session.createTextMessage(message);

		TemporaryQueue temporaryQueue = session.createTemporaryQueue();

		m.setJMSReplyTo(temporaryQueue);
		
		producer.send(m);
		
		logger.debug("Mensagem enviada.");
		producer.close();

		// receive
		MessageConsumer consumer = session.createConsumer(temporaryQueue);

		logger.debug("Aguardando resposta...");
		javax.jms.Message response = consumer.receive();
		consumer.close();
		session.close();

		return JMSMessageUtils.bodyAsString(response);
	}

	public List<String> receiveFrom(Session session, Destination destination,
			int count) throws Exception {
		// TODO: implement
		throw new Exception("Not implemented yet");
	}

	public String receiveFrom(Session session, Destination destination)
			throws Exception {
		// TODO: implement
		throw new Exception("Not implemented yet");
	}

	public void sendTo(String message, Session session, Destination destination)
			throws Exception {
		// TODO: implement
		throw new Exception("Not implemented yet");

	}

	public void sendTo(List<String> messages, Session session,
			Destination destination) throws Exception {
		// TODO: implement
		throw new Exception("Not implemented yet");

	}

}
