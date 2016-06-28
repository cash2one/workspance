package com.ast.ast1949.persist.sample;

import com.ast.ast1949.domain.sample.ContactClickLog;

public interface ContactClickLogDao {

	public ContactClickLog queryById(Integer companyId, Integer targetId);

	public Integer insert(ContactClickLog contactClickLog);
	
	/**
	 * 已查看的个数
	 * @param companyId
	 * @return
	 */
	public Integer countClick(Integer companyId);
}
