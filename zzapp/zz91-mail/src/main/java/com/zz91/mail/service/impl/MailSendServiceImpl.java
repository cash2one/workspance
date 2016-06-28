package com.zz91.mail.service.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.zz91.mail.dao.MailInfoDao;
import com.zz91.mail.dao.TemplateDao;
import com.zz91.mail.domain.AccountDomain;
import com.zz91.mail.domain.MailInfoDomain;
import com.zz91.mail.domain.TemplateDomain;
import com.zz91.mail.service.AccountService;
import com.zz91.mail.service.MailSendService;
import com.zz91.util.lang.StringUtils;

@Service("mailSendService")
public class MailSendServiceImpl implements MailSendService {

	private static Logger LOG = Logger.getLogger(MailSendServiceImpl.class);

	private static final String MAIL_TYPE = "text/html;charset=utf-8";
	public final static int SUCCESS = 1;
	public final static int FAILURE = 2;

	// private static final String DEFAULT_ACCOUNT = "master@zz91.cn";
	// private static final String DEFAULT_PASSWORD = "88888888";
	// private static final String DEFAULT_HOST = "mail.zz91.cn";
	// private static final String DEFAULT_FROM = "service@zz91.cn";

	@Resource
	private TemplateDao templateDao;
	@Resource
	private MailInfoDao mailInfoDao;
	@Resource
	private AccountService accountService;

	/**
	 * 根据模板编号获取模板内容
	 */
	private String getTemplateContentByTemplateCode(String templateCode) {
		TemplateDomain templateDomain = templateDao
				.queryTemplateByCode(templateCode);
		if (templateDomain != null && templateDomain.gettContent() != null) {
			return templateDomain.gettContent();
		} else {
			return "";
		}
	}

	/**
	 * 根据模板内容组装email正文
	 */
	private String buildEmailContent(String emailContent,
			Map<String, Object> map) {
		StringWriter w = new StringWriter();
		VelocityContext c = new VelocityContext(map);
		try {
			Velocity.evaluate(c, w, "EmailContent", emailContent);
		} catch (ParseErrorException e) {
			e.printStackTrace();
		} catch (MethodInvocationException e) {
			e.printStackTrace();
		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String str = w.toString();
		if (str != null && str.length() > 0) {
			return w.toString();
		} else {
			return "";
		}
	}

	/**
	 * insert数据入email_info表
	 */
	private Integer insertInfoToTable(MailInfoDomain mto) {
		Integer i = mailInfoDao.insert(mto);
		if (i > 0) {
			return i;
		}
		return null;
	}

	/**
	 * 根据不同的账户和条件选择Host
	 * 
	 * @param ato
	 */
	private Properties getSmtpConfig(MailInfoDomain mailInfoDomain) {
		Properties props = new Properties();
		props.put("mail.smtp.host", mailInfoDomain.getSendHost());
		props.put("mail.smtp.port", "25");
		props.put("mail.smtp.auth", "true");
		props.put("mail.transport.protocl", "smtp");
		return props;
	}

	/**
	 * 邮件title
	 * 
	 * @param mailSubject
	 */
	// private void setTitle(String mailTitle, MimeMessage mimeMsg) {
	// if (!mailTitle.equals("") && mailTitle != null) {
	// try {
	// mimeMsg.setSubject(mailTitle);
	// } catch (MessagingException e) {
	// LOG.error("setTitle error");
	// e.printStackTrace();
	// }
	// }
	// }

	/**
	 * set邮件正文
	 * 
	 * @param contentId
	 * @param map
	 */
	// private void setEmailContent(String emailContent, Multipart mp) {
	// BodyPart bp = new MimeBodyPart();
	// try {
	// bp.setContent(
	// "<meta http-equiv=Context-Type context=text/html;charset=gb2312>"
	// + emailContent, "text/html;charset=GB2312");
	// mp.addBodyPart(bp);
	// } catch (MessagingException e) {
	// LOG.debug("setEmailContent error");
	// e.printStackTrace();
	// }
	// }

	/**
	 * 添加附件
	 * 
	 * @param filename
	 * @param mp
	 * @return public boolean addFileAffix(String filename, Multipart mp) {
	 *         LOG.debug("增加附件.."); if (filename.equals("") || filename == null)
	 *         { return false; } filename = filename.replace("http:",
	 *         "http://"); String file[]; file = filename.split(";");
	 *         LOG.debug("你有 " + file.length + " 个附件!"); try { for (int i = 0; i
	 *         < file.length; i++) { BodyPart bp = new MimeBodyPart();
	 *         FileDataSource fileds = new FileDataSource(file[i]);
	 *         bp.setDataHandler(new DataHandler(fileds));
	 *         bp.setFileName(fileds.getName()); mp.addBodyPart(bp); } return
	 *         true; } catch (Exception e) { LOG.debug("增加附件: " + filename +
	 *         "--faild!" + e); return false; } }
	 */

	/**
	 * set邮件发送方
	 * 
	 * @param from
	 */
	// private void setFrom(String from, MimeMessage mimeMsg) {
	// LOG.debug("Set From .");
	// try {
	// mimeMsg.setFrom(new InternetAddress(from));
	// } catch (Exception e) {
	// LOG.error("setFrom error");
	// }
	// }

	/**
	 * set邮件接收者
	 * 
	 * @param to
	 */
	// private void setTo(String to, MimeMessage mimeMsg) {
	// LOG.debug("Set to.");
	// try {
	// mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress
	// .parse(to));
	// } catch (Exception e) {
	// LOG.error("setTo error");
	// }
	// }

	/**
	 * 发送邮件
	 * 
	 * @param accountDomain
	 * @param mailInfoDomain
	 * @param mailId
	 * @return
	 */
	public Integer doSendMail(MailInfoDomain mailInfoDomain) {

		if (StringUtils.isEmpty(mailInfoDomain.getReceiver())) {
			return FAILURE;
		}
		
		if(StringUtils.isEmpty(mailInfoDomain.getSender())){
			
		}

		Properties props = getSmtpConfig(mailInfoDomain);

		final String username = mailInfoDomain.getSendName();
		final String pwd = mailInfoDomain.getSendPassword();

		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, pwd);
			}
		});

		// session.setDebug(true);

		MimeMessage msg = new MimeMessage(session);
		try {
			InternetAddress[] address = InternetAddress.parse(mailInfoDomain
					.getReceiver(), false);
			if (StringUtils.isEmpty(mailInfoDomain.getNickname())) {
				msg.setFrom(new InternetAddress(mailInfoDomain.getSender()));
			} else {
				msg.setFrom(new InternetAddress(mailInfoDomain.getSender(),
						mailInfoDomain.getNickname()));
			}
			msg.setRecipients(Message.RecipientType.TO, address);

			msg.setSubject(mailInfoDomain.getEmailTitle());
			msg.setContent(mailInfoDomain.getContent(), MAIL_TYPE);
			msg.setSentDate(new Date());

			msg.saveChanges();

			Transport.send(msg);
			return SUCCESS;
		} catch (Exception e) {
			LOG.error("Send Mail error. Mail:[subject]"
					+ mailInfoDomain.getEmailTitle() + "[form]"
					+ mailInfoDomain.getSender() + " error:" + e.getMessage());
		}
		return FAILURE;
	}

	/**
	 * 发送默认配置的邮件(客户端没有传入smtp帐号和账户code)
	 */
	@Override
	public Integer sendEmail(MailInfoDomain mailInfoDomain) {
		// 存入mail_info表
		mailInfoDomain
				.setContent(buildEmailContent(
						getTemplateContentByTemplateCode(mailInfoDomain
								.getTemplateId()), mailInfoDomain
								.getEmailParameter()));
		mailInfoDomain.setSendStatus(0);
		setDefaultHostInfo(mailInfoDomain);
		return insertInfoToTable(mailInfoDomain);
	}

	/**
	 * 当客户端传给来的用户名和密码为空，但账户code不为空时，则将用传过来的账户code从数据库取得SMTP服务器登录信息并且发送邮件
	 * 此方法包含业务逻辑为： 1.通过传过来的账户code去账户表中查询账户信息，若信息有多个，则随机取得一个即可
	 * 2.通过传过来的模板code来取得模板并且与传过来的参数组装成邮件信息 3.配置发送邮件服务器信息并且将邮件信息存入数据库
	 * 4.发送邮件并且更新邮件信息表
	 */
	@Override
	public Integer sendEmailByCode(MailInfoDomain mailInfoDomain) {
		mailInfoDomain.setContent(buildEmailContent(getTemplateContentByTemplateCode(mailInfoDomain.getTemplateId()), mailInfoDomain.getEmailParameter()));
		String code = mailInfoDomain.getAccountCode();
		// AccountDomain accountDomain = accountDao.queryAccountByCode(code);
		AccountDomain accountDomain = accountService
				.randomAccountFromCache(code);
		if (accountDomain == null) {
			return null;
		}

		mailInfoDomain.setSender(accountDomain.getEmail());
		mailInfoDomain.setSendName(accountDomain.getUsername());
		mailInfoDomain.setSendPassword(accountDomain.getPassword());
		mailInfoDomain.setSendHost(accountDomain.getHost());
		mailInfoDomain.setNickname(accountDomain.getNickname());
		mailInfoDomain.setSendStatus(0);

		return mailInfoDao.insert(mailInfoDomain);
	}

	/**
	 * 当客户端传给来的用户名和密码不为空时，并且账户code为空时，则将用传过来的用户名密码登录SMTP服务器并且发送邮件 此方法包含业务逻辑为：
	 * 1.验证传过来的用户名是否已存在于账户表中，若存在，则取出账户信息，若不存在，则截取域来确定SMTP服务器主机以及端口信息
	 * 2.通过传过来的模板code来取得模板并且与传过来的参数组装成邮件信息 3.配置发送邮件服务器信息并且将邮件信息存入数据库
	 * 4.发送邮件并且更新邮件信息表
	 */
	@Override
	public Integer sendEmailByUsename(MailInfoDomain mailInfoDomain) {
		mailInfoDomain
				.setContent(buildEmailContent(
						getTemplateContentByTemplateCode(mailInfoDomain
								.getTemplateId()), mailInfoDomain
								.getEmailParameter()));
		AccountDomain accountDomain = accountService
				.queryAccountByUsername(mailInfoDomain.getSendName());

		if (accountDomain == null) {
			return null;
		}

		if (accountDomain != null) {
			mailInfoDomain.setSender(accountDomain.getEmail());
			mailInfoDomain.setSendHost(accountDomain.getHost());
			mailInfoDomain.setSendPassword(accountDomain.getPassword());
		}

		mailInfoDomain.setSendStatus(0);
		
		return mailInfoDao.insert(mailInfoDomain);
	}

	/**
	 * 发送定时邮件
	 */
	@Override
	public Integer sendEmailForThread(MailInfoDomain mailInfoDomain) {
		AccountDomain accountDomain = new AccountDomain();
		String email = mailInfoDomain.getSender();
		int d = email.indexOf("@");
		if (d == -1) {
			return 0;
		}
		email = email.substring(d, email.length());
		if (email.indexOf("huanbao") > -1) {
			accountDomain.setHost("mail.huanbao.com");
			accountDomain.setUsername(mailInfoDomain.getSender());
			accountDomain.setPassword("88888888");
		}
		return doSendMail(mailInfoDomain);
	}

	// final static Map<String, String> SMTP_MAP = new HashMap<String,
	// String>();
	// static {
	// SMTP_MAP.put("huanbao.com", "mail.huanbao.com");
	// SMTP_MAP.put("zz91.com", "mail.zz91.com");
	// SMTP_MAP.put("zz91.cn", "mail.zz91.cn");
	// SMTP_MAP.put("zz91.net", "mail.zz91.net");
	// }

	// private String getSmtpHost(String email) {
	// if (!StringUtils.isEmail(email)) {
	// return null;
	// }
	// int d = email.indexOf("@");
	// String domain = email.substring(d, email.length());
	// return SMTP_MAP.get(domain);
	// }

	/**
	 * set default host,account,password
	 * 
	 * @param mailInfoDomain
	 */
	private void setDefaultHostInfo(MailInfoDomain mailInfoDomain) {
		mailInfoDomain.setSendHost("mail.zz91.cn");
		AccountDomain accountDomain = accountService.queryAccountByCode(DEFAULT_CODE);
		mailInfoDomain.setSender(DEFAULT_SENDER);
		mailInfoDomain.setSendName(accountDomain.getUsername());
		mailInfoDomain.setSendPassword(accountDomain.getPassword());
	}
}
