/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-25
 */
package com.ast.ast1949.service.products;

import java.util.List;

import com.ast.ast1949.domain.products.ProductsAlbumsDO;
import com.ast.ast1949.dto.ExtTreeDto;

/**
 * @author yuyonghui
 *
 */
public interface ProductsAlbumsService {
	/**
	 *   查询所有相册  
	 * @param id
	 * @return ProductsAlbumsDO
	 *    
	 */
public ProductsAlbumsDO queryProductsAlbumsById(int id);

    /**
     *  
     * @param parentId
     * @return  有值返回 true 
     *          无值返回 false 
     */
//public List<ProductsAlbumsDO> queryProductsAlbumsByParentId(int parentId);

/**
 * 获取某个类别下的子类别,并且返回Ext tree需要的数据格式
 * @param parentId 父结点编号,如果为空,则转换成默认根节点编号
 * @return ext 树结点
 *		ExtTreeDto.id对应ProductsAlbumsDO.id
 *		ExtTreeDto.text对应ProductsAlbumsDOname
 *		ExtTreeDto.data对应ProductsAlbumsDO.data
 *		ExtTreeDto.leaf = false 表示仍有子节点,true表示无子节点
 */
public List<ExtTreeDto> child(Integer parentId);

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
