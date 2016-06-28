/*
 * 文件名称：IbdCategoryServiceImpl.java
 * 创建者　：涂灵峰
 * 创建时间：2012-6-26 下午5:44:57
 * 版本号　：1.0.0
 */
package com.zz91.ep.service.trade.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zz91.ep.dao.trade.IbdCategoryDao;
import com.zz91.ep.domain.trade.IbdCategory;
import com.zz91.ep.service.trade.IbdCategoryService;

/**
 * 项目名称：中国环保网
 * 模块编号：业务逻辑Service层
 * 模块描述：行业买家库类别
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-06-26　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Service("ibdCategoryService")
public class IbdCategoryServiceImpl implements IbdCategoryService {

	@Resource
	private IbdCategoryDao ibdCategoryDao;
	
	@Override
	public List<IbdCategory> queryCategoryByParentCode(String categoryCode) {
		return ibdCategoryDao.queryCategoryByParentCode(categoryCode);
	}

}
