package com.ast.ast1949.persist.company.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.CompanyAttest;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyAttestDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.company.CompanyAttestDAO;

/**
 * 公司工商或个人注册信息
 * 
 * @author zhouzk
 * 
 */
@Component("companyAttestDAO")
@SuppressWarnings("unchecked")
public class CompanyAttestDAOImpl extends BaseDaoSupport implements CompanyAttestDAO {

    private static String SQL_PREFIX = "companyAttest";
    
    @Override
    public List<CompanyAttestDto> queryCompanyAttest(CompanyAttest companyAttest,String compName,
            PageDto<CompanyAttestDto> page) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("companyAttest", companyAttest);
        map.put("compName", compName);
        map.put("page", page);
        return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "query"), map);
    }
    
    @Override
    public Integer queryCompanyAttestCount (CompanyAttest companyAttest ,String compName) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("companyAttest", companyAttest);
        map.put("compName", compName);
        return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryCount"), map);
    }

    @Override
    public CompanyAttest queryByCondition(CompanyAttest companyAttest) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("companyAttest", companyAttest);
        return (CompanyAttest) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryByCondition"), map);
    }

    @Override
    public Integer insertCompanyAttest(CompanyAttest companyAttest) {
        return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insert"), companyAttest);
    }
    @Override
    public CompanyAttest queryAttestByCid (Integer companyId) {
    	return (CompanyAttest) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryAttestByCid"), companyId);
    }
    @Override
    public CompanyAttest queryAttestById (Integer id) {
    	return (CompanyAttest) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryAttestById"), id);
    }
    @Override
    public Integer updateCompanyAttest(CompanyAttest companyAttest) {
        return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "update"), companyAttest);
    }
    @Override
    public Integer updateCheckStatusById (Integer id, String checkStatus, String checkPerson) {
    	 Map<String, Object> map = new HashMap<String, Object>();
         map.put("id", id);
         map.put("checkStatus", checkStatus);
         map.put("checkPerson", checkPerson);
         return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateCheckStatusById"), map);
    }
    @Override
    public Integer deleteCompanyAttest(Integer id) {
        return getSqlMapClientTemplate().delete(addSqlKeyPreFix(SQL_PREFIX, "delete"), id);
    }

}
