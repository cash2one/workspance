/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-31 by liulei.
 */
package com.ast.ast1949.service.products;

import java.util.List;

import com.ast.ast1949.domain.products.ProductsSeriesDO;

/**
 * @author liulei
 * 
 */
public interface ProductsSeriesService {

	/**
	 * 根据ProductsSeriesDO中companyId,排序seriesOrder查询已分类别
	 * 
	 * @param productsSeriesDO
	 *            为供求系列归类信息<br/>
	 *            companyId不能为null，否则抛出异常
	 * @return ProductsSeriesDTO集合，可为null
	 */
//	public List<ProductsSeriesDO> queryProductsSeries(ProductsSeriesDO productsSeriesDO);

	/**
	 * 根据ProductsSeriesDO中companyId,排序seriesOrder查询已分类别
	 * 
	 * @param companyId不能为null
	 *            ，否则抛出异常
	 * @return ProductsSeriesDTO集合，可为null
	 */
//	public Map<ProductsSeriesDO, List<ProductsSeriesDTO>> queryHadSubSeries(
//			ProductsSeriesDO productsSeriesDO);

	/**
	 * 根据companyId查询未分类别
	 * 
	 * @param companyId不能为null
	 *            ，否则抛出异常
	 * @return ProductsSeriesDTO集合，可为null
	 */
//	public List<ProductsSeriesDTO> queryNoHadSubSeries(Integer companyId);

	

	

	/**
	 * 修改ProductsSeriesDO中的供求系列名称(name)信息
	 * 
	 * @param productsSeriesDO为供求系列信息
	 * <br/>
	 *            productsSeriesDO.getId()不能为空，否则抛出异常
	 * @return 修改成功返回大于0的值
	 */
//	public Integer updateProductsSeriesName(ProductsSeriesDO productsSeriesDO);

	

	/**
	 * 删除ProductsSeriesContactsDO信息
	 * 
	 * @param id为ProductsSeriesContactsDO主键
	 * <br/>
	 *            id不能为空，否则抛出异常
	 * @return 删除成功返回大于0的值
	 */
//	public Integer deleteProductsSeriesContacts(Integer id);

	/**
	 * 修改ProductsSeriesContactsDO信息
	 * 
	 * @param productsSeriesContactsDO
	 *            供求系列关联信息<br/>
	 *            productsSeriesContactsDO.getId()不能为空，否则抛出异常
	 * @return 修改成功返回大于0的值
	 */
//	public Integer updateProductsSeriesContacts(ProductsSeriesContactsDO productsSeriesContactsDO);

	/**
	 * 添加ProductsSeriesDO信息
	 * 
	 * @param productsSeriesDO为ProductsSeriesDO信息
	 * <br/>
	 *            productsSeriesDO不能为空，否则抛出异常
	 * @return 插入成功返回添加记录的主键值
	 */
//	public Integer insertProductsSeries(ProductsSeriesDO productsSeriesDO);

	/**
	 * 添加ProductsSeriesContactsDO信息
	 * 
	 * @param productsSeriesContactsDO为ProductsSeriesContactsDO信息
	 * <br/>
	 *            productsSeriesContactsDO不能为空，否则抛出异常
	 * @return 插入成功返回添加记录的主键值
	 */
//	public Integer insertProductsSeriesContacts(ProductsSeriesContactsDO productsSeriesContactsDO);

	/**
	 * 修改ProductsSeriesContactsDO信息
	 * 
	 * @param ids是productsDO信息的主键值
	 * @param productsSeriesId是供求系列的主键值
	 * @return 修改成功返回大于0的值
	 */
//	public Integer editProductsSeriesContactsStatus(String ids, Integer productsSeriesId);

	/**
	 * 修改ProductsSeriesDO信息
	 * 
	 * @param productsSeriesDO
	 *            为供求系列归类信息<br/>
	 *            productsSeriesDO.getId()不能为null，否则抛出异常
	 * @return 修改成功返回大于0的值
	 */
//	public Integer updateProductsSeriesOrder(ProductsSeriesDO productsSeriesDO);

	/**
	 * 查询指定公司的某个系列下的产品分页列表
	 * 
	 * @param userSeries
	 *            公司ID不能为空，如果系列为空表示未分组信息
	 * @param pager
	 * @return
	 */
//	public PageDto<ProductsSeriesDTO> queryProductInSeriesListBySeriesId(
//			Integer userSeriesId, PageDto<ProductsSeriesDTO> pager);

//	PageDto<ProductsSeriesDTO> queryProductNotInSeriesListByCompanyId(Integer companyId,
//			PageDto<ProductsSeriesDTO> pager);

	/*****up code is old*****************************/
	
	/**
	 * 创建供求系列，创建前需要判断用户可创建系列的最大值（25个）
	 * @param group
	 * @return
	 */
	public Integer createSeries(ProductsSeriesDO group);
	
	/**
	 * 根据id查询ProductsSeriesDO信息
	 * 
	 * @param id为供求系列归类主键
	 * <br/>
	 *            id不能为null，否则抛出异常
	 * @return ProductsSeriesDO信息，没查询到数据为null
	 */
	public ProductsSeriesDO queryProductsSeriesById(Integer id);
	
	/**
	 * 修改ProductsSeriesDO信息
	 * 
	 * @param productsSeriesDO
	 *            为供求系列归类信息<br/>
	 *            productsSeriesDO.getId()对象不能为空，否则抛出异常
	 * @return 修改成功返回大于0的值
	 */
	public Integer updateProductsSeries(ProductsSeriesDO productsSeriesDO);
	
	/**查询公司自己创建的系列信息，并按排序字段正序排序
	 * @param companyId
	 * @return
	 */
	public List<ProductsSeriesDO> querySeriesOfCompany(Integer companyId);
	
	public Integer createSeriesContacts(Integer groupId, Integer[] productId);
	
	/**
	 * 从系列中移除
	 * @param groupId：如果为空，则将供求信息从全部系列中移除，否则从指定系列移除
	 * @param productId
	 * @return
	 */
	public Integer deleteSeriesContacts(Integer groupId, Integer[] productId);
	
	/**
	 * 删除ProductsSeriesDO信息
	 * 
	 * @param id为ProductsSeriesDO主键
	 * <br/>
	 *            id不能为空，否则抛出异常
	 * @return 删除成功返回大于0的值
	 */
	public Integer deleteProductsSeries(Integer id);
	
	/**
	 * 根据供求id搜索供求所在类别
	 * @param productId
	 * @return
	 */
	public ProductsSeriesDO queryProductsSeriesByProudctId(Integer productId); 
}
