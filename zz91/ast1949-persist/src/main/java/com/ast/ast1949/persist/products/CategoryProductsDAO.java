/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-2
 */
package com.ast.ast1949.persist.products;

import java.util.List;

import com.ast.ast1949.domain.products.CategoryProductsDO;

/**
 * @author yuyonghui
 *
 */
public interface CategoryProductsDAO {

	final static String DELED_TAGS = "1";

	/**
	 * 查询所有产品类别
	 *
	 * @param categoryProductsDTO
	 * @return 返回结果集
	 */
	public List<CategoryProductsDO> queryAllCategoryProducts();
   /**
    *   根据条件查询所有产品类别
    * @param categoryProductsDTO
    * @return  返回结果集
    */
//	public List<CategoryProductsDO> queryCategoryProductsByCondition(CategoryProductsDTO categoryProductsDTO);

	/**
	 *   按ID 查询产品类别
	 * @param id
	 * @return  CategoryProductsDO
	 *         null
	 */
	public CategoryProductsDO queryCategoryProductsById(int id);

//	public CategoryProductsDO queryCategoryProductsByCode(String code);
	/**
	 * 按preCode 查询最大值    ---供添加使用
	 * @param preCode
	 * @return
	 */
	public String queryMaxCodeBypreCode(String preCode);

	/**
	 *  按code 查询所有产品类别
	 * @param code
	 * @return   结果集
	 *         0
	 */
	public List<CategoryProductsDO> queryCategoryProductsByCode(String code,String isAssist);

	/**
	 *   添加产品类别
	 * @param categoryProductsDO
	 * @return  成功：返回影响行数；<br/>
	 * 			失败：返回0.
	 */
	public int insertCategoryProducts(CategoryProductsDO categoryProductsDO);
     /**
      *   修改产品类别
      * @param categoryProductsDO
      * @return 成功：返回影响行数；<br/>
	  * 			失败：返回0.
      */
	public Integer updatecategoryProducts(CategoryProductsDO categoryProductsDO);

	 /**
	  *   删除产品类别
	  * @param categoryProductsDO
	  * @return  成功 返回影响行数
	  *          失败 返回 0
	  */
//	public int deleteCategoryProductsById(CategoryProductsDO categoryProductsDO);

	/**
	 * 查询产品类别code长度为4的信息
	 * @return CategoryProductsDO集合，没查询到数据返回为null
	 */
//	public List<CategoryProductsDO> queryCategoryProductsFront();

	/**
	 * 查询  供求类别名称  根据code
	 * @return CategoryProductsDO集合，
	 *      没查询到数据返回为null
	 */
//	public CategoryProductsDO queryCategoryNameByCode(String code);

	/**
	 * 删除供求类别及其子类别
	 * @param parentCode:待删除的类别code,不能为null
	 * @return
	 */
	public Integer deleteCategoryProductsAndChild(String parentCode);

	/**
	 * 通过拼音首字母检索相应的供求类别
	 * @param pingy:检索用的首字母,不能为null
	 * @return
	 */
	public List<CategoryProductsDO> queryCategoryProductsByCnspell(String pingy);

	public CategoryProductsDO queryCategoryProductsByLabel(String label);
	
	/*********
	 * 通过关键字查询 该关键字对应的 报价code 
	 * 用在企业报价的最新供求的 查询 小类别的 供求
	 * @param label
	 * @return
	 */
	public CategoryProductsDO queryCategoryProductsByKey(String label);
	
	public List<CategoryProductsDO> queryCategoryByTags(String keywords, Integer size);

	/**
	 * 根据关键字搜索所有的匹配类别，使用于生意管家发布供求的搜索
	 * @param label
	 * @return
	 */
	public List<CategoryProductsDO> queryAllCategoryProductsByLabel(String label);
	
	/**
	 * 根据companyId搜索所有的匹配类别，使用于生意管家发布供求的历史类别
	 * @param companyId
	 * @return
	 */
	public List<CategoryProductsDO> queryHistoryCategoryByCompanyId(Integer companyId);
	
	public String queryNameByCode(String code);
	
	public Integer updateSearchLabelById(Integer id,String searchLabel);
}
