package com.ast.ast1949.persist.market;

import java.util.List;

import com.ast.ast1949.domain.market.MarketSubscribe;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.market.MarketSearchDto;
import com.ast.ast1949.dto.market.MarketSubscribeDto;

public interface MarketSubscribeDao {

	public Integer insert(MarketSubscribe marketSubscribe);

	public Integer updateToDel(Integer id, Integer companyId);

	public List<MarketSubscribe> queryByCompanyId(Integer companyId, Integer size);

	public List<MarketSubscribe> queryByAdmin(MarketSearchDto searchDto, PageDto<MarketSubscribeDto> page);

	public Integer queryCountByAdmin(MarketSearchDto searchDto);
}