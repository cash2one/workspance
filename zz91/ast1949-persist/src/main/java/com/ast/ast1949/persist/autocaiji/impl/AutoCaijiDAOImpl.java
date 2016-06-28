package com.ast.ast1949.persist.autocaiji.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.autocaiji.AutoCaiji;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.autocaiji.AutoCaijiDAO;

@SuppressWarnings("unchecked")
@Component("autoCaijiDAO")
public class AutoCaijiDAOImpl extends BaseDaoSupport implements AutoCaijiDAO {

    final static String SQL_PREFIX = "autoCaiji";
    
    
    @Override
    public List<AutoCaiji> queryPageLog(PageDto<AutoCaiji> page, String from,
            String to) {
        Map<String , Object> map = new HashMap<String, Object>();
        map.put("start", from);
        map.put("end", to);
        map.put("page", page);
        return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryPageRang"), map);
    }

    @Override
    public List<AutoCaiji> queryListLog( String from,
            String to) {
        Map<String , Object> map = new HashMap<String, Object>();
        map.put("start", from);
        map.put("end", to);
        return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryListRang"), map);
    }
    @Override
    public Integer queryCount(String from, String to) {
        Map<String , Object> map = new HashMap<String, Object>();
        map.put("start", from);
        map.put("end", to);
        return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryCount"), map);
    }

}
