/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-12-21 下午03:51:49
 */
package com.zz91.crm.service;

import org.apache.poi.hssf.usermodel.HSSFRow;

import com.zz91.crm.dto.data.CrmCompanyMakeMap;

public interface DataGatherService {
	
	public boolean createCrmCompany(CrmCompanyMakeMap comp,HSSFRow row, String account);

}
