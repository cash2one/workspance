/*
 * Copyright 2009 ASTO.
 * All right reserved.
 * Created on 2009-12-17 by Ryan.
 */
package com.ast.ast1949.util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import junit.framework.TestCase;

/**
 * @author Ryan
 *
 */
public class AlgorithmUtilsTest extends TestCase{
	public void testMd5(){
		try {
			assertEquals("16位md5测试","49ba59abbe56e057",AlgorithmUtils.MD5("123456", 16));
			//assertEquals("32位md5测试","49ba59abbe56e057",AlgorithmUtils.MD5("123456", 32));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
