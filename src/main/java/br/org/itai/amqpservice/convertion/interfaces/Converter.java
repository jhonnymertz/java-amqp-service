package br.org.itai.amqpservice.convertion.interfaces;

import java.io.IOException;

public interface Converter {

	public String marshall(Object object) throws IOException;

	public <T> T unmarshall(String message, Class<T> returnType)
			throws IOException;

}
