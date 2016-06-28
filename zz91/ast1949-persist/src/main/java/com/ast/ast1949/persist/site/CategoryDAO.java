/*
 * Copyright 2009 ASTO.
 * All right reserved.
 * Created on 2009-12-9 by Ryan.
 */
package com.ast.ast1949.persist.site;

import java.io.IOException;
import java.util.List;

import com.ast.ast1949.domain.site.CategoryDO;
import com.ast.ast1949.dto.site.CategoryDTO;

/**
 * @author Ryan
 *
 */
public interface CategoryDAO {

	/**
	 * 根据前缀Code选择所有对应的类别
	 * @param preCode
	 *          父类别Code
	 * @return

	 */
	public List<CategoryDO> queryCategoriesByPreCode(String preCode);

	/**
	 * 添加类别
	 * @param categoryDO
	 * @param preCode
	 * 		根据这个值计算出要添加的类别的code
	 * @return
	 *      成功:插入的那条记录的ID
	 *      失败:抛出异常
	 * @throws IOException
	 */
	public int insertCategory(CategoryDO categories);

	/**
	 * 修改类别
	 *
	 * @param id
	 * @return
	 */
	public int updateCategory(CategoryDO categoryDO);

	/**
	 * 删除类别
	 *
	 * @param code
	 * 		删除和code匹配的所有类别
	 * @return
	 * 		成功删除的条数
	 */
	public int deleteCategoryByCode(String code);

	/**
	 * 获取类别下一级的最大可用code
	 *
	 * @param preCode
	 * @return
	 * @throws IOException
	 */
	public String queryMaxCodeByPreCode(String preCode);

	/**
	 * 根据id选择记录
	 *
	 * @param id
	 * @return
	 */
	public CategoryDO queryCategoryById(int id);
	/**
     * 根据label选择记录
     *
     * @param id
     * @return
     */
    public CategoryDO queryCategoryBylabel(String label);

	/**
	 * 获取列表
	 * @param dto 带搜索和分页参数
	 * @return
	 */
	public List<CategoryDO> queryCategoriesByCondition(CategoryDTO dto);

	/**
	 * 统计记录数
	 *
	 * @param map
	 * @return
	 */
//	public int queryRecordCountByCondition(CategoryDTO dto);

	/**
	 *    根据code 查询 label值
	 * @param code
	 * @return   成功返回结果
	 *           失败返回 null
	 *
	 */
	public CategoryDO queryCategoryByCode(String code);

//	public List<CategoryDO> queryCategoriesByParentCode(String parentCode);

	public List<CategoryDO> queryCategoryList();
	
	public String queryCodeByLabel(String label);
	
}
