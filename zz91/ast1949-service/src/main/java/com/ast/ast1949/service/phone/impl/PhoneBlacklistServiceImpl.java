package com.ast.ast1949.service.phone.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.phone.PhoneBlacklist;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.phone.PhoneBlacklistDao;
import com.ast.ast1949.service.phone.PhoneBlacklistService;
import com.zz91.util.lang.StringUtils;

@Component("phoneBlacklistService")
public class PhoneBlacklistServiceImpl implements PhoneBlacklistService {

	@Resource
	private PhoneBlacklistDao phoneBlacklistDao;
	
	@Override
	public Integer insert(PhoneBlacklist phoneBlacklist) {
		if (phoneBlacklist==null||StringUtils.isEmpty(phoneBlacklist.getPhone())) {
			return 0;
		}
		PhoneBlacklist search = new PhoneBlacklist();
		search.setPhone(phoneBlacklist.getPhone());
		Integer i = phoneBlacklistDao.queryCount(search);
		if (i>0) {
			return 0;
		}
		
		return phoneBlacklistDao.insert(phoneBlacklist);
	}
	
	@Override
	public Integer batchInsert(String phone) {
		// 处理 电话字段
		String[] phoneArray = null;
		if (phone.indexOf(",")!=-1) {
			phoneArray = phone.split(",");
		}else{
			phoneArray = new String[]{phone};
		}
		
		// 批量插入
		List<PhoneBlacklist> list = new ArrayList<PhoneBlacklist>();
		for (String key:phoneArray) {
			PhoneBlacklist obj = new PhoneBlacklist();
			obj.setPhone(key);
			// 判断是否已经存在该号码
			Integer i = phoneBlacklistDao.queryCount(obj);
			if (i>0) {
				continue;
			}
			list.add(obj);
			obj = new PhoneBlacklist();
			obj.setPhone("0"+key);
			list.add(obj);
		}
		return phoneBlacklistDao.batchInsert(list);
	}

	@Override
	public PageDto<PhoneBlacklist> page(PhoneBlacklist phoneBlacklist,
			PageDto<PhoneBlacklist> page) {
		page.setRecords(phoneBlacklistDao.query(phoneBlacklist, page));
		page.setTotalRecords(phoneBlacklistDao.queryCount(phoneBlacklist));
		return page;
	}

	@Override
	public PhoneBlacklist queryById(Integer id) {
		if (id<=0) {
			return null;
		}
		return phoneBlacklistDao.queryById(id);
	}

	@Override
	public Integer batchDelete(String ids) {
		String[] idArray = null;
		if (ids.indexOf(",")!=-1) {
			idArray = ids.split(",");
		}else{
			idArray = new String[]{ids};
		}
		return phoneBlacklistDao.batchDelete(idArray);
	}

}
