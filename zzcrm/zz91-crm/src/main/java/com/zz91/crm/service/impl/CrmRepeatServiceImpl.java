package com.zz91.crm.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.zz91.crm.dao.CrmCompanyDao;
import com.zz91.crm.dao.CrmRepeatDao;
import com.zz91.crm.domain.CrmRepeat;
import com.zz91.crm.dto.CrmRepeatDto;
import com.zz91.crm.dto.PageDto;
import com.zz91.crm.service.CrmRepeatService;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2012-2-15 
 */
@Component("crmRepeatService")
public class CrmRepeatServiceImpl implements CrmRepeatService {
	
	@Resource
	private CrmRepeatDao crmRepeatDao;
	@Resource
	private CrmCompanyDao crmCompanyDao;
	
	@Override
	public Integer createCrmRepeat(CrmRepeat repeat) {
		return crmRepeatDao.insertCrmRepeat(repeat);
	}

	@Override
	@Transactional
	public Integer updateCheckStatus(Integer id, Short status,Integer targetId) {
		Integer i=0;
		if (status==1){
			//1.更新审核状态
			i=crmRepeatDao.updateCheckStatus(id, CrmRepeatService.STATUS_CHECK);
			//2.更新公司表repeat_id为目标公司
			if(!id.equals(targetId) && i.intValue()>0){
				Integer j=crmCompanyDao.updateRepeatId(id, targetId);
				if (i==0 || j==0){
					i=0;
				}
			}
		}else {
			i=crmRepeatDao.updateCheckStatus(id, CrmRepeatService.STATUS_CHECK);
		}
		return i;
	}

	@Override
	public PageDto<CrmRepeat> pageCrmRepeat(Short status,
			PageDto<CrmRepeat> page) {
		if (page.getSort()==null){
			page.setSort("gmt_created");
		}
		if (page.getDir()==null){
			page.setDir("desc");
		}
		page.setRecords(crmRepeatDao.queryRepeat(status, page));
		page.setTotals(crmRepeatDao.queryRepeatCount(status));
		return page;
	}
	
	@Override
	public List<CrmRepeatDto> queryRepeatByOrderId(Integer orderId, Short status) {
		return crmRepeatDao.queryRepeatByOrderId(orderId, status);
	}

}
