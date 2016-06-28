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
import com.zz91.util.log.LogUtil;
import com.zz91.util.mail.MailUtil;

public class CaijiWasterBatteryPTask implements ZZTask {
	final static String PRICE_OPERTION = "price_caiji";
    final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    CaiJiCommonOperate operate = new CaiJiCommonOperate();
    final static Map<String, String> STEELCN_MAP = new HashMap<String, String>();
    static{
        // httpget 使用的编码 不然会有乱码
        STEELCN_MAP.put("charset", "gb2312");
        // 采集地址
        STEELCN_MAP.put("url", "http://www.ly10000.com.cn/index_inc/price_quotes/metal_market_list.php?metalTopCategoryName=%C7%A6%CF%B5%C6%B7%D6%D6&metalName=%B7%CF%B5%E7%C6%BF&currentPage=2&metalTopCategoryName=%C7%A6%CF%B5%C6%B7%D6%D6&metalName=%B7%CF%B5%E7%C6%BF&marketId=");
        // 后台类别对应采集地址 的key
        STEELCN_MAP.put("52", ""); //全国废金属行情
        
        STEELCN_MAP.put("listStart", "lybusinesschoice_middlethead1_03.gif"); // 列表页开头
        STEELCN_MAP.put("listEnd", "<td align=\"right\" class=\"normal\">"); // 列表页结尾
        
        STEELCN_MAP.put("contentStart", "<TABLE style=\"BACKGROUND-COLOR: #9f9f9f; FONT-FAMILY: '宋体'; FONT-SIZE: 14px\" border=0 cellSpacing=1 cellPadding=0 width=660 align=center>");
        STEELCN_MAP.put("contentEnd", "<br><br><br><br>");
        
    }
	@Override
	public boolean exec(Date baseDate) throws Exception {
		  String defaultTime = DateUtil.toString(baseDate, DATE_FORMAT).substring(11,16);
	        int flagFailure = 0 ;
	        Integer typeId = 52;
	        String url = STEELCN_MAP.get("url");
	        Map<String, String> urlmap = new HashMap<String, String>();
	        String errContentUrl = "";
	        do {
	            // 获取list 列表
	        	 String content = operate.httpClientHtml(url, STEELCN_MAP.get("charset"));
	            // 检查日期是否对应今天
	            String format = DateUtil.toString(baseDate, "MM/dd");
	            String formatZero = DateUtil.toString(baseDate, "M/d");
	            if(content==null){
	                errContentUrl = errContentUrl + " 抓取来源网站内容失败！请点击<a href='" +  STEELCN_MAP.get("url") +"'>"
	                        + "<font color='#FF0000'>全国各地废电瓶价格行情(52)</font></a>查看来源网站是否更改地址！<br />"
	                        + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_worldscrap_wasteplastics.htm'>"
	                    + "<font color='#FF0000'>抓取</font></a>！";
	                LogUtil.getInstance().log(
	                        "caiji-auto", PRICE_OPERTION, null, "{'title':'全国各地废电瓶价格行情(52)','type':'failure','url':'<a href='" + url +"'target='_blank'>"
	                                + "利源信息网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
	                Map<String, Object> map = new HashMap<String, Object>();
	                map.put("content", errContentUrl);
	                MailUtil.getInstance().sendMail(
	                        "全国各地废电瓶价格行情自动抓取报错", 
	                        "zz91.price.caiji.auto@asto.mail", null,
	                        null, "zz91", "blank",
	                        map, MailUtil.PRIORITY_DEFAULT);
	                return false;
	            }
	            Integer start = content.indexOf(STEELCN_MAP.get("listStart"));
	            Integer end = content.indexOf(STEELCN_MAP.get("listEnd"));
	            String result = content.substring(start, end);
	            String[] str = result.split("</tr>");
	            List<String> list = new ArrayList<String>();
	            
	            for (String s : str) {
	                if (s.indexOf(format) != -1) {
	                    if(s.contains("利源各地废电瓶价格行情(不含税)")) {
	                        list.add(s);
	                    }
	                }
	            }
	            if (list.size() < 1) {
	                errContentUrl = errContentUrl + " 抓取失败，来源网站没有数据可抓取！请点击<a href='" +  STEELCN_MAP.get("url") +"'>"
	                        + "<font color='#FF0000'>全国各地废电瓶价格行情(52)</font></a>查看来源网站<br />"
	                        + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_worldscrap_wasteplastics.htm'>"
	                    + "<font color='#FF0000'>抓取</font></a>！";
	                LogUtil.getInstance().log(
	                        "caiji-auto", PRICE_OPERTION, null, "{'title':'全国各地废电瓶价格行情(52)','type':'failure','url':'<a href='" + url +"' target='_blank'>"
	                                + "利源信息网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
	                Map<String, Object> map = new HashMap<String, Object>();
	                map.put("content", errContentUrl);
	                MailUtil.getInstance().sendMail(
	                        "全国各地废电瓶价格行情自动抓取报错", 
	                        "zz91.price.caiji.auto@asto.mail", null,
	                        null, "zz91", "blank",
	                        map, MailUtil.PRIORITY_DEFAULT);
	                return false;
	            }
	            String typeName = operate.queryTypeNameByTypeId(typeId);
	            String[] string=list.get(0).split("\"");
	            String newUrl="http://www.ly10000.com.cn/index_inc/price_quotes/"+string[13];
	            String resultContent = operate.getContent(STEELCN_MAP, newUrl);
	            resultContent = Jsoup.clean(resultContent, Whitelist.none().addTags("div","p","ul","br","li","table","th","tr","td").addAttributes("td","rowspan"));
	            format=format.replace('/', '.');
	            String resultTitle="全国各地废电瓶价格行情（"+format+")";
	            Price price = new Price();
                price.setTitle(resultTitle);
                price.setTypeId(typeId);
                price.setTags(typeName);
                price.setContent(resultContent);
                price.setGmtOrder(baseDate);
                Integer flg = operate.doInsert(price,false);
                if (flg != null && flg > 0) {
                    String catchTime = DateUtil.toString(new Date(), DATE_FORMAT).substring(11,16);
                    LogUtil.getInstance().log(
                            "caiji-auto", PRICE_OPERTION, null, "{'title':'废电瓶("+typeId+")','type':'success','url':'<a href='" + url +"' target='_blank'>"
                                    + "利源信息网</a>','defaultTime':'"+defaultTime+"','catchTime':'"+catchTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                } else if (flg == null){
                    errContentUrl = errContentUrl + "数据存数据库失败！请联系网页抓取开发者！<a href='" + urlmap.get(resultTitle) +"'>"
                            + "<font color='#FF0000'>" +resultTitle+"</font></a><br />";
                    LogUtil.getInstance().log(
                            "caiji-auto", PRICE_OPERTION, null, "{'title':'废电瓶("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                    + "利源信息网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                    flagFailure++;
                }
	        }while(false);
		return true;
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
		 CaijiWasterBatteryPTask js=new CaijiWasterBatteryPTask();
	        try {
	            js.exec(DateUtil.getDate("2014-08-08", "yyyy-MM-dd"));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        System.out.println("123");
	}

}
