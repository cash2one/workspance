package com.ast.ast1949.persist.phone;

import java.util.List;

import com.ast.ast1949.domain.phone.PhoneCostSvr;
import com.ast.ast1949.dto.PageDto;

public interface PhoneCostSvrDao {

	public Integer insert(PhoneCostSvr phoneCoseSvr);

	public Integer queryListCountByAdmin(PhoneCostSvr phoneCostSvr);

	public List<PhoneCostSvr> queryListByAdmin(PhoneCostSvr phoneCostSvr,PageDto<PhoneCostSvr> page);

	public Integer updateSvr(PhoneCostSvr phoneCostSvr);

	public List<PhoneCostSvr> queryListByCost(Integer companyId);
	
	public List<PhoneCostSvr> queryGmtZeroByCompanyId(Integer companyId);

	public String countFeeByCompanyId(Integer companyId);

	public PhoneCostSvr queryById(Integer id);
	
	public String sumLaveByCompanyId(Integer companyId);
	
	public PhoneCostSvr queryPhoneService(Integer companyId);

	/**
	 * 补齐所有的余额
	 * @param companyId
	 * @return
	 */
	public Integer updateLaveFull(Integer companyId);

}
