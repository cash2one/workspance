/*
 * 文件名称：CompProfileDao.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.dao.comp;

import java.util.List;

import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.domain.comp.WebsiteProfile;
import com.zz91.ep.dto.comp.CompProfileDto;
import com.zz91.ep.dto.comp.CompProfileNormDto;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作DAO层
 * 模块描述：公司信息接口。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public interface CompProfileDao {
	
	/**
	 * 函数名称：queryNewestCompany
	 * 功能描述：查询最新注册公司信息
	 * 输入参数：
	 *         @param industryCode 行业类别
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
	 * 　　　　　2013/05/31　　  方潮 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<CompProfile> queryRecommendCompany(String categoryCode, Integer size);

	/**
	 * 函数名称：updateMessageCountById
	 * 功能描述：更新留言数
	 * 输入参数：
	 * 		
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer updateMessageCountById(Integer id);

	/**
	 * 函数名称：queryContactByCid
	 * 功能描述：查询联系方式
	 * 输入参数：
	 * 		
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public CompProfileDto queryContactByCid(Integer cid);
	
	/**
	 * 函数名称：queryCompProfileDtoById
	 * 功能描述：根据公司ID查询公司信息
	 * 输入参数：@param cid 公司ID
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public CompProfileDto queryMemberInfoByCid(Integer cid);
	
	/**
	 * 函数名称：insertCompProfile
	 * 功能描述：插入公司信息
	 * 输入参数：@param cid 公司ID
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
    public Integer insertCompProfile(CompProfile compProfile);
    
    /**
     * 
     * 函数名称：updateBaseCompProfile
     * 功能描述：修改公司信息
     * 输入参数：@param test1 参数1
     * 　　　　　.......
     * 　　　　　@param test2 参数2
     * 异　　常：[按照异常名字的字母顺序]
     * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
     * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
     */
	public Integer updateBaseCompProfile(CompProfile comp) ;
	/**
	 * 
	 * 函数名称：getCompProfile
	 * 功能描述：根据id查询公司所有信息
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public CompProfile getCompProfileById(Integer id);
	

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
	public Integer updateMainProduct(Integer cid, String mainProductSupply,
			String mainProductBuy);
	
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
	 * 函数名称：queryWebsiteConfigCount
	 * 功能描述：查看是否存在用户配置
	 * 输入参数：@param cid 公司id
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer queryWebsiteConfigCount(Integer companyId);
	
	/**
	 * 函数名称：updateCompanyStyle
	 * 功能描述：更新公司显示样式
	 * 输入参数：@param cid 公司id
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer updateCompanyStyle(WebsiteProfile profile);
	
	/**
	 * 函数名称：insertCompanyStyle
	 * 功能描述：添加公司显示样式
	 * 输入参数：@param cid 公司id
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer insertCompanyStyle(WebsiteProfile profile);
	/**
	 * 函数名称：queryIdByDomainOrDomainTwo
	 * 功能描述：根据二级域名查找公司id
	 * 输入参数：@param 公司domain_two
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/08/02　　 黄怀清 　　 　　 　 1.0.0　　 　　 创建方法函数
	 *         2013/01/14     齐振杰            1.0.1        修改方法
	 */
	public Integer queryIdByDomainOrDomainTwo(String domain,String domainTwo);
	
	/**
     * 根据公司ID查询公司的基本信息
     */
    public CompProfile queryCompProfileById(Integer cid);
    
    /**
     * 验证是否能正常登陆
     * @param account
     * @return
     */
    public Integer isDelStatus(Integer cid);
    
    
    public String getMemberCodeById(Integer id);

    /**
	 * 函数名称：updateGmtModifiedById
	 * 功能描述：更新最后修改时间
	 * 输入参数：@param cid 公司id
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/09/25　　 齐振杰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer updateGmtModifiedById(Integer cid);
	
	/**
	 * 函数名称：queryShortCompProfileById
	 * 功能描述：根据公司id查询简单的公司信息
	 * 输入参数：@param 公司id
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/09/28　　 怀欣冉　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public CompProfile queryShortCompProfileById(Integer id);
	
	public CompProfileNormDto queryProfileWithAccount(Integer id);
	
	public String queryNameById(Integer id);

	/**
	 * @param cid
	 * @return
	 */
	public Integer queryCompCountById(Integer cid);

	/**
	 * 函数名称：queryMemberCodeById
	 * 功能描述：根据公司id查询会员code
	 * 输入参数：@param 公司id
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2013/01/11　　 齐振杰　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public String queryMemberCodeById(Integer id);
	
//	public CompProfile queryProfileById(Integer id);
	
	public WebsiteProfile querySiteProfile(Integer cid);
    
	/**
	 * 函数名称：insertCompProfile
	 * 功能描述：插入公司信息
	 * 输入参数：@param cid 公司ID
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2013/08/27　　  方潮　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
    public Integer insertCompProfiles(CompProfile compProfile);
}