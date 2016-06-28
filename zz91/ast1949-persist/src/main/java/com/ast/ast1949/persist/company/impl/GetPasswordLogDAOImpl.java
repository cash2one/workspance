package com.ast.ast1949.persist.company.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.GetPasswordLog;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.company.GetPasswordLogDAO;

@Component("getPasswordLogDAO")
public class GetPasswordLogDAOImpl extends BaseDaoSupport implements GetPasswordLogDAO {
    
    final static String SQL_PREFIX = "getPasswordLog";

    @SuppressWarnings("unchecked")
    @Override
    public List<GetPasswordLog> queryPasswordLogByCompanyId(Integer companyId , String type , String date) {
        Map<String , Object> map = new HashMap<String, Object>();
        map.put("companyId", companyId);
        map.put("type", type);
        map.put("date", date);
        return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryPasswordLogByCompanyId"), map);
    }

    @Override
    public Integer insertPasswordLog(GetPasswordLog getPasswordLog) {
        return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insertPasswordLog"), getPasswordLog);
    }

}
