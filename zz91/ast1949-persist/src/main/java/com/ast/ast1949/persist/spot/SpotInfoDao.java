package com.ast.ast1949.persist.spot;

import com.ast.ast1949.domain.spot.SpotInfo;

public interface SpotInfoDao {
	public SpotInfo queryOne(Integer id);

	public Integer insert(SpotInfo spotInfo);

	public Integer update(SpotInfo spotInfo);
}
