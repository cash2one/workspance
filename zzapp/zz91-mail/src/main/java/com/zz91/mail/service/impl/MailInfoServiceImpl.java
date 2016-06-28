/**
 * Project name: zz91-mail
 * File name: MailInfoServiceImpl.java
 * Copyright: 2005-2011 ASTO Info TechCo.,Ltd. All rights reserved
 */
package com.zz91.mail.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.zz91.mail.dao.MailInfoDao;
import com.zz91.mail.domain.AccountDomain;
import com.zz91.mail.domain.MailInfoDomain;
import com.zz91.mail.domain.dto.PageDto;
import com.zz91.mail.service.AccountService;
import com.zz91.mail.service.MailInfoService;

/**
 * @author kongsj
 * @email kongsj@zz91.net
 * @date 2011-11-10
 */
@Service("mailInfoService")
public class MailInfoServiceImpl implements MailInfoService {

    @Resource
    private MailInfoDao mailInfoDao;
    @Resource
    private AccountService accountService;
    @Override
    public MailInfoDomain selectById(Integer id) {
        return mailInfoDao.selectById(id);
    }

    @Override
    public Integer insert(MailInfoDomain mto) {
        return mailInfoDao.insert(mto);
    }

    @Override
    public Integer update(MailInfoDomain mto) {
        return mailInfoDao.update(mto);
    }

    @Override
    public Integer deleteById(Integer id) {
        return mailInfoDao.deleteById(id);
    }

    @Override
    public List<MailInfoDomain> selectMailQueue(Map<String, Object> map) {
        return mailInfoDao.selectMailQueue(map);
    }

    @Override
    public List<MailInfoDomain> queryMailForSend(Integer i) {
        return mailInfoDao.queryMailForSend(i);
    }

    @Override
    public Integer updateSending(Integer id) {
    	
        return mailInfoDao.updateSendStatus(id, MailInfoService.SEND_SENDING);
    }

    @Override
    public Integer updateComplete(Integer id, Integer sendStatus) {
//        Map<String,Integer> map = new HashMap<String,Integer>();
//        map.put("id", id);
//        map.put("sendStatus", sendStatus);
    	if(sendStatus==null){
    		return null;
    	}
        return mailInfoDao.updateSendStatus(id, sendStatus);
    }

	@Override
	public boolean shutdownRecovery(Integer fromStatus, Integer toStatus) {
		if(fromStatus==null||toStatus==null){
			return false;
		}
		if(fromStatus.intValue()==toStatus.intValue()){
			return false;
		}
		Integer i=mailInfoDao.recoverStatus(fromStatus, toStatus);
		if(i!=null){
			return true;
		}
		return false;
	}
	
	
	/***************************************************************************/

	@Override
	public PageDto<MailInfoDomain> pageMail(Date from, Date to,
			Integer priority, PageDto<MailInfoDomain> page) {
		if(page.getSort()==null){
			page.setSort("gmt_post");
		}
		if(page.getDir()==null){
			page.setDir("desc");
		}
			
		page.setRecords(mailInfoDao.queryMail(from, to, priority, page));
		page.setTotals(mailInfoDao.queryMailCount(from, to, priority));
		return page;
	}

	@Override
	public MailInfoDomain queryOne(Integer id) {
		return mailInfoDao.queryOne(id);
	}

	@Override
	public Boolean resend(Integer id) {
		Integer i=mailInfoDao.updateSendStatus(id, SEND_WAITING);
		if(i!=null && i.intValue()>0){
			return true;
		}
		return false;
	}

	@Override
	public Boolean sendMail(String title, String sender, String receiver,String content) {
		MailInfoDomain mailInfoDomain=new MailInfoDomain();
		String code = mailInfoDomain.getAccountCode();
		AccountDomain accountDomain = accountService
		.randomAccountFromCache(code);
		if (accountDomain == null) {
			return null;
		}
		mailInfoDomain.setTemplateId("");
		mailInfoDomain.setEmailTitle(title);
		mailInfoDomain.setReceiver(receiver);
		mailInfoDomain.setContent(content);
		mailInfoDomain.setSendStatus(0);
		mailInfoDomain.setSender(sender);
		mailInfoDomain.setPriority(0);
		mailInfoDomain.setGmtPost(new Date());
		
		Integer i=mailInfoDao.insert(mailInfoDomain);
		if(i!=null && i.intValue()>0){
			return true;
		}
		return false;
	}

}
