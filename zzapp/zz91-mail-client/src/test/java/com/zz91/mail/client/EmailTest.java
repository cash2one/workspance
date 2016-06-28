/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on Oct 13, 2010
 */
package com.zz91.mail.client;

import org.junit.Assert;
import org.junit.Test;


/**
 * @author mays(x03570227@gmail.com)
 *
 * Created on Oct 13, 2010
 */
public class EmailTest {

	@Test
	public void test_send(){
		int i= ZZmail.getInstance().send("d6agdpa@163.com", "mays@zz91.net", "135246", "email test", "<h1>email html</h1>hello email");
		Assert.assertEquals(ZZmail.SUCCESS, i);
	}
	
	@Test
	public void test_sendOnTime(){
		
	}
}
