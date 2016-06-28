/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-23
 */
package com.zz91.ep.admin.dao.trade;

import java.util.List;

import com.zz91.ep.domain.trade.DescriptionTemplateDO;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.trade.DescriptionTemplateDTO;



/**
 * @author zhouzk
 *
 */
public interface DescriptionTemplateDAO {

	/**
	 * 查询所有描述模板
	 * @param descriptionTemplateDTO
	 * @return   返回记录，没有记录返回空
	 * @throws IllegalArgumentException
	 *
	 */
	public List<DescriptionTemplateDTO> queryDescriptionTemplateByCondition(DescriptionTemplateDTO descriptionTemplateDTO, PageDto<DescriptionTemplateDTO> page);

	/**
	 *   查询记录描述模板总数
	 * @param descriptionTemplateDTO
	 * @return   返回记录总数，没有记录返回0
	 * @throws IllegalArgumentException
	 *
	 */
	public Integer queryDescriptionTemplateRecordCount(DescriptionTemplateDTO descriptionTemplateDTO);

	/**
	 *    添加描述模板
	 * @param descriptionTemplateDO
	 * @return 添加成功  返回DescriptionTemplateDO TRUE
	 *         失败           返回false
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
}
