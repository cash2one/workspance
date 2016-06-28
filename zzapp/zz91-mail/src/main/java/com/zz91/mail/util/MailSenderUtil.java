package com.zz91.mail.util;

import java.io.File;
import java.io.StringWriter;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Deprecated
@Component("mailSenderUtil")
public class MailSenderUtil {
    protected static final Logger LOG = Logger.getLogger(MailSenderUtil.class);
    @Resource
    private JavaMailSender javaMailSender;// spring配置中定义
    @Resource
    private VelocityEngine velocityEngine;// spring配置中定义
    @Resource
    private SimpleMailMessage simpleMailMessage;// spring配置中定义
    private String from;
    private String to;
    private String subject;
    private String content;
    private String templateName;
    // 是否需要身份验证
    private boolean validate = false;

    /**
     * 发送模板邮件
     * 
     */
    public void sendWithTemplate(Map<String,Object> model) {
        simpleMailMessage.setTo(this.getTo()); // 接收人
        simpleMailMessage.setFrom(simpleMailMessage.getFrom()); // 发送人,从配置文件中取得
        simpleMailMessage.setSubject(this.getSubject());
        String result = null;
        try {
            StringWriter rw = new StringWriter();
            VelocityContext velocityContext = new VelocityContext(model);
            velocityEngine.mergeTemplate("mail.vm", "UTF-8", velocityContext, rw);
            result = rw.toString();
            // result =
            // VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
            // this.getTemplateName(), "UTF-8",model);
        } catch (Exception e) {
        }
        simpleMailMessage.setText(result);
        javaMailSender.send(simpleMailMessage);
    }

    /**
     * 发送普通文本邮件
     * 
     */
    public boolean sendText() {
        boolean flag = true;
        simpleMailMessage.setTo(this.getTo()); // 接收人
        simpleMailMessage.setFrom(this.getFrom()); // 发送人,从配置文件中取得
        simpleMailMessage.setSubject(this.getSubject());
        simpleMailMessage.setText(this.getContent());
        try {
            javaMailSender.send(simpleMailMessage);
        } catch (MailException e) {
            LOG.error("sendText email error");
            flag = false;
        }
        return flag;
    }

    /**
     * 发送普通Html邮件
     * 
     */
    public void sendHtml() {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        try {
            messageHelper.setTo(this.getTo());
            messageHelper.setFrom(simpleMailMessage.getFrom());
            messageHelper.setSubject(this.getSubject());
            messageHelper.setText(this.getContent(), true);
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        javaMailSender.send(mimeMessage);
    }

    /**
     * 发送普通带一张图片的Html邮件
     * 
     */
    public void sendHtmlWithImage(String imagePath) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setTo(this.getTo());
            messageHelper.setFrom(simpleMailMessage.getFrom());
            messageHelper.setSubject(this.getSubject());
            messageHelper.setText(this.getContent(), true);
            // Content="<html><head></head><body><img src=\"cid:image\"/></body></html>";
            // 图片必须这样子：<img src='cid:image'/>
            FileSystemResource img = new FileSystemResource(new File(imagePath));
            messageHelper.addInline("image", img);
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        javaMailSender.send(mimeMessage);
    }

    /**
     * 发送普通带附件的Html邮件
     * 
     */
    public void sendHtmlWithAttachment(String filePath) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setTo(this.getTo());
            messageHelper.setFrom(simpleMailMessage.getFrom());
            messageHelper.setSubject(this.getSubject());
            messageHelper.setText(this.getContent(), true);
            FileSystemResource file = new FileSystemResource(new File(filePath));
            messageHelper.addAttachment(file.getFilename(), file);
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        javaMailSender.send(mimeMessage);
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }

    public JavaMailSender getJavaMailSender() {
        return javaMailSender;
    }

    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    public SimpleMailMessage getSimpleMailMessage() {
        return simpleMailMessage;
    }

    public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
        this.simpleMailMessage = simpleMailMessage;
    }

}
