package com.zz91.sms.service;

import java.util.List;

import com.zz91.sms.domain.Template;

public interface TemplateService {
	
	public Integer create(Template template);
	
	public Integer update(Template template);
	
	public Integer remove(Integer id);
	
	public List<Template> query();
	
	public Template queryOne(Integer id);
}
