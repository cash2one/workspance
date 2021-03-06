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

public class CaijiNaturalRubberTask implements ZZTask{
    
    final static String PRICE_OPERTION = "price_caiji";
    final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    CaiJiCommonOperate operate = new CaiJiCommonOperate();

    final static Map<String, String> REVIVESLMARKET_MAP = new HashMap<String, String>();
    static{
        // httpget 使用的编码 不然会有乱码
        REVIVESLMARKET_MAP.put("charset", "gb2312");
        
        //采集地址 正式
        REVIVESLMARKET_MAP.put("url", "http://baojia.qqzssl.com/price_p_cid_bjcid5.99_dqid_.html");
        REVIVESLMARKET_MAP.put("contentUrl", "");
        REVIVESLMARKET_MAP.put("listStart", "<div class=\"wk_l_1 left\">"); // 列表页开头
        REVIVESLMARKET_MAP.put("listEnd", "<div id=\"AspNetPager1\" class=\"anpager\">"); // 列表页结尾
        
        REVIVESLMARKET_MAP.put("contentStart", "<div class=\"xxy_nr\">");
        REVIVESLMARKET_MAP.put("contentEnd", "<div style=\"float:left; width:339px; padding:10px 0px 10px 10px;\">");
        REVIVESLMARKET_MAP.put("split", "<li>");
        REVIVESLMARKET_MAP.put("splitLink", "\"");
        
    }

    @Override
    public boolean init() throws Exception {
        return false;
    }

    @Override
    public boolean exec(Date baseDate) throws Exception {
        String defaultTime = DateUtil.toString(baseDate, DATE_FORMAT).substring(11,16);
        int flagFailure = 0 ;
        Integer typeId = 219;
        String url = "";
        Map<String, String> urlmap = new HashMap<String, String>();
        String errContentUrl = "";
        do {
            String content ="";
            try {
                url = REVIVESLMARKET_MAP.get("url");
                content = operate.httpClientHtml(url, REVIVESLMARKET_MAP.get("charset"));
            } catch (HttpException e) {
                content = null;
            } catch (IOException e) {
                content = null;
            }
            if(content==null){
                errContentUrl = errContentUrl + " 抓取来源网站内容失败！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>全球塑料再生网-天然橡胶"+"("+typeId+")"+"</font></a>查看来源网站是否更改地址！<br />"
                                + "如果有，请<a href='http://apps2.zz91.com/task/job/definition/doTask.htm?jobName=CaijiNaturalRubberTask&start="+DateUtil.getDate(new Date(), "yyyy-MM-dd")+" 00:00:00'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'天然橡胶("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "全球塑料再生网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "天然橡胶自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
            Integer start = content.indexOf(REVIVESLMARKET_MAP.get("listStart"));
            Integer end = content.indexOf(REVIVESLMARKET_MAP.get("listEnd"));
            String result = content.substring(start, end);
            String[] str = result.split(REVIVESLMARKET_MAP.get("split"));

            // 检查日期是否对应今天
            String formatDate = DateUtil.toString(baseDate, "MM-dd");
            List<String> list = new ArrayList<String>();
            for (String s : str) {
                if (s.indexOf(formatDate) != -1) {
                    list.add(s);
                }
            }
            if(list.size()<1){
                errContentUrl = errContentUrl + " 抓取失败，来源网站没有数据可抓取！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>全球塑料再生网-天然橡胶"+"("+typeId+")"+"</font></a>查看来源网站<br />"
                                + "如果有，请<a href='http://apps2.zz91.com/task/job/definition/doTask.htm?jobName=CaijiNaturalRubberTask&start="+DateUtil.getDate(new Date(), "yyyy-MM-dd")+" 00:00:00'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'天然橡胶("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "全球塑料再生网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "天然橡胶自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
            if (list.size() < 3) {
                flagFailure = 3 - list.size();
                errContentUrl = errContentUrl + "来源网站未抓取到："+flagFailure+"条----请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>全球塑料再生网-天然橡胶"+"("+typeId+")"+"</font></a> 查看来源网站<br />-------------------------------------------------"
                        + "<br />";
            }
            String typeName = operate.queryTypeNameByTypeId(typeId);
            
            for(String aStr:list){
                String linkStr = operate.getAlink(aStr);
                if(StringUtils.isEmpty(linkStr)){
                    continue;
                }else{
                    String[] alink = linkStr.split(REVIVESLMARKET_MAP.get("splitLink"));
                    if(alink.length>0){
                        String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
                        String resultContent ="";
                        String newUrl = REVIVESLMARKET_MAP.get("contentUrl") + alink[1]; 
                        resultContent = operate.getContent(REVIVESLMARKET_MAP, newUrl);
                        resultContent = Jsoup.clean(resultContent, Whitelist.none().addTags("p","ul","br","li","table","th","tr","td").addAttributes("td","rowspan"));
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
            String content = "天然橡胶"+"("+typeId+")"+"未抓取"+flagFailure+"条。分别为：<br />"
                    +errContentUrl + "如果有，请<a href='http://apps2.zz91.com/task/job/definition/doTask.htm?jobName=CaijiNaturalRubberTask&start="+DateUtil.getDate(new Date(), "yyyy-MM-dd")+" 00:00:00'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'天然橡胶("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                            + "全球塑料再生网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("content", content);
            MailUtil.getInstance().sendMail(
                    "天然橡胶自动抓取报错", 
                    "zz91.price.caiji.auto@asto.mail", null,
                    null, "zz91", "blank",
                    map, MailUtil.PRIORITY_DEFAULT);
            return false;
        } else {
            String catchTime = DateUtil.toString(new Date(), DATE_FORMAT).substring(11,16);
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'天然橡胶("+typeId+")','type':'success','url':'<a href='" + url +"' target='_blank'>"
                            + "全球塑料再生网</a>','defaultTime':'"+defaultTime+"','catchTime':'"+catchTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            return true;
        }
    }

    @Override
    public boolean clear(Date baseDate) throws Exception {
        return false;
    }

    public static void main(String[] args) {
        
        DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
        CaijiNaturalRubberTask js=new CaijiNaturalRubberTask();
        try {
            js.exec(DateUtil.getDate(new Date(), "yyyy-MM-dd"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
