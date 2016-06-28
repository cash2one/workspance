/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-1-17
 */
package com.ast.ast1949.web.controller.zz91;

import net.sf.json.JSONObject;
import net.sf.json.test.JSONAssert;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.AbstractTransactionalSpringContextTests;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

import com.ast.ast1949.dto.ExtResult;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-1-17
 */
public class AdminControllerTest extends
		AbstractTransactionalSpringContextTests {

	public AdminController adminController;

	/**
	 * Test method for
	 * {@link com.ast.ast1949.web.controller.zz91.AdminController#login(java.util.Map)}
	 * .
	 * @throws Exception 
	 */
	@Test
	public void testchangePassword() throws Exception {
//		AdminController adminController = new AdminController();
//		
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
//		
		request.setMethod("GET");
//		request.setAttribute("pwd", "135246");
//		request.setAttribute("newpwd", "123456");
//		request.setAttribute("newpwd2", "123456");
		
//		ModelAndView modelAndView = adminController
//		ModelMap map = new ModelMap();
//		adminController.changePassword("135246", "123456", "123456", request, map);
		
		request.setRequestURI("/admin/login.htm");
		ModelAndView mav = new AnnotationMethodHandlerAdapter().handle(request, response, new AdminController());
		ExtResult result = new ExtResult();
		result.setSuccess(true);
		JSONAssert.assertEquals(JSONObject.fromObject(result).toString(), 
			mav.getModel().get("json"));
	}
}
