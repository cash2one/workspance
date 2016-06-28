/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-15
 */
package com.ast.ast1949.service.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ast.ast1949.domain.site.MemberRuleDO;
import com.ast.ast1949.util.StringUtils;
import com.zz91.util.cache.MemcachedUtils;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-4-15
 */
public class MemberRuleFacade implements CategoryAccessor{
	
	private static MemberRuleFacade _instance;
	
	private static Map<String, Object> MEM_RULE=new HashMap<String, Object>();
	
	private static String MEM_TYPE = CategoryAccessor.ACCESS_FROM_MEMORY;
	
	public static synchronized MemberRuleFacade getInstance(){
		if(_instance==null){
			_instance=new MemberRuleFacade();
		}
		return _instance;
	}
	
	public void init(List<MemberRuleDO> list, String initTargetType){
		if(list==null){
			return ;
		}
		
		if(StringUtils.isNotEmpty(initTargetType)){
			MEM_TYPE = initTargetType;
		}
		
		for(MemberRuleDO rule: list){
			putObject(rule.getOperationCode()+"@"+rule.getMembershipCode(), rule.getResults());
		}
	}

	public Object holdObject(String key) {
		if(CategoryAccessor.ACCESS_FROM_MEMCACHE.equals(MEM_TYPE)){
			return MemcachedUtils.getInstance().getClient().get(key);
		}
		return MEM_RULE.get(key);
	}

	public final static int EXPIRATION = 0;
	
	public void putObject(String key, Object value) {
		if(CategoryAccessor.ACCESS_FROM_MEMCACHE.equals(MEM_TYPE)){
			MemcachedUtils.getInstance().getClient().set(key, EXPIRATION, value);
			return ;
		}
		MEM_RULE.put(key, value);
	}
	
	public String getValue(String membershipCode, String operation){
		if(operation==null || membershipCode==null){
			return null;
		}
		return (String) holdObject(operation+"@"+membershipCode);
	}

}
