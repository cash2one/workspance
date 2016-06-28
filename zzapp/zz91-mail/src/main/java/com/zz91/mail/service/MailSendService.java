package com.zz91.mail.service;

import com.zz91.mail.domain.MailInfoDomain;

public interface MailSendService {
	
	final static String DEFAULT_SENDER = "master@zz91.cn";
	final static String DEFAULT_CODE = "zz91";
	
	
    Integer sendEmail(MailInfoDomain jto);

    public Integer sendEmailByUsename(MailInfoDomain jto);

    public Integer sendEmailByCode(MailInfoDomain jto);

    public Integer sendEmailForThread(MailInfoDomain sto);
    
    public Integer doSendMail(MailInfoDomain sto);

}
