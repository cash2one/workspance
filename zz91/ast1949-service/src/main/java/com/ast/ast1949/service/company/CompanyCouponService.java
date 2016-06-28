/**
 * @author kongsj
 * @date 2014年11月4日
 * 
 */
package com.ast.ast1949.service.company;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ast.ast1949.domain.company.CompanyCoupon;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyCouponDto;


public interface CompanyCouponService {

	final static Integer STATUS_OPEN = 1;
	final static Integer STATUS_CLOSE = 2;
	final static Integer STATUS_SUCCESS = 3;
	final static Integer STATUS_ING = 4;
	
	
	final static Map<Integer, String> COUPON_NAME_MAP = new  HashMap<Integer, String>();
	final static Map<Integer, Integer> REDUCE_MAP = new  HashMap<Integer, Integer>();
	
	final static String FROM  = "2015-1-1";
	final static String TO  = "2016-1-1";
	
	/**
	 * 创建一个优惠券
	 * @param companyId
	 * @param serviceName
	 * @param reducePrice
	 * @return
	 */
	public Integer createOneCoupon(Integer companyId,Integer serviceId);

	/**
	 * 关闭一个优惠券服务
	 * @param companyId
	 * @param id
	 * @return
	 */
	public Integer closeOneCoupon(Integer companyId,Integer id);
	
	/**
	 * 创建一个优惠券 通用
	 */
	public Integer createOneCoupon(CompanyCoupon companyCoupon);

	/**
	 * 生意管家页面展示
	 * @param companyId
	 * @return
	 */
	public List<CompanyCoupon> queryForMyrc(Integer companyId);
	/**
	
	 * 验证是否有获取过优惠券
	 * @param companyId
	 * @return 0:都不可领取，1:可领取会员券 2:可领取广告券 3:都可以领取
	 */
	public Integer validateCouponHaveOrNot(Integer companyId);
	/**
	 * 查找所有领用了优惠劵的客户
	 * @return
	 */
	public PageDto<CompanyCouponDto> queryCompanyCoupon(String email,PageDto<CompanyCouponDto>page);
	/**
	 * 通过Id查找优惠劵客户
	 * @param id
	 * @return
	 */
	public CompanyCoupon queryCompanyCouponById(Integer id);
	/**
	 * 更新CompanyCoupon
	 * @param companyCoupon
	 * @return
	 */
	public Integer updateCompanyCoupon(CompanyCoupon companyCoupon);
	/**
	 * 获取优惠劵码 
	 * @param codeId   优惠服务id
	 * @return
	 */
	
	public CompanyCoupon yzCoupon(Integer codeId, Integer companyId);
	/**
	 * 验证优惠劵码
	 * @param companyId
	 * @param codeId
	 * @param code
	 * @return
	 */
	public CompanyCoupon yzCouponCode(Integer companyId,Integer codeId ,String code);
	/**
	 * 获取客户所有领用的优惠劵
	 * @param companyId
	 * @return
	 */
	public List<CompanyCoupon> queryCouponByCompanyId(Integer companyId);
	/**
	 * 记录支付没成功的人(待支付) status=4
	 * @param companyCoupon
	 * @return
	 */
	public Integer createCompanyCouponByStatus(Integer companyId,Integer serviceId);
	
	/**
	 * 激活优惠券
	 * @param id
	 * @return
	 */
	public Integer openCoupon(Integer id);
	
	/**
	 * 验证优惠券是否可用
	 */
	public CompanyCoupon getCouponByCode(Integer companyId,String code);
}
