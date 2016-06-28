/**
 * 
 */
package com.ast.ast1949.trade.controller;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.service.products.ProductsPicService;

/**
 * @author mays (mays@zz91.net)
 *
 * Created by 2011-12-22
 */

@Controller
public class ProductPicController extends BaseController {

	@Resource
	private ProductsPicService productsPicService;
	
	@RequestMapping
	public ModelAndView deleteOnlyPic(HttpServletRequest request, Map<String, Object> out,Integer id) throws IOException{
		Integer[] ids=new Integer[1];//picids.split(",");
		ids[0]=id;
		productsPicService.batchDeleteProductPicbyId(ids);
		ExtResult result=new ExtResult();
		result.setSuccess(true);
		return printJson(result, out);
	}
}
