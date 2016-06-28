/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-25
 */
package com.ast.ast1949.web.controller.zz91.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.products.ProductsAlbumsDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.ExtTreeDto;
import com.ast.ast1949.service.products.ProductsAlbumsService;
import com.ast.ast1949.util.AstConst;
import com.ast.ast1949.web.controller.BaseController;

/**
 * @author yuyonghui
 * 
 */
@Controller
public class ProductsAlbumsController extends BaseController {

	@Autowired
	private ProductsAlbumsService productsAlbumsService;

	@RequestMapping
	public void view() {
	}

	@RequestMapping
	public ModelAndView child(Integer id, Map<String, Object> out) throws IOException {
		if (id == null) {
			id = 0;
		} else if (id.equals("0")) {
			id = 0;
		}
		List<ExtTreeDto> list = productsAlbumsService.child(id);
		return printJson(list, out);
	}

	@RequestMapping(value = "init.htm")
	public ModelAndView init(Integer id, Map<String, Object> out) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (id != null && id.intValue() > 0) {
			ProductsAlbumsDO productsAlbumsDO = productsAlbumsService.queryProductsAlbumsById(id);
			List<ProductsAlbumsDO> list = new ArrayList<ProductsAlbumsDO>();
			list.add(productsAlbumsDO);
			map.put("records", list);
		}
		return printJson(map, out);

	}

	@RequestMapping(value = "edit.htm", method = RequestMethod.POST)
	public ModelAndView edit(ProductsAlbumsDO productsAlbumsDO, Map<String, Object> map)
			throws IOException {

		ExtResult result = new ExtResult();
		int impacted = 0;
		if (productsAlbumsDO.getId() == null || productsAlbumsDO.getId() <= 0) {
			impacted = productsAlbumsService.insertProductsAlbums(productsAlbumsDO);
		} else {
			impacted = productsAlbumsService.updateProductsAlbums(productsAlbumsDO);
		}
		if (impacted > 0) {
			result.setSuccess(true);
		} else {
			result.setSuccess(false);
		}
		result.setData(impacted);
		return printJson(result, map);

	}

	@RequestMapping
	public ModelAndView delete(Integer id, Map<String, Object> map) throws IOException {
	    ProductsAlbumsDO productsAlbumsDO=productsAlbumsService.queryProductsAlbumsById(id);
		ExtResult result = new ExtResult();
		productsAlbumsDO.setIsDelete(String.valueOf(AstConst.IS_DELETE_TRUE));
	    Integer i=productsAlbumsService.updateProductsAlbumsIsDelete(productsAlbumsDO);
		if (i > 0) {
			result.setSuccess(true);
		} else {
			result.setSuccess(false);
		}
		return printJson(result, map);
	}
}
