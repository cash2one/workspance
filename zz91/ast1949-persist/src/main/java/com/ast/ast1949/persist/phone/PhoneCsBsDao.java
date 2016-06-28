package com.ast.ast1949.persist.phone;

import com.ast.ast1949.domain.phone.PhoneCsBs;

public interface PhoneCsBsDao {
	/**
	 * 插入记录到phone_cs_bs 表中
	 * @param phoneCsBs
	 * @return
	 */
	public Integer insert(PhoneCsBs phoneCsBs);
	
	public PhoneCsBs queryByCompanyId(Integer companyId);

}
