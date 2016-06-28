/**
 * Project name: zz91-mail
 * File name: MailInfoService.java
 * Copyright: 2005-2011 ASTO Info TechCo.,Ltd. All rights reserved
 */
package com.zz91.mail.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.zz91.mail.domain.MailInfoDomain;
import com.zz91.mail.domain.dto.PageDto;

/**
 * @author kongsj
 * @email kongsj@zz91.net
 * @date 2011-11-10
 */
public interface MailInfoService {
	
	public final static int SEND_SUCCESS=1;
	public final static int SEND_FAILURE=2;
	public final static int SEND_SENDING=3;
	public final static int SEND_EMAIL_ERROR=4;
	public final static int SEND_WAITING=0;
	
	
	public MailInfoDomain selectById(Integer id);

	public Integer insert(MailInfoDomain mto);

	public Integer update(MailInfoDomain mto);

	public Integer deleteById(Integer id);

	public List<MailInfoDomain> selectMailQueue(Map<String, Object> map);

	/***********************/
	
	public List<MailInfoDomain> queryMailForSend(Integer i);

	public Integer updateSending(Integer id);

	public Integer updateComplete(Integer id, Integer sendStatus);
	
	public boolean shutdownRecovery(Integer fromStatus, Integer toStatus);
	
	
	/********************************************************************/
	public  MailInfoDomain queryOne(Integer id);
	
	public Boolean resend(Integer id);
	
	public PageDto<MailInfoDomain> pageMail(Date from,Date to,Integer priority,PageDto<MailInfoDomain> page);
	
	public Boolean sendMail(String title,String receiver,String content,String sender);
}
