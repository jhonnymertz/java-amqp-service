package br.org.itai.amqpservice.test.config.interfaces;

import java.util.Date;

import br.org.itai.amqpservice.proxyservice.annotations.param.Param;
import br.org.itai.amqpservice.proxyservice.annotations.param.config.AddressConfig;
import br.org.itai.amqpservice.proxyservice.annotations.service.ServiceAddress;
import br.org.itai.amqpservice.proxyservice.annotations.service.ServiceQueue;
import br.org.itai.amqpservice.test.config.server.ResponseMessage;

@ServiceAddress("amqp://{username}:{password}@{hostname}/{virtualhost}?brokerlist='tcp://{hostname}:{port}'")
@ServiceQueue("lalacw3")
public interface ServiceDefined {

	public String manualTrigger(@AddressConfig("hostname") String hostname,
			@AddressConfig("port") Integer port,
			@AddressConfig("virtualhost") String virtualhost,
			@AddressConfig("username") String username,
			@AddressConfig("password") String password,
			@Param("longRegister") Boolean longRegister,
			@Param("shortRegister") Boolean shortRegister);

	@ServiceAddress("amqp://guest:guest@clientId/{virtualhost}?brokerlist='tcp://localhost:5672'")
	public ResponseMessage configuration(
			@AddressConfig("virtualhost") String virtualhost,
			@Param("configuration") String configuration);
	
	@ServiceAddress("amqp://guest:guest@clientId/{virtualhost}?brokerlist='tcp://localhost:5672'")
	public ResponseMessage potatos(
			@AddressConfig("virtualhost") String virtualhost,
			@Param("potato") String potato);

	@ServiceAddress("amqp://localhost/{vhost}")
	public String status(@Param("value") String status);

	public String values(@Param("string") String string,
			@Param("int") Integer integer, @Param("boolean") boolean bool,
			@Param("long") long longint, @Param("date") Date date);
}