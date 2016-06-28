/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-04-07.
 */
package com.zz91.ads.board.dao.ad;

import com.zz91.ads.board.domain.ad.AdExactType;

/**
 * 定位精确广告的详细信息接口
 * 
 * @author Rolyer(rolyer.live@gmail.com)
 */
public interface AdExactTypeDao {

	/**
	 * 添加关联 
	 * 注：ad_id 广告编号不能为空。 
	 * exact_put_id 精确定位的类型编号不能为空。 
	 * anchor_point 锚点（定位条件）不能为空。
	 */
	public Integer insertAdExactType(AdExactType adExactType);

	/**
	 *  
	 */
	public Integer deleteAdExactTypeByAdId(Integer id);
	
	public Integer queryAdIdById(Integer id);
}
