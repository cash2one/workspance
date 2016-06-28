/*
 * 文件名称：TradeBuyDaoImpl.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.dao.trade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zz91.ep.dao.common.impl.BaseDao;
import com.zz91.ep.dao.trade.TradeBuyDao;
import com.zz91.ep.domain.trade.TradeBuy;
import com.zz91.ep.dto.CommonDto;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.trade.BuyMessageDto;
import com.zz91.ep.dto.trade.TradeBuyDto;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作DAO层
 * 模块描述：求购信息相关数据操作
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Repository("tradeBuyDao")
public class TradeBuyDaoImpl extends BaseDao implements TradeBuyDao {

	final static String SQL_PREFIX="tradebuy";

	@SuppressWarnings("unchecked")
	@Override
	public List<CommonDto> queryBuysByRecommend(Integer size) {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryBuysByRecommend"), size);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CommonDto> queryNewestBuys(Integer size) {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryNewestBuys"), size);
	}

	@Override
	public Integer updateMessageCountById(Integer id) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateMessageCountById"), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TradeBuy> queryBuysDetailsByRecommend(Integer size) {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryBuysDetailsByRecommend"), size);
	}

	@Override
	public TradeBuyDto queryBuyDetailsById(Integer id) {
		return (TradeBuyDto) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryBuyDetailsById"), id);
	}

	@Override
	public Integer getTradeBuyId(Integer uid) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "getTradeBuyId"),uid);
	}
	
	@Override
    public Integer insertTradeBuy(TradeBuy tradeBuy) {
        return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertTradeBuy"), tradeBuy);
    }
	
	@SuppressWarnings("unchecked")
	@Override
    public List<TradeBuy> queryBuyByConditions(Integer cid,
            Integer pauseStatus, Integer overdueStatus, Integer checkStatus, PageDto<BuyMessageDto> page) {
        Map<String,Object> root =new HashMap<String, Object>();
        root.put("cid", cid);
        root.put("pauseStatus", pauseStatus);
        root.put("overdueStatus", overdueStatus);
        root.put("checkStatus", checkStatus);
        root.put("page", page);
        return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryBuyByConditions"), root);
    }

    @Override
    public Integer queryBuyByConditionsCount(Integer cid, Integer pauseStatus,
           Integer overdueStatus, Integer checkStatus) {
        Map<String,Object> root =new HashMap<String, Object>();
        root.put("cid", cid);
        root.put("pauseStatus", pauseStatus);
        root.put("overdueStatus", overdueStatus);
        root.put("checkStatus", checkStatus);
        return (Integer)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryBuyByConditionsCount"), root);
    }
    
    @Override
	public Integer updateDelStatusById(Integer id, Integer cid) {
		Map<String,Object> root =new HashMap<String, Object>();
        root.put("id", id);
        root.put("cid", cid);
        return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateDelStatusById"), root);
    }
    
    @Override
    public Integer updatePauseStatusById(Integer id, Integer cid, Integer newStatus) {
        Map<String,Object> root =new HashMap<String, Object>();
        root.put("id", id);
        root.put("cid", cid);
        root.put("newStatus", newStatus);
        return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updatePauseStatusById"), root);
    }
    
    @Override
    public Integer updateRefreshById(Integer id, Integer cid) {
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("id", id);
        root.put("cid", cid);
        return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateRefreshById"), root);
    }
    
    @Override
    public TradeBuy queryBuySimpleDetailsById(Integer id) {
        return (TradeBuy) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryBuySimpleDetailsById"), id);
    }
    
    @Override
	public TradeBuy queryUpdateBuyById(Integer id, Integer cid) {
		Map<String, Object> root = new HashMap<String, Object>();
        root.put("id", id);
        root.put("cid", cid);
		return (TradeBuy) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryUpdateBuyById"), root);
	}
    
    @Override
    public Integer updateBaseBuyById(TradeBuy buy, Integer id, Integer cid) {
        Map<String,Object> root =new HashMap<String, Object>();
        root.put("id", id);
        root.put("cid", cid);
        root.put("buy", buy);
        return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateBaseBuyById"), root);
    }
	@SuppressWarnings("unchecked")
	@Override
	public List<TradeBuy> queryImpTradeBuy(Integer maxId) {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryImpTradeBuy"), maxId);
	}

	@Override
	public Integer updateImpTradeBuy(Integer maxId) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateImpTradeBuy"), maxId);
	}

	@Override
	public Integer countBuysOfCompanyByStatus(Integer cid, Integer pauseStatus,
			Integer overdueStatus, Integer checkStatus) {
		Map<String, Integer> root = new HashMap<String, Integer>();
		root.put("cid", cid);
		root.put("pauseStatus", pauseStatus);
		root.put("overdueStatus", overdueStatus);
		root.put("checkStatus", checkStatus);
		return (Integer)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "countBuysOfCompanyByStatus"),root);
	}

}