/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-8-9 下午04:22:31
 */
package com.zz91.ep.service.crm;

import com.zz91.ep.domain.crm.CrmCompSvr;

public interface CrmCompSvrService {
	
	public final static int ZHT_SVR_ID=2;
	/**
	 * 查询最后一次服务信息
	 * @param cid
	 * @return
	 */
	public CrmCompSvr queryLastSvr(Integer cid,Integer svrId);
	
	/**
	 * 检索 公司高会服务年限 根据中环通服务开通的数量，统计每一个服务的服务开始时间和结束时间的年
	 * @param cid
	 * @param svrId
	 * @return
	 */
	public Integer queryYearByCid(Integer cid);
	
	/**
	 * 查询seo服务数量
	 * @param cid
	 * @param svrId
	 * @return
	 */
	public Integer querySeoSvrCount(Integer cid,Integer svrId);
	
}
