/**
 * 
 */
package com.ast.feiliao91.admin.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.feiliao91.admin.util.WebConst;
import com.ast.feiliao91.dto.ExtResult;
import com.ast.feiliao91.dto.ExtTreeDto;
import com.ast.feiliao91.service.commom.CategoryService;
import com.zz91.util.file.MvcUpload;

/**
 * @author root
 *
 */
@Controller
public class CommonController extends BaseController {
	
	@Resource
	private CategoryService categoryService;
//	private static final String DEFAULTS_CLASS = "excelDefaults";  
//	private static StringBuffer lsb = new StringBuffer();
//	private static int firstColumn;  
//	private static int endColumn; 
//	private static Workbook wb;  
	
	@RequestMapping
	public ModelAndView categoryTreeNode(HttpServletRequest request, Map<String, Object> out, 
			String parentCode) throws IOException{

		List<ExtTreeDto> category=categoryService.child(parentCode);
		
		return printJson(category, out);
	}
	
	@RequestMapping
	public ModelAndView doUpload(HttpServletRequest request, Map<String, Object> out) 
			throws IOException{
		
		String path=MvcUpload.getModalPath(WebConst.UPLOAD_MODEL_DEFAULT);
		String filename=UUID.randomUUID().toString();
		ExtResult result=new ExtResult();
		try {
			String finalname=MvcUpload.localUpload(request, path, filename);
			result.setSuccess(true);
			result.setData(path.replace(MvcUpload.getDestRoot(), "")+"/"+finalname);
		} catch (Exception e) {
			result.setData(MvcUpload.getErrorMessage(e.getMessage()));
		}
		return printJson(result, out);
	}

}
