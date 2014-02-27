package br.org.itai.amqpservice.convertion.implementation.json;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

import br.org.itai.amqpservice.convertion.interfaces.Converter;

public class JsonConverter implements Converter {

	private ObjectMapper mapper;

	public JsonConverter(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	public String marshall(Object object) throws IOException {
		return mapper.writeValueAsString(object);
	}

	public <T> T unmarshall(String message, Class<T> returnType)
			throws IOException {
		return mapper.readValue(message, returnType);
	}

}
