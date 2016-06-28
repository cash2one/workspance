/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-23
 */
package com.ast.ast1949.persist.products.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.products.DescriptionTemplateDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.products.DescriptionTemplateDTO;
import com.ast.ast1949.exception.PersistLayerException;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.products.DescriptionTemplateDAO;
import com.ast.ast1949.util.Assert;

/**
 * @author yuyonghui
 *
 */
@Component("DescriptionTemplateDAO")
public class DescriptionTemplateDAOImpl extends BaseDaoSupport implements DescriptionTemplateDAO{


	final static String SQL_PREFIX="descriptionTemplate";
	
	@SuppressWarnings("unchecked")
	public List<DescriptionTemplateDTO> queryDescriptionTemplateByCondition(
			DescriptionTemplateDTO descriptionTemplateDTO, PageDto<DescriptionTemplateDTO> page) {
		Assert.notNull(descriptionTemplateDTO,"descriptionTemplateDTO  is not null");
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("dto", descriptionTemplateDTO);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryDescriptionTemplateByCondition"),root);
	}

	public Integer queryDescriptionTemplateRecordCount(
			DescriptionTemplateDTO descriptionTemplateDTO) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("dto", descriptionTemplateDTO);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryDescriptionTemplateRecordCount"),root);
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
			throw new PersistLayerException("batch delete user failed.", e);
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
