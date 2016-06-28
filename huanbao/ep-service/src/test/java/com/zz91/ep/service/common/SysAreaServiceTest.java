package com.zz91.ep.service.common;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = {"classpath:spring-common-config*.xml" })
public class SysAreaServiceTest extends AbstractJUnit4SpringContextTests {
	
	@Resource(name="sysAreaService")
	private SysAreaService sysAreaService;
	
	 @Test
     public void testSave() throws Exception {
          this.sysAreaService.queryAllSysAreas();
     }
}
