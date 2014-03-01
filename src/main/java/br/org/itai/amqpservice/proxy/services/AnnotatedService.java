package br.org.itai.amqpservice.proxy.services;

import java.lang.reflect.Method;

import br.org.itai.amqpservice.proxy.message.RequestMessage;

public interface AnnotatedService {

	public RequestMessage getMessage(Method m, Object[] args) throws Exception;

	public String getAddress(Method m, Object[] args) throws Exception;

	public <T> T request(RequestMessage message, String address, String queue,
			Class<T> returnType) throws Exception;

	public String getQueue(Method m, Object[] args) throws Exception;
}
