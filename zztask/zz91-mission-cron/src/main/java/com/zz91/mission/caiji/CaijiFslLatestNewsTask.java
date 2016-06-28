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

public class CaijiFslLatestNewsTask implements ZZTask{
    
    final static String PRICE_OPERTION = "price_caiji";
    final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    CaiJiCommonOperate operate = new CaiJiCommonOperate();

    final static Map<String, String> FSLLATESTNEWS_MAP = new HashMap<String, String>();
    static{
        // httpget 使用的编码 不然会有乱码
        FSLLATESTNEWS_MAP.put("charset", "gb2312");
        
        //采集地址 正式
        FSLLATESTNEWS_MAP.put("url", "http://info.plas.hc360.com/list/hydt_list.shtml");
        FSLLATESTNEWS_MAP.put("contentUrl", "http://info.plas.hc360.com");
        FSLLATESTNEWS_MAP.put("listStart", " <td id=\"wezi141\" style=\"padding-right:10px;\">"); // 列表页开头
        FSLLATESTNEWS_MAP.put("listEnd", "<table width=100%><tr><td align=center >"); // 列表页结尾
        
        FSLLATESTNEWS_MAP.put("contentStart", "慧聪塑料网</A>讯：");
        FSLLATESTNEWS_MAP.put("contentEnd", "<span class=\"relRead\">");
        FSLLATESTNEWS_MAP.put("split", "<tr>");
        FSLLATESTNEWS_MAP.put("splitLink", "\"");
        
    }

    @Override
    public boolean init() throws Exception {
        return false;
    }

    @Override
    public boolean exec(Date baseDate) throws Exception {
        String defaultTime = DateUtil.toString(baseDate, DATE_FORMAT).substring(11,16);
        int flagFailure = 0 ;
        Integer typeId = 217;
        String url = "";
        Map<String, String> urlmap = new HashMap<String, String>();
        String errContentUrl = "";
        do {
            String content ="";
            try {
                url = FSLLATESTNEWS_MAP.get("url");
                content = operate.httpClientHtml(url, FSLLATESTNEWS_MAP.get("charset"));
            } catch (HttpException e) {
                content = null;
            } catch (IOException e) {
                content = null;
            }
            if(content==null){
                errContentUrl = errContentUrl + " 抓取来源网站内容失败！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>慧聪塑料网-废塑料最新资讯"+"("+typeId+")"+"</font></a>查看来源网站是否更改地址！<br />"
                                + "如果有，请<a href='http://apps2.zz91.com/task/job/definition/doTask.htm?jobName=CaijiFslLatestNewsTask&start="+DateUtil.getDate(new Date(), "yyyy-MM-dd")+" 00:00:00'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'废塑料最新资讯("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "慧聪塑料网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "废塑料最新资讯自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
            Integer start = content.indexOf(FSLLATESTNEWS_MAP.get("listStart"));
            Integer end = content.indexOf(FSLLATESTNEWS_MAP.get("listEnd"));
            String result = content.substring(start, end);
            String[] str = result.split(FSLLATESTNEWS_MAP.get("split"));

            // 检查日期是否对应今天
            String formatDate = DateUtil.toString(baseDate, "M月d日");
            List<String> list = new ArrayList<String>();
            for (String s : str) {
                if (s.indexOf(formatDate) != -1) {
                    if (list.size() < 10) {
                        list.add(s);
                    }
                }
            }
            if(list.size()<1){
                errContentUrl = errContentUrl + " 抓取失败，来源网站没有数据可抓取！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>慧聪塑料网-废塑料最新资讯"+"("+typeId+")"+"</font></a>查看来源网站<br />"
                                + "如果有，请<a href='http://apps2.zz91.com/task/job/definition/doTask.htm?jobName=CaijiFslLatestNewsTask&start="+DateUtil.getDate(new Date(), "yyyy-MM-dd")+" 00:00:00'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'废塑料最新资讯("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "慧聪塑料网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "废塑料最新资讯自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
            if (list.size() < 10) {
                flagFailure = 10 - list.size();
                errContentUrl = errContentUrl + "来源网站未抓取到："+flagFailure+"条----请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>慧聪塑料网-废塑料最新资讯"+"("+typeId+")"+"</font></a> 查看来源网站<br />-------------------------------------------------"
                        + "<br />";
            }
            String typeName = operate.queryTypeNameByTypeId(typeId);
            
            for(String aStr:list){
                String linkStr = operate.getAlink(aStr);
                if(StringUtils.isEmpty(linkStr)){
                    continue;
                }else{
                    String[] alink = linkStr.split(FSLLATESTNEWS_MAP.get("splitLink"));
                    if(alink.length>0){
                        String resultTitle = operate.changeLetter(Jsoup.clean(linkStr, Whitelist.none()));
                        String resultContent ="";
                        String newUrl = FSLLATESTNEWS_MAP.get("contentUrl") + alink[1]; 
                        resultContent = operate.getContent(FSLLATESTNEWS_MAP, newUrl);
                        if (resultContent.contains("<div id=\'relaword\'>")) {
                            resultContent = resultContent.substring(0, resultContent.indexOf("<div id=\'relaword\'>"));
                        }
                        resultContent = Jsoup.clean(resultContent, Whitelist.none().addTags("p","ul","br","li","table","th","tr","td").addAttributes("td","rowspan"));
                        resultContent = resultContent.replace("慧聪塑料网讯：", "");
                        resultContent="<p>"+resultContent;
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
            String content = "废塑料最新资讯"+"("+typeId+")"+"未抓取"+flagFailure+"条。分别为：<br />"
                    +errContentUrl + "如果有，请<a href='http://apps2.zz91.com/task/job/definition/doTask.htm?jobName=CaijiFslLatestNewsTask&start="+DateUtil.getDate(new Date(), "yyyy-MM-dd")+" 00:00:00'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'废塑料最新资讯("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                            + "慧聪塑料网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("content", content);
            MailUtil.getInstance().sendMail(
                    "废塑料最新资讯自动抓取报错", 
                    "zz91.price.caiji.auto@asto.mail", null,
                    null, "zz91", "blank",
                    map, MailUtil.PRIORITY_DEFAULT);
            return false;
        } else {
            String catchTime = DateUtil.toString(new Date(), DATE_FORMAT).substring(11,16);
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'废塑料最新资讯("+typeId+")','type':'success','url':'<a href='" + url +"' target='_blank'>"
                            + "慧聪塑料网</a>','defaultTime':'"+defaultTime+"','catchTime':'"+catchTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            return true;
        }
    }

    @Override
    public boolean clear(Date baseDate) throws Exception {
        return false;
    }

    public static void main(String[] args) {
        
        DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
        CaijiFslLatestNewsTask js=new CaijiFslLatestNewsTask();
        try {
            js.exec(DateUtil.getDate(new Date(), "yyyy-MM-dd"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
