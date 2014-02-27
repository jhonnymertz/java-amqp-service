package br.org.itai.amqpservice.connection.implementation.qpid.connection;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.Session;

import org.apache.qpid.client.AMQConnection;

import br.org.itai.amqpservice.connection.interfaces.ConnectionFactory;

public class QpidConnectionFactory implements ConnectionFactory {

	private Map<String, Connection> connections;

	public Session getSession(String address) throws Exception {

		return createConnection(address).createSession(false,
				Session.AUTO_ACKNOWLEDGE);

	}

	public synchronized Connection createConnection(String address)
			throws Exception {
		if (connections == null)
			connections = new HashMap<String, Connection>();

		if (connections.containsKey(address))
			return connections.get(address);

		Connection connection = new AMQConnection(address);
		connection.start();
		connections.put(address, connection);

		return connection;
	}

}
