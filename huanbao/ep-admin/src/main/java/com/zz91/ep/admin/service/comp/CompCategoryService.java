/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-11-29
 */
package com.zz91.ep.admin.service.comp;

import java.util.List;
import java.util.Map;

import com.zz91.ep.domain.comp.CompCategory;


/**
 */
public interface CompCategoryService {

	/**
	 * 列出所有公司类别信息
	 * @return
	 */
	public List<CompCategory> listAllCompCategory();

	/**
	 * 增加公司类别信息
	 * @param compCategory
	 * @return
	 */
	public Integer createCompCategory(CompCategory compCategory);

	/**
	 * 修改公司类别信息
	 * @param compCategory
	 * @return
	 */
	public Integer updateCompCategory(CompCategory compCategory);

	/**
	 * 查询单个公司类别信息
	 * @param id
	 * @return
	 */
	public CompCategory listOneCompCategoryById(Integer id);

	/**
	 * 删除公司类别信息
	 * @param id
	 * @return
	 */
	public Integer deleteCompCategory(Integer id);

	/**
	 * 列出所有公司类别信息,并且返回一个Map
	 * @return
	 */
	public Map<String, String> listAllCompCategoryToMap();

}