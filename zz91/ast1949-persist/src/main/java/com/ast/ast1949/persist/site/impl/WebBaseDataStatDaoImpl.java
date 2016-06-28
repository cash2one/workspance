/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-7
 */
package com.ast.ast1949.persist.site.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.site.WebBaseDataStatDo;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.site.WebBaseDataStatDao;
import com.ast.ast1949.util.DateUtil;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-4-7
 */
@Component("webBaseDataStatDao")
public class WebBaseDataStatDaoImpl extends BaseDaoSupport implements
		WebBaseDataStatDao {

	final static String SQL_PREFIX = "webBaseDataStat";

	@SuppressWarnings("unchecked")
	@Override
	public List<WebBaseDataStatDo> queryDataByDate(Date d) {
		if (d == null) {
			try {
				d = DateUtil.getDate(new Date(), "yyyy-MM-dd");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return getSqlMapClientTemplate().queryForList(
				addSqlKeyPreFix(SQL_PREFIX, "queryDataByDate"), d);
	}

	@Override
	public WebBaseDataStatDo queryDataByCate(String cate, Date d) {
		if (d == null) {
			d = new Date();
		}
		try {
			d=DateUtil.getDate(new Date(), "yyyy-MM-dd");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("statCate", cate);
		root.put("gmtStatDate", d);
		
		return (WebBaseDataStatDo) getSqlMapClientTemplate().queryForObject(
				addSqlKeyPreFix(SQL_PREFIX, "queryDataByCate"), root);
	}

	@Override
	public List<WebBaseDataStatDo> queryWebBaseDataStat(String statCate,Date statDate,PageDto<WebBaseDataStatDo> page) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("page", page);
		root.put("statCate", statCate);
		root.put("statDate", statDate);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryWebBaseDataStat"), root);
	}

	@Override
	public Integer queryWebBaseDataStatCount(String statCate, Date statDate) {
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("statCate", statCate);
		root.put("statDate", statDate);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryWebBaseDataStatCount"), root);
	}

	@Override
	public Integer queryWeekPublish(Map<String,Object>map) {
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryWeekPublish"), map);
	}

}
