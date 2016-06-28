/**
 jiu * Project name: zz91-mail
 * File name: MailService.java
 * Copyright: 2005-2011 ASTO Info TechCo.,Ltd. All rights reserved
 */
package com.zz91.mail.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import com.zz91.mail.domain.MailInfoDomain;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;

/**
 * @author kongsj
 * @email kongsj@zz91.net
 * @date 2011-10-26
 */
public class MailService {
    private static Logger LOG = Logger.getLogger(MailService.class);
    private static MailService _instance = null;
    private static String SMTP_HOST_FILE = "mail-info.properties";// 邮件系统配置
    public MimeMessage mimeMsg; // 要发送的email信息
    private Session session;
    private Properties props;
    private String username = "";
    private String password = "";
    private Multipart mp; // 存放邮件的title 内容和附件
    private static InputStream is = null;
    private static Properties p = new Properties();
    private MailInfoDomain pto = null;

    public synchronized static MailService getInstance() {
        if (_instance == null) {
            _instance = new MailService();
            init();
        }
        return _instance;
    }

    public boolean sendMailStep1(MailInfoDomain mto) throws SQLException {
        boolean flag = false;
        pto = mto;
        String sql = buildSelectSql(mto);
        final Map<String, String> map = new HashMap<String, String>();
        DBUtils.select("zzmail", sql, new IReadDataHandler() {
            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    map.put("content", rs.getString(1));
                    // .....
                    // .....
                    // .....
                    // .....
                }
            }
        });

        // if (pto.getEmailParameter() != null) {
        // String content = buildEmailContent(pto.getEmailParameter(),
        // map.get("content"));
        // pto.setContent(content);
        // } else {
        // pto.setContent(map.get("content"));
        // }
        // str = rs.getString("t_content");
        sendMailStep2(pto);

        return flag;
    }

    /**
     * 遍历传过来的map逐一替换模板的内容
     * 
     * @param hostName
     * @return
     */
	public String buildEmailContent(Map<String, Object> map, String content) {
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            key = "$!{" + key + "}";
            content = content.replace(key, value);
        }
        return content;
    }

    private String buildSelectSql(MailInfoDomain mto) {
        StringBuffer sqlBuffer = new StringBuffer("");
        sqlBuffer.append("select t_content from template where code ='" + mto.getTemplateId() + "'");
        return sqlBuffer.toString();
    }

    public void SendMail(String stmp) {
        setSmtpHost(stmp);
        createMimeMessage();
    }

    public static void init() {
        is = MailService.getInstance().getClass().getClassLoader().getResourceAsStream(SMTP_HOST_FILE);
        try {
            p.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param hostName
     * @return
     */
    public void setSmtpHost(String hostName) {
        if (props == null) {
            props = System.getProperties();
        }
        props.put("mail.smtp.host", p.getProperty("host"));
        props.put("mail.smtp.port", "25");
        props.put("mail.smtp.auth", "true");
    }

    public boolean createMimeMessage() {
        try {
            LOG.debug("Session begin-----------");
            session = Session.getInstance(props, null);
        } catch (Exception e) {
            LOG.debug("Session.getInstance faild!" + e);
            return false;
        }
        LOG.debug("MimeMEssage begin---------!");
        try {
            mimeMsg = new MimeMessage(session);
            mp = new MimeMultipart();
            return true;
        } catch (Exception e) {
            LOG.debug("MimeMessage fiald! " + e.toString());
            return false;
        }
    }

    /**
     * 
     * @param name
     * @param pass
     */
    public void setNamePass() {
        String uname = (String) props.get("username");
        if (uname == null || uname.length() == 0) {
            username = p.getProperty("username");
        } else {
            username = uname;
        }
        String pass = (String) props.get("password");
        if (pass == null || pass.length() == 0) {
            password = p.getProperty("password");
        } else {
            password = pass;
        }
        props.put("password", password);
    }

    /**
     * 
     * @param mailSubject
     * @return boolean
     */
    public boolean setSubject(String mailSubject) {
        LOG.debug("set title begin.");
        try {
            if (!mailSubject.equals("") && mailSubject != null) {
                mimeMsg.setSubject(mailSubject);
            }
            return true;
        } catch (Exception e) {
            LOG.debug("set Title faild!");
            return false;
        }
    }

    /**
     * 添加附件..
     * 
     * @param filename
     * @return
     */
    public boolean addFileAffix(String filename) {
        LOG.debug("增加附件..");
        if (filename.equals("") || filename == null) {
            return false;
        }
        String file[];
        file = filename.split(";");
        LOG.debug("你有 " + file.length + " 个附件!");
        try {
            for (int i = 0; i < file.length; i++) {
                BodyPart bp = new MimeBodyPart();
                FileDataSource fileds = new FileDataSource(file[i]);
                bp.setDataHandler(new DataHandler(fileds));
                bp.setFileName(fileds.getName());
                mp.addBodyPart(bp);
            }
            return true;
        } catch (Exception e) {
            LOG.debug("增加附件: " + filename + "--faild!" + e);
            return false;
        }
    }

    /**
     * 
     * @param from
     * @return
     */
    public boolean setFrom(String from) {
        LOG.debug("Set From .");
        try {
            mimeMsg.setFrom(new InternetAddress(from));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 
     * @param to
     * @return
     */
    public boolean setTo(String to) {
        LOG.debug("Set to.");
        if (to == null || to.equals("")) {
            return false;
        }
        try {
            mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean setCopyTo(String copyto) {
        if (copyto.equals("") || copyto == null) {
            return false;
        }
        try {
            String copy[];
            copy = copyto.split(";");
            for (int i = 0; i < copy.length; i++) {
                mimeMsg.setRecipients(Message.RecipientType.TO, (Address[]) InternetAddress.parse(copy[i]));
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 设置信的内容!
     * 
     * @param mailBody
     * @return boolean
     */
    public boolean setBody(String mailBody) {
        try {
            BodyPart bp = new MimeBodyPart();
            bp.setContent("<meta http-equiv=Context-Type context=text/html;charset=gb2312>" + mailBody, "text/html;charset=GB2312");
            mp.addBodyPart(bp);
            return true;
        } catch (Exception e) {
            LOG.debug("Set context Faild! " + e);
            return false;
        }
    }

    /**
     * 
     * @param htmlpath
     * @return boolean
     */
    public boolean setHtml(String htmlpath) {
        try {
            if (!htmlpath.equals("") || htmlpath != null) {
                BodyPart mbp = new MimeBodyPart();
                DataSource ds = new FileDataSource(htmlpath);
                mbp.setDataHandler(new DataHandler(ds));
                mbp.setHeader("Context-ID", "meme");
                mp.addBodyPart(mbp);
            }
            return true;
        } catch (Exception e) {
            LOG.debug("Set Html Faild!" + e);
            return false;
        }
    }

    // 发送邮件
    public boolean setOut() {
        try {
            mimeMsg.setContent(mp);
            mimeMsg.saveChanges();
            LOG.debug("SendMail..");
            @SuppressWarnings("static-access")
            Session mailSession = session.getInstance(props, null);
            Transport tp = mailSession.getTransport("smtp");
            tp.connect((String) props.getProperty("mail.smtp.host"), username, password);
            tp.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.TO));
            LOG.debug("Send Mail Success..");
            tp.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void sendMailStep2(MailInfoDomain sto) {
        SendMail(sto.getSender());
        setNamePass();// 帐号密码
        setSubject(sto.getEmailTitle());// 标题
        setFrom(sto.getSender());// 发送方
        setTo(sto.getReceiver());// 接收方
        setBody(sto.getContent());// 正文
        boolean b = setOut();
        if (b) {
            LOG.debug("email send success");
        } else {
            LOG.debug("email send error");
        }
    }
    // public static void main(String[] args) {
    //
    // MailService sm=MailService.getInstance();
    // sm.SendMail("mail.zz91.cn");
    // sm.setNamePass("master@zz91.cn","88888888");
    // sm.setSubject("测试,测试");
    // sm.setFrom("master@zz91.cn");
    // sm.setTo("kxzjzjzzkx@sohu.com");
    // sm.addFileAffix("f:/adsl.txt");
    // StringBuffer bs=new StringBuffer();
    // bs.append("裴德万:\n");
    // bs.append("       测试度奇珍异宝埼地在檌!!!!!!!!!!!");
    // sm.setBody("DFSAAAAAAAAAAAAAAAAA");
    // sm.setNeedAuth(true);
    // boolean b=sm.setOut();
    // if(b){
    // System.out.println("\n邮件发送成功!!!!!");
    // }
    // else{
    // System.out.println("邮件发送失败!!!!");
    // }
    // }
}
