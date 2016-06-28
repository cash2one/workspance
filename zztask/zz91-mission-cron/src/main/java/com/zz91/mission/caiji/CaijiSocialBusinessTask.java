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

public class CaijiSocialBusinessTask implements ZZTask{
    
    final static String PRICE_OPERTION = "price_caiji";
    final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    CaiJiCommonOperate operate = new CaiJiCommonOperate();

    final static Map<String, String> SOCIALBUSINESS_MAP = new HashMap<String, String>();
    static{
        // httpget 使用的编码 不然会有乱码
        SOCIALBUSINESS_MAP.put("charset", "UTF-8");
        
        //采集地址 正式
        SOCIALBUSINESS_MAP.put("url", "http://www.100ppi.com/news/list-15-12--1.html");
        SOCIALBUSINESS_MAP.put("contentUrl", "http://www.100ppi.com/news/");
        SOCIALBUSINESS_MAP.put("listStart", "<div class=\"list-c\">"); // 列表页开头
        SOCIALBUSINESS_MAP.put("listEnd", "<div class=\"page-inc\">"); // 列表页结尾
        
        SOCIALBUSINESS_MAP.put("contentStart", "<div class=\"nd-c\" style=\"overflow:hidden;width:588px;\">");
        SOCIALBUSINESS_MAP.put("contentEnd", "(文章来源");
        SOCIALBUSINESS_MAP.put("split", "<li>");
        SOCIALBUSINESS_MAP.put("splitLink", "\"");
        
    }

    @Override
    public boolean init() throws Exception {
        return false;
    }

    @Override
    public boolean exec(Date baseDate) throws Exception {
        String defaultTime = DateUtil.toString(baseDate, DATE_FORMAT).substring(11,16);
        int flagFailure = 0 ;
        Integer typeId = 0;
        String url = "";
        Map<String, String> urlmap = new HashMap<String, String>();
        String errContentUrl = "";
        do {
            String content ="";
            try {
                url = SOCIALBUSINESS_MAP.get("url");
                content = operate.httpClientHtml(url, SOCIALBUSINESS_MAP.get("charset"));
            } catch (HttpException e) {
                content = null;
            } catch (IOException e) {
                content = null;
            }
            if(content==null){
                errContentUrl = errContentUrl + " 抓取来源网站内容失败！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>生意社-生意社塑料(22子类下)</font></a>查看来源网站是否更改地址！<br />"
                                + "如果有，请<a href='http://apps2.zz91.com/task/job/definition/doTask.htm?jobName=CaijiSocialBusinessTask&start="+DateUtil.getDate(new Date(), "yyyy-MM-dd")+" 00:00:00'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'生意社塑料(22子类下)','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "生意社</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "生意社塑料自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
            Integer start = content.indexOf(SOCIALBUSINESS_MAP.get("listStart"));
            Integer end = content.indexOf(SOCIALBUSINESS_MAP.get("listEnd"));
            String result = content.substring(start, end);
            String[] str = result.split(SOCIALBUSINESS_MAP.get("split"));

            // 检查日期是否对应今天
            String formatDate = DateUtil.toString(baseDate, "yyyy-MM-dd");
            List<String> list = new ArrayList<String>();
            for (String s : str) {
                if (s.indexOf(formatDate) != -1) {
                    //判断标题是否含有英文
                    String linkStr = s.substring(s.indexOf("<a href=\"detail"));
                    String title = linkStr.substring(linkStr.indexOf("\"blueq\">")+8, linkStr.indexOf("</a>"));
                    if (operate.hasLetter(title)) {
                        if (s.contains("顺德") || s.contains("成都") || s.contains("汕头") || s.contains("福建")
                                || s.contains("宁波") || s.contains("东莞") || s.contains("南京")
                                || s.contains("上海") || s.contains("常州") || s.contains("广州")
                                || s.contains("河南") || s.contains("天津") || s.contains("河北")
                                || s.contains("华东") || s.contains("华南") || s.contains("华北")) {
                            list.add(s);
                        }
                    }
                }
            }
            if(list.size()<1){
                errContentUrl = errContentUrl + " 抓取失败，来源网站没有数据可抓取！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>生意社-生意社塑料(22子类下)</font></a>查看来源网站<br />"
                                + "如果有，请<a href='http://apps2.zz91.com/task/job/definition/doTask.htm?jobName=CaijiSocialBusinessTask&start="+DateUtil.getDate(new Date(), "yyyy-MM-dd")+" 00:00:00'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'生意社塑料(22子类下)','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "生意社</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "生意社塑料自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
            if (list.size() < 16) {
                flagFailure = 16 - list.size();
                errContentUrl = errContentUrl + "来源网站未抓取到："+flagFailure+"条----请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>生意社-生意社塑料(22子类下)</font></a> 查看来源网站<br />-------------------------------------------------"
                        + "<br />";
            }
            
            for(String aStr:list){
                String linkStr = aStr.substring(aStr.indexOf("<a href=\"detail"));
                if(StringUtils.isEmpty(linkStr)){
                    continue;
                }else{
                    String[] alink = linkStr.split(SOCIALBUSINESS_MAP.get("splitLink"));
                    if(alink.length>0){
                        String resultTitle = operate.changeLetter(linkStr.substring(linkStr.indexOf("\"blueq\">")+8, linkStr.indexOf("</a>")));
                        resultTitle = operate.splitTitle(resultTitle, "废塑料市场动态");
                        String resultContent ="";
                        String newUrl = SOCIALBUSINESS_MAP.get("contentUrl") + alink[1]; 
                        resultContent = operate.getContent(SOCIALBUSINESS_MAP, newUrl);
                        resultContent = Jsoup.clean(resultContent, Whitelist.none().addTags("p","ul","br","li","table","th","tr","td").addAttributes("td","colspan"));
                        
                        if(resultTitle.contains("顺德")) {
                            typeId = 315;
                        }
                        if(resultTitle.contains("成都")) {
                            typeId = 316;
                        }
                        if(resultTitle.contains("汕头")) {
                            typeId = 317;
                        }
                        if(resultTitle.contains("福建")) {
                            typeId = 131;
                        }
                        if(resultTitle.contains("宁波")) {
                            typeId = 318;
                        }
                        if(resultTitle.contains("东莞")){
                            typeId = 319;
                        }
                        if(resultTitle.contains("南京")) {
                            typeId = 320;
                        }
                        if(resultTitle.contains("上海")) {
                            typeId = 129;
                        }
                        if(resultTitle.contains("常州")) {
                            typeId = 321;
                        }
                        if(resultTitle.contains("广州")) {
                            typeId = 130;
                        }
                        if(resultTitle.contains("河南")) {
                            typeId = 142;
                        }
                        if(resultTitle.contains("天津")){
                            typeId = 140;
                        }
                        if(resultTitle.contains("河北")) {
                            typeId = 138;
                        }
                        if(resultTitle.contains("华东") || resultTitle.contains("华南")
                                 || resultTitle.contains("华北")) {
                            typeId = 20;
                        }
                        resultContent=resultContent.replaceAll("生意社", "<p>zz91再生网");
                        String typeName = operate.queryTypeNameByTypeId(typeId);
                        Price price = new Price();
                        price.setTitle(resultTitle);
                        price.setTypeId(typeId);
                        price.setTags(typeName);
                        price.setContent(resultContent);
                        price.setGmtOrder(baseDate);
                        urlmap.put(resultTitle, url);

                        Integer flg = operate.doInsert(price,false);
                       //执行插入并判断插入是否成功
                       if (flg == null){
                           errContentUrl = errContentUrl + "数据存数据库失败！请联系网页抓取开发者！<a href='" + urlmap.get(resultTitle) +"'>"
                                   + "<font color='#FF0000'>" +resultTitle+"</font></a><br />";
                           flagFailure++;
                       }
                    }
                }
            }
            
        } while (false);
        if (flagFailure != 0){
            String content = "生意社塑料(22子类下)未抓取"+flagFailure+"条。分别为：<br />"
                    +errContentUrl + "如果有，请<a href='http://apps2.zz91.com/task/job/definition/doTask.htm?jobName=CaijiSocialBusinessTask&start="+DateUtil.getDate(new Date(), "yyyy-MM-dd")+" 00:00:00'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'生意社塑料("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                            + "生意社</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("content", content);
            MailUtil.getInstance().sendMail(
                    "生意社塑料自动抓取报错", 
                    "zz91.price.caiji.auto@asto.mail", null,
                    null, "zz91", "blank",
                    map, MailUtil.PRIORITY_DEFAULT);
            return false;
        } else {
            String catchTime = DateUtil.toString(new Date(), DATE_FORMAT).substring(11,16);
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'生意社塑料(22子类下)','type':'success','url':'<a href='" + url +"' target='_blank'>"
                            + "生意社</a>','defaultTime':'"+defaultTime+"','catchTime':'"+catchTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            return true;
        }
    }

    @Override
    public boolean clear(Date baseDate) throws Exception {
        return false;
    }

    public static void main(String[] args) {
        
        DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
        CaijiSocialBusinessTask js=new CaijiSocialBusinessTask();
        try {
            js.exec(DateUtil.getDate(new Date(), "yyyy-MM-dd"));
            System.out.println("123");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}