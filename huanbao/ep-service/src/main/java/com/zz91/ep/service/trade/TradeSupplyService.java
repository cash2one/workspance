/*
 * 文件名称：TradeSupplyService.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.service.trade;

import java.util.List;
import java.util.Map;

import com.zz91.ep.domain.trade.TradeKeyword;
import com.zz91.ep.domain.trade.TradeSupply;
import com.zz91.ep.dto.CommonDto;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.search.SearchSupplyDto;
import com.zz91.ep.dto.trade.SupplyMessageDto;
import com.zz91.ep.dto.trade.TradeSupplyDto;
import com.zz91.ep.dto.trade.TradeSupplyNormDto;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作Service层
 * 模块描述：供应信息接口。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public interface TradeSupplyService {

	public static final int MAX_SIZE = 10;

	public static final int STATUS_PAUSE_YES = 1;

	public static final int STATUS_PAUSE_NO = 0;

	public static final int STATUS_OVERDUE_YES = 0;

	public static final int STATUS_OVERDUE_NO = 1;

	public static final int STATUS_CHECK_UN = 0;

	public static final int STATUS_CHECK_YES = 1;

	public static final int STATUS_CHECK_NO = 2;
	
	public static final int PUB_LIMIT = 20;
	/**
	 * 函数名称：querySupplyCountByCategory
	 * 功能描述：查询所有的不同类别资讯信息数量（页面片段缓存）
	 * 输入参数：
	 *        @param code 查询用的类别名（前匹配）
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
     * 		  @param size Integer 数量
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<CommonDto> querySupplysByRecommend(Integer size);

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
	 * 函数名称：querySupplysByIds
	 * 功能描述：根据一组id查询供应信息
	 * 输入参数：
     * 		  @param size Integer 数量
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<TradeSupplyDto> querySupplysByIds(Integer[] ids,Integer flag);

	/**
	 * 函数名称：pageSupplyByCompany
	 * 功能描述：公司求购信息列表
	 * 输入参数：@param group 不同分组
	 * 　　　　　@param keywords 关键字
	 * 异　　常：
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public PageDto<TradeSupply> pageSupplyByCompany(Integer group, String keywords, Integer cid, PageDto<TradeSupply> page);

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
	 * 功能描述：查询供应信息
	 * 输入参数：@param cid 公司ID
	 * 　　　　　@param size 条数
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public TradeSupplyDto queryDetailsById(Integer id);
	

	/**
	 * 根据供应信息ID更新供应信息缩略图（封面图片）
	 */
	public Integer updatePhotoCoverById(Integer id, String photoCover,
			Integer cid);
	
	/**
	 * 
	 * 函数名称：createTradeSupply
	 * 功能描述：发布供应信息
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer createTradeSupply(TradeSupply tradeSupply);
	
	/**
	 * 
	 * 函数名称：pageSupplyByConditions
	 * 功能描述：根据不同条件筛选供应信息（分页显示）
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public PageDto<SupplyMessageDto> pageSupplyByConditions(Integer cid,
			Integer pauseStatus, Integer overdueStatus, Integer checkStatus, Integer recommend,
			Integer groupId, PageDto<SupplyMessageDto> page);
	
	/**
     * 
     * 函数名称：deleteSupplyById
     * 功能描述：根据id删除供应信息
     * 输入参数：@param test1 参数1
     * 　　　　　.......
     * 　　　　　@param test2 参数2
     * 异　　常：[按照异常名字的字母顺序]
     * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
     * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
     */
	public Integer deleteSupplyById(Integer id, Integer cid);
	
	/**
	 * 函数名称：updateSupplyGroupIdById
	 * 功能描述：根据一组供应信息编号更新供应信息分组编号
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
	public TradeSupplyDto queryUpdateSupplyById(Integer id, Integer cid);
	
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
	 * 函数名称：queryImpTradeSupply
	 * 功能描述：查询导入数据
	 * 输入参数：@param maxId 外网导入数据最大ID
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<TradeSupply> queryImpTradeSupply(Integer maxId);

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
	 * 函数名称：queryBwListByKeyword
	 * 功能描述：查询标王关键字列表
	 * 输入参数：@param keywords 购买关键字
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

//	/**
//	 * 加载
//	 * @param page
//	 * @return
//	 */
//	public PageDto<TradeSupplySearchDto> loadCompanyMainProduct(PageDto<TradeSupplySearchDto> page);
	
	/**
	 * 函数名称：queryCategoryByCid
	 * 功能描述：根据cid查询category_code
	 * 输入参数：cid
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/10/25　　 怀欣冉 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<TradeSupply> queryCategoryByCid(Integer cid);
	
	/**
	 * solr查询供应信息
	 * @param search
	 * @param page
	 * @return
	 */
	public PageDto<TradeSupplyNormDto> searchSolrSupply(SearchSupplyDto search,PageDto<TradeSupplyNormDto> page,String sort);
	
	/**
	 * 查询相关supply
	 * @param search
	 * @param page
	 * @param sort
	 * @return
	 */
	public PageDto<TradeSupplyNormDto> searchSupplyByCategory(SearchSupplyDto search,PageDto<TradeSupplyNormDto> page,String sort);
	
	/**
	 * 列表supply
	 * @param search
	 * @param page
	 * @param sort
	 * @return
	 */
	public PageDto<TradeSupplyNormDto> searchListSupply(SearchSupplyDto search,PageDto<TradeSupplyNormDto> page,String sort); 
	
	/**
	 * 查询信息对应的图片
	 * @param id
	 * @return
	 */
	public String queryPhotoCover(Integer id);
	
	/**
	 * 统计公司供求信息
	 * @param cid
	 * @return
	 */
	public Map<String,Integer> countSupplyByCompany(Integer cid,Integer groupId);

	/**
	 * 函数名称：querySimpleDetailsById
	 * 功能描述：根据id查询产品简要信息
	 * 输入参数：cid
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2013/01/ 　　 　齐振杰　 　       1.0.0　　 　　 创建方法函数
	 */
	public TradeSupply querySimpleDetailsById(Integer id);
	
	/**
	 * 函数名称 : validateToPub
	 * 功能描述 : 根据用户company_id(必须是普会用户)判断该用户是否超过了普会发布信息的限制。暂定限制为20条
	 * 参数输入 : companyId
	 * 返回说明 : 返回true，则说明没有达到限制标准，可以继续发布供求，返回其他，则表示不能发布供求
	 */
	public Boolean validateToPub(Integer companyId);
	
	/**
	 * 函数名称 : validateTitleRepeat
	 * 功能描述 : 检验改用户发布的供应信息标题是否重复
	 * 参数输入 : title、companyId
	 * 返回说明 : true，说明用户可以以此标题发布信息 ；false，说明用户已经发布过该标题，不能再发布
	 * @param companyId
	 * @param title
	 * @return
	 */
	public Boolean validateTitleRepeat(Integer companyId,String title);
	/**
	 * 函数名称 : updatePhotoCover
	 * 功能描述 : 根据id修改photoCover
	 * 参数输入 : id
	 *  @param id
	 * 
	 * 
	 */
	public Integer updatePhotoCover(Integer id, String photoCover);
	

	/**
	 * 使用coreseek搜索引擎 搜索 供应信息解决solr的错误
	 * @param tradeSupplyNormDto
	 * @param page
	 * @return
	 */
	public PageDto<TradeSupplyNormDto> pageBySearchEngine(SearchSupplyDto search,PageDto<TradeSupplyNormDto> page);
	
	public PageDto<TradeSupplyNormDto> pageBySearchEngineTrade(SearchSupplyDto search,PageDto<TradeSupplyNormDto> page);

	/**
	 * //修改图片或者上传图片的时候审核状态为未审核
	 * @param targetId
	 */
	public Integer  updateUncheckByIdForMyesite(Integer targetId);
	
}