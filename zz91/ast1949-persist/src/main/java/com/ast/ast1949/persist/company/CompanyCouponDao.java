/**
 * @author kongsj
 * @date 2014年11月4日
 * 
 */
package com.ast.ast1949.persist.company;

import java.util.List;

import com.ast.ast1949.domain.company.CompanyCoupon;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyCouponDto;

public interface CompanyCouponDao {
	
	public Integer insert(CompanyCoupon companyCoupon);

	public Integer updateStatus(CompanyCoupon companyCoupon);

	public Integer updateCouponInfo(CompanyCoupon companyCoupon);

	public CompanyCoupon selectByCode(Integer companyId, String code,Integer type);

	public List<CompanyCoupon> selectByCompanyId(Integer companyId);
	
	public List<CompanyCouponDto> queryCompanyCoupon(String email,PageDto<CompanyCouponDto> page);
	
	public Integer queryCompanyCouponCount(String email);
	
	public CompanyCoupon selectById(Integer id);
	
	public CompanyCoupon selectByServiceName(Integer companyId, String serviceName);
	
	public CompanyCoupon queryByNameCode(Integer companyId, String serviceName,String code);

	public List<CompanyCoupon> selectByCompanyId(Integer companyId,Integer status, String from, String to);

	public CompanyCoupon getCouponByCode(Integer companyId, String code);
}