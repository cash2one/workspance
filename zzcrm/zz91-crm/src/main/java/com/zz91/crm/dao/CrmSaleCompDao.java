/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-12-10
 */
package com.zz91.crm.dao;

import java.util.Date;
import java.util.List;
import com.zz91.crm.domain.CrmLog;
import com.zz91.crm.domain.CrmSaleComp;
import com.zz91.crm.domain.CrmStatistics;
import com.zz91.crm.domain.CrmTurnStarStatistics;
import com.zz91.crm.dto.CrmContactStatisticsDto;
import com.zz91.crm.dto.CrmSaleDataDto;
import com.zz91.crm.dto.CrmSaleStatisticsDto;
import com.zz91.crm.dto.PageDto;

/**
 * @author totly created on 2011-12-10
 */
public interface CrmSaleCompDao {

	public static final Short TYPE_CONTACT_ABLE = 0;// 有效效联系方式
	public static final Short TYPE_CONTACT_DISABLE = 1;// 无效联系方式
	public static final Short SALE_TYPE1 = 0;// 销售
	public static final Short SALE_TYPE2 = 1;// 客服

    /**
     * 新建销售-公司关系
     * @param crmSaleComp
     * @return
     */
    public Integer createCrmSaleComp(CrmSaleComp crmSaleComp);

    /**
     * 放入公海/自动掉公海(同时更新公司表状态)type:0:放入公海 1:自动掉公海
     * @param cid
     * @param type
     * @return
     */
    public Integer updateStatus(Integer cid, Short type);

    /**
     * 设置重点客户
     * @param id
     * @param type 0:普通客户 1:重点客户
     * @return
     */
    public Integer updateCompanyType(Integer id, Short type);

    /**
     * 更新是否放入废品池状态
     * @param cid
     * @param status 是否放入废品池 0:否 1:是
     * @return
     */
    public Integer updateDisableStatus(Integer cid, Short status);
    
    /**
     * 更新有效无效联系次数
     * @param cid
     * @param situation 0:有效 其他:无效
     * @param saleDept
     * @param saleAccount 
     * @return
     */
	public Integer updateContactCount(Integer cid, Short situation,Integer logId,Date gmtNextContact, String saleAccount, String saleDept);
	/**
	 * 查看是否存在有效公司记录
	 * @param cid
	 * @param status
	 * @return
	 */
	public Integer queryCountByCidAndStatus(Integer cid,Short status);
	/**
	 * 根据Cid查寻挑选记录
	 * @param Cid
	 * @return
	 */
	public List<CrmSaleComp> queryCrmSaleCompByCid(Integer cid);

	/**
	 * 更新拖单/毁单数
	 * @param id
	 * @param flag 0:拖单 1:毁单
	 */
	public Integer updateOrderCountById(Integer id, Short flag);
	
    /**
     * 查询联系量统计结果
     * @param account 帐号
     * @param deptCode  部门
     * @param start  开始时间
     * @param end  结束时间
     * @param group 是否合并统计数（true：合计统计结果;false：每天统计结果）
     * @return
     */
	public List<CrmContactStatisticsDto> queryContactData(String account,
			String deptCode, String start, String end, Short group);

    /**
     * 查询注册统计结果
     * @return
     */
	public List<CrmSaleStatisticsDto> queryRegisterData(String start, String end,
			Short group);

    /**
     * 查询用户客户数统计结果
     * @return
     */
	public List<CrmSaleDataDto> querySaleCompanyData(String account,
			String deptCode);
	/**
	 * 查询明日联系人数
	 * @param saleAccount
	 * @param saleDept
	 * @return
	 */
	public Integer queryTomContact(String saleAccount, String saleDept);
	
	/**
	 * 查询今日联系人数
	 * @param saleAccount
	 * @param saleDept
	 * @return
	 */
	public Integer queryTodContact(String saleAccount, String saleDept);

	/**
	 * 查询部门中需要统计数据的销售人员
	 * @param account
	 * @param deptCode
	 * @return
	 */
	public List<CrmSaleDataDto> querySales(String account, String deptCode);

	/**
	 * 查询当天联系量统计数据
	 * @param account
	 * @param deptCode
	 * @return
	 */
	public List<CrmLog> queryContactDataByToday(String account, String deptCode);
	
	/**
	 * 查询数据统计
	 * @param page
	 * @return
	 */
	public List<CrmStatistics> queryCrmStatistics(PageDto<CrmStatistics> page);
	
	/**
	 * 查询数据统计数量
	 * @return
	 */
	public Integer queryCrmStatisticsCount();

	/**
	 * 查询时间(用于颜色显示)
	 * @return
	 */
	public List<Date> queryGmtTarget();
	
	/**
	 * 统计今天客户数(客户总数,公海库总数,个人库总数等)
	 * @return
	 */
	public List<CrmSaleDataDto> queryCrmCount();
	
	/**
	 * 查询今天放入公海客户总数
	 * @return
	 */
	public Integer queryTodaySeaCount();
	
	/**
	 * 查询今天挑入公海客户总数
	 * @return
	 */
	public Integer queryTodayChooseSeaCount();
	
	/**
	 * 查询今天新分配客户总数
	 * @return
	 */
	public Integer queryTodayAssignCount();
	
	/**
	 * 查询今天重复客户总数
	 * @return
	 */
	public Integer queryTodayRepeatCount();

	/**
	 * 查询转四星/转五星数据结果
	 * @param start
	 * @param end
	 * @param page
	 * @return
	 */
	public List<CrmTurnStarStatistics> queryFourOrFiveStar(String start,
			String end, PageDto<CrmTurnStarStatistics> page);

	/**
	 * 查询转四星/转五星总数量
	 * @param start
	 * @param end
	 * @return
	 */
	public Integer queryFourOrFiveStarCount(String start, String end);

	/**
	 * @param account
	 * @return
	 */
	public CrmSaleDataDto querySaleNameAndSaleDeptByAccount(String account);

	/**
	 * @param account
	 * @param oldDept
	 * @param newDept
	 * @return
	 */
	public Integer updateSaleDeptByAccountAndDept(String account,
			String oldDept, String newDept);
}