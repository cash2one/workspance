package com.zz91.mail.test;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import com.zz91.mail.domain.MailInfoDomain;
import com.zz91.mail.domain.TemplateDomain;
import com.zz91.mail.service.MailInfoService;
import com.zz91.mail.service.MailSendService;
import com.zz91.mail.service.TemplateService;
import com.zz91.util.datetime.DateUtil;

public class MailSenderServiceTest extends BaseServiceTestCase {

    @Resource
    private TemplateService templateService;
    @Resource
    private MailSendService mailSendService;
    @Resource
    private MailInfoService mailInfoService;

    public void testGetTemplate() {
        fail();
        TemplateDomain templateDomain = templateService.queryTemplateByCode("4");
        System.out.println("Template " + templateDomain.getName() + " : " + templateDomain.gettContent());
    }


    public void test_SendMailByString() {
        JSONObject map = new JSONObject();
        map.put("sender", "kxzjzjzzkxsohu.com");
        map.put("receiver", "kxzjzjzzkx@gmail.com");
        map.put("emailTitle", "I'm title");
        map.put("templateId", "1");
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("name", "哟西");
        map.put("emailParameter", para);
        MailInfoDomain sto = (MailInfoDomain) JSONObject.toBean(map, MailInfoDomain.class);
        int i = mailSendService.sendEmail(sto);
        assertEquals("SendMailByString error", 0, i);
    }
    
    public void test_selectMailForThread(){
        Map<String,Object>map = new HashMap<String,Object>();
        try {
            map.put("gmtPost", DateUtil.getDate(new Date(), "yyyy-MM-dd"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<MailInfoDomain> list =mailInfoService.selectMailQueue(map);
        assertTrue(list.size()>0);
    }
}
