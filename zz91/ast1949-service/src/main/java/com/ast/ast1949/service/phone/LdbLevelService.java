package com.ast.ast1949.service.phone;

import com.ast.ast1949.domain.phone.LdbLevel;

public interface LdbLevelService {
	public void resetLevel(Integer companyId,double fee);
	
	public LdbLevel queryByCompanyId(Integer companyId);
}
