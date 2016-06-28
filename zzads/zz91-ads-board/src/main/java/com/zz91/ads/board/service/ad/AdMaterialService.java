/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-04-07.
 */
package com.zz91.ads.board.service.ad;

import java.util.List;

import com.zz91.ads.board.domain.ad.AdMaterial;

/**
 * 
 * @author Rolyer(rolyer.live@gmail.com)
 * 
 */
public interface AdMaterialService {

	/**
	 * 添加素材 
	 * 注：ad_id 广告编号不能为空。 name 素材名称不能为空。
	 */
	public Integer insertAdMaterial(AdMaterial adMaterial);

	/**
	 * 根据素材编号删除素材 如果有图片，要删除图片
	 */
	public Integer deleteAdMaterialById(Integer id);

	/**
	 * 根据广告编号读取素材信息 
	 */
	public List<AdMaterial> queryAdMaterialByAdId(Integer id);
}
