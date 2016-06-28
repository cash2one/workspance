/**
 * Project name: zz91-mail
 * File name: TemplateServiceTest.java
 * Copyright: 2005-2011 ASTO Info TechCo.,Ltd. All rights reserved
 */
package com.zz91.mail.test;

import javax.annotation.Resource;

import com.zz91.mail.domain.TemplateDomain;
import com.zz91.mail.service.TemplateService;

/**
 * @author kongsj
 * @email kongsj@zz91.net
 * @date 2011-11-16
 */
public class TemplateServiceTest extends BaseServiceTestCase {
    @Resource
    private TemplateService templateService;

    private Integer init() {
        TemplateDomain to = new TemplateDomain();
        to.setCode("1");
        to.setName("上节测试");
        to.settContent("$!{name}你好。。。测试。。。");
        Integer i = templateService.insertTemplate(to);
        return i;
    }

    private void delete(Integer id) {
        templateService.deleteTemplateById(id);
    }

    public void test_selectById() {
        Integer i = init();
        TemplateDomain tdo = templateService.queryTemplateById(i);
        assertEquals("selectById error", tdo.getCode(), "1");
        delete(i);
    }

    public void test_selectByCode() {
        Integer i = init();
        TemplateDomain tdo = templateService.queryTemplateByCode("1");
        assertEquals("selectByCode error", tdo.getCode(), "1");
        delete(i);
    }

    public void test_update() {
        Integer i = init();
        TemplateDomain tdo = templateService.queryTemplateById(i);
        tdo.setCode("2");
        templateService.updateTemplate(tdo);
        tdo = templateService.queryTemplateById(i);
        assertEquals("update error", "2", tdo.getCode());
        delete(i);
    }

    public void test_deleteByCode() {
        Integer i = init();
        templateService.deleteTemplateByCode("1");
        TemplateDomain tdo = templateService.queryTemplateById(i);
        assertTrue(tdo == null);
    }
}
