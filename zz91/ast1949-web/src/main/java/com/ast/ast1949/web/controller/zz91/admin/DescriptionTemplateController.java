/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-23 by yuyonghui.
 */
package com.ast.ast1949.web.controller.zz91.admin;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.products.DescriptionTemplateDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.products.DescriptionTemplateDTO;
import com.ast.ast1949.service.products.DescriptionTemplateService;
import com.ast.ast1949.util.AstConst;
import com.ast.ast1949.web.controller.BaseController;

/**
 * @author yuyonghui
 * 
 */
@Controller
public class DescriptionTemplateController extends BaseController {

	@Autowired
	private DescriptionTemplateService descriptionTemplateService;

	@RequestMapping
	public void list() {

	}

	@SuppressWarnings("unchecked")
	@RequestMapping
	public ModelAndView query(DescriptionTemplateDO descriptionTemplateDO,
			DescriptionTemplateDTO descriptionTemplateDTO,
			PageDto<DescriptionTemplateDTO> pageDto, Map<String, Object> map)
			throws IOException {

		if (pageDto == null) {
			pageDto = new PageDto(AstConst.PAGE_SIZE);
		}

		descriptionTemplateDTO.setDescriptionTemplateDO(descriptionTemplateDO);
		// descriptionTemplateDTO.setPageDto(pageDto);
		pageDto.setPageSize(50);
		pageDto = descriptionTemplateService
				.pageDescriptionTemplateByCondition(descriptionTemplateDTO,
						pageDto);

		// pageDto.setRecords(descriptionTemplateService.queryDescriptionTemplateByCondition(descriptionTemplateDTO));
		// pageDto.setTotalRecords(descriptionTemplateService.queryDescriptionTemplateRecordCount(descriptionTemplateDTO));
		return printJson(pageDto, map);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping
	public ModelAndView queryList(DescriptionTemplateDO descriptionTemplateDO,
			DescriptionTemplateDTO descriptionTemplateDTO,
			PageDto<DescriptionTemplateDTO> pageDto, Map<String, Object> map)
			throws IOException {

		if (pageDto == null) {
			pageDto = new PageDto(AstConst.PAGE_SIZE);
		}

		descriptionTemplateDTO.setDescriptionTemplateDO(descriptionTemplateDO);
		List<DescriptionTemplateDO> list = descriptionTemplateService
				.queryListDescriptionTemplateByCondition(
						descriptionTemplateDTO, pageDto);
		return printJson(list, map);
	}

	@RequestMapping
	public ModelAndView add(DescriptionTemplateDO descriptionTemplateDO,
			Map<String, Object> map) throws IOException {
		ExtResult result = new ExtResult();

		// descriptionTemplateDO.setTemplateCode(templateCode);
		descriptionTemplateDO.setGmtCreated(new Date());
		int i = descriptionTemplateService
				.insertDescriptionTemplate(descriptionTemplateDO);
		if (i > 0) {
			result.setSuccess(true);
		} else {
			result.setSuccess(false);
		}
		return printJson(result, map);
	}

	@RequestMapping(value = "delete.htm")
	public ModelAndView delete(String ids, Map<String, Object> map)
			throws IOException {

		ExtResult result = new ExtResult();
		String[] entities = ids.split(",");
		int[] i = new int[entities.length];
		for (int ii = 0; ii < entities.length; ii++) {
			i[ii] = Integer.valueOf(entities[ii]);
		}
		int impacted = descriptionTemplateService
				.batchDeleteDescriptionTemplateById(i);
		if (impacted != 1) {
			result.setSuccess(false);
		} else {
			result.setSuccess(true);
		}

		return printJson(result, map);
	}
}
