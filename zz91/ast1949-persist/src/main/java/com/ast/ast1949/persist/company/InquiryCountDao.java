/**
 * @author kongsj
 * @date 2014年5月30日
 * 
 */
package com.ast.ast1949.persist.company;

import java.util.List;

import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyDto;

public interface InquiryCountDao {
	public Integer queryByCompanyId(Integer companyId);
	//统计发布询价公司
	public List<Integer> queryCompany(PageDto<CompanyDto> page);
	//统计发布询价公司数量
	public Integer queryCompanyCount();
}
