/**
 * @author shiqp
 * @date 2016-01-15
 */
package com.ast.feiliao91.service.facade;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ast.feiliao91.domain.goods.GoodsCategory;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.lang.StringUtils;

public class GCategoryFacade implements CategoryAccessor{

	private static GCategoryFacade _instance = new GCategoryFacade();
	public final static String CACHE_KEY = "goods_cache";
	public final static int EXPIRATION = 0;
	private static Map<String, Object> GOODS_MAP = new LinkedHashMap<String, Object>();
	private static String MEM_TYPE = CategoryAccessor.ACCESS_FROM_MEMORY;

	private GCategoryFacade() {
	}

	public static GCategoryFacade getInstance() {
		return _instance;
	}
	
	public void putObject(String key, Object value) {
		if (CategoryAccessor.ACCESS_FROM_MEMCACHE.equals(MEM_TYPE)) {
			MemcachedUtils.getInstance().getClient().set(key, GCategoryFacade.EXPIRATION,
					value);
		} else {
			GOODS_MAP.put(key, value);
		}
	}

	public Object holdObject(String key) {
		if (CategoryAccessor.ACCESS_FROM_MEMCACHE.equals(MEM_TYPE)) {
			return MemcachedUtils.getInstance().getClient().get(key);
		} else {
			return GOODS_MAP.get(key);
		}
	}

	@SuppressWarnings("unchecked")
	public void init(List<GoodsCategory> list,String initTargetType) {
		if(StringUtils.isNotEmpty(initTargetType)){
			MEM_TYPE = initTargetType;
		}
		if (list==null||list.size()==0){
			return ;
		}

		for (GoodsCategory category : list) {
			putObject("goodsCategory@"+category.getCode(), category.getLabel());
			String mapkey="goodsCategory@lists@";
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
		return (String) _instance.holdObject("goodsCategory@" + code);
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getChild(String code) {
		if(code==null){
			return null;
		}
		return (Map<String, String>) _instance.holdObject("goodsCategory@lists@"+code);
	}

}
