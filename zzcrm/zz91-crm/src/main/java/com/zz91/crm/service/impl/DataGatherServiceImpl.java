/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-12-21 下午03:53:49
 */
package com.zz91.crm.service.impl;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.zz91.crm.dao.CrmCompanyDao;
import com.zz91.crm.dao.SysAreaDao;
import com.zz91.crm.domain.CrmCompany;
import com.zz91.crm.dto.data.CrmCompanyMakeMap;
import com.zz91.crm.service.DataGatherService;
import com.zz91.util.lang.StringUtils;

@Component("dataGatherService")
public class DataGatherServiceImpl implements DataGatherService {
	
	@Resource
	private SysAreaDao sysAreaDao;
	@Resource
	private CrmCompanyDao crmCompanyDao;

	@Override
	public boolean createCrmCompany(CrmCompanyMakeMap comp, HSSFRow row,String account) {
		do {
			CrmCompany crmCompany = new CrmCompany();

			if (comp.getCname() != null) {
				if(row.getCell(comp.getCname()) != null){
					String cname = row.getCell(comp.getCname())
					.getRichStringCellValue().getString();
					crmCompany.setCname(cname);
				}
			}

			if (comp.getName() != null) {
				if (row.getCell(comp.getName()) != null) {
					String name = row.getCell(comp.getName())
							.getRichStringCellValue().getString();
					crmCompany.setName(name);
				}
			} else {
				crmCompany.setName("");
			}
			
			if (comp.getSex() != null) {
				if (row.getCell(comp.getSex())!=null){
					
					String sex =row.getCell(comp.getSex())
					.getRichStringCellValue().getString();
					
					if (sex.equals("男")){
						crmCompany.setSex((short)0);
					}else if (sex.equals("女")) {
						crmCompany.setSex((short)1);
					}
				}
			} else {
				crmCompany.setSex((short)0);
			}
			
			if(comp.getIndustryCode()!=null){
				crmCompany.setIndustryCode(comp.getIndustryCode());
			}
			
			if (comp.getBussinessCode()!=null){
				crmCompany.setBusinessCode(comp.getBussinessCode());
			}

			if (comp.getMobile()!=null){
				if (row.getCell(comp.getMobile()) != null) {
					String mobile=null;
					try {
						Double d=row.getCell(comp.getMobile()).getNumericCellValue();
						NumberFormat format = new DecimalFormat("#");
						mobile=format.format(d);
					} catch (Exception e) {
						mobile = row.getCell(comp.getMobile())
						.getRichStringCellValue().getString();
					}
					crmCompany.setMobile(mobile);
				}
			}
			
			if (comp.getPhone()!=null){
				if (row.getCell(comp.getPhone()) != null) {
					String phone=null;
					try {
						Double d=row.getCell(comp.getPhone()).getNumericCellValue();
						NumberFormat format = new DecimalFormat("#");
						phone=format.format(d);
					} catch (Exception e) {
						phone = row.getCell(comp.getPhone())
						.getRichStringCellValue().getString();
					}
					crmCompany.setPhone(phone);
				}
			}
			
			//婚妈fax存放qq
			if (comp.getFax()!=null){
				if (row.getCell(comp.getFax()) != null) {
					String qq=null;
					try {
						Double d=row.getCell(comp.getFax()).getNumericCellValue();
						NumberFormat format = new DecimalFormat("#");
						qq=format.format(d);
					} catch (Exception e) {
						qq = row.getCell(comp.getFax())
						.getRichStringCellValue().getString();
					}
					crmCompany.setFax(qq);
				}
			}
			
			if (comp.getProvinceName()!=null){
				if (row.getCell(comp.getProvinceName()) != null) {
					String	provinceName=null;
					try {
						Double d=row.getCell(comp.getProvinceName()).getNumericCellValue();
						NumberFormat format = new DecimalFormat("#");
						provinceName=format.format(d);
					} catch (Exception e) {
						provinceName = row.getCell(comp.getProvinceName())
						.getRichStringCellValue().getString();
					}
					String code = sysAreaDao.queryCodeByName(provinceName);
					if(StringUtils.isNotEmpty(code)){
						crmCompany.setProvinceCode(code);
					}else {
						crmCompany.setProvinceCode("");
					}
				}
			}else {
				crmCompany.setProvinceCode("");
			}
				
			if (comp.getAreaName()!=null){
				if (row.getCell(comp.getAreaName())!=null) {
					String	areaName=null;
					try {
						Double d=row.getCell(comp.getAreaName()).getNumericCellValue();
						NumberFormat format = new DecimalFormat("#");
						areaName=format.format(d);
					} catch (Exception e) {
						areaName = row.getCell(comp.getAreaName())
						.getRichStringCellValue().getString();
					}
					String code = sysAreaDao.queryCodeByName(areaName);
					
					if(StringUtils.isNotEmpty(code)){
						crmCompany.setAreaCode("");
					}else {
						crmCompany.setAreaCode("");
					}
				}
			}else {
				crmCompany.setAreaCode("");
			}
			
			if (comp.getAddress()!=null){
				if (row.getCell(comp.getAddress()) != null) {
					String address = row.getCell(comp.getAddress())
							.getRichStringCellValue().getString();
					crmCompany.setAddress(address);
				}
			}
			
			if (comp.getDetails()!=null){
				if (row.getCell(comp.getDetails()) != null) {
					String details = row.getCell(comp.getDetails())
							.getRichStringCellValue().getString();
					details = Jsoup.clean(details, Whitelist.none());
					crmCompany.setDetails(details);
				}
			}
			
			if (comp.getUseType()!=null){
				if (row.getCell(comp.getUseType()) != null) {
					String useType = row.getCell(comp.getUseType())
							.getRichStringCellValue().getString();
					useType = Jsoup.clean(useType, Whitelist.none());
					crmCompany.setUseType(useType);
				}
			}
			
			if (comp.getMemberCode()!=null){
				if(comp.getMemberCode().equals("10011001")){
					crmCompany.setCtype((short)3);
				}else{
					crmCompany.setCtype((short)1);
				}
				crmCompany.setMemberCode(comp.getMemberCode());
			}
			
			
			if (crmCompany == null) {
				break;
			}
			
			crmCompany.setRegistStatus((short)1);
			crmCompany.setEmail("");
			crmCompany.setRegisterCode((short)0);
			crmCompany.setMainBuy((short)0);
			crmCompany.setMainSupply((short)0);
			crmCompany.setMainProductBuy("");
			crmCompany.setMainProductSupply("");
			crmCompany.setSaleAccount("");
			crmCompany.setSaleAccount("");
			crmCompany.setSaleName("");
			
			//新加字段 表示录入者
			crmCompany.setInputAccount(account);
			
			Integer id = null;
			try {
				id = crmCompanyDao.createCompany(crmCompany);
			} catch (DataAccessException e) {
			}

			if (id == null || id.intValue() <= 0) {
				break;
			}
			return true;
		} while (false);
		return false;
	}
}
