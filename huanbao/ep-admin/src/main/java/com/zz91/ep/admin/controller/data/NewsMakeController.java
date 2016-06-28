package com.zz91.ep.admin.controller.data;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
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

import com.zz91.ep.admin.controller.BaseController;
import com.zz91.ep.admin.service.sys.DataGatherService;
import com.zz91.ep.dto.data.NewsMakeMap;

@Controller
public class NewsMakeController extends BaseController {
	
	@Resource
	private DataGatherService dataGatherService;

	@RequestMapping
	public ModelAndView index(HttpServletRequest request, Map<String, Object> out){
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView initImpt(HttpServletRequest request, Map<String, Object> out){
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView impt(HttpServletRequest request, Map<String, Object> out, 
			NewsMakeMap newsMap){
		
		long start=System.currentTimeMillis();
		Map<String, String> importlog=imptNews(request, newsMap);
		long end=System.currentTimeMillis();
		out.put("importLog", importlog);
		out.put("costTime", end-start);
		return null;
	}
	
	private Map<String, String> imptNews(HttpServletRequest request, NewsMakeMap newsMap){
		
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
//			short cf=0;
//			short cl=0;
			for(int i=f+1;i<=l;i++){
				row = sheet.getRow(i);
				if(row==null){
					continue;
				}
				
				String title=row.getCell(newsMap.getTitle()).getRichStringCellValue().getString();
				if(dataGatherService.createNews(newsMap, row)){
					errorInfo.put(row.getCell(0).getNumericCellValue()+" "+title, "success");
				}else{
					errorInfo.put(row.getCell(0).getNumericCellValue()+" "+title, "error");
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return errorInfo;
	}
}
