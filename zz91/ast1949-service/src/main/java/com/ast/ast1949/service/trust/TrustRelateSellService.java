/**
 * @author shiqp
 * @date 2015-07-22
 */
package com.ast.ast1949.service.trust;

import java.util.List;

import com.ast.ast1949.domain.company.Company;

public interface TrustRelateSellService {
	/**
	 * 根据采购id获取供应公司的信息
	 * @param buyId
	 * @return
	 */
	public List<Company> queryCompanyByBuyId(Integer buyId,Integer companyId);

}
