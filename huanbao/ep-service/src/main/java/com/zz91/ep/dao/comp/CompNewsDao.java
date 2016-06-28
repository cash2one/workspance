/*
 * 文件名称：CompNewsDao.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.dao.comp;

import java.util.List;

import com.zz91.ep.domain.comp.CompNews;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.comp.CompNewsDto;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作DAO层
 * 模块描述：公司资讯信息接口。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public interface CompNewsDao {

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
	 * 功能描述：查询最新高级会员公司发布的企业动态
	 * 输入参数：
	 * 		   @param size Integer 数量
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2013/06/06　　  方潮 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<CompNews> queryNewestCompNewsTop(Integer size);
	/**
	 * 函数名称：queryNewestCompany
	 * 功能描述：查询最新普通会员公司发布的企业动态
	 * 输入参数：
	 * 		   @param size Integer 数量
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2013/06/06　　  方潮 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<CompNews> queryNewestCompNewsSize(Integer size);
	
	
	//产询没有通过审核的文章的数量
	public Integer queryWtgshCount(Integer cid);
	/**
	 * 函数名称：queryCompNewsByCid
	 * 功能描述：根据公司ID查询资讯信息
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
	public List<CompNews> queryCompNewsByCid(Integer cid, String type, String keywords, Short pause, Short check, Short delete, PageDto<CompNews> page);

	/**
	 * 函数名称：queryCompNewsByCidCount
	 * 功能描述：根据公司ID查询资讯信息数
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
	public Integer queryCompNewsByCidCount(Integer cid, String type, String keywords, Short pause, Short delete, Short check);

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
	 * 函数名称：insertArticle
	 * 功能描述：插入文章
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer insertArticle(CompNews compNews);
	
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
	 * 函数名称：updateDeleteStatusByCid
	 * 功能描述: 删除文章
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer updateDeleteStatusByCid(Integer id, Integer cid, Short status);
	
	/**
	 * 
	 * 函数名称：updatePauseStatusByCid
	 * 功能描述：发布/不发布
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer updatePauseStatusByCid(Integer id, Integer cid, Short status);

	/**
     * 
     * 函数名称：countForCidAndTitle
     * 功能描述：根据companyId和companyTitle获得条数
     * 输入参数：@param test1 参数1
     * 　　　　　.......
     * 　　　　　@param test2 参数2
     * 异　　常：[按照异常名字的字母顺序]
     * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
     * 　　　　　2013/09/04　　 周宗坤 　　 　　 　 1.1.0　　 　　 创建方法函数
     */
	public Integer countForCidAndTitle(Integer companyId, String title);


	
	
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
	 * 更新点击浏览数
	 * @param id
	 */
    public 	Integer updateViewCountById(Integer id);
    
    /**
	 * 查询技术文章列表页
	 * @param page
	 */
    public List<CompNewsDto>  queryCompNewsForArticle(PageDto<CompNewsDto> page); 
    
    public Integer  queryCompNewsForArticleCount();
    
    /**
	 * 查询一周热门的技术文章
	 * @param page
	 */
    
    public  List<CompNews>  queryWeekForArticle(Integer size);
   

}