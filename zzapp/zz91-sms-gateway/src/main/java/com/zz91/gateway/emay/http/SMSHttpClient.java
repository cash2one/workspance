package com.zz91.gateway.emay.http;

import java.rmi.RemoteException;

public class SMSHttpClient {
	private String softwareSerialNo;
	private String key;
	private static int SMS_PRIORITY = 5; // 发送优先级
	private static String ADDSERIAL = ""; // 顺序
	private static String GBK_CHARSET = "gbk";

	public SMSHttpClient(String sn, String key) {
		this.softwareSerialNo = sn;
		this.key = key;
		init();
	}

	SDKServiceBindingStub binding;

	public void init() {
		try {
			binding = (SDKServiceBindingStub) new SDKServiceLocator()
					.getSDKService();
		} catch (javax.xml.rpc.ServiceException jre) {
			if (jre.getLinkedCause() != null)
				jre.getLinkedCause().printStackTrace();
			System.out.println("JAX-RPC ServiceException caught: " + jre);
		}
	}

	public double getBalance() throws RemoteException {
		double value = 0.0;
		value = binding.getBalance(softwareSerialNo, key);
		return value;
	}

	public int sendSMS(String[] mobiles, String smsContent, String addSerial,
			int smsPriority) throws RemoteException {
		int value = -1;
		value = binding.sendSMS(softwareSerialNo, key, "", mobiles, smsContent,
				ADDSERIAL, GBK_CHARSET, SMS_PRIORITY, 0);
		return value;
	}

}
