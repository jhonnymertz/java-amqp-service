package br.org.itai.amqpservice.proxyservice.util;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.qpid.client.message.JMSTextMessage;

public class JMSMessageUtils {

	public static String bodyAsString(Message message) throws JMSException {
		if (message instanceof JMSTextMessage)
			return ((JMSTextMessage) message).getText();
		BytesMessage bytesMessage = (BytesMessage) message;
		byte[] byteArr;
		String text = "";
		try {
			byteArr = new byte[(int) bytesMessage.getBodyLength()];
			bytesMessage.readBytes(byteArr);
			text = new String(byteArr);
			return text;
		} catch (JMSException e) {
			e.printStackTrace();
			return null;
		}
	}

}
