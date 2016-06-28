/**
 * @author shiqp
 * @date 2014-09-11
 */
package com.ast.ast1949.service.analysis.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.analysis.AnalysisMyrcVisitor;
import com.ast.ast1949.domain.analysis.AnalysisMyrcVisitors;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.analysis.AnalysisMyrcVisitorDao;
import com.ast.ast1949.persist.company.InquiryDao;
import com.ast.ast1949.persist.phone.PhoneLogDao;
import com.ast.ast1949.persist.products.ProductsDAO;
import com.ast.ast1949.service.analysis.AnalysisMyrcVisitorService;
import com.ast.ast1949.service.facade.CategoryProductsFacade;
import com.zz91.util.datetime.DateUtil;

@Component("analysisMyrcVisitorService")
public class AnalysisMyrcVisitorServiceImpl implements AnalysisMyrcVisitorService {
	@Resource
	private AnalysisMyrcVisitorDao analysisMyrcVisitorDao;
	@Resource
	private InquiryDao inquiryDao;
	@Resource
	private PhoneLogDao phoneLogDao;
	@Resource
	private ProductsDAO productsDAO;
	

	@Override
	public AnalysisMyrcVisitors getVisitorsData(Integer companyId, String from,
			String to) {
		AnalysisMyrcVisitors visitor = new AnalysisMyrcVisitors();
		Integer view = 0;
		Integer visit = 0;
		Integer inquiry = 0;
		Integer yj = 0;
		Integer wj = 0;
		Integer telCount = 0;
		// 昨天的数据
		view = analysisMyrcVisitorDao.countViewByCidATime(companyId, from, to);
		visitor.setView(view);
		visit = analysisMyrcVisitorDao.countVisitByCidATime(companyId, from, to);
		visitor.setVisit(visit);
		inquiry = inquiryDao.countInquiryByCidAtime(companyId, from, to);
		visitor.setInquiry(inquiry);
		yj = phoneLogDao.countYJCompanyBytime(companyId, from, to);
		wj = phoneLogDao.countWJCompanyBytime(companyId, from, to);
		telCount = yj + wj;
		visitor.setTelCount(telCount);
		// 前天的数据
		String start = DateUtil.toString(
				DateUtil.getDateAfterDays(new Date(), -2), "yyyy-MM-dd");
		String end = DateUtil.toString(
				DateUtil.getDateAfterDays(new Date(), -2),"yyyy-MM-dd 23:59:59");
		view = analysisMyrcVisitorDao.countViewByCidATime(companyId, start, end);
		visitor.setViewFlag(getData(visitor.getView(),view).get("flag"));
		visitor.setViewData(getData(visitor.getView(),view).get("data"));
		visit = analysisMyrcVisitorDao.countVisitByCidATime(companyId, start, end);
		visitor.setVisitFlag(getData(visitor.getVisit(),visit).get("flag"));
		visitor.setVisitData(getData(visitor.getVisit(),visit).get("data"));
		inquiry = inquiryDao.countInquiryByCidAtime(companyId, start, end);
		visitor.setInquiryFlag(getData(visitor.getInquiry(),inquiry).get("flag"));
		visitor.setInquiryData(getData(visitor.getInquiry(),inquiry).get("data"));
		yj = phoneLogDao.countYJCompanyBytime(companyId, start, end);
		wj = phoneLogDao.countWJCompanyBytime(companyId, start, end);
		telCount = yj + wj;
		visitor.setTelCFlag(getData(visitor.getTelCount(),telCount).get("flag"));
		visitor.setTelCData(getData(visitor.getTelCount(),telCount).get("data"));
		return visitor;
	}

	// 处理小数点类型
	public String getDoubleToString(double number, int size) {
			BigDecimal db = new BigDecimal(number);
			db = db.setScale(size, BigDecimal.ROUND_HALF_UP);
			return String.valueOf(db);
	}

	// 标记升降和升降量的计算
	public Map<String,String> getData(Integer dataF, Integer dataT) {
		Map<String,String> map=new HashMap<String,String>();
		Integer data = 0;
		if (dataT != 0 || dataF!=0) {
			if(dataF - dataT>0){
				map.put("flag", "1");
			   data = (dataF - dataT); 
			}else{
				map.put("flag", "0");
			   data=dataT - dataF;
			}
		}else{
			data=0;
		}
		if(data==0){
			map.put("data","--");
		}else{
			map.put("data", String.valueOf(data));
		}
		
		return map;
	}

	@Override
	public PageDto<AnalysisMyrcVisitor> getVisitorList(Integer companyId, String from, String to,Integer tag, PageDto<AnalysisMyrcVisitor> page) {
		List<AnalysisMyrcVisitor> list=new ArrayList<AnalysisMyrcVisitor>();
		if(tag==0){
			list=analysisMyrcVisitorDao.queryViewByCidATime(companyId, from, to, page);
			page.setTotalRecords(analysisMyrcVisitorDao.countViewByCidATimeLen(companyId, from, to));
		}else{
			list=analysisMyrcVisitorDao.queryVisitByCidATime(companyId, from, to, page);
			page.setTotalRecords(analysisMyrcVisitorDao.countVisitByCidATimeLen(companyId, from, to));
		}
		for(AnalysisMyrcVisitor li:list){
			//组装交易中心最终页的title
			if(li.getUrl().contains("http://trade.zz91.com/productDetails")){
				ProductsDO product=productsDAO.queryProductsById(Integer.valueOf(li.getUrl().replace("http://trade.zz91.com/productDetails", "").replace(".htm", "")));
				String bigCategory = CategoryProductsFacade.getInstance().getValue(product.getCategoryProductsMainCode().substring(0, 4));
				String fCategory = CategoryProductsFacade.getInstance().getValue(product.getCategoryProductsMainCode());
				String title=product.getTitle()+"_"+fCategory+"价格_"+fCategory+"相关求购,供应信息_"+fCategory+"商家联系方式_"+bigCategory+"交易中心-zz91再生网";
				li.setTitle(title);
				//更新title
				analysisMyrcVisitorDao.updateTitle(title, li.getUrl());
				//有无询盘，有无来电
				if(li.getCompanyId()==0){
					li.setInquiryFlag(0);
					li.setTelFlag(0);
				}else{
					Integer i=inquiryDao.countInquiryByCidAtime(companyId, DateUtil.toString(li.getGmtTarget(), "yyyy-MM-dd"), DateUtil.toString(li.getGmtTarget(), "yyyy-MM-dd 23:59:59"));
					if(i>0){
						li.setInquiryFlag(1);
					}else{
						li.setInquiryFlag(0);
					}
					Integer j=phoneLogDao.countWJCompanyBytime(companyId, DateUtil.toString(li.getGmtTarget(), "yyyy-MM-dd"), DateUtil.toString(li.getGmtTarget(), "yyyy-MM-dd 23:59:59"));
					Integer	m=phoneLogDao.countYJCompanyBytime(companyId, DateUtil.toString(li.getGmtTarget(), "yyyy-MM-dd"), DateUtil.toString(li.getGmtTarget(), "yyyy-MM-dd 23:59:59"));
					if(j+m>0){
						li.setTelFlag(1);
					}else{
						li.setTelFlag(0);
					}
				}
			}
		}
		page.setRecords(list);
		return page;
	}

	@Override
	public Map<String, Object> getPoint(Integer companyId, String from, String to, String menberShip) {
		Map<String, Object> map=new HashMap<String, Object>();
		//判断from和to之间的时间间隔
		Integer days=0;
		try {
			days = DateUtil.getIntervalDays(DateUtil.getDate(to, "yyyy-MM-dd"), DateUtil.getDate(from, "yyyy-MM-dd"));
		} catch (ParseException e) {
		}
		//浏览量//访客数//询盘数//电话数
		Integer view=0,visit=0,inquiry=0,telcount=0;
		//浏览量坐标	//访客数坐标//询盘数坐标//电话数坐标
		String viewPoint="",visitPoint="",inquiryPoint="",telPoint="";
		//横坐标的刻度
		String string="";
		//浏览量横坐标的提示//浏览量横坐标的提示
		String titleView="",titleVisit="",titleInquiry="",titleTel="",titleBV="";;
		String time=from;
		String start=null;
		String end=null;
		Integer big=0;
		if(days>0){
			for(int i=0;i<days+1;i++){
				try {
					if(i!=0){
						from=DateUtil.toString(DateUtil.getDateAfterDays(DateUtil.getDate(start,"yyyy-MM-dd"), 1),"yyyy-MM-dd");
					}
					start=DateUtil.toString(DateUtil.getDate(from, "yyyy-MM-dd"), "yyyy-MM-dd");
					end=DateUtil.toString(DateUtil.getDate(from, "yyyy-MM-dd"), "yyyy-MM-dd 23:59:59");
				} catch (ParseException e) {
				}
				view=analysisMyrcVisitorDao.countViewByCidATime(companyId, start, end);
				if(big<view){
					big=view;
				}
				visit=analysisMyrcVisitorDao.countVisitByCidATime(companyId, start, end);
				if(big<visit){
					big=visit;
				}
				inquiry=inquiryDao.countInquiryByCidAtime(companyId, start, end);
				if(big<inquiry){
					big=inquiry;
				}
				//点集
				viewPoint=viewPoint+"["+i+","+view+"]";
				visitPoint=visitPoint+"["+i+","+visit+"]";
				inquiryPoint=inquiryPoint+"["+i+","+inquiry+"]";
				if("10051003".equals(menberShip)){
					Integer wj=phoneLogDao.countWJCompanyBytime(companyId, start, end);
					Integer yj=phoneLogDao.countYJCompanyBytime(companyId, start, end);
					telcount=wj+yj;
					if(big<telcount){
						big=telcount;
					}
				}
				telPoint=telPoint+"["+i+","+telcount+"]";
				if(Integer.valueOf(view)==Integer.valueOf(visit)){
					titleBV=titleBV+"["+i+","+"\""+start+","+"浏览量:"+view+","+"访客数:"+visit+"\""+","+"\""+"green"+"\""+"]";
				}else{
					titleBV=titleBV+"["+i+","+"\""+start+","+"浏览量:"+view+"\""+","+"\""+"green"+"\""+"]";

				}
				titleView=titleView+"["+i+","+"\""+start+","+"浏览量:"+view+"\""+","+"\""+"green"+"\""+"]";
				titleVisit=titleVisit+"["+i+","+"\""+start+","+"访客数:"+visit+"\""+","+"\""+"pink"+"\""+"]";
				titleInquiry=titleInquiry+"["+i+","+"\""+start+","+"询盘数:"+inquiry+"\""+","+"\""+"inquiry"+"\""+"]";
				titleTel=titleTel+"["+i+","+"\""+start+","+"电话数："+telcount+"\""+","+"\""+"tel"+"\""+"]";
				//判断是否为最后一个点
				if(days!=i){
					viewPoint=viewPoint+",";
					visitPoint=visitPoint+",";
					inquiryPoint=inquiryPoint+",";
					telPoint=telPoint+",";
					titleView=titleView+",";
					titleVisit=titleVisit+",";
					titleInquiry=titleInquiry+",";
					titleBV=titleBV+",";
					titleTel=titleTel+",";
				}
			}
			//横坐标刻度
			try {
			if(days<=7){
				for(int i=0;i<days+1;i++){
					if(i==0){
						time=DateUtil.toString(DateUtil.getDate(time, "yyyy-MM-dd"), "yyyy-MM-dd");
					}else{
						time=DateUtil.toString(DateUtil.getDateAfterDays(DateUtil.getDate(time, "yyyy-MM-dd"), 1), "yyyy-MM-dd");
					}
					string=string+"["+i+","+"'"+time+"'"+"]";
					if(i!=days){
						string=string+",";
					}
				}
			}else{
				int j=days/7;
				for(int i=0;i<8;i++){
					if(i==0){
						time=DateUtil.toString(DateUtil.getDate(time, "yyyy-MM-dd"), "yyyy-MM-dd");
					}else{
							time=DateUtil.toString(DateUtil.getDateAfterDays(DateUtil.getDate(time, "yyyy-MM-dd"), j), "yyyy-MM-dd");
					}
					string=string+"["+i*j+","+"'"+time+"'"+"]";
					if(i!=7){
						string=string+",";
					}
				}
			}
			} catch (ParseException e) {
			}
		}else{
			long dayL=0;
			try {
			    dayL=DateUtil.getDate(from, "yyyy-MM-dd").getTime();
			} catch (ParseException e) {
			}
			for(int i=0;i<24;i++){
				start=DateUtil.toString(new Date(dayL+3600000*i), "yyyy-MM-dd HH:mm:ss");
				end=DateUtil.toString(new Date(dayL+3600000*(i+1)), "yyyy-MM-dd HH:mm:ss");
				view=analysisMyrcVisitorDao.countViewByCidATime(companyId, start, end);
				if(big<view){
					big=view;
				}
				visit=analysisMyrcVisitorDao.countVisitByCidATime(companyId, start, end);
				if(big<visit){
					big=visit;
				}
				inquiry=inquiryDao.countInquiryByCidAtime(companyId, start, end);
				if(big<inquiry){
					big=inquiry;
				}
				//点集
				viewPoint=viewPoint+"["+i+","+view+"]";
				visitPoint=visitPoint+"["+i+","+visit+"]";
				inquiryPoint=inquiryPoint+"["+i+","+inquiry+"]";
				if("10051003".equals(menberShip)){
					Integer wj=phoneLogDao.countWJCompanyBytime(companyId, start, end);
					Integer yj=phoneLogDao.countYJCompanyBytime(companyId, start, end);
					telcount=wj+yj;
					if(big<telcount){
						big=telcount;
					}
				}
				telPoint=telPoint+"["+i+","+telcount+"]";
				if(Integer.valueOf(view)==Integer.valueOf(visit)){
					titleBV=titleBV+"["+i+","+"\""+i+"点"+","+"浏览量:"+view+","+"访客数:"+visit+"\""+","+"\""+"green"+"\""+"]";
				}else{
					titleBV=titleBV+"["+i+","+"\""+i+"点"+","+"浏览量:"+view+"\""+","+"\""+"green"+"\""+"]";

				}
				titleView=titleView+"["+i+","+"\""+i+"点"+","+"浏览量:"+view+"\""+","+"\""+"green"+"\""+"]";
				titleVisit=titleVisit+"["+i+","+"\""+i+"点"+","+"访客数:"+visit+"\""+","+"\""+"pink"+"\""+"]";
				titleInquiry=titleInquiry+"["+i+","+"\""+i+"点"+","+"询盘数："+inquiry+"\""+","+"\""+"inquiry"+"\""+"]";
				titleTel=titleTel+"["+i+","+"\""+i+"点"+","+"电话数:"+telcount+"\""+","+"\""+"tel"+"\""+"]";
				string=string+"["+i+","+i+"]";
				//判断是否为最后一个点
				if(i!=23){
					viewPoint=viewPoint+",";
					visitPoint=visitPoint+",";
					inquiryPoint=inquiryPoint+",";
					telPoint=telPoint+",";
					titleView=titleView+",";
					titleVisit=titleVisit+",";
					titleInquiry=titleInquiry+",";
					titleTel=titleTel+",";
					titleBV=titleBV+",";
					string=string+",";
				}
			}
		}
		map.put("viewPoint", viewPoint);
		map.put("visitPoint", visitPoint);
		map.put("inquiryPoint", inquiryPoint);
		map.put("telPoint", telPoint);
		map.put("titleView", titleView);
		map.put("titleVisit", titleVisit);
		map.put("titleInquiry", titleInquiry);
		map.put("titleTel", titleTel);
		map.put("titleBV", titleBV);
		map.put("string", string);
		if(big==0){
			big=5;
		}
		if(big%5!=0){
			big=5*(big/5+1);
		}
		map.put("big", big);
		return map;
	}
}
