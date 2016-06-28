/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-8.
 */
package com.zz91.ads.board.service.ad.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ads.board.dao.ad.AdPositionDao;
import com.zz91.ads.board.domain.ad.AdPosition;
import com.zz91.ads.board.dto.ExtTreeDto;
import com.zz91.ads.board.dto.ad.AdPositionDto;
import com.zz91.ads.board.service.ad.AdPositionService;
import com.zz91.util.Assert;

/**
 * 广告位接口
 * @author Rolyer(rolyer.live@gmail.com)
 * 
 */
@Component("adPositionService")
public class AdPositionServiceImpl implements AdPositionService {

	@Resource
	private AdPositionDao adPositionDao;

	@Override
	public Integer insertAdPosition(AdPosition adPosition) {
		Assert.notNull(adPosition, "the object of adPosition must not be null");
		Assert.notNull(adPosition.getName(), "the name must not be null");
		
		if(adPosition.getPaymentType()==null){
			adPosition.setPaymentType(0);
		}
		
		if(adPosition.getParentId()==null){
			adPosition.setParentId(AdPositionDao.DEF_PARENT_ID);
		}
		if(adPosition.getHasExactAd()==null){
			adPosition.setHasExactAd(AdPositionDao.HAS_EXACT_AD_FALSE);
		}
		
		return adPositionDao.insertAdPosition(adPosition);
	}

	@Override
	public AdPosition queryAdPositionById(Integer id) {
		Assert.notNull(id, "the id must not be null");

		return adPositionDao.queryAdPositionById(id);
	}

	@Override
	public List<AdPosition> queryAdPositionByParentId(Integer id) {
		Assert.notNull(id, "the id must not be null");

		return adPositionDao.queryAdPositionByParentId(id);
	}

	@Override
	public AdPositionDto queryAdPositionDtoById(Integer id) {
		Assert.notNull(id, "the id must not be null");
		return adPositionDao.queryAdPositionDtoById(id);
	}

	@Override
	public List<ExtTreeDto> queryAdPositionNodesByParentId(Integer id) {
		Assert.notNull(id, "the id must not be null");
		List<AdPosition> list = queryAdPositionByParentId(id);
		List<ExtTreeDto> tree = new ArrayList<ExtTreeDto>();
		for (AdPosition p : list) {
			ExtTreeDto node = new ExtTreeDto();
			node.setId(String.valueOf(p.getId()));
			node.setText(p.getName());
			node.setData(p.getId().toString());
			// 要判断节点是否有子节点。
			Integer count = adPositionDao.countAdPositionNodesByParentId(p.getId());
			if (count > 0) {
				node.setLeaf(false);
			} else {
				node.setLeaf(true);
			}

			tree.add(node);
		}

		return tree;
	}

	@Override
	public Integer signDelete(Integer id) {
		Assert.notNull(id, "the id must not be null");

		return adPositionDao.signDelete(id);
	}

	@Override
	public Integer updateAdPosition(AdPosition adPosition) {
		Assert.notNull(adPosition, "the object of adPosition must not be null");
		Assert.notNull(adPosition.getId(), "the id must not be null");
		Assert.notNull(adPosition.getName(), "the name must not be null");
		Assert.notNull(adPosition.getSequence(), "the sequence must not be null");
		
		if(adPosition.getParentId()==null){
			adPosition.setParentId(AdPositionDao.DEF_PARENT_ID);
		}
		if(adPosition.getHasExactAd()==null){
			adPosition.setHasExactAd(AdPositionDao.HAS_EXACT_AD_FALSE);
		}

		return adPositionDao.updateAdPosition(adPosition);
	}

	@Override
	public AdPositionDto queryAdPositionDtoByIdForEdit(Integer id) {
		Assert.notNull(id, "the id must not be null");
		return adPositionDao.queryAdPositionDtoByIdForEdit(id);
	}
}
