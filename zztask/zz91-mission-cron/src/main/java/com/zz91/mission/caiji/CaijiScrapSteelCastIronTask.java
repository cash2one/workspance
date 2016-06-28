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
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.log.LogUtil;
import com.zz91.util.mail.MailUtil;

public class CaijiScrapSteelCastIronTask implements ZZTask{
    
    final static String PRICE_OPERTION = "price_caiji";
    final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    CaiJiCommonOperate operate = new CaiJiCommonOperate();

    final static Map<String, String> ZHIJIN_MAP = new HashMap<String, String>();
    static{
        // httpget 使用的编码 不然会有乱码
        ZHIJIN_MAP.put("charset", "utf-8");
        ZHIJIN_MAP.put("web", "http://www.zhijinsteel.com");
        ZHIJIN_MAP.put("url", "http://www.zhijinsteel.com/gangchang/cghz/index.html");// 采集地址
        ZHIJIN_MAP.put("279", "");

        ZHIJIN_MAP.put("listStart", "<ul class=\"list lh24 f14\">"); // 列表页开头
        ZHIJIN_MAP.put("listEnd", "上一页"); // 列表页结尾

        ZHIJIN_MAP.put("split", "<li>");
        
        ZHIJIN_MAP.put("contentStart", "<div class=\"content\" id='content'>");
        ZHIJIN_MAP.put("contentEnd", "</table>");

    }
   
    @Override
    public boolean init() throws Exception {
        return false;
    }
    @Override
    public boolean exec(Date baseDate) throws Exception {
        String defaultTime = DateUtil.toString(baseDate, DATE_FORMAT).substring(11,16);
        int flagFailure = 0 ;
        Integer typeId = 279;
        String url = "";
        Map<String, String> urlmap = new HashMap<String, String>();
        String errContentUrl = "";
        do {
            String content = "";
            try {
                url = ZHIJIN_MAP.get("url");
                content = operate.httpClientHtml(url,ZHIJIN_MAP.get("charset"));
            } catch (HttpException e) {
                content = null;
            } catch (IOException e) {
                content = null;
            }
            if(content==null){
                errContentUrl = errContentUrl + " 抓取来源网站内容失败！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>至金钢铁-废钢/生铁"+"("+typeId+")"+"</font></a>查看来源网站是否更改地址！<br />"
                                + "如果有，请<<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_zhijin.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'废钢/生铁("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "至金钢铁</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "废钢/生铁自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
            Integer start = content.indexOf(ZHIJIN_MAP.get("listStart"));
            Integer end = content.indexOf(ZHIJIN_MAP.get("listEnd"));
            String result = operate.subContent(content, start, end);
            // 分解list 为多个元素
            String[] str = result.split(ZHIJIN_MAP.get("split"));

            // 检查日期是否对应今天
            String format = DateUtil.toString(baseDate, "MM月dd日");
            String formatZero = DateUtil.toString(baseDate, "M月d日");
            List<String> list = new ArrayList<String>();

            for (String s : str) {
                if (s.indexOf(format) != -1 || s.indexOf(formatZero) != -1) {
                    if(s.indexOf("生铁")!=-1||s.indexOf("废钢")!=-1){
                        list.add(s);
                    }
                }
            }
            if(list.size()<1){
                errContentUrl = errContentUrl + " 抓取失败，来源网站没有数据可抓取！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>至金钢铁-废钢/生铁"+"("+typeId+")"+"</font></a>查看来源网站<br />"
                                + "如果有，请<<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_zhijin.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'废钢/生铁("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "至金钢铁</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "废钢/生铁自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }

            if (list.size() < 2) {
                flagFailure = 2 - list.size();
                errContentUrl = errContentUrl + "来源网站未抓取到："+flagFailure+"条----请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>至金钢铁-废钢/生铁"+"("+typeId+")"+"</font></a> 查看来源网站<br />-------------------------------------------------"
                        + "<br />";
            }
            String typeName = operate.queryTypeNameByTypeId(typeId);

            for (String aStr : list) {
                String linkStr = operate.getAlink(aStr);
                if (StringUtils.isEmpty(linkStr)) {
                    continue;
                } else {
                    String[] alink = linkStr.split("\"");
                    if (alink.length > 0) {
                        String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
                        resultTitle = resultTitle.replace("格汇总", "");
                        resultTitle = resultTitle.replace("全国", "");
                        String resultContent = "";
                        resultContent = operate.getContent(ZHIJIN_MAP, alink[1]);
                        if (StringUtils.isEmpty(resultContent)) {
                            continue;
                        }
                        // 获取内容
                        resultContent = Jsoup.clean(resultContent, Whitelist.none().addTags("p", "table", "th", "tr", "td","b").addAttributes("td", "rowspan"));
                        

                        Price price = new Price();
                        price.setTitle(resultTitle);
                        price.setTypeId(typeId);
                        price.setTags(typeName);
                        price.setContent(resultContent);
                        price.setGmtOrder(baseDate);
                        urlmap.put(resultTitle, url);
                        
                        Integer flg = 0;
                        
                        if(resultTitle.indexOf("废钢")!=-1){
                            flg = operate.doInsert(price, true);
                        }else{
                            flg = operate.doInsert(price, false);
                        }
                      //执行插入并判断插入是否成功
                       /* if (flg != null && flg > 0) {
                            LogUtil.getInstance().log(
                                    "caiji-auto", PRICE_OPERTION, null, "{'title':'"+resultTitle.substring(5, resultTitle.length())+"("+typeId+")','type':'success','url':'<a href='" + urlmap.get(resultTitle) +"'>"
                                            + "至金钢铁</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                        } else */if (flg == null){
                            errContentUrl = errContentUrl + "数据存数据库失败！请联系网页抓取开发者！<a href='" + urlmap.get(resultTitle) +"'>"
                                    + "<font color='#FF0000'>" +resultTitle+"</font></a><br />";
                            /*LogUtil.getInstance().log(
                                    "caiji-auto", PRICE_OPERTION, null, "{'title':'"+resultTitle.substring(5, resultTitle.length())+"("+typeId+")','type':'failure','url':'<a href='" + urlmap.get(resultTitle) +"'>"
                                            + "至金钢铁</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");*/
                            flagFailure++;
                        }
                    }
                }
            }
        } while (false);
        if (flagFailure != 0){
            String content = "废钢/生铁"+"("+typeId+")"+"未抓取"+flagFailure+"条。分别为：<br />"
                    +errContentUrl + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_zhijin.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'废钢/生铁("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                            + "至金钢铁</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("content", content);
            MailUtil.getInstance().sendMail(
                    "废钢/生铁自动抓取报错", 
                    "zz91.price.caiji.auto@asto.mail", null,
                    null, "zz91", "blank",
                    map, MailUtil.PRIORITY_DEFAULT);
            return false;
        } else {
            String catchTime = DateUtil.toString(new Date(), DATE_FORMAT).substring(11,16);
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'废钢/生铁("+typeId+")','type':'success','url':'<a href='" + url +"' target='_blank'>"
                            + "至金钢铁</a>','defaultTime':'"+defaultTime+"','catchTime':'"+catchTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            return true;
        }
    }
    @Override
    public boolean clear(Date baseDate) throws Exception {
        return false;
    }
    
    public static void main(String[] args) {
        
        DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
        CaijiScrapSteelCastIronTask js=new CaijiScrapSteelCastIronTask();
        try {
            js.exec(DateUtil.getDate("2015-06-25", "yyyy-MM-dd"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
