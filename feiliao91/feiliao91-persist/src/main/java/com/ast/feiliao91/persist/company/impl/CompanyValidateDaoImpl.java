/**
 * @author shiqp
 * @date 2016-01-09
 */
package com.ast.feiliao91.persist.company.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.feiliao91.domain.company.CompanyValidate;
import com.ast.feiliao91.domain.company.CompanyValidateSearch;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.company.CompanyValidateDto;
import com.ast.feiliao91.persist.BaseDaoSupport;
import com.ast.feiliao91.persist.company.CompanyValidateDao;
@Component("companyValidateDao")
public class CompanyValidateDaoImpl extends BaseDaoSupport implements CompanyValidateDao {
	final static String SQL_PREFIX="companyValidate";

	@Override
	public Integer insertValidate(CompanyValidate validate) {
		return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insertValidate"),validate);
	}

	@Override
	public CompanyValidate queryValidateByNameAndType(String targetName, String targetType) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("targetName", targetName);
		map.put("targetType", targetType);
		return (CompanyValidate) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryValidateByNameAndType"), map);
	}

	@Override
	public Integer updateValidateById(Integer id) {
		return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateValidateById"), id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyValidate> querycompanyValidateByAdmin(
			PageDto<CompanyValidateDto> page, CompanyValidateSearch searchDto){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("page", page);
		map.put("searchDto", searchDto);
		return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "querycompanyValidateByAdmin"), map);
	}
	
	@Override
	public Integer countcompanyValidateByAdmin(CompanyValidateSearch searchDto){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("search", searchDto);
		return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "countcompanyValidateByAdmin"), map);
	}
}
