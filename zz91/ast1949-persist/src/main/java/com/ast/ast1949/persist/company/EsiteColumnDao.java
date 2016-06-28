/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-2-13
 */
package com.ast.ast1949.persist.company;

import java.util.Map;

import com.ast.ast1949.domain.company.EsiteColumnDo;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-2-13
 */
public interface EsiteColumnDao {

	/**
	 * 查找系统中所有定义的列
	 * @param category:查找的列类型，没有值时默认为:0
	 * @return
	 */
	public Map<String, EsiteColumnDo> queryAllColumnByCategory(String category);
}
