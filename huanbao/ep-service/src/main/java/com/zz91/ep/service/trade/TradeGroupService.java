/*
 * 文件名称：TradeGroupService.java
 * 创建者　：涂灵峰
 * 创建时间：2012-6-18 下午3:31:50
 * 版本号　：1.0.0
 */
package com.zz91.ep.service.trade;

import java.util.List;

import com.zz91.ep.domain.trade.TradeGroup;
import com.zz91.ep.dto.trade.TradeGroupDto;

/**
 * 项目名称：中国环保网
 * 模块编号：业务逻辑Service层
 * 模块描述：用户自定义类别操作接口
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-05-05　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public interface TradeGroupService {

	/**
	 * 函数名称：queryTradeGroupDtoById
	 * 功能描述：查询用户分组信息
	 * 输入参数：@param cid 公司ID
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 * 
	 */
	public List<TradeGroupDto> queryTradeGroupDtoById(Integer cid);

	/**
	 * 函数名称：queryNameById
	 * 功能描述：更句分组ID查询name
	 * 输入参数：@param id 信息ID
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public String queryNameById(Integer id);
	
	/**
	 * 
	 * 函数名称：queryTradeGroupById
	 * 功能描述：根据用户取所有分组类别
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<TradeGroup> queryTradeGroupById(Integer cid);
	
	/**
	 * 
	 * 函数名称：createTradeGroup
	 * 功能描述：添加用户分组
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
    public Integer createTradeGroup(TradeGroup tradeGroup);
    
   /**
    * 
    * 函数名称：deleteTradeGroup
    * 功能描述：删除用户分组
    * 输入参数：@param test1 参数1
    * 　　　　　.......
    * 　　　　　@param test2 参数2
    * 异　　常：[按照异常名字的字母顺序]
    * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
    * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
    */
    public Integer deleteTradeGroup(Integer gid, Integer cid);
    
    /**
     * 
     * 函数名称：updateTradeGroup
     * 功能描述：更新用户分组
     * 输入参数：@param test1 参数1
     * 　　　　　.......
     * 　　　　　@param test2 参数2
     * 异　　常：[按照异常名字的字母顺序]
     * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
     * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
     */
    public Integer updateTradeGroup(TradeGroup tradeGroup);
    
    /**
     * 根据id查询供求类别
     * @param id
     * @return
     */
    public TradeGroup queryTradeGroup(Integer id);
    
    /**
     * 根据cid查询 和父类id查询子类别
     * @param cid
     * @return
     */
	public List<TradeGroup> queryTradeGroupByCid(Integer cid,Integer parentId);
    
	public Boolean childAvalible(Integer cid, Integer parentId);
    
}