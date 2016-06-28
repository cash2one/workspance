/**
 * @author shiqp
 * @date 2015-07-08
 */
package com.zz91.ads.board.service.ad;

import com.zz91.ads.board.domain.ad.AdRenew;
import com.zz91.ads.board.dto.Pager;
import com.zz91.ads.board.dto.ad.AdDto;
import com.zz91.ads.board.dto.ad.AdSearchDto;

public interface AdRenewService {
	public Integer createRenew(AdRenew adRenew);
	
	public Pager<AdRenew> pageRenewByAdId(Pager<AdRenew> page,Integer adId);
	
	public Integer updateRenew(AdRenew adRenew);
	
	public AdRenew queryRenewById(Integer id);
	
	public Pager<AdDto> pageRenewByCondition(AdSearchDto adSearch, Pager<AdDto> page); 


}
