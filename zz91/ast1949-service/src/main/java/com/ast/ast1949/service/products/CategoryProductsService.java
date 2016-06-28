/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-2
 */
package com.ast.ast1949.service.products;

import java.util.List;
import java.util.Map;

import com.ast.ast1949.domain.products.CategoryProductsDO;
import com.ast.ast1949.dto.ExtTreeDto;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.products.CategoryProductsDTO;

/**
 * @author yuyonghui
 *
 */
public interface CategoryProductsService {

	final static String CACHE_KEY = "category_products_recursion";
	final static int CACHE_TIMEOUT = 0;
	final static String DEFAULT_PINGY = "a";

	/**
	 * 查询所有产品类别
	 *
	 * @param categoryProductsDTO
	 * @return 返回结果集
	 */
	public List<CategoryProductsDO> queryAllCategoryProducts();
	
	/**
	 * 根据条件查询所有产品类别
	 *
	 * @param categoryProductsDTO
	 * @return 返回结果集
	 */
//	public List<CategoryProductsDO> queryCategoryProductsByCondition(CategoryProductsDTO categoryProductsDTO);

	/**
	 * 按ID 查询产品类别
	 *
	 * @param id
	 * @return CategoryProductsDO null
	 */
	public CategoryProductsDO queryCategoryProductsById(int id);

	/**
	 * 获取某个类别下的子类别,并且返回Ext tree需要的数据格式
	 *
	 * @param code
	 *            父结点编号
	 * @return ext 树结点 ExtTreeDto.id对应CategoryProductsDO.id
	 *         ExtTreeDto.text对应CategoryProductsDO.label
	 *         ExtTreeDto.data对应CategoryProductsDO.data ExtTreeDto.leaf = false
	 *         表示仍有子节点,true表示无子节点
	 */
	public List<ExtTreeDto> child(String code, String isAssist);

	/**
	 * 按preCode 查询最大值
	 *
	 * @param preCode
	 * @return
	 */
	public String queryMaxCodeBypreCode(String preCode);

	/**
	 * 按code 查询所有产品类别
	 *
	 * @param code
	 * @return 结果集 0
	 */
	public List<CategoryProductsDO> queryCategoryProductsByCode(String code, String isAssist);

	/**
	 * 添加产品类别
	 *
	 * @param categoryProductsDO
	 * @return 成功：返回影响行数；<br/>
	 *         失败：返回0.
	 */
	public int insertCategoryProducts(CategoryProductsDO categoryProductsDO, String preCode);

	/**
	 * 修改产品类别
	 *
	 * @param categoryProductsDO
	 * @return 成功：返回影响行数；<br/>
	 *         失败：返回0.
	 */
	public Integer updatecategoryProducts(CategoryProductsDO categoryProductsDO);

	/**
	 * 删除产品类别
	 *
	 * @param categoryProductsDO
	 * @return 成功 返回影响行数 失败 返回 0
	 */
//	public int deleteCategoryProductsById(CategoryProductsDO categoryProductsDO);

	/**
	 * 查询  供求类别名称  根据code
	 * @return CategoryProductsDO集合，
	 *      没查询到数据返回为null
	 */
//	public CategoryProductsDO queryCategoryNameByCode(String code);

	/**
	 * 查询产品类别code长度为4的信息
	 * @return CategoryProductsDO集合，没查询到数据返回为null
	 */
//	public List<CategoryProductsDO> queryCategoryProductsFront();

	/**
	 * 删除供求类别及其子类别
	 * @param id:供求类别ID,不能为null
	 * @return
	 */
	public Integer deleteCategoryProductsAndChildById(Integer id);

	/**
	 * 通过拼音首字母检索相应的供求类别
	 * @param pingy:首字母,为null时默认{@link #DEFAULT_PINGY}
	 * @return
	 */
	public List<CategoryProductsDO> queryCategoryProductsByCnspell(String pingy);

	public CategoryProductsDO queryCategoryProductsByLabel(String keywords);
	
	/*********
	 * 通过关键字查询 该关键字对应的 报价code 
	 * 用在企业报价的最新供求的 查询 小类别的 供求
	 * @param label
	 * @return
	 */
	public CategoryProductsDO queryCategoryProductsByKey(String label);
	
	public List<CategoryProductsDO> queryCategoryByTags(String keywords, Integer size);
	/**
	 * 根据关键字匹配相应的类别，使用于生意管家供求发布的搜索功能
	 * @param label
	 * @return
	 */
	public List<Map<String,Object>> queryAllCategoryProductsByLabel(String label);
	/**
	 * 根据companyId搜索用户所有发布成功的类别列表，使用于生意管家供求发布的常用类别功能
	 * @param label
	 * @return
	 */
	public List<Map<String, Object>> queryHistoryCategoryByCompanyId(Integer companyId);
	
	public String queryNameByCode(String code);

	
	/**
	 * 构建所有类别 search_label
	 */
	void buildAllSearchLabel();

	/**
	 * 搜索引擎(非solr) 获取供求类别
	 * @param title
	 * @param page
	 * @return
	 */
	public PageDto<CategoryProductsDTO> pageCategoryProductsBySearchEngine(String title, PageDto<CategoryProductsDTO> page);

	/**
	 * 搜索一条以上数据 通过搜索引擎(非solr)
	 * @param title
	 * @param page
	 * @return
	 */
	public PageDto<CategoryProductsDTO> pageMoreOneCategoryProductsBySearchEngine(String title, PageDto<CategoryProductsDTO> page);

}
