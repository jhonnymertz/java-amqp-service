package br.org.itai.amqpservice.proxyservice.services;

import java.lang.reflect.Method;

import br.org.itai.amqpservice.proxyservice.message.RequestMessage;

public interface AnnotatedService {

	public RequestMessage getMessage(Method m, Object[] args) throws Exception;

	public String getAddress(Method m, Object[] args) throws Exception;

	public <T> T request(RequestMessage message, String address, String queue,
			Class<T> returnType) throws Exception;

	public String getQueue(Method m, Object[] args) throws Exception;
}
