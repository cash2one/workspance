package com.zz91.mail.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zz91.mail.dao.TemplateDao;
import com.zz91.mail.domain.TemplateDomain;
import com.zz91.mail.domain.dto.PageDto;
import com.zz91.mail.service.TemplateService;
import com.zz91.util.lang.StringUtils;

@Service("templateService")
public class TemplateServiceImpl implements TemplateService {

	@Resource
	private TemplateDao templateDao;

	@Override
	public TemplateDomain queryTemplateByCode(String code) {
		TemplateDomain templateDomain = null;
		if (StringUtils.isNotEmpty(code)) {
			templateDomain = templateDao.queryTemplateByCode(code);
		}
		return templateDomain;
	}

	@Override
	public TemplateDomain queryTemplateById(Integer id) {
		return templateDao.queryTemplateById(id);
	}

	@Override
	public Integer insertTemplate(TemplateDomain template) {
		return templateDao.insertTemplate(template);
	}

	@Override
	public Integer updateTemplate(TemplateDomain template) {
		return templateDao.updateTemplate(template);
	}

	@Override
	public Integer deleteTemplateById(Integer id) {
		return templateDao.deleteTemplateById(id);
	}

	@Override
	public Integer deleteTemplateByCode(String code) {
		return templateDao.deleteTemplateByCode(code);
	}

	@Override
	public PageDto<TemplateDomain> pageAllTemplates(TemplateDomain template,
			PageDto<TemplateDomain> page) {
		page.setRecords(templateDao.queryTemplate(template, page));
		page.setTotals(templateDao.queryTemplateCount(template));
		return page;
	}

}
