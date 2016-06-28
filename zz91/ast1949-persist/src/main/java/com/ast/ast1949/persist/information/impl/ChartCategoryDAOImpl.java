/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-25 by Rolyer.
 */
package com.ast.ast1949.persist.information.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.information.ChartCategoryDO;
import com.ast.ast1949.dto.information.ChartCategoryDTO;
import com.ast.ast1949.persist.information.ChartCategoryDAO;
import com.ast.ast1949.util.Assert;

/**
 *
 * @author Rolyer(rolyer.live@gmail.com)
 *
 */
@Component("chartCategoryDAO")
public class ChartCategoryDAOImpl extends SqlMapClientDaoSupport implements ChartCategoryDAO {

	public Integer deleteChartCategoryById(Integer id) {
		Assert.notNull(id, "the id must not be null");
		return getSqlMapClientTemplate().delete("chartCategory.deleteChartCategoryById", id);
	}

	public Integer insertChartCategory(ChartCategoryDO chartCategory) {
		Assert.notNull(chartCategory, "the object of chartCategory must not be null");
		return (Integer) getSqlMapClientTemplate().insert("chartCategory.insertChartCategory", chartCategory);
	}

	public ChartCategoryDO queryChartCategoryById(Integer id) {
		Assert.notNull(id, "the id must not be null");
		return (ChartCategoryDO) getSqlMapClientTemplate().queryForObject("chartCategory.queryChartCategoryById", id);
	}

	@SuppressWarnings("unchecked")
	public List<ChartCategoryDO> queryChartCategoryByParentId(Integer id) {
		Assert.notNull(id, "the id must not be null");
		return getSqlMapClientTemplate().queryForList("chartCategory.queryChartCategoryByParentId", id);
	}

	public Integer updateChartCategoryById(ChartCategoryDO chartCategory) {
		Assert.notNull(chartCategory, "the object of chartCategory must not be null");
		return getSqlMapClientTemplate().update("chartCategory.updateChartCategoryById", chartCategory);
	}

//	public Integer countChartCategoryListByParentId(
//			ChartCategoryDTO chartCategoryDTO) {
//		return (Integer) getSqlMapClientTemplate().queryForObject("chartCategory.countChartCategoryListByParentId", chartCategoryDTO);
//	}

	@SuppressWarnings("unchecked")
	public List<ChartCategoryDTO> queryChartCategoryListByParentId(
			ChartCategoryDTO chartCategoryDTO) {
		return getSqlMapClientTemplate().queryForList("chartCategory.queryChartCategoryListByParentId", chartCategoryDTO);
	}

	public ChartCategoryDTO queryChartCategoryDtoById(Integer id) {
		Assert.notNull(id, "the id must not be null");
		return (ChartCategoryDTO) getSqlMapClientTemplate().queryForObject("chartCategory.queryChartCategoryDtoById", id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChartCategoryDO> queryChartCategoryCanShowInHome(Integer max) {
		Assert.notNull(max, "the max must not be null");
		return getSqlMapClientTemplate().queryForList("chartCategory.queryChartCategoryCanShowInHome", max);
	}
}
