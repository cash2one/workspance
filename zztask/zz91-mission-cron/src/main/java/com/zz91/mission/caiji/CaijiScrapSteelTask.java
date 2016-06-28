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
import com.zz91.util.lang.StringUtils;
import com.zz91.util.log.LogUtil;
import com.zz91.util.mail.MailUtil;

public class CaijiScrapSteelTask implements ZZTask{
    
    final static String PRICE_OPERTION = "price_caiji";
    final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    CaiJiCommonOperate operate = new CaiJiCommonOperate();

    final static Map<String, String> IRON_MAP = new HashMap<String, String>();
    static{
        // httpget 使用的编码 不然会有乱码
        IRON_MAP.put("charset", "gbk");
        IRON_MAP.put("url", "http://info.1688.com/tags_list/v6-d1162453217.html");// 采集地址
    }

    @Override
    public boolean init() throws Exception {
        return false;
    }

    @Override
    public boolean exec(Date baseDate) throws Exception {
        String defaultTime = DateUtil.toString(baseDate, DATE_FORMAT).substring(11,16);
        int flagFailure = 0 ;
        Integer typeId = 45;
        String url = "";
        Map<String, String> urlmap = new HashMap<String, String>();
        String errContentUrl = "";
        do {
            String content ="";
            try {
                url = IRON_MAP.get("url");
                content = operate.httpClientHtml(url , IRON_MAP.get("charset"));
            } catch (HttpException e) {
                content = null;
            } catch (IOException e) {
                content = null;
            }
            if(content==null){
                errContentUrl = errContentUrl + " 抓取来源网站内容失败！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>阿里巴巴-废钢"+"("+typeId+")"+"</font></a>查看来源网站是否更改地址！<br />"
                                + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_albb_iron.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'废钢("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "阿里巴巴</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "废钢自动抓取报错", 
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
            List<String> list = new ArrayList<String>();
            for (String s:str) {
                if(s.indexOf(format)!=-1){
                    if (s.contains("华东")
                            || s.contains("华北")
                            || s.contains("东北")
                            || s.contains("中南及其它地区")
                            || s.contains("全国各地废钢价格行情汇总")
                            || s.contains("重废")
                            || s.contains("统料")
                            || s.contains("中废")) {
                             list.add(s);
                         }
                }
            }
            if(list.size()<1){
                errContentUrl = errContentUrl + " 抓取失败，来源网站没有数据可抓取！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>阿里巴巴-废钢"+"("+typeId+")"+"</font></a>查看来源网站<br />"
                                + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_albb_iron.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'废钢("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "阿里巴巴</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "废钢自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
            if (list.size() < 4) {
                flagFailure = 4 - list.size();
                errContentUrl = errContentUrl + "来源网站未抓取到："+flagFailure+"条----请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>阿里巴巴-废钢"+"("+typeId+")"+"</font></a> 查看来源网站<br />-------------------------------------------------"
                        + "<br />";
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
                    if (resultTitle.contains("全国各地废钢价格行情汇总")) {
                        resultTitle = resultTitle.replace("行情汇总", "");
                    }
                    resultTitle=resultTitle.replace("行情", "汇总");
                    String resultLink = "http://info.1688.com" + alink[1];
                    String resultContent ="";
                    try {
                        resultContent = operate.httpClientHtml(resultLink, IRON_MAP.get("charset"));
                    } catch (HttpException e) {
                    } catch (IOException e) {
                    }
                    Integer cStart = resultContent.indexOf("<div class=\"d-content\">");
                    Integer cEnd = resultContent.indexOf("(责任编辑：");
                    resultContent = resultContent.substring(cStart, cEnd);
                    resultContent = Jsoup.clean(resultContent, Whitelist.none().addTags("div","p","ul","li","br","table","th","tr","td").addAttributes("td","rowspan"));
                    if (!(resultTitle.contains("华东")
                            || resultTitle.contains("华北")
                            || resultTitle.contains("东北")
                            || resultTitle.contains("中南及其它地区")
                            || resultTitle.contains("全国各地废钢价格行情汇总"))) {
                        resultContent = resultContent.replace("<td>趋势图</td>", "");
                    } else {
                        resultContent = resultContent.replace("<br>", "");
                        resultContent = resultContent.replace("<br />", "");
                        resultContent = resultContent.replace("<p>", "<p style=\"text-align: center;\">");
                        resultContent = resultContent.replace("</table>", "</table><p style=\"text-align: center;\">");
                        resultContent = resultContent.replace("<table>", "</p><table>");
                        resultContent=resultContent.replace("行情", "汇总");
                    }
                    
                    
                    Price price = new Price();
                    String typeName = operate.queryTypeNameByTypeId(typeId);
                    
                    price.setTitle(resultTitle);
                    price.setTypeId(typeId);
                    price.setTags(typeName);
                    price.setContent(resultContent);
                    price.setGmtOrder(baseDate);
                    urlmap.put(resultTitle, url);
                    Integer flg = 0;
                    //执行插入 
                    if (!(resultTitle.contains("华东")
                            || resultTitle.contains("华北")
                            || resultTitle.contains("东北")
                            || resultTitle.contains("中南及其它地区"))) {
                        flg = operate.doInsert(price,true);
                    } else {
                        flg = operate.doInsert(price,false);
                    }
                  //执行插入并判断插入是否成功
                    /*if (flg != null && flg > 0) {
                        LogUtil.getInstance().log(
                                "caiji-auto", PRICE_OPERTION, null, "{'title':'"+resultTitle.substring(5, resultTitle.length())+"("+typeId+")','type':'success','url':'<a href='" + urlmap.get(resultTitle) +"'>"
                                        + "阿里巴巴</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                    } else */if (flg == null){
                        errContentUrl = errContentUrl + "数据存数据库失败！请联系网页抓取开发者！<a href='" + urlmap.get(resultTitle) +"'>"
                                + "<font color='#FF0000'>" +resultTitle+"</font></a><br />";
                       /* LogUtil.getInstance().log(
                                "caiji-auto", PRICE_OPERTION, null, "{'title':'"+resultTitle.substring(5, resultTitle.length())+"("+typeId+")','type':'failure','url':'<a href='" + urlmap.get(resultTitle) +"'>"
                                        + "阿里巴巴</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");*/
                        flagFailure++;
                    }
                }
               
            }
        } while (false);
        if (flagFailure != 0){
            String content = "废钢"+"("+typeId+")"+"未抓取"+flagFailure+"条。分别为：<br />"
                    +errContentUrl + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_albb_iron.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'废钢("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                            + "阿里巴巴</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("content", content);
            MailUtil.getInstance().sendMail(
                    "废钢自动抓取报错", 
                    "zz91.price.caiji.auto@asto.mail", null,
                    null, "zz91", "blank",
                    map, MailUtil.PRIORITY_DEFAULT);
            return false;
        } else {
            String catchTime = DateUtil.toString(new Date(), DATE_FORMAT).substring(11,16);
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'废钢("+typeId+")','type':'success','url':'<a href='" + url +"' target='_blank'>"
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
        CaijiScrapSteelTask js=new CaijiScrapSteelTask();
        try {
            js.exec(DateUtil.getDate("2014-08-28", "yyyy-MM-dd"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
