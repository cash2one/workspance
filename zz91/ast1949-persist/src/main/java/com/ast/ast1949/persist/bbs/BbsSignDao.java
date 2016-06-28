/**
 * @author shiqp 日期:2014-11-24
 */
package com.ast.ast1949.persist.bbs;

import com.ast.ast1949.domain.bbs.BbsSignDO;

public interface BbsSignDao {
	/**
	 * 最新的签名
	 * @param companyId
	 * @return
	 */
	public BbsSignDO querySignByCompanyId(Integer companyId);

}
