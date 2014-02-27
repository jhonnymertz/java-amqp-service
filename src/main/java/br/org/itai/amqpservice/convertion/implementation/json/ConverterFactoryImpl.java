package br.org.itai.amqpservice.convertion.implementation.json;

import org.codehaus.jackson.map.ObjectMapper;

import br.org.itai.amqpservice.convertion.interfaces.Converter;
import br.org.itai.amqpservice.convertion.interfaces.ConverterFactory;
import br.org.itai.amqpservice.convertion.interfaces.ConverterType;

public class ConverterFactoryImpl implements ConverterFactory {

	public ConverterFactoryImpl() {
		// TODO Auto-generated constructor stub
	}

	public Converter createConverter(ConverterType converterType) {
		return new JsonConverter(new ObjectMapper());
	}
}
