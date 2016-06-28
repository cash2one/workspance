/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-25
 */
package com.ast.ast1949.persist.products.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.products.ProductsAlbumsDO;
import com.ast.ast1949.persist.products.ProductsAlbumsDAO;
import com.ast.ast1949.util.Assert;

/**
 * @author yuyonghui
 *
 */
@Component("ProductsAlbumsDAO")
public class ProductsAlbumsDAOImpl extends SqlMapClientDaoSupport implements ProductsAlbumsDAO{
  
	public ProductsAlbumsDO queryProductsAlbumsById(int id) {
        //  Assert.notNull(id, "id is not null");
		return (ProductsAlbumsDO) getSqlMapClientTemplate().queryForObject("productsAlbums.queryProductsAlbumsById", id);
	}

	@SuppressWarnings("unchecked")
	public List<ProductsAlbumsDO> queryProductsAlbumsByParentId(int parentId) {
		Assert.notNull(parentId, "parentId is not null");
		return getSqlMapClientTemplate().queryForList("productsAlbums.queryProductsAlbumsByParentId", parentId);
	}

	public int insertProductsAlbums(ProductsAlbumsDO productsAlbumsDO) {
        Assert.notNull(productsAlbumsDO, "productsAlbumsDO is not null");
		return Integer.valueOf(getSqlMapClientTemplate().insert("productsAlbums.insertProductsAlbums", productsAlbumsDO).toString());
	}

	public int updateProductsAlbums(ProductsAlbumsDO productsAlbumsDO) {
		Assert.notNull(productsAlbumsDO, "productsAlbumsDO is not null");
		return getSqlMapClientTemplate().update("productsAlbums.updateProductsAlbums", productsAlbumsDO);
	}

	public Integer updateProductsAlbumsIsDelete(ProductsAlbumsDO productsAlbumsDO) {
		Assert.notNull(productsAlbumsDO, "productsAlbumsDO is not null");
		return getSqlMapClientTemplate().update("productsAlbums.updateProductsAlbumsIsDelete", productsAlbumsDO);
	}

}
