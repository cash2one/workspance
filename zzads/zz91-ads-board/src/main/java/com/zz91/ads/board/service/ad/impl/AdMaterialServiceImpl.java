/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-8.
 */
package com.zz91.ads.board.service.ad.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ads.board.dao.ad.AdMaterialDao;
import com.zz91.ads.board.domain.ad.AdMaterial;
import com.zz91.ads.board.service.ad.AdMaterialService;
import com.zz91.util.Assert;
import com.zz91.util.lang.StringUtils;

/**
 * 广告素材接口
 * @author Rolyer(rolyer.live@gmail.com)
 * 
 */
@Component("adMaterialService")
public class AdMaterialServiceImpl implements AdMaterialService {

	@Resource
	private AdMaterialDao adMaterialDao;

	@Override
	public Integer deleteAdMaterialById(Integer id) {
		Assert.notNull(id, "the id must not be null");

		return adMaterialDao.deleteAdMaterialById(id);
	}

	@Override
	public Integer insertAdMaterial(AdMaterial adMaterial) {
		Assert.notNull(adMaterial, "the object of adMaterial must not be null");
		Assert.notNull(adMaterial.getName(), "the name must not be null");
		Assert.notNull(adMaterial.getAdId(), "the adId must not be null");
		if(StringUtils.isEmpty(adMaterial.getFilePath())){
			adMaterial.setMaterialType("text");
		}else{
			adMaterial.setMaterialType("image");
		}
		return adMaterialDao.insertAdMaterial(adMaterial);
	}

	@Override
	public List<AdMaterial> queryAdMaterialByAdId(Integer id) {
		Assert.notNull(id, "the id must not be null");

		return adMaterialDao.queryAdMaterialByAdId(id);
	}
}
