/**
 * Project name: commons-sms
 * File name: SMSClient.java
 * Copyright: 2005-2011 ASTO Info TechCo.,Ltd. All rights reserved
 */
package com.recyclechina.commons.sms;

import java.util.ResourceBundle;

import com.recyclechina.commons.sms.impl.SMSApiImpl;

/**
 * @author Ryan
 * @email rxm1025@gmail.com
 * @date 2011-3-22
 */
public class SMSClient {
    private static SMSApi smsApi = null;

    private SMSClient() {

    }

    public synchronized static SMSApi getClient() {
        if (smsApi == null) {
            ResourceBundle bundle = ResourceBundle.getBundle("sms-config");
            try {
                smsApi = new SMSApiImpl(bundle.getString("softwareSerialNo"),bundle.getString("password"), bundle.getString("key"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return smsApi;
    }
}
