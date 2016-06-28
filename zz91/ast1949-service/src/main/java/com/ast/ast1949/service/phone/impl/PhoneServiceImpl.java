package com.ast.ast1949.service.phone.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.phone.Phone;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.phone.PhoneDao;
import com.ast.ast1949.service.phone.PhoneService;
import com.zz91.util.lang.StringUtils;

/**
 * author:kongsj date:2013-7-3
 */
@Component("phoneService")
public class PhoneServiceImpl implements PhoneService {

	@Resource
	private PhoneDao phoneDao;

	@Override
	public Integer insert(Phone phone) {
		do {
			if(phone.getCompanyId()==null&&StringUtils.isEmpty(phone.getAccount())){
				break;
			}
			if (StringUtils.isEmpty(phone.getAccount())) {
				break;
			}
			Integer i = phoneDao.countByAccount(phone.getAccount());
			if (i > 0) {
				break;
			}
			if (phone.getCompanyId() == null) {
				break;
			}
			i = phoneDao.countByCompanyId(phone.getCompanyId());
			if (i > 0) {
				break;
			}
			// 新增一个400 号码
			return phoneDao.insert(phone);
		} while (false);
		return 0;
	}

	@Override
	public PageDto<Phone> pageList(Phone phone, PageDto<Phone> page) {
		if (StringUtils.isEmpty(page.getSort())) {
			page.setSort("id");
		}
		page.setTotalRecords(phoneDao.queryListCount(phone));
		page.setRecords(phoneDao.queryList(phone, page));
		return page;
	}

	@Override
	public Phone queryById(Integer id) {
		if (id == null) {
			return null;
		}
		return phoneDao.queryById(id);
	}

	@Override
	public Integer update(Phone phone) {
		if (phone.getId() == null) {
			return 0;
		}
		return phoneDao.update(phone);
	}

	@Override
	public Integer delete(Integer id) {
		if(id==null){
			return 0;
		}
		return phoneDao.deleteById(id);
	}

	@Override
	public Phone queryByCompanyId(Integer companyId) {
		if(companyId==null){
			return null;
		}
		return phoneDao.queryByCompanyId(companyId);
	}

	@Override
	public Phone queryByTel(String tel) {
		if(StringUtils.isEmpty(tel)){
			return null;
		}
		return phoneDao.queryByTel(tel);
	}

}
