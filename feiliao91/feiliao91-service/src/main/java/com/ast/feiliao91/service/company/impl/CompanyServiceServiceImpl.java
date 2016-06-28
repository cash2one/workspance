package com.ast.feiliao91.service.company.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.feiliao91.domain.company.CompanyService;
import com.ast.feiliao91.persist.company.CompanyServiceDao;
import com.ast.feiliao91.service.company.CompanyServiceService;
import com.zz91.util.lang.StringUtils;
@Component("companyServiceService")
public class CompanyServiceServiceImpl implements CompanyServiceService {
	@Resource
	private CompanyServiceDao companyServiceDao;
	
	@Override
	public Integer insertCompanyService(CompanyService companyService) {
		companyService.setIsDel(0);
		return companyServiceDao.insertCompanyService(companyService);
	}

	@Override
	public CompanyService queryCompanyServiceById(Integer id) {
		return companyServiceDao.queryCompanyServiceById(id);
	}
	
	@Override
	public List<CompanyService> queryCompanyServiceListByCompanyId(Integer companyId,Integer pageSize){
		if (companyId != null && pageSize!=null) {
			if(pageSize>=50){
				pageSize=50;
				return companyServiceDao.queryCompanyServiceListByCompanyId(companyId,pageSize);
			}else if(pageSize>=0 && pageSize<50){
				return companyServiceDao.queryCompanyServiceListByCompanyId(companyId,pageSize);
			}else{
				return null;
			}
		}else{
			return null;
		}
	}

	@Override
	public boolean validateServiceByCode(Integer companyId, String code) {
		if (companyId==null||StringUtils.isEmpty(code)) {
			return false;
		}
		Integer i = companyServiceDao.queryServiceCount(companyId, code);
		if(i!=null&&i>0){
			return true;
		}
		return false;
	}

	@Override
	public Integer createByCode(Integer companyId, String code) {
		if (companyId==null||StringUtils.isEmpty(code)) {
			return 0;
		}
		Integer i = companyServiceDao.queryServiceCount(companyId, code);
		Integer j = 0;
		if (i>0) {
			j = companyServiceDao.updateToOpen(companyId, code); //存在则更新
		}else{
			CompanyService companyService =new CompanyService();
			companyService.setCompanyId(companyId);
			companyService.setServiceCode(code);
			j = insertCompanyService(companyService);  // 不存在则插入
		}
		return j;
	}
	
	@Override
	public Integer closeByCode(Integer companyId, String code){
		if (companyId==null||StringUtils.isEmpty(code)) {
			return 0;
		}
		Integer i = companyServiceDao.updateToClose(companyId, code);
		return i;
	}
}
