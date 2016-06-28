package com.zz91.mission.caiji;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class CaijiSlMarketTask implements ZZTask{
    
    final static String PRICE_OPERTION = "price_caiji";
    final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    CaiJiCommonOperate operate = new CaiJiCommonOperate();

    final static Map<String, String> PLASTIC_MAP = new HashMap<String, String>();
    static{
        // httpget 使用的编码 不然会有乱码
        PLASTIC_MAP.put("charset", "utf-8");
        PLASTIC_MAP.put("url", "http://www.ex-cp.com/plastic/list-37.html");
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
        do {
            Integer typeId = 0;
            // 获取list 列表
            String content = "";
            content = HttpUtils.getInstance().httpGet(PLASTIC_MAP.get("url"), PLASTIC_MAP.get("charset"));
            if(content==null){
                  errContentUrl = errContentUrl + " 抓取来源网站内容失败！请点击<a href='" +  PLASTIC_MAP.get("url") +"'>"
                            + "<font color='#FF0000'>浙江塑料城网上交易市场-塑料市场价(60下子类)</font></a>查看来源网站是否更改地址！<br />";
                    LogUtil.getInstance().log(
                            "caiji-auto", PRICE_OPERTION, null, "{'title':'塑料市场价(60下子类)','type':'failure','url':'<a href='" + PLASTIC_MAP.get("url") +"' target='_blank'>"
                                    + "浙江塑料城网上交易市场</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                    flagFailure++;
                    continue;
                }
                Integer start = content.indexOf("<div class=\"gdjg_catlist\">");
                Integer end = content.indexOf("<div class=\"m_r f_l\">");
                String result = content.substring(start, end);
                String [] str = result.split("<li>");
        
                // 检查日期是否对应今天
                String format = DateUtil.toString(baseDate, "M月d日");
                String formatDate = DateUtil.toString(baseDate, "MM月dd日");
                List<String> list = new ArrayList<String>();
                for (String s:str) {
                    if(s.indexOf(format)!=-1){
                    	list.add(s);
                    }
                }
        
                if(list.size()<1){
                    errContentUrl = errContentUrl + " 抓取失败，来源网站没有数据可抓取！请点击<a href='" +  PLASTIC_MAP.get("url") +"'>"
                            + "<font color='#FF0000'>浙江塑料城网上交易市场-塑料市场价(60下子类)</font></a>查看来源网站<br />";
                    LogUtil.getInstance().log(
                            "caiji-auto", PRICE_OPERTION, null, "{'title':'塑料市场价(60下子类)','type':'failure','url':'<a href='" + PLASTIC_MAP.get("url") +"' target='_blank'>"
                                    + "浙江塑料城网上交易市场</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                    flagFailure++;
                    continue;
                }

                for (String link:list) {
                    Pattern pattern = Pattern.compile("<a(?:\\s+.+?)*?\\s+href=\"([^\"]*?)\".+>(.*?)</a>");
                    Matcher matcher = pattern.matcher(link);
                    String linkStr = "";
                    if(matcher.find()){
                        linkStr = matcher.group();
                    }
                    if(StringUtils.isEmpty(linkStr)){
                        break;
                    }
                    
                    String[] alink = linkStr.split("\"");
                    if(alink.length>0){
                        String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
                        int tempStart = resultTitle.indexOf("【");
                        int tempEnd = resultTitle.indexOf("】");
                        resultTitle = formatDate + resultTitle.substring(tempStart+1, tempEnd) + "塑料市场价";
                        String typeName = "";
                        if(resultTitle.contains("东莞")) {
                            typeId = 111;
                        } else if(resultTitle.contains("北京")) {
                            typeId = 112;
                        } else if(resultTitle.contains("广州")) {
                            typeId = 113;
                        } else if(resultTitle.contains("上海")) {
                            typeId = 115;
                        } else if(resultTitle.contains("汕头")) {
                            typeId = 118;
                        } else if(resultTitle.contains("杭州")) {
                            typeId = 119;
                        } else if(resultTitle.contains("顺德")) {
                            typeId = 120;
                        } else if(resultTitle.contains("临沂")) {
                            typeId = 121;
                        } else if(resultTitle.contains("齐鲁化工城")) {
                            typeId = 126;;
                        }
                        typeName = operate.queryTypeNameByTypeId(typeId);
                        String resultLink = alink[1];
                        String resultContent ="";
                        resultContent = HttpUtils.getInstance().httpGet(resultLink, PLASTIC_MAP.get("charset"));
                        Integer cStart = resultContent.indexOf("<div class=\"content\" id=\"article\">");
                        Integer cEnd = resultContent.indexOf("<div class=\"b10 c_b\">&nbsp;</div>");
                        resultContent = resultContent.substring(cStart, cEnd);
                        resultContent = Jsoup.clean(resultContent, Whitelist.none().addTags("div","p","ul","li","br","table","th","tr","td").addAttributes("td","rowspan"));
                      
                        Price price = new Price();
                        price.setTitle(resultTitle);
                        price.setTypeId(typeId);
                        price.setTags(typeName);
                        price.setContent(resultContent);
                        price.setGmtOrder(baseDate);
                        urlmap.put(resultTitle, PLASTIC_MAP.get("url"));

                        Integer flg = operate.doInsert(price,false);
                       //执行插入并判断插入是否成功
                       if (flg != null && flg > 0) {
                           String catchTime = DateUtil.toString(new Date(), DATE_FORMAT).substring(11,16);
                           LogUtil.getInstance().log(
                                   "caiji-auto", PRICE_OPERTION, null, "{'title':'塑料市场价(60下子类)','type':'success','url':'<a href='" + PLASTIC_MAP.get("url") +"' target='_blank'>"
                                           + "浙江塑料城网上交易市场</a>','defaultTime':'"+defaultTime+"','catchTime':'"+catchTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                       } else if (flg == null){
                           errContentUrl = errContentUrl + "数据存数据库失败！请联系网页抓取开发者！<a href='" + urlmap.get(resultTitle) +"'>"
                                   + "<font color='#FF0000'>" +resultTitle+"</font></a><br />";
                           LogUtil.getInstance().log(
                                   "caiji-auto", PRICE_OPERTION, null, "{'title':'塑料市场价(60下子类)','type':'failure','url':'<a href='" + PLASTIC_MAP.get("url") +"' target='_blank'>"
                                           + "浙江塑料城网上交易市场</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                           flagFailure++;
                       }
                    }
           
                }
        } while (false);
        if (flagFailure != 0){
            String content = "塑料市场价(60下子类)未抓取"+flagFailure+"条。分别为：<br />"
                    +errContentUrl + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_lzzz_plastic.htm'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("content", content);
            MailUtil.getInstance().sendMail(
                    "塑料市场价自动抓取报错", 
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
        CaijiSlMarketTask js=new CaijiSlMarketTask();
        try {
            js.exec(DateUtil.getDate("2015-09-28", "yyyy-MM-dd"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
