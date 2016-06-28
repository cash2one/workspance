package com.zz91.crm.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zz91.crm.dao.BaseDao;
import com.zz91.crm.dao.CrmCompanyDao;
import com.zz91.crm.dao.CrmLogDao;
import com.zz91.crm.dao.CrmSaleCompDao;
import com.zz91.crm.domain.CrmCompany;
import com.zz91.crm.domain.CrmLog;
import com.zz91.crm.dto.CrmLogDto;
import com.zz91.crm.dto.PageDto;
import com.zz91.crm.exception.LogicException;
import com.zz91.crm.service.CrmLogService;
import com.zz91.util.Assert;
import com.zz91.util.datetime.DateUtil;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2011-12-13 
 */
@Component("crmLogService")
public class CrmLogServiceImpl extends BaseDao implements CrmLogService {
	
	@Resource
	private CrmLogDao crmLogDao;
	@Resource
	private CrmSaleCompDao crmSaleCompDao;
	@Resource
	private CrmCompanyDao crmCompanyDao;
	
	@Override
	@Transactional
	public Integer createCrmLog(CrmLog crmLog) throws LogicException{
		Assert.notNull(crmLog.getCallType(), "the call_type can not be null");
		Assert.notNull(crmLog.getSaleDept(), "the sale_dept can not be null");
		Assert.notNull(crmLog.getSaleAccount(), "the sale_account can not be null");
		Assert.notNull(crmLog.getCid(), "the cid can not be null");
		Assert.notNull(crmLog.getStar(), "the stat can not be null");
		Assert.notNull(crmLog.getNextContact(), "the next_contact can not be null");
		Assert.notNull(crmLog.getSituation(), "the situation can not be null");
		if (crmLog.getFlag() != null) {
			crmSaleCompDao.updateOrderCountById(crmLog.getCid(),crmLog.getFlag());
		}
		if(crmLog.getSituation().equals(CrmLogDao.SITUATION_DISABLE_3)){
			crmCompanyDao.updateDisableContactById(crmLog.getCid(), CrmSaleCompDao.TYPE_CONTACT_DISABLE);
		}
		Integer count1 = crmLogDao.createCrmLog(crmLog);
		Integer count2;
		try {
			Date gmtNextContact=DateUtil.getDate(crmLog.getGmtNextContactStr(), "yyyy-MM-dd");
			count2 = crmSaleCompDao.updateContactCount(crmLog.getCid(),crmLog.getSituation(),count1,gmtNextContact,crmLog.getSaleAccount(),crmLog.getSaleDept());
			if (count1==0){
				throw new LogicException("创建小计发生错误!");
			}
			if (count2==0){
				throw new LogicException("更新关系表信息发生错误!");
			}
			if(count1 == 0 && count2 == 0) {
				throw new LogicException("没有同时插入和更新!");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return count1;
	}

	@Override
	public List<CrmLog> queryCrmLogByCid(Integer cid,Short callType) {
		Assert.notNull(cid, "the cid can not be null");
		Assert.notNull(callType, "the callType can not be null");
		return crmLogDao.queryCrmLogByCid(cid,callType);
	}

	@Override
	public List<CrmLog> querySaleAccountByDeptCode(String deptCode) {
		return crmLogDao.queryAccountByDeptCode(deptCode);
	}
	
	@Override
	public Integer querytomContactCount(String account) {
		return crmLogDao.querytomContactCount(account);
	}

	@Override
	public PageDto<CrmLogDto> pageCrmLogByToday(String account, String tdate,PageDto<CrmLogDto> page,
			Short disable,Short star,Short type) {
		List<CrmLogDto> list=crmLogDao.queryCrmLogByToday(account, tdate, page,disable,star,type);
		for (CrmLogDto dto : list) {
			dto.setCname(crmCompanyDao.queryCnameById(dto.getCid()));
		}
		page.setRecords(list);
		page.setTotals(crmLogDao.queryCrmLogCountByToday(account, tdate,disable,star,type));
		return page;
	}

	@Override
	public List<CrmLogDto> queryTurnStarCompByStar(Short star, String tDate,
			String account) {
		Assert.notNull(star, "the star can not be null");
		Assert.notNull(tDate, "the tDate can not be null");
		Assert.notNull(account, "the account can not be null");
		List<CrmLog> logList = crmLogDao.queryTurnStarCompByStar(star,tDate,account);
		List<CrmLogDto> dtoList = new ArrayList<CrmLogDto>();
		CrmLogDto dto =null;
		for (CrmLog crmLog : logList) {
			CrmCompany crmCompany =crmCompanyDao.querySimplyCompById(crmLog.getCid());
			dto = new CrmLogDto();
			dto.setCompId(crmCompany.getId());
			dto.setCname(crmCompany.getCname());
			dto.setEmail(crmCompany.getEmail());
			dto.setStar(crmCompany.getStar());
			dto.setGmtLogin(crmCompany.getGmtLogin());
			dto.setLoginCount(crmCompany.getLoginCount());
			dto.setGmtRegister(crmCompany.getGmtRegister());
			dto.setId(crmLog.getId());
			dto.setCid(crmLog.getCid());
			dto.setGmtNextContact(crmLog.getGmtNextContact());
			dto.setGmtCreated(crmLog.getGmtCreated());
			dto.setSaleName(crmLog.getSaleName());
			dtoList.add(dto);
		}
		return dtoList;
	}
}
