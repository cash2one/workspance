package com.zz91.crm.service;

import java.util.List;
import com.zz91.crm.domain.CrmRepeat;
import com.zz91.crm.dto.CrmRepeatDto;
import com.zz91.crm.dto.PageDto;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2012-2-15 
 */
public interface CrmRepeatService {

	public static final Short STATUS_CHECK = 1;//已审核
    public static final Short STATUS_UNCHECK = 0;//未审核
    
	/**
	 * 添加一条记录
	 * @param repeat
	 * @return
	 */
	public Integer createCrmRepeat(CrmRepeat repeat);
	
	/**
	 * 更新审核状态
	 * @param id
	 * @param status
	 * @param targetId
	 * @return
	 */
	public Integer updateCheckStatus(Integer id,Short status,Integer targetId);
	
	/**
	 * 查询申请记录(group by repeatId)
	 * @param orderId
	 * @param status
	 * @param page
	 * @return
	 */
	public PageDto<CrmRepeat> pageCrmRepeat(Short status,PageDto<CrmRepeat> page);
	
	/**
	 * 查询重复公司信息(合并用)
	 * @param orderId
	 * @param status
	 * @return
	 */
	public List<CrmRepeatDto> queryRepeatByOrderId(Integer orderId,Short status);
	
}
