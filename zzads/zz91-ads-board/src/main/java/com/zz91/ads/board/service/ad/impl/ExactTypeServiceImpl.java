/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-8.
 */
package com.zz91.ads.board.service.ad.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ads.board.dao.ad.ExactTypeDao;
import com.zz91.ads.board.domain.ad.ExactType;
import com.zz91.ads.board.dto.Pager;
import com.zz91.ads.board.service.ad.ExactTypeService;
import com.zz91.util.Assert;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 * 
 */
@Component("exactTypeService")
public class ExactTypeServiceImpl implements ExactTypeService {

	@Resource
	private ExactTypeDao axactTypeDao;

	@Override
	public Integer deleteExactTypeById(Integer id) {
		Assert.notNull(id, "the id must not be null");

		return axactTypeDao.deleteExactTypeById(id);
	}

	@Override
	public Pager<ExactType> pageExactType(Pager<ExactType> pager) {
		Assert.notNull(pager, "the pager must not be null");

		pager.setRecords(axactTypeDao.queryExactType(pager));
		pager.setTotals(axactTypeDao.queryExactTypeCount());

		return pager;
	}

	@Override
	public List<ExactType> queryExactTypeByAdPositionId(Integer id) {
		Assert.notNull(id, "the id must not be null");

		return axactTypeDao.queryExactTypeByAdPositionId(id);
	}

	@Override
	public ExactType queryExactTypeByExactName(String name) {
		Assert.notNull(name, "the name must not be null");

		return axactTypeDao.queryExactTypeByExactName(name);
	}

	@Override
	public ExactType queryExactTypeById(Integer id) {
		Assert.notNull(id, "the id must not be null");

		return axactTypeDao.queryExactTypeById(id);
	}

	@Override
	public Integer saveExactType(ExactType exactType) {
		Assert.notNull(exactType, "the object of exactType must not be null");
		Assert.notNull(exactType.getExactName(), "the exactName must not be null");
		
		ExactType et = queryExactTypeByExactName(exactType.getExactName());
		if (et != null && et.getId().intValue() > 0) {
			return et.getId();
		} else {
			return axactTypeDao.insertExactType(exactType);
		}
	}

}
