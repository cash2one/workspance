/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-23
 */
package com.zz91.ep.admin.dao.trade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.trade.DescriptionTemplateDAO;
import com.zz91.ep.domain.trade.DescriptionTemplateDO;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.trade.DescriptionTemplateDTO;
import com.zz91.util.Assert;



/**
 * @author zhouzk
 *
 */
@Component("DescriptionTemplateDAO")
public class DescriptionTemplateDAOImpl extends BaseDao implements DescriptionTemplateDAO{


	final static String SQL_PREFIX="descriptionTemplate";
	
	@SuppressWarnings("unchecked")
	public List<DescriptionTemplateDTO> queryDescriptionTemplateByCondition(
			DescriptionTemplateDTO descriptionTemplateDTO, PageDto<DescriptionTemplateDTO> page) {
		Assert.notNull(descriptionTemplateDTO,"descriptionTemplateDTO  is not null");
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("dto", descriptionTemplateDTO);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryDescriptionTemplateByCondition"),root);
	}

	public Integer queryDescriptionTemplateRecordCount(
			DescriptionTemplateDTO descriptionTemplateDTO) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("dto", descriptionTemplateDTO);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryDescriptionTemplateRecordCount"),root);
	}
	
	final private int DEFAULT_BATCH_SIZE = 20;
	public int batchDeleteDescriptionTemplateById(int[] entities) {
		Assert.notNull(entities, "entities code can not be null");
		int impacted = 0;
		int batchNum = (entities.length + DEFAULT_BATCH_SIZE - 1)
				/ DEFAULT_BATCH_SIZE;
		try {
			for (int currentBatch = 0; currentBatch < batchNum; currentBatch++) {
				getSqlMapClient().startBatch();
				int beginIndex = currentBatch * DEFAULT_BATCH_SIZE;
				int endIndex = (currentBatch + 1) * DEFAULT_BATCH_SIZE;
				endIndex = endIndex > entities.length ? entities.length
						: endIndex;
				for (int i = beginIndex; i < endIndex; i++) {
					impacted += getSqlMapClientTemplate()
							.delete("descriptionTemplate.batchDeleteDescriptionTemplateById",
									entities[i]);
				}
				getSqlMapClient().executeBatch();
			}
		} catch (Exception e) {
			
		}
		return impacted;
	}

	public int insertDescriptionTemplate(
			DescriptionTemplateDO descriptionTemplateDO) {

		return Integer.valueOf(getSqlMapClientTemplate().insert("descriptionTemplate.insertDescriptionTemplate", descriptionTemplateDO).toString());
	}

//	public DescriptionTemplateDO queryTemplateCategoryById(Integer id) {
//		return (DescriptionTemplateDO) getSqlMapClientTemplate().queryForObject("descriptionTemplate.queryTemplateCategoryById",id);
//	}


}
