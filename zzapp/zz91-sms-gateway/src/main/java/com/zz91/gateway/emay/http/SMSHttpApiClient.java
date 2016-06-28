package com.zz91.gateway.emay.http;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class SMSHttpApiClient {
	private static SMSHttpClient client = null;

	private SMSHttpApiClient() {
	}

	public synchronized static SMSHttpClient getClient(String softwareSerialNo,
			String key) {
		if (client == null) {
			try {
				client = new SMSHttpClient(softwareSerialNo, key);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return client;
	}

	public synchronized static SMSHttpClient getClient() {
		ResourceBundle bundle = PropertyResourceBundle.getBundle("emay-http-config");
		if (client == null) {
			try {
				client = new SMSHttpClient(
						bundle.getString("softwareSerialNo"),
						bundle.getString("key"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return client;
	}

	public static void main(String str[]) {
		SMSHttpApiClient.getClient();
	}

}
