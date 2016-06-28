package com.ast.ast1949.web.controller.zz91.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.company.CategoryGardenDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CategoryGardenDTO;
import com.ast.ast1949.service.company.CategoryGardenService;
import com.ast.ast1949.util.AstConst;
import com.ast.ast1949.web.controller.BaseController;

@Controller
public class CategoryGardenController extends BaseController {
	@Autowired
	private CategoryGardenService categoryGardenService;

	/**
	 * 根据ID获取一条记录
	 * 
	 * @param req
	 * @param res
	 * @throws IOException
	 */
	@RequestMapping(value = "init.htm")
	public ModelAndView init(Integer id, Map<String, Object> model)
			throws IOException {
		CategoryGardenDO categoryGardenDO = categoryGardenService
				.queryCategoryGardenById(id);
		List<CategoryGardenDO> list = new ArrayList<CategoryGardenDO>();
		list.add(categoryGardenDO);
		PageDto page = new PageDto();
		page.setRecords(list);
		return printJson(page, model);
	}

	@RequestMapping(value = "list.htm")
	public void CategoryGardenlist() {

	}

	@RequestMapping(value = "query.htm")
	public ModelAndView query(CategoryGardenDTO dto, PageDto page,
			Map<String, Object> model) throws IOException {
		if (page == null) {
			page = new PageDto(AstConst.PAGE_SIZE);
		}
		dto.setPageDto(page);
		page.setTotalRecords(categoryGardenService
				.getCategoryGardenRecordCountByCondition(dto));
		page.setRecords(categoryGardenService
				.queryCategoryGardenByCondition(dto));
		return printJson(page, model);
	}

	@RequestMapping(value = "add.htm")
	public ModelAndView add(CategoryGardenDO categoryGardenDO,
			Map<String, Object> model) throws IOException {

		ExtResult result = new ExtResult();

		categoryGardenDO.setGmtCreated(new Date());
		categoryGardenDO.setGmtModified(new Date());
		int i = categoryGardenService.insertCategoryGrden(categoryGardenDO);
		if (i > 0) {
			result.setSuccess(true);
		}
		return printJson(result, model);
	}

	@RequestMapping(value = "update.htm")
	public ModelAndView update(CategoryGardenDO categoryGardenDO,
			Map<String, Object> model) throws IOException {
		ExtResult result = new ExtResult();
		categoryGardenDO.setGmtModified(new Date());
		int i = categoryGardenService.updateCategoryGrden(categoryGardenDO);
		if (i > 0) {
			result.setSuccess(true);
		}

		return printJson(result, model);
	}

	@RequestMapping(value = "delete.htm")
	public ModelAndView delete(String ids, Map<String, Object> model)
			throws IOException {

		ExtResult result = new ExtResult();

		String[] entities = ids.split(",");

		int[] i = new int[entities.length];
		for (int ii = 0; ii < entities.length; ii++) {
			i[ii] = Integer.valueOf(entities[ii]);
		}

		int impacted = categoryGardenService.batchDeleteCategoryGrdenById(i);
		if (impacted == entities.length) {
			result.setSuccess(true);
		}

		result.setData(impacted);

		return printJson(result, model);
	}
	
	/**
	 * 根据industryCode，areaCode，gardenTypeCode查询园区类别
	 */
	@RequestMapping
	public ModelAndView queryBySomeCode(CategoryGardenDO categoryGardenDO, Map<String, Object> model) 
			throws IOException{
		//ext没办法判断，故在此判断，若cardenTypeCode为中文设为null
		List<CategoryGardenDO> list = new ArrayList<CategoryGardenDO>();
		if(categoryGardenDO!=null) {
			if(categoryGardenDO.getGardenTypeCode()!=null && categoryGardenDO.getGardenTypeCode().matches("[\\u4e00-\\u9fa5]+")){
				categoryGardenDO.setGardenTypeCode(null);
			}
			list = categoryGardenService.queryCategoryGardenBySomeCode(categoryGardenDO);
		}
		return printJson(list,model);
	}
	
}
