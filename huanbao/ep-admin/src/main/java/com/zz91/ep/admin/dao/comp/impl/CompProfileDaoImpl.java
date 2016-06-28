/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-13
 */
package com.zz91.ep.admin.dao.comp.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.comp.CompProfileDao;
import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.domain.comp.CompRecommend;
import com.zz91.ep.domain.comp.WebsiteProfile;
import com.zz91.ep.domain.comp.WebsiteStatistics;
import com.zz91.ep.domain.crm.CrmCompany;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.comp.CompProfileDto;
import com.zz91.ep.dto.comp.EsiteMemberDto;
import com.zz91.util.lang.StringUtils;

/**
 * @author totly
 *
 * created on 2011-9-13
 */
@Repository("compProfileDao")
public class CompProfileDaoImpl extends BaseDao implements CompProfileDao {

    final static String SQL_PREFIX = "compProfile";

//    @Override
//    public String queryCompDetailsById(Integer cid) {
//        return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX,"queryCompDetailsById"),cid);
//    }

    @Override
    public CompProfile queryCompProfileById(Integer cid) {
        return (CompProfile) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX,"queryCompProfileById"), cid);
    }

    @Override
    public Integer updateMessageCountById(Integer cid) {
        return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateMessageCount"), cid);
    }

	@Override
	public Integer queryCidByName(String companyName) {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCidByName"), companyName);
	}

	@Override
	public CompProfile queryCompProfileByName(String companyName) {
		return (CompProfile) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCompProfileByName"), companyName);
	}

	@Override
	public Integer updateCompProfile(CompProfile compProfile) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateCompProfile"), compProfile);
	}

	@Override
	public Integer insertCompProfile(CompProfile compProfile) {
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertCompProfile"), compProfile);
	}

//	@Override
//	public Integer updateBaseCompProfile(CompProfile comp) {
//		return (Integer)getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateBaseCompProfile"), comp);
//	}

	@Override
	public CompProfile queryFullProfile(Integer cid) {
		return (CompProfile) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryFullProfile"), cid);
	}

//	@Override
//	public CompProfileDto queryDetailsByCompId(Integer compId) {
//		return (CompProfileDto) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryDetailsByCompanyId"), compId);
//	}

//	@Override
//	public Integer queryCidByDomain(String domain) {
//		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCidByDomain"), domain);
//	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CompProfileDto> queryCompProfile(PageDto<CompProfileDto> page,
			CompProfile compProfile,String from,String to,String phone, String email, String recommendCode ,
			String account,Integer otherSearch,String subnetCategory,Integer messagetime,Short chainId) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("page", page);
		root.put("compProfile", compProfile);
		root.put("from", from);
        root.put("to", to);
		root.put("phone", phone);
		root.put("email", email);
		root.put("categoryCode", recommendCode);
		root.put("account", account);
		root.put("otherSearch", otherSearch);
		root.put("subnetCategory", subnetCategory);
		root.put("messagetime", messagetime);
		root.put("chainId", chainId);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryCompProfile"), root);
	}

	@Override
	public Integer queryCompProfileCount(CompProfile compProfile,String from,String to,String phone,String email, 
			String recommendCode ,String account,Integer otherSearch,String subnetCategory,Integer messagetime,Short chainId) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("compProfile", compProfile);
		root.put("from", from);
		root.put("to", to);
		root.put("phone", phone);
        root.put("email", email);
		root.put("categoryCode", recommendCode);
		root.put("account", account);
		root.put("otherSearch", otherSearch);
		root.put("subnetCategory", subnetCategory);
		root.put("messagetime", messagetime);
		root.put("chainId", chainId);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCompProfileCount"), root);
	}

	@Override
	public Integer updateMeberCodeBlockById(Integer cid, String block) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("cid", cid);
		root.put("block", block);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateMeberCodeBlockById"), root);
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<CompProfile> queryCompProfileByCategory(String parentCode,Integer size) {
//		Map<String, Object> root=new HashMap<String, Object>();
//		root.put("parentCode", parentCode);
//		root.put("size", size);
//		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryCompProfileByCategory"), root);
//	}

	@SuppressWarnings("unchecked")

	@Override
	public List<CompProfileDto> queryCompanyByEmail(String email) {
		return (List<CompProfileDto>) getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryCompanyByEmail"), email);
	}
	public Integer updateDelStatus(Integer id, Integer delStatus) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("id", id);
		root.put("delStatus", delStatus);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateDelStatus"), root);
	}
	@Override
    public Integer updateMemberCodeBlock(Integer id, String memberCodeBlock) {
	    Map<String, Object> root=new HashMap<String, Object>();
        root.put("id", id);
        root.put("memberCodeBlock", memberCodeBlock);
        return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateMemberCodeBlock"), root);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<CrmCompany> queryTodayUpdateCompany(String dateStr,Integer start, Integer limit) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("date", dateStr);
		root.put("start", start);
		root.put("limit", limit);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryTodayUpdateCompany"),root);
	}
	@Override
	public Integer openZhtWithUpdateMemberCodeAndDomainTwo(
			CompProfile compProfile) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "openZhtWithUpdateMemberCodeAndDomainTwo"), compProfile);
	}

	@Override
	public Integer queryCidByDomainTwo(String domainTwo) {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCidByDomainTwo"), domainTwo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CompProfile> queryNewestComp(Integer size,String week) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("size", size);
		root.put("week", week);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryNewestComp"),root);
	}

	@Override
	public Integer cancelRecommendComp(Integer id) {
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "cancelRecommendComp"), id);
	}

	@Override
	public Integer insertCompRecommend(Integer id, String type) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("cid", id);
		root.put("categoryCode", type);
		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertCompRecommend"), root);
	}

	@Override
	public CompRecommend queryRecommendByCidAndType(Integer id, String type) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("cid", id);
		root.put("categoryCode", type);
		return (CompRecommend) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryRecommendByCidAndType"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CompProfileDto> queryCommCompanyByContact(Integer id,
			String mobile, String phone) {
		Map<String, Object> root = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(mobile)){
			if (mobile.length() > 11) {
				root.put("mobile", mobile.substring(mobile.length()-11, mobile.length()));
			} else {
				root.put("mobile", mobile);
			}
		}
		if (StringUtils.isNotEmpty(phone)){
			if (phone.length() > 7) {
				root.put("phone", phone.substring(phone.length()-7, phone.length()));
			} else {
				root.put("phone", phone);
			}
		}
		root.put("id", id);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryCommCompanyByContact"), root);
	}

//	@Override
//	public EsiteMemberDto queryMemberInfoByCid(Integer cid) {
//		return (EsiteMemberDto) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryMemberInfoByCid"),cid);
//	}

//	@Override
//	public Integer updateMainProduct(Integer cid, String mainProductSupply,
//			String mainProductBuy) {
//		Map<String, Object> root = new HashMap<String, Object>();
//		root.put("cid", cid);
//		root.put("mainProductSupply", mainProductSupply);
//		root.put("mainProductBuy", mainProductBuy);
//		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateMainProduct"), root);
//	}

//	@Override
//	public Integer insertCompanyStyle(WebsiteProfile profile) {
//		return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertCompanyStyle"), profile);
//	}

//	@Override
//	public Integer queryWebsiteConfigCount(Integer companyId) {
//		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryWebsiteConfigCount"), companyId);
//	}

//	@Override
//	public Integer updateCompanyStyle(WebsiteProfile profile) {
//		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateCompanyStyle"), profile);
//	}

//	@Override
//	public String queryCssStyleByCid(Integer companyId) {
//		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCssStyleByCid"), companyId);
//	}

	@Override
	public Integer updateMeberCodeById(String code, Integer companyId) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("code", code);
		root.put("cid", companyId);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateMeberCodeById"), root);
	}

	@Override
	public Integer queryMaxId() {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryMaxId"));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WebsiteStatistics> queryWebsiteStatistics(PageDto<WebsiteStatistics> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryWebsiteStatistics"), root);
	}

	@Override
	public Integer queryWebsiteStatisticsCount() {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryWebsiteStatisticsCount"));
	}

	@Override
	public Integer queryTodayUpdateCompanyCount(String dateStr) {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryTodayUpdateCompanyCount"), dateStr);
	}

	@Override
	public CompProfile queryShortCompDetailsById(Integer cid) {
		return (CompProfile) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryShortCompDetailsById"),cid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CrmCompany> queryCrmCompany(Integer start, Integer limit,
			String from, String to) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("from", from);
		root.put("to", to);
		root.put("start", start);
		root.put("limit", limit);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryCrmCompany"), root);
	}

	@Override
	public CompProfile querySimpProfileById(Integer cid) {
		return (CompProfile) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "querySimpProfileById"), cid);
	}

	@Override
	public Integer closeSeoSvr(Integer cid) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "closeSeoSvr"), cid);
	}
	
	@Override
	public Integer updateProfileGmtModified(Integer id) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateProfileGmtModified"), id);
	}

    
}