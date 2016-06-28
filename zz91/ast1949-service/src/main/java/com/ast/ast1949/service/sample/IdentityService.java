package com.ast.ast1949.service.sample;

import java.util.Map;

import com.ast.ast1949.domain.sample.Identity;
import com.ast.ast1949.dto.PageDto;

public interface IdentityService {
	
	//状态 00-申请  01-审核成功  02-审核失败  03-失效
	final static String STATUS_CHECKING = "00";
	final static String STATUS_PASS = "01";
	final static String STATUS_FAILURE = "02";
	final static String STATUS_EXPIRED = "03";
	
	Integer insert(Identity identity);

	int updateByPrimaryKey(Identity record);

	int updateByPrimaryKeySelective(Identity record);

	Identity selectByPrimaryKey(Integer id);

	int deleteByPrimaryKey(Integer id);

	public Identity queryIdentityByCompanyId(Integer companyId);

	public void updateFrontByCompanyId(Integer companyId, String str);

	public void updateBackByCompanyId(Integer companyId, String str);

	PageDto<Identity> queryListByFilter(PageDto<Identity> page, Map<String, Object> map);
}
