package com.ast.ast1949.web.controller.zz91.analysis;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
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

import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.analysis.AnalysisPpcAdslogDto;
import com.ast.ast1949.dto.products.KeywordSearchDto;
import com.ast.ast1949.service.analysis.AnalysisPpcAdslogService;
import com.ast.ast1949.util.StringUtils;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.datetime.DateUtil;

@Controller
public class AdsController extends BaseController{
	@Resource
	private AnalysisPpcAdslogService analysisPpcAdslogService;
	@SuppressWarnings("unchecked")
	@RequestMapping
	public ModelAndView index(Map<String, Object>out,HttpServletRequest request,PageDto<AnalysisPpcAdslogDto> page,String adposition,String sort,String dir,
			String from,String to,String tel) throws IOException, ParseException{
		//获取广告位名称和id的映射和父子节点的映射
		Map<String,Object> map=analysisPpcAdslogService.getAdposition();
		//父子节点的映射
		Map<String,List<String>> mapP=(Map<String,List<String>>)map.get("mapParent");
		//广告位名称和id的映射
		Map<String,String> mapM=(Map<String,String>)map.get("mapNameAndId");
		out.put("map", mapP);
		out.put("mapM", mapM);
		List<String> key=new ArrayList<String>();
		if("400123456-5555".equals(tel)){
			tel="";
		}
		//判断有无选中广告位，没有，就全选来电宝通用广告位，有就将选中的选中的分离成一个队列
		if(adposition==null){
			if(StringUtils.isNotEmpty(tel)){
				for(String num:mapM.keySet()){
					if(StringUtils.isNumber(num)){
						key.add(num);
						if(StringUtils.isEmpty(adposition)){
							adposition=num+",";
						}else{
							adposition=adposition+num+',';
						}
					}
				}
			}else{
				key.addAll(mapP.get("634"));
				key.add("634");
				adposition="634"+',';
				for(String nu:mapP.get("634")){
					adposition=adposition+nu+',';
				}
			}
			adposition=adposition.substring(0, adposition.length()-1);
		}else if(adposition!=null){
			String[] string=adposition.split(",");
			for(String str:string){
				key.add(str);
			}
		}
		//判断有没有父子节点都被选的，有，就将父节点去掉，但是广告位中要保留
		List<String> keyZ=new ArrayList<String>();
		for(String str:key){
			//不是父节点
			if(!mapP.keySet().contains(str)){
				keyZ.add(str);
			}
		}
		if(StringUtils.isEmpty(to) || StringUtils.isEmpty(from)){
			to=DateUtil.toString(new Date(), "yyyy-M-d 23:59:59");
			from=DateUtil.toString(DateUtil.getDateAfterDays(new Date(), -6), "yyyy-M-d");
		}else{
			to=DateUtil.toString(DateUtil.getDate(to, "yyyy-M-d"), "yyyy-M-d 23:59:59");
		}
		page=analysisPpcAdslogService.pageAdslogDto(page, from, to,keyZ,sort,dir,tel);
		if(page!=null){
			out.put("page", page);
		}
		//总量
		AnalysisPpcAdslogDto adslogA=analysisPpcAdslogService.queryAllCountByTime(keyZ,tel,from,to);
		//平均每天的值
		AnalysisPpcAdslogDto adslogAve=analysisPpcAdslogService.getAveCount(adslogA,from,to);
		//平均每广告位的值
		AnalysisPpcAdslogDto adslogAveC=analysisPpcAdslogService.getAvePositionCount(adslogA,keyZ);
		//各坐标轴的点集
		if(page.getRecords()!=null && page.getRecords().size()!=1){
		List<String> listP=analysisPpcAdslogService.getBarPoint(page);
		out.put("listP", listP);
		}
		if(adposition!=null){
		   adposition=java.net.URLEncoder.encode(adposition,"utf-8");   
		}
		if(StringUtils.isEmpty(tel)){
			tel="400123456-5555";
		}
		out.put("adslogA", adslogA);
		out.put("adslogAve", adslogAve);
		out.put("adslogAveC", adslogAveC);
		out.put("from", from);
		out.put("to", to);
		out.put("adposition", adposition);
		out.put("tel", tel);
		out.put("key", key);
		out.put("sort", sort);
		out.put("dir", dir);
		return null;
	}
	@SuppressWarnings("unchecked")
	@RequestMapping
	public ModelAndView time(Map<String, Object>out,HttpServletRequest request,PageDto<AnalysisPpcAdslogDto> page,String adposition,String sort,String dir,
			String from,String to,String tel) throws IOException, ParseException{
		Map<String,Object> map=analysisPpcAdslogService.getAdposition();
		Map<String,List<String>> mapP=(Map<String,List<String>>)map.get("mapParent");
		Map<String,String> mapM=(Map<String,String>)map.get("mapNameAndId");
		out.put("map", mapP);
		out.put("mapM", mapM);
		if(StringUtils.isEmpty(to) || StringUtils.isEmpty(from)){
			to=DateUtil.toString(new Date(), "yyyy-M-d 23:59:59");
			from=DateUtil.toString(DateUtil.getDateAfterDays(new Date(), -6), "yyyy-M-d");
		}else{
			to=DateUtil.toString(DateUtil.getDate(to, "yyyy-M-d"), "yyyy-M-d 23:59:59");
		}
		List<String> key=new ArrayList<String>();
		if("400123456-5555".equals(tel)){
			tel="";
		}
		//判断有无选中广告位，没有，就全选来电宝通用广告位，有就将选中的选中的分离成一个队列
				if(adposition==null){
					if(StringUtils.isNotEmpty(tel)){
						for(String num:mapM.keySet()){
							if(StringUtils.isNumber(num)){
								key.add(num);
								if(StringUtils.isEmpty(adposition)){
									adposition=num+",";
								}else{
									adposition=adposition+num+',';
								}
							}
						}
					}else{
						key.addAll(mapP.get("634"));
						key.add("634");
						adposition="634"+',';
						for(String nu:mapP.get("634")){
							adposition=adposition+nu+',';
						}
					}
					adposition=adposition.substring(0, adposition.length()-1);
				}else if(adposition!=null){
					String[] string=adposition.split(",");
					for(String str:string){
						key.add(str);
					}
				}
				//判断有没有父子节点都被选的，有，就将父节点去掉，但是广告位中要保留
				List<String> keyZ=new ArrayList<String>();
				for(String str:key){
					//不是父节点
					if(!mapP.keySet().contains(str)){
						keyZ.add(str);
					}
				}
		page=analysisPpcAdslogService.pageAdslogTime(page, from, to, keyZ,sort,dir,tel);
		if(page.getRecords()!=null&& page.getRecords().size()!=1){
			List<String> listP=analysisPpcAdslogService.getPoint(page);
			out.put("listP", listP);
		}
		out.put("page", page);
		//总量
		AnalysisPpcAdslogDto adslogA=analysisPpcAdslogService.queryAllCountByTime(keyZ,tel,from,to);
		//平均每天的值
		AnalysisPpcAdslogDto adslogAve=analysisPpcAdslogService.getAveCount(adslogA,from,to);
		//平均每广告位的值
		AnalysisPpcAdslogDto adslogAveC=analysisPpcAdslogService.getAvePositionCount(adslogA,keyZ);

		if(StringUtils.isEmpty(tel)){
			tel="400123456-5555";
		}
		out.put("adslogA", adslogA);
		out.put("adslogAve", adslogAve);
		out.put("adslogAveC", adslogAveC);
		out.put("from", from);
		out.put("to", to);
		out.put("adposition", adposition);
		out.put("tel", tel);
		out.put("key", key);
		out.put("sort", sort);
		out.put("dir", dir);
		return null;
	}
	@RequestMapping
	public ModelAndView exportIndex(HttpServletRequest request,HttpServletResponse response,String id,String tel,String from ,String to ) throws IOException, RowsExceededException, WriteException{
		if("400123456-5555".equals(tel)){
			tel="";
		}
		response.setContentType("application/msexcel");
		OutputStream os = response.getOutputStream();
		WritableWorkbook wwb = Workbook.createWorkbook(os);//创建可写工作薄   
		WritableSheet ws = wwb.createSheet("sheet1", 0);//创建可写工作表
		if(StringUtils.isEmpty(id)){
			return new ModelAndView("redirect:"+"index.htm");
		}
		List<AnalysisPpcAdslogDto> list=analysisPpcAdslogService.findDataByAdATime(id, tel, from, to);
		if(list.size()==0){
			return new ModelAndView("redirect:"+"index.htm");
		}
		ws.addCell(new Label(0,0,"400号码"));
		ws.addCell(new Label(1,0,"展现量"));
		ws.addCell(new Label(2,0,"点击量"));
		ws.addCell(new Label(3,0,"点击率"));
		ws.addCell(new Label(4,0,"电话量"));
		ws.addCell(new Label(5,0,"电话率"));
		int i=1;
		for(AnalysisPpcAdslogDto obj:list){
			ws.addCell(new Label(0,i,obj.getName()));
			ws.addCell(new jxl.write.Number(1,i,obj.getShowcount()));
			ws.addCell(new jxl.write.Number(2,i,obj.getCheckcount()));
			ws.addCell(new Label(3,i,obj.getClickRate()));
			ws.addCell(new jxl.write.Number(4,i,obj.getTelCount()));
			ws.addCell(new Label(5,i,obj.getChangeRate()));
			i++;
		}
		//现在可以写了   
		wwb.write();
		//写完后关闭   
		wwb.close();
		//输出流也关闭吧   
		os.close(); 
		return null;
	}
	@RequestMapping
	public ModelAndView exportTime(HttpServletRequest request,HttpServletResponse response,String id,String tel,String from ,String to ) throws IOException, RowsExceededException, WriteException{
		if("400123456-5555".equals(tel)){
			tel="";
		}
		if(StringUtils.isEmpty(id)){
			return new ModelAndView("redirect:"+"time.htm");
		}
		response.setContentType("application/msexcel");
		OutputStream os = response.getOutputStream();
		WritableWorkbook wwb = Workbook.createWorkbook(os);//创建可写工作薄   
		WritableSheet ws = wwb.createSheet("sheet1", 0);//创建可写工作表
		List<AnalysisPpcAdslogDto> list=analysisPpcAdslogService.findTimeDateByAdATime(id, tel, from, to);
		if(list.size()==0){
			return new ModelAndView("redirect:"+"time.htm");
		}
		ws.addCell(new Label(0,0,"时间"));
		ws.addCell(new Label(1,0,"展现量"));
		ws.addCell(new Label(2,0,"点击量"));
		ws.addCell(new Label(3,0,"点击率"));
		ws.addCell(new Label(4,0,"电话量"));
		ws.addCell(new Label(5,0,"电话率"));
		int i=1;
		for(AnalysisPpcAdslogDto obj:list){
			ws.addCell(new Label(0,i,obj.getTime()));
			ws.addCell(new jxl.write.Number(1,i,obj.getShowcount()));
			ws.addCell(new jxl.write.Number(2,i,obj.getCheckcount()));
			ws.addCell(new Label(3,i,obj.getClickRate()));
			ws.addCell(new jxl.write.Number(4,i,obj.getTelCount()));
			ws.addCell(new Label(5,i,obj.getChangeRate()));
			i++;
		}
		//现在可以写了   
		wwb.write();
		//写完后关闭   
		wwb.close();
		//输出流也关闭吧   
		os.close(); 
		if(StringUtils.isEmpty(id) || list.size()==0){
			return new ModelAndView("redirect:"+"time.htm");
		}
		return null;
	}
}
