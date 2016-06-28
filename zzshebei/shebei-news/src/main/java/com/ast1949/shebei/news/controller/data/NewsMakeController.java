package com.ast1949.shebei.news.controller.data;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
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

import com.ast1949.shebei.domain.NewsCategory;
import com.ast1949.shebei.dto.data.NewsMakeMap;
import com.ast1949.shebei.service.DataGatherService;
import com.ast1949.shebei.service.NewsCategoryService;

@Controller
public class NewsMakeController {
	
	@Resource
	private DataGatherService dataGatherService;
	@Resource
	private NewsCategoryService newsCategoryService;
	
	@RequestMapping
	public ModelAndView newCategory(Map<String, Object> out,HttpServletRequest request){
		return null;
	}

	@RequestMapping
	public ModelAndView  initImpt(Map<String, Object> out,HttpServletRequest request){
		List<NewsCategory> list=newsCategoryService.queryAllNewsCategory();
		out.put("list", list);
		return null;
	}
	
	@RequestMapping
	public ModelAndView impt(HttpServletRequest request, Map<String, Object> out, NewsMakeMap newsMap){
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
				
//		File file = new File("/usr/data/resources/shebei-news/"+fileName); //读取指定路径文件
		
		Map<String, String> errorInfo=new LinkedHashMap<String, String>();
		
		try {
//				FileInputStream in = new FileInputStream(file);//读取指定路径文件
				InputStream in=new BufferedInputStream(file.getInputStream());
				HSSFWorkbook wb=new HSSFWorkbook(in);
				HSSFSheet sheet=wb.getSheetAt(0);
				
				int f=sheet.getFirstRowNum();
				int l=sheet.getLastRowNum();
				in.close();
				HSSFRow row;
				long seed=System.currentTimeMillis();
				Date dt=dataGatherService.queryNewsMaxGmtShow();
				if (dt!=null && dt.getTime()>seed){
					seed = dt.getTime();   // dt.getTime/1000得到秒数，Date类型的getTime()返回毫秒数
				}
				
				for(int i=f+1;i<=l;i++){
					row = sheet.getRow(i);
					if(row==null){
						continue;
					}
					
					String title=null;
					if (row.getCell(newsMap.getTitle())!=null){
						try {
							Double d=row.getCell(newsMap.getTitle()).getNumericCellValue();
							NumberFormat format = new DecimalFormat("#");
							title=format.format(d);
						} catch (Exception e) {
							title=row.getCell(newsMap.getTitle()).getRichStringCellValue().getString();
						}
					}
					
					long nseed=dataGatherService.createNews(newsMap, row, seed);
					if(nseed>0){
						seed=nseed;
						errorInfo.put(title, "success");
					}else{
						errorInfo.put(title, "error");
					}
				}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return errorInfo;
	}
}
