package com.zz91.mail.client;

import javax.mail.Message;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.zz91.mail.service.impl.SimpleSendMailServiceImpl;

public class MailSendTest {
	Logger logger = Logger.getLogger(MailSendTest.class);

	//@Test
	public void mailSendStringContent() {
		logger.debug("1 创建一封邮件信息：邮箱地址：密码：SMTP服务器 ");
		EMailMessage evp = new EMailMessage(Message.RecipientType.TO, "tramp@sohu.com",
				"coolbuy710518!", "smtp.sohu.com");
		logger.debug("2、为邮件添加收件人");
		evp.addToEmail("liuwb820809@gmail.net");
		evp.addToEmail("liuwb0809@sohu.net");
		logger.debug("3、为邮件添邮件主题");
		evp.setSubject("test java mail ");
		logger.debug("4、为邮件添加内容主体");
		evp.setMailStringContent("这是邮件的内容。");
		logger.debug("发送消息");
		SimpleSendMailServiceImpl sm = new SimpleSendMailServiceImpl();
		sm.setDebug(true);
		sm.sendMail(evp);
	}

	@Test
	public void mailSendHtmlContent() {
		logger.debug("1 创建一封邮件信息：邮箱地址：密码：SMTP服务器 ");
		EMailMessage evp = new EMailMessage(Message.RecipientType.TO, "liuwb0809@sohu.com",
				"123456789sys", "smtp.sohu.com");
		logger.debug("2、为邮件添加收件人");
		evp.addToEmail("liuwb@zz91.net");
		evp.addToEmail("liuwb0809@sohu.com");
		evp.addToEmail("trampliu@sohu.com");
		logger.debug("3、为邮件添邮件主题");
		evp.setSubject("test java mail ");
		logger.debug("4、为邮件添加内容主体");
		EMailHtmlContent htmlContent = new EMailHtmlContent();
		htmlContent.setContentModule("test.vm");
		htmlContent.addContentValueToMap("name", "这个是名称");
		evp.setMailHtmlContent(htmlContent);
		logger.debug("发送消息");
		SimpleSendMailServiceImpl sm = new SimpleSendMailServiceImpl();
		sm.setDebug(false);
		sm.sendMail(evp);
	}

}
