package com.ast.ast1949.service.facade;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ast.ast1949.domain.products.CategoryProductsDO;
import com.ast.ast1949.util.StringUtils;
import com.zz91.util.cache.MemcachedUtils;

public class CategoryProductsFacade implements CategoryAccessor {
	
	private static CategoryProductsFacade _instance = new CategoryProductsFacade();
	private static Map<String, Object> MEM_CATEGORY = new LinkedHashMap<String, Object>();
	
	private static String MEM_TYPE = CategoryAccessor.ACCESS_FROM_MEMORY;
	
	public final static String KEY_PREFIX = "cpc@";
	public final static String KEY_ASSIST_PREFIX = "cpac@";
	public final static String KEY_LIST = "@list";
	
	private CategoryProductsFacade() {
		
	}

	public static CategoryProductsFacade getInstance() {
		return _instance;
	}

	//初始化供求类别，并将类别信息
	public void init(List<CategoryProductsDO> list, String initTargetType){
		if(list==null){
			return ;
		}
		
		if(StringUtils.isNotEmpty(initTargetType)){
			MEM_TYPE = initTargetType;
		}
		
		for(CategoryProductsDO category:list){
			if("1".equals(category.getIsAssist())){
				//辅助类别
				buildupObject(KEY_ASSIST_PREFIX, category.getCode(), category.getLabel());
			}else{
				//主类别
				buildupObject(KEY_PREFIX, category.getCode(), category.getLabel());
			}
		}
	}
	
	final static int CODE_LENGTH = 4;
	@SuppressWarnings("unchecked")
	private void buildupObject(String prefix, String code, String label){
		if(StringUtils.isEmpty(code)){
			return ;
		}
		label = StringUtils.getNotNullValue(label);
		putObject(prefix+code, label);
		
		String mapkey=prefix+KEY_LIST;
		if(code.length()>CODE_LENGTH){
			mapkey=prefix+code.substring(0, code.length()-CODE_LENGTH)+KEY_LIST;
		}
		
		Map<String, String> map=(Map<String, String>) holdObject(mapkey);
		if(map==null ){
			map = new LinkedHashMap<String, String>();
		}
		map.put(code, label);
		putObject(mapkey, map);
	}
	
	
	
	/**
	 * 根据父类编号获取子类编号，当parentCode为null或者""时，获取根节点类别
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getChild(String parentCode){
		String mapkey=KEY_PREFIX+KEY_LIST;
		if(StringUtils.isNotEmpty(parentCode)){
			mapkey=KEY_PREFIX+parentCode+KEY_LIST;
		}
		return (Map<String, String>) holdObject(mapkey);
	}
	
	/**
	 * 根据类别code获取类别名称
	 */
	public String getValue(String code){
		if(code==null){
			return null;
		}
		return (String) holdObject(KEY_PREFIX+code);
	}
	
	/**
	 * 根据辅助类别code获取类别名称
	 */
	public String getAssistValue(String code){
		if(code==null){
			return null;
		}
		return (String) holdObject(KEY_ASSIST_PREFIX+code);
	}
	
	/**
	 * 根据主类别获取辅助类别
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getAssistChild(String mainCode){
		String mapkey=KEY_ASSIST_PREFIX+KEY_LIST;
		if(StringUtils.isNotEmpty(mainCode)){
			mapkey=KEY_ASSIST_PREFIX+mainCode+KEY_LIST;
		}
		return (Map<String, String>) holdObject(mapkey);
	}
	
	public Object holdObject(String key) {
		if(CategoryAccessor.ACCESS_FROM_MEMCACHE.equals(MEM_TYPE)){
			return MemcachedUtils.getInstance().getClient().get(key);
		}
		return MEM_CATEGORY.get(key);
	}

	public final static int EXPIRATION = 0;
	
	public void putObject(String key, Object value) {
		if(CategoryAccessor.ACCESS_FROM_MEMCACHE.equals(MEM_TYPE)){
			MemcachedUtils.getInstance().getClient().set(key, EXPIRATION, value);
			return ;
		}
		MEM_CATEGORY.put(key, value);
	}
	
}
