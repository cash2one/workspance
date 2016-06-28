/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-23
 */
package com.ast.ast1949.dto.products;

import java.io.Serializable;

import com.ast.ast1949.domain.products.DescriptionTemplateDO;

/**
 * @author yuyonghui
 *
 */
public class DescriptionTemplateDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DescriptionTemplateDO descriptionTemplateDO;
	private String templateName;

	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public DescriptionTemplateDO getDescriptionTemplateDO() {
		return descriptionTemplateDO;
	}
	public void setDescriptionTemplateDO(DescriptionTemplateDO descriptionTemplateDO) {
		this.descriptionTemplateDO = descriptionTemplateDO;
	}

}
