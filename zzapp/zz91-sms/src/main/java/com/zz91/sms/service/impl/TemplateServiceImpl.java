package com.zz91.sms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zz91.sms.dao.TemplateDao;
import com.zz91.sms.domain.Template;
import com.zz91.sms.service.TemplateService;
import com.zz91.util.Assert;

@Component("templateService")
public class TemplateServiceImpl implements TemplateService{
	
	@Resource
	private TemplateDao templateDao;
	
	@Override
	public Integer create(Template template) {
		return templateDao.insert(template);
	}

	@Override
	public List<Template> query() {
		return templateDao.query();
	}

	@Override
	public Integer remove(Integer id) {
		Assert.notNull(id, "the id can not be null!");
		return templateDao.delete(id);
	}

	@Override
	public Integer update(Template template) {
		return templateDao.update(template);
	}

	@Override
	public Template queryOne(Integer id) {
		Assert.notNull(id, "the id can not be null!");
		return templateDao.queryOne(id);
	}

}
