/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-8-9 下午04:39:47
 */
package com.zz91.ep.dao.crm;

import java.util.List;

import com.zz91.ep.domain.crm.CrmCompSvr;

public interface CrmCompSvrDao {
	
	public CrmCompSvr queryLastSvr(Integer cid, Integer svrId);

	/**
	 * @param cid
	 * @param svrId
	 * @return
	 */
	public Integer querySeoSvrCount(Integer cid, Integer svrId);
	
	public List<CrmCompSvr> querySvr(Integer cid, Integer svrId);

}
