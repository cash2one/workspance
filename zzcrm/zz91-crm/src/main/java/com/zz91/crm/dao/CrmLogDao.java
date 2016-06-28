/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-12-10
 */
package com.zz91.crm.dao;

import java.util.List;

import com.zz91.crm.domain.CrmLog;
import com.zz91.crm.dto.CrmLogDto;
import com.zz91.crm.dto.PageDto;

/**
 * @author totly created on 2011-12-10
 */
public interface CrmLogDao {
	
	public static final Short SITUATION_ABLE = 0;//有效联系
	public static final Short SITUATION_DISABLE_1 = 1;//无进展
	public static final Short SITUATION_DISABLE_2 = 2;//无人接听
	public static final Short SITUATION_DISABLE_3 = 3;//号码错误
	public static final Short SITUATION_DISABLE_4 = 4;//停机
	public static final Short SITUATION_DISABLE_5 = 5;//关机

    /**
     * 添加小记
     * @param crmLog
     * @return
     */
    public Integer createCrmLog(CrmLog crmLog);
    /**
     * 根据cid查询小记信息
     * @param cid
     * @param callType 
     * @return
     */
	public List<CrmLog> queryCrmLogByCid(Integer cid, Short callType);
	
	/**
	 * 查询部门人员根据部门code
	 */
	public List<CrmLog> queryAccountByDeptCode(String deptCode);
	
	/**
	 * 查询明天客户联系数量
	 * @param account
	 * @return
	 */
	public Integer querytomContactCount(String account);
	
	/**
	 * 查询今天所有小记
	 * @param account
	 * @param tdate
	 * @param page
	 * @param star 
	 * @param disable 
	 * @param type 
	 * @return
	 */
	public List<CrmLogDto> queryCrmLogByToday(String account, String tdate,PageDto<CrmLogDto> page, 
			Short disable, Short star, Short type);
	
	/**
	 * 查询今天所有小记数量
	 * @param account
	 * @param tdate
	 * @param star 
	 * @param disable 
	 * @param type 
	 * @param page
	 * @return
	 */
	public Integer queryCrmLogCountByToday(String account, String tdate, Short disable, Short star, Short type);
	 
	/**
	 * 查询转四星,转五星的公司信息
	 * @param star
	 * @param tDate
	 * @param account
	 * @return
	 */
	public List<CrmLog> queryTurnStarCompByStar(Short star, String tDate,
			String account);
	/**
	 * 查询今天转四星,转五星的销售人员
	 * @param star
	 * @param tDate
	 * @param account
	 * @return
	 */
	public List<CrmLog> queryTurnFourOrFiveAccountByToday();
	
	/**
	 * 查询今天销售销售记录
	 * @param saleAccount
	 * @return
	 */
	public List<CrmLog> queryFourOrFiveBySaleAccountToday(String saleAccount);
}