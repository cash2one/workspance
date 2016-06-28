/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-1-25
 */
package com.ast.ast1949.web.controller.zz91.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.price.PriceCategoryDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.ExtTreeDto;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.price.PriceCategoryDTO;
import com.ast.ast1949.service.price.PriceCategoryService;
import com.ast.ast1949.web.controller.BaseController;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */

@Controller
public class PriceCategoryController extends BaseController{
	@Autowired
	private PriceCategoryService priceCategoryService;
//	@Autowired
//	private PriceCategoryLinkService priceCategoryLinkService;

	@RequestMapping
	public void view(){

	}

	@RequestMapping(value="child.htm")
	public ModelAndView child(@RequestParam(required = false) Integer id, Map<String, Object> model) throws IOException{
		if(id == null){
			id = 0;
		}else if (id.equals("0")) {
			id = 0;
		}
		List<ExtTreeDto> list = priceCategoryService.queryExtTreeChildNodeByParentId(id);

		return printJson(list, model);
	}

	@RequestMapping(value="init.htm")
	public ModelAndView init(Integer id, Map<String, Object> model) throws IOException {
		PageDto pageDto=new PageDto();
		List<PriceCategoryDTO> list = new ArrayList<PriceCategoryDTO>();
		if(id!=null && id.intValue()>0){
			list.add(priceCategoryService.queryPriceCategoryDtoById(id));
		}else{
			list.add(new PriceCategoryDTO());
		}
		pageDto.setRecords(list);

		return printJson(pageDto, model);
	}
	
	/**
	 * 获取关联类别
	 * @param model
	 * @return
	 */
//	@RequestMapping
//	public ModelAndView query(Map<String, Object> model,Integer id) throws IOException{
//		PageDto page =new PageDto();
//		page.setRecords(priceCategoryService.queryAssistPriceCategoryByTypeId(id));
//		
//		return printJson(page, model);
//	}

	@RequestMapping(value="edit.htm",method = RequestMethod.POST)
	public ModelAndView edit(PriceCategoryDO newsCategory, Map<String, Object> model) throws IOException{

		ExtResult result = new ExtResult();
		if(newsCategory.getId()!=null && newsCategory.getId()>0){
			//更新操作
			if(priceCategoryService.updatePriceCategoryById(newsCategory)>0){
				result.setSuccess(true);
			}
		}else{
			//新增操作
			int i=priceCategoryService.insertPriceCategory(newsCategory);
			if(i>0){
				result.setSuccess(true);
			}
		}

		return printJson(result, model);
	}
	
	/**
	 * 删除类别
	 * @param id
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView delete(Integer id,Map<String, Object> model) throws IOException {
		ExtResult result =new ExtResult();
		
		if(priceCategoryService.deletePriceCategoryById(id)>0){
			result.setSuccess(true);
		}
		
		return printJson(result, model);
	}
	
	/**
	 * 添加关联
	 * @param model
	 * @param priceCategoryLink
	 * @return
	 * @throws IOException
	 */
//	@RequestMapping
//	public ModelAndView addRelated(Map<String, Object> model, Integer priceTypeId,String ids) throws IOException {
//		ExtResult result=new ExtResult();
//		
//		String[] id=ids.split(",");
//		int impacted=0;
//		PriceCategoryLinkDO priceCategoryLink=new PriceCategoryLinkDO();
//		//删除老数据
//		priceCategoryLinkService.deletePriceCategoryLinkByPriceTypeId(priceTypeId);
//		
//		for (int i = 0; i < id.length; i++) {
//			if(StringUtils.isNumber(id[i])){
//				priceCategoryLink.setPriceTypeId(priceTypeId);
//				priceCategoryLink.setPriceAssistTypeId(Integer.parseInt(id[i]));
//				Boolean b = priceCategoryLinkService.insert(priceCategoryLink);
//				if(b){
//					impacted++;
//				}
//			} else {
//				impacted++;
//			}
//		}
//		
//		if(impacted==id.length){
//			result.setSuccess(true);
//		} else {
//			result.setSuccess(false);
//		}
//		
//		return printJson(result, model);
//	}
	
	/**
	 * 删除关联
	 * @param model
	 * @param id
	 * @return
	 * @throws IOException 
	 */
//	@RequestMapping
//	public ModelAndView deleteRelated(Map<String, Object> model, String ids) throws IOException {
//		ExtResult result=new ExtResult();
//		
//		String[] id=ids.split(",");
//		int impacted=0;
//		for (int i = 0; i < id.length; i++) {
//			if(StringUtils.isNumber(id[i])){
//				Integer im = priceCategoryLinkService.deletePriceCategoryLinkById(Integer.parseInt(id[i]));
//				if(im.intValue()>0){
//					impacted++;
//				}
//			}
//		}
//		
//		if(impacted==id.length){
//			result.setSuccess(true);
//		} else {
//			result.setSuccess(false);
//		}
//		
//		return printJson(result, model);
//	}
	
	/**
	 * 根据父节点编号查询类别列表
	 * @param model
	 * @param page
	 * @param priceCategoryDO
	 * @param priceCategoryDto
	 * @return
	 * @throws IOException
	 */
//	@RequestMapping
//	public ModelAndView queryAssit(Map<String, Object> model, PageDto page,
//			PriceCategoryDO priceCategoryDO, PriceCategoryDTO priceCategoryDto) throws IOException {
//		// 设置分页条件
//		if (page == null) {
//			page = new PageDto(AstConst.PAGE_SIZE);
//			page.setSort("id");
//			page.setDir("desc");
//		} else {
//			if (page.getSort() == null) {
//				page.setSort("id");
//			}
//			if (page.getDir() == null) {
//				page.setDir("desc");
//			}
//		}
//
//		priceCategoryDto.setPriceCategoryDO(priceCategoryDO);
//		priceCategoryDto.setPage(page);
//		
//		page.setTotalRecords(priceCategoryService.countPriceCategoryByCondition(priceCategoryDto));
//		page.setRecords(priceCategoryService.queryPriceCategoryByCondition(priceCategoryDto));
//
//		return printJson(page, model);
//	}
	
	/**
	 * 报价类别复选框(CheckBox)的树。
	 * @param parentId
	 * @param ids
	 * @param model
	 * @return
	 * @throws IOException
	 */
//	@RequestMapping
//	public ModelAndView children(Integer parentId, String ids, Map<String, Object> model) throws IOException {
////		if(parentId==null){
////			parentId=2;
////		}else if (parentId.equals("0")) {
////			parentId=2;
////		}
//		List<ExtCheckBoxTreeDto> list = priceCategoryService.queryExtCheckBoxTreeChildNodeByParentId(2,ids);
//
//		return printJson(list, model);
//	}
	
	/**
	 * 
	 * @param model
	 * @param id
	 * @return
	 * @throws IOException
	 */
//	@RequestMapping
//	public ModelAndView queryAssistCategoryId(Map<String, Object> model,Integer id) throws IOException{
//		PageDto page =new PageDto();
//		page.setRecords(priceCategoryService.queryAssistPriceCategoryIdByTypeId(id));
//		
//		return printJson(page, model);
//	}
	
}
