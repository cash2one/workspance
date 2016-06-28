/**
 * 
 */
package com.ast.ast1949.service.analysis;

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
import com.ast.ast1949.dto.products.KeywordSearchDto;

/**
 * @author root
 *
 */
public interface AnalysisService {
	
	public Integer queryEsiteVisit(Integer companyId);
	
	/**
	 * 查找指定来源及时间范围内的注册信息
	 * @param regfromCode：可选
	 * @param start：必需存在，如果为null，默认为当前时间前一天
	 * @param end：如果为null，默认等于start
	 * @return
	 */
	public List<AnalysisRegister> queryRegister(String regfromCode, Date start, Date end);
	
	/**
	 * 分页查找留言统计结果
	 * @param inquiryType：留言类型，可选
	 * @param gmtTarget：统计日期
	 * @return
	 */
	public PageDto<AnalysisInquiry> pageInquiry(String inquiryType, Date gmtTarget, PageDto<AnalysisInquiry> page);
	
	public AnalysisInquiry summaryInquiry(String inquiryType, Date gmtTarget);
	
	/**
	 * 查找某一询盘目标的留言情况
	 * @param inquiryType：不能为null，表示留言类型
	 * @param inquiryTarget：不能为null
	 * @param start：必需存在，如果为null，默认为当前时间前一天
	 * @param end：如果为null，默认等于start
	 * @return
	 */
	public List<AnalysisInquiry> queryInquiry(String inquiryType, Integer inquiryTarget, Date start, Date end);
	
	/**
	 * 分页查找当日所有关键字搜索结果
	 * @param gmtTarget：不能为null
	 * @return
	 */
	public PageDto<AnalysisTradeKeywords> pageKeywords(Date gmtTarget, PageDto<AnalysisTradeKeywords> page);
	
	/**
	 * 计算汇总情况
	 * @param gmtTarget
	 * @return
	 */
	public AnalysisTradeKeywords summaryKeywords(Date gmtTarget);
	
	/**
	 * 查找某一关键字的详细搜索情况
	 * @param kw：关键字，非空
	 * @param start：必需存在，如果为null，默认为当前时间前一天
	 * @param end：如果为null，默认等于start
	 * @return
	 */
	public List<AnalysisTradeKeywords> queryKeywords(String kw, Date start, Date end);
	
	/**
	 * 查找供求发布统计情况
	 * @param typeCode：供求类型，可选
	 * @param categoryCode：供求类别，可选
	 * @param start：必需存在，如果为null，默认为当前时间前一天
	 * @param end：如果为null，默认等于start
	 * @return
	 */
	public List<AnalysisProduct> queryProduct(String typeCode, String categoryCode, Date start, Date end);
	
	/**
	 * 查找企业报价发布统计情况
	 * @param categoryCode：报价类型，可选
	 * @param start：必需存在，如果为null，默认为当前时间前一天
	 * @param end：如果为null，默认等于start
	 * @return
	 */
	public List<AnalysisCompPrice> queryCompPrice(String categoryCode, Date start, Date end);

	/**
	 * 查询指定时间区域内qq登录登录明细
	 * @param operator 
	 * @param operation
	 * @param start：必需存在，如果为null，默认为当前时间前一天
	 * @param end：如果为null，默认等于start
	 * @return
	 */
	public List<AnalysisLog> queryAnalysisLog(String operator, String operation,Date from, Date to);
	
	public PageDto<AnalysisSpotDto> queryAnalysisLogForSpot(PageDto<AnalysisSpotDto> page,String operator, String operation,Date from, Date to);
	
	public List<AnalysisLog> querySpot(String operator,Date start, Date end);
	
	public AnalysisSpotDto summarySpot(Date from,Date to,String operation);
	
}
