package com.ast.ast1949.service.sample.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.sample.Identity;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.company.CompanyAccountDao;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.sample.IdentityDAO;
import com.ast.ast1949.service.sample.IdentityService;
import com.zz91.util.lang.StringUtils;

@Component("identityService")
public class IdentityServiceImpl implements IdentityService {

	@Resource
	private IdentityDAO identityDao;
	@Resource
	private CompanyDAO companyDAO;
	@Resource
	private CompanyAccountDao companyAccountDao;

	@Override
	public Integer insert(Identity record) {
		if (StringUtils.isEmpty(record.getState())) {
			record.setState(IdentityService.STATUS_CHECKING);
		}
		return identityDao.insert(record);
	}

	@Override
	public int updateByPrimaryKey(Identity record) {
		return identityDao.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(Identity record) {
		return identityDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public Identity selectByPrimaryKey(Integer id) {
		return identityDao.selectByPrimaryKey(id);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return identityDao.deleteByPrimaryKey(id);
	}

	@Override
	public Identity queryIdentityByCompanyId(Integer companyId) {
		return identityDao.queryIdentityByCompanyId(companyId);
	}

	@Override
	public void updateFrontByCompanyId(Integer companyId, String str) {
		identityDao.updateFrontByCompanyId(companyId, str);
	}

	@Override
	public void updateBackByCompanyId(Integer companyId, String str) {
		identityDao.updateBackByCompanyId(companyId, str);
	}

	@Override
	public PageDto<Identity> queryListByFilter(PageDto<Identity> page, Map<String, Object> filterMap) {
		if(page.getSort()==null){
			page.setSort("id");
		}
		
		filterMap.put("page", page);
		page.setTotalRecords(identityDao.queryListByFilterCount(filterMap));
		List<Identity> list = identityDao.queryListByFilter(filterMap);
		for (Identity obj:list) {
			Integer companyId = obj.getCompanyId();
			CompanyAccount ca = companyAccountDao.queryAccountByCompanyId(companyId);
			Company c = companyDAO.queryCompanyById(companyId);
			if (ca!=null&&StringUtils.isNotEmpty(ca.getAccount())) {
				obj.setAccount(ca.getAccount());
			}
			if (c!=null && StringUtils.isNotEmpty(c.getName())) {
				obj.setCompanyName(c.getName());
			}
		}
		page.setRecords(list);
		return page;
	}
	
}
