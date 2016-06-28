package com.zz91.crm.service;

import javax.annotation.Resource;
import org.junit.Test;
import com.zz91.crm.domain.SysArea;
import com.zz91.crm.service.SysAreaService;

public class SysAreaServiceTestOfBasic extends BaseServiceTestCase {
    
    @Resource
    private SysAreaService sysAreaService;
    
   /* @Test
    public void testQuerySysAreaByCode()  {
    	System.out.println("Starting...");
    	List<SysArea> lists = sysAreaService.querySysAreaByCode("1000", SysAreaService.TYPE_SELF);
    	for (SysArea sysArea : lists) {
			System.out.println("code: " + sysArea.getCode() + "| name: " + sysArea.getName());
		}
    }*/ 
    
    @Test
    public void testInsertSysArea()  {
    	System.out.println("Starting...");
    	Integer i = 0;
    	try {
    		i = sysAreaService.insertSysArea(new SysArea(null, "1008", "湖州", (short)0, "浙江省湖州", null, null));
		} catch (Exception e) {
			System.out.println("发生异常拉");
		}
    	System.out.println("id:" + i);
    }
}