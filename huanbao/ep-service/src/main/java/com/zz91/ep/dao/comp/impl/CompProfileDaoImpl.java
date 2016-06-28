/*
 * 文件名称：CompProfileDaoImpl.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.dao.comp.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;


import com.zz91.ep.dao.common.impl.BaseDao;
import com.zz91.ep.dao.comp.CompProfileDao;
import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.domain.comp.WebsiteProfile;
import com.zz91.ep.dto.comp.CompProfileDto;
import com.zz91.ep.dto.comp.CompProfileNormDto;

/**
 * 项目名称：中国环保网 模块编号：数据操作DAO层 模块描述：公司信息相关数据操作 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 * 　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Repository("compProfileDao")
public class CompProfileDaoImpl extends BaseDao implements CompProfileDao {

	final static String SQL_PREFIX = "compprofile";

	@SuppressWarnings("unchecked")
	@Override                                          
	public List<CompProfile> queryNewestCompany(String industryCode, Integer size) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("size", size);
		root.put("industryCode",industryCode);
		if(size==null){
			size=10;
		}
		return getSqlMapClientTemplate().queryForList(
				buildId(SQL_PREFIX, "queryNewestCompany"),root);
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<CompProfile> queryRecommendCompany(String categoryCode,
			Integer size) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("code", categoryCode);
		root.put("size", size);
		return getSqlMapClientTemplate().queryForList(
				buildId(SQL_PREFIX, "queryRecommendCompany"), root);
	}

	@Override
	public Integer updateMessageCountById(Integer id) {
		return getSqlMapClientTemplate().update(
				buildId(SQL_PREFIX, "updateMessageCountById"), id);
	}

	@Override
	public CompProfileDto queryContactByCid(Integer cid) {
		return (CompProfileDto) getSqlMapClientTemplate().queryForObject(
				buildId(SQL_PREFIX, "queryContactByCid"), cid);
	}

	@Override
	public CompProfileDto queryMemberInfoByCid(Integer cid) {
		return (CompProfileDto) getSqlMapClientTemplate().queryForObject(
				buildId(SQL_PREFIX, "queryMemberInfoByCid"), cid);
	}

	@Override
	public Integer insertCompProfile(CompProfile compProfile) {
		return (Integer) getSqlMapClientTemplate().insert(
				buildId(SQL_PREFIX, "insertCompProfile"), compProfile);
	}

	@Override
	public Integer updateBaseCompProfile(CompProfile comp) {
		return (Integer) getSqlMapClientTemplate().update(
				buildId(SQL_PREFIX, "updateCompProfile"), comp);
	}

	@Override
	public CompProfile getCompProfileById(Integer id) {
		return (CompProfile) getSqlMapClientTemplate().queryForObject(
				buildId(SQL_PREFIX, "getCompProfileById"), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CompProfile> queryImpCompProfile(Integer maxId) {
		return getSqlMapClientTemplate().queryForList(
				buildId(SQL_PREFIX, "queryImpCompProfile"), maxId);
	}

	@Override
	public Integer updateMainProduct(Integer cid, String mainProductSupply,
			String mainProductBuy) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("cid", cid);
		root.put("mainProductSupply", mainProductSupply);
		root.put("mainProductBuy", mainProductBuy);
		return getSqlMapClientTemplate().update(
				buildId(SQL_PREFIX, "updateMainProduct"), root);
	}

	@Override
	public String queryCssStyleByCid(Integer companyId) {
		return (String) getSqlMapClientTemplate().queryForObject(
				buildId(SQL_PREFIX, "queryCssStyleByCid"), companyId);
	}

	@Override
	public Integer insertCompanyStyle(WebsiteProfile profile) {
		return (Integer) getSqlMapClientTemplate().insert(
				buildId(SQL_PREFIX, "insertCompanyStyle"), profile);
	}

	@Override
	public Integer queryWebsiteConfigCount(Integer companyId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				buildId(SQL_PREFIX, "queryWebsiteConfigCount"), companyId);
	}

	@Override
	public Integer updateCompanyStyle(WebsiteProfile profile) {
		return getSqlMapClientTemplate().update(
				buildId(SQL_PREFIX, "updateCompanyStyle"), profile);
	}

	@Override
	public Integer queryIdByDomainOrDomainTwo(String domain , String domainTwo) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("domain", domain);
		root.put("domainTwo", domainTwo);
		return (Integer) getSqlMapClientTemplate().queryForObject(
				buildId(SQL_PREFIX, "queryIdByDomainOrDomainTwo"), root);
	}

	@Override
	public CompProfile queryCompProfileById(Integer cid) {
		return (CompProfile) getSqlMapClientTemplate().queryForObject(
				buildId(SQL_PREFIX, "queryCompProfileById"), cid);
	}

	@Override
	public Integer isDelStatus(Integer cid) {
		return (Integer)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "isDelStatus"),cid);
	}

	@Override
	public String getMemberCodeById(Integer id) {
		return (String)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "getMemberCodeById"),id);
	}

	@Override
	public Integer updateGmtModifiedById(Integer cid) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateGmtModifiedById"), cid);
	}

	@Override
	public CompProfile queryShortCompProfileById(Integer id) {
		return (CompProfile) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryShortCompProfileById"), id);
	}

	@Override
	public CompProfileNormDto queryProfileWithAccount(Integer id) {
		return (CompProfileNormDto) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryProfileWithAccount"), id);
	}

	@Override
	public String queryNameById(Integer id) {
		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryNameById"),id);
	}

	@Override
	public Integer queryCompCountById(Integer cid) {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCompCountById"), cid);
	}

	@Override
	public String queryMemberCodeById(Integer id) {
		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryMemberCodeById"), id);
	}

	@Override
	public WebsiteProfile querySiteProfile(Integer cid) {
		return (WebsiteProfile) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "querySiteProfile"), cid);
	}


	@Override
	public Integer insertCompProfiles(CompProfile compProfile) {
		return (Integer) getSqlMapClientTemplate().insert(
					buildId(SQL_PREFIX, "insertCompProfiles"), compProfile);
		
	}


	

//	@Override
//	public CompProfile queryProfileById(Integer id) {
//		return (CompProfile) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryProfileById"), id);
//	}
}