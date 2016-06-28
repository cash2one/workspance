package com.ast.ast1949.web.controller.zz91.analysis;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.analysis.AnalysisCompPrice;
import com.ast.ast1949.domain.analysis.AnalysisInquiry;
import com.ast.ast1949.domain.analysis.AnalysisLog;
import com.ast.ast1949.domain.analysis.AnalysisProduct;
import com.ast.ast1949.domain.analysis.AnalysisRegister;
import com.ast.ast1949.domain.analysis.AnalysisTradeKeywords;
import com.ast.ast1949.domain.analysis.AnalysisXiaZaiKeywords;
import com.ast.ast1949.domain.price.PriceDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.analysis.AnalysisSpotDto;
import com.ast.ast1949.service.analysis.AnalysisService;
import com.ast.ast1949.service.analysis.AnalysisXiaZaiKeywordsService;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;

@Controller
public class AnalysisController extends BaseController {
	@Resource
	private AnalysisService analysisService;
	@Resource
	private AnalysisXiaZaiKeywordsService analysisXiaZaiKeywordsService;
	
	@RequestMapping
	public ModelAndView viewRegister(HttpServletRequest request, Map<String, Object> out) throws IOException{
		return null;
	}
	
	@RequestMapping
	public ModelAndView viewInquiry(HttpServletRequest request, Map<String, Object> out) throws IOException{
		return null;
	}
	
	@RequestMapping
	public ModelAndView viewProduct(HttpServletRequest request, Map<String, Object> out) throws IOException{
		return null;
	}
	
	@RequestMapping
	public ModelAndView viewKw(HttpServletRequest request, Map<String, Object> out) throws IOException{
		return null;
	}
	@RequestMapping
    public ModelAndView viewXiaZaiKw(HttpServletRequest request, Map<String, Object> out) throws IOException{
        return null;
    }
	@RequestMapping
	public ModelAndView viewSpot(HttpServletRequest request, Map<String, Object> out) throws IOException{
		return null;
	}
	
	@RequestMapping
	public ModelAndView viewCompPrice(HttpServletRequest request, Map<String, Object> out) throws IOException{
		return null;
	}
	
	@RequestMapping
	public ModelAndView queryRegister(HttpServletRequest request,Map<String,Object>out,AnalysisRegister register,String regfromCode,Date from,Date to) throws IOException{
		List<AnalysisRegister> list = analysisService.queryRegister(regfromCode, from, to);
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView pageKw(HttpServletRequest request,Map<String,Object>out, 
			PageDto<AnalysisTradeKeywords> page, Date gmtTarget) throws IOException{
		if(gmtTarget==null){
			gmtTarget=new Date(DateUtil.getTheDayZero(new Date(), -1)*1000l);
		}
		page=analysisService.pageKeywords(gmtTarget, page);
		if(page.getRecords().size()>0){
			page.getRecords().add(analysisService.summaryKeywords(gmtTarget));
		}
		return printJson(page, out);
	}
	@RequestMapping
    public ModelAndView pageXiaZaiKw(HttpServletRequest request,Map<String,Object>out, 
            PageDto<AnalysisXiaZaiKeywords> page, Date gmtTarget) throws IOException{
        if(gmtTarget==null){
            gmtTarget=new Date(DateUtil.getTheDayZero(new Date(), -1)*1000l);
        }
        page=analysisXiaZaiKeywordsService.pageKeywords(gmtTarget, page);
        if(page.getRecords().size()>0){
            page.getRecords().add(analysisXiaZaiKeywordsService.summaryKeywords(gmtTarget));
        }
        return printJson(page, out);
    }
	
	@RequestMapping
	public ModelAndView queryKw(HttpServletRequest request,Map<String,Object>out, 
			String kw, Date from, Date to) throws IOException{
		List<AnalysisTradeKeywords> list=analysisService.queryKeywords(kw, from, to);
		return printJson(list, out);
	}
	@RequestMapping
    public ModelAndView queryXiaZaiKw(HttpServletRequest request,Map<String,Object>out, 
            String kw, Date from, Date to) throws IOException{
        List<AnalysisXiaZaiKeywords> list=analysisXiaZaiKeywordsService.queryKeywords(kw, from, to);
        return printJson(list, out);
    }
	@RequestMapping
    public ModelAndView exportData(HttpServletRequest request,HttpServletResponse response,String gmtTarget) throws IOException, RowsExceededException, WriteException, ParseException{
        response.setContentType("application/msexcel");
        OutputStream os = response.getOutputStream();
        WritableWorkbook wwb = Workbook.createWorkbook(os);//创建可写工作薄   
        WritableSheet ws = wwb.createSheet("sheet1", 0);//创建可写工作表
        
        // 检索所有list
        SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
        Date formatDate=sim.parse(gmtTarget.toString());
        List<AnalysisXiaZaiKeywords> list = analysisXiaZaiKeywordsService.queryListByFromTo(formatDate);
        AnalysisXiaZaiKeywords analysisXiaZaiKeywords = analysisXiaZaiKeywordsService.summaryKeywords(formatDate);
        int sum = analysisXiaZaiKeywords.getNum();
        
        ws.addCell(new Label(0,0,"关键字"));
        ws.addCell(new Label(1,0,"数量"));
        ws.addCell(new Label(2,0,"百分比"));
        ws.addCell(new Label(3,0,"时间"));
        int i=1;
        for(AnalysisXiaZaiKeywords obj:list){
            ws.addCell(new Label(0,i,obj.getKw()));
            ws.addCell(new Label(1,i,obj.getNum().toString()));
            double k = (double)obj.getNum()/sum*100;
            java.math.BigDecimal   big   =   new   java.math.BigDecimal(k);  
            String  l = big.setScale(2,java.math.BigDecimal.ROUND_HALF_UP).doubleValue() +"%";
            ws.addCell(new Label(2,i,l));
            String date = DateUtil.toString(obj.getGmtTarget(), "yyyy-MM-dd");
            ws.addCell(new Label(3,i,date));
            i++;
        }
         
        wwb.write();
        //写完后关闭   
        wwb.close();
        //输出流也关闭吧   
        os.close(); 
        return null;
    }
	@RequestMapping
	public ModelAndView pageInquiry(HttpServletRequest request,Map<String,Object> out,
			Date gmtTarget, String inquiryType, PageDto<AnalysisInquiry> page) throws IOException{
		if(gmtTarget==null){
			gmtTarget=new Date(DateUtil.getTheDayZero(new Date(), -1)*1000l);
		}
		page=analysisService.pageInquiry(inquiryType, gmtTarget, page);
		if(page.getRecords().size()>0){
			page.getRecords().add(analysisService.summaryInquiry(inquiryType, gmtTarget));
		}
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView queryInquiry(HttpServletRequest request,Map<String,Object> out,
			String inquiryType, Integer inquiryTarget, Date from, Date to) throws IOException{
		List<AnalysisInquiry> list=analysisService.queryInquiry(inquiryType, inquiryTarget, from, to);
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView queryProduct(HttpServletRequest request,Map<String,Object>out,AnalysisProduct product,String typeCode,String categoryCode,Date from,Date to) throws IOException{
		List<AnalysisProduct> list = analysisService.queryProduct(typeCode, categoryCode, from, to);
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView queryCompPrice(HttpServletRequest request,Map<String,Object>out,AnalysisCompPrice compprice,String categoryCode,Date from,Date to) throws IOException{
		List<AnalysisCompPrice> list = analysisService.queryCompPrice(categoryCode, from, to);
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView viewQQ(HttpServletRequest request, Map<String, Object> out) throws IOException{
		return null;
	}
	
	@RequestMapping
	public ModelAndView queryQQ(HttpServletRequest request,Map<String,Object>out,Date from,Date to,String operation,String operator) throws IOException{
		if(StringUtils.isEmpty(operator)){
			operator = "qq_login";
		}
		List<AnalysisLog> list = analysisService.queryAnalysisLog(operator, operation, from, to);
		return printJson(list, out);
	}
	
	@RequestMapping
	public ModelAndView pageSpot(HttpServletRequest request,Map<String,Object>out, 
			PageDto<AnalysisSpotDto> page,Date from,Date to,String operation) throws IOException{
		page=analysisService.queryAnalysisLogForSpot(page, null, "spot_hit", from, to);
		if(page.getRecords().size()>0){
			page.getRecords().add(analysisService.summarySpot(from ,to,"spot_hit"));
		}
		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView querySpotChart(HttpServletRequest request,Map<String,Object>out, 
			PageDto<AnalysisSpotDto> page,Date from,Date to,String operator) throws IOException{
		List<AnalysisLog> list = analysisService.querySpot(operator, from, to);
		return printJson(list, out);
	}
}
