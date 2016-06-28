package com.zz91.mail.service;

import java.io.IOException;

import javax.mail.MessagingException;

import com.zz91.mail.client.EMailMessage;

public interface SendMailService {

	/**
	 * 发送e_mail，返回类型为int 当返回值为0时，说明邮件发送成功 当返回值为3时，说明邮件发送失败
	 */
	int sendMail(EMailMessage envelope) throws IOException, MessagingException;

}