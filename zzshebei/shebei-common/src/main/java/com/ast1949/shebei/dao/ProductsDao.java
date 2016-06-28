package com.ast1949.shebei.dao;

import java.util.Date;
import java.util.List;
import com.ast1949.shebei.domain.Products;
import com.ast1949.shebei.dto.PageDto;

/**
 * 
 * @author 陈庆林
 * 2012-7-24 下午4:09:14
 */


public interface ProductsDao {
	/**
	 * 数据导入
	 * @author 陈庆林
	 * @param product(产品信息)
	 * @return
	 */
	public Integer insertProducts(Products product);
	
	/**
	 * 查询公司供求信息
	 * @param cid(公司id)
	 * @param type(类别)
	 * @param size(查询的条数)
	 * @return
	 */
	public List<Products> queryProductsByType(Integer cid,Short type,Integer size);
	
	/**
	 * 查询所有的供应信息
	 * @author 陈庆林
	 * @param categoryCode
	 * @param cid
	 * @param type
	 * @param page
	 * @return
	 */
	public List<Products> queryProductsByCidAndType(String categoryCode,Integer cid,Short type,PageDto<Products> page);
	
	/***
	 * 查询产品数量
	 * @author 陈庆林
	 * @param categoryCode
	 * @param cid
	 * @param type
	 * @return
	 */
	public Integer queryProductsByCidAndTypeCount(String categoryCode,Integer cid,Short type);
	
	/**
	 * @author 陈庆林
	 * 查询产品详细信息
	 * @param id
	 * @return
	 */
	public Products queryProductbyId(Integer id);
	
	/**
	 * @author 陈庆林
	 * 查询公司其他供求产品
	 * (cid =#cid# and category_code!=#categoryCode#)
	 */
	public List<Products> queryOtherProducts(Integer cid,String categoryCode,Short type,Integer size);

	/**
	 * @author 陈庆林
	 * 查询相关产品
	 * (cid!=cid and category_code=#categoryCode#)
	 */
	public List<Products> queryRelatedProducts(String categoryCode,Integer cid,Short type,Integer size);
	
	/**
	 * @author 陈庆林
	 * (查询最大展示时间)
	 * @return
	 */
	public Date queryMaxGmtShow();
}
