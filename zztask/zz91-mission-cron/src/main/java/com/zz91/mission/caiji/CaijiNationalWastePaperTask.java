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

public class CaijiNationalWastePaperTask implements ZZTask{
    
    final static String PRICE_OPERTION = "price_caiji";
    final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    CaiJiCommonOperate operate = new CaiJiCommonOperate();

    final static Map<String, String> EACHCOUNTRY_MAP = new HashMap<String, String>();
    static{
        // httpget 使用的编码 不然会有乱码
        EACHCOUNTRY_MAP.put("charset", "GBK");
        // 采集地址
        EACHCOUNTRY_MAP.put("url", "http://baojia.feijiu.net/price-p-cid-bjcid3.95-dqid-.html," +
                                   "http://baojia.feijiu.net/price-p-cid-bjcid3.94-dqid-.html," +
                                   "http://baojia.feijiu.net/price-p-cid-bjcid3.92-dqid-.html," +
                                   "http://baojia.feijiu.net/price-p-cid-bjcid3.96-dqid-.html");
        EACHCOUNTRY_MAP.put("contentUrl", "");
        EACHCOUNTRY_MAP.put("listStart", "<div class=\"l_newsmaindetails\">"); // 列表页开头
        EACHCOUNTRY_MAP.put("listEnd", "<div id=\"ctl00_ContentPlaceHolder1_pagerTop\""); // 列表页结尾
        
        EACHCOUNTRY_MAP.put("contentStart", "<div class=\"l_newsdetails\"");
        EACHCOUNTRY_MAP.put("contentEnd", "<div class=\"l_newsmainbom\">");
        EACHCOUNTRY_MAP.put("split", "<li>");
        EACHCOUNTRY_MAP.put("splitLink", "\'");
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
            String content = "";
            String urlSplit = EACHCOUNTRY_MAP.get("url");
            String[] urlArray = urlSplit.split(",");
            for(String urlStr :urlArray){
                content = HttpUtils.getInstance().httpGet(urlStr,EACHCOUNTRY_MAP.get("charset"));
                if(content==null){
                    errContentUrl = errContentUrl + " 抓取来源网站内容失败！请点击<a href='" +  urlStr +"'>"
                            + "<font color='#FF0000'>废旧-各国废纸(13下子类)</font></a>查看来源网站是否更改地址！<br />";
                    LogUtil.getInstance().log(
                            "caiji-auto", PRICE_OPERTION, null, "{'title':'各国废纸(13下子类)','type':'failure','url':'<a href='" + urlStr +"' target='_blank'>"
                                    + "废旧</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                    flagFailure++;
                    continue;
                }
                Integer start = content.indexOf(EACHCOUNTRY_MAP.get("listStart"));
                Integer end = content.indexOf(EACHCOUNTRY_MAP.get("listEnd"));
                String result = operate.subContent(content, start, end);
                if(StringUtils.isEmpty(result)){
                    continue;
                }
                // 分解list 为多个元素
                String[] str = result.split(EACHCOUNTRY_MAP.get("split"));
    
                // 检查日期是否对应今天
                String formatDate = DateUtil.toString(baseDate, "yyyy-MM-dd");
                List<String> list = new ArrayList<String>();
                
                for (String s : str) {
                    if (s.indexOf(formatDate) != -1) {
                            list.add(s);
                    }
                }
                if(list.size()<1){
                    errContentUrl = errContentUrl + " 抓取失败，来源网站没有数据可抓取！请点击<a href='" +  urlStr +"'>"
                            + "<font color='#FF0000'>废旧-各国废纸(13下子类)</font></a>查看来源网站<br />";
                    LogUtil.getInstance().log(
                            "caiji-auto", PRICE_OPERTION, null, "{'title':'各国废纸(13下子类)','type':'failure','url':'<a href='" + urlStr +"' target='_blank'>"
                                    + "废旧</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                    flagFailure++;
                    continue;
                }
                
                for(String aStr:list){
                    String linkStr = operate.getAlink(aStr);
                    if(StringUtils.isEmpty(linkStr)){
                        continue;
                    }else{
                        String[] alink = linkStr.split(EACHCOUNTRY_MAP.get("splitLink"));
                        if(alink.length>0){
                            String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
                            resultTitle = resultTitle.replace("最新市场价","市场价格");
                            resultTitle = resultTitle.replace("最新废纸价格","废纸市场价格");
                            String resultContent ="";
                            String newUrl =  EACHCOUNTRY_MAP.get("contentUrl")+alink[1];
                            
                            resultContent = operate.getContent(EACHCOUNTRY_MAP, newUrl);
                            
                            // 获取内容
                            resultContent = Jsoup.clean(resultContent, Whitelist.none().addTags("p","ul","li","table","th","tr","td").addAttributes("td","rowspan"));
                            int resultStart = resultContent.indexOf("<table>");
                            int resultEnd= resultContent.indexOf("<p></p>");
                            resultContent = resultContent.substring(resultStart, resultEnd);
                             if (resultTitle.contains("国内日废")) {
                                 typeId = 27;
                             } else if (resultTitle.contains("国内美废")) {
                                 typeId = 28;
                             } else if (resultTitle.contains("国外")) {
                                 typeId = 29;
                             } else if(resultTitle.contains("国内欧废")) {
                                 typeId = 26;
                             }
                            String typeName = operate.queryTypeNameByTypeId(typeId);
                            Price price = new Price();
                            price.setTitle(resultTitle);
                            price.setTypeId(typeId);
                            price.setTags(typeName);
                            price.setContent(resultContent);
                            price.setGmtOrder(baseDate);
                            urlmap.put(resultTitle, urlStr);

                            Integer flg = operate.doInsert(price,false);
                           //执行插入并判断插入是否成功
                           if (flg != null && flg > 0) {
                               String catchTime = DateUtil.toString(new Date(), DATE_FORMAT).substring(11,16);
                               LogUtil.getInstance().log(
                                       "caiji-auto", PRICE_OPERTION, null, "{'title':'各国废纸(13下子类)','type':'success','url':'<a href='" + urlmap.get(resultTitle) +"' target='_blank'>"
                                               + "废旧</a>','defaultTime':'"+defaultTime+"','catchTime':'"+catchTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                               /*LogUtil.getInstance().log(
                                       "caiji-auto", PRICE_OPERTION, null, "{'title':'"+resultTitle.substring(5, resultTitle.length())+"("+typeId+")','type':'success','url':'<a href='" + urlmap.get(resultTitle) +"'>"
                                               + "废旧</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");*/
                           } else if (flg == null){
                               errContentUrl = errContentUrl + "数据存数据库失败！请联系网页抓取开发者！<a href='" + urlmap.get(resultTitle) +"'>"
                                       + "<font color='#FF0000'>" +resultTitle+"</font></a><br />";
                               LogUtil.getInstance().log(
                                       "caiji-auto", PRICE_OPERTION, null, "{'title':'各国废纸(13下子类)','type':'failure','url':'<a href='" + urlmap.get(resultTitle) +"' target='_blank'>"
                                               + "废旧</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                              /* LogUtil.getInstance().log(
                                       "caiji-auto", PRICE_OPERTION, null, "{'title':'"+resultTitle.substring(5, resultTitle.length())+"("+typeId+")','type':'failure','url':'<a href='" + urlmap.get(resultTitle) +"'>"
                                               + "废旧</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");*/
                               flagFailure++;
                           }
                        }
                    }
                }
            }
            
        } while (false);
        if (flagFailure != 0){
            String content = "各国废纸(13下子类)未抓取"+flagFailure+"条。分别为：<br />"
                    +errContentUrl + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_feijiu_eachcountry.htm'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("content", content);
            MailUtil.getInstance().sendMail(
                    "各国废纸自动抓取报错", 
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
        CaijiNationalWastePaperTask js=new CaijiNationalWastePaperTask();
        try {
            js.exec(DateUtil.getDate("2013-10-29", "yyyy-MM-dd"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
