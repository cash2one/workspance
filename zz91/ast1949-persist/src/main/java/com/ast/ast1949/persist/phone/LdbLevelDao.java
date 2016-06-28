package com.ast.ast1949.persist.phone;

import com.ast.ast1949.domain.phone.LdbLevel;

public interface LdbLevelDao {
	/**
	 * 插入来电宝等级制度
	 * @param ldbLevel
	 * @return
	 */
	public Integer insertLdbLevel(LdbLevel ldbLevel);
	/**
	 * 根据公司id修改来电宝等级和总花费
	 * @param companyId
	 * @return
	 */
	public LdbLevel queryLdbLevelByCompanyId(Integer companyId);
	/**
	 * 更新来电宝等级跟花费
	 * @param ldbLevel
	 * @return
	 */
	public Integer updateLevelByCompanyId(LdbLevel ldbLevel);

}
