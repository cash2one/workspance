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

public class CaijiAroundFeiSlMarketTask implements ZZTask{
    
    final static String PRICE_OPERTION = "price_caiji";
    final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    CaiJiCommonOperate operate = new CaiJiCommonOperate();

    final static Map<String, String> WASTEPLASTICS_MAP = new HashMap<String, String>();
    static{
        // httpget 使用的编码 不然会有乱码
        WASTEPLASTICS_MAP.put("charset", "utf-8");
        // 采集地址
        WASTEPLASTICS_MAP.put("url", "http://china.worldscrap.com/modules/cn/plastic/cndick_index.php?sort=7-2");
        WASTEPLASTICS_MAP.put("contentUrl", "http://china.worldscrap.com/modules/cn/plastic/");
        WASTEPLASTICS_MAP.put("listStart", "<td width=\"625\" valign=\"top\">"); // 列表页开头
        WASTEPLASTICS_MAP.put("listEnd", "<td height=\"30\" bgcolor=\"#f4f4f4\">"); // 列表页结尾
        
        WASTEPLASTICS_MAP.put("contentStart", "<td class=\"line_height_new\">");
        WASTEPLASTICS_MAP.put("contentEnd", "（worldscrap.com MQ）");
        
    }

    @Override
    public boolean init() throws Exception {
        return false;
    }

    @Override
    public boolean exec(Date baseDate) throws Exception {
        String defaultTime = DateUtil.toString(baseDate, DATE_FORMAT).substring(11,16);
        
        int flagFailure = 0 ;
        Map<String, String> urlmap = new HashMap<String, String>();
        String errContentUrl = "";
        String url = "";
        do {
            Integer typeId = 0;
            String content ="";
            try {
                url = WASTEPLASTICS_MAP.get("url");
                content = operate.httpClientHtml(url, WASTEPLASTICS_MAP.get("charset"));
            } catch (HttpException e) {
                content = null;
            } catch (IOException e) {
                content = null;
            }
            if(content==null){
                errContentUrl = errContentUrl + " 抓取来源网站内容失败！请点击<a href='" +  WASTEPLASTICS_MAP.get("url") +"'>"
                        + "<font color='#FF0000'>各地废塑料市场(22下子类)</font></a>查看来源网站是否更改地址！<br />"
                        + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_worldscrap_wasteplastics.htm'>"
                    + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'各地废塑料市场(22下子类)','type':'failure','url':'<a href='" + url +"'target='_blank'>"
                                + "世界废料网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "各地废塑料市场自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
            Integer start = content.indexOf(WASTEPLASTICS_MAP.get("listStart"));
            Integer end = content.indexOf(WASTEPLASTICS_MAP.get("listEnd"));
            String result = content.substring(start, end);
            String[] str = result.split("</tr>");

            // 检查日期是否对应今天
            String format = DateUtil.toString(baseDate, "M月d日");
            List<String> list = new ArrayList<String>();
            
            for (String s : str) {
                if (s.indexOf(format) != -1) {
                    if(s.contains("浙江") || s.contains("河北") || s.contains("山东")
                            || s.contains("湖南") || s.contains("广东") || s.contains("江苏")) {
                        list.add(s);
                    }
                }
            }
            if (list.size() < 1) {
                errContentUrl = errContentUrl + " 抓取失败，来源网站没有数据可抓取！请点击<a href='" +  WASTEPLASTICS_MAP.get("url") +"'>"
                        + "<font color='#FF0000'>各地废塑料市场(22下子类)</font></a>查看来源网站<br />"
                        + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_worldscrap_wasteplastics.htm'>"
                    + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'各地废塑料市场(22下子类)','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "世界废料网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "各地废塑料市场自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
            if (list.size() < 5) {
                flagFailure = 5 - list.size();
                errContentUrl = errContentUrl + "来源网站未抓取到："+flagFailure+"条----请点击<a href='" +  WASTEPLASTICS_MAP.get("url") +"'>"
                        + "<font color='#FF0000'>各地废塑料市场(22下子类)</font></a> 查看来源网站<br />-------------------------------------------------"
                        + "<br />";
            }
            for(String aStr:list){
                String linkStr = operate.getAlink(aStr);
                if(StringUtils.isEmpty(linkStr)){
                    continue;
                }else{
                    String[] alink = linkStr.split("\"");
                    if(alink.length>0){
                        String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
                        resultTitle=resultTitle.replace("行情评述", "动态");
                        urlmap.put(resultTitle, "http://china.worldscrap.com/modules/cn/plastic/cndick_index.php?sort=7-2");
                        String resultContent ="";
                        String newUrl = WASTEPLASTICS_MAP.get("contentUrl") + alink[1]; 
                        resultContent = operate.getContent(WASTEPLASTICS_MAP, newUrl);
                        if(StringUtils.isEmpty(resultContent)) {
                            continue;
                        }
                        resultContent = resultContent.replaceAll("<td class=\"line_height_new\">", "<p>");
                        resultContent = resultContent.concat("</p>");
                        resultContent = Jsoup.clean(resultContent, Whitelist.none().addTags("div","p","ul","br","li","table","th","tr","td").addAttributes("td","rowspan"));
                        if(resultContent.contains("世界再生网讯")){
                            resultContent = resultContent.replace("世界再生网讯", "");
                        }
                        String typeName = "";
                        if(resultTitle.contains("浙江")) {
                            typeId = 127;
                        } else if(resultTitle.contains("河北")) {
                            typeId = 138;
                        } else if(resultTitle.contains("山东")) {
                            typeId = 132;
                        } else if(resultTitle.contains("广东")) {
                            typeId = 130;
                        } else if(resultTitle.contains("江苏")) {
                            typeId = 128;
                        } else if(resultTitle.contains("湖南")){
                            typeId = 133;
                        }
                        typeName = operate.queryTypeNameByTypeId(typeId);
                      /*  //获取typeName所在的下标
                        Integer ends=resultContent.indexOf(typeName);
                        //获取今日所在的下标
                        Integer starts=resultContent.indexOf("今日，");
                        //获取今日，
                        String strs=resultContent.substring(starts, starts+3);
                        //获取总体内容
                        resultContent=strs+resultContent.substring(ends);*/
                        String[] strs=resultContent.split("</p>");
                        String[] resultContents=strs[2].split("<p>");
                        resultContent=resultContents[1];
                        resultContent="<p>"+"今日,"+resultContent+"</p>";
                        Price price = new Price();
                        price.setTitle(resultTitle);
                        price.setTypeId(typeId);
                        price.setTags(typeName);
                        price.setContent(resultContent);
                        price.setGmtOrder(baseDate);
                        Integer flg = operate.doInsert(price,false);
                        //执行插入并判断插入是否成功
                        /*if (flg != null && flg > 0) {
                            LogUtil.getInstance().log(
                                    "caiji-auto", PRICE_OPERTION, null, "{'title':'"+resultTitle.substring(5, resultTitle.length())+"("+typeId+")','type':'success','url':'<a href='" + urlmap.get(resultTitle) +"'>"
                                            + "世界废料网</a>','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                        } else*/ if (flg == null){
                            errContentUrl = errContentUrl + "数据存数据库失败！请联系网页抓取开发者！<a href='" + urlmap.get(resultTitle) +"'>"
                                    + "<font color='#FF0000'>" +resultTitle+"</font></a><br />";/*
                            LogUtil.getInstance().log(
                                    "caiji-auto", PRICE_OPERTION, null, "{'title':'"+resultTitle.substring(5, resultTitle.length())+"("+typeId+")','type':'failure','url':'<a href='" + urlmap.get(resultTitle) +"'>"
                                            + "世界废料网</a>','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");*/
                            flagFailure++;
                        }
                    }
                }
            }
            
        } while (false);
        if (flagFailure != 0){
            String content = "各地废塑料市场(22下子类)未抓取"+flagFailure+"条。分别为：<br />"
                    +errContentUrl + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_worldscrap_wasteplastics.htm'>"
                    + "<font color='#FF0000'>抓取</font></a>！"; 
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'各地废塑料市场(22下子类)','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                            + "世界废料网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("content", content);
            MailUtil.getInstance().sendMail(
                    "各地废塑料市场自动抓取报错", 
                    "zz91.price.caiji.auto@asto.mail", null,
                    null, "zz91", "blank",
                    map, MailUtil.PRIORITY_DEFAULT);
            return false;
        } else {
            String catchTime = DateUtil.toString(new Date(), DATE_FORMAT).substring(11,16);
            
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'各地废塑料市场(22下子类)','type':'success','url':'<a href='" + url +"' target='_blank'>"
                            + "世界废料网</a>','defaultTime':'"+defaultTime+"','catchTime':'"+catchTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            return true;
        }
    }

    @Override
    public boolean clear(Date baseDate) throws Exception {
        return false;
    }

    public static void main(String[] args) {
        
        DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
        CaijiAroundFeiSlMarketTask js=new CaijiAroundFeiSlMarketTask();
        try {
            js.exec(DateUtil.getDate("2014-09-01", "yyyy-MM-dd"));
            System.out.println("123");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
