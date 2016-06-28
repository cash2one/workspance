/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-6-28
 */
package com.ast.ast1949.service.bbs.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.bbs.BbsUserProfilerDO;
import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.bbs.BbsPostSearchDto;
import com.ast.ast1949.dto.bbs.BbsUserProfilerDto;
import com.ast.ast1949.persist.bbs.BbsPostDAO;
import com.ast.ast1949.persist.bbs.BbsPostReplyDao;
import com.ast.ast1949.persist.bbs.BbsUserProfilerDao;
import com.ast.ast1949.persist.company.CompanyAccountDao;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.service.bbs.BbsService;
import com.ast.ast1949.service.bbs.BbsUserProfilerService;
import com.ast.ast1949.util.Assert;
import com.zz91.util.lang.StringUtils;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-6-28
 */
@Component("bbsUserProfilerService")
public class BbsUserProfilerServiceImpl implements BbsUserProfilerService {

	@Resource
	private BbsUserProfilerDao bbsUserProfilerDao;

	@Resource
	private CompanyDAO companyDAO;
	@Resource
	private CompanyAccountDao companyAccountDao;
	@Resource
	private BbsPostDAO bbsPostDAO;
	@Resource
	private BbsPostReplyDao bbsPostReplyDao;

	@Resource
	private BbsService bbsService;

	@Override
	public Integer queryIntegralByAccount(String account) {
		Assert.notNull(account, "the account can not be null");
		Integer i = bbsUserProfilerDao.queryIntegralByAccount(account);
		if (i == null) {
			i = 0;
		}
		return i;
	}

	@Override
	public boolean isProfilerExist(String account) {
		Integer i = bbsUserProfilerDao.countProfilerByAccount(account);
		if (i != null && i.intValue() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Integer createEmptyProfilerByUser(String account) {
		CompanyAccount companyAccount = companyAccountDao
				.queryAccountByAccount(account);
		BbsUserProfilerDO profiler = new BbsUserProfilerDO();
		if (companyAccount != null) {
			profiler.setCompanyId(companyAccount.getCompanyId());
			profiler.setTel(companyAccount.getTel());
			profiler.setMsn(companyAccount.getMsn());
			profiler.setRealName(companyAccount.getContact());
		} else {
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
		if (size == null) {
			size = 6;
		}
		return bbsUserProfilerDao.queryTopByPostNum(size);
	}

	@Override
	public BbsUserProfilerDO queryProfilerOfAccount(String account) {
		BbsUserProfilerDO profiler = bbsUserProfilerDao.queryProfilerOfAccount(account);
		if (profiler == null) {
			profiler = new BbsUserProfilerDO();
			profiler.setAccount(account);
		}
		if(StringUtils.isEmpty(profiler.getNickname())){
			CompanyAccount ca=companyAccountDao.queryAccountByAccount(account);
			if(ca!=null&&StringUtils.isNotEmpty(ca.getContact())){
				profiler.setNickname(ca.getContact());
			}else{
				profiler.setNickname("匿名");
			}
			if(profiler.getNickname().length()>6){
				profiler.setNickname(profiler.getNickname().substring(0, 6));
			}
		}
		return profiler;
	}

	@Override
	public Integer updatePostNumber(String account) {
		BbsPostSearchDto searchDto = new BbsPostSearchDto();
		searchDto.setAccount(account);
		searchDto.setCheckStatus("1");
		searchDto.setIsDel("0");
		Integer i = bbsPostDAO.queryPostByUserCount(searchDto);
		return bbsUserProfilerDao.updatePostNumber(account, i);
	}

	@Override
	public Integer updateReply(Integer replyCount, String account) {
		return bbsUserProfilerDao.updateReplyCount(replyCount, account);
	}

	@Override
	public Integer updateBbsReplyCount(String account) {
		Integer i = bbsPostReplyDao.queryReplyByUserCount(account, "1", null);
		return bbsUserProfilerDao.updateReplyCount(i, account);
	}

	@Override
	public Integer countUserProfilerByAccount(String accountName) {

		return bbsUserProfilerDao.countUserProfilerByAccount(accountName);
	}

	@Override
	public BbsUserProfilerDO queryUserByAccount(String accountName) {

		return (BbsUserProfilerDO) bbsUserProfilerDao
				.queryUserByAccount(accountName);
	}

	@Override
	public BbsUserProfilerDO queryUserByCompanyId(Integer companyId) {

		return (BbsUserProfilerDO) bbsUserProfilerDao
				.queryUserByCompanyId(companyId);
	}

	@Override
	public List<BbsUserProfilerDO> queryNewUser(Integer size) {
		if (size == null) {
			size = 6;
		}
		return bbsUserProfilerDao.queryNewUser(size);
	}

	@Override
	public PageDto<BbsUserProfilerDto> pageUserByAdmin(
			BbsUserProfilerDto bbsUserProfilerDto,
			PageDto<BbsUserProfilerDto> page) {

		page.setTotalRecords(bbsUserProfilerDao
				.queryCountByAdmin(bbsUserProfilerDto));
		List<BbsUserProfilerDto> resultList = new ArrayList<BbsUserProfilerDto>();
		List<BbsUserProfilerDO> list = bbsUserProfilerDao.queryByAdmin(bbsUserProfilerDto, page);
		for (BbsUserProfilerDO obj : list) {
			try {
				BbsUserProfilerDto dto = new BbsUserProfilerDto();
				dto.setBbsUserProfiler(obj);
				
				// 回帖 、发贴 数据
				Map<String, Object> out = new HashMap<String, Object>();
				bbsService.countBbsInfo(out, obj.getAccount(),null);
				dto.setTotalPost((Integer) out.get("countPosted"));
				dto.setTotalQA((Integer) out.get("countPostedQA"));
				dto.setTotalReply((Integer) out.get("countReply"));
				dto.setTotalReplyQA((Integer) out.get("countReplyQA"));
				
				// 公司信息获取
				CompanyAccount account = companyAccountDao.queryAccountByAccount(obj.getAccount());
				Company company = companyDAO.queryCompanyById(obj.getCompanyId());
				
				if(account!=null&&StringUtils.isNotEmpty(account.getContact())){
					dto.setContact(account.getContact());
				}
				
				if(company!=null&&StringUtils.isNotEmpty(company.getName())){
					dto.setName(company.getName());
				}
				resultList.add(dto);
			} catch (Exception e) {
				continue;
			}
		}
		page.setRecords(resultList);
		return page;
	}
	
	@Override
	public Integer queryRank(Integer i){
		Integer count = bbsUserProfilerDao.queryRank(i);
		return count+1;
	}
}
