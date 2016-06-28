/*
 * 文件名称：SolrService.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.service.common;

import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;

import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.domain.exhibit.Exhibit;
import com.zz91.ep.domain.news.News;
import com.zz91.ep.domain.news.NewsNormDto;
import com.zz91.ep.domain.trade.SubnetCategory;
import com.zz91.ep.domain.trade.TradeCategory;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.ResultSolrDto;
import com.zz91.ep.dto.comp.CompNewsDto;
import com.zz91.ep.dto.comp.CompanyNormDto;
import com.zz91.ep.dto.exhibit.ExhibitNormDto;
import com.zz91.ep.dto.search.SearchBuyDto;
import com.zz91.ep.dto.search.SearchCompanyDto;
import com.zz91.ep.dto.search.SearchSupplyDto;
import com.zz91.ep.dto.trade.TradeBuyNormDto;
import com.zz91.ep.dto.trade.TradeSupplyNormDto;


/**
 * 项目名称：中国环保网
 * 模块编号：数据操作Service层
 * 模块描述：搜索引擎相关查询接口。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public interface SolrService {
	
	/**
	 * 函数名称：queryNewByKeywords
	 * 功能描述：根据关键字查询新闻信息
	 * 输入参数：无
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 * @throws SolrServerException 
	 */
//	public List<News> queryNewByKeywords(String keyword, Integer size) ;
	public PageDto<News> pageNewsForKeyworks(PageDto<News> page,String keywords);

	
	/**
	 * 函数名称：queryNewByKeywords
	 * 功能描述：根据关键字查询公司信息
	 * 输入参数：无
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 * @throws SolrServerException 
	 */
	public List<CompProfile> queryCompanyByKeyword(String keyword, Integer size) ;

	/**
	 * 函数名称：queryExhibitByKeywords
	 * 功能描述：根据关键字查询展会信息
	 * 输入参数：无
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 * @throws SolrServerException 
	 */
	public List<Exhibit> queryExhibitByKeywords(String keyword, Integer size);

//	/**
//	 * 函数名称：queryTradeSupplyByKeyword
//	 * 功能描述：根据关键字查询供应信息
//	 * 输入参数：无
//	 * 异　　常：无
//	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
//	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
//	 * @throws SolrServerException 
//	 */
//	public List<TradeSupplySearchDto> queryTradeSupplyByKeyword(String keyword, Integer size) throws SolrServerException;

	/**
	 * 函数名称：pageCompanyBySearch
	 * 功能描述：根据关键字查询公司信息
	 * 输入参数：无
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 * @throws SolrServerException 
	 * sort 0按创建时间排序,1按会员类型排序
	 */
	public PageDto<CompanyNormDto> pageCompanyBySearch(SearchCompanyDto search, PageDto<CompanyNormDto> page);
//	/**
//	 * 函数名称：pageNewSupplys
//	 * 功能描述：查询最新发布供应信息
//	 * 输入参数：无
//	 * 异　　常：无
//	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
//	 * 　　　　　2012/09/25　　 黄怀清 　　 　　 　 1.0.0　　 　　 创建方法函数
//	 * @throws SolrServerException 
//	 */
//	public PageDto<TradeSupplySearchDto> pageNewSupplys(SearchSupplyDto search, PageDto<TradeSupplySearchDto> page) throws SolrServerException;
//	/**
//	 * 函数名称：pageNewCompanyBySearch
//	 * 功能描述：查询最新公司信息
//	 * 输入参数：无
//	 * 异　　常：无
//	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
//	 * 　　　　　2012/08/06　　 黄怀清 　　 　　 　 1.0.0　　 　　 创建方法函数
//	 * @throws SolrServerException 
//	 */
//	public PageDto<CompanyNormDto> pageNewCompanyBySearch(
//			SearchCompanyDto search, PageDto<CompanyNormDto> page)
//			throws SolrServerException;

	/**
	 * 函数名称：pageExhibitBySearch
	 * 功能描述：根据查询条件查询展会信息
	 * 输入参数：无
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 * @throws SolrServerException 
	 */
	public PageDto<ExhibitNormDto> pageExhibitBySearch(Exhibit exhibit, PageDto<ExhibitNormDto> page);

	/**
	 * 函数名称：pageNewsBySearch
	 * 功能描述：根据关键字和类别查询资讯信息
	 * 输入参数：无
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 * @throws SolrServerException 
	 */
	public PageDto<NewsNormDto> pageNewsBySearch(String keywords, String categoryCode, PageDto<NewsNormDto> page);
//
	/**
	 * 函数名称：pageSupplysBySearch
	 * 功能描述：根据关键字和类别查询供应信息
	 * 输入参数：无
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 	创建方法函数
	 * 			2012-10-16		黄怀清				1.0.1			去掉方法最前端设置分页数,solr查询对象增加判断消除异常
	 * 			2012-11-23		马元生				1.1.0			升级solrj，优化搜索
	 * sort 1表示按serach 查询 2 表示查询Hot
	 */
	public ResultSolrDto pageSupplysBySearch(SearchSupplyDto search, PageDto<TradeSupplyNormDto> page,String sort);
//
//	/**
//	 * 函数名称：queryHotSupplys
//	 * 功能描述：根据关键字和类别查询供应信息
//	 * 输入参数：无
//	 * 异　　常：无
//	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
//	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
//	 * @throws SolrServerException 
//	 */
//	public List<TradeSupplySearchDto> queryHotSupplys(String categoryCode, String keyWords, Integer size) throws SolrServerException;
//
	/**
	 * 函数名称：pageBuysBySearch
	 * 功能描述：根据关键字和类别查询求购信息
	 * 输入参数：无
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 * @throws SolrServerException 
	 * sort 1 表示根据关键字查询  2 查询热门buys
	 */
	public PageDto<TradeBuyNormDto> pageBuysBySearch(SearchBuyDto search, PageDto<TradeBuyNormDto> page,String sort);
//
//	/**
//	 * 函数名称：queryHotBuys
//	 * 功能描述：根据关键字和类别查询热门求购信息
//	 * 输入参数：无
//	 * 异　　常：无
//	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
//	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
//	 * @throws SolrServerException 
//	 */
//	public List<TradeBuySearchDto> queryHotBuys(String keyWords, Integer size) throws SolrServerException ,ParseException;
//	/**
//	 * 函数名称：pageSupplysByCategory
//	 * 功能描述：根据供应类别查询供应信息
//	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
//	 * 　　　　　2012/07/19　　 黄怀清 　　 　　 　 1.0.0　　 　　 创建方法函数
//	 * @param categoryCode
//	 * @return
//	 * @throws SolrServerException
//	 */
//	public PageDto<TradeSupplySearchDto> pageSupplysByCategory(String categoryCode, PageDto<TradeSupplySearchDto> page) throws SolrServerException;
//	/**
//	 * 函数名称：pageSupplysByCategory
//	 * 功能描述：根据求购类别查询供应信息
//	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
//	 * 　　　　　2012/07/19　　 黄怀清 　　 　　 　 1.0.0　　 　　 创建方法函数
//	 * @param categoryCode
//	 * @return
//	 * @throws SolrServerException,ParseException
//	 */
//	public PageDto<TradeBuySearchDto> pageBuysByCategory(String categoryCode, PageDto<TradeBuySearchDto> page) throws SolrServerException, ParseException;
	/**
	 * 函数名称：pageSupplysByCategory
	 * 功能描述：根据行业查询交易类别信息
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/07/23　　 黄怀清 　　 　　 　 1.0.0　　 　　 创建方法函数
	 * @param code
	 * @return
	 * @throws SolrServerException,ParseException
	 */
	public PageDto<TradeCategory> pageCategory(TradeCategory cat, PageDto<TradeCategory> page);
	/**
	 * 函数名称：pageSupplysByCategory
	 * 功能描述：分页查询子网类别信息
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/07/24　　 黄怀清 　　 　　 　 1.0.0　　 　　 创建方法函数
	 * @param SubnetCategorySearchDto
	 * @return
	 * @throws SolrServerException,ParseException
	 */
	public PageDto<SubnetCategory> pageSubCategory(SubnetCategory sub, PageDto<SubnetCategory> page);
	
	
	public PageDto<CompNewsDto>  pageCompNewsBySearch(PageDto<CompNewsDto> page,CompNewsDto compNewsDto,String keywords);

  }