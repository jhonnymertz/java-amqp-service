package br.org.itai.amqpservice.convertion.interfaces;

public interface ConverterFactory {

	public Converter createConverter(ConverterType converterType);

}
