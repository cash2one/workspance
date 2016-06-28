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

public class CaijiInterFeiJsSpotTask implements ZZTask{
    
    final static String PRICE_OPERTION = "price_caiji";
    final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    CaiJiCommonOperate operate = new CaiJiCommonOperate();

    final static Map<String, String> CNAL_METAL_MAP = new HashMap<String, String>();
    static{
        // httpget 使用的编码 不然会有乱码
        CNAL_METAL_MAP.put("charset", "utf-8");
        //CNAL_METAL_MAP.put("url", "http://info.1688.com");// 采集地址
        CNAL_METAL_MAP.put("216", "http://market.cnal.com/other/index.shtml");
        
        CNAL_METAL_MAP.put("listStart", "<div id=\"marketlistbody\">"); // 列表页开头
        CNAL_METAL_MAP.put("listStart1", "<div class=\"grid-content\">");
        CNAL_METAL_MAP.put("listEnd", "<div class=\"epages\">"); // 列表页结尾
        CNAL_METAL_MAP.put("listEnd1", "<div class=\"page-new\">");
        
        CNAL_METAL_MAP.put("split", "</li>");

        CNAL_METAL_MAP.put("contentStart", "<div class=\"marketcontent\">");
        CNAL_METAL_MAP.put("contentEnd", "<p class=\"tag\">");
    }

    @Override
    public boolean init() throws Exception {
        return false;
    }

    @Override
    public boolean exec(Date baseDate) throws Exception {
        String defaultTime = DateUtil.toString(baseDate, DATE_FORMAT).substring(11,16);
        int flagFailure = 0 ;
        Integer typeId = 216;
        Map<String, String> urlmap = new HashMap<String, String>();
        String errContentUrl = "";
        String url = "";
        do {
            String content = "";
            url = CNAL_METAL_MAP.get("" + typeId);
            content = HttpUtils.getInstance().httpGet(url,
			        CNAL_METAL_MAP.get("charset"),20000,20000);
            if(content==null){
                errContentUrl = errContentUrl + " 抓取来源网站内容失败！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>中铝网-国际废旧金属现货"+"("+typeId+")"+"</font></a>查看来源网站是否更改地址！<br />"
                                + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_cnal_metal.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'国际废旧金属现货("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "中铝网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "国际废旧金属现货自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
            Integer start = content.indexOf(CNAL_METAL_MAP.get("listStart"));
            if (start == -1) {
                start = content.indexOf(CNAL_METAL_MAP.get("listStart1"));
            }
            Integer end = content.indexOf(CNAL_METAL_MAP.get("listEnd"));
            if (end == -1) {
                end = content.indexOf(CNAL_METAL_MAP.get("listEnd1"));
            }
            String result = operate.subContent(content, start, end);
            // 分解list 为多个元素
            String[] str = result.split(CNAL_METAL_MAP.get("split"));

            // 检查日期是否对应今天
            String formatDate = DateUtil.toString(baseDate, "yyyy-MM-dd");
            String format = DateUtil.toString(baseDate, "MM月dd日");
            List<String> list = new ArrayList<String>();

            for (String s : str) {
                if (s.indexOf(formatDate) != -1 ) {
                        if (s.indexOf("国际废旧金属现货行情") != -1) {
                            list.add(s);
                        }
                }
            }
            if(list.size()<1){
                errContentUrl = errContentUrl + " 抓取失败，来源网站没有数据可抓取！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>中铝网-国际废旧金属现货"+"("+typeId+")"+"</font></a>查看来源网站<br />"
                                + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_cnal_metal.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'国际废旧金属现货("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "中铝网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "国际废旧金属现货自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
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
                            resultTitle = format + "国际废旧金属现货行情";
                            String resultContent = "";
                            String link = "";
                            for (int i = 0 ; i < alink.length; i++) {
                                if (alink[i].contains("http")) {
                                    link = alink[i];
                                    break;
                                }
                            }
                            resultContent = operate.getContent(CNAL_METAL_MAP, link);
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

                            urlmap.put(resultTitle, url);

                            Integer flg = operate.doInsert(price,false);
                           //执行插入并判断插入是否成功
                           /*if (flg != null && flg > 0) {
                               LogUtil.getInstance().log(
                                       "caiji-auto", PRICE_OPERTION, null, "{'title':'"+resultTitle.substring(5, resultTitle.length())+"("+typeId+")','type':'success','url':'<a href='" + urlmap.get(resultTitle) +"'>"
                                               + "中铝网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                           } else*/ if (flg == null){
                               errContentUrl = errContentUrl + "数据存数据库失败！请联系网页抓取开发者！<a href='" + urlmap.get(resultTitle) +"'>"
                                       + "<font color='#FF0000'>" +resultTitle+"</font></a><br />";
                              /* LogUtil.getInstance().log(
                                       "caiji-auto", PRICE_OPERTION, null, "{'title':'"+resultTitle.substring(5, resultTitle.length())+"("+typeId+")','type':'failure','url':'<a href='" + urlmap.get(resultTitle) +"'>"
                                               + "中铝网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");*/
                               flagFailure++;
                           }
                    }
                }
            }
        
        } while (false);
        if (flagFailure != 0){
            String content = "国际废旧金属现货"+"("+typeId+")"+"未抓取"+flagFailure+"条。分别为：<br />"
                    +errContentUrl + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_cnal_metal.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'国际废旧金属现货("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                            + "中铝网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("content", content);
            MailUtil.getInstance().sendMail(
                    "国际废旧金属现货自动抓取报错", 
                    "zz91.price.caiji.auto@asto.mail", null,
                    null, "zz91", "blank",
                    map, MailUtil.PRIORITY_DEFAULT);
            return false;
        } else {
            String catchTime = DateUtil.toString(new Date(), DATE_FORMAT).substring(11,16);
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'国际废旧金属现货("+typeId+")','type':'success','url':'<a href='" + url +"' target='_blank'>"
                            + "中铝网</a>','defaultTime':'"+defaultTime+"','catchTime':'"+catchTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            return true;
        }
    }

    @Override
    public boolean clear(Date baseDate) throws Exception {
        return false;
    }

    public static void main(String[] args) {
        
        DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
        CaijiInterFeiJsSpotTask js=new CaijiInterFeiJsSpotTask();
        try {
            js.exec(DateUtil.getDate("2013-11-4", "yyyy-MM-dd"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
