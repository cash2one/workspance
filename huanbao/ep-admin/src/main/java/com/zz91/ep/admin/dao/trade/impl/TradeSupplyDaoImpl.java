/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-13
 */
package com.zz91.ep.admin.dao.trade.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.trade.TradeSupplyDao;
import com.zz91.ep.domain.trade.TradeSupply;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.trade.TradeSupplyDto;
import com.zz91.util.lang.StringUtils;

/**
 * @author totly
 *
 * created on 2011-9-13
 */
@Component("tradeSupplyDao")
public class TradeSupplyDaoImpl extends BaseDao implements TradeSupplyDao {

    final static String SQL_PREFIX="tradeSupply";

    @Override
    public Integer insertTradeSupply(TradeSupply tradeSupply) {
        return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertTradeSupply"), tradeSupply);
    }

//    @SuppressWarnings("unchecked")
//	@Override
//    public List<TradeSupply> querySupplyByCompany(String categoryCode,Integer group,String keywords,Integer cid , PageDto<TradeSupply> page) {
//        Map<String,Object> root =new HashMap<String, Object>();
//        root.put("categoryCode", categoryCode);
//        root.put("keywords", keywords);
//        root.put("group", group);
//        root.put("page", page);
//        root.put("cid", cid);
//        return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "querySupplyByCompany"),root);
//    }

//    @Override
//    public TradeSupplyDto queryLongDetailsById(Integer id) {
//        return (TradeSupplyDto) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryLongDetailsById"), id);
//    }

//    @Override
//    public TradeSupplyDto queryShortDetailsById(Integer id) {
//        return (TradeSupplyDto) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryShortDetailsById"), id);
//    }

    @Override
    public Integer updatePhotoCoverById(Integer id, String photoCover) {
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("id", id);
        root.put("photoCover", photoCover);
        return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updatePhotoCoverById"), root);
    }

    @Override
    public Integer updateMessageCountById(Integer id) {
        return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateMessageCount"), id);
    }

//    @Override
//    public Integer querySupplyByCompanyCount(String categoryCode,Integer group,String keywords, Integer cid) {
//        Map<String,Object> root =new HashMap<String, Object>();
//        root.put("categoryCode", categoryCode);
//        root.put("keywords", keywords);
//        root.put("group", group);
//        root.put("cid", cid);
//        return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "querySupplyByCompanyCount"),root);
//    }

	@Override
	public Integer deleteSupplyById(Integer id) {
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteSupplyById"),id);
	}

//    @SuppressWarnings("unchecked")
//	@Override
//    public List<TradeSupply> querySupplyByConditions(Integer cid,
//            Integer pauseStatus, Integer overdueStatus, Integer checkStatus,Integer recommend, Integer groupId,
//            PageDto<TradeSupply> page) {
//        Map<String,Object> root =new HashMap<String, Object>();
//        root.put("cid", cid);
//        root.put("pauseStatus", pauseStatus);
//        root.put("overdueStatus", overdueStatus);
//        root.put("checkStatus", checkStatus);
//        root.put("recommend", recommend);
//        root.put("groupId", groupId);
//        root.put("page", page);
//        return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "querySupplyByConditions"), root);
//    }

//    @Override
//    public Integer querySupplyByConditionsCount(Integer cid,
//            Integer pauseStatus, Integer overdueStatus, Integer checkStatus,Integer recommend, Integer groupId) {
//        Map<String,Object> root =new HashMap<String, Object>();
//        root.put("cid", cid);
//        root.put("pauseStatus", pauseStatus);
//        root.put("overdueStatus", overdueStatus);
//        root.put("checkStatus", checkStatus);
//        root.put("recommend", recommend);
//        root.put("groupId", groupId);
//        return (Integer)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "querySupplyByConditionsCount"), root);
//    }

//    @Override
//    public Integer updateBaseSupplyById(TradeSupply supply, Integer id, Integer cid) {
//        Map<String,Object> root =new HashMap<String, Object>();
//        root.put("id", id);
//        root.put("cid", cid);
//        root.put("supply", supply);
//        return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateBaseSupplyById"), root);
//    }

//    @Override
//    public Integer updateCategoryById(String category,String propertyValue, Integer id, Integer cid) {
//        Map<String,Object> root =new HashMap<String, Object>();
//        root.put("id", id);
//        root.put("cid", cid);
//        root.put("category", category);
//        root.put("propertyValue", propertyValue);
//        return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateCategoryById"), root);
//    }

//    @Override
//    public Integer updatePauseStatusById(Integer id, Integer cid, Integer newStatus) {
//        Map<String,Object> root =new HashMap<String, Object>();
//        root.put("id", id);
//        root.put("cid", cid);
//        root.put("newStatus", newStatus);
//        return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updatePauseStatusById"), root);
//    }

//    @Override
//    public Integer updateRefreshById(Integer id,  Integer cid) {
//        Map<String,Object> root =new HashMap<String, Object>();
//        root.put("id", id);
//        root.put("cid", cid);
//        return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateRefreshById"), root);
//    }

//    @Override
//    public Integer updateSupplyGroupIdById(Integer id, Integer cid, Integer gid) {
//        Map<String,Object> root =new HashMap<String, Object>();
//        root.put("id", id);
//        root.put("cid", cid);
//        root.put("gid", gid);
//        return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateSupplyGroupIdById"), root);
//    }

//	@Override
//	public TradeSupplyDto queryUpdateSupplyById(Integer id, Integer cid) {
//		Map<String,Object> root =new HashMap<String, Object>();
//        root.put("id", id);
//        root.put("cid", cid);
//		return (TradeSupplyDto) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryUpdateSupplyById"), root);
//	}

	@Override
	public Integer updateStatusOfTradeSupply(Integer id, Integer checkStatus) {
		Map<String,Object> root =new HashMap<String, Object>();
        root.put("id", id);
        root.put("checkStatus", checkStatus);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateStatusOfTradeSupply"), root);
	}
	
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<TradeSupplyDto> querySupplyByCategoryCode(TradeSupplyDto dto,
//			PageDto<TradeSupplyDto> page) {
//		Map<String, Object> root = new HashMap<String, Object>();
//		root.put("dto", dto);
//		root.put("page", page);
//		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "querySupplyByCategoryCode"), root);
//	}

//	@Override
//	public Integer querySupplysCount(TradeSupplyDto dto) {
//		Map<String, Object> root = new HashMap<String, Object>();
//		root.put("dto", dto);
//		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "querySupplysCount"), root);
//	}

	@Override
	public Integer countTradeSupply(Integer cid, Integer uid,
			String categoryCode, String title) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("uid", uid);
		root.put("cid", cid);
		root.put("categoryCode", categoryCode);
		root.put("title", title);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "countTradeSupply"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TradeSupplyDto> querySupplyByCategoryCodeAndTitleAndCheckStatus(
			TradeSupplyDto dto, PageDto<TradeSupplyDto> page,String gmtPublishStart,
			String gmtPublishEnd,String queryType,String memberCode, Short recommendType,String subRecommend,String codeBlock) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("dto", dto);
		root.put("page", page);
		root.put("gmtPublishStart", gmtPublishStart);
		root.put("gmtPublishEnd", gmtPublishEnd);
		root.put("queryType", queryType);
		root.put("memberCode", memberCode);
		root.put("recommendType", recommendType);
		root.put("subRecommend", subRecommend);
		root.put("codeBlock", codeBlock);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "querySupplyByCategoryCodeAndTitleAndCheckStatus"), root);
	}
	
	@Override
	public Integer querySupplysCountByTitleAndCheckStatus(
			TradeSupplyDto dto,PageDto<TradeSupplyDto> page,String gmtPublishStart,
			String gmtPublishEnd,String queryType,String memberCode,Short recommendType,String subRecommend,String codeBlock ) {
		Map<String, Object> root = new HashMap<String, Object>();
		String compProfile=null;
		if(StringUtils.isNotEmpty(dto.getCompName())||StringUtils.isNotEmpty(codeBlock)||StringUtils.isNotEmpty(memberCode)){
			compProfile="true";
		}
		root.put("compProfile", compProfile);
		root.put("dto", dto);
		root.put("gmtPublishStart", gmtPublishStart);
		root.put("gmtPublishEnd", gmtPublishEnd);
		root.put("queryType", queryType);
		root.put("memberCode", memberCode);
		root.put("recommendType", recommendType);
		root.put("subRecommend", subRecommend);
		root.put("codeBlock", codeBlock);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "querySupplysCountByTitleAndCheckStatus"), root);
	}

	@Override
	public Integer querySupplyCount(Integer companyId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "querySupplyCount"), companyId);
	}

//	@Override
//	public Integer querySupplyCountByCategory(String categoryCode) {
//		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "querySupplyCountByCategory"), categoryCode);
//	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TradeSupplyDto> querySupplyByAdmin(
			PageDto<TradeSupplyDto> page, TradeSupply tradeSupply,Short type) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("tradeSupply", tradeSupply);
		root.put("page", page);
		root.put("type", type);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "querySupplyByAdmin"), root);
	}

	@Override
	public Integer querySupplyByAdminCount(TradeSupply tradeSupply,Short type) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("tradeSupply", tradeSupply);
		root.put("type", type);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "querySupplyByAdminCount"), root);
	}

//	@Override
//	public Integer queryAllSupplyCount() {
//		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryAllSupplyCount"));
//	}

//	@Override
//	public Integer queryWeekSupplyCount() {
//		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryWeekSupplyCount"));
//	}

	@Override
	public TradeSupply queryOneSupplyById(Integer id) {
		return (TradeSupply) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryOneSupplyById"), id);
	}

	@Override
	public Integer updateTradeSupply(TradeSupply supply) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateTradeSupply"), supply);
	}

	@Override
	public Integer queryCidById(Integer id) {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCidById"), id);
	}

	@Override
	public Integer updateUnPassCheckStatus(Integer id, Integer checkStatus,
			String checkAdmin, String checkRefuse) {
		Map<String, Object> root= new HashMap<String, Object>();
		root.put("id", id);
		root.put("checkStatus", checkStatus);
		root.put("checkAdmin", checkAdmin);
		root.put("checkRefuse", checkRefuse);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateUnPassCheckStatus"), root);
	}

	@Override
	public Integer updateDelStatus(Integer id, Integer delStatus) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("delStatus", delStatus);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateDelStatus"), root);
	}

	@Override
	public String queryCategoryCodeById(Integer id) {
		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCategoryCodeById"), id);
	}

	@Override
	public Integer updateGmtRefresh(Integer id , Date gmtExpired , int validDays) {
	    Map<String, Object> root = new HashMap<String, Object>();
        root.put("id", id);
        root.put("gmtExpired", gmtExpired);
        root.put("validDays", validDays);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateGmtRefresh"), root);
	}
	@Override
	public Integer updateSupplyCheckStatus(Integer id,Integer checkStatus,
			String check_admin,String check_refuse){
		Map<String,Object> root =new HashMap<String, Object>();
        root.put("id", id);
        root.put("checkStatus", checkStatus);
        root.put("check_admin", check_admin);
        root.put("check_refuse", check_refuse);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateSupplyCheckStatus"), root);
	}

//	@Override
//	public Integer updatePropertyQueryById(Integer id, String properyValue) {
//		Map<String, Object> root= new HashMap<String, Object>();
//		root.put("id", id);
//		root.put("properyValue", properyValue);
//		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updatePropertyQueryById"), root);
//	}

	@Override
	public String queryPropertyQueryById(Integer id) {
		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryPropertyQueryById"), id);
	}

	@Override
	public Integer updatePropertyQueryAllById(Integer supplyId, String query) {
		Map<String, Object> root= new HashMap<String, Object>();
		root.put("id", supplyId);
		root.put("properyValue", query);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updatePropertyQueryAllById"), root);
	}
	
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<CompProfile> queryCompByKeywords(String keywords, Integer size) {
//		Map<String, Object> root=new HashMap<String, Object>();
//		root.put("keywords", keywords);
//		root.put("size", size);
//		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryCompByKeywords"), root);
//	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TradeSupply> querySupplyByKeywords(String keywords, Integer size) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("keywords", keywords);
		root.put("size", size);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "querySupplyByKeywords"), root);
	}

	@Override
	public Integer updateCategoryCodeById(Integer id, String categoryCode) {
		Map<String, Object> root= new HashMap<String, Object>();
		root.put("id", id);
		root.put("categoryCode", categoryCode);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateCategoryCodeById"), root);
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<TradeSupplyDto> querySupplysByKeywords(String keywords,
//			Integer size) {
//		Map<String, Object> root=new HashMap<String, Object>();
//		root.put("keywords", keywords);
//		root.put("size", size);
//		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "querySupplysByKeywords"), root);
//	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<TradeSupply> queryRecommendSupply(Integer cid, Integer size) {
//		Map<String, Object> root=new HashMap<String, Object>();
//		root.put("cid", cid);
//		root.put("size", size);
//		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryRecommendSupply"), root);
//	}
	
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<TradeSupply> queryRecommendSupplyC(Integer cid, Integer size) {
//		Map<String, Object> root=new HashMap<String, Object>();
//		root.put("cid", cid);
//		root.put("size", size);
//		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryRecommendSupplyC"), root);
//	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TradeSupply> querySimplyByKeywords(String keywords,String compName,Integer loginCount) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("keywords",keywords );
		root.put("compName",compName );
		root.put("loginCount", loginCount);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "querySimplyByKeywords"), root);
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<TradeSupplyDto> queryNewSimplySupply(String parentCode,
//			Integer size) {
//		Map<String, Object> root = new HashMap<String, Object>();
//		root.put("code", parentCode);
//		root.put("size", size);
//		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryNewSimplySupply"), root);
//	}

	@Override
	public Integer updateGmtmodifiedBySvrClose(Integer cid) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateGmtmodifiedBySvrClose"), cid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TradeSupply> queryNewestSupply(Integer cid,Integer max) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("cid", cid);
		root.put("max", max);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryTopNewestSupply"), root);
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<TradeSupply> queryNewestSupply(String category,
//			Integer size) {
//		Map<String, Object> root = new HashMap<String, Object>();
//		root.put("code", category);
//		root.put("size", size);
//		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryNewestSupply"), root);
//	}

	@Override
	public TradeSupply queryPropertyQueryAndCategoryCodeById(Integer id) {
		return (TradeSupply) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryPropertyQueryAndCategoryCodeById"), id);
	}

	@Override
	public Integer queryMaxId() {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryMaxId"));
	}

	@Override
	public TradeSupply queryShortBwDetailsById(Integer sid) {
		return (TradeSupply) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryShortBwDetailsById"),sid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TradeSupply> querySupplys(PageDto<TradeSupply> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "querySupplys"), root);
	}

	@Override
	public Integer querySupplysCount() {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "querySupplysCount"));
	}

   
}