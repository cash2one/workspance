/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-13
 */
package com.zz91.ep.admin.dao.comp;

import java.util.List;

import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.domain.comp.CompRecommend;
import com.zz91.ep.domain.comp.WebsiteStatistics;
import com.zz91.ep.domain.crm.CrmCompany;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.comp.CompProfileDto;

/**
 * @author totly
 *
 * created on 2011-9-13
 */
public interface CompProfileDao {

    /**
     * 根据公司ID查询公司的基本信息
     */
    public CompProfile queryCompProfileById(Integer cid);

    /**
     * 根据公司ID查询公司的简介
     */
//    public String queryCompDetailsById(Integer cid);
    
    /**
     * 更新公司留言信息条数
     */
    public Integer updateMessageCountById(Integer cid);

    /**
     * 通过公司名称查询公司信息
     * @param companyName
     * @return
     */
    public CompProfile queryCompProfileByName(String companyName);

    /**
     * 更新公司信息
     * @param compProfile
     * @return
     */
    public Integer updateCompProfile(CompProfile compProfile);

    /**
     * 通过公司名字查询公司ID
     * @param companyName
     * @return
     */
    public Integer queryCidByName(String companyName);

    /**
     * 保存公司信息对象
     * @param compProfile
     */
    public Integer insertCompProfile(CompProfile compProfile);

    /**
     * 更新公司基本信息
     * @param comp
     * @return
     */
//    public Integer updateBaseCompProfile(CompProfile comp);
    
    public CompProfile queryFullProfile(Integer cid);
    /**
     * 根据公司id查询详细信息和联系方式
     * @param compId
     * @return
     */
//    public CompProfileDto queryDetailsByCompId(Integer compId);

//	public Integer queryCidByDomain(String domain);
	
	//以下为后台接口
	
	public List<CompProfileDto> queryCompProfile(PageDto<CompProfileDto> page,CompProfile profile,String from,String to,String phone,String email, 
			String recommendCode, String account,Integer otherSearch, String subnetCategory,Integer messagetime, Short chainId);
	
	public Integer queryCompProfileCount(CompProfile profile,String from,String to,String phone,String email, 
			String recommendCode, String account,Integer otherSearch, String subnetCategory,Integer messagetime, Short chainId);
	
	public Integer updateMeberCodeBlockById(Integer cid,String block);

//	public List<CompProfile> queryCompProfileByCategory(String parentCode,Integer size);

	public List<CompProfileDto> queryCompanyByEmail(String email);
	
	public Integer updateDelStatus(Integer id, Integer delStatus);
	
	/**
     * 根据memberCodeBlock的变换更新冻结
     * @param id
     * @param mobile
     * @param phone
     * @return
     * 
     */
	public Integer updateMemberCodeBlock(Integer id, String memberCodeBlock);

	public List<CrmCompany> queryTodayUpdateCompany(String dateStr, Integer start, Integer limit);

	public Integer openZhtWithUpdateMemberCodeAndDomainTwo(
			CompProfile compProfile);

	public Integer queryCidByDomainTwo(String domain);

	public List<CompProfile> queryNewestComp(Integer size,String week);

	public Integer cancelRecommendComp(Integer id);

	public Integer insertCompRecommend(Integer id, String type);

	public CompRecommend queryRecommendByCidAndType(Integer id, String type);

	/**
	 * 查询相同联系方式的公司信息
	 * @param id
	 * @param mobile
	 * @param phone
	 * @return
	 */
	public List<CompProfileDto> queryCommCompanyByContact(Integer id,
			String mobile, String phone);

//	public EsiteMemberDto queryMemberInfoByCid(Integer cid);

//	public Integer updateMainProduct(Integer cid, String mainProductSupply,
//			String mainProductBuy);

//	public Integer insertCompanyStyle(WebsiteProfile profile);

//	public Integer queryWebsiteConfigCount(Integer companyId);

//	public Integer updateCompanyStyle(WebsiteProfile profile);

//	public String queryCssStyleByCid(Integer companyId);

	/**
	 * 关闭服务 更新member_code
	 * @param companyId
	 * @param code
	 * @return
	 */
	public Integer updateMeberCodeById(String code,Integer companyId);

	public Integer queryMaxId();

	public List<WebsiteStatistics> queryWebsiteStatistics(PageDto<WebsiteStatistics> page);

	public Integer queryWebsiteStatisticsCount();

	/**
	 * @param dateStr
	 * @return
	 */
	public Integer queryTodayUpdateCompanyCount(String dateStr);

	/**
	 * 查询公司信息(标王使用)
	 * @param cid
	 * @return
	 */
	public CompProfile queryShortCompDetailsById(Integer cid);

	/**
	 * @param start
	 * @param limit
	 * @param from
	 * @param to
	 * @return
	 */
	public List<CrmCompany> queryCrmCompany(Integer start, Integer limit,
			String from, String to);

	
	public CompProfile querySimpProfileById(Integer cid);

	/**
	 * 关闭seo服务(domain_two清空)
	 * @param cid
	 * @return
	 */
	public Integer closeSeoSvr(Integer cid);
	/**
	 * 刷新公司更新时间
	 * @param id
	 * @return
	 */
	public Integer updateProfileGmtModified(Integer id);
}