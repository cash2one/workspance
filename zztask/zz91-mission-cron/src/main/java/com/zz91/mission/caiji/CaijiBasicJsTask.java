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

public class CaijiBasicJsTask implements ZZTask{

    final static String PRICE_OPERTION = "price_caiji";
    final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    @Override
    public boolean init() throws Exception {
        return false;
    }

    @Override
    public boolean exec(Date baseDate) throws Exception {
        String defaultTime = DateUtil.toString(baseDate, DATE_FORMAT).substring(11,16);

        CaiJiCommonOperate operate = new CaiJiCommonOperate();
        final Map<String, String> OMETAL_MAP = new HashMap<String, String>();
        // httpget 使用的编码 不然会有乱码
        OMETAL_MAP.put("charset", "gb2312");
        // 采集地址
        OMETAL_MAP.put("url", "http://www.ometal.com/bin/new/searchkey.asp?type=");
        // 后台类别对应采集地址 的key
        OMETAL_MAP.put("79", "%C9%CF%BA%A3%CF%D6%BB%F5-%BB%F9%B1%BE%BD%F0%CA%F4"); // 基本金属
        
        
        //判断自动抓取是否成功
        int flagFailure = 0 ;
        Map<String, String> urlmap = new HashMap<String, String>();
        String errContentUrl = "";
        String url = "";
        Integer typeId = 79;
        do {
            
            String content ="";
            url = OMETAL_MAP.get("url") + OMETAL_MAP.get(String.valueOf(typeId));
			content = HttpUtils.getInstance().httpGet(url, OMETAL_MAP.get("charset"));
            if(content==null){
                errContentUrl = errContentUrl + " 抓取来源网站内容失败！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>全球金属网-基本金属"+"("+typeId+")"+"</font></a>查看来源网站是否更改地址！<br />"
                                + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_ometal.htm?typeId="+typeId +"'><font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'基本金属("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "全球金属网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "基本金属自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
            Integer start = content.indexOf("搜索结果：");
            Integer end = content.indexOf("上一页");
            String result = content.substring(start, end);
            String [] str = result.split("</tr>");
            
            // 检查日期是否对应今天
            String format = DateUtil.toString(baseDate, "yyyy-M-d");
            List<String> list = new ArrayList<String>();
            for (String s:str) {
                if(s.indexOf(format)!=-1 && s.indexOf("部分") == -1){
                    list.add(s);
                }
            }
            if (list.size() < 1) {
                errContentUrl = errContentUrl + " 抓取失败，来源网站没有数据可抓取！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>全球金属网-基本金属"+"("+typeId+")"+"</font></a>查看来源网站<br />"
                                + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_ometal.htm?typeId="+typeId +"'><font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'基本金属("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "全球金属网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "基本金属自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
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
                
                String resultLink = "http://www.ometal.com" + alink[1];
                String resultContent ="";
                resultContent = HttpUtils.getInstance().httpGet(resultLink, OMETAL_MAP.get("charset"));
                Integer cStart = resultContent.indexOf("<div id=\"fontzoom\">");
                Integer cEnd = resultContent.indexOf("(您想天天");
                resultContent = resultContent.substring(cStart, cEnd);
                start = resultContent.indexOf("<TABLE");
                end = resultContent.indexOf("</TABLE>");
                resultContent = resultContent.substring(start, end+8);
                resultContent = Jsoup.clean(resultContent, Whitelist.none().addTags("div","p","ul","li","br","table","th","tr","td").addAttributes("td","rowspan"));
                
                resultContent = resultContent.replace("(全球金属网 OMETAL.COM)", "");
                resultContent = resultContent.replace("全球金属网", "");
                resultContent = resultContent.replace("OMETAL.COM", "");
                
                Price price = new Price();
                String typeName = operate.queryTypeNameByTypeId(typeId);
                String resultTitle = "";
                resultTitle = DateUtil.toString(new Date(), "MM月dd日")+typeName+"行情";
                resultTitle = resultTitle.replaceAll("上海", "上海现货(SMM)");
                urlmap.put(resultTitle, url);
                price.setTitle(resultTitle);
                price.setTypeId(typeId);
                price.setTags(typeName);
                price.setContent(resultContent);
                price.setGmtOrder(baseDate);
                
                //执行插入 
                Integer flg = operate.doInsert(price,true);
                //执行插入并判断插入是否成功
                /*if (flg != null && flg > 0) {
                    LogUtil.getInstance().log(
                            "caiji-auto", PRICE_OPERTION, null, "{'title':'"+resultTitle.substring(5, resultTitle.length())+"("+typeId+")','type':'success','url':'<a href='" + urlmap.get(resultTitle) +"'>"
                                    + "全球金属网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                } else */if (flg == null){
                    errContentUrl = errContentUrl + "数据存数据库失败！请联系网页抓取开发者！<a href='" + urlmap.get(resultTitle) +"'>"
                            + "<font color='#FF0000'>" +resultTitle+"</font></a><br />";
                   /* LogUtil.getInstance().log(
                            "caiji-auto", PRICE_OPERTION, null, "{'title':'"+resultTitle.substring(5, resultTitle.length())+"("+typeId+")','type':'failure','url':'<a href='" + urlmap.get(resultTitle) +"'>"
                                    + "全球金属网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");*/
                    flagFailure++;
                }
            }
            } while (false);
        if (flagFailure != 0){
            String content = "基本金属"+"("+typeId+")"+"未抓取"+flagFailure+"条。分别为：<br />"
                    +errContentUrl + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_ometal.htm?typeId="+typeId +"'><font color='#FF0000'>抓取</font></a>！";
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'基本金属("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                            + "全球金属网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("content", content);
            MailUtil.getInstance().sendMail(
                    "基本金属自动抓取报错", 
                    "zz91.price.caiji.auto@asto.mail", null,
                    null, "zz91", "blank",
                    map, MailUtil.PRIORITY_DEFAULT);
            return false;
        } else {
            String catchTime = DateUtil.toString(new Date(), DATE_FORMAT).substring(11,16);
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'基本金属("+typeId+")','type':'success','url':'<a href='" + url +"' target='_blank'>"
                            + "全球金属网</a>','defaultTime':'"+defaultTime+"','catchTime':'"+catchTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            return true;
        }
        
    }

    @Override
    public boolean clear(Date baseDate) throws Exception {
        return false;
    }
    
    public static void main(String[] args) {
        DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
        CaijiBasicJsTask js=new CaijiBasicJsTask();
        try {
            js.exec(DateUtil.getDate("2013-10-29", "yyyy-MM-dd"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
