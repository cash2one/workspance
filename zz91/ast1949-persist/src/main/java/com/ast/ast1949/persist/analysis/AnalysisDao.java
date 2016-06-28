/**
 * 
 */
package com.ast.ast1949.persist.analysis;

import java.util.Date;
import java.util.List;

import com.ast.ast1949.domain.analysis.AnalysisCompPrice;
import com.ast.ast1949.domain.analysis.AnalysisInquiry;
import com.ast.ast1949.domain.analysis.AnalysisLog;
import com.ast.ast1949.domain.analysis.AnalysisProduct;
import com.ast.ast1949.domain.analysis.AnalysisRegister;
import com.ast.ast1949.domain.analysis.AnalysisTradeKeywords;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.analysis.AnalysisSpotDto;

/**
 * @author root
 *
 */
public interface AnalysisDao {

	public Integer queryEsiteVisit(Integer companyId);
	
	public List<AnalysisInquiry> queryInquiry(String inquiryType,Date gmtTarget,PageDto<AnalysisInquiry> page);
	
	public Integer queryInquiryCount(String inquiryType,Date gmtTarget);
	
	public List<AnalysisTradeKeywords> queryKeywords(Date gmtTarget, PageDto<AnalysisTradeKeywords> page);
	
	public Integer queryKeywordsCount(Date gmtTarget);
	
	public List<AnalysisCompPrice> queryCompPrice(String categoryCode,Date start, Date end);
	
	public List<AnalysisInquiry> queryInquiryRang(String inquiryType,Integer inquiryTarget,Date start,Date end);
	
	public List<AnalysisTradeKeywords> queryKeywordsRang(String kw, Date start,Date end);
	
	public List<AnalysisProduct> queryProduct(String typeCode,String categoryCode, Date start, Date end);
	
	public List<AnalysisRegister> queryRegister(String regfromCode, Date start,Date end);
	
	public Integer summaryInquiry(String inquiryType, Date gmtTarget);
	
	public Integer summaryKeywords(Date gmtTarget);

	public List<AnalysisLog> queryAnalysisLog(String operator, String operation,Date start, Date end);
	
	public List<AnalysisSpotDto> queryAnalysisLogForSpot(PageDto<AnalysisSpotDto> page,String operator, String operation,Date start, Date end);
	
	public List<AnalysisLog> querySpot(String operator,Date start,Date end);
	
	public Integer countQueryAnalysisLogForSpot(String operator, String operation,Date start, Date end);
	
	public Integer summarySpot(Date start, Date end,String operation);
}
