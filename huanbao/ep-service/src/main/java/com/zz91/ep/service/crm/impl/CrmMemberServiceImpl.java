/**
 * 
 */
package com.zz91.ep.service.crm.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.dao.crm.CrmMemberDao;
import com.zz91.ep.service.crm.CrmMemberService;

/**
 * @author mays
 *
 */
@Component("crmMemberService")
public class CrmMemberServiceImpl implements CrmMemberService {

	static final Map<String, String> MEMBER_CACHE = new HashMap<String, String>();
	
	@Resource
	private CrmMemberDao crmMemberDao;

	@Override
	public String queryName(String code) {
		// the cache should be expired
		String name=MEMBER_CACHE.get(code);
		if(name==null){
			name=crmMemberDao.queryName(code);
			MEMBER_CACHE.put(code, name);
		}
		return name;
	}

}
