package com.ast.ast1949.persist.company;

import java.util.List;

import com.ast.ast1949.domain.company.CompanyAccessView;

public interface CompanyAccessViewDao {
	
	public List<CompanyAccessView> queryByCondition(Integer companyId,Integer targetId, String account, String gmtTarget, Integer size);

	public Integer queryCountByCondition(Integer companyId, Integer targetId,String account, String gmtTarget);

	public Integer insert(CompanyAccessView companyAccessView);
}
