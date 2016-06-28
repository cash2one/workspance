package com.zz91.ep.admin.service.exhibit.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.exhibit.ExhibitIndustryCategoryDao;
import com.zz91.ep.admin.service.exhibit.ExhibitIndustryCategoryService;
import com.zz91.ep.domain.exhibit.ExhibitIndustryCategory;

@Component("exhibitIndustryCategoryService")
public class ExhibitIndustryCategoryServiceImpl implements ExhibitIndustryCategoryService {
	@Resource
	private ExhibitIndustryCategoryDao exhibitIndustryCategoryDao;
	@Override
	public List<ExhibitIndustryCategory> queryExhibitIndustryCategoryAll() {
		
		return exhibitIndustryCategoryDao.queryExhibitIndustryCategoryAll();
	}

}
