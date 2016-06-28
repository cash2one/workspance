package com.ast.ast1949.persist.phone;

import com.ast.ast1949.domain.phone.PhoneLostCus;

public interface PhoneLostCusDao {
	public PhoneLostCus queryPhoneLostCusBycompanyId(Integer companyId);
	
	public Integer deletePhoneLostCusBycompanyId(Integer companyId);

}
