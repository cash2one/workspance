/*
 * 文件名称：IbdCategoryServiceImpl.java
 * 创建者　：涂灵峰
 * 创建时间：2012-6-26 下午5:44:57
 * 版本号　：1.0.0
 */
package com.zz91.ep.admin.service.trade.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zz91.ep.admin.dao.trade.IbdCompanyDao;
import com.zz91.ep.admin.service.trade.IbdCompanyService;
import com.zz91.ep.domain.trade.IbdCompany;
import com.zz91.ep.dto.PageDto;

/**
 * 项目名称：中国环保网
 * 模块编号：业务逻辑Service层
 * 模块描述：行业买家库类别
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-06-26　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Service("ibdCompanyService")
public class IbdCompanyServiceImpl implements IbdCompanyService {

	@Resource 
	private IbdCompanyDao ibdCompanyDao;
	
//	@Override
//	public Integer queryCountByCategoryCode(String categoryCode) {
//		return ibdCompanyDao.queryCountByCategoryCode(categoryCode);
//	}

//	@Override
//	public PageDto<IbdCompany> pageCompanyByCategoryAndKewords(String categoryCode, String keywords, PageDto<IbdCompany> page) {
//		page.setLimit(10);
//		page.setRecords(ibdCompanyDao.queryCompanyByCategoryAndKewords(categoryCode, keywords, page));
//		page.setTotals(ibdCompanyDao.queryCompanyByCategoryAndKewordsCount(categoryCode, keywords));
//		return page;
//	}

//	@Override
//	public IbdCompany queryIbdCompanyById(Integer id) {
//		return ibdCompanyDao.queryIbdCompanyById(id);
//	}

//	@Override
//	public IbdCompany queryContactByCid(Integer id) {
//		return ibdCompanyDao.queryContactByCid(id);
//	}

	@Override
	public Integer createIbdCompanyByAdmin(IbdCompany comp) {
		if (comp.getDelStatus()==null){
			comp.setDelStatus(1);
		}
		return ibdCompanyDao.insertIbdCompanyByAdmin(comp);
	}

}