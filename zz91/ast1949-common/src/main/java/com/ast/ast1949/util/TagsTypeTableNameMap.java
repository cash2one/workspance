/*
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-17 下午01:28:22
 */
package com.ast.ast1949.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 标签对应类别对应的表名
 *
 * @author Ryan(rxm1025@gmail.com)
 *
 */
public class TagsTypeTableNameMap {
	private static TagsTypeTableNameMap instance = null;
	private Map<String, String> parameters = new HashMap<String, String>();

	private TagsTypeTableNameMap() {
		parameters.put("10351000", "news");
		parameters.put("10351001", "products");
	}

	public static synchronized TagsTypeTableNameMap getInstance() {
		if (instance == null) {
			instance = new TagsTypeTableNameMap();
		}
		return instance;
	}

	/**
	 * @return the parameters
	 */
	public Map<String, String> getParameters() {
		return parameters;
	}

}
