package br.org.itai.amqpservice.proxyservice.message;

import java.util.HashMap;
import java.util.Map;

public class RequestMessage {

	private String service;
	private Map<String, Object> args;

	public RequestMessage() {
		super();
		this.args = new HashMap<String, Object>();
	}

	public RequestMessage(String jsonStringMessage) {
		super();
		this.args = new HashMap<String, Object>();
	}

	public RequestMessage(String service, Map<String, Object> parameters) {
		super();
		this.args = parameters;
	}

	public void addParameter(String key, Object value) {
		this.args.put(key, value);
	}

	public Map<String, Object> getArgs() {
		return args;
	}

	@Override
	public String toString() {
		return "JsonMessage [service=" + service + ", args=" + args + "]";
	}

	/**
	 * @return the service
	 */
	public String getService() {
		return service;
	}

	/**
	 * @param service
	 *            the service to set
	 */
	public void setService(String service) {
		this.service = service;
	}

}
