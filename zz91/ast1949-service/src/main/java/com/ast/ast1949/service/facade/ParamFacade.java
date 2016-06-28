package com.ast.ast1949.service.facade;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.domain.Param;

public class ParamFacade {

	private static ParamFacade _instance		= null;
	public final static String CACHE_KEY		= "param_cache";
	public final static int EXPIRATION			= 0;

	public static synchronized ParamFacade getInstance2(){
		if(_instance==null){
			_instance = new ParamFacade();
		}
		return _instance;
	}
	
	/**
	 * 获取所有参数类型列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Set<String> getParamTypes(){
		Map<String, Map<String, Param>> map=(Map<String, Map<String, Param>>)
		MemcachedUtils.getInstance().getClient().get(CACHE_KEY);
		return map.keySet();
	}

	/**
	 * 通过参数类型获取该类型下的所有参数信息
	 * @param type:参数类型
	 * @return
	 */
	public Map<String, String> getParamByType(String type){
		if(type==null){
			return new HashMap<String, String>();
		}
		Map<String, Param> m=getParamObjectMapByType(type);
		if(m==null){
			return new HashMap<String, String>();
		}
		Map<String, String> mymap = new HashMap<String, String>();
		for(String k:m.keySet()){
			mymap.put(k, m.get(k).getValue());
		}
		return mymap;

	}

	/**
	 * 根据类型(type)的健(key)获取值(value)
	 * @param type:参数类型
	 * @param key:健
	 * @return 健对应的值
	 */
	public String getParamValue(String type, String key){
		Map<String, String> map=getParamByType(type);
		return map.get(key);
	}

	/**
	 * @param type:参数类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Param> getParamObjectMapByType(String type){
		Map<String, Map<String, Param>> map=(Map<String, Map<String, Param>>)
		MemcachedUtils.getInstance().getClient().get(CACHE_KEY);
		return map.get(type);
	}

}
