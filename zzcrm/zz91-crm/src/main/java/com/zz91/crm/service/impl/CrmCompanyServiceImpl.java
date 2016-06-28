/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-12-10
 */
package com.zz91.crm.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zz91.crm.dao.CrmCompanyBackupDao;
import com.zz91.crm.dao.CrmCompanyDao;
import com.zz91.crm.dao.CrmSaleCompDao;
import com.zz91.crm.dao.SysAreaDao;
import com.zz91.crm.domain.CrmCompany;
import com.zz91.crm.domain.CrmCompanyBackup;
import com.zz91.crm.dto.CommCompanyDto;
import com.zz91.crm.dto.CompanySearchDto;
import com.zz91.crm.dto.CrmCompanyDto;
import com.zz91.crm.dto.PageDto;
import com.zz91.crm.dto.SaleCompanyDto;
import com.zz91.crm.exception.LogicException;
import com.zz91.crm.service.CrmCompanyService;
import com.zz91.crm.service.ParamService;
import com.zz91.util.Assert;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;

/**
 * @author totly created on 2011-12-10
 */
@Service("crmCompanyService")
public class CrmCompanyServiceImpl implements CrmCompanyService {

	@Resource
	private CrmCompanyDao crmCompanyDao;
	
	@Resource
	private CrmCompanyBackupDao crmCompanyBackupDao;
	@Resource
	private CrmSaleCompDao crmSaleCompDao;
	@Resource
	private SysAreaDao sysAreaDao;

	@Override
	public Integer createCompany(CrmCompany company) {
		Assert.notNull(company, "the company can not be null");
		Assert.notNull(company.getCtype(), "the ctype can not be null");
		Assert.notNull(company.getCname(), "the cname can not be null");
		Assert.notNull(company.getName(), "the name can not be null");
		Assert.notNull(company.getSex(), "the sex can not be null");
		Assert.notNull(company.getEmail(), "the email can not be null");
		Assert.notNull(company.getMobile(), "the mobile can not be null");
		Assert.notNull(company.getPhone(), "the phone not be null");
		company.setRegistStatus(CrmCompanyService.REGIST_STATUS_UNCHECK);
		return crmCompanyDao.createCompany(company);
	}

	@Override
	@Transactional
	public Integer createCompanySys(CrmCompanyBackup companyBackup) throws LogicException{
		CrmCompany company = new CrmCompany();
		company.setCid(companyBackup.getId());
		company.setRegistStatus(CrmCompanyService.REGIST_STATUS_CHECK);
		company.setCname(companyBackup.getCname());
		company.setUid(companyBackup.getUid());
		company.setAccount(companyBackup.getAccount());
		company.setEmail(companyBackup.getEmail());
		company.setName(companyBackup.getName());
		company.setSex(companyBackup.getSex());
		company.setPhoneCountry(companyBackup.getPhoneCountry());
		company.setPhoneArea(companyBackup.getPhoneArea());
		company.setPhone(companyBackup.getPhone());
		company.setFaxCountry(companyBackup.getFaxCountry());
		company.setFaxArea(companyBackup.getFaxArea());
		company.setFax(companyBackup.getFax());
		company.setAddress(companyBackup.getAddress());
		company.setAddressZip(companyBackup.getAddressZip());
		company.setDetails(companyBackup.getDetails());
		company.setIndustryCode(companyBackup.getIndustryCode());
		company.setMemberCode(companyBackup.getMemberCode());
		company.setRegisterCode(companyBackup.getRegisterCode());
		company.setBusinessCode(companyBackup.getBusinessCode());
		company.setProvinceCode(companyBackup.getProvinceCode());
		company.setAreaCode(companyBackup.getAreaCode());
		company.setMainBuy(companyBackup.getMainBuy());
		company.setMainProductBuy(companyBackup.getMainProductBuy());
		company.setMainSupply(companyBackup.getMainSupply());
		company.setMainProductSupply(companyBackup.getMainProductSupply());
		company.setLoginCount(companyBackup.getLoginCount());
		company.setGmtLogin(companyBackup.getGmtLogin());
		company.setGmtRegister(companyBackup.getGmtRegister());
		company.setGmtInput(companyBackup.getGmtInput());
		company.setSaleAccount("system");
		Integer id = crmCompanyDao.queryIdByCid(company.getCid());
		//判断公司是否存在
		if (id != null && id > 0) {
			crmCompanyDao.updateCompany(company);
		} else {
			Integer count1 = crmCompanyDao.createCompany(company);
			Integer count2 = crmCompanyBackupDao.createCompany(companyBackup);
			if (count1 > 0  && count2 >0 ) {
				throw new LogicException("数据没有同时插入!");
			}
		}
		return id;
	}

	@Override
	public List<CrmCompany> queryCommCompany(String cname, String name,
			String email, String mobile, String phone, String fax,Integer limit) {
		if (limit == null) {
			limit = DEFAULT_LIMIT;
		}
		return crmCompanyDao.queryCommCompanyByConditions(cname, name, email, mobile, phone, fax, limit);
	}

	@Override
	public List<SaleCompanyDto> queryCommCompanyByContact(Integer cid, Integer limit) {
		if (limit == null) {
			limit = DEFAULT_LIMIT;
		}
		CrmCompany comp=crmCompanyDao.queryContactById(cid);
		if (StringUtils.isNotEmpty(comp.getMobile()) || StringUtils.isNotEmpty(comp.getPhone())){
			return crmCompanyDao.queryCommCompanyByContact(comp.getMobile(), comp.getPhone(),cid, limit);
		}
		return null;
	}

	@Override
	public CrmCompany queryCompanyById(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return crmCompanyDao.queryCompanyById(id,null);
	}
	
	@Override
	public void timeHandle(CompanySearchDto searchdto){
		if(StringUtils.isNotEmpty(searchdto.getGmtNextContactStart())){
			searchdto.setGmtNextContactStart(searchdto.getGmtNextContactStart().substring(0, 10));
		}
		if (StringUtils.isNotEmpty(searchdto.getGmtNextContactEnd())){
			searchdto.setGmtNextContactEnd(searchdto.getGmtNextContactEnd().substring(0, 10));
		}
		if(StringUtils.isNotEmpty(searchdto.getGmtRegisterStart())){
			searchdto.setGmtRegisterStart(searchdto.getGmtRegisterStart().substring(0, 10));
		}
		if (StringUtils.isNotEmpty(searchdto.getGmtRegisterEnd())){
			searchdto.setGmtRegisterEnd(searchdto.getGmtRegisterEnd().substring(0, 10));
		}
		
		if(StringUtils.isNotEmpty(searchdto.getGmtLastContactStart())){
			searchdto.setGmtLastContactStart(searchdto.getGmtLastContactStart().substring(0, 10));
		}
		if (StringUtils.isNotEmpty(searchdto.getGmtLastContactEnd())){
			searchdto.setGmtLastContactEnd(searchdto.getGmtLastContactEnd().substring(0, 10));
		}
		if(StringUtils.isNotEmpty(searchdto.getGmtLoginStart())){
			searchdto.setGmtLoginStart(searchdto.getGmtLoginStart().substring(0, 10));
		}
		if (StringUtils.isNotEmpty(searchdto.getGmtLoginEnd())){
			searchdto.setGmtLoginEnd(searchdto.getGmtLoginEnd().substring(0, 10));
		}
	}

	@Override
	public void sortByField(PageDto<SaleCompanyDto> page,PageDto<CommCompanyDto> page2,String sr,Integer sortMode){
		if (StringUtils.isNotEmpty(sr) && sortMode!=null){
			if (page!=null && page2==null){
				if(sr.equals("gmt_register")){
					page.setSort("cc.gmt_register");
				}
				if(sr.equals("gmt_login")){
					page.setSort("cc.gmt_login");
				}
				if(sr.equals("gmt_contact")){
					page.setSort("csc.gmt_contact");
				}
				if(sr.equals("gmt_next_contact")){
					page.setSort("csc.gmt_next_contact");
				}
				if(sr.equals("star")){
					page.setSort("cc.star");
				}
				if(sr.equals("login_count")){
					page.setSort("cc.login_count");
				}
				if(sr.equals("contact_able_count")){
					page.setSort("csc.contact_able_count");
				}
				if(sortMode==1){
					page.setDir("desc");
				}
				if(sortMode==0){
					page.setDir("asc");
				}
			}
			if (page2!=null && page==null){
				if(sr.equals("gmt_register")){
					page2.setSort("cc.gmt_register");
				}
				if(sr.equals("gmt_login")){
					page2.setSort("cc.gmt_login");
				}
				if(sr.equals("gmt_contact")){
					page2.setSort("csc.gmt_contact");
				}
				if(sr.equals("gmt_next_contact")){
					page2.setSort("csc.gmt_next_contact");
				}
				if(sr.equals("star")){
					page2.setSort("cc.star");
				}
				if(sr.equals("login_count")){
					page2.setSort("cc.login_count");
				}
				if(sr.equals("contact_able_count")){
					page2.setSort("csc.contact_able_count");
				}
				if(sortMode==1){
					page2.setDir("desc");
				}
				if(sortMode==0){
					page2.setDir("asc");
				}
			}
		}
	}
	
	@Override
	public PageDto<CommCompanyDto> searchCommCompany(
			CompanySearchDto search, PageDto<CommCompanyDto> page) {
		search.setRegistStatus(REGIST_STATUS_CHECK);
		List<CommCompanyDto> dtoList=crmCompanyDao.queryCommCompany(search,page);
		for (CommCompanyDto dto : dtoList) {
			try {
				Integer day=DateUtil.getIntervalDays(new Date(), dto.getGmtLogin());
				dto.setColor(day.shortValue());
			} catch (ParseException e) {
			}
			if (StringUtils.isNotEmpty(dto.getProvinceCode()) && dto.getProvinceCode()!=""){
				dto.setProvinceName(sysAreaDao.queryNameByCode(dto.getProvinceCode()));
			}
			if (StringUtils.isNotEmpty(dto.getAreaCode()) && dto.getAreaCode()!=""){
				dto.setAreaName(sysAreaDao.queryNameByCode(dto.getAreaCode()));
			}
		}
		page.setRecords(dtoList);
		page.setTotals(crmCompanyDao.queryCommCompanyCount(search));
		return page;
	}

	@Override
	public PageDto<SaleCompanyDto> searchMyCompany(
			CompanySearchDto search, PageDto<SaleCompanyDto> page) {
		Assert.notNull(search, "the search can not be null");
		
		List<SaleCompanyDto> list=crmCompanyDao.queryMyCompany(search, page);
		for (SaleCompanyDto dto : list) {
			if (dto.getContactCount()==0){
				Integer d1;
				try {
					d1 = DateUtil.getIntervalDays(new Date(),dto.getGmtCreated());
					dto.setDay(3-d1);
				} catch (ParseException e) {
				}
			}
			if (dto.getContactCount()>0){
				Integer d2;
				try {
					d2=DateUtil.getIntervalDays(new Date(), dto.getGmtContact()==null?dto.getGmtCreated():dto.getGmtContact());
					dto.setDay(30-d2);
				} catch (ParseException e) {
				}
			}
			try {
				Integer day=DateUtil.getIntervalDays(new Date(), dto.getGmtLogin());
				dto.setColor(day.shortValue());
			} catch (ParseException e) {
			}
			if (StringUtils.isNotEmpty(dto.getProvinceCode()) && dto.getProvinceCode()!=""){
				dto.setProvinceName(sysAreaDao.queryNameByCode(dto.getProvinceCode()));
			}
			if (StringUtils.isNotEmpty(dto.getAreaCode()) && dto.getAreaCode()!=""){
				dto.setAreaName(sysAreaDao.queryNameByCode(dto.getAreaCode()));
			}
		}
		page.setRecords(list);
		page.setTotals(crmCompanyDao.queryMyCompanyCount(search));
		return page;
	}

	@Override
	public Integer updateCompany(CrmCompany crmCompany) {
		Assert.notNull(crmCompany, "the crmCompany can not be null");
		Assert.notNull(crmCompany.getId(), "the id can not be null");
		return crmCompanyDao.updateCompany(crmCompany);
	}

	@Override
	public Integer updateRegistStatus(Integer id, Short status,String memberCode) {
		Assert.notNull(id, "the id can not be null");
		Assert.notNull(status, "the status can not be null");
		return crmCompanyDao.updateRegistStatus(id, status,memberCode);
	}

	@Override
	@Transactional
	public Integer updateRepeatIds(Integer[] ids, Integer newId) {
		Integer result = 0;
		for (Integer id:ids) {
			result += crmCompanyDao.updateRepeatId(id, newId);
		}
		return result;
	}

	@Override
	public boolean queryMobileAndEmail(String mobile, String email) {
		Integer i=crmCompanyDao.queryCountByMobileAndEmail(mobile, email);
		if (i!=null && i.intValue()>0){
			return true;
		}
		return false;
	}

	@Override
	public Integer queryCountById(Integer id, Short ctype) {
		return crmCompanyDao.queryCountById(id,ctype);
	}

	@Override
	public CrmCompany queryCrmCompById(Integer id) {
		return crmCompanyDao.queryCompById(id);
	}

	@Override
	public Integer updateCtypeById(Integer id, Short newType) {
		return crmCompanyDao.updateCtypeById(id, newType);
	}

	@Override
	public Integer updateStarByCid(Integer id, Short star,Short source,Short maturity,Short promote,
			String saleAccount,String saleName,Short kp,Short callType) {
		return crmCompanyDao.updateStarByCid(id,star,source,maturity,promote,saleAccount,saleName,kp,callType);
	}
	
	@Override
	public CrmCompany queryStarById(Integer id) {
		return crmCompanyDao.queryStarById(id);
	}

	@Override
	@Transactional
	public Integer updateSaleStatusById(Integer id, Short saleStatus) throws LogicException {
		Integer count1=crmCompanyDao.updateSaleStatusById(id,saleStatus);
		if (saleStatus==1){
			Integer count2=crmSaleCompDao.updateStatus(id, null);
			if (count1==0 || count2==0){
				throw new LogicException("同时更新或操作失败!");
			}
		}
		return count1;
	}

	@Override
	public PageDto<CommCompanyDto> searchOnceFourOrFive(
			CompanySearchDto search, PageDto<CommCompanyDto> page) {
		
		search.setRepeatId(null);
		search.setSaleStatus(null);
		
		List<CommCompanyDto> list=crmCompanyDao.queryOnceFourOrFive(search, page);
		for (CommCompanyDto dto : list) {
			try {
				Integer day=DateUtil.getIntervalDays(new Date(), dto.getGmtLogin());
				dto.setColor(day.shortValue());
			} catch (ParseException e) {
			}
			if (StringUtils.isNotEmpty(dto.getProvinceCode()) && dto.getProvinceCode()!=""){
				dto.setProvinceName(sysAreaDao.queryNameByCode(dto.getProvinceCode()));
			}
			if (StringUtils.isNotEmpty(dto.getAreaCode()) && dto.getAreaCode()!=""){
				dto.setAreaName(sysAreaDao.queryNameByCode(dto.getAreaCode()));
			}
		}
		page.setRecords(list);
		page.setTotals(crmCompanyDao.queryOnceFourOrFiveCount(search));
		return page;
	}

	@Override
	public List<CrmCompanyDto> querySimplyComp() {
		return crmCompanyDao.querySimplyComp();
	}

	@Override
	public PageDto<SaleCompanyDto> pageComp(PageDto<SaleCompanyDto> page,
			CrmCompany company) {
		List<SaleCompanyDto> compList=crmCompanyDao.queryBaseComp(page,company);
		for (SaleCompanyDto comp : compList) {
			if (StringUtils.isNotEmpty(comp.getProvinceCode()) && comp.getProvinceCode()!=""){
				comp.setProvinceName(sysAreaDao.queryNameByCode(comp.getProvinceCode()));
			}
			if (StringUtils.isNotEmpty(comp.getAreaCode()) && comp.getAreaCode()!=""){
				comp.setAreaName(sysAreaDao.queryNameByCode(comp.getAreaCode()));
			}
		}
		page.setRecords(compList);
		page.setTotals(crmCompanyDao.queryBaseCompCount(company));
		return page;
	}

}