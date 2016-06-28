package com.kl91.service.products;

import com.kl91.domain.dto.PageDto;
import com.kl91.domain.dto.products.ProductsDto;
import com.kl91.domain.dto.products.ProductsSearchDto;
import com.kl91.domain.products.Products;

public interface ProductsService {
	
	public static final Integer DELETE_SUCCESS = 1;
	public static final Integer DELETE_FAILTURE= 0;
	
	public static final Integer CHECKED_SUCCESS = 1;
	public static final Integer CHECKED_FAILTURE= 0;
	public static final Integer CHECKED_WAITING = 2;
	
	public static final Integer PUBLISH_FLAG = 1;
	public static final Integer NOT_PUBLISH_FLAG= 0;

	public static final String BUY_TYPE_CODE = "0";
	public static final String SELL_TYPE_CODE = "1";
	

	/**
	 * 创建供求 1、含图片地址 1.1、供求成功insert后，根据picid更新targetid信息 2、不含图片地址,进入3 3、更新供求信息
	 */
	public Integer createProducts(Products product, Integer picId);

	/**
	 * 编辑供求 1、含图片地址更改 1.1、供求成功insert后，根据picid更新targetid信息 2、不含图片地址更改,进入3
	 * 3、更新供求信息
	*/
	public Integer editProducts(Products product, Boolean nfileFlag,Integer picid);

	/**
	 * 删除一条供求 将is_deleted更新为true
	*/
	public Integer deleteProducts(Integer id);

	/**
	 * 搜索供求列表solr
	 * 
	*/
	public PageDto<ProductsDto> queryProductsFromSolr(ProductsSearchDto dto,
			PageDto<ProductsDto> page);

	public PageDto<Products> queryProducts(Products products ,PageDto<Products> page);
	/**
	 * 获取一条供求 通过 ID 生意管家更改供求用到表连接,需要获取图片地址
	*/
	public Products queryById(Integer id);

	/**
	 * 批量更新供求至暂不发布 id为","分割的字符串
	*/
	public Integer updateProductsIsNoPub(String id,Integer publishFlag);

	/**
	 * 批量刷新供求 id为","分割的字符串
	*/
	public Integer refreshProducts(String id);
	
	public Integer deleteMost(String id,Integer deleteFlag);
	/**
	 * 搜索 高会 供求 前期搜索后台导入的再生通会员信息
	*/
	public PageDto<Products> queryVIPProducts(ProductsSearchDto searchDto,
			PageDto<Products> page);

	/**
	 * 搜索 供求信息 通过供求类型 solr
	 * 
	*/
	public PageDto<Products> queryProductsByTypeCode(String code,
			PageDto<Products> page);

	/**
	 * 供求搜索页面列表页 solr
	 */
	public PageDto<ProductsDto> queryProductsForList(Integer deletedFlag,
			Integer publishFlag,Integer checkedFlag,PageDto<ProductsDto> page);

	/**
	 * 搜索供求信息详细页,包括公司信息 旺铺供求detail页面数据
	 */
	public ProductsDto queryProductsAndCompanyById(Integer id);

	/**
	 * 搜索指定公司ID的所有供求产品 solr
	 */
	public PageDto<Products> queryProductsByCompanyId(Integer companyId,
			PageDto<Products> page, ProductsSearchDto searchDto);

	/**
	 * 后台审核供求信息 checkStatus为审核状态 
	 * 逻辑: 
	 * 1、后台人员通过后。 2、根据id搜索公司ID 3、根据公司ID,统计该公司供求数量
	 * (用countProductsIsPassByCompanyId)
	 */
	public Integer batchCheckByStatus(String id, Boolean checkStatus);
	/**
	 * 逻辑：
	 * 1、根据公司ID统计返回公司发布的供求数量 2、更新数量至num_pass字段
	 */
	public Integer countProductsIsPassByCompanyId(Integer companyId);
	
	/**
	 * 根据公司ID和供求类型，分别统计客户发布的供应信息数和求购信息数
	 */
	public Integer countProducts(Integer companyId,String productType);
	
}
