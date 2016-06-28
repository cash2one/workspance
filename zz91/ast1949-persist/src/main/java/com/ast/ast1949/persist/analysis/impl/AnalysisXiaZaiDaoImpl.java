package com.ast.ast1949.persist.analysis.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.common.util.Hash;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.analysis.AnalysisXiaZaiKeywords;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.BaseDaoSupport;
import com.ast.ast1949.persist.analysis.AnalysisXiaZaiDao;

@SuppressWarnings("unchecked")
@Component("analysisXiaZaiDao")
public class AnalysisXiaZaiDaoImpl extends BaseDaoSupport implements AnalysisXiaZaiDao {

    final static String SQL_PREFIX = "analysisXiaZaiKeywords";
    
    @Override
    public List<AnalysisXiaZaiKeywords> queryKeywords(Date gmtTarget,
            PageDto<AnalysisXiaZaiKeywords> page) {
        page.setSort("num");
        page.setDir("DESC");
        Map<String , Object> map = new HashMap<String ,  Object>();
        map.put("gmtTarget", gmtTarget);
        map.put("page", page);
        
        return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryKeywords"), map);
    }

    @Override
    public List<AnalysisXiaZaiKeywords> queryKeywordsRang(String kw,
            Date start, Date end) {
        Map<String , Object> map = new HashMap<String, Object>();
        map.put("kw", kw);
        map.put("start", start);
        map.put("end", end);
        
        return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryKeywordsRang"), map);
    }

    @Override
    public Integer queryKeywordsCount(Date gmtTarget) {
        Map<String , Object> map = new HashMap<String, Object>();
        map.put("gmtTarget", gmtTarget);
        
        return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "queryKeywordsCount"), map);
    }

    @Override
    public Integer summaryKeywords(Date gmtTarget) {
        Map<String , Object> map = new HashMap<String, Object>();
        map.put("gmtTarget", gmtTarget);
        
        return (Integer) getSqlMapClientTemplate().queryForObject(addSqlKeyPreFix(SQL_PREFIX, "summaryKeywords"), map);
    }

    @Override
    public Integer insertKeyword(AnalysisXiaZaiKeywords analysisXiaZaiKeywords) {
        return (Integer) getSqlMapClientTemplate().insert(addSqlKeyPreFix(SQL_PREFIX, "insertKeyword"), analysisXiaZaiKeywords);
    }

    @Override
    public Integer updateKeywordOfNum(
            AnalysisXiaZaiKeywords analysisXiaZaiKeywords) {
        
        return getSqlMapClientTemplate().update(addSqlKeyPreFix(SQL_PREFIX, "updateKeywordOfNum"), analysisXiaZaiKeywords);
    }

    @Override
    public List<AnalysisXiaZaiKeywords> queryListByFromTo(Date gmtTarget) {
        Map<String , Object> map = new HashMap<String, Object>();
        map.put("gmtTarget", gmtTarget);
        
        return getSqlMapClientTemplate().queryForList(addSqlKeyPreFix(SQL_PREFIX, "queryListByFromTo"), map);
    }

}
