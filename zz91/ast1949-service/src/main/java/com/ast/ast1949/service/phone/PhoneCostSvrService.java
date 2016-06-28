package com.ast.ast1949.service.phone;

import com.ast.ast1949.domain.phone.PhoneCostSvr;
import com.ast.ast1949.dto.PageDto;

public interface PhoneCostSvrService {
	
	final static String IS_LACK = "1";
	final static String NO_LACK = "0";

	/**
	 * 根据id 检索 domain
	 * @param id
	 * @return
	 */
	public PhoneCostSvr queryById(Integer id);

	/**
	 * 新增来电宝充值服务
	 * @param phoneCostSvr
	 * @return
	 */
	public Integer insert(PhoneCostSvr phoneCostSvr);

	/**
	 * 更新所有 服务属性
	 * @param phoneCostSvr
	 * @return
	 */
	public Integer update(PhoneCostSvr phoneCostSvr);
	
	/**
	 * 后台服务分页
	 */
	public PageDto<PhoneCostSvr> pageByAdmin(PhoneCostSvr phoneCostSvr,PageDto<PhoneCostSvr> page);
	
	/**
	 * 更新扣费价格
	 * @param id
	 * @param telFee
	 * @param clickFee
	 * @return
	 */
	public Integer updateFee(Integer id,Integer companyId,Float telFee,Float clickFee);
	
	/**
	 * 欠费关闭服务
	 * @param id
	 * @param companyId
	 * @return
	 */
	public Integer closeSvr(Integer id,Integer companyId);
	
	/**
	 * 根据消费计算余额
	 * 注：临界值问题。
	 * 如果余额不足时：
	 * 1、记录A的余额不够，则检索是否有其他充值记录 B
	 * 2、有记录B，将余额不足的记录剩余金额加到B记录总金额
	 * 3、扣除B记录的费用
	 * 4、得到最新余额
	 * 5、关闭记录A 调用closeSvr方法
	 * @param id
	 * @param companyId
	 * @param reFee
	 * @return
	 */
	public Integer reduceFee(Integer id,Integer companyId,Float reFee);
	
	/**
	 * 根据公司id检索最早时间激活中的服务
	 * @param companyId
	 * @return
	 */
	public PhoneCostSvr queryByCompanyId(Integer companyId);
	
	/**
	 * 最近余额为零的日期
	 * @param companyId
	 * @return
	 */
	public PhoneCostSvr queryGmtZeroByCompanyId(Integer companyId);
	
	
	/**
	 * 根据companyId 查询充值记录
	 * @param companyId
	 * @return
	 */
	public String countFeeByCompanyId(Integer companyId);
	
	/**
	 * 根据companyId统计余额，扣余额，插入扣款记录
	 * @param companyId
	 * @return
	 */
	public Float reduceFee(Integer companyId);

	/**
	 * 
	 * @param companyId
	 */
	public Float sumLave(Integer companyId);
	/**
	 * 扣服务费
	 * @param companyId
	 * @param reFee
	 * @return
	 */
	public Integer reduceServiceFee(Integer companyId, Float reFee);

}
