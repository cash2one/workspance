/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on Oct 13, 2010
 */
package com.zz91.mail.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

/**
 * @author mays(x03570227@gmail.com)
 *
 * Created on Oct 13, 2010
 */
public class ZZmail {

	private static ZZmail _instance = null;
	
	private ZZmail(){
		
	}
	public synchronized static ZZmail getInstance(){
		if(_instance==null){
			_instance=new ZZmail();
		}
		return _instance;
	}
	private static final Logger LOG = Logger.getLogger(ZZmail.class);
	
	/**
	 * 配置信息所在的资源文件名称
	 */
	private final static String PROP_FILE = "mail.properties";
	/**
	 * 发送HTML邮件内容时使用的头信息
	 */
	private final static String MAIL_TYPE = "text/html;charset=utf-8";
	/**
	 * 发送成功时的状态：200
	 */
	public final static int SUCCESS = 200;
	/**
	 * 邮件发送失败时的状态：500
	 */
	public final static int FAILURE = 500;
	
	/**
	 * 发送邮件时需要使用的配置，从配置文件读取
	 */
	private final static Map<String, String> mailConfig=new HashMap<String, String>();
	
	/**
	 * 初始化邮件配置，从配置文件读取，配置文件的名称：{@link #PROP_FILE}
	 */
	private void initConfig(){
		if(mailConfig.size()==0){
			LOG.debug("{mail} Start reading the configuration file:"+PROP_FILE);
			Properties props = new Properties();
			InputStream inputstream = this.getClass().getClassLoader().getResourceAsStream(PROP_FILE);
			try {
				props.load(inputstream);
				String tmp = null;
				for (Object key : props.keySet()) {
					tmp = String.valueOf(key);
					if (tmp != null) {
						mailConfig.put(String.valueOf(key), String.valueOf(props.get(key)));
					}
				}
				LOG.debug("{mail} Configuration file have bean read.");
			} catch (IOException e) {
				LOG.error("{mail} Config files wrong:"+PROP_FILE, e);
			}
		}
	}
	
	public String getContentAccordingTemplate(String template,Map<String,String> valueMap){
		for (String key : valueMap.keySet()) {
			if (valueMap.get(key) != null){
				template=template.replaceAll("#:"+key+"#", valueMap.get(key));
			}
		}
		return template;
	}
	/**
	 * 发送邮件到指定地址
	 * @param from:发送者的email地址
	 * @param to:邮件发送的目标email地址
	 * @param password:邮件发送者的登录密码
	 * @param subject:邮件的标题
	 * @param body:邮件的正文,可以是纯文本的内容,也可以是html内容
	 * @return
	 * 		当邮件发送成功时返回 {@link #SUCCESS}
	 * 		当邮件发送失败时返回 {@link #FAILURE}
	 */
	public int send(String from, String to, String password, String subject, String body){
		LOG.debug("{mail} Preparing to send a message...");
		
		initConfig();
		
		final String username = from.split("@")[0];
		final String pwd = password;
		
		Properties props = new Properties();
		
		for(String key:mailConfig.keySet()){
			props.put(key, mailConfig.get(key));
		}
		
//		props.put("mail.smtp.user", from);
//		props.put("mail.smtp.password", password);
		
		Session session = Session.getDefaultInstance(props, new Authenticator() {
			@Override
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, pwd);
			}
		});
		
		session.setDebug(Boolean.valueOf(mailConfig.get("debug")));
		
		Message msg = new MimeMessage(session);
		try {
			InternetAddress[] address = InternetAddress.parse(to, false);
			
			msg.setFrom(new InternetAddress(from));
			msg.setRecipients(Message.RecipientType.TO, address);

			msg.setSubject(subject);
			msg.setContent(body, MAIL_TYPE);
			msg.setSentDate(new Date());
			
			msg.saveChanges();
			
			LOG.debug("{mail} Sending message...");
			Transport.send(msg);
			LOG.debug("{mail} Message has been sent.");
			return SUCCESS;
		} catch (AddressException e) {
			LOG.error("{mail} Email adress is not correct.", e);
		} catch (MessagingException e) {
			LOG.error("{mail} Create message failure.",e);
		}
		
		return FAILURE;
	}
	
	/**
	 * 为系统添加定时发送邮件的任务,邮件定时发送任务最终会被提交到task模块进行处理
	 * @param from:发送者的email地址
	 * @param to:邮件发送的目标email地址
	 * @param password:邮件发送者的登录密码
	 * @param subject:邮件的标题
	 * @param body:邮件的正文,可以是纯文本的内容,也可以是html内容
	 * @param byDate
	 * @return
	 * 		当邮件发送成功时返回 {@link #SUCCESS}
	 * 		当邮件发送失败时返回 {@link #FAILURE}
	 */
	public int sendOnTime(String from, String to, String password, String subject, String body, Date byDate){
		//需要在此处与task模块进行安全认证,确保信息的提交是合法的
		return FAILURE;
	}
	
	public static void main(String[] args) {
		String body="<h1>email html</h1>hello email";

		body=ZZmail.getInstance().getContentAccordingTemplate(body, new HashMap<String, String>());

		int i= ZZmail.getInstance().send("d6agdpa@163.com", "mays@zz91.net", "135246", "email test", body);
		System.out.println(i);
	}
	
}
