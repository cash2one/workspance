/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-25
 */
package com.ast.ast1949.service.products.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.products.ProductsAlbumsDO;
import com.ast.ast1949.dto.ExtTreeDto;
import com.ast.ast1949.persist.products.ProductsAlbumsDAO;
import com.ast.ast1949.service.products.ProductsAlbumsService;
import com.ast.ast1949.util.Assert;
import com.ast.ast1949.util.AstConst;

/**
 * @author yuyonghui
 * 
 */
@Component("ProductsAlbumsService")
public class ProductsAlbumsServiceImpl implements ProductsAlbumsService {

	@Autowired
	private ProductsAlbumsDAO productsAlbumsDAO;

	// private final static Integer DEFAULT_PARENT_ID = 0;
	public ProductsAlbumsDO queryProductsAlbumsById(int id) {

		return productsAlbumsDAO.queryProductsAlbumsById(id);
	}

//	public List<ProductsAlbumsDO> queryProductsAlbumsByParentId(int parentId) {
//		Assert.notNull(parentId, "parentId is not null");
//		return productsAlbumsDAO.queryProductsAlbumsByParentId(parentId);
//	}

	public List<ExtTreeDto> child(Integer parentId) {

		if (parentId == null) {
			parentId = 0;
		}
		List<ProductsAlbumsDO> productsAlbumsDOs = productsAlbumsDAO
				.queryProductsAlbumsByParentId(parentId);
		List<ExtTreeDto> treeList = new ArrayList<ExtTreeDto>();
		for (ProductsAlbumsDO pDo : productsAlbumsDOs) {
		 if(String.valueOf(AstConst.IS_DELETE_FALSE).equals(pDo.getIsDelete())){
			ExtTreeDto node = new ExtTreeDto();
			node.setId("node-" + String.valueOf(pDo.getId()));
			node.setLeaf(false);
			node.setText(pDo.getName());
			node.setData(pDo.getId().toString());
			node.setDisabled(false);
			treeList.add(node);
			 }
		}
		return treeList;
	}

	public int insertProductsAlbums(ProductsAlbumsDO productsAlbumsDO) {
		Assert.notNull(productsAlbumsDO, "productsAlbumsDO is not null");
		productsAlbumsDO.setIsDelete(String.valueOf(AstConst.IS_DELETE_FALSE));
		if (productsAlbumsDO.getParentId() == null) {
			productsAlbumsDO.setParentId(0);
		}
		return productsAlbumsDAO.insertProductsAlbums(productsAlbumsDO);
	}

	public int updateProductsAlbums(ProductsAlbumsDO productsAlbumsDO) {
		Assert.notNull(productsAlbumsDO, "productsAlbumsDO is not null");

		if (productsAlbumsDO.getParentId() == null) {
			productsAlbumsDO.setParentId(0);
		}

		return productsAlbumsDAO.updateProductsAlbums(productsAlbumsDO);
	}

	public Integer updateProductsAlbumsIsDelete(ProductsAlbumsDO productsAlbumsDO) {
		Assert.notNull(productsAlbumsDO, "productsAlbumsDO is not null");

		return productsAlbumsDAO.updateProductsAlbumsIsDelete(productsAlbumsDO);
	}

}
