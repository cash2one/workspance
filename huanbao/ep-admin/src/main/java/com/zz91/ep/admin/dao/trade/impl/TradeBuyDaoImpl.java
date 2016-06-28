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
import com.zz91.ep.admin.dao.trade.TradeBuyDao;
import com.zz91.ep.domain.trade.TradeBuy;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.trade.TradeBuyDto;

/**
 * @author totly
 *
 * created on 2011-9-13
 */
@Component("tradeBuyDao")
public class TradeBuyDaoImpl extends BaseDao implements TradeBuyDao {

    final static String SQL_PREFIX="tradeBuy";

    @Override
    public Integer insertTradeBuy(TradeBuy tradeBuy) {
        return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertTradeBuy"), tradeBuy);
    }

//    @Override
//    public TradeBuy queryBuyDetailsById(Integer id) {
//        return (TradeBuy) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryBuyDetailsById"), id);
//    }

//    @Override
//    public TradeBuy queryBuySimpleDetailsById(Integer id) {
//        return (TradeBuy) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryBuySimpleDetailsById"), id);
//    }

//    @SuppressWarnings("unchecked")
//	@Override
//    public List<Integer> queryBuyListById(Integer cid, Integer max) {
//        Map<String, Object> root = new HashMap<String, Object>();
//        root.put("cid", cid);
//        root.put("max", max);
//        return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryBuyListById"), root);
//    }

//    @SuppressWarnings("unchecked")
//	@Override
//    public List<TradeBuy> queryCommonBuyByCategory(String categoryCode,
//            Integer max) {
//        Map<String, Object> root = new HashMap<String, Object>();
//        root.put("categoryCode", categoryCode);
//        root.put("max", max);
//        return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryCommonBuyByCategory"), root);
//    }

//    @SuppressWarnings("unchecked")
//	@Override
//    public List<TradeBuy> queryNewestBuyByCategory(String category,
//            Boolean isDirect, Integer max) {
//        Map<String, Object> root = new HashMap<String, Object>();
//        root.put("category", category);
//        root.put("isDirect", isDirect);
//        root.put("max", max);
//        return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryNewestBuyByCategory"), root);
//    }

    @Override
    public Integer updateMessageCountById(Integer id) {
        return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateMessageCount"), id);
    }

//    @SuppressWarnings("unchecked")
//	@Override
//    public List<TradeBuy> queryBuyByConditions(Integer cid,
//            Integer pauseStatus, Integer overdueStatus, Integer checkStatus, PageDto<TradeBuy> page) {
//        Map<String,Object> root =new HashMap<String, Object>();
//        root.put("cid", cid);
//        root.put("pauseStatus", pauseStatus);
//        root.put("overdueStatus", overdueStatus);
//        root.put("checkStatus", checkStatus);
//        root.put("page", page);
//        return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryBuyByConditions"), root);
//    }

//    @Override
//    public Integer queryBuyByConditionsCount(Integer cid, Integer pauseStatus,
//           Integer overdueStatus, Integer checkStatus) {
//        Map<String,Object> root =new HashMap<String, Object>();
//        root.put("cid", cid);
//        root.put("pauseStatus", pauseStatus);
//        root.put("overdueStatus", overdueStatus);
//        root.put("checkStatus", checkStatus);
//        return (Integer)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryBuyByConditionsCount"), root);
//    }

//    @Override
//    public Integer updateBaseBuyById(TradeBuy buy, Integer id, Integer cid) {
//        Map<String,Object> root =new HashMap<String, Object>();
//        root.put("id", id);
//        root.put("cid", cid);
//        root.put("buy", buy);
//        return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateBaseBuyById"), root);
//    }

//    @Override
//    public Integer updateCategoryById(Integer id, String category, Integer cid) {
//        Map<String,Object> root =new HashMap<String, Object>();
//        root.put("id", id);
//        root.put("cid", cid);
//        root.put("category", category);
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
//    public Integer updateRefreshById(Integer id, Integer cid) {
//        Map<String, Object> root = new HashMap<String, Object>();
//        root.put("id", id);
//        root.put("cid", cid);
//        return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateRefreshById"), root);
//    }

    @Override
	public Integer updateBuyCheckStatus(Integer id,Integer checkStatus,
			String check_admin,String check_refuse){
		Map<String,Object> root =new HashMap<String, Object>();
        root.put("id", id);
        root.put("checkStatus", checkStatus);
        root.put("check_admin", check_admin);
        root.put("check_refuse", check_refuse);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateBuyCheckStatus"), root);
	}
	@Override
	public Integer deleteBuyById(Integer id) {
		return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteBuyById"), id);
	}

//	@Override
//	public TradeBuy queryUpdateBuyById(Integer id, Integer cid) {
//		Map<String, Object> root = new HashMap<String, Object>();
//        root.put("id", id);
//        root.put("cid", cid);
//		return (TradeBuy) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryUpdateBuyById"), root);
//	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<TradeBuyDto> queryBuyByCategoryCode(TradeBuyDto dto,
//			PageDto<TradeBuyDto> page) {
//		Map<String, Object> root = new HashMap<String, Object>();
//		root.put("dto", dto);
//		root.put("page", page);
//		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryBuyByCategoryCode"), root);
//	}

//	@Override
//	public Integer queryBuyCount(TradeBuyDto dto) {
//		Map<String, Object> root = new HashMap<String, Object>();
//		root.put("dto", dto);
//		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryBuyCount"), root);
//	}

	@Override
	public Integer updateStatusOfTradeBuy(Integer id, Integer checkStatus) {
		Map<String,Object> root =new HashMap<String, Object>();
        root.put("id", id);
        root.put("checkStatus", checkStatus);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateStatusOfTradeBuy"), root);
	}
	
	@Override
	public Integer querySupplyCount(Integer companyId) {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "querySupplyCount"), companyId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TradeBuyDto> queryCompBuyByAdmin(TradeBuy tradeBuy,
			PageDto<TradeBuyDto> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("tradeBuy", tradeBuy);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryCompBuyByAdmin"), root);
	}

	@Override
	public Integer queryCompBuyByAdminCount(TradeBuy tradeBuy) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("tradeBuy", tradeBuy);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCompBuyByAdminCount"),root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TradeBuyDto> queryBuyByCategoryCodeAndTitleAndCheckStatus(
			TradeBuyDto dto, PageDto<TradeBuyDto> page, String gmtPublishStart,
			String gmtPublishEnd,String queryType,Short recommendType,Integer infoComeFrom,Integer regComeFrom) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("dto", dto);
		root.put("page", page);
		root.put("gmtPublishStart", gmtPublishStart);
		root.put("gmtPublishEnd", gmtPublishEnd);
		root.put("queryType", queryType);
		root.put("recommendType", recommendType);
		root.put("infoComeFrom", infoComeFrom);
		root.put("regComeFrom", regComeFrom);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryBuyByCategoryCodeAndTitleAndCheckStatus"), root);
	}

	@Override
	public Integer queryBuysCountByTitleAndCheckStatus(
			TradeBuyDto dto, PageDto<TradeBuyDto> page, String gmtPublishStart,
			String gmtPublishEnd,String queryType,Short recommendType,Integer infoComeFrom,Integer regComeFrom) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("dto", dto);
		root.put("gmtPublishStart", gmtPublishStart);
		root.put("gmtPublishEnd", gmtPublishEnd);
		root.put("queryType", queryType);
		root.put("recommendType", recommendType);
		root.put("infoComeFrom", infoComeFrom);
		root.put("regComeFrom", regComeFrom);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryBuysCountByTitleAndCheckStatus"), root);
	}

	@Override
	public Integer updateDelStatus(Integer id, Integer delStatus) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("delStatus", delStatus);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateDelStatus"), root);
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
	public TradeBuy queryOneBuy(Integer id) {
		return (TradeBuy) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryOneBuy"), id);
	}

	@Override
	public Integer queryCidById(Integer id) {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryCidById"), id);
	}

	@Override
	public Integer updateUnPassCheckStatus(Integer intId,
			Integer intCheckStatus, String admin, String checkRefuse) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", intId);
		root.put("checkStatus", intCheckStatus);
		root.put("admin", admin);
		root.put("checkRefuse", checkRefuse);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateUnPassCheckStatus"), root);
	}

	@Override
	public Integer updateTradeBuy(TradeBuy buy) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateTradeBuy"), buy);
	}

	@Override
	public Integer updateCategoryCodeById(Integer id, String categoryCode) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("categoryCode", categoryCode);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateCategoryCodeById"), root);
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<TradeBuy> queryRecommendBuy(Integer size) {
//		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryRecommendBuy"), size);
//	}

	@Override
	public Integer countTradeBuy(Integer cid, Integer uid, String categoryCode,
			String title) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("uid", uid);
		root.put("cid", cid);
		root.put("categoryCode", categoryCode);
		root.put("title", title);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "countTradeBuy"), root);
	}

	@Override
	public Integer updateGmtmodifiedBySvrClose(Integer cid) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateGmtmodifiedBySvrClose"), cid);
	}

//	@Override
//	public Integer updatePhotoCoverById(Integer id, String path, Integer cid) {
//		Map<String, Object> root = new HashMap<String, Object>();
//        root.put("id", id);
//        root.put("cid", cid);
//        root.put("photoCover", path);
//        return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updatePhotoCoverById"), root);
//	}

	@Override
	public Integer queryMaxId() {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryMaxId"));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TradeBuy> queryBuys(PageDto<TradeBuy> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryBuys"), root);
	}

	@Override
	public Integer queryBuysCount() {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryBuysCount"));
	}
}