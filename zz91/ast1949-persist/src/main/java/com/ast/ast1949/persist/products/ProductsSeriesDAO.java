/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-30 by liulei.
 */
package com.ast.ast1949.persist.products;

import java.util.List;

import com.ast.ast1949.domain.products.ProductsSeriesDO;

/**
 * @author liulei
 *
 */
public interface ProductsSeriesDAO {
	
	/**
	 * 根据ProductsSeriesDO中companyId,排序seriesOrder查询已分类别
	 * @param productsSeriesDO 为供求系列归类信息<br/>
	 * 		companyId不能为null，否则抛出异常
	 * @return ProductsSeriesDTO集合，可为null
	 */
//	public List<ProductsSeriesDO> queryProductsSeries(ProductsSeriesDO productsSeriesDO);
	
	/**
	 * //根据ProductsSeriesDO中id,companyId,排序seriesOrder查询已分类别
	 * @param companyId不能为null，否则抛出异常
	 * @return ProductsSeriesDTO集合，可为null
	 */
//	public List<ProductsSeriesDTO> queryHadSubSeries(ProductsSeriesDO productsSeriesDO); 
	
	/**
	 * 根据companyId查询未分类别
	 * @param companyId不能为null，否则抛出异常
	 * @return ProductsSeriesDTO集合，可为null
	 */
//	public List<ProductsSeriesDTO> queryNoHadSubSeries(Integer companyId);
	
	
	
	
	
	/**
	 * 修改ProductsSeriesDO中的供求系列名称(name)信息
	 * @param productsSeriesDO为供求系列信息<br/>
	 * 		id不能为空，否则抛出异常
	 * @return 修改成功返回大于0的值
	 */
//	public Integer updateProductsSeriesName(ProductsSeriesDO productsSeriesDO);
	
	
	
	/**
	 * 删除ProductsSeriesContactsDO信息
	 * @param id为ProductsSeriesContactsDO主键<br/>	
	 * 		id不能为空，否则抛出异常
	 * @return 删除成功返回大于0的值	
	 */
//	public Integer deleteProductsSeriesContacts(Integer id);
	
	/**
	 * 修改ProductsSeriesContactsDO信息
	 * @param productsSeriesContactsDO 供求系列关联信息<br/>
	 * 		productsSeriesContactsDO不能为空，否则抛出异常
	 * @return 修改成功返回大于0的值
	 */
//	public Integer updateProductsSeriesContacts(ProductsSeriesContactsDO productsSeriesContactsDO);
	
	
	
	/**
	 * 添加ProductsSeriesContactsDO信息
	 * @param productsSeriesContactsDO为ProductsSeriesContactsDO信息<br/>
	 * 		productsSeriesContactsDO不能为空，否则抛出异常
	 * @return	插入成功返回添加记录的主键值
	 */
//	public Integer insertProductsSeriesContacts(ProductsSeriesContactsDO productsSeriesContactsDO);
	
	/**
	 * 查询ProductsSeriesDO中的id在 ProductsSeriesContactsDO不存在的ProductsSeriesContactsDO主键值id
	 * @return ProductsSeriesContactsDO结果集，可为空
	 */
//	public List<ProductsSeriesContactsDO> queryNoContactsIdBySeriesId();
	
	/**
	 * 根据ProductsId查询ProductsSeriesContactsDO信息
	 * @param productsId为productsDO的主键
	 * @return ProductsSeriesContactsDO信息，可为空
	 */
//	public ProductsSeriesContactsDO selectProductSeriesContactsByProductsId(Integer productsId);
	
	/**
	 * 修改ProductsSeriesDO信息
	 * @param productsSeriesDO 为供求系列归类信息<br/>
	 * 		productsSeriesDO.getId()不能为null，否则抛出异常
	 * @return 修改成功返回大于0的值
	 */
//	public Integer updateProductsSeriesOrder(ProductsSeriesDO productsSeriesDO);

	/**
	 * 查询指定公司的某个系列下的产品分页列表
	 * @param userSeries 公司ID不能为空，如果系列为空表示未分组信息
	 * @param pager
	 * @return
	 */
//	public PageDto<ProductsSeriesDTO> queryProductInSeriesListBySeriesId(
//			Integer userSeriesId, PageDto<ProductsSeriesDTO> pager);

//	public PageDto<ProductsSeriesDTO> queryProductNotInSeriesListByCompanyId(Integer companyId,
//			PageDto<ProductsSeriesDTO> pager);
	
	/**************************/
	
	/**
	 * 添加ProductsSeriesDO信息
	 * @param productsSeriesDO为ProductsSeriesDO信息<br/>
	 * 		productsSeriesDO不能为空，否则抛出异常
	 * @return 插入成功返回添加记录的主键值
	 */
	public Integer insertProductsSeries(ProductsSeriesDO productsSeriesDO);
	
	public Integer countSeriesOfCompany(Integer companyId);
	
	public Integer insertSeriesContacts(Integer groupId, Integer productId);

	public Integer deleteSeriesContacts(Integer groupId, Integer productId);
	
	public List<ProductsSeriesDO> querySeriesOfCompany(Integer companyId);
	
	/**
	 * 根据id查询ProductsSeriesDO信息
	 * @param id为供求系列归类主键<br/>
	 * 		id不能为null，否则抛出异常
	 * @return ProductsSeriesDO信息，没查询到数据为null
	 */
	public ProductsSeriesDO queryProductsSeriesById(Integer id);
	
	/**
	 * 修改ProductsSeriesDO信息
	 * @param productsSeriesDO 为供求系列归类信息<br/>
	 * 		productsSeriesDO对象不能为空，否则抛出异常
	 * @return 修改成功返回大于0的值
	 */
	public Integer updateProductsSeries(ProductsSeriesDO productsSeriesDO);
	
	/**
	 * 删除ProductsSeriesDO信息
	 * @param id为ProductsSeriesDO主键<br/>
	 * 		id不能为空，否则抛出异常
	 * @return 删除成功返回大于0的值
	 */
	public Integer deleteProductsSeries(Integer id);

	public ProductsSeriesDO queryProductsSeriesByProudctId(Integer productId);
	
	/**
	 * 检索某一个供求系列下供求的数量
	 * @param sid 
	 * @return
	 */
	public Integer querySeriesContacts(Integer sid);
}
