package com.ast.ast1949.price.controller.subject;

import java.io.IOException;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.xml.XMLSerializer;

import org.apache.commons.httpclient.HttpException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ast.ast1949.price.controller.BaseController;
import com.zz91.util.http.HttpUtils;
@Controller
public class DongsuController extends BaseController {

	@RequestMapping
	public void index(Map<String, Object> out) throws HttpException, IOException {
		// TYL //原料通用料品名
		// GCL //原料工程料品名
		// TXT //原料弹性体品名
		// YLQuote //原料报价
		// GXL //改性料品名
		// GXLTX //改性料特性
		// GXLYT //改性料用途
		// GXQuote //改性料报价，查询几条
		// ZSL //再生料品名
		// ZSLevel //再生料级别
		// ZSQuote //再生料报价
		// ZJL //助剂料品名
		// ZJQuote //助剂料报价
		// JYH //精英会

		String str = HttpUtils.getInstance().httpGet("http://www.dgs6.com/API/ZZ91.aspx?action=TYL&top=14",HttpUtils.CHARSET_UTF8);
		XMLSerializer xmlSerializer = new XMLSerializer() ;  
		JSON json = xmlSerializer.read(str);
		JSONArray ja = JSONArray.fromObject(json);
		out.put("tyl", ja);
		str = HttpUtils.getInstance().httpGet("http://www.dgs6.com/API/ZZ91.aspx?action=GCL&top=14",HttpUtils.CHARSET_UTF8);
		xmlSerializer = new XMLSerializer() ;  
		JSON jsonGCL = xmlSerializer.read(str);
		JSONArray jaGCL = JSONArray.fromObject(jsonGCL);
		out.put("gcl", jaGCL);
		str = HttpUtils.getInstance().httpGet("http://www.dgs6.com/API/ZZ91.aspx?action=TXT&top=14",HttpUtils.CHARSET_UTF8);
		xmlSerializer = new XMLSerializer() ;  
		JSON jsonTXT = xmlSerializer.read(str);
		JSONArray jaTXT = JSONArray.fromObject(jsonTXT);
		out.put("txt", jaTXT);
		
		str = HttpUtils.getInstance().httpGet("http://www.dgs6.com/API/ZZ91.aspx?action=YLQuote&top=10",HttpUtils.CHARSET_UTF8);
		xmlSerializer = new XMLSerializer() ;  
		JSON jsonYLQuote = xmlSerializer.read(str);
		JSONArray jaYLQuote = JSONArray.fromObject(jsonYLQuote);
		out.put("ylquote", jaYLQuote);
		
		str = HttpUtils.getInstance().httpGet("http://www.dgs6.com/API/ZZ91.aspx?action=GXL&top=7",HttpUtils.CHARSET_UTF8);
		xmlSerializer = new XMLSerializer() ;  
		JSON jsonGXL = xmlSerializer.read(str);
		JSONArray jaGXL = JSONArray.fromObject(jsonGXL);
		out.put("gxl", jaGXL);

		str = HttpUtils.getInstance().httpGet("http://www.dgs6.com/API/ZZ91.aspx?action=GXLTX&top=10",HttpUtils.CHARSET_UTF8);
		xmlSerializer = new XMLSerializer() ;  
		JSON jsonGXLTX = xmlSerializer.read(str);
		JSONArray jaGXLTX = JSONArray.fromObject(jsonGXLTX);
		out.put("gxltx", jaGXLTX);

		str = HttpUtils.getInstance().httpGet("http://www.dgs6.com/API/ZZ91.aspx?action=GXLYT&top=8",HttpUtils.CHARSET_UTF8);
		xmlSerializer = new XMLSerializer() ;  
		JSON jsonGXLYT = xmlSerializer.read(str);
		JSONArray jaGXLYT = JSONArray.fromObject(jsonGXLYT);
		out.put("gxlyt", jaGXLYT);

		str = HttpUtils.getInstance().httpGet("http://www.dgs6.com/API/ZZ91.aspx?action=GXQuote&top=2",HttpUtils.CHARSET_UTF8);
		xmlSerializer = new XMLSerializer() ;  
		JSON jsonGXQuote = xmlSerializer.read(str);
		JSONArray jaGXQuote = JSONArray.fromObject(jsonGXQuote);
		out.put("gxquote", jaGXQuote);
		
		str = HttpUtils.getInstance().httpGet("http://www.dgs6.com/API/ZZ91.aspx?action=ZSL&top=10",HttpUtils.CHARSET_UTF8);
		xmlSerializer = new XMLSerializer() ;  
		JSON jsonZSL = xmlSerializer.read(str);
		JSONArray jaZSL = JSONArray.fromObject(jsonZSL);
		out.put("zsl", jaZSL);
		
		str = HttpUtils.getInstance().httpGet("http://www.dgs6.com/API/ZZ91.aspx?action=ZSLevel&top=10",HttpUtils.CHARSET_UTF8);
		xmlSerializer = new XMLSerializer() ;  
		JSON jsonZSLevel = xmlSerializer.read(str);
		JSONArray jaZSLevel = JSONArray.fromObject(jsonZSLevel);
		out.put("zslevel", jaZSLevel);
		
		str = HttpUtils.getInstance().httpGet("http://www.dgs6.com/API/ZZ91.aspx?action=ZSQuote&top=2",HttpUtils.CHARSET_UTF8);
		xmlSerializer = new XMLSerializer() ;
		JSON jsonZSQuote = xmlSerializer.read(str);
		JSONArray jaZSQuote = JSONArray.fromObject(jsonZSQuote);
		out.put("zsquote", jaZSQuote);
		
		str = HttpUtils.getInstance().httpGet("http://www.dgs6.com/API/ZZ91.aspx?action=ZJL&top=20",HttpUtils.CHARSET_UTF8);
		xmlSerializer = new XMLSerializer() ;  
		JSON jsonZJL = xmlSerializer.read(str);
		JSONArray jaZJL = JSONArray.fromObject(jsonZJL);
		out.put("zjl", jaZJL);
		
		str = HttpUtils.getInstance().httpGet("http://www.dgs6.com/API/ZZ91.aspx?action=ZJQuote&top=2",HttpUtils.CHARSET_UTF8);
		xmlSerializer = new XMLSerializer() ;  
		JSON jsonZJQuote = xmlSerializer.read(str);
		JSONArray jaZJQuote = JSONArray.fromObject(jsonZJQuote);
		out.put("zjquote", jaZJQuote);
		
		str = HttpUtils.getInstance().httpGet("http://www.dgs6.com/API/ZZ91.aspx?action=JYH&top=1",HttpUtils.CHARSET_UTF8);
		xmlSerializer = new XMLSerializer() ;  
		JSON jsonJYH = xmlSerializer.read(str);
		JSONArray jaJYH = JSONArray.fromObject(jsonJYH);
		out.put("jyh", jaJYH);
		
		str = HttpUtils.getInstance().httpGet("http://www.dgs6.com/api/zz91.aspx?action=AD",HttpUtils.CHARSET_UTF8);
		xmlSerializer = new XMLSerializer() ;  
		JSON jsonAD = xmlSerializer.read(str);
		JSONArray jaAD = JSONArray.fromObject(jsonAD);
		out.put("ad", jaAD);
		
		
	}
}
