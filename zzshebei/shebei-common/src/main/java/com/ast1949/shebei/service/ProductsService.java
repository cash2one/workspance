package com.ast1949.shebei.service;

import java.util.Date;
import java.util.List;
import com.ast1949.shebei.domain.Products;
import com.ast1949.shebei.dto.PageDto;

public interface ProductsService {
	/**
	 * 数据导入
	 * @author 陈庆林
	 * @param product(产品信息)
	 * @return
	 */
	public Integer createProducts(Products product);


	/**
	 * 查询公司供求信息
	 * @param cid(公司id)
	 * @param type(类别)
	 * @param size(查询的条数)
	 * @return
	 */
	public List<Products> queryProductsByType(Integer cid,Short type,Integer size);

	/**
	 * 查询公司所有供求信息(根据类别查询供应信息)
	 * @param categoryCode
	 * @param cid
	 * @param type
	 * @param page
	 * @return
	 */
	public PageDto<Products> pageProducts(String categoryCode,Integer cid,Short type, PageDto<Products> page);

	/**
	 * 查询产品详细信息
	 * @param id
	 * @return
	 */
	public Products queryProductbyId(Integer id);

	/**
	 * 查询公司其他供求产品
	 * (cid =#cid# and category_code!=#categoryCode#)
	 */
	
	public List<Products> queryOtherProducts(Integer cid,String categoryCode,Short type,Integer size);

	/**
	 * 查询相关产品(查询其他公司的相同类型产品)
	 * (cid!=cid and category_code=#categoryCode#)
	 */
	
	public List<Products> queryRelatedProducts(String categoryCode,Integer cid,Short type,Integer size);

	/**
	 * (查询最大展示时间)
	 * @return
	 */
	public Date queryMaxGmtShow();
}
