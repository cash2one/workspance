/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-23
 */
package com.ast.ast1949.service.products.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.products.DescriptionTemplateDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.products.DescriptionTemplateDTO;
import com.ast.ast1949.persist.products.DescriptionTemplateDAO;
import com.ast.ast1949.service.products.DescriptionTemplateService;
import com.ast.ast1949.util.Assert;

/**
 * @author yuyonghui
 * 
 */
@Component("DescriptionTemplateService")
public class DescriptionTemplateServiceImpl implements
		DescriptionTemplateService {

	@Autowired
	private DescriptionTemplateDAO descriptionTemplateDAO;

	// public List<DescriptionTemplateDTO> queryDescriptionTemplateByCondition(
	// DescriptionTemplateDTO descriptionTemplateDTO) {
	// Assert.notNull(descriptionTemplateDTO,
	// "descriptionTemplateDTO is not null");
	// return
	// descriptionTemplateDAO.queryDescriptionTemplateByCondition(descriptionTemplateDTO);
	// }
	// public int queryDescriptionTemplateRecordCount(
	// DescriptionTemplateDTO descriptionTemplateDTO) {
	// Assert.notNull(descriptionTemplateDTO,
	// "descriptionTemplateDTO is not null");
	// return
	// descriptionTemplateDAO.queryDescriptionTemplateRecordCount(descriptionTemplateDTO);
	// }
	public int batchDeleteDescriptionTemplateById(int[] entities) {

		return descriptionTemplateDAO
				.batchDeleteDescriptionTemplateById(entities);
	}

	public int insertDescriptionTemplate(
			DescriptionTemplateDO descriptionTemplateDO) {
		Assert.notNull(descriptionTemplateDO,
				"descriptionTemplateDO is not null");
		return descriptionTemplateDAO
				.insertDescriptionTemplate(descriptionTemplateDO);
	}

	// public DescriptionTemplateDO queryTemplateCategoryById(Integer id) {
	// return descriptionTemplateDAO.queryTemplateCategoryById(id);
	// }

	public PageDto<DescriptionTemplateDTO> pageDescriptionTemplateByCondition(
			DescriptionTemplateDTO descriptionTemplateDTO,
			PageDto<DescriptionTemplateDTO> page) {
		page.setTotalRecords(descriptionTemplateDAO
				.queryDescriptionTemplateRecordCount(descriptionTemplateDTO));
		page.setRecords(descriptionTemplateDAO
				.queryDescriptionTemplateByCondition(descriptionTemplateDTO,
						page));
		return page;
	}

	@Override
	public List<DescriptionTemplateDO> queryListDescriptionTemplateByCondition(
			DescriptionTemplateDTO descriptionTemplateDTO,
			PageDto<DescriptionTemplateDTO> page) {
		List<DescriptionTemplateDTO> list = descriptionTemplateDAO.queryDescriptionTemplateByCondition(
				descriptionTemplateDTO, page);
		List<DescriptionTemplateDO> newList = new ArrayList<DescriptionTemplateDO>();
		for(DescriptionTemplateDTO obj :list){
			newList.add(obj.getDescriptionTemplateDO());
		}
		return newList;
	}

}
