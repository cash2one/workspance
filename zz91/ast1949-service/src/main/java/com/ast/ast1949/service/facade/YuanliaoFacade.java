package com.ast.ast1949.service.facade;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ast.ast1949.domain.yuanliao.CategoryYuanliao;
import com.ast.ast1949.util.StringUtils;
import com.zz91.util.cache.MemcachedUtils;

public class YuanliaoFacade implements CategoryAccessor{

	private static YuanliaoFacade _instance = new YuanliaoFacade();
	public final static String CACHE_KEY = "yuanliao_cache";
	public final static int EXPIRATION = 0;
	private static Map<String, Object> yuanliaoMap = new LinkedHashMap<String, Object>();
	private static String MEM_TYPE = CategoryAccessor.ACCESS_FROM_MEMORY;
	public final static Map<String, String> PINYIN_MAP = new HashMap<String, String>();
	public final static Map<String, String> CATE_MAP = new HashMap<String, String>();

	private YuanliaoFacade() {
	}

	public static YuanliaoFacade getInstance() {
		return _instance;
	}

	public void putObject(String key, Object value) {
		if (CategoryAccessor.ACCESS_FROM_MEMCACHE.equals(MEM_TYPE)) {
			MemcachedUtils.getInstance().getClient().set(key, YuanliaoFacade.EXPIRATION,
					value);
		} else {
			yuanliaoMap.put(key, value);
		}
	}

	public Object holdObject(String key) {
		if (CategoryAccessor.ACCESS_FROM_MEMCACHE.equals(MEM_TYPE)) {
			return MemcachedUtils.getInstance().getClient().get(key);
		} else {
			return yuanliaoMap.get(key);
		}
	}

	@SuppressWarnings("unchecked")
	public void init(List<CategoryYuanliao> list,String initTargetType) {
		if(StringUtils.isNotEmpty(initTargetType)){
			MEM_TYPE = initTargetType;
		}
		if (list==null||list.size()==0){
			return ;
		}

		for (CategoryYuanliao category : list) {
			putObject("yuanliao@"+category.getCode(), category.getLabel());
			String mapkey="yuanliao@lists@";
			if(category.getCode().length()>4){
				mapkey=mapkey+category.getCode().substring(0, category.getCode().length()-4);
			}
			Map<String, String> map=(Map<String, String>) holdObject(mapkey);
			if(map==null ){
				map = new LinkedHashMap<String, String>();
			}
			map.put(category.getCode(), category.getLabel());
			putObject(mapkey, map);
			
			// 拼音装载入静态 map
			if (StringUtils.isNotEmpty(category.getPinyin())) {
				PINYIN_MAP.put(category.getPinyin(),category.getCode());
				PINYIN_MAP.put(category.getCode(),category.getPinyin());
			}
			if(category.getCode().length()>16){
				CATE_MAP.put(String.valueOf(category.getId()), category.getCode());
				CATE_MAP.put(category.getCode(), String.valueOf(category.getId()));
			}
		}
	}

	public String getValue(String code) {
		if(code==null){
			return null;
		}
		return (String) _instance.holdObject("yuanliao@" + code);
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getChild(String code) {
		if(code==null){
			return null;
		}
		return (Map<String, String>) _instance.holdObject("yuanliao@lists@"+code);
	}

}
