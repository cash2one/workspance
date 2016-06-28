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

public class CaijiPagerTask implements ZZTask{
    
    final static String PRICE_OPERTION = "price_caiji";
    final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    CaiJiCommonOperate operate = new CaiJiCommonOperate();

    final static Map<String, String> PAGER_MAP = new HashMap<String, String>();
    static{
        // httpget 使用的编码 不然会有乱码
        PAGER_MAP.put("charset", "gb2312");
        PAGER_MAP.put("urlMor", "http://www.chinapaper.net/paperdata/list.php?catid=26," +
                "http://www.chinapaper.net/paperdata/list.php?catid=29,"+"http://www.chinapaper.net/paperdata/list.php?catid=32");// 采集地址
        //PAGER_MAP.put("urlAft", "http://www.chinapaper.net/paperdata/list.php?catid=32");// 采集地址
        
        PAGER_MAP.put("listStart", "<div class=\"main_left\">"); // 列表页开头
        PAGER_MAP.put("listEnd", "<ul class=\"list_b\">"); // 列表页结尾

        PAGER_MAP.put("split", "<li class=\"main_c\">");

        PAGER_MAP.put("contentStart", "<div id=\"content\">");
        PAGER_MAP.put("contentEnd", "<div class=\"b10 c_b\">");
    }

    @Override
    public boolean init() throws Exception {
        return false;
    }

    @Override
    public boolean exec(Date baseDate) throws Exception {
        String defaultTime = DateUtil.toString(baseDate, DATE_FORMAT).substring(11,16);
        int flagFailure = 0 ;
        Integer typeId = 231;
        Map<String, String> urlmap = new HashMap<String, String>();
        String errContentUrl = "";
        do {
            String content = "";
            String typeValue = PAGER_MAP.get("urlMor");
            String[] linkArray = typeValue.split(",");
            for (String url : linkArray) {
                try {
                    content = operate.httpClientHtml(url, PAGER_MAP.get("charset"));
                } catch (HttpException e) {
                    content = null;
                } catch (IOException e) {
                    content = null;
                }
                if(content==null){
                    errContentUrl = errContentUrl + " 抓取来源网站内容失败！请点击<a href='" +  url +"'>"
                            + "<font color='#FF0000'>中国纸业网-各种用纸"+"("+typeId+")"+"</font></a>查看来源网站是否更改地址！<br />";
                    LogUtil.getInstance().log(
                            "caiji-auto", PRICE_OPERTION, null, "{'title':'各种用纸("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                    + "中国纸业网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                    flagFailure++;
                    continue;
                }
                Integer start = content.indexOf(PAGER_MAP.get("listStart"));
                Integer end = content.indexOf(PAGER_MAP.get("listEnd"));
                String result = operate.subContent(content, start, end);
                if (StringUtils.isEmpty(result)) {
                    continue;
                }
                // 分解list 为多个元素
                String[] str = result.split(PAGER_MAP.get("split"));

                // 检查日期是否对应今天
                String format = DateUtil.toString(baseDate, "yyyy-MM-dd");
                List<String> list = new ArrayList<String>();

                for (String s : str) {
                    if (s.indexOf(format) != -1 ) {
                       list.add(s);
                    }
                }
                if(list.size()<1){
                    errContentUrl = errContentUrl + " 抓取失败，来源网站没有数据可抓取！请点击<a href='" +  url +"'>"
                            + "<font color='#FF0000'>中国纸业网-各种用纸"+"("+typeId+")"+"</font></a>查看来源网站<br />";
                    LogUtil.getInstance().log(
                            "caiji-auto", PRICE_OPERTION, null, "{'title':'各种用纸("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                    + "中国纸业网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                    flagFailure++;
                    continue;
                }

                String typeName = operate.queryTypeNameByTypeId(typeId);

                for (String aStr : list) {
                    String linkStr = operate.getAlink(aStr);
                    if (StringUtils.isEmpty(linkStr)) {
                        continue;
                    } else {
                        String[] alink = linkStr.split("\"");
                        if (alink.length > 0) {
                            String resultTitle = Jsoup.clean(linkStr,Whitelist.none());
                            String resultContent = "";
                            resultContent = operate.getContent(PAGER_MAP, alink[1]);
                            if (StringUtils.isEmpty(resultContent)) {
                                continue;
                            }
                            resultContent = resultContent.replaceAll("&nbsp;", "");
                            resultContent = Jsoup.clean(
                                    resultContent,Whitelist
                                            .none()
                                            .addTags("p", "table", "th", "tr","td","b","br")
                                            .addAttributes("td", "rowspan","colspan"));

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
                                       "caiji-auto", PRICE_OPERTION, null, "{'title':'各种用纸("+typeId+")','type':'success','url':'<a href='" + url +"' target='_blank'>"
                                               + "中国纸业网</a>','defaultTime':'"+defaultTime+"','catchTime':'"+catchTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                           } else if (flg == null){
                               errContentUrl = errContentUrl + "数据存数据库失败！请联系网页抓取开发者！<a href='" + urlmap.get(resultTitle) +"'>"
                                       + "<font color='#FF0000'>" +resultTitle+"</font></a><br />";
                               LogUtil.getInstance().log(
                                       "caiji-auto", PRICE_OPERTION, null, "{'title':'各种用纸("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                               + "中国纸业网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                               flagFailure++;
                           }
                        }
                    }
                }
            }
        } while (false);
        if (flagFailure != 0){
            String content = "各种用纸"+"("+typeId+")"+"未抓取"+flagFailure+"条。分别为：<br />"
                    +errContentUrl + "如果有，请<a href='http://apps2.zz91.com/task/job/definition/doTask.htm?jobName=CaijiPagerTask&start="+DateUtil.getDate(new Date(), "yyyy-MM-dd")+" 00:00:00'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("content", content);
            MailUtil.getInstance().sendMail(
                    "各种用纸自动抓取报错", 
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
        CaijiPagerTask js=new CaijiPagerTask();
        try {
            js.exec(DateUtil.getDate("2014-11-17", "yyyy-MM-dd"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}