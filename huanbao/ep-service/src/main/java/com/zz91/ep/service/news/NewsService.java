/*
 * 文件名称：NewsService.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.service.news;

import java.util.List;

import com.zz91.ep.domain.news.News;
import com.zz91.ep.dto.CommonDto;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.news.NewsDto;
import com.zz91.ep.dto.news.NewsSearchDto;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作Service层
 * 模块描述：资讯信息接口。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public interface NewsService {
	
	/**
	 * 函数名称：queryNewsByCategory
	 * 功能描述：查询所有的不同类别资讯信息（页面片段缓存）
	 * 输入参数：
	 *        @param code String 资讯类别
	 *        @param size Integer 获取信息条数
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2013/07/25　　 方潮　　 　　 　  1.0.0　　 　　 创建方法函数
	 */
	public List<News> queryNewsByCategory(String code, Integer size);

	/**
	 * 函数名称：queryNewsByRecommend
	 * 功能描述：根据推荐类型code查询资讯信息（页面片段缓存）
	 * 输入参数：
	 *        @param code String 资讯推荐类别
	 *        @param size Integer 获取信息条数
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<CommonDto> queryNewsByRecommend(String code, Integer size);

	/**
	 * 函数名称：queryTopNews
	 * 功能描述：阅读排行榜（页面片段缓存）
	 * 输入参数：
	 *        @param size Integer 获取信息条数
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<News> queryTopNews(Integer size);

	/**
	 * 函数名称：queryValueByTypeAndKey
	 * 功能描述：根据推荐类型查询相关资讯信息
	 * 输入参数：
	 *        @param size Integer 获取信息条数
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public PageDto<NewsSearchDto> queryValueByTypeAndKey(String categoryKey, PageDto<NewsSearchDto> page);

	/**
	 * 函数名称：queryNewDetailsById
	 * 功能描述：根据id查询资讯详细信息
	 * 输入参数：
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public News queryNewDetailsById(Integer id);

	/**
	 * 函数名称：updateViewCountById
	 * 功能描述：更新浏览次数
	 * 输入参数：
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer updateViewCountById(Integer id);

	/**
	 * 函数名称：queryPrevNewsById
	 * 功能描述：根据id查询上一篇资讯信息
	 * 输入参数：
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public News queryPrevNewsById(Integer id, String categoryCode);

	/**
	 * 函数名称：queryNextNewsById
	 * 功能描述：根据id查询下一篇资讯信息
	 * 输入参数：
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public News queryNextNewsById(Integer id, String categoryCode);
	
	/**
	 * 函数名称：pageNewsByCategory
	 * 功能描述：根据类别查询资讯信息
	 * 输入参数：
	 * 　　　　　@param categoryCode 资讯类别
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public PageDto<NewsSearchDto> pageNewsByCategory(String categoryCode, PageDto<NewsSearchDto> page);
	
	/**
	 * 函数名称：queryNewsByCode
	 * 功能描述：根据类别查询资讯
	 * 输入参数：
	 * 　　　　　@param categoryCode 资讯类别
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2013/09/07　　 方潮 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	
	public List<News> queryNewsByCode(String categoryCode,Integer size);
   
	
	/**
	 * 函数名称：queryNewsAndUrlByCode
	 * 功能描述：查询所有的不同类别资讯信息（页面片段缓存）
	 * 输入参数：
	 *        @param code String 资讯类别
	 *        @param size Integer 获取信息条数
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2013/09/14　　 方潮　　 　　 　  1.0.0　　 　　 创建方法函数
	 */
	public List<NewsDto> queryNewsAndUrlByCode(String code, Integer size);
	
}