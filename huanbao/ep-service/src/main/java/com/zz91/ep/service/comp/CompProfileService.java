/*
 * 文件名称：CompProfileService.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.service.comp;

import java.util.List;
import java.util.Map;

import com.zz91.ep.domain.comp.CompAccount;
import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.dto.StatisticsDto;
import com.zz91.ep.dto.comp.CompProfileDto;
import com.zz91.ep.dto.comp.RegistDto;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作Service层
 * 模块描述：公司信息接口。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public interface CompProfileService {
	
	public final static String DEFAULT_MEMERCODE = "10011000";// 普通会员code
	public final static String DEFAULT_MEMERNAME = "普通会员";// 普通会员
	
	public final static String PAY_MEMERCODE = "10011001"; // 中环通会员code
	public final static String PAY_MEMERNAME = "付费会员";// 付费会员
	
	/**
	 * 函数名称：queryNewestCompany
	 * 功能描述：查询最新注册公司信息
	 * 输入参数：
	 * 		   @param industryCode 行业类别 
	 * 		   @param size Integer 数量
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */                     
	public List<CompProfile> queryNewestCompany(String industryCode, Integer size);

	/**
	 * 函数名称：queryRecommendCompany
	 * 功能描述：查询各种推荐公司信息
	 * 输入参数：
	 * 		  @param categoryCode String 推荐类别
	 *        @param size Integer 数量
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<CompProfile> queryRecommendCompany(String categoryCode, Integer size);

	/**
	 * 函数名称：queryContactByCid
	 * 功能描述：查询各种推荐公司联系信息
	 * 输入参数：
	 * 		  @param categoryCode String 推荐类别
	 *        @param size Integer 数量
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public CompProfileDto queryContactByCid(Integer cid);

	/**
	 * 函数名称：queryMemberInfoByCid
	 * 功能描述：根据公司ID查询公司基本信息
	 * 输入参数：@param cid 公司ID
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public CompProfileDto queryMemberInfoByCid(Integer cid);

	/**
	 * 
	 * 函数名称：regist
	 * 功能描述：注册用户
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Boolean regist(CompProfile profile, CompAccount account);
	
	/**
	 * 
	 * 函数名称：updateRegistInfo
	 * 功能描述：更新注册信息
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	@Deprecated
	public boolean updateRegistInfo(RegistDto registDto,Integer compProfileId,Integer [] industryChain);
	/**
	 * 
	 * 函数名称：getCompProfileById
	 * 功能描述：获取公司信息
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public CompProfile getCompProfileById(Integer id);
	
	/**
	 * 
	 * 函数名称：statisticsMessage
	 * 功能描述：统计信息
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 * 　　　　　2012/10/10　　 黄怀清　　　　　　　1.0.1			现在审核后供应数和总供应数用的同一个,所以新增审核数
	 */
	public StatisticsDto statisticsMessage(Integer uid);
	
	/**
	 * 
	 * 函数名称：updateBaseCompProfile
	 * 功能描述: 更新基本信息
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Boolean updateBaseCompProfile(CompProfile comp ,Integer []industryChain);
	
	/**
	 * 函数名称：queryImpCompProfile
	 * 功能描述：导入数据查询
	 * 输入参数：@param maxId 外网导入数据最大ID
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<CompProfile> queryImpCompProfile(Integer maxId);
	
	/**
	 * 函数名称：updateMainProduct
	 * 功能描述：修改公司主营业务
	 * 输入参数：@param cid 
	 *         @param mainProductSupply
	 *         @param mainProductSupply
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer updateMainProduct(Integer cid,String mainProductSupply, String mainProductBuy);
	
	/**
	 * 函数名称：queryCssStyleByCid
	 * 功能描述：查询公司显示样式
	 * 输入参数：@param cid 公司id
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public String queryCssStyleByCid(Integer companyId);
	
	/**
	 * 函数名称：updateStyle
	 * 功能描述：门市部装修-模版更改
	 * 输入参数：@param cid 公司id
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer updateStyle(String type, Integer cid);
	
//	/**
//	 * 函数名称：queryIdByDomainOrDomainTwo
//	 * 功能描述：根据顶级或二级域名查找公司id
//	 * 输入参数：@param domain 顶级 
//	 * 		   @param domain_two 二级
//	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
//	 * 　　　　　2012/08/02　　 黄怀清 　　 　　 　 1.0.0　　 　　 创建方法函数
//	 * 		   2013/01/14     齐振杰           1.0.1        修改方法
//	 */
//	public Integer queryIdByDomainOrDomainTwo(String domain,String domainTwo);
	
	
	 /**
     * 根据公司ID查询公司的基本信息
     * @author 陈庆林
     * 
     */
    public CompProfile queryCompProfileById(Integer cid);
    
    /**
     * 修改公司的时间 重做公司索引
     * @param cid
     */
    public void updateGmtModified(Integer cid);
    
    /**
	 * 函数名称：queryShortCompProfileById
	 * 功能描述：根据公司id查询简单公司信息
	 * 输入参数：@param 公司id
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/09/28　　 怀欣冉 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
    public CompProfile queryShortCompProfileById(Integer id);
    
    /**
     * 查询公司名称
     * @param id
     * @return
     */
    public String  queryNameById(Integer id);

	/**
	 * @param cid
	 * @return
	 */
	public Integer queryCompCountById(Integer cid);

	/**
	 * 函数名称：queryMemberCodeById
	 * 功能描述：根据公司id查询会员code
	 * 输入参数：@param id
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2013/01/11　　 齐振杰 　　 　　 　1.0.0　　 　　创建方法函数
	 */
	public String queryMemberCodeById(Integer id);
	
	public Integer initCid(String serverName, Integer cid, Map<String, Object> out);
	
	public boolean initEsite(Integer cid, String serverName, Map<String, Object> out);
	
	/**
	 * 
	 * 函数名称：regists
	 * 功能描述：注册用户
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2013/08/27　　  方潮 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Boolean regists(CompProfile profile, CompAccount account);
}