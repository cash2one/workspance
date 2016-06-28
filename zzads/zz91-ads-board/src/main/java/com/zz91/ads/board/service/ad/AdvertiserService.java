/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-04-07.
 */
package com.zz91.ads.board.service.ad;

import com.zz91.ads.board.domain.ad.Advertiser;
import com.zz91.ads.board.dto.Pager;

/**
 * 广告主接口
 * @author Rolyer(rolyer.live@gmail.com)
 */
public interface AdvertiserService {

	/**
	 * 添加一个广告主<br/>
	 * 注：name不能为空。
	 */
	public Integer insertAdvertiser(Advertiser advertiser);

	/**
	 * (伪)删除一个广告主,更新字段deleted的值为Y，即已删除。<br/>
	 * 注：id不能为空。
	 */
	public Integer signDeleted(Integer id);

	/**
	 * 根据广告主编号更新 
	 * 注：id，mane不能为空。
	 */
	public Integer updateAdvertiser(Advertiser advertiser);

	/**
	 * 根据编号读取一个广告主 
	 * 注：id不能为空。
	 */
	public Advertiser queryAdvertiserById(Integer id);

	/**
	 * 读取广告主记录 
	 * 注：name 广告主名称， category 广告主类型， delete 是否标记未删除,null默认为false。
	 * 当name,category为空时，查询所有数据。
	 */
	public Pager<Advertiser> pageAdvertiserByConditions(Advertiser advertiser, Boolean delete, Pager<Advertiser> pager);
	
	public Integer queryIdByEmail(String email);
}
