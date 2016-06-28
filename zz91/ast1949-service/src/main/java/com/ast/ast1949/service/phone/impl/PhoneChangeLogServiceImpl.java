/**
 * @author kongsj
 * @date 2014年8月26日
 * 
 */
package com.ast.ast1949.service.phone.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.phone.PhoneChangeLog;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.phone.PhoneChangeLogDto;
import com.ast.ast1949.persist.phone.PhoneChangeLogDao;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.phone.PhoneChangeLogService;
import com.ast.ast1949.service.products.ProductsService;

@Component("phoneChangeLogService")
public class PhoneChangeLogServiceImpl implements PhoneChangeLogService{

	@Resource
	private PhoneChangeLogDao phoneChangeLogDao;
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private CompanyService companyService;
	@Resource
	private ProductsService productsService;
	@Override
	public Integer createLog(String changeType, String changeContent,
			Integer companyId, Integer targetId) {
		PhoneChangeLog phoneChangeLog = new PhoneChangeLog();
		phoneChangeLog.setCheckStatus(PhoneChangeLogService.CHECK_WAIT);
		
		phoneChangeLog.setChangeContent(changeContent);
		phoneChangeLog.setChangeType(changeType);
		phoneChangeLog.setCompanyId(companyId);
		phoneChangeLog.setTargetId(targetId);
		return phoneChangeLogDao.insert(phoneChangeLog);
	}

	@Override
	public Integer updateContent(Integer id, String changeContent) {
	
		if(id!=null){
			PhoneChangeLog phoneChangeLog=new PhoneChangeLog();
			phoneChangeLog.setId(id);
			phoneChangeLog.setChangeContent(changeContent);
			Integer i=phoneChangeLogDao.update(phoneChangeLog);
			return i;
		}else{
			return 0;
		}
		
	}
	@Override
	public Integer updateChangeContextByType(Integer id){
		Integer i=0;
		if(id!=null&&id!=0){
			
			PhoneChangeLog phoneChangeLog=phoneChangeLogDao.queryById(id);
			if(phoneChangeLog!=null&&phoneChangeLog.getChangeType()!=null&&phoneChangeLog.getTargetId()!=null){
				if("1".equals(phoneChangeLog.getChangeType())){
					Company company=new Company();
					company.setId(phoneChangeLog.getTargetId());
					company.setIntroduction(phoneChangeLog.getChangeContent());
					i=companyService.updateCompanyByAdminCheck(company);
					
				}
				if("2".equals(phoneChangeLog.getChangeType())){
					Company company=new Company();
					company.setId(phoneChangeLog.getTargetId());
					company.setBusiness(phoneChangeLog.getChangeContent());
					i=companyService.updateCompanyByAdminCheck(company);
					
				}
				if("3".equals(phoneChangeLog.getChangeType())){
					ProductsDO productsDO=new ProductsDO();
					productsDO.setId(phoneChangeLog.getTargetId());
					productsDO.setDetails(phoneChangeLog.getChangeContent());
					i=productsService.updateProductByAdminCheck(productsDO);
					
				}
				
			}	
		}
		return i;
	}
	@Override
	public Integer updateStatus(Integer id, String status) {
		
		Integer i=0;
		 if(id!=null){
			 PhoneChangeLog phoneChangeLog=new PhoneChangeLog();
			 phoneChangeLog.setId(id);
			 phoneChangeLog.setCheckStatus(status);
	        i =phoneChangeLogDao.update(phoneChangeLog);
		}
		 return i;
	}
	
	@Override
	public PageDto<PhoneChangeLogDto> queryAllPhoneChangeLogs(PageDto<PhoneChangeLogDto> page,String checkStatus){
	
		List<PhoneChangeLogDto> list=phoneChangeLogDao.queryAllPhoneChangeLogs(page,checkStatus);
		if(list!=null){
			for(PhoneChangeLogDto dto :list){
				if(dto!=null){
					if(dto.getPhoneChangeLog()!=null){
						if(dto.getPhoneChangeLog().getCompanyId()!=null){
							CompanyAccount companyAccount= companyAccountService.queryAdminAccountByCompanyId(dto.getPhoneChangeLog().getCompanyId());
							//公司名称
							String name=companyService.queryCompanyNameById(dto.getPhoneChangeLog().getCompanyId());
							if(companyAccount!=null){
								dto.setAccount(companyAccount.getAccount());
							}
							    dto.setCompanyName(name);
						}
						
					}
					
				}
			}
			
		}
		page.setRecords(list);
		page.setTotalRecords(phoneChangeLogDao.queryListCount(new PhoneChangeLog(),checkStatus));
		return page;
	}
	@Override
	public PhoneChangeLog queryPhoChangeLogbyid(Integer id){
		return phoneChangeLogDao.queryById(id);
	}
}
