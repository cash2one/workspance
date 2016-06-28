/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-6-28
 */
package com.ast.ast1949.service.bbs.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.bbs.BbsPostDO;
import com.ast.ast1949.domain.bbs.BbsUserProfilerDO;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.persist.bbs.BbsPostDAO;
import com.ast.ast1949.persist.bbs.BbsPostReplyDao;
import com.ast.ast1949.persist.bbs.BbsUserProfilerDao;
import com.ast.ast1949.persist.company.CompanyAccountDao;
import com.ast.ast1949.service.bbs.BbsUserProfilerService;
import com.ast.ast1949.util.Assert;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-6-28
 */
@Component("bbsUserProfilerService")
public class BbsUserProfilerServiceImpl implements BbsUserProfilerService{

	@Resource
	private BbsUserProfilerDao bbsUserProfilerDao;
	
	@Resource
	private CompanyAccountDao companyAccountDao;
	@Resource
	private BbsPostDAO bbsPostDAO;
	@Resource
	private BbsPostReplyDao bbsPostReplyDao;
	
	@Override
	public Integer queryIntegralByAccount(String account) {
		Assert.notNull(account, "the account can not be null");
		Integer i=bbsUserProfilerDao.queryIntegralByAccount(account);
		if(i==null){
			i=0;
		}
		return i;
	}

	@Override
	public boolean isProfilerExist(String account) {
		Integer i = bbsUserProfilerDao.countProfilerByAccount(account);
		if(i!=null && i.intValue()>0){
			return true;
		}
		return false;
	}

	@Override
	public Integer createEmptyProfilerByUser(String account) {
		CompanyAccount companyAccount = companyAccountDao.queryAccountByAccount(account);
		BbsUserProfilerDO profiler =new BbsUserProfilerDO();
		if (companyAccount != null) {
			profiler.setCompanyId(companyAccount.getCompanyId());
			profiler.setTel(companyAccount.getTel());
			profiler.setMsn(companyAccount.getMsn());
			profiler.setRealName(companyAccount.getContact());
		}else{
			return null;
		}
		profiler.setAccount(account);
		profiler.setIntegral(0);
		profiler.setPostNumber(0);
		profiler.setEssenceNumber(0);
		profiler.setReplyNumber(0);
		return bbsUserProfilerDao.insertProfiler(profiler);
	}

	@Override
	public List<BbsUserProfilerDO> queryTopByPostNum(Integer size) {
		if(size==null){
			size=6;
		}
		return bbsUserProfilerDao.queryTopByPostNum(size);
	}

	@Override
	public BbsUserProfilerDO queryProfilerOfAccount(String account) {
		BbsUserProfilerDO profiler=bbsUserProfilerDao.queryProfilerOfAccount(account);
		if(profiler==null){
			profiler=new BbsUserProfilerDO();
			profiler.setAccount(account);
		}
		return profiler;
	}

	@Override
	public Integer updatePostNumber(String account) {
		Integer i=bbsPostDAO.queryPostByUserCount(account, "1", "0");
		return bbsUserProfilerDao.updatePostNumber(account,i);
	}
	
	@Override
	public Integer updateReply(Integer replyCount, String account) {
		return bbsUserProfilerDao.updateReplyCount(replyCount, account);
	}

	@Override
	public Integer updateBbsReplyCount(String account) {
		Integer i=bbsPostReplyDao.queryReplyByUserCount(account, "1");
		return bbsUserProfilerDao.updateReplyCount(i, account);
	}
}
