package com.zz91.ep.admin.dao.exhibit.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.exhibit.ExhibitIndustryCategoryDao;
import com.zz91.ep.domain.exhibit.ExhibitIndustryCategory;
@Component("exhibitIndustryCategoryDao")
public class ExhibitIndustryCategoryDaoImpl extends BaseDao implements ExhibitIndustryCategoryDao {
	
	final static String SQL_PREFIX = "industry";

	@SuppressWarnings("unchecked")
	@Override
	public List<ExhibitIndustryCategory> queryExhibitIndustryCategoryAll() {
		
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryExhibitIndustryCategoryAll"));
	}
}
