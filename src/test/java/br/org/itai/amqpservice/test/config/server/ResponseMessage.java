package br.org.itai.amqpservice.test.config.server;


public class ResponseMessage {

	private String status;

	public ResponseMessage() {
		super();
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ResponseMessage [status=" + status + "]";
	}

}
