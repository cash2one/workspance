package com.zz91.mission.caiji;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class CaijiUSAPriceTask implements ZZTask{

    final static String PRICE_OPERTION = "price_caiji";
    final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    CaiJiCommonOperate operate = new CaiJiCommonOperate();
    final static Map<String, String> FEIJIU_MAP = new HashMap<String, String>();
    static{
        // httpget 使用的编码 不然会有乱码
        FEIJIU_MAP.put("charset", "gb2312");
        // 采集地址
        FEIJIU_MAP.put("url", "http://baojia.feijiu.net/");
        // 后台类别对应采集地址 的key
        FEIJIU_MAP.put("62", "price-p-cid-bjcid2.32-dqid-.html"); //美国价格
        
    }
    @Override
    public boolean init() throws Exception {
        return false;
    }

    @Override
    public boolean exec(Date baseDate) throws Exception {
        String defaultTime = DateUtil.toString(baseDate, DATE_FORMAT).substring(11,16);
        //判断自动抓取是否成功
        int flagFailure = 0 ;
        Integer typeId = 62;
        String url = "";
        Map<String, String> urlmap = new HashMap<String, String>();
        String errContentUrl = "";
        do {
            String content ="";
            try {
                url = FEIJIU_MAP.get("url") + FEIJIU_MAP.get(String.valueOf(typeId));
                content = operate.httpClientHtml(url, FEIJIU_MAP.get("charset"));
            } catch (HttpException e) {
                content = null;
            } catch (IOException e) {
            }
            if(content==null){
                errContentUrl = errContentUrl + " 抓取来源网站内容失败！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>废旧网-美国价格"+"("+typeId+")"+"</font></a>查看来源网站是否更改地址！<br />"
                                + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_feijiu.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'美国价格("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "废旧网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "美国价格自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
            Integer start = content.indexOf("废塑料价格行情</p>");
            Integer end = content.indexOf("<div id=\"ctl00_ContentPlaceHolder1_pagerTop\"");
            if(start>=end){
                break;
            }
            String result = content.substring(start, end);
            String [] str = result.split("</li>");
            
            // 检查日期是否对应今天
            String format = DateUtil.toString(baseDate, "yyyy-MM-dd");
            List<String> list = new ArrayList<String>();
            for (String s :str) {
                if(s.indexOf(format)!=-1){
                    list.add(s);
                }
            }
            
            if(list.size()<1){
                errContentUrl = errContentUrl + " 抓取失败，来源网站没有数据可抓取！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>废旧网-美国价格"+"("+typeId+")"+"</font></a>查看来源网站<br />"
                                + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_feijiu.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'美国价格("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "废旧网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "美国价格自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
            if (list.size() < 6) {
                flagFailure = 6 - list.size();
                errContentUrl = errContentUrl + "来源网站未抓取到："+flagFailure+"条----请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>废旧网-美国价格"+"("+typeId+")"+"</font></a> 查看来源网站<br />-------------------------------------------------"
                        + "<br />";
            }
            String typeName = operate.queryTypeNameByTypeId(typeId);
            for (String s:list) {
                do{
                Pattern pattern = Pattern.compile("<a(?:\\s+.+?)*?\\s+href=\'([^\"]*?)\'.+>(.*?)</a>");
                
                Matcher matcher = pattern.matcher(s);
                
                String linkStr = "";
                if(matcher.find()){
                    linkStr = matcher.group();
                }
                if(StringUtils.isEmpty(linkStr)){
                    break;
                }
                
                String[] alink = linkStr.split("\'");
                
                String resultLink = alink[1];
                String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
                String resultContent ="";
                try {
                    resultContent = operate.httpClientHtml(resultLink, FEIJIU_MAP.get("charset"));
                } catch (HttpException e) {
                } catch (IOException e) {
                }
                start = resultContent.indexOf("<TABLE");
                end = resultContent.indexOf("</TABLE>");
                resultContent = resultContent.substring(start, end+8);
                resultContent.replace("&nbsp;", "");
                resultContent = Jsoup.clean(resultContent, Whitelist.none().addTags("p","ul","li","br","table","tbody","th","tr","td","b").addAttributes("td","rowspan","colspan").addAttributes("tr","rowspan","colspan"));
                resultContent = "<p>元/吨</p>" + resultContent;
                
                Price price = new Price();
                resultTitle = resultTitle.replaceAll("塑料价格", "废塑料市场价格");
                resultTitle = resultTitle.replaceAll("价格行情", "价格");
                price.setTitle(resultTitle);
                price.setTypeId(typeId);
                price.setTags(typeName);
                price.setContent(resultContent);
                price.setGmtOrder(baseDate);
                urlmap.put(resultTitle, url);

                Integer flg = operate.doInsert(price,true);
               //执行插入并判断插入是否成功
/*               if (flg != null && flg > 0) {
                   LogUtil.getInstance().log(
                           "caiji-auto", PRICE_OPERTION, null, "{'title':'"+resultTitle.substring(5, resultTitle.length())+"("+typeId+")','type':'success','url':'<a href='" + urlmap.get(resultTitle) +"'>"
                                   + "废旧网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
               } else*/ if (flg == null){
                   errContentUrl = errContentUrl + "数据存数据库失败！请联系网页抓取开发者！<a href='" + urlmap.get(resultTitle) +"'>"
                           + "<font color='#FF0000'>" +resultTitle+"</font></a><br />";
                   /*LogUtil.getInstance().log(
                           "caiji-auto", PRICE_OPERTION, null, "{'title':'"+resultTitle.substring(5, resultTitle.length())+"("+typeId+")','type':'failure','url':'<a href='" + urlmap.get(resultTitle) +"'>"
                                   + "废旧网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");*/
                   flagFailure++;
               }
                }while(false);
            }
            
        } while (false);
        if (flagFailure != 0){
            String content = "美国价格"+"("+typeId+")"+"未抓取"+flagFailure+"条。分别为：<br />"
                    +errContentUrl + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_feijiu.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'美国价格("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                            + "废旧网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("content", content);
            MailUtil.getInstance().sendMail(
                    "美国价格自动抓取报错", 
                    "zz91.price.caiji.auto@asto.mail", null,
                    null, "zz91", "blank",
                    map, MailUtil.PRIORITY_DEFAULT);
            return false;
        } else {
            String catchTime = DateUtil.toString(new Date(), DATE_FORMAT).substring(11,16);
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'美国价格("+typeId+")','type':'success','url':'<a href='" + url +"' target='_blank'>"
                            + "废旧网</a>','defaultTime':'"+defaultTime+"','catchTime':'"+catchTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            return true;
        }
    }

    @Override
    public boolean clear(Date baseDate) throws Exception {
        return false;
    }
    
    public static void main(String[] args) {

        DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
        CaijiUSAPriceTask js=new CaijiUSAPriceTask();
        try {
            js.exec(DateUtil.getDate("2015-07-03", "yyyy-MM-dd"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
