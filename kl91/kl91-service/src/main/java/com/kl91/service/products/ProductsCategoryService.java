package com.kl91.service.products;

import java.util.List;

import com.kl91.domain.products.ProductsCategory;

public interface ProductsCategoryService {

	/**
	 * 创建一个产品类别 后台管理类别 
	 * 1、使用parentCode和queryMaxCodeBypreCode获得最大code
	 * 2、然后组装obj后,insert入Db表中
	 */
	public Integer createProductsCategory(ProductsCategory productsCategory,
			String parentCode);

	/**
	 * 编辑一个产品类别 后台管理,更改类别详细信息 
	 * 1、根据id搜索类别详细信息在页面中显示 
	 * 2、在表单中填入更新信息更新
	 * 
	 */
	public Integer editProductCategory(ProductsCategory productsCategory);

	/**
	 * 删除一个产品类别 后台管理操作,最好先做过确认。
	 */
	public Integer deleteProductsCategory(Integer id);

	/**
	 * 通过产品类别ID搜搜产品类别详细 搜索类别详细信息使用于更新或者查看类别
	 */
	public ProductsCategory queryById(Integer id);

	/**
	 * 搜索类别及其下所有的子类别 如果code不传值,则搜索出所有的类别
	 */
	public List<ProductsCategory> queryByCode(String code);

	/**
	 * 搜索最大Code,适合于添加类别的时候使用。
	 * 1、传入code算出符合code下的最大code
	 */
	public String queryMaxCodeBypreCode(String preCode);
	
	public List<ProductsCategory> queryAllCategoryProducts();
}
