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

public class CaijiSlMorningCommentsTask implements ZZTask{
    
    final static String PRICE_OPERTION = "price_caiji";
    final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    CaiJiCommonOperate operate = new CaiJiCommonOperate();
    final static Map<String, String> ALBB_MAP = new HashMap<String, String>();
    static{
        // httpget 使用的编码 不然会有乱码
        ALBB_MAP .put("charset", "gb2312");
        // 采集地址
        ALBB_MAP .put("url", "http://info.1688.com");
        // 后台类别对应采集地址 的key
        ALBB_MAP .put("217", "/tags_list/v5003220-l18935.html"); //全国废塑料行情
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
        Integer typeId = 217;
        String url = "";
        Map<String, String> urlmap = new HashMap<String, String>();
        String errContentUrl = "";
        do {
            String content ="";
            url = ALBB_MAP .get("url") + ALBB_MAP.get(String.valueOf(typeId));
			content = HttpUtils.getInstance().httpGet(url, ALBB_MAP .get("charset"));
            if(content==null){
                errContentUrl = errContentUrl + " 抓取来源网站内容失败！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>阿里巴巴-塑料早间评论"+"("+typeId+")"+"</font></a>查看来源网站是否更改地址！<br />"
                                + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_albb.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'塑料早间评论("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "阿里巴巴</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "塑料早间评论自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
            Integer start = content.indexOf("<dl class=\"li23 listcontent1\">");
            Integer end = content.indexOf("<!--分页 start-->");
            String result = content.substring(start, end);
            String [] str = result.split("<li>");
            
            // 检查日期是否对应今天
            String format = DateUtil.toString(baseDate, "yyyy-MM-dd");
            String formatZero = DateUtil.toString(baseDate, "yyyy-M-d");
            String formatTitle = DateUtil.toString(baseDate, "MM月dd日");
            List<String> list = new ArrayList<String>();
            for (String s :str) {
                if(s.indexOf(format)!=-1||s.indexOf(formatZero)!=-1){
                    if(s.indexOf("塑料原料")==-1){
                        list.add(s);
                    }
                }
            }
            
            if(list.size()<1){
                errContentUrl = errContentUrl + " 抓取失败，来源网站没有数据可抓取！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>阿里巴巴-塑料早间评论"+"("+typeId+")"+"</font></a>查看来源网站<br />"
                                + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_albb.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'塑料早间评论("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "阿里巴巴</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "塑料早间评论自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
            if (list.size() < 6) {
                flagFailure = 6 - list.size();
                errContentUrl = errContentUrl + "来源网站未抓取到："+flagFailure+"条----请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>阿里巴巴-塑料早间评论"+"("+typeId+")"+"</font></a> 查看来源网站<br />-------------------------------------------------"
                        + "<br />";
            }
            String typeName = operate.queryTypeNameByTypeId(typeId);
            for (String s:list) {
                    do {
                        Pattern pattern = Pattern.compile("<a(?:\\s+.+?)*?\\s+href=\"([^\"]*?)\".+>(.*?)</a>");
                        
                        Matcher matcher = pattern.matcher(s);
                        
                        String linkStr = "";
                        if(matcher.find()){
                            linkStr = matcher.group();
                        }
                        if(StringUtils.isEmpty(linkStr)){
                            break;
                        }
                        
                        String[] alink = linkStr.split("\"");
                        
                        String resultLink = ALBB_MAP.get("url")+alink[1];
                        String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
                        resultTitle = resultTitle.replace(" ", "");
                        for (int i = 0 ; i < resultTitle.length() ; i++) {
                            if (resultTitle.charAt(i) >= 'A' && resultTitle.charAt(i) <= 'Z') {
                                resultTitle = resultTitle.substring(i, resultTitle.length());
                                break;
                             }
                         }
                        resultTitle = formatTitle + resultTitle;
                        resultTitle = resultTitle.replace("点评", "评论");
                        resultTitle = resultTitle.replace("PP粉", "PP粉料");
                        resultTitle = resultTitle.replace("PP粒", "PP");
                        if(resultTitle.indexOf("现货")==-1){
                            resultTitle = resultTitle.replace("市场", "现货市场");
                        }
                        
                        String resultContent ="";
                        resultContent = HttpUtils.getInstance().httpGet(resultLink, ALBB_MAP .get("charset"));
                        start = resultContent.indexOf("<div class=\"d-content\">");
                        end = resultContent.indexOf("<span class=\"editor\">");
                        resultContent = resultContent.substring(start, end);
        //              resultContent = resultContent.toLowerCase();
                        resultContent = Jsoup.clean(resultContent, Whitelist.none().addTags("p","ul","li","br","table","th","tr","td").addAttributes("td","rowspan"));
                        if(resultContent.indexOf("<table")==-1){
                            resultContent = resultContent.replace(" ", "");
                        }
                        
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
                                           + "阿里巴巴</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                       } else */if (flg == null){
                           errContentUrl = errContentUrl + "数据存数据库失败！请联系网页抓取开发者！<a href='" + urlmap.get(resultTitle) +"'>"
                                   + "<font color='#FF0000'>" +resultTitle+"</font></a><br />";
                           /*LogUtil.getInstance().log(
                                   "caiji-auto", PRICE_OPERTION, null, "{'title':'"+resultTitle.substring(5, resultTitle.length())+"("+typeId+")','type':'failure','url':'<a href='" + urlmap.get(resultTitle) +"'>"
                                           + "阿里巴巴</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");*/
                           flagFailure++;
                       }
                }while(false);
            }
            
        } while (false);
        if (flagFailure != 0){
            String content = "塑料早间评论"+"("+typeId+")"+"未抓取"+flagFailure+"条。分别为：<br />"
                    +errContentUrl + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_albb.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'塑料早间评论("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                            + "阿里巴巴</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("content", content);
            MailUtil.getInstance().sendMail(
                    "塑料早间评论自动抓取报错", 
                    "zz91.price.caiji.auto@asto.mail", null,
                    null, "zz91", "blank",
                    map, MailUtil.PRIORITY_DEFAULT);
            return false;
        } else {
            String catchTime = DateUtil.toString(new Date(), DATE_FORMAT).substring(11,16);
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'塑料早间评论("+typeId+")','type':'success','url':'<a href='" + url +"' target='_blank'>"
                            + "阿里巴巴</a>','defaultTime':'"+defaultTime+"','catchTime':'"+catchTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            return true;
        }
    }

    @Override
    public boolean clear(Date baseDate) throws Exception {
        return false;
    }
    
    public static void main(String[] args) {

        DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
        CaijiSlMorningCommentsTask js=new CaijiSlMorningCommentsTask();
        try {
            js.exec(DateUtil.getDate("2013-10-25", "yyyy-MM-dd"));
            System.out.println("1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
