package br.org.itai.amqpservice.connection.interfaces;

import javax.jms.Destination;

public interface DestinationFactory {

	public Destination createQueue(String queue) throws Exception;

}
