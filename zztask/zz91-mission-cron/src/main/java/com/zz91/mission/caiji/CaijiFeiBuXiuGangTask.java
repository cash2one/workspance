package com.zz91.mission.caiji;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.zz91.mission.domain.subscribe.Price;
import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.pool.DBPoolFactory;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.log.LogUtil;
import com.zz91.util.mail.MailUtil;

public class CaijiFeiBuXiuGangTask implements ZZTask {
	final static String PRICE_OPERTION = "price_caiji";
    final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    CaiJiCommonOperate operate = new CaiJiCommonOperate();
    final static Map<String, String> STEELCN_MAP = new HashMap<String, String>();
    static{
        // httpget 使用的编码 不然会有乱码
        STEELCN_MAP.put("charset", "gb2312");
        // 采集地址
        STEELCN_MAP.put("url", "http://search.f139.com/search.do?f=%E5%BA%9F%E6%96%99%E6%97%A5%E8%AF%84&site=1&rg=article");
        // 后台类别对应采集地址 的key
        STEELCN_MAP.put("216", ""); //废不锈钢日评
        
        STEELCN_MAP.put("listStart", "<div id=\"list_m\">"); // 列表页开头
        STEELCN_MAP.put("listEnd", "<div class=\"pages\">"); // 列表页结尾
        
        STEELCN_MAP.put("contentStart", "【废不锈钢】<br />");
        STEELCN_MAP.put("contentEnd", "更多资讯，请点击：");
        STEELCN_MAP.put("splitLink", "\"");
        
    }
	@Override
	public boolean exec(Date baseDate) throws Exception {
		//当前日期
		 String defaultTime = DateUtil.toString(baseDate, DATE_FORMAT);
		 String errContentUrl = "";
		 String url=STEELCN_MAP.get("url");
		 Integer typeId=216;
		 Integer flagFailure=0;
		 do{
			 // 获取list 列表
       	 String content = operate.httpClientHtml(url, STEELCN_MAP.get("charset"));
       	 if(content==null){
	                errContentUrl = errContentUrl + " 抓取来源网站内容失败！请点击<a href='" +  STEELCN_MAP.get("url") +"'>"
	                        + "<font color='#FF0000'>废金属行情综述(废不锈钢日评)（219）</font></a>查看来源网站是否更改地址！<br />"
	                        + "如果有，请<a href='http://search.f139.com/search.do?f=%E5%BA%9F%E6%96%99%E6%97%A5%E8%AF%84&site=1&rg=article'>"
	                    + "<font color='#FF0000'>抓取</font></a>！";
	                LogUtil.getInstance().log(
	                        "caiji-auto", PRICE_OPERTION, null, "{'title':'废金属行情综述(废不锈钢日评)（219）','type':'failure','url':'<a href='" + url +"'target='_blank'>"
	                                + "富宝</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
	                Map<String, Object> map = new HashMap<String, Object>();
	                map.put("content", errContentUrl);
	                MailUtil.getInstance().sendMail(
	                        "废金属行情综述(废不锈钢日评)自动抓取报错", 
	                        "zz91.price.caiji.auto@asto.mail", null,
	                        null, "zz91", "blank",
	                        map, MailUtil.PRIORITY_DEFAULT);
	                return false;
	            }
       	 	 Integer start = content.indexOf(STEELCN_MAP.get("listStart"));
	         Integer end = content.indexOf(STEELCN_MAP.get("listEnd"));
	         String result = content.substring(start, end);
	         String[] str = result.split("</dd>");
	         String format=DateUtil.toString(baseDate, "yyyy-MM-dd");
	         List<String> list = new ArrayList<String>();
	         for (String s : str) {
	                if (s.indexOf(format) != -1) {
	                     list.add(s);
	                }
	           }
	         String typeName = operate.queryTypeNameByTypeId(typeId);
	         for(String aStr:list){
	                String linkStr = operate.getAlink(aStr);
	                if(StringUtils.isEmpty(linkStr)){
	                    continue;
	                }else{
	                    String[] alink = linkStr.split(STEELCN_MAP.get("splitLink"));
	                    if(alink.length>0){
	                        String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
	                        resultTitle=resultTitle.substring(2);
	                        resultTitle=DateUtil.toString(baseDate, "MM月dd日")+"废不锈钢"+resultTitle;
	                        String resultContent ="";
	                        String newurl=alink[1];
	                        resultContent = operate.httpClientHtml(newurl, STEELCN_MAP.get("charset"));
	                        Integer contentStart=resultContent.indexOf(STEELCN_MAP.get("contentStart"));
	                        Integer contentEnd=resultContent.indexOf(STEELCN_MAP.get("contentEnd"));
	                        resultContent = resultContent.substring(contentStart, contentEnd);
	                        resultContent = Jsoup.clean(resultContent, Whitelist.none().addTags("p","ul","br","li","table","th","tr","td").addAttributes("td","rowspan"));
	                        resultContent=resultContent.replace("【废不锈钢】", "");
	                        resultContent=resultContent.replace("<br />", "<p>");
	                        Price price = new Price();
	                        price.setTitle(resultTitle);
	                        price.setTypeId(typeId);
	                        price.setTags(typeName);
	                        price.setContent(resultContent);
	                        price.setGmtOrder(baseDate);
	                        price.setIsChecked("0");
	                        Integer flg = operate.doInsert(price,false);
	                       //执行插入并判断插入是否成功
	                       if (flg == null){
	                           errContentUrl = errContentUrl + "数据存数据库失败！请联系网页抓取开发者！<a href='" + resultTitle +"'>"
	                                   + "<font color='#FF0000'>" +resultTitle+"</font></a><br />";
	                           flagFailure++;
	                       }
	                    }
	                }
	         }
		 }while(false);
		  if (flagFailure != 0){
	            String content = "废金属行情综述(废不锈钢日评)"+"("+typeId+")"+"未抓取"+flagFailure+"条。分别为：<br />"
	                    +errContentUrl + "如果有，请<a href='http://apps2.zz91.com/task/job/definition/doTask.htm?jobName=CaijiFeiBuXiuGangTask&start="+DateUtil.getDate(new Date(), "yyyy-MM-dd")+" 00:00:00'>"
	                            + "<font color='#FF0000'>抓取</font></a>！";
	            LogUtil.getInstance().log(
	                    "caiji-auto", PRICE_OPERTION, null, "{'title':'废金属行情综述(废不锈钢日评)("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
	                            + "富宝</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
	            Map<String, Object> map = new HashMap<String, Object>();
	            map.put("content", content);
	            MailUtil.getInstance().sendMail(
	                    "废金属行情综述(废不锈钢日评)自动抓取报错", 
	                    "zz91.price.caiji.auto@asto.mail", null,
	                    null, "zz91", "blank",
	                    map, MailUtil.PRIORITY_DEFAULT);
	            return false;
	        } else {
	            String catchTime = DateUtil.toString(new Date(), "yyyy-MM-dd HH:mm:ss").substring(11,16);
	            LogUtil.getInstance().log(
	                    "caiji-auto", PRICE_OPERTION, null, "{'title':'废金属行情综述(废不锈钢日评)("+typeId+")','type':'success','url':'<a href='" + url +"' target='_blank'>"
	                            + "富宝</a>','defaultTime':'"+defaultTime+"','catchTime':'"+catchTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
	            return true;
	        }
	}

	@Override
	public boolean init() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean clear(Date baseDate) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public static void main(String[] args) {
		  DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
		  CaijiFeiBuXiuGangTask js=new CaijiFeiBuXiuGangTask();
	        try {
	            js.exec(DateUtil.getDate("2015-04-10", "yyyy-MM-dd"));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}

}
