package br.org.itai.amqpservice.connection.interfaces;

import javax.jms.Connection;
import javax.jms.Session;

public interface ConnectionFactory {

	public Connection createConnection(String url) throws Exception;

	public Session getSession(String address) throws Exception;

}
