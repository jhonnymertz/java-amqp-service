package br.org.itai.amqpservice.connection.implementation.qpid.destination;

import javax.jms.Destination;

import org.apache.qpid.client.AMQAnyDestination;

import br.org.itai.amqpservice.connection.interfaces.DestinationFactory;

public class QpidDestinationFactory implements DestinationFactory {

	public Destination createQueue(String name) throws Exception {
		return new AMQAnyDestination(name);
	}

}
