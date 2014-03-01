package br.org.itai.amqpservice.proxy.annotations.param.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface ServiceConfig {

	boolean enable() default true;
	
	String name() default "service";
	
//	boolean groupParams() default true;

}
