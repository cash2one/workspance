package com.zz91.mission.caiji;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.zz91.mission.domain.subscribe.Price;
import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.pool.DBPoolFactory;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.log.LogUtil;
import com.zz91.util.mail.MailUtil;

public class CaijiWasteBatteryTask implements ZZTask{
    
    final static String PRICE_OPERTION = "price_caiji";
    final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    CaiJiCommonOperate operate = new CaiJiCommonOperate();
    final static Map<String, String> FB_MAP = new HashMap<String, String>();
    static{
        // httpget 使用的编码 不然会有乱码
        FB_MAP.put("charset", "gb2312");
        FB_MAP.put("web", "http://www.ly10000.com");
        FB_MAP.put("pweb","http://www.ly10000.com.cn/index_inc/price_quotes/"); //拼接使用地址
        FB_MAP.put("url", "http://www.ly10000.com.cn/index_inc/price_quotes/metal_market_list.php?");// 采集地址
        FB_MAP.put("52",  "metalTopCategoryName=%C7%A6%CF%B5%C6%B7%D6%D6&metalName=%B7%CF%B5%E7%C6%BF&marketId=|" );

        FB_MAP.put("listStart", "<td valign=\"top\" align=\"center\">"); // 列表页开头
        FB_MAP.put("listEnd", "function cPage(newpage)"); // 列表页结尾
        
        FB_MAP.put("split", "</tr>");
        
        FB_MAP.put("contentStart", "<span id=\"fontbody\" style=\"font-size:14px; \">");
        FB_MAP.put("contentEnd", "<SCRIPT language=javascript>");

    }
    
    @Override
    public boolean init() throws Exception {
        return false;
    }
    @Override
    public boolean exec(Date baseDate) throws Exception {
        String defaultTime = DateUtil.toString(baseDate, DATE_FORMAT).substring(11,16);
        int flagFailure = 0 ;
        Integer typeId = 52;
        String url = "";
        Map<String, String> urlmap = new HashMap<String, String>();
        String errContentUrl = "";
        do {
            String content = "";
            String urlSplit = FB_MAP.get(""+typeId);
            String[] urlArray = urlSplit.split("\\|");
            for(String urlStr:urlArray){
                try {
                    url = FB_MAP.get("url")+urlStr;
                    content = operate.httpClientHtml(url,FB_MAP.get("charset"));
                } catch (HttpException e) {
                    content = null;
                } catch (IOException e) {
                    content = null;
                }
                if(content==null){
                    errContentUrl = errContentUrl + " 抓取来源网站内容失败！请点击<a href='" +  url +"'>"
                            + "<font color='#FF0000'>利源-废电瓶"+"("+typeId+")"+"</font></a>查看来源网站是否更改地址！<br />";
                    LogUtil.getInstance().log(
                            "caiji-auto", PRICE_OPERTION, null, "{'title':'废电瓶("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                    + "利源</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                    flagFailure++;
                    continue;
                }
                Integer start = content.indexOf(FB_MAP.get("listStart"));
                Integer end = content.indexOf(FB_MAP.get("listEnd"));
                String result = operate.subContent(content, start, end);
                if(StringUtils.isEmpty(result)){
                    continue;
                }
                // 分解list 为多个元素
                String[] str = result.split(FB_MAP.get("split"));
                // 检查日期是否对应今天
                String format = DateUtil.toString(baseDate, "MM/dd");
                String formatZero = DateUtil.toString(baseDate, "M/d");
                List<String> list = new ArrayList<String>();    
                for (String s : str) {
                    if (s.indexOf(format) != -1 || s.indexOf(formatZero) != -1) {
                        if(s.indexOf("利源各地废电瓶价格行情")!=-1&&s.indexOf("游客")!=-1){
                            list.add(s);
                        }
                    }
                }
                if(list.size()<1){
                    errContentUrl = errContentUrl + " 抓取失败，来源网站没有数据可抓取！请点击<a href='" +  url +"'>"
                            + "<font color='#FF0000'>利源-废电瓶"+"("+typeId+")"+"</font></a>查看来源网站<br />";
                    LogUtil.getInstance().log(
                            "caiji-auto", PRICE_OPERTION, null, "{'title':'废电瓶("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                    + "利源</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                    flagFailure++;
                    continue;
                }
                if (url.equals("http://www.ly10000.com.cn/index_inc/price_quotes/metal_market_list.php?metalTopCategoryName=%C7%A6%CF%B5%C6%B7%D6%D6&metalName=%B7%CF%B5%E7%C6%BF&marketId=") )
                {
                    if (list.size() < 2) {
                        flagFailure = flagFailure + 2 - list.size();
                        errContentUrl = errContentUrl + "来源网站未抓取到："+flagFailure+"条----请点击<a href='" +  url +"'>"
                                + "<font color='#FF0000'>利源-废电瓶"+"("+typeId+")"+"</font></a> 查看来源网站<br />-------------------------------------------------"
                                + "<br />";
                        LogUtil.getInstance().log(
                                "caiji-auto", PRICE_OPERTION, null, "{'title':'废电瓶("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                        + "利源</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                    }
                }
                String typeName = operate.queryTypeNameByTypeId(typeId);
                
                for(String aStr:list){
                    String linkStr = operate.getAlink(aStr);
                    if(StringUtils.isEmpty(linkStr)){
                        continue;
                    }else{
                        String[] alink = linkStr.split("\"");
                        if(alink.length>0){
                            String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
                            resultTitle = resultTitle.replace("利源各地废电瓶价格行情(不含税)", "全国各地废电瓶价格行情");
                            resultTitle=resultTitle+"("+DateUtil.toString(baseDate, "MM.dd")+")";
                            String resultContent = "";
                            if(alink[3].indexOf("metal")!=-1){ 
                                resultContent = operate.httpClientHtml(FB_MAP.get("pweb")+alink[3],FB_MAP.get("charset"));
                            }else{
                                resultContent = operate.httpClientHtml(FB_MAP.get("pweb")+alink[3],FB_MAP.get("charset"));
                            }
                            start = resultContent.indexOf(FB_MAP.get("contentStart"));
                            end = resultContent.indexOf(FB_MAP.get("contentEnd"));
                            resultContent = operate.subContent(resultContent,start, end);
                            if(StringUtils.isEmpty(resultContent)){
                                continue;
                            }
                            // 获取内容 (保留需要的标签内容)            
                            resultContent =Jsoup.clean(resultContent, Whitelist.none().addTags("table","td","tr"));
                            if(resultContent.indexOf("<p> 利源资讯")!=-1&& resultContent.indexOf("<p>利源资讯免责声明")!=-1){
                                resultContent = operate.subContent(resultContent, resultContent.indexOf("<p> 利源资讯"), resultContent.indexOf("<p>利源资讯免责声明"));                 
                            }else if(resultContent.indexOf("<table")!=-1&& resultContent.indexOf("</table>")!=-1){
                                resultContent = operate.subContent(resultContent, resultContent.indexOf("<table"), resultContent.indexOf("</table>")+8);
                            }
                            Price price = new Price();
                            price.setTitle(resultTitle);
                            price.setTypeId(typeId);
                            price.setTags(typeName);
                            price.setContent(resultContent); 
                            price.setGmtOrder(baseDate);
                            urlmap.put(resultTitle, url);
                            Integer flg = operate.doInsert(price,false);
                     
                           //执行插入并判断插入是否成功
                           if (flg != null && flg > 0) {
                               String catchTime = DateUtil.toString(new Date(), DATE_FORMAT).substring(11,16);
                               LogUtil.getInstance().log(
                                       "caiji-auto", PRICE_OPERTION, null, "{'title':'废电瓶("+typeId+")','type':'success','url':'<a href='" + url +"' target='_blank'>"
                                               + "利源</a>','defaultTime':'"+defaultTime+"','catchTime':'"+catchTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                               /*LogUtil.getInstance().log(
                                       "caiji-auto", PRICE_OPERTION, null, "{'title':'"+resultTitle.substring(5, resultTitle.length())+"("+typeId+")','type':'success','url':'<a href='" + urlmap.get(resultTitle) +"'>"
                                               + "利源</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");*/
                           } else if (flg == null){
                               errContentUrl = errContentUrl + "数据存数据库失败！请联系网页抓取开发者！<a href='" + urlmap.get(resultTitle) +"'>"
                                       + "<font color='#FF0000'>" +resultTitle+"</font></a><br />";
                               LogUtil.getInstance().log(
                                       "caiji-auto", PRICE_OPERTION, null, "{'title':'废电瓶("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                               + "利源</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                               /*LogUtil.getInstance().log(
                                       "caiji-auto", PRICE_OPERTION, null, "{'title':'"+resultTitle.substring(5, resultTitle.length())+"("+typeId+")','type':'failure','url':'<a href='" + urlmap.get(resultTitle) +"'>"
                                               + "利源</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");*/
                               flagFailure++;
                           }
                        }
                    }
                }
            }
        } while (false);
        if (flagFailure != 0){
            String content = "废电瓶"+"("+typeId+")"+"未抓取"+flagFailure+"条。分别为：<br />"
                    +errContentUrl + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_fb.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("content", content);
            MailUtil.getInstance().sendMail(
                    "废电瓶自动抓取报错", 
                    "zz91.price.caiji.auto@asto.mail", null,
                    null, "zz91", "blank",
                    map, MailUtil.PRIORITY_DEFAULT);
            return false;
        } else {
            return true;
        }
    }
    @Override
    public boolean clear(Date baseDate) throws Exception {
        return false;
    }

    public static void main(String[] args) {
        DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
        CaijiWasteBatteryTask js=new CaijiWasteBatteryTask();
      
        try {
            js.exec(DateUtil.getDate("2014-06-23", "yyyy-MM-dd"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
