package com.ast.ast1949.persist.sample;

import java.util.List;
import java.util.Map;

import com.ast.ast1949.domain.sample.Identity;

public interface IdentityDAO {

	Integer  insert(Identity record);

	int updateByPrimaryKey(Identity record);

	int updateByPrimaryKeySelective(Identity record);

	Identity selectByPrimaryKey(Integer id);

	int deleteByPrimaryKey(Integer id);

	public Identity queryIdentityByCompanyId(Integer companyId);

	public void updateFrontByCompanyId(Integer companyId, String str);

	public void updateBackByCompanyId(Integer companyId, String str);

	Integer queryListByFilterCount(Map<String, Object> filterMap);

	List<Identity> queryListByFilter(Map<String, Object> filterMap);
}