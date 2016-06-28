/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-7-12 上午09:26:15
 */
package com.zz91.ep.admin.controller.data;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.admin.service.sys.DataGatherService;
import com.zz91.ep.admin.service.trade.IbdCategoryService;
import com.zz91.ep.domain.trade.IbdCategory;
import com.zz91.ep.dto.data.IbdCompanyMakeMap;

/**
 * 买家库信息导入Controller
 */
@Controller
public class IbdCompanyController {
	
	@Resource
	private DataGatherService dataGatherService;
	
	@Resource
	private  IbdCategoryService ibdCategoryService;
	
	
	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out){
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView initImpt(HttpServletRequest request, Map<String, Object> out){
		List<IbdCategory> list=ibdCategoryService.queryCategoryByParentCode("1000");
		out.put("list", list);
		return null;
	}
	
	@RequestMapping
	public ModelAndView impt(HttpServletRequest request, Map<String, Object> out,IbdCompanyMakeMap companyMap){
		
		long start=System.currentTimeMillis();
		Map<String, String> importlog=imptIbdCompany(request,companyMap);
		long end=System.currentTimeMillis();
		out.put("importLog", importlog);
		out.put("costTime", end-start);
		return null;
	}

	private Map<String, String> imptIbdCompany(HttpServletRequest request,IbdCompanyMakeMap companyMap){
		MultipartRequest multipartRequest = (MultipartRequest) request;
		
		MultipartFile file = multipartRequest.getFile("excel");
		
		Map<String, String> errorInfo=new LinkedHashMap<String, String>();
		
		try {
			InputStream in=new BufferedInputStream(file.getInputStream());
			
			HSSFWorkbook wb=new HSSFWorkbook(in);
			HSSFSheet sheet=wb.getSheetAt(0);
			
			int f=sheet.getFirstRowNum();
			int l=sheet.getLastRowNum();
			in.close();
			HSSFRow row;
			for(int i=f+1;i<=l;i++){
				row = sheet.getRow(i);
				if(row==null){
					continue;
				}
				
				String compName=row.getCell(companyMap.getName()).getRichStringCellValue().getString();
				if(dataGatherService.createIbdCompany(companyMap, row)){
					errorInfo.put(compName, "success");
				}else{
					errorInfo.put(compName, "error");
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return errorInfo;
	}
}
