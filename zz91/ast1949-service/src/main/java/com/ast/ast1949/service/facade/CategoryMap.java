package com.ast.ast1949.service.facade;

import java.util.HashMap;
import java.util.Map;

import com.ast.ast1949.service.facade.YuanliaoFacade;
import com.ast.ast1949.util.StringUtils;

public class CategoryMap {
	private static CategoryMap _instance = new CategoryMap();
	public final static Map<String, String> CODE_MAP = new HashMap<String, String>();

	private CategoryMap() {

	}

	public static CategoryMap getInstance() {
		return _instance;
	}

	public void init() {
		// 主类别
		Map<String, String> prentMap = YuanliaoFacade.getInstance().getChild(
				"20091000");
		Map<String, String> proMap = new HashMap<String, String>();
		Integer i = 1;
		for (String key : prentMap.keySet()) {
			if (StringUtils.isNber(key)) {
				CODE_MAP.put(String.valueOf(i),
						YuanliaoFacade.PINYIN_MAP.get(key));
				CODE_MAP.put(YuanliaoFacade.PINYIN_MAP.get(key),
						String.valueOf(i));
				i += 1;
			}
			if (StringUtils.isNber(key)) {
				proMap = YuanliaoFacade.getInstance().getChild(key);
				for (String keys : proMap.keySet()) {
					if (StringUtils.isNber(keys)) {
						CODE_MAP.put(String.valueOf(i),
								YuanliaoFacade.PINYIN_MAP.get(keys));
						CODE_MAP.put(YuanliaoFacade.PINYIN_MAP.get(keys),
								String.valueOf(i));
						i += 1;
					}
				}
			}
		}
		i += 1;
		CODE_MAP.put(String.valueOf(i), "gdsz");
		CODE_MAP.put("gdsz", String.valueOf(i));
		i += 1;
		CODE_MAP.put(String.valueOf(i), "gddw");
		CODE_MAP.put("gddw", String.valueOf(i));
		i += 1;
		CODE_MAP.put(String.valueOf(i), "zjnb");
		CODE_MAP.put("zjnb", String.valueOf(i));
		i += 1;
		CODE_MAP.put(String.valueOf(i), "zssz");
		CODE_MAP.put("zssz", String.valueOf(i));
		i += 1;
		CODE_MAP.put(String.valueOf(i), "gdmm");
		CODE_MAP.put("gdmm", String.valueOf(i));
		i += 1;
		CODE_MAP.put(String.valueOf(i), "sdqd");
		CODE_MAP.put("sdqd", String.valueOf(i));
		i += 1;
		CODE_MAP.put(String.valueOf(i), "sh");
		CODE_MAP.put("sh", String.valueOf(i));
		i += 1;
		CODE_MAP.put(String.valueOf(i), "jswx");
		CODE_MAP.put("jswx", String.valueOf(i));
		i += 1;
		CODE_MAP.put(String.valueOf(i), "gdfs");
		CODE_MAP.put("gdfs", String.valueOf(i));
		i += 1;
		CODE_MAP.put(String.valueOf(i), "sccd");
		CODE_MAP.put("sccd", String.valueOf(i));
		i += 1;
		CODE_MAP.put(String.valueOf(i), "lnsy");
		CODE_MAP.put("lnsy", String.valueOf(i));
		i += 1;
		CODE_MAP.put(String.valueOf(i), "zjhz");
		CODE_MAP.put("zjhz", String.valueOf(i));
		i += 1;
		CODE_MAP.put(String.valueOf(i), "zsnj");
		CODE_MAP.put("zsnj", String.valueOf(i));
		i += 1;
		CODE_MAP.put(String.valueOf(i), "qbdq");
		CODE_MAP.put("qbdq", String.valueOf(i));
	}
}
