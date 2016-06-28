/**
 * 
 */
package com.ast.ast1949.web.controller.zz91.admin;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.dataindex.ProductsIndex;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.service.dataindex.ProductsIndexService;
import com.ast.ast1949.web.controller.BaseController;

/**
 * @author root
 *
 */
@Controller
public class ProductsIndexController extends BaseController {

	@Resource
	private ProductsIndexService productsIndexService;
	
	@RequestMapping
	public ModelAndView productsIndex(HttpServletRequest request, Map<String, Object> out, 
			String categoryCode, Integer productId) throws IOException{
		
		productsIndexService.buildIndex(productId, categoryCode);
		ExtResult result = new ExtResult();
		result.setSuccess(true);
		
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out){
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView queryIndex(HttpServletRequest request, Map<String, Object> out, 
			String categoryCode, PageDto<ProductsIndex> page) throws IOException{
		
		page =productsIndexService.pageIndex(categoryCode, page);
		
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView queryOne(HttpServletRequest request, Map<String, Object> out,
			Integer id){
	
		return null;
	}
	
	@RequestMapping
	public ModelAndView doUpdate(HttpServletRequest request, Map<String, Object> out,
			ProductsIndex index) throws IOException{
		
		Integer i=productsIndexService.updateIndex(index);
		ExtResult result=new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView delete(HttpServletRequest request, Map<String, Object> out, 
			Integer id) throws IOException{
		Integer i=productsIndexService.removeIndex(id);
		ExtResult result=new ExtResult();
		if(i!=null && i.intValue()>0){
			result.setSuccess(true);
		}
		return printJson(result, out);
	}
	
	@RequestMapping
	public ModelAndView doUpdateOrderby(HttpServletRequest request, Map<String, Object> out,
			Integer id, Float orderby){
		
		return null;
	}
}
