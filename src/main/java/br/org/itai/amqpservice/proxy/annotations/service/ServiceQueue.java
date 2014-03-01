package br.org.itai.amqpservice.proxy.annotations.service;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
@Documented
public @interface ServiceQueue {

	/**
	 * Queue name
	 * @return
	 */
	String value();

//	// always, never, receiver, sender
//	String create() default "always";
//
//	String description() default "ADDR:{name}; {create:{create}}";
}
