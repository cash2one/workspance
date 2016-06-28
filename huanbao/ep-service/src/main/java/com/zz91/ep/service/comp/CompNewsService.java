/*
 * 文件名称：CompNewsService.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.service.comp;

import java.util.List;

import com.zz91.ep.domain.comp.CompNews;
import com.zz91.ep.dto.ExtResult;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.comp.CompNewsDto;
import com.zz91.ep.dto.trade.TradeSupplyNormDto;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作Service层
 * 模块描述：公司资讯信息接口。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public interface CompNewsService {

	public final static String TYPE_COMPANY_NEWS = "1000";
	public final static String TYPE_TECHNICAL = "1001";
	public final static String TYPE_SUCCESS = "1002";
	
	/**
	 * 函数名称：queryNewestCompany
	 * 功能描述：查询最新公司发布资讯信息
	 * 输入参数：
	 * 		   @param categoryCode String 资讯类别
	 * 		   @param cid Integer 公司ID
	 * 		   @param size Integer 数量
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<CompNews> queryNewestCompNews(String categoryCode, Integer cid, Integer size);
	/**
	 * 函数名称：queryNewestCompany
	 * 功能描述：查询最新公司发布资讯信息	
	List<CompNews> queryNewestCompNewsFour();
	 * 输入参数：
	 * 		   @param categoryCode String 资讯类别
	 * 		   @param cid Integer 公司ID
	 * 		   @param size Integer 数量
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<CompNews> queryNewestCompNewsTop(Integer size);
	/**
	 * 函数名称：queryNewestCompany
	 * 功能描述：查询最新公司发布资讯信息
	 * 输入参数：
	 * 		   @param categoryCode String 资讯类别
	 * 		   @param cid Integer 公司ID
	 * 		   @param size Integer 数量
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<CompNews> queryNewestCompNewsSize(Integer size);
	/**
	 * 函数名称：pageCompNewsByCid
	 * 功能描述：根据公司ID查询公司的发布的信息
	 * 输入参数：
	 *         @param cid
	 *         @param type 类型(1000:公司动态,1001:技术文章,1002:成功案例)
	 *         @param pause(是否发布0:未发布,1:发布)
	 *         @param check(是否审核0:未审核,1:审核通过,2:审核不通过)
	 *         @param delete(是否审核0:删除,1:未删除)
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public PageDto<CompNews> pageCompNewsByCid(Integer cid, String type, String keywords, Short pause, Short check, Short delete,PageDto<CompNews> page);

	/**
	 * 函数名称：queryCompNewsById
	 * 功能描述：查询公司详细信息
	 * 输入参数：@param id 信息ID
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public CompNews queryCompNewsById(Integer id);
	
	/**
	 * 
	 * 函数名称：createArticle
	 * 功能描述：添加文章
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer createArticle(CompNews compNews);
	
	/**
	 * 
	 * 函数名称：updateArticle
	 * 功能描述：修改文章
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer updateArticle(CompNews compNews);
	
	/**
	 * 
	 * 函数名称：deleteArticle
	 * 功能描述：根据 compNewsId和 companyId删除新闻
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public ExtResult deleteArticle(Integer compNewsId ,Integer companyId);
	
	/**
	 * 
	 * 函数名称：publishArticle
	 * 功能描述：发布/不发布文章
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public ExtResult publishArticle(Integer compNewsId ,Integer companyId,Short status);
	/**
	 * 函数名称：validateTitleRepeat
     * 功能描述：判断同一个公司是否添加相同的标题
     * 输入参数：@param test1 参数1
     * 　　　　　.......
     * 　　　　　@param test2 参数2
     * 异　　常：[按照异常名字的字母顺序]
     * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
     * 　　　　　2013/09/04　　 周宗坤 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Boolean validateTitleRepeat(Integer companyId,String title); 

	
	
	/**
	 * 查询前一张公司新闻
	 * @param id
	 * @param cid 
	 */
	public CompNews queryPrevCompNewsById(Integer id,Integer cid,String categoryCode);
	/**
	 * 查询后一张公司新闻
	 * @param id
	 * @param cid 
	 */
	public CompNews queryNextCompNewsById(Integer id, Integer cid,String categoryCode);
	/**
	 * 更新浏览数
	 * @param id
	 */
	public Integer updateViewCountById(Integer id);
	
	/**
	 * 查询技术文章列表页
	 * @param page
	 */
	public PageDto<CompNewsDto> pageCompNewsForArticle(PageDto<CompNewsDto> page);
	
	/**
	 * 查询一周的技术文章列表页
	 * @param page
	 */
	
	public List<CompNews> queryWeekForArticle(Integer size);
	
	/**
	 * 技术文章搜索引擎
	 * @param page
	 */
	
	public PageDto<CompNewsDto> pageBySearchEngine(String keywords,PageDto<CompNewsDto> page);
	
	
	
}
