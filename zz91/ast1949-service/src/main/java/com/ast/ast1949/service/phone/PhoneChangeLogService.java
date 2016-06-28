/**
 * @author kongsj
 * @date 2014年8月22日
 * 
 */
package com.ast.ast1949.service.phone;

import java.util.List;

import com.ast.ast1949.domain.phone.PhoneChangeLog;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.phone.PhoneChangeLogDto;

public interface PhoneChangeLogService {
	
	final static String TYPE_COMPANY_NAME = "0"; //0:公司名称
	final static String TYPE_COMPANY_INTRO = "1"; // 1:公司简介
	final static String TYPE_COMPANY_BUSINESS = "2"; //2:主营业务
	final static String TYPE_PRODUCT_DETAIL = "3"; //3:产品详细
	
	final static String CHECK_PASS = "1"; //1:通过
	final static String CHECK_WAIT = "0"; //0:未审核
			
	public Integer createLog(String changeType,String changeContent,Integer companyId,Integer targetId);
	
	public Integer updateContent(Integer id ,String changeContent);
	
	public Integer updateStatus(Integer id,String status);
	
	public PhoneChangeLog queryPhoChangeLogbyid(Integer id);

	public PageDto<PhoneChangeLogDto> queryAllPhoneChangeLogs(PageDto<PhoneChangeLogDto> page,String checkStatus);
	
	public Integer updateChangeContextByType(Integer id);
	
}
