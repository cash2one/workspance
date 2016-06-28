/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-25
 */
package com.ast.ast1949.persist.products;

import java.util.List;

import com.ast.ast1949.domain.products.ProductsAlbumsDO;

/**
 * @author yuyonghui
 * 
 */
public interface ProductsAlbumsDAO {

	/**
	 * 查询所有相册
	 * 
	 * @param id
	 * @return ProductsAlbumsDO
	 * 
	 */
	public ProductsAlbumsDO queryProductsAlbumsById(int id);

	/**
	 * 
	 * @param parentId
	 * @return 有值返回 true 无值返回 false
	 */
	public List<ProductsAlbumsDO> queryProductsAlbumsByParentId(int parentId);

	/**
	 *     添加相册类别
	 * @param productsAlbumsDO
	 * @return   成功：返回影响行数；
	 * 			失败：返回0.
	 * 
	 */
	public int insertProductsAlbums(ProductsAlbumsDO productsAlbumsDO);

	/**
	 *      修改相册类别
	 * @param productsAlbumsDO
	 * @return 成功：返回影响行数；
	 * 			失败：返回0.
	 * 
	 */
	public int updateProductsAlbums(ProductsAlbumsDO productsAlbumsDO);
	 /**
	  *   伪删除相册类别  更新字段is_delete
	  * @param productsAlbumsDO
	  * @return  如果>0 删除成功
	  *          否则失败
	  */
	public Integer updateProductsAlbumsIsDelete(ProductsAlbumsDO productsAlbumsDO);

}
