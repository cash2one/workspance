/**
 * Project name: zz91-mail
 * File name: MailInfoDao.java
 * Copyright: 2005-2011 ASTO Info TechCo.,Ltd. All rights reserved
 */
package com.zz91.mail.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.zz91.mail.domain.MailInfoDomain;
import com.zz91.mail.domain.dto.PageDto;

/**
 * 邮件消息
 * @author kongsj
 * @email kongsj@zz91.net
 * @date 2011-11-10
 */
public interface MailInfoDao {
	public MailInfoDomain selectById(Integer id);

    public Integer insert(MailInfoDomain mto);

    public Integer update(MailInfoDomain mto);

    public List<MailInfoDomain> selectMailQueue(Map<String,Object>map);
    
    public List<MailInfoDomain> queryMailForSend(Integer i);
    
//    Integer updateSending(Integer id);
    
//    Integer updateComplete(Map<String,Integer> map);
    
    public Integer updateSendStatus(Integer id, Integer status);
    
    public Integer recoverStatus(Integer fromStatus, Integer toStatus);
    
    
    /**************************************/
    public MailInfoDomain queryOne(Integer id);
    
    public Integer deleteById(Integer id);
    
    public Integer queryMailCount(Date from,Date to,Integer priority);
    
    public List<MailInfoDomain> queryMail(Date from,Date to,Integer priority,PageDto<MailInfoDomain> page);
}
