/*
 * Copyright 2009 ASTO.
 * All right reserved.
 * Created on 2009-12-17 by Ryan.
 */
package com.ast.ast1949.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.ast.ast1949.domain.auth.AuthUser;

import junit.framework.TestCase;

/**
 * @author Ryan
 *
 */
public class CategoryUtilsTest extends TestCase {

	/**
	 * Test method for {@link com.ast.ast1949.util.CategoryUtils#GetCategoryCode(java.lang.String)}.
	 */
	public void testGetCategoryCode() {
		
	}
	
	public void test_date() throws ParseException{
		List<AuthUser> list=new ArrayList<AuthUser>();
		AuthUser u = new AuthUser();
		u.setUsername("test");
		list.add(u);
		
		AuthUser u2 = new AuthUser();
		u2.setUsername("test");
		
		if(list.contains(u)){
			System.out.print("drgfdt");
		}
//		int day=DateUtil.getIntervalDays(DateUtil.getDate("2010-9-10","yyyy-MM-dd"), DateUtil.getDate("2010-8-10","yyyy-MM-dd"));
//		assertTrue("DAY="+day, true);
	}
}
