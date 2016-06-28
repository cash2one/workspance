package com.zz91.mail.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import com.zz91.mail.client.EMailMessage;
import com.zz91.mail.exception.ZZ91MailException;
import com.zz91.mail.service.SendMailService;
import com.zz91.util.lang.StringUtils;

public class SimpleSendMailServiceImpl implements SendMailService {
	private Logger logger = Logger.getLogger(SimpleSendMailServiceImpl.class);
	//是否采用调试方式
	private boolean debug = true;
	private String messageContentMimeType = "text/html; charset=utf-8";

	public void setDebug(boolean debugFlag) {
		debug = debugFlag;
	}

	public void setMessageContentMimeType(String mimeType) {
		messageContentMimeType = mimeType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.liu.mail.SendMailService#sendMail()
	 */
	public int sendMail(final EMailMessage envelope) {
		String errorMsg = null;
		if (envelope == null) {
			if (logger.isDebugEnabled())
				logger.debug("邮件信息对象［envelope］不存在！");
			throw new ZZ91MailException("邮件信息不存在！");
		}
		errorMsg = envelope.isAvalible();
		if (errorMsg != null) {
			if (logger.isDebugEnabled())
				logger.debug("邮件信息［envelope］内容不对或信息不全：" + errorMsg);
			throw new ZZ91MailException(errorMsg);
		}
		Properties props = System.getProperties();
		props.put("mail.smtp.host", envelope.getSmtpHost());
		props.put("mail.smtp.auth", "true");
		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(envelope.getFormEmail(), envelope.getPassword());
			}
		});
		session.setDebug(debug);
		MimeMessage msg = new MimeMessage(session);
		Transport trans = null;
		try {
			fillMail(envelope, session, msg);
			trans = session.getTransport("smtp");
			trans.connect(envelope.getSmtpHost(), envelope.getFormEmail(), envelope.getPassword());
			trans.sendMessage(msg, msg.getAllRecipients());
			trans.close();
		} catch (NoSuchProviderException e) {
			throw new ZZ91MailException("连接邮件服务器错误：验证失败", e);
		} catch (IOException e) {
			throw new ZZ91MailException("连接邮件服务器错误", e);
		} catch (MessagingException e) {
			throw new ZZ91MailException("发送邮件失败：", e);
		} finally {
			try {
				if (trans != null && trans.isConnected())
					trans.close();
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
		if (logger.isDebugEnabled())
			logger.debug("发送邮件成功！");
		return 0;
	}

	private void fillMail(EMailMessage envelope, Session session, MimeMessage msg)
			throws IOException, MessagingException {
		Multipart mPart = new MimeMultipart();
		//set sender information
		InternetAddress senderAddress = new InternetAddress(envelope.getFormEmail());
		msg.setFrom(senderAddress);
		//设置回复地址
		InternetAddress[] replyAddress = {senderAddress};
		msg.setReplyTo(replyAddress);
		if (logger.isDebugEnabled())
			logger.debug("发送人Mail地址：" + envelope.getFormEmail());
		// Message.RecipientType.TO Message.RecipientType.CC  Message.RecipientType.BCC
		InternetAddress[] address = InternetAddress.parse(StringUtils.getCollectionStringBySplit(
				envelope.getToEmails(), ","), false);
		msg.setRecipients(envelope.getRecipientType(), address);
		if (logger.isDebugEnabled())
			logger.debug("收件人Mail地址："
					+ StringUtils.getCollectionStringBySplit(envelope.getToEmails(), ","));
		msg.setSubject(envelope.getSubject());
		// create and fill the first message part
		MimeBodyPart mBodyContent = new MimeBodyPart();
		if (StringUtils.isNotEmpty(envelope.getMailStringContent())) {
			mBodyContent.setContent(envelope.getMailStringContent(), messageContentMimeType);
			mPart.addBodyPart(mBodyContent);
		} else {
			String htmlcontent = envelope.getMailHtmlContent().getHtmlContentString();
			mBodyContent.setContent(StringUtils.getNotNullValue(htmlcontent),
					messageContentMimeType);
			mPart.addBodyPart(mBodyContent);
		}
		// attach the file to the message
		attachFiles(mPart, envelope.getAttachedFileList());
		msg.setContent(mPart);
		msg.setSentDate(new Date());
	}

	private void attachFiles(Multipart mPart, List<String> fileList) throws MessagingException {
		if (fileList != null && fileList.size() > 0) {
			for (String fileName : fileList) {
				MimeBodyPart mBodyPart = new MimeBodyPart();
				FileDataSource fds = new FileDataSource(fileName);
				if (logger.isDebugEnabled())
					logger.debug("Mail发送的附件为：" + fileName);
				mBodyPart.setDataHandler(new DataHandler(fds));
				mBodyPart.setFileName(fileName);
				mPart.addBodyPart(mBodyPart);
			}
		}
	}

}
