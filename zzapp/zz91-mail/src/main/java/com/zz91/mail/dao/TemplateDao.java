package com.zz91.mail.dao;

import java.util.List;

import com.zz91.mail.domain.TemplateDomain;
import com.zz91.mail.domain.dto.PageDto;

public interface TemplateDao {

    /**
     * 根据模板编号搜索记录
     * 
     * @param code
     * @return
     */
    TemplateDomain queryTemplateByCode(String code);

    /**
     * 根据模板ID搜索记录
     * 
     * @param id
     * @return
     */
    TemplateDomain queryTemplateById(Integer id);

    /**
     * 插入一条模板记录
     * 
     * @param template
     * @return
     */
    Integer insertTemplate(TemplateDomain template);

    /**
     * 更新一条模板记录
     * 
     * @param template
     * @return
     */
    Integer updateTemplate(TemplateDomain template);

    /**
     * 删除一条模板记录根据模板ID
     * 
     * @param id
     * @return
     */
    Integer deleteTemplateById(Integer id);

    /**
     * 删除一条模板记录根据编号
     * 
     * @param code
     * @return
     */
    Integer deleteTemplateByCode(String code);

	List<TemplateDomain> queryTemplate(TemplateDomain template,
			PageDto<TemplateDomain> page);

	Integer queryTemplateCount(TemplateDomain template);
}
