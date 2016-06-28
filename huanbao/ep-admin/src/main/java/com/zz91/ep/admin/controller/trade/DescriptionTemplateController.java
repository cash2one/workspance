/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-23 by yuyonghui.
 */
package com.zz91.ep.admin.controller.trade;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.admin.controller.BaseController;
import com.zz91.ep.admin.service.sys.ParamTypeService;
import com.zz91.ep.admin.service.trade.DescriptionTemplateService;
import com.zz91.ep.domain.trade.DescriptionTemplateDO;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.trade.DescriptionTemplateDTO;
import com.zz91.ep.utils.AstConst;
import com.zz91.util.domain.ParamType;



/**
 * @author zhouzk
 * 
 */
@Controller
public class DescriptionTemplateController extends BaseController {

	@Resource
	private DescriptionTemplateService descriptionTemplateService;
	@Resource
	private ParamTypeService paramTypeService;
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

		pageDto = descriptionTemplateService.pageDescriptionTemplateByCondition(descriptionTemplateDTO,
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
