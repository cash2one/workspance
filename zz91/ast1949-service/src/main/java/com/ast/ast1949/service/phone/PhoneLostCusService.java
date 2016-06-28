package com.ast.ast1949.service.phone;

import com.ast.ast1949.domain.phone.PhoneLostCus;

public interface PhoneLostCusService  {
	public PhoneLostCus queryPhoneLostCusBycompanyId(Integer companyId);
	
	public Integer deletePhoneLostCusBycompanyId(Integer companyId);

}
