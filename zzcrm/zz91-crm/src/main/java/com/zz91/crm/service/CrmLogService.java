/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-12-10
 */
package com.zz91.crm.service;

import java.text.ParseException;
import java.util.List;

import com.zz91.crm.domain.CrmLog;
import com.zz91.crm.dto.CrmLogDto;
import com.zz91.crm.dto.PageDto;
import com.zz91.crm.exception.LogicException;

/**
 * @author totly created on 2011-12-10
 */
public interface CrmLogService {

    /**
     * 添加小记(同时更新联系次数)
     * @param crmLog
     * @param id 销售/客服用户挑选公司表id
     * @param flag null:不是拖单/毁单 0:拖单 1:毁单
     * @return
     * @throws ParseException 
     */
    public Integer createCrmLog(CrmLog crmLog) throws LogicException;
    /**
     * 根据cid查询小记信息
     * @param cid
     * @param callType 
     * @return
     */
    public List<CrmLog> queryCrmLogByCid(Integer cid, Short callType);
	
	/**
	 * 根据部门code查询销售人员
	 * @param deptCode
	 * @return
	 */
	public List<CrmLog> querySaleAccountByDeptCode(String deptCode);
	
    /**
     * 查询明天联系客户数量
     * @param account
     * @return
     */
    public Integer querytomContactCount(String account);
    
    /**
     * 查询今天所有联系小记
     * @param account
     * @param tdate
     * @param star 
     * @param disable 
     * @param type
     * @return
     */
	public PageDto<CrmLogDto> pageCrmLogByToday(String account, String tdate,
			PageDto<CrmLogDto> page, Short disable, Short star, Short type);
	
	/**
	 * 根据所转星级查询公司信息
	 * @param star
	 * @param tDate
	 * @param account
	 * @return
	 */
	public List<CrmLogDto> queryTurnStarCompByStar(Short star, String tDate,
			String account);
}