/**
 * 
 */
package com.ast.ast1949.service.analysis.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.analysis.AnalysisCompPrice;
import com.ast.ast1949.domain.analysis.AnalysisInquiry;
import com.ast.ast1949.domain.analysis.AnalysisLog;
import com.ast.ast1949.domain.analysis.AnalysisProduct;
import com.ast.ast1949.domain.analysis.AnalysisRegister;
import com.ast.ast1949.domain.analysis.AnalysisTradeKeywords;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.analysis.AnalysisSpotDto;
import com.ast.ast1949.dto.products.KeywordSearchDto;
import com.ast.ast1949.persist.analysis.AnalysisDao;
import com.ast.ast1949.persist.products.ProductsDAO;
import com.ast.ast1949.service.analysis.AnalysisService;
import com.ast.ast1949.service.facade.CategoryProductsFacade;
import com.ast.ast1949.util.Assert;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;

/**
 * @author root
 *
 */
@Component("analysisService")
public class AnalysisServiceImpl implements AnalysisService {
	
	@Resource
	private AnalysisDao analysisDao;
	@Resource
	private ProductsDAO productsDAO;

	@Override
	public Integer queryEsiteVisit(Integer companyId) {
		if(companyId==null){
			return 0;
		}
		return analysisDao.queryEsiteVisit(companyId);
	}

	@Override
	public PageDto<AnalysisInquiry> pageInquiry(String inquiryType,
			Date gmtTarget, PageDto<AnalysisInquiry> page) {
		if(page.getSort()==null){
			page.setSort("num");
		}
		page.setTotalRecords(analysisDao.queryInquiryCount(inquiryType,gmtTarget));
		page.setRecords(analysisDao.queryInquiry(inquiryType,gmtTarget,page));
		return page;
	}

	@Override
	public PageDto<AnalysisTradeKeywords> pageKeywords(Date gmtTarget, PageDto<AnalysisTradeKeywords> page) {
		Assert.notNull(gmtTarget, "the gmtTarget can not be null");
		page.setTotalRecords(analysisDao.queryKeywordsCount(gmtTarget));
		page.setRecords(analysisDao.queryKeywords(gmtTarget,page));
		return page;
	}

	@Override
	public List<AnalysisCompPrice> queryCompPrice(String categoryCode,Date start, Date end) {
		if(start==null){
			try {
				start = DateUtil.getDateAfterDays(DateUtil.getDate(new Date(), "yyyy-MM-dd"), -1);
			} catch (ParseException e) {
			}
		}
		if(end==null){
			end = start; 
		}
		return analysisDao.queryCompPrice(categoryCode,start,end);
	}

	@Override
	public List<AnalysisInquiry> queryInquiry(String inquiryType,
			Integer inquiryTarget, Date start, Date end) {
		Assert.notNull(inquiryType, "inquiryType can not be null");
		Assert.notNull(inquiryTarget, "inquiryType can not be null");
		if(start==null){
			try {
				start = DateUtil.getDateAfterDays(DateUtil.getDate(new Date(), "yyyy-MM-dd"), -1);
			} catch (ParseException e) {
			}
		}
		if(end==null){
			end = start; 
		}
		return analysisDao.queryInquiryRang(inquiryType,inquiryTarget,start,end);
	}
	
	@Override
	public List<AnalysisTradeKeywords> queryKeywords(String kw, Date start,
			Date end) {
		if(start==null){
			try {
				start = DateUtil.getDateAfterDays(DateUtil.getDate(new Date(), "yyyy-MM-dd"), -1);
			} catch (ParseException e) {
			}
		}
		if(end==null){
			end = start; 
		}
		return analysisDao.queryKeywordsRang(kw,start,end);
	}

	@Override
	public List<AnalysisProduct> queryProduct(String typeCode,
			String categoryCode, Date start, Date end) {
		if(start==null){
			try {
				start = DateUtil.getDateAfterDays(DateUtil.getDate(new Date(), "yyyy-MM-dd"), -1);
			} catch (ParseException e) {
			}
		}
		if(end==null){
			end = start; 
		}
		return analysisDao.queryProduct(typeCode,categoryCode,start,end);
	}

	@Override
	public List<AnalysisRegister> queryRegister(String regfromCode, Date start,
			Date end) {
		if(start==null){
			try {
				start = DateUtil.getDateAfterDays(DateUtil.getDate(new Date(), "yyyy-MM-dd"), -1);
			} catch (ParseException e) {
			}
		}
		if(end==null){
			end = start; 
		}
		return analysisDao.queryRegister(regfromCode,start,end);
	}

	@Override
	public AnalysisInquiry summaryInquiry(String inquiryType, Date gmtTarget) {
		AnalysisInquiry inquiry=new AnalysisInquiry();
		inquiry.setNum(analysisDao.summaryInquiry(inquiryType, gmtTarget));
		inquiry.setInquiryType("总计：");
		return inquiry;
	}

	@Override
	public AnalysisTradeKeywords summaryKeywords(Date gmtTarget) {
		AnalysisTradeKeywords keywords=new AnalysisTradeKeywords();
		
		keywords.setNum(analysisDao.summaryKeywords(gmtTarget));
		keywords.setKw("总计：");
		return keywords;
	}
	
	@Override
	public List<AnalysisLog> queryAnalysisLog(String operator, String operation,Date from, Date to){
		if(from==null){
			try {
				from = DateUtil.getDateAfterDays(DateUtil.getDate(new Date(), "yyyy-MM-dd"), -1);
			} catch (ParseException e) {
			}
		}
		if(to==null){
			to = from; 
		}else{
			int i = 0;
			try {
				i = DateUtil.getIntervalDays(from, to);
			} catch (ParseException e) {
				return null;
			}
			if(i>0){
				to = from;
			}
		}
		return analysisDao.queryAnalysisLog(operator, operation, from, to);
	}

	@Override
	public PageDto<AnalysisSpotDto> queryAnalysisLogForSpot(PageDto<AnalysisSpotDto> page,String operator,
			String operation, Date from, Date to) {
		if(from==null){
			try {
				from = DateUtil.getDateAfterDays(DateUtil.getDate(new Date(), "yyyy-MM-dd"), -1);
			} catch (ParseException e) {
			}
		}
		if(to==null){
			to = from; 
		}else{
			int i = 0;
			try {
				i = DateUtil.getIntervalDays(from, to);
			} catch (ParseException e) {
				return null;
			}
			if(i>0){
				to = from;
			}
		}
		List<AnalysisSpotDto> list = analysisDao.queryAnalysisLogForSpot(page,null, operation, from, to);
		CategoryProductsFacade categoryProductsFacade=CategoryProductsFacade.getInstance();
		for(AnalysisSpotDto dto:list){
			ProductsDO productsDO=productsDAO.queryProductsById(Integer.parseInt(dto.getOperator()));
			if(productsDO.getTitle()==null || StringUtils.isEmpty(productsDO.getTitle())){
				dto.setTitle(categoryProductsFacade.getValue(productsDO.getCategoryProductsMainCode()));
			}else{
				dto.setTitle(productsDO.getTitle());
			}
		}
		page.setRecords(list);
		page.setTotalRecords(analysisDao.countQueryAnalysisLogForSpot(operator, operation, from, to));
		return page;
	}

	@Override
	public List<AnalysisLog> querySpot(String operator, Date start, Date end) {
		if(start==null){
			try {
				start = DateUtil.getDateAfterDays(DateUtil.getDate(new Date(), "yyyy-MM-dd"), -1);
			} catch (ParseException e) {
			}
		}
		if(end==null){
			end = start; 
		}
		return analysisDao.querySpot(operator, start, end);
	}

	@Override
	public AnalysisSpotDto summarySpot(Date from,Date to,String operation) {
		if(from==null){
			try {
				from = DateUtil.getDateAfterDays(DateUtil.getDate(new Date(), "yyyy-MM-dd"), -1);
			} catch (ParseException e) {
			}
		}
		if(to==null){
			to = from; 
		}else{
			int i = 0;
			try {
				i = DateUtil.getIntervalDays(from, to);
			} catch (ParseException e) {
				return null;
			}
			if(i>0){
				to = from;
			}
		}
		AnalysisSpotDto log=new AnalysisSpotDto();
		
		log.setNum(analysisDao.summarySpot(from,to,operation));
		log.setTitle("总计：");
		return log;
	}

}
