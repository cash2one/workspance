package com.kl91.persist.company;

import java.util.List;

import com.kl91.domain.company.EsiteFriendlink;
import com.kl91.domain.dto.company.EsiteFriendlinkSearchDto;

public interface EsiteFriendlinkDao {

	public Integer insert(EsiteFriendlink esiteFriendlink);

	public Integer update(EsiteFriendlink esiteFriendlink);

	public Integer deleteById(Integer id);

	public EsiteFriendlink queryById(Integer id);

	public List<EsiteFriendlink> queryFriendlink(EsiteFriendlinkSearchDto searchDto);
}
