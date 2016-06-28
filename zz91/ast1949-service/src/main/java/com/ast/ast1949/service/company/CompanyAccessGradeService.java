/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-5-5
 */
package com.ast.ast1949.service.company;

import com.ast.ast1949.domain.company.CompanyAccessGradeDO;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
public interface CompanyAccessGradeService {
	/**
	 * 添加记录
	 * @param companyAccessGradeDO
	 * @return
	 */
	public Integer insertCompanyAccessGrade(CompanyAccessGradeDO companyAccessGradeDO);
	/**
	 * 修改客户类别
	 * @param param 参数：<br/>
	 * 		accessGradeCode 会员类型Code值<br/>
	 * 		companyId 公司ID<br/>
	 * @return
	 */
//	public Integer chengeAccessGradeCode(Map<String, Object> param);
	/**
	 * 根据公司编号统计记录总数
	 * @param companyAccessGrade
	 * @return
	 */
	public Integer queryCountByCompanyId(Integer id);
	
	/**
	 * 设为黑名单
	 * @param id 公司编号
	 * @return 返回影响行
	 */
//	public Integer blacklisted(int id);
	/**
	 * 客户归类
	 * @param id 公司ID
	 * @param code 类别Code值（access_grade_code）
	 * @return
	 */
//	public Integer classify(int id,String code);
	/**
	 * 根据公司编号删除记录
	 * @param id 公司ID
	 * @return
	 */
	public Integer deleteByCompanyId(Integer id);
	/**
	 * 根据公司编号批量删除记录
	 * @param entities
	 * @return
	 */
//	public Integer batchDeleteByCompanyId(int[] entities);
}
