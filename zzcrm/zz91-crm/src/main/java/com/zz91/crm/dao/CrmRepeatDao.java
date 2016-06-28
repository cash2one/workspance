package com.zz91.crm.dao;

import java.util.List;

import com.zz91.crm.domain.CrmRepeat;
import com.zz91.crm.dto.CrmRepeatDto;
import com.zz91.crm.dto.PageDto;

/** 
 * @author qizj 
 * @email  qizj@zz91.net
 * @version 创建时间：2012-2-15 
 */
public interface CrmRepeatDao {
	
	/**
	 * 添加一条记录
	 * @param repeat
	 * @return
	 */
	public Integer insertCrmRepeat(CrmRepeat repeat);
	
	/**
	 * 更新审核状态
	 * @param id
	 * @param status
	 * @return
	 */
	public Integer updateCheckStatus(Integer id,Short status);
	
	/**
	 * 查询申请记录(group by repeatId)
	 * @param status
	 * @param page
	 * @return
	 */
	public List<CrmRepeat> queryRepeat(Short status,PageDto<CrmRepeat> page);
	
	/**
	 * 查询申请记录总数
	 * @param status
	 * @return
	 */
	public Integer queryRepeatCount(Short status);
	
	/**
	 * 查询重复公司信息(合并用)
	 * @param orderId
	 * @param status
	 * @return
	 */
	public List<CrmRepeatDto> queryRepeatByOrderId(Integer orderId,Short status);
}
