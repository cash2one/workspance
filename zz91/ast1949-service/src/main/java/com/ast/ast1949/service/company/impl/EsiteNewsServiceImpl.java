/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-2-18
 */
package com.ast.ast1949.service.company.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.EsiteNewsDo;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.company.EsiteNewsDao;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.company.EsiteNewsService;
import com.ast.ast1949.util.Assert;
import com.zz91.util.lang.StringUtils;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-2-18
 */
@Component("EsiteNewsService")
public class EsiteNewsServiceImpl implements EsiteNewsService {
	
	@Autowired
	EsiteNewsDao esiteNewsDao;
	@Resource
	private CompanyService companyService;

	@Override
	public Integer deleteNewsByIdAndCompany(Integer id, Integer companyId) {
		Assert.notNull(id, "the id can not be null");
		Assert.notNull(companyId, "the companyId can not be null");
		return esiteNewsDao.deleteNewsByCompany(id, companyId);
	}

	@Override
	public Integer insertNews(EsiteNewsDo news) {
		if (StringUtils.isEmpty(news.getTitle())) {
			return 0;
		}
		if (StringUtils.isEmpty(news.getContent())) {
			return 0;
		}
		return esiteNewsDao.insertNews(news);
	}

	@Override
	public PageDto<EsiteNewsDo> pageNewsByCompany(Integer companyId,
			PageDto<EsiteNewsDo> page) {
		Assert.notNull(companyId, "the companyId can not be null");
		Assert.notNull(page, "the object page can not be null");
		page.setTotalRecords(esiteNewsDao.queryNewsByCompanyCount(companyId));
		page.setRecords(esiteNewsDao.queryNewsByCompany(companyId, page));
		return page;
	}

	@Override
	public Integer updateNewsById(EsiteNewsDo news) {
		Assert.notNull(news, "the object news can not be null");
		return esiteNewsDao.updateNewsById(news);
	}

	@Override
	public EsiteNewsDo queryOneNewsById(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return esiteNewsDao.queryOneNewsById(id);
	}

	@Override
	public EsiteNewsDo queryLastNewsById(Integer id,Integer companyId){
		Assert.notNull(id, "the id can not be null");
		Assert.notNull(companyId, "the companyId can not be null");
		return esiteNewsDao.queryLastNewsById(id,companyId);
	}

	@Override
	public EsiteNewsDo queryNextNewsById(Integer id,Integer companyId){
		Assert.notNull(id, "the id can not be null");
		Assert.notNull(companyId, "the companyId can not be null");
		return esiteNewsDao.queryNextNewsById(id,companyId);
	}

	@Override
	public List<EsiteNewsDo> queryList(Integer size) {
		if (size ==null) {
			size = 10;
		}
		if(size>100){
			size =100;
		}
		List<EsiteNewsDo> list = esiteNewsDao.queryList(size*10);
		List<EsiteNewsDo> nlist = new ArrayList<EsiteNewsDo>();
		Set<Integer> set = new HashSet<Integer>();
		for (EsiteNewsDo obj :list) {
			if(!set.contains(obj.getCompanyId())){
				if(obj.getCompanyId()!=null){
					obj.setDomain(companyService.queryDomainOfCompany(obj.getCompanyId()).getDomainZz91());
				}
				nlist.add(obj);
				set.add(obj.getCompanyId());
			}
			if(nlist.size()>=size){
				break;
			}
		}
		return nlist;
	}

	@Override
	public List<EsiteNewsDo> querybyCompanyAll(Integer companyId) {
		return esiteNewsDao.querybyCompanyAll(companyId);
	}

}
