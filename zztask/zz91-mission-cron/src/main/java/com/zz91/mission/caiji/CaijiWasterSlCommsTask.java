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
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.log.LogUtil;
import com.zz91.util.mail.MailUtil;

public class CaijiWasterSlCommsTask implements ZZTask{
    
    final static String PRICE_OPERTION = "price_caiji";
    final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    CaiJiCommonOperate operate = new CaiJiCommonOperate();

    final static Map<String, String> ZS_MAP = new HashMap<String, String>();
    static{
        // httpget 使用的编码 不然会有乱码
        ZS_MAP.put("charset", "utf-8");
        ZS_MAP.put("url", "http://www.ex-cp.com/plastic/list-57.html");// 采集地址
        ZS_MAP.put("listStart", "<div class=\"catlist\">"); // 列表页开头
        ZS_MAP.put("listEnd", "<div class=\"pages\">"); // 列表页结尾

        ZS_MAP.put("split", "<li");

        ZS_MAP.put("contentStart", "<div class=\"content\" id=\"article\">");
        ZS_MAP.put("contentEnd", "<div class=\"b10 c_b\">&nbsp;</div>");

    }

    @Override
    public boolean init() throws Exception {
        return false;
    }

    @Override
    public boolean exec(Date baseDate) throws Exception {
        String defaultTime = DateUtil.toString(baseDate, DATE_FORMAT).substring(11,16);
        int flagFailure = 0 ;
        String time = DateUtil.toString(baseDate, DATE_FORMAT).substring(11, 13);
        Integer typeId = 217;
        Map<String, String> urlmap = new HashMap<String, String>();
        String errContentUrl = "";
        do {
            String content = "";
            content = HttpUtils.getInstance().httpGet(ZS_MAP.get("url"),
			        ZS_MAP.get("charset"),20000,20000);
            if(content==null){
                errContentUrl = errContentUrl + " 抓取来源网站内容失败！请点击<a href='" +  ZS_MAP.get("url") +"'>"
                        + "<font color='#FF0000'>浙江塑料城网上交易市场-废塑料评论"+"("+typeId+")"+"</font></a>查看来源网站是否更改地址！<br />"
                                + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_lzzz.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'废塑料评论("+typeId+")','type':'failure','url':'<a href='" + ZS_MAP.get("url") +"' target='_blank'>"
                                + "浙江塑料城网上交易市场</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "废塑料评论自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
            Integer start = content.indexOf(ZS_MAP.get("listStart"));
            Integer end = content.indexOf(ZS_MAP.get("listEnd"));
            String result = operate.subContent(content, start, end);
            if (StringUtils.isEmpty(result)) {
                continue;
            }
            // 分解list 为多个元素
            String[] str = result.split(ZS_MAP.get("split"));

            // 检查日期是否对应今天
            String formatDate = DateUtil.toString(baseDate, "yyyy-MM-dd");
            String format = DateUtil.toString(baseDate, "MM月dd日");
            String formatZero = DateUtil.toString(baseDate, "M月d日");
            List<String> list = new ArrayList<String>();

            for (String s : str) {
                if (s.indexOf(formatDate) != -1) {
                    if (Integer.valueOf(time) <= 15) {
                        if (s.indexOf("ABS/PS市场概况") != -1
                                || s.indexOf("PE市场概况") != -1
                                || s.indexOf("PP市场概况") != -1) {
                            list.add(s);
                        }
                    } else {
                        if (s.indexOf("【PP】") != -1
                                || s.indexOf("【PVC】") != -1
                                || s.indexOf("【HDPE】") != -1
                                || s.indexOf("【LLDPE】") != -1
                                || s.indexOf("【ABS/PS】") != -1) {
                            list.add(s);
                        }
                    }
                }
            }
            if(list.size()<1){
                errContentUrl = errContentUrl + " 抓取失败，来源网站没有数据可抓取！请点击<a href='" +  ZS_MAP.get("url") +"'>"
                        + "<font color='#FF0000'>浙江塑料城网上交易市场-废塑料评论"+"("+typeId+")"+"</font></a>查看来源网站<br />"
                                + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_lzzz.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'废塑料评论("+typeId+")','type':'failure','url':'<a href='" + ZS_MAP.get("url") +"' target='_blank'>"
                                + "浙江塑料城网上交易市场</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "废塑料评论自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }


            if (Integer.valueOf(time) <= 15) {
                if (list.size() < 3) {
                    flagFailure = 3 - list.size();
                    errContentUrl = errContentUrl + "来源网站未抓取到："+flagFailure+"条----请点击<a href='" +  ZS_MAP.get("url") +"'>"
                            + "<font color='#FF0000'>浙江塑料城网上交易市场-废塑料评论"+"("+typeId+")"+"</font></a> 查看来源网站<br />-------------------------------------------------"
                            + "<br />";
                }
            } else {
                if (list.size() < 5) {
                    flagFailure = 5 - list.size();
                    errContentUrl = errContentUrl + "来源网站未抓取到："+flagFailure+"条----请点击<a href='" +  ZS_MAP.get("url") +"'>"
                            + "<font color='#FF0000'>浙江塑料城网上交易市场-废塑料评论"+"("+typeId+")"+"</font></a> 查看来源网站<br />-------------------------------------------------"
                            + "<br />";
                }
            }
            String typeName = operate.queryTypeNameByTypeId(typeId);

            for (String aStr : list) {
                String linkStr = operate.getAlink(aStr);
                if (StringUtils.isEmpty(linkStr)) {
                    continue;
                } else {
                    String[] alink = linkStr.split("\"");
                    if (alink.length > 0) {
                        String resultTitle = "";
                        resultTitle = Jsoup.clean(linkStr,Whitelist.none());
                        resultTitle = resultTitle.replace("各地", "全国各地").replace(formatZero, format);
                        String resultContent = "";
                        resultContent = operate.getContent(ZS_MAP, alink[1]);
                        if (StringUtils.isEmpty(resultContent)) {
                            continue;
                        }
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
                        urlmap.put(resultTitle, ZS_MAP.get("url"));

                        Integer flg = operate.doInsert(price,false);
                        if (flg == null){
                           errContentUrl = errContentUrl + "数据存数据库失败！请联系网页抓取开发者！<a href='" + urlmap.get(resultTitle) +"'>"
                                   + "<font color='#FF0000'>" +resultTitle+"</font></a><br />";
                           flagFailure++;
                       }
                    }
                }
            }
        } while (false);
        if (flagFailure != 0) {
            String content = "废塑料评论"+"("+typeId+")"+"未抓取"+flagFailure+"条。分别为：<br />"
                    +errContentUrl + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_lzzz.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'废塑料评论("+typeId+")','type':'failure','url':'<a href='" + ZS_MAP.get("url") +"' target='_blank'>"
                            + "浙江塑料城网上交易市场</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("content", content);
            MailUtil.getInstance().sendMail(
                    "废塑料评论自动抓取报错", 
                    "zz91.price.caiji.auto@asto.mail", null,
                    null, "zz91", "blank",
                    map, MailUtil.PRIORITY_DEFAULT);
            return false;
        } else {
            String catchTime = DateUtil.toString(new Date(), DATE_FORMAT).substring(11,16);
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'废塑料评论("+typeId+")','type':'success','url':'<a href='" + ZS_MAP.get("url") +"' target='_blank'>"
                            + "浙江塑料城网上交易市场</a>','defaultTime':'"+defaultTime+"','catchTime':'"+catchTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            return true;
        }
    }

    @Override
    public boolean clear(Date baseDate) throws Exception {
        return false;
    }

    public static void main(String[] args) {
        
        DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
        CaijiWasterSlCommsTask js=new CaijiWasterSlCommsTask();
        try {
            js.exec(DateUtil.getDate("2015-09-28 16:00:00", "yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
