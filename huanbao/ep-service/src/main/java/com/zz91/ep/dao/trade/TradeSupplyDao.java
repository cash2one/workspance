/*
 * 文件名称：TradeSupplyDao.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.dao.trade;

import java.util.List;

import com.zz91.ep.domain.trade.TradeKeyword;
import com.zz91.ep.domain.trade.TradeSupply;
import com.zz91.ep.dto.CommonDto;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.trade.SupplyMessageDto;
import com.zz91.ep.dto.trade.TradeSupplyDto;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作Dao层
 * 模块描述：交易中心供求信息接口。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public interface TradeSupplyDao {

	/**
	 * 函数名称：querySupplyCountByCategory
	 * 功能描述：查询所有的不同类别信息数目（页面片段缓存）
	 * 输入参数：
	 *        @param code 查询用的类别名
     * 
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer querySupplyCountByCategory(String code);

	/**
	 * 函数名称：querySupplysByRecommend
	 * 功能描述：推荐供应信息（页面片段缓存）
	 * 输入参数：
     * 		  @param cid Integer 公司ID
     * 		  @param type Short 推荐类型
     * 		  @param size Integer 数量
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<CommonDto> querySupplysByRecommend(Integer cid, Short type, Integer size);

	/**
	 * 函数名称：queryNewestSupplys
	 * 功能描述：获取最新供应信息
	 * 输入参数：
     * 		  @param size Integer 数量
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<TradeSupply> queryNewestSupplys(Integer cid, Integer size,String category,Integer uid);
	

	/**
	 * 函数名称：queryShortDetailsById
	 * 功能描述：根据ID获取供应信息
	 * 输入参数：
     * 		  @param id Integer ID
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public TradeSupply queryShortDetailsById(Integer id);

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
	 * 函数名称：querySupplyByCompany
	 * 功能描述：获取不同公司的供应信息
	 * 输入参数：@param group 分组
	 * 　　　　　@param keywords 关键字
	 * 　　　　　@param cid 公司ID
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<TradeSupply> querySupplyByCompany(Integer group, String keywords, Integer cid, PageDto<TradeSupply> page);

	/**
	 * 函数名称：querySupplyByCompanyCount
	 * 功能描述：获取不同公司的供应信息条数
	 * 输入参数：@param group 分组
	 * 　　　　　@param keywords 关键字
	 * 　　　　　@param cid 公司ID
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer querySupplyByCompanyCount(Integer group, String keywords, Integer cid);

	/**
	 * 函数名称：queryRecommendSupplysByCid
	 * 功能描述：查询公司推荐供应信息
	 * 输入参数：@param cid 公司ID
	 * 　　　　　@param size 条数
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<TradeSupply> queryRecommendSupplysByCid(Integer cid, Integer size);

	/**
	 * 函数名称：queryDetailsById
	 * 功能描述：根据ID获取供应信息
	 * 输入参数：
     * 		  @param id Integer ID
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 * 		   2012/08/28　　 齐振杰 　　 　　 　 1.0.1　　 　　 修改方法返回类型(TradeSupplyDto -> TradeSupply)
	 */
	public TradeSupply queryDetailsById(Integer id);
	/**
	 * 
	 * 函数名称：getTradeSupplyCount
	 * 功能描述：统计供求信息
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 * 　　　　　2012/10/10　　 黄怀清　　　　　　　1.0.1			增加审核状态
	 */
	public Integer getTradeSupplyCount(Integer uid,Integer checkStatus);
	
	/**
	 * 
	 * 函数名称：updatePhotoCoverById
	 * 功能描述：更新图片
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer updatePhotoCoverById(Integer id, String photoCover,
			Integer cid);

	
	/**
	 * 
	 * 函数名称：insertTradeSupply
	 * 功能描述：[插入供应信息
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer insertTradeSupply(TradeSupply tradeSupply);
	
	/**
	 * 
	 * 函数名称：querySupplyByConditions
	 * 功能描述：[根据不同条件筛选供应信息（分页显示）
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<TradeSupply> querySupplyByConditions(Integer cid,
			Integer pauseStatus, Integer overdueStatus, Integer checkStatus,Integer recommend,
			Integer groupId, PageDto<SupplyMessageDto> page);

	/**
	 * 
	 * 函数名称：querySupplyByConditionsCount
	 * 功能描述：根据不同条件筛选供应信息数（分页显示）
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer querySupplyByConditionsCount(Integer cid,
			Integer pauseStatus, Integer overdueStatus, Integer checkStatus,Integer recommend,
			Integer groupId);
	
	/**
	 * 
	 * 函数名称：updateDelStatusById
	 * 功能描述:逻辑删除供应信息
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
	 * 函数名称：updateSupplyGroupIdById
	 * 根据一组供应信息编号更新供应信息分组编号
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer updateSupplyGroupIdById(Integer id, Integer cid, Integer gid);
	
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
	 * 功能描述：刷新供应信息
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
	 * 函数名称：queryUpdateSupplyById
	 * 查找更新供应信息及公司基本信息（富文本简介）
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public TradeSupply queryUpdateSupplyById(Integer id, Integer cid);
	
	/**
	 * 
	 * 函数名称：updateCategoryById
	 * 功能描述：更新供应信息类别
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer updateCategoryById(String category,String propertyValue, Integer id, Integer cid);

	/**
	 * 
	 * 函数名称：updateBaseSupplyById
	 * 功能描述：更新基本供应信息
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer updateBaseSupplyById(TradeSupply supply, Integer id,
			Integer cid);
	
	/**
	 * 
	 * 函数名称：updatePropertyQueryById
	 * 功能描述：更新专业属性（搜索用）
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer updatePropertyQueryById(Integer id, String properyValue);
	
	/**
	 * 
	 * 函数名称：queryOneSupplyById
	 * 功能描述：根据id查询一条记录
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public TradeSupply queryOneSupplyById(Integer id);
	
	

	/**
	 * 函数名称：updateImpTradeSupply
	 * 功能描述：更新导入数据时间
	 * 输入参数：@param maxId 外网导入数据最大ID
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer updateImpTradeSupply(Integer maxId);

	/**
	 * 函数名称：queryImpTradeSupply
	 * 功能描述：查询导入数据
	 * 输入参数：@param maxId 外网导入数据最大ID
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<TradeSupply> queryImpTradeSupply(Integer maxId);

	/**
	 * 函数名称：queryBwListByKeyword
	 * 功能描述：查询标王关键字列表
	 * 输入参数：@param keywords 标王购买关键字
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/08/31　　 齐振杰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<TradeKeyword> queryBwListByKeyword(String keywords);
	
	
	/**
	 * 更新产品修改时间  便于重做索引
	 * @param tradeId
	 */
	public Integer updateGmtModefiled(Integer tradeId);
	
	/**
	 * 根据code和随机数连续取5条供应信息
	 * @param code
	 * @param random
	 * @return
	 */
	public List<TradeSupply> queryRandomSupply(String code, Integer random);
	
	/**
	 * 函数名称：queryCategoryByCid
	 * 功能描述：通过cid查询category_code
	 * 输入参数：cid
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/10/25　　 怀欣冉 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<TradeSupply> queryCategoryByCid(Integer cid);
   
	public TradeSupply querySupplyOmitDetails(Integer id);
	
	/**
	 * 根据id查询supply应用于solr
	 * @param id
	 * @return
	 */
	public TradeSupply querySolrSupplybyId(Integer id);
	
	/**
	 * 查询信息对应的图片
	 * @param id
	 * @return
	 */
	public String queryPhotoCover(Integer id);
	
	/**
	 * 统计各种状态下供求数据数量
	 * @param cid
	 * @param pauseStatus
	 * @param overdueStatus
	 * @param checkStatus
	 * @return
	 */
	public Integer countSupplysOfCompanyByStatus(Integer cid, Integer pauseStatus, Integer overdueStatus, Integer checkStatus,Integer groupId);

	/**
	 * 函数名称：querySimpleDetailsById
	 * 功能描述：通过id查询产品简要信息
	 * 输入参数：id
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2013/01/25　　 齐振杰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public TradeSupply querySimpleDetailsById(Integer id);
	
	public Integer countForCidAndDate(Integer companyId,String from,String to);
	
	/**
	 * 检索指定cid公司下，标题为title的供应的信息的条数
	 * @param companyId
	 * @param title
	 * @return
	 */
	public Integer countForCidAndTitle(Integer companyId,String title);
	
	public Integer updatePhotoCover(Integer id, String photoCover);

	/**
	 * 修改图片或者上传图片的时候审核状态为未审核
	 * @param id
	 * @return
	 */
	public Integer updateUncheckByIdForMyesite(Integer id);
}