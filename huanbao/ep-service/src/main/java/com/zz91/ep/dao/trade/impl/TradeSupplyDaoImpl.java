/*
 * 文件名称：TradeSupplyDaoImpl.java
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
import com.zz91.ep.dao.trade.TradeSupplyDao;
import com.zz91.ep.domain.trade.TradeKeyword;
import com.zz91.ep.domain.trade.TradeSupply;
import com.zz91.ep.dto.CommonDto;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.trade.SupplyMessageDto;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作DAO层
 * 模块描述：供应信息相关数据操作
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Repository("tradeSupplyDao")
public class TradeSupplyDaoImpl extends BaseDao implements TradeSupplyDao {

	final static String SQL_PREFIX="tradesupply";
	
	@Override
	public Integer querySupplyCountByCategory(String code) {
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "querySupplyCountByCategory"), code);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CommonDto> querySupplysByRecommend(Integer cid, Short type, Integer size) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("cid", cid);
		root.put("type", type);
		root.put("size", size);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "querySupplysByRecommend"), root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TradeSupply> queryNewestSupplys(Integer cid, Integer size,String category,Integer uid) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("cid", cid);
		root.put("size", size);
		root.put("category", category);
		root.put("uid", uid);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryNewestSupplys"), root);
	}
	

	@Override
	public TradeSupply queryShortDetailsById(Integer id) {
		return (TradeSupply)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryShortDetailsById"), id);
	}

	@Override
	public Integer updateMessageCountById(Integer id) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateMessageCountById"), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TradeSupply> querySupplyByCompany(Integer group, String keywords, Integer cid, PageDto<TradeSupply> page) {
		Map<String,Object> root =new HashMap<String, Object>();
        root.put("keywords", keywords);
        root.put("group", group);
        root.put("page", page);
        root.put("cid", cid);
        return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "querySupplyByCompany"),root);
	}

	@Override
	public Integer querySupplyByCompanyCount(Integer group, String keywords, Integer cid) {
		Map<String,Object> root =new HashMap<String, Object>();
        root.put("keywords", keywords);
        root.put("group", group);
        root.put("cid", cid);
        return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "querySupplyByCompanyCount"),root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TradeSupply> queryRecommendSupplysByCid(Integer cid, Integer size) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("cid", cid);
		root.put("size", size);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryRecommendSupplysByCid"), root);
	}

	@Override
	public TradeSupply queryDetailsById(Integer id) {
		return (TradeSupply) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryDetailsById"), id);
	}

	@Override
	public Integer getTradeSupplyCount(Integer uid, Integer checkStatus) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("uid", uid);
		if(checkStatus!=null){
			root.put("checkStatus", checkStatus);
		}
		return (Integer)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "getTradeSupplyCount"),root);
	}

	 @Override
	    public Integer updatePhotoCoverById(Integer id, String photoCover, Integer cid) {
	        Map<String, Object> root = new HashMap<String, Object>();
	        root.put("id", id);
	        root.put("cid", cid);
	        root.put("photoCover", photoCover);
	        return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updatePhotoCoverById"), root);
	    }
	
	 @Override
	    public Integer insertTradeSupply(TradeSupply tradeSupply) {
	        return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertTradeSupply"), tradeSupply);
	    }
	 
	@SuppressWarnings("unchecked")
	@Override
	    public List<TradeSupply> querySupplyByConditions(Integer cid,
	            Integer pauseStatus, Integer overdueStatus, Integer checkStatus,Integer recommend, Integer groupId,
	            PageDto<SupplyMessageDto> page) {
	        Map<String,Object> root =new HashMap<String, Object>();
	        root.put("cid", cid);
	        root.put("pauseStatus", pauseStatus);
	        root.put("overdueStatus", overdueStatus);
	        root.put("checkStatus", checkStatus);
	        root.put("recommend", recommend);
	        root.put("groupId", groupId);
	        root.put("page", page);
	        return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "querySupplyByConditions"), root);
	    }

	    @Override
	    public Integer querySupplyByConditionsCount(Integer cid,
	            Integer pauseStatus, Integer overdueStatus, Integer checkStatus,Integer recommend, Integer groupId) {
	        Map<String,Object> root =new HashMap<String, Object>();
	        root.put("cid", cid);
	        root.put("pauseStatus", pauseStatus);
	        root.put("overdueStatus", overdueStatus);
	        root.put("checkStatus", checkStatus);
	        root.put("recommend", recommend);
	        root.put("groupId", groupId);
	        return (Integer)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "querySupplyByConditionsCount"), root);
	    }
	    
	    @Override
		public Integer updateDelStatusById(Integer id, Integer cid) {
			Map<String,Object> root =new HashMap<String, Object>();
	        root.put("id", id);
	        root.put("cid", cid);
	        return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateDelStatusById"), root);
	    }
	    
	    @Override
	    public Integer updateSupplyGroupIdById(Integer id, Integer cid, Integer gid) {
	        Map<String,Object> root =new HashMap<String, Object>();
	        root.put("id", id);
	        root.put("cid", cid);
	        root.put("gid", gid);
	        return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateSupplyGroupIdById"), root);
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
	    public Integer updateRefreshById(Integer id,  Integer cid) {
	        Map<String,Object> root =new HashMap<String, Object>();
	        root.put("id", id);
	        root.put("cid", cid);
	        return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateRefreshById"), root);
	    }
	    
	    @Override
		public TradeSupply queryUpdateSupplyById(Integer id, Integer cid) {
			Map<String,Object> root =new HashMap<String, Object>();
	        root.put("id", id);
	        root.put("cid", cid);
			return (TradeSupply) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryUpdateSupplyById"), root);
		}
	    
	    @Override
	    public Integer updateCategoryById(String category,String propertyValue, Integer id, Integer cid) {
	        Map<String,Object> root =new HashMap<String, Object>();
	        root.put("id", id);
	        root.put("cid", cid);
	        root.put("category", category);
	        root.put("propertyValue", propertyValue);
	        return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateCategoryById"), root);
	    }
	    
	    @Override
	    public Integer updateBaseSupplyById(TradeSupply supply, Integer id, Integer cid) {
	        Map<String,Object> root =new HashMap<String, Object>();
	        root.put("id", id);
	        root.put("cid", cid);
	        root.put("supply", supply);
	        return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateBaseSupplyById"), root);
	    }
	    
	    @Override
		public Integer updatePropertyQueryById(Integer id, String properyValue) {
			Map<String, Object> root= new HashMap<String, Object>();
			root.put("id", id);
			root.put("properyValue", properyValue);
			return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updatePropertyQueryById"), root);
		}
	    
	    @Override
		public TradeSupply queryOneSupplyById(Integer id) {
			return (TradeSupply) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryOneSupplyById"), id);
		}
	public Integer updateImpTradeSupply(Integer maxId) {
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateImpTradeSupply"), maxId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TradeSupply> queryImpTradeSupply(Integer maxId) {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryImpTradeSupply"), maxId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TradeKeyword> queryBwListByKeyword(String keywords) {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryBwListByKeyword"), keywords);
	}

	@Override
	public Integer updateGmtModefiled(Integer tradeId) {
		
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateGmtModefiled"),tradeId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TradeSupply> queryRandomSupply(String code, Integer random) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("random",random);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryRandomSupply"),map);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TradeSupply> queryCategoryByCid(Integer cid) {
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryCategoryByCid"), cid);
	}

	@Override
	public TradeSupply querySupplyOmitDetails(Integer id) {
		
		return (TradeSupply) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "querySupplyOmitDetails"), id);
	}

	@Override
	public TradeSupply querySolrSupplybyId(Integer id) {
		
		return (TradeSupply)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "querySolrSupplybyId"),id);
	}

	@Override
	public String queryPhotoCover(Integer id) {
		return (String) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryPhotoCover"), id);
	}

	@Override
	public Integer countSupplysOfCompanyByStatus(Integer cid,
			Integer pauseStatus, Integer overdueStatus, Integer checkStatus,Integer groupId) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("cid", cid);
		root.put("pauseStatus", pauseStatus);
		root.put("overdueStatus", overdueStatus);
		root.put("checkStatus", checkStatus);
		root.put("groupId", groupId);
		return (Integer)getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "countSupplysOfCompanyByStatus"),root);
	}

	@Override
	public TradeSupply querySimpleDetailsById(Integer id) {
		return (TradeSupply) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "querySimpleDetailsById"), id);
	}

	@Override
	public Integer countForCidAndDate(Integer companyId, String from, String to) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("from", from);
		map.put("to", to);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "countForCidAndDate"), map);
	}

	@Override
	public Integer countForCidAndTitle(Integer companyId, String title) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId",companyId);
		map.put("title", title);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "countForCidAndTitle"), map);
	}

	@Override
	public Integer updatePhotoCover(Integer id, String photoCover) {
		Map<String, Object> map = new HashMap<String, Object>();
	    map.put("id", id);
		map.put("photoCover", photoCover);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updatePhotoCover"),map);
	}

	@Override
	public Integer updateUncheckByIdForMyesite(Integer id) {
		return (Integer) getSqlMapClientTemplate().update(
				buildId(SQL_PREFIX, "updateUncheckByIdForMyesite"), id);
	}
}