/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-31 by Rolyer.
 */
package com.ast.ast1949.persist.information.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.information.ChartsInfoDO;
import com.ast.ast1949.dto.information.ChartsInfoDTO;
import com.ast.ast1949.persist.information.ChartsInfoDAO;
import com.ast.ast1949.util.Assert;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 */
@Component("chartsInfoDAO")
public class ChartsInfoDAOImpl extends SqlMapClientDaoSupport implements ChartsInfoDAO {

	public Integer deleteChartsInfoById(Integer id) {
		Assert.notNull(id, "the id must not be null");
		
		return getSqlMapClientTemplate().delete("chartsinfo.deleteChartsInfoById", id);
	}

	public Integer insertChartsInfo(ChartsInfoDO chartsInfo) {
		Assert.notNull(chartsInfo, "the object of chartsInfo must not be null");
		Assert.notNull(chartsInfo.getChartCategoryId(), "the chartCategoryId must not be null");
		Assert.notNull(chartsInfo.getGmtDate(), "the gmtDate must not be null");
		
		return (Integer) getSqlMapClientTemplate().insert("chartsinfo.insertChartsInfo", chartsInfo);
	}

	public ChartsInfoDO queryChartsInfoById(Integer id) {
		Assert.notNull(id, "the id must not be null");
		
		return (ChartsInfoDO)getSqlMapClientTemplate().queryForObject("chartsinfo.queryChartsInfoById", id);
	}
		
	public Integer updateChartsInfoById(ChartsInfoDO chartsInfo) {
		Assert.notNull(chartsInfo, "the object of chartsInfo must not be null");
		Assert.notNull(chartsInfo.getChartCategoryId(), "the chartCategoryId must not be null");
		Assert.notNull(chartsInfo.getGmtDate(), "the gmtDate must not be null");
		
		return getSqlMapClientTemplate().update("chartsinfo.updateChartsInfoById", chartsInfo);
	}

	public Integer countChartsInfoList(ChartsInfoDTO chartsInfoDTO) {
		return (Integer) getSqlMapClientTemplate().queryForObject("chartsinfo.countChartsInfoList",chartsInfoDTO);
	}

	@SuppressWarnings("unchecked")
	public List<ChartsInfoDTO> queryChartsInfoList(ChartsInfoDTO chartsInfoDTO) {
		return getSqlMapClientTemplate().queryForList("chartsinfo.queryChartsInfoList", chartsInfoDTO);
	}

}
