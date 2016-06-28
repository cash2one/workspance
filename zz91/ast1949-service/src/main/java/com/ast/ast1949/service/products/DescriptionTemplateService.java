/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-23
 */
package com.ast.ast1949.service.products;

import java.util.List;

import com.ast.ast1949.domain.products.DescriptionTemplateDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.products.DescriptionTemplateDTO;

/**
 * @author yuyonghui
 *
 */
public interface DescriptionTemplateService {

	/**
	 * 查询所有描述模板
	 * @param descriptionTemplateDTO
	 * @return   返回记录，没有记录返回空
	 * @throws IllegalArgumentException
	 *
	 */
//	public List<DescriptionTemplateDTO> queryDescriptionTemplateByCondition(DescriptionTemplateDTO descriptionTemplateDTO);

	/**
	 *   查询记录描述模板总数
	 * @param descriptionTemplateDTO
	 * @return   返回记录总数，没有记录返回0
	 * @throws IllegalArgumentException
	 *
	 */
//	public int queryDescriptionTemplateRecordCount(DescriptionTemplateDTO descriptionTemplateDTO);

	public PageDto<DescriptionTemplateDTO> pageDescriptionTemplateByCondition(DescriptionTemplateDTO descriptionTemplateDTO, PageDto<DescriptionTemplateDTO> page);
	
	/**
	 *    添加描述模板
	 * @param descriptionTemplateDO
	 * @return
	 *
	 */
	public int insertDescriptionTemplate(DescriptionTemplateDO descriptionTemplateDO);

//	/**
//	 *    查询模板类别
//	 * @param id
//	 * @return  DescriptionTemplateDO
//	 *
//	 */
//	public DescriptionTemplateDO queryTemplateCategoryById(Integer id);
	/**
	 *  批量删除 描述模板
	 * @param entities
	 * @return
	 *
	 */
	public int batchDeleteDescriptionTemplateById(int[] entities) ;
	
	public List<DescriptionTemplateDO> queryListDescriptionTemplateByCondition(DescriptionTemplateDTO descriptionTemplateDTO,PageDto<DescriptionTemplateDTO> page);
	
}
