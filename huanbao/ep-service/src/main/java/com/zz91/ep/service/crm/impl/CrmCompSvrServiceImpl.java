/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-8-9 下午04:38:36
 */
package com.zz91.ep.service.crm.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.ep.dao.crm.CrmCompSvrDao;
import com.zz91.ep.domain.crm.CrmCompSvr;
import com.zz91.ep.service.crm.CrmCompSvrService;
import com.zz91.util.Assert;
import com.zz91.util.datetime.DateUtil;

@Component("crmCompSvrService")
public class CrmCompSvrServiceImpl implements CrmCompSvrService {
	
	@Resource
	private CrmCompSvrDao compSvrDao;

	@Override
	public CrmCompSvr queryLastSvr(Integer cid, Integer svrId) {
		return compSvrDao.queryLastSvr(cid, svrId);
	}

	@Override
	public Integer querySeoSvrCount(Integer cid, Integer svrId) {
		Assert.notNull(cid, "the cid is not null");
		Assert.notNull(svrId, "the svrId is not null");
		return compSvrDao.querySeoSvrCount(cid,svrId);
	}

	@Override
	public Integer queryYearByCid(Integer cid) {
		do {
			if(cid==null){
				break;
			}
			List<CrmCompSvr> list = compSvrDao.querySvr(cid,ZHT_SVR_ID);
			if(list==null||list.size()<1){
				break;
			}
			Integer i  =0;
			for(CrmCompSvr obj:list){
				i =DateUtil.getIntervalMonths(obj.getGmtStart(), obj.getGmtEnd())/12 + i;
			}
			
			return i;
		} while (false);
		return 0;
	}

}
