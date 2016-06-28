/**
 * Copyright 2013 ASTO.
 * All right reserved.
 * Created on 2013-12-19
 */
package com.zz91.ep.admin.service.trade.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.trade.DescriptionTemplateDAO;
import com.zz91.ep.admin.service.trade.DescriptionTemplateService;
import com.zz91.ep.domain.trade.DescriptionTemplateDO;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.trade.DescriptionTemplateDTO;
import com.zz91.util.Assert;



/**
 * @author zhouzk
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
		page.setTotals(descriptionTemplateDAO.queryDescriptionTemplateRecordCount(descriptionTemplateDTO));
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
