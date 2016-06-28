package com.zz91.sms.dao;

import java.util.List;

import com.zz91.sms.domain.Template;

public interface TemplateDao {
	
	public Integer insert(Template template);
	
	public Integer update(Template template);
	
	public Integer delete(Integer id);
	
	public List<Template> query();
	
	public Template queryOne(Integer id);
	
	public Template queryTemplateByCode(String code);
}
