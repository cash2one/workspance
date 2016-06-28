/**
 * @author shiqp
 * @date 2015-07-08
 */
package com.zz91.ads.board.dao.ad;

import java.util.List;

import com.zz91.ads.board.domain.ad.AdRenew;
import com.zz91.ads.board.dto.Pager;
import com.zz91.ads.board.dto.ad.AdDto;
import com.zz91.ads.board.dto.ad.AdSearchDto;

public interface AdRenewDao {
	public Integer createRenew(AdRenew adRenew);

	public List<AdRenew> queryRenewByAdId(Pager<AdRenew> page, Integer adId);

	public Integer countRenewByAdId(Integer adId);

	public Integer updateRenew(AdRenew adRenew);

	public AdRenew queryRenewById(Integer id);

	public List<AdRenew> queryRenewByCondition(AdSearchDto adSearch, Pager<AdDto> page);
	
	public Integer countRenewByCondition(AdSearchDto adSearch);

}
