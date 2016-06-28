package com.ast1949.shebei.dao;

import java.util.List;

import com.ast1949.shebei.domain.CategoryProducts;

public interface CategoryProductsDao {
	
	/**
	 * 查询所有类别
	 * @author 陈庆林
	 * @return
	 */
	public List<CategoryProducts> queryAllCategorys(Short flag,String code);
	
	/**
	 * 根据类别查询类别名
	 * @author 陈庆林
	 * @param code(类别)
	 * @return
	 */
	public CategoryProducts queryCtypeByCode(String code);
}
