/*
 * 文件名称：TradeBuyDao.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.dao.trade;

import java.util.List;

import com.zz91.ep.domain.trade.TradeBuy;
import com.zz91.ep.dto.CommonDto;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.trade.BuyMessageDto;
import com.zz91.ep.dto.trade.TradeBuyDto;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作Dao层
 * 模块描述：交易中心求购信息接口。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public interface TradeBuyDao {

	/**
	 * 函数名称：queryBuysByRecommend
	 * 功能描述：推荐求购信息（页面片段缓存）
	 * 输入参数：
     * 		  @param size Integer 数量
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<CommonDto> queryBuysByRecommend(Integer size);

	/**
	 * 函数名称：queryNewestBuys
	 * 功能描述：最新求购信息（页面片段缓存）
	 * 输入参数：
     * 		  @param size Integer 数量
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<CommonDto> queryNewestBuys(Integer size);

	/**
	 * 函数名称：updateMessageCountById
	 * 功能描述：更新留言数
	 * 输入参数：
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer updateMessageCountById(Integer id);

	/**
	 * 函数名称：queryBuysDetailsByRecommend
	 * 功能描述：推荐求购信息（页面片段缓存）
	 * 输入参数：
     * 		  @param size Integer 数量
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<TradeBuy> queryBuysDetailsByRecommend(Integer size);

	/**
	 * 函数名称：queryBuyDetailsById
	 * 功能描述：根据ID查询求购信息
	 * 输入参数：
     * 		  @param size Integer 数量
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public TradeBuyDto queryBuyDetailsById(Integer id);
	
	/**
	 * 
	 * 函数名称：getTradeBuyId
	 * 功能描述：统计求购信息
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer getTradeBuyId(Integer uid);
	
	/**
	 * 
	 * 函数名称：insertTradeBuy
	 * 功能描述：插入求购信息
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer insertTradeBuy(TradeBuy tradeBuy);
	
	 /**
	  * 
	  * 函数名称：queryBuyByConditions
	  * 条件筛选求购信息（分页显示）
	  * 输入参数：@param test1 参数1
	  * 　　　　　.......
	  * 　　　　　@param test2 参数2
	  * 异　　常：[按照异常名字的字母顺序]
	  * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	  * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	  */
    public List<TradeBuy> queryBuyByConditions(Integer cid, Integer pauseStatus,Integer overdueStatus, Integer checkStatus, PageDto<BuyMessageDto> page);

    /**
     * 
     * 函数名称：queryBuyByConditionsCount
     * 功能描述：条件筛选求购信息（分页显示）
     * 输入参数：@param test1 参数1
     * 　　　　　.......
     * 　　　　　@param test2 参数2
     * 异　　常：[按照异常名字的字母顺序]
     * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
     * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
     */
    public Integer queryBuyByConditionsCount(Integer cid, Integer pauseStatus, Integer overdueStatus, Integer checkStatus);
    
    /**
     * 
     * 函数名称：updateDelStatusById
     * 功能描述：
     * 输入参数：@param test1 参数1
     * 　　　　　.......
     * 　　　　　@param test2 参数2
     * 异　　常：[按照异常名字的字母顺序]
     * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
     * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
     */
    public Integer updateDelStatusById(Integer id, Integer cid);
	
	/**
	 * 
	 * 函数名称：updatePauseStatusById
	 * 功能描述：更新发布状态
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer updatePauseStatusById(Integer id, Integer cid,
			Integer newStatus);
	
	/**
	 * 
	 * 函数名称：updateRefreshById
	 * 功能描述：刷新求购信息
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer updateRefreshById(Integer id, Integer cid);
	
	/**
	 * 
	 * 函数名称：queryBuySimpleDetailsById
	 * 功能描述：根据求购信息Id查询求购基本信息（列表展现）
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public TradeBuy queryBuySimpleDetailsById(Integer id);
	
	/**
	 * 
	 * 函数名称：queryUpdateBuyById
	 * 功能描述:根据id查询需要修改的信息
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public TradeBuy queryUpdateBuyById(Integer id, Integer cid);
	
	/**
	 * 
	 * 函数名称：updateBaseBuyById
	 * 功能描述：更新求购基本信息
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer updateBaseBuyById(TradeBuy buy, Integer id, Integer cid);

	/**
	 * 函数名称：queryImpTradeBuy
	 * 功能描述：查询导入数据
	 * 输入参数：@param maxId 外网导入数据最大ID
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<TradeBuy> queryImpTradeBuy(Integer maxId);

	/**
	 * 函数名称：updateImpTradeBuy
	 * 功能描述：更新导入数据时间
	 * 输入参数：@param maxId 外网导入数据最大ID
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer updateImpTradeBuy(Integer maxId);
	
	/**
	 * 统计各种状态下求购数据数量
	 * @param cid
	 * @param pauseStatus
	 * @param overdueStatus
	 * @param checkStatus
	 * @return
	 */
	public Integer countBuysOfCompanyByStatus(Integer cid, Integer pauseStatus, Integer overdueStatus, Integer checkStatus);
}