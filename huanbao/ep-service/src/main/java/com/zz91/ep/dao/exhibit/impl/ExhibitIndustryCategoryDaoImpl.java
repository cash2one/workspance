/*
 * 文件名称：ExhibitIndustryCategoryDaoImpl.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.dao.exhibit.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zz91.ep.dao.common.impl.BaseDao;
import com.zz91.ep.dao.exhibit.ExhibitIndustryCategoryDao;
import com.zz91.ep.domain.exhibit.ExhibitIndustryCategory;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作DAO层
 * 模块描述：展会行业类别信息相关数据操作
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Repository("exhibitIndustryCategoryDao")
public class ExhibitIndustryCategoryDaoImpl extends BaseDao implements ExhibitIndustryCategoryDao {

	final static String SQL_PREFIX = "exhibitindustrycategory";

	@SuppressWarnings("unchecked")
	@Override
	public List<ExhibitIndustryCategory> queryExhibitIndustryCategoryAll() {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryExhibitIndustryCategoryAll"));
	}

	@Override
	public ExhibitIndustryCategory queryExhibitIndustryCategoryById(String code) {
		
		return (ExhibitIndustryCategory)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryExhibitIndustryCategoryById"),code);
	}
	
}