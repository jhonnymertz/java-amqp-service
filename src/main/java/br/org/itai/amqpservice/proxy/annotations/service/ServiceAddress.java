package br.org.itai.amqpservice.proxy.annotations.service;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
@Documented
public @interface ServiceAddress {

	/**
	 * In order to support any kind of url pattern. The default value is the
	 * simplest url known of an AMQP address.
	 * 
	 * @return url pattern that will be used to communicate
	 */
	public String value() default "amqp://localhost:5672";

}
