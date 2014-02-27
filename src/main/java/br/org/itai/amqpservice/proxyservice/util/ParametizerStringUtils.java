package br.org.itai.amqpservice.proxyservice.util;

import java.util.Map;
import java.util.Map.Entry;

public class ParametizerStringUtils {

	// TODO: review it, may be used the commons lang lib
	public static boolean isCompleteFilled(String urlPattern, int count) {
		int counter = 0;
		for (int i = 0; i < urlPattern.length(); i++) {
			if (urlPattern.charAt(i) == '{') {
				counter++;
			}
		}

		if (counter > count)
			return false;
		else
			return true;
	}

	public static boolean hasParam(String urlPattern, String paramName) {
		return urlPattern.contains("{" + paramName + "}");
	}

	public static String format(String urlPattern, Map<String, Object> values) {

		StringBuilder builder = new StringBuilder(urlPattern);

		for (Entry<String, Object> entry : values.entrySet()) {

			int start;
			String pattern = "{" + entry.getKey() + "}";
			String value = entry.getValue().toString();

			// Substitui qualquer ocorrencia de '{<key>}
			while ((start = builder.indexOf(pattern)) != -1) {
				builder.replace(start, start + pattern.length(), value);
			}
		}

		return builder.toString();
	}

	public static String format(String urlPattern, String key, Object value) {

		StringBuilder builder = new StringBuilder(urlPattern);

		int start;
		String pattern = "{" + key + "}";

		// Substitui qualquer ocorrencia de '{<key>}
		while ((start = builder.indexOf(pattern)) != -1) {
			builder.replace(start, start + pattern.length(), value.toString());
		}

		return builder.toString();
	}

}
