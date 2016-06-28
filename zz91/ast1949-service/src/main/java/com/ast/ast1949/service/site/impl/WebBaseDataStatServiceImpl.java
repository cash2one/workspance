/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-7
 */
package com.ast.ast1949.service.site.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.site.WebBaseDataStatDo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.products.ProductsDAO;
import com.ast.ast1949.persist.site.WebBaseDataStatDao;
import com.ast.ast1949.service.site.WebBaseDataStatService;
import com.ast.ast1949.util.DateUtil;
import com.zz91.util.Assert;
import com.zz91.util.cache.MemcachedUtils;
import com.zz91.util.lang.NumberUtil;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-4-7
 */
@Component("webBaseDataStatService")
public class WebBaseDataStatServiceImpl implements WebBaseDataStatService {

	@Autowired
	private WebBaseDataStatDao webBaseDataStatDao;
	@Resource
	private ProductsDAO productsDAO;
	@Resource
	private CompanyDAO companyDAO;
	
	private static Integer WEEKLY_PUBLISH=50000; 
	
	private static Integer TOTAL_COMPANY=100000;
	
	private static Integer TOTAL_MEMBER = 2000;
	
	private static Integer PRODUCT_COUNT = 5000000;
	
	private static Integer AN_DAY_SECONDS= 86400;
	@Override
	public Map<String, Integer> queryDataByDate(Date d) {
		List<WebBaseDataStatDo> list=webBaseDataStatDao.queryDataByDate(d);
		Map<String, Integer> map=new HashMap<String, Integer>();
		if(list!=null){
			for(WebBaseDataStatDo obj:list){
				map.put(obj.getStatCate(), obj.getStatCount());
			}
		}
		return map;
	}

	@Override
	public WebBaseDataStatDo queryTodayDataByCate(String cate) {
		return webBaseDataStatDao.queryDataByCate(cate, new Date());
	}

	@Override
	public PageDto<WebBaseDataStatDo> pageWebBaseDataStat(PageDto<WebBaseDataStatDo> page,String statCate,Date gmtStatDate) {
		Assert.notNull(page, "the page can not be null");
		page.setRecords(webBaseDataStatDao.queryWebBaseDataStat(statCate, gmtStatDate,page));
		page.setTotalRecords(webBaseDataStatDao.queryWebBaseDataStatCount(statCate, gmtStatDate));
		return page;
	}

	@Override
	public void indexTotal(Map<String,Object>out) {
		double db;
		//一周新增信息
		if(MemcachedUtils.getInstance().getClient().get("front_index_weekly_publish")==null){
			Map<String,Object> map = new HashMap<String,Object>();
			try {
				map.put("gmtStart", DateUtil.getDateAfterDays(DateUtil.getDate(new Date(), "yyyy-MM-dd"), -8));
				map.put("gmtEnd", DateUtil.getDateAfterDays(DateUtil.getDate(new Date(), "yyyy-MM-dd"), -1));
			} catch (ParseException e) {
			}
			Integer tempCount = webBaseDataStatDao.queryWeekPublish(map);
			tempCount = tempCount==null? 0:tempCount;
			MemcachedUtils.getInstance().getClient().set("front_index_weekly_publish", AN_DAY_SECONDS, tempCount+WEEKLY_PUBLISH);
		}
		db = Double.valueOf( MemcachedUtils.getInstance().getClient().get("front_index_weekly_publish").toString());
		out.put("weeklyPublish", NumberUtil.formatCurrency(db,"##,###"));
		
		// 废料企业总数
		if(MemcachedUtils.getInstance().getClient().get("front_index_company_count")==null){
			Integer tempCount = companyDAO.queryCompanyCount(null);
			tempCount = tempCount==null? 0:tempCount;
			MemcachedUtils.getInstance().getClient().set("front_index_company_count", AN_DAY_SECONDS, tempCount+TOTAL_COMPANY);
		}
		db = Double.valueOf( MemcachedUtils.getInstance().getClient().get("front_index_company_count").toString());
		out.put("totalCompany", NumberUtil.formatCurrency(db,"##,###"));
		
		// 优质商铺展示
		if(MemcachedUtils.getInstance().getClient().get("front_index_member")==null){
			Integer tempCount = companyDAO.queryCompanyCount("10051000");
			tempCount = tempCount==null? 0:tempCount;
			MemcachedUtils.getInstance().getClient().set("front_index_member", AN_DAY_SECONDS, tempCount+TOTAL_MEMBER);
		}
		db = Double.valueOf( MemcachedUtils.getInstance().getClient().get("front_index_member").toString());
		out.put("highMemberCompany", NumberUtil.formatCurrency(db,"##,###"));
		
		// 供求信息总量
		if(MemcachedUtils.getInstance().getClient().get("front_index_product_count")==null){
			Integer tempCount =  productsDAO.queryProductsCount();
			tempCount = tempCount==null? 0:tempCount;
			MemcachedUtils.getInstance().getClient().set("front_index_product_count", AN_DAY_SECONDS,tempCount+PRODUCT_COUNT);
		}
		db = Double.valueOf( MemcachedUtils.getInstance().getClient().get("front_index_product_count").toString());
		out.put("productsCount", NumberUtil.formatCurrency(db,"##,###"));
	}
}
