package com.zz91.gateway;

import java.rmi.RemoteException;

import com.zz91.gateway.emay.http.SMSHttpApiClient;
import com.zz91.sms.common.ZZSms;

public class EmayWebServiceGateway implements ZZSms {
	
	@Override
	public Integer send(String mobile, String content) {
		String [] mobiles = new String[]{mobile};
		Integer sendStatus = 1; 
		try {
			sendStatus = SMSHttpApiClient.getClient().sendSMS(mobiles, content, "", 5);
		} catch (RemoteException e) {
			//抛错，显示发送中
			return FAILURE;
		}
		if(sendStatus==0){
			// emay 0 为成功，return 2 为sms_log 表示发送成功 
			return SUCCESS;
		}
		return sendStatus;
	}

	@Override
	public Object balance() {
		try {
			return SMSHttpApiClient.getClient().getBalance()*10;
		} catch (RemoteException e) {
			//抛错 
			return -1;
		}
	}

	
}
