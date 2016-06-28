package com.zz91.mail.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zz91.mail.dao.BaseDao;
import com.zz91.mail.dao.TemplateDao;
import com.zz91.mail.domain.TemplateDomain;
import com.zz91.mail.domain.dto.PageDto;

@Repository("templateDao")
public class TemplateDaoImpl extends BaseDao implements TemplateDao {

    final static String SQL_PREFIX = "templateDomain";

    @Override
    public TemplateDomain queryTemplateByCode(String code) {
        return (TemplateDomain) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryTemplateByCode"), code);
    }

    @Override
    public TemplateDomain queryTemplateById(Integer id) {
        return (TemplateDomain) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryTemplateById"), id);
    }

    @Override
    public Integer insertTemplate(TemplateDomain template) {
        return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertTemplate"), template);
    }

    @Override
    public Integer updateTemplate(TemplateDomain template) {
        return (Integer) getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateTemplate"), template);
    }

    @Override
    public Integer deleteTemplateById(Integer id) {
        return (Integer) getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteTemplateById"), id);
    }

    @Override
    public Integer deleteTemplateByCode(String code) {
        return (Integer) getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deleteTemplateByCode"), code);
    }

	@SuppressWarnings("unchecked")
    @Override
	public List<TemplateDomain> queryTemplate(TemplateDomain template,
			PageDto<TemplateDomain> page) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("template", template);
		root.put("page", page);
		return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryTemplate"), root);
	}

	@Override
	public Integer queryTemplateCount(TemplateDomain template) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("template", template);
		return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryTemplateCount"), root);
	}

}
