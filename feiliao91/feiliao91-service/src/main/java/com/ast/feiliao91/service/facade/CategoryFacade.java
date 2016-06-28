/**
 * @author shiqp
 * @date 2016-01-16
 */
package com.ast.feiliao91.service.facade;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ast.feiliao91.domain.common.Category;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.lang.StringUtils;

public class CategoryFacade implements CategoryAccessor{

	private static CategoryFacade _instance = new CategoryFacade();
	public final static String CACHE_KEY = "category_cache";
	public final static int EXPIRATION = 0;
	private static Map<String, Object> CATEGORY_MAP = new LinkedHashMap<String, Object>();
	private static String MEM_TYPE = CategoryAccessor.ACCESS_FROM_MEMORY;
	public static Map<String,Object> PRICE_LEVEL=new LinkedHashMap<String,Object>();

	private CategoryFacade() {
	}

	public static CategoryFacade getInstance() {
		return _instance;
	}
	
	public void putObject(String key, Object value) {
		if (CategoryAccessor.ACCESS_FROM_MEMCACHE.equals(MEM_TYPE)) {
			MemcachedUtils.getInstance().getClient().set(key, CategoryFacade.EXPIRATION,
					value);
		} else {
			CATEGORY_MAP.put(key, value);
		}
	}

	public Object holdObject(String key) {
		if (CategoryAccessor.ACCESS_FROM_MEMCACHE.equals(MEM_TYPE)) {
			return MemcachedUtils.getInstance().getClient().get(key);
		} else {
			return CATEGORY_MAP.get(key);
		}
	}

	@SuppressWarnings("unchecked")
	public void init(List<Category> list,String initTargetType) {
		if(StringUtils.isNotEmpty(initTargetType)){
			MEM_TYPE = initTargetType;
		}
		if (list==null||list.size()==0){
			return ;
		}

		for (Category category : list) {
			putObject("category@"+category.getCode(), category.getLabel());
			String mapkey="category@lists@";
			if(category.getCode().length()>4){
				mapkey=mapkey+category.getCode().substring(0, category.getCode().length()-4);
			}
			Map<String, String> map=(Map<String, String>) holdObject(mapkey);
			if(map==null ){
				map = new LinkedHashMap<String, String>();
			}
			map.put(category.getCode(), category.getLabel());
			putObject(mapkey, map);
		}
	}

	public String getValue(String code) {
		if(code==null){
			return null;
		}
		return (String) _instance.holdObject("category@" + code);
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getChild(String code) {
		if(code==null){
			return null;
		}
		return (Map<String, String>) _instance.holdObject("category@lists@"+code);
	}

}
