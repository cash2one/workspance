package com.ast.ast1949.service.spot.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.spot.SpotTrust;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.spot.SpotTrustDto;
import com.ast.ast1949.persist.spot.SpotTrustDao;
import com.ast.ast1949.service.spot.SpotTrustService;
import com.zz91.util.Assert;

/**
 * author:kongsj date:2013-5-20
 */
@Component("spotTrustService")
public class SpotTrustServiceImpl implements SpotTrustService {

	@Resource
	private SpotTrustDao spotTrustDao;

	@Override
	public Integer insert(SpotTrust spotTrust) {
		// 默认不删除
		spotTrust.setIsDelete(SpotTrustService.DELETE_NO);
		return spotTrustDao.insert(spotTrust);
	}

	@Override
	public PageDto<SpotTrustDto> pageList(SpotTrustDto spotTrustDto,
			PageDto<SpotTrustDto> page) {
		page.setTotalRecords(spotTrustDao.queryListCount(spotTrustDto));
		List<SpotTrust> list = spotTrustDao.queryList(spotTrustDto, page);
		List<SpotTrustDto> nlist = new ArrayList<SpotTrustDto>();
		for (SpotTrust obj : list) {
			SpotTrustDto dto = new SpotTrustDto();
			dto.setSpotTrust(obj);
			nlist.add(dto);
		}
		page.setRecords(nlist);
		return page;
	}

	@Override
	public SpotTrust queryById(Integer id) {
		Assert.notNull(id, "id must not be null");
		return spotTrustDao.queryById(id);
	}

	@Override
	public List<SpotTrust> queryListForFront(Integer start,Integer size) {
		if(start==null){
			start = 0;
		}
		if (size == null) {
			size = 10;
		}
		if (size > 100) {
			size = 100;
		}
		return spotTrustDao.queryListForFront(start,size);
	}

	@Override
	public Integer update(SpotTrust spotTrust) {
		Assert.notNull(spotTrust.getId(), "id must not be null");
		return spotTrustDao.update(spotTrust);
	}
	
	@Override
	public Integer updateChecked(String isChecked,Integer id){
		return spotTrustDao.updateForChecked(isChecked, id);
	}
	
	@Override
	public Integer updateDelete(String isDelete ,Integer id){
		return spotTrustDao.updateForDelete(isDelete, id);
	}

}
