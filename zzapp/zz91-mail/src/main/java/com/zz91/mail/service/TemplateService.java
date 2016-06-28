package com.zz91.mail.service;

import com.zz91.mail.domain.TemplateDomain;
import com.zz91.mail.domain.dto.PageDto;

public interface TemplateService {

	public TemplateDomain queryTemplateByCode(String code);

	public TemplateDomain queryTemplateById(Integer id);

	public Integer insertTemplate(TemplateDomain template);

	public Integer updateTemplate(TemplateDomain template);

	public Integer deleteTemplateById(Integer id);

	public Integer deleteTemplateByCode(String code);

	public PageDto<TemplateDomain> pageAllTemplates(TemplateDomain template,
			PageDto<TemplateDomain> page);
}
