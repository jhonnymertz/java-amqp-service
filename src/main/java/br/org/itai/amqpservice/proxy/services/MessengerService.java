package br.org.itai.amqpservice.proxy.services;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.qpid.AMQException;
import org.apache.qpid.url.URLSyntaxException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

public interface MessengerService {

	public List<String> receiveFrom(Session session, Destination destination,
			int count) throws Exception;

	public String receiveFrom(Session session, Destination destination)
			throws JMSException, URLSyntaxException, AMQException,
			URISyntaxException, Exception;

	public void sendTo(String message, Session session, Destination destination)
			throws URLSyntaxException, AMQException, JMSException,
			URISyntaxException, Exception;

	public void sendTo(List<String> messages, Session session,
			Destination destination) throws URLSyntaxException, AMQException,
			JMSException, URISyntaxException, Exception;

	public String requestTo(String message, Session session,
			Destination destination) throws URLSyntaxException, AMQException,
			JMSException, URISyntaxException, JsonGenerationException,
			JsonMappingException, IOException;

}
