/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-15
 */
package com.zz91.ep.admin.service.comp;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.domain.comp.WebsiteStatistics;
import com.zz91.ep.domain.crm.CrmCompany;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.comp.CompProfileDto;

/**
 * @author totly
 *
 * created on 2011-9-15
 */
public interface CompProfileService {
	
	final static String IS_DELETE = "1";
    final static String IS_NO_DELETE = "0";

	/**
	 * 创建公司信息
	 * @param comp
	 * @return
	 */
	 public Integer insertCompProfile(CompProfile compProfile);
	 
	 /**
	  * 更新公司信息
	  * @param compProfile
	  * @return
	  */
	 public Integer updateCompProfile(CompProfile compProfile);
    /**
     * 根据公司ID查询公司的基本信息
     */
    public CompProfile queryCompProfileById(Integer cid);

    /**
     * 根据公司ID查询公司的简介
     */
//    public String queryCompDetailsById(Integer cid);

    /**
     * 通过公司ID查询会员code
     * @param companyId
     * @return
     */
//    public String getMemberCodeByCompanyId(Integer companyId);

    /**
     * 更新公司基本信息
     * @param comp
     * @return
     */
//    public Integer updateBaseCompProfile(CompProfile comp);
    
    public CompProfile queryFullProfile(Integer cid);

	
	/**
	 * 根据公司id查询公司详细信息
	 * 主要用于查看联系方式
	 * @return
	 */
//	public CompProfileDto queryDetailsByCompanyId(Integer compId);
	
	//以下接口用于公司库后台
	
	public CompProfileDto builDto(CompProfile profile);
	/**
	 * 公司库分页（后台使用）
	 * @param email 
	 * @param recommendCode 
	 * @param mainSupply 
	 * @param mainBuy 
	 * @param subnetCategory 
	 * @param chainId 
	 */
	public  PageDto<CompProfileDto> pageCompDetails(PageDto<CompProfileDto> page,String name,String memberCode,String from,String to,
			String registerCode,String phone,String businessCode,String industryCode,Integer delStatus,
			String memberCodeBlock, String email, String recommendCode, String account,Integer otherSearch,
			Short serviceType, String subnetCategory,Integer messagetime, Short chainId);
	/**
	 * 更新MeberCodeBlock
	 * @param cid
	 * @param block
	 * @return
	 */
//	public Integer updateMeberCodeBlockById(Integer cid,String block);

	/**
	 * 公司库搜索
	 * @param search
	 * @param page
	 * @return
	 * @throws SolrServerException 
	 */
//	public PageDto<CompanySearchDto> pageCompanyBySearch(SearchCompanyDto search,
//			PageDto<CompanySearchDto> page) throws SolrServerException;

//	public List<CompProfile> queryCompProfileByCategory(String parentCode, Integer size);

	public List<CompProfileDto> queryCompanyByEmail(String email);
	/**
	 * 更新删除状态
	 * @param id
	 * @param delStatus
	 * @return
	 */
	public Integer updateDelStatus(Integer id,Integer delStatus);
	   /**
     * 更新冻结
     * @param id
     * @param mobile
     * @param phone
     * @return
     * 
     */
    public Integer updateMemberCodeBlock(Integer id, String memberCodeBlock);

	/**
	 * 内网CRM每天取更新数据
	 * @param date 
	 * @param limit 
	 * @param start 
	 * @return
	 */
	public List<CrmCompany> queryTodayUpdateCompany(Date date, Integer start, Integer limit) throws ParseException;

	public Integer updateMemberCode(String code, Integer companyId);

	public Boolean validateDomain(Integer companyId, String domain);

	public List<CompProfile> queryNewestComp(Integer size, String week);
	/**
	 * 推荐公司
	 * @param id
	 * @param type
	 * @return
	 */
	public Integer updateCompRecommend(Integer id, String type);

	/**
	 * 取消推荐公司
	 * @param id
	 * @return
	 */
	public Integer cancelRecommendComp(Integer id);

	/**
	 * 查询相同联系方式的公司
	 * @param id
	 * @param mobile
	 * @param phone
	 * @return
	 */
	public List<CompProfileDto> queryCommonCompByContacts(Integer id);

//	public EsiteMemberDto queryMemberInfoByCid(Integer cid);

//	public Integer updateMainProduct(Integer companyId,
//			String mainProductSupply, String mainProductBuy);

//	public Integer queryWebsiteConfigCount(Integer companyId);

//	public Integer updateCompanyStyle(WebsiteProfile profile);

//	public Integer insertCompanyStyle(WebsiteProfile profile);

//	public String queryCssStyleByCid(Integer companyId);

	public Integer queryMaxId();
	
	/**
	 * 网站数据统计（后台使用）
	 */
	public  PageDto<WebsiteStatistics> pageWebsiteStatistics(PageDto<WebsiteStatistics> page);

	/**
	 * @param date2
	 * @return
	 */
	public Integer queryTodayUpdateCompanyCount(Date date);

	/**
	 * 查询公司信息(用于crm数据导入)
	 * @param start
	 * @param limit
	 * @param from
	 * @param to
	 * @return
	 */
	public List<CrmCompany> queryCompProfile(Integer start, Integer limit,
			String from, String to);
	/**
	 * 更新公司修改时间
	 * @param id
	 * @return
	 */
	public Integer updateProfileGmtModified(Integer id);
}