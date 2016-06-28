/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-25
 */
package com.ast.ast1949.persist.information.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.information.Periodical;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.information.PeriodicalDTO;
import com.ast.ast1949.persist.information.PeriodicalDAO;
import com.ast.ast1949.util.Assert;

/**
 * @author Mays (x03570227@gmail.com)
 *
 */
@Component("periodicalDAO")
public class PeriodicalDAOImpl extends SqlMapClientDaoSupport implements PeriodicalDAO {



	public Integer createPeriodical(Periodical periodical) {
		Assert.notNull(periodical, "the periodical must not be null");
		return (Integer) getSqlMapClientTemplate().insert("periodical.createPeriodical",periodical);
	}

	public Integer deletePeriodicalById(Integer id) {
		Assert.notNull(id, "the periodical must not be null");
		return getSqlMapClientTemplate().delete("periodical.deletePeriodicalById", id);
	}

	public Periodical listOnePeriodicalById(Integer id) {
		Assert.notNull(id, "the periodical must not be null");
		return (Periodical) getSqlMapClientTemplate().queryForObject("periodical.listOnePeriodicalById",id);
	}

	@SuppressWarnings("unchecked")
	public List<Periodical> pagePeriodicalWithoutSearch(PageDto page) {
		Assert.notNull(page, "the periodical must not be null");
		Map<String, Object> root=new HashMap<String, Object>();
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList("periodical.pagePeriodicalWithoutSearch", root);
	}

	public Integer updatePeriodical(Periodical periodical) {
		Assert.notNull(periodical, "the periodical must not be null");
		return getSqlMapClientTemplate().update("periodical.updatePeriodical", periodical);
	}

	public Integer countPagePeriodicalWithoutSearch() {
		return (Integer) getSqlMapClientTemplate().queryForObject("periodical.countPagePeriodicalWithoutSearch");
	}

	public Integer updatePeriodicalZipPath(Integer id, String zipPath) {
		Assert.notNull(id, "the id must not be null");
		Assert.notNull(zipPath, "the zipPath must not be null");
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("zipPath", zipPath);
		return getSqlMapClientTemplate().update("periodical.updatePeriodicalZipPath", root);
	}

	@SuppressWarnings("unchecked")
	public List<PeriodicalDTO> listFrontCoverPeriodicalBySize(Integer size) {
		Assert.notNull(size, "size is not null");
		return getSqlMapClientTemplate().queryForList("periodical.listFrontCoverPeriodicalBySize", size);
	}

	public Integer updateNumUpById(Integer id, Integer num) {
		Assert.notNull(id, "the id is not null");
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", id);
		root.put("num", num);
		return getSqlMapClientTemplate().update("periodical.updateNumUpById", root);
	}

	public Integer updateNumViewById(Integer periodicalId, Integer num) {
		Assert.notNull(periodicalId, "the periodicalId is not null");
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", periodicalId);
		root.put("num", num);
		return getSqlMapClientTemplate().update("periodical.updateNumViewById", root);
	}

}
