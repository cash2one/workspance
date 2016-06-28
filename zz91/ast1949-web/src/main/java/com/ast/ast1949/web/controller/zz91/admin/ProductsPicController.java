/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-26
 */
package com.ast.ast1949.web.controller.zz91.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.products.ProductsPicDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.products.ProductsPicDTO;
import com.ast.ast1949.service.products.ProductsPicService;
import com.ast.ast1949.util.AstConst;
import com.ast.ast1949.util.StringUtils;
import com.ast.ast1949.web.controller.BaseController;
import com.ast.ast1949.web.util.WebConst;
import com.zz91.util.cache.MemcachedUtils;

/**
 * @author yuyonghui
 *
 */
@Controller
public class ProductsPicController extends BaseController{

	@Autowired
	private ProductsPicService productsPicService;

	@RequestMapping
	public void list(Map<String, Object> map) {
		map.put("upload_filetype", AstConst.UPLOAD_FILETYPE_IMG);
		map.put("upload_model", WebConst.UPLOAD_MODEL_PRODUCTS);
		map.put("upload_url", MemcachedUtils.getInstance().getClient().get(
				"baseConfig.resource_url"));
	}

	@RequestMapping
	public ModelAndView query(ProductsPicDTO productsPicDTO,
			ProductsPicDO productsPicDO, PageDto<ProductsPicDO> page, Map<String, Object> map) throws IOException {
		if (page == null) {
			page = new PageDto<ProductsPicDO>(AstConst.PAGE_SIZE);
		}
		productsPicDTO.setPage(page);
		productsPicDTO.setProductsPicDO(productsPicDO);
		page.setTotalRecords(productsPicService.getProductsPicRecordCountByCondition(productsPicDTO));
		page.setRecords(productsPicService.queryProductsPicByCondition(productsPicDTO));

		return printJson(page, map);
	}
	@RequestMapping
	public void view(Integer productId,Map<String, Object> map) throws IOException{

		map.put("productId", productId);
	//	map.put("albumId",albumId);
		map.put("upload_filetype", AstConst.UPLOAD_FILETYPE_IMG);
		map.put("upload_model", WebConst.UPLOAD_MODEL_PRODUCTS);
		map.put("upload_url", MemcachedUtils.getInstance().getClient().get(
				"baseConfig.resource_url"));
	}
	@RequestMapping
	public ModelAndView queryByProductId(Integer productId,ProductsPicDTO productsPicDTO,
			ProductsPicDO productsPicDO,PageDto<ProductsPicDO> page,Map<String, Object> map) throws IOException{
		List<ProductsPicDO> productsPicDOs=productsPicService.queryProductPicInfoByProductsId(productId);
		page.setRecords(productsPicDOs);
		return printJson(page, map);
	}

	@RequestMapping
	public ModelAndView init(Integer id,Map<String, Object> map) throws IOException{

		ProductsPicDTO productsPicDTO=productsPicService.queryProductPicById(id);
		List<ProductsPicDTO> list=new ArrayList<ProductsPicDTO>();
		list.add(productsPicDTO);

		PageDto page=new PageDto();
		page.setRecords(list);
		return printJson(page, map);

	}
	@RequestMapping
	public ModelAndView update(ProductsPicDO productsPicDO,Integer productId,Map<String, Object> map) throws IOException{
		ExtResult result=new ExtResult();
		productsPicDO.setProductId(productId);
		int i=productsPicService.updateProductsPic(productsPicDO);
		if (i>0) {
			result.setSuccess(true);
		}
		return printJson(result, map);
	}
	@RequestMapping
	public ModelAndView add(ProductsPicDO productsPicDO,Map<String, Object> map,Integer productId,Integer albumId) throws IOException{

		ExtResult result=new ExtResult();

		productsPicDO.setProductId(productId);
		int i=productsPicService.insertProductsPic(productsPicDO);
		if(i>0){
			result.setSuccess(true);
		}
		return printJson(result, map);
	}
	@RequestMapping
	public ModelAndView delete(String ids,Map<String, Object> map) throws IOException{
		ExtResult result = new ExtResult();
		String[] entities = ids.split(",");
		Integer[] i = new Integer[entities.length];
		for (int ii = 0; ii < entities.length; ii++) {
			i[ii] = Integer.valueOf(entities[ii]);
		}
		int impacted = productsPicService.batchDeleteProductPicbyId(StringUtils.StringToIntegerArray(ids));
		if (impacted != 1) {
			result.setSuccess(true);
		} else {
			result.setSuccess(false);
		}
		return printJson(result, map);
	}

}
