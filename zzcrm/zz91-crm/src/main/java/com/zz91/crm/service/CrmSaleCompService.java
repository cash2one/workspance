/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-12-10
 */
package com.zz91.crm.service;

import java.util.Date;
import java.util.List;

import com.zz91.crm.domain.CrmSaleComp;
import com.zz91.crm.domain.CrmStatistics;
import com.zz91.crm.domain.CrmTurnStarStatistics;
import com.zz91.crm.dto.CrmContactStatisticsDto;
import com.zz91.crm.dto.CrmSaleDataDto;
import com.zz91.crm.dto.CrmSaleStatisticsDto;
import com.zz91.crm.dto.PageDto;
import com.zz91.crm.dto.SaleCompanyDataDto;
import com.zz91.crm.exception.LogicException;

/**
 * @author totly created on 2011-12-10
 */
public interface CrmSaleCompService {

    public static final Short COMPANY_TYPE_NORMAL = 0; // 普通客户
    public static final Short COMPANY_TYPE_IMPORTANT = 1; // 重点客户
    public static final Short DISABLE_STATUS_UNCHECK = 0; // 放入废品池审核失败
    public static final Short DISABLE_STATUS_CHECK = 1; // 放入废品池审核成功
    public static final Short STATUS_DISABLE = 0; // 无效数据
    public static final Short STATUS_ABLE = 1; // 有效数据
    public static final Short AUTO_BLOCK_TRUE= 1; //自动掉入公海
    public static final Short AUTO_BLOCK_FALSE = 0; //手动放入公海
    public static final Short DRAG_DESTORY_THREE = 2; //拖单/毁单
    public static final Short STAR = 5; //五星客户
    public static final Short WASTE_STATUS = 1; //废品池状态
    
	static final String DATA_INPUT_CONFIG="data_input_config";
	static final String HUANBAO_DEPT_CODE="huanbao_dept_code";
	static final String HUANBAO_SERVICE_DEPT_CODE="huanbao_service_dept_code";

    /**
     * 放入个人库(同时更新公司表类型ctype)
     * @param crmSaleComp
     * @param ctype 
     * @return
     */
    public Integer createCrmSaleComp(CrmSaleComp crmSaleComp, Short ctype) throws LogicException;

    /**
     * 放入公海/自动掉公海(同时更新公司表状态)type:0:放入公海 1:自动掉公海
     * @param id
     * @param type
     * @return
     */
    public Integer updateStatus(Integer id, Short type) throws LogicException;

    /**
     * 设置重点客户
     * @param id
     * @param type
     * @return
     */
    public Integer updateCompanyType(Integer id, Short type);

    /**
     * 放入废品池
     * @param id
     * @return
     */
    public Integer updateDisableStatus(Integer id);

    /**
     * 审核放入废品池(审核通过,同时更改公司类型)
     * @param cid 为公司id 
     * @param status 0:审核失败 1:审核成功
     * @return
     */
    public Integer checkDisableStatus(Integer cid, Short status);

    /**
     * 查询客户挑选记录
     * @return
     */
    public List<CrmSaleComp> queryCrmSaleCompByCid(Integer cid);
    
    /**
     * 判断改用户是否已被挑选
     * @param cid
     * @return
     */
    public boolean isExsitCrmSale(Integer cid);
    
    /**
     * 重新分配客户
     * @param crmSaleComp
     * @return
     * @throws LogicException
     */
    public Integer reSetCrmSaleComp(CrmSaleComp crmSaleComp) throws LogicException;
    
    /**
     * 查询联系量统计结果
     * @param account 帐号
     * @param deptCode  部门
     * @param start  开始时间
     * @param end  结束时间
     * @param group 是否合并统计数（true：合计统计结果;false：每天统计结果）
     * @return
     */
    public List<CrmContactStatisticsDto> queryContactData(String account, String deptCode, String start, String end, Short group);
    
    /**
     * 查询注册统计结果
     * @return
     */
    public List<CrmSaleStatisticsDto> queryRegisterData(String start, String end, Short group);
    
    /**
     * 查询用户客户数统计结果
     * @return
     */
    public List<SaleCompanyDataDto> querySaleCompanyData(String account, String deptCode);
    
    /**
     * 查询我今天联系量统计结果
     * @param account
     * @param deptCode
     * @return
     */
    public CrmContactStatisticsDto queryMyContactDataByToday(String account,String deptCode);
    
    /**
     * 查询部门今天联系量统计结果
     * @param account
     * @param deptCode
     * @return
     */
    public List<CrmContactStatisticsDto> queryDeptContactDataByToday(String deptCode);
    
    /**
     * 数据统计概览
     * @param page
     * @return
     */
    public PageDto<CrmStatistics> pageCrmStatistics(PageDto<CrmStatistics> page);

    /**
     * 查询时间(用于颜色显示)
     * @return
     */
	public List<Date> queryGmtTarget();
	
	/**
	 * @param cid
	 * @return
	 */
	public Integer updateStatusByCid(Integer cid);

	/**
	 * 查询转四星/转五星统计结果
	 * @param start
	 * @param end
	 * @param page
	 * @return
	 */
	public PageDto<CrmTurnStarStatistics> pageFourOrFiveStar(String start,
			String end, PageDto<CrmTurnStarStatistics> page);

	/**
	 * @param string
	 * @return
	 */
	public CrmSaleDataDto querySaleNameAndSaleDept(String account);

	/**
	 * @param account 账户
	 * @param oldDept 原所在部门
	 * @param newDept 现在所在部门
	 * @return
	 */
	public Integer updateSaleDept(String account, String oldDept, String newDept);
}