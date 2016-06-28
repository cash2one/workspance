/**
 * Project name: commons-sms
 * File name: SMSApiImplTest.java
 * Copyright: 2005-2011 ASTO Info TechCo.,Ltd. All rights reserved
 */
package com.recyclechina.commons.sms;

import org.junit.Assert;
import org.junit.Test;


/**
 * @author Ryan
 * @email rxm1025@gmail.com
 * @date 2011-3-22
 */
public class SMSApiImplTest {

    // @Test
    // public void testSendSMS() throws InterruptedException {
    // SMSClient.getClient().sendSMS(new String[] { "15990032576" }, "title",
    // "test");
    // SMSClient.getClient().sendSMS(new String[] { "15990032576" }, "title",
    // "test");
    // SMSClient.getClient().sendSMS(new String[] { "15990032576" }, "title",
    // "test");
    // Thread.sleep(100000);
    // }

     @Test
     public void testGetBalance() {
     String i = SMSClient.getClient().getBalance();
     Assert.assertTrue("余额不够", i!="0");
     }

    // @Test
    // public void testGetReceiveMessages() {
    // List<MO> list = SMSClient.getClient().getReplyMessages();
    // Assert.assertTrue(list.size() > 0);
    // }

    // @Test
    // public void testRegister() {
    // double i = SMSClient.getClient().register();
    // Assert.assertTrue("注册失败", i > 0);
    // }
}
