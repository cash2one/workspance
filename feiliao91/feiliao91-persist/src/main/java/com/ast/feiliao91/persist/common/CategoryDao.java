package com.ast.feiliao91.persist.common;

import java.io.IOException;
import java.util.List;

import com.ast.feiliao91.domain.common.Category;

public interface CategoryDao {
	/**
	 * 插入信息
	 * 
	 * @param category表
	 * @return
	 */
	public Integer insert(Category category);

	/**
	 * 根据ID查询
	 */
	public Category selectById(Integer id);
	/**
	 * 检索所有未删除的类别
	 * @return
	 */
	public List<Category> queryAllCategory();
	
	/**
	 * 根据前缀Code选择所有对应的类别
	 * @param preCode
	 *          父类别Code
	 * @return

	 */
	public List<Category> queryCategoriesByPreCode(String preCode);
	
	/**
	 * 获取类别下一级的最大可用code
	 *
	 * @param preCode
	 * @return
	 * @throws IOException
	 */
	public String queryMaxCodeByPreCode(String preCode);
	
	public Integer delete(Integer id);
	
	public Integer deleteCategoryByCode(String code);
	
	public Category selectByCode(String code);

	Integer updateCategory(Category category);
	
}
