/*
 * 文件名称：ExhibitIndustryCategoryServiceImpl.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.service.exhibit.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zz91.ep.dao.exhibit.ExhibitIndustryCategoryDao;
import com.zz91.ep.domain.exhibit.ExhibitIndustryCategory;
import com.zz91.ep.service.exhibit.ExhibitIndustryCategoryService;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作Service层
 * 模块描述：展会信息Service实现类。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Service("exhibitIndustryCategoryService")
public class ExhibitIndustryCategoryServiceImpl implements ExhibitIndustryCategoryService {
	
	@Resource
	private ExhibitIndustryCategoryDao exhibitIndustryCategoryDao;

	@Override
	public List<ExhibitIndustryCategory> queryExhibitIndustryCategoryAll() {
		return exhibitIndustryCategoryDao.queryExhibitIndustryCategoryAll();
	}

}