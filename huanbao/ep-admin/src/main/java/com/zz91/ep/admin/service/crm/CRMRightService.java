package com.zz91.ep.admin.service.crm;

import java.util.List;

import com.zz91.ep.domain.crm.CrmRight;
import com.zz91.ep.dto.ExtTreeDto;


public interface CRMRightService {


	/**
	 * 通过公司ID和会员CODE查询权限列表
	 * @param companyId
	 * @param memberCode
	 * @param memberCodeBlock 
	 * @return
	 */
	public String[] getCrmRightListByCompanyIdAndMemberCode(Integer companyId,
			String memberCode, String memberCodeBlock, String project);
	/**
	 * 创建权限信息，code根据类别生成
	 * code生成规则：同级类别最大code＋1
	 */
	public Integer createRight(CrmRight right, String parentCode);
	/**
	 * 更新权限基本信息，不更新code
	 */
	public Integer updateRight(CrmRight right);
	/**
	 * 删除自己及其所有子类别
	 */
	public Integer deleteRightByCode(String code);
	
	public CrmRight queryOneRight(String code);
	
	public List<ExtTreeDto> queryTreeNode(String parentCode);

}
