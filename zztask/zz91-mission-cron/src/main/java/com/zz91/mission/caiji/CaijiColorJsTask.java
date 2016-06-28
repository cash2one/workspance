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
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.log.LogUtil;
import com.zz91.util.mail.MailUtil;

public class CaijiColorJsTask implements ZZTask{
    
    final static String PRICE_OPERTION = "price_caiji";
    final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    CaiJiCommonOperate operate = new CaiJiCommonOperate();
    final static Map<String, String> ALBB_YS_MAP = new HashMap<String, String>();
    static{
        // httpget 使用的编码 不然会有乱码
        ALBB_YS_MAP.put("charset", "gb2312");
        // 采集地址
        ALBB_YS_MAP.put("url", "http://info.1688.com/news/newsListByTags/v6-l1441.html," +
                "http://info.1688.com/news/newsListByTags/v6-l1428.html," +
                "http://info.1688.com/news/newsListByTags/v6-l1440.html," +
                "http://info.1688.com/news/newsListByTags/v6-l1439.html," +
                "http://info.1688.com/news/newsListByTags/v6-l1438.html," +
                "http://s.1688.com/news/-B9E3D6DDD3D0C9AB.html," +
                "http://s.1688.com/news/-C0A5C3F7D3D0C9AB.html," +
                "http://s.1688.com/news/-CEE4BABAD3D0C9AB.html," +
                "http://s.1688.com/news/-CEF7B0B2D3D0C9AB.html," +
                "http://s.1688.com/news/-D6D8C7ECD3D0C9AB.html");
        
        ALBB_YS_MAP.put("contentUrl", "http://info.1688.com");
        
        ALBB_YS_MAP.put("65", ""); 
        
        ALBB_YS_MAP.put("listStart", "<dl class=\"li23 listcontent1\">"); // 列表页开头
        ALBB_YS_MAP.put("listEnd", "<div class=\"cl\"></div>"); // 列表页结尾
        ALBB_YS_MAP.put("split", "<li><span class=\"r\">");
        
        ALBB_YS_MAP.put("listStartS", "<div class=\"info-offer\">"); // 列表页开头
        ALBB_YS_MAP.put("listEndS", "<div class=\"pagination\">"); // 列表页结尾
        ALBB_YS_MAP.put("splitS", "<h3 class=\"title\">");

        
        ALBB_YS_MAP.put("contentStart", "<div class=\"detail\">");
        ALBB_YS_MAP.put("contentEnd", "(责任编辑：");
        ALBB_YS_MAP.put("finalContentStart", "<table style=\"width: 88");
        ALBB_YS_MAP.put("finalContentEnd", "<span class=\"editor\">");
        
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
        Integer typeId = 65;
        do {
            // 获取list 列表
//          String result = getListContent(ALBB_YS_MAP, typeId);
            String content = "";
            String urlSplit = ALBB_YS_MAP.get("url");
            String[] urlArray = urlSplit.split(",");
            for(String urlStr :urlArray){
                content = HttpUtils.getInstance().httpGet(urlStr,ALBB_YS_MAP.get("charset"));
                if(content==null){
                    errContentUrl = errContentUrl + " 抓取来源网站内容失败！请点击<a href='" +  urlStr +"'>"
                            + "<font color='#FF0000'>阿里巴巴-有色金属"+"("+typeId+")"+"</font></a>查看来源网站是否更改地址！<br />";
                    LogUtil.getInstance().log(
                            "caiji-auto", PRICE_OPERTION, null, "{'title':'有色金属("+typeId+")','type':'failure','url':'<a href='" + urlStr +"' target='_blank'>"
                                    + "阿里巴巴</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                    flagFailure++;
                    continue;
                }
                String fix = "";
                if(urlStr.indexOf("s.1688.com")!=-1){
                    fix = "S";
                }
                Integer start = content.indexOf(ALBB_YS_MAP.get("listStart"+fix));
                Integer end = content.indexOf(ALBB_YS_MAP.get("listEnd"+fix));
                String result = operate.subContent(content, start, end);
                if(StringUtils.isEmpty(result)){
                    continue;
                }
                // 分解list 为多个元素
                String[] str = result.split(ALBB_YS_MAP.get("split"+fix));
    
                // 检查日期是否对应今天
                String format = DateUtil.toString(baseDate, "MM月dd日");
                String formatZero = DateUtil.toString(baseDate, "M月d日");
                List<String> list = new ArrayList<String>();
                
                for (String s : str) {
                    if (s.indexOf(format) != -1 || s.indexOf(formatZero) != -1) {
                        if(s.indexOf("市场")!=-1){
                            list.add(s);
                        }
                    }
                }
                if (list.size() < 1) {
                    errContentUrl = errContentUrl + " 抓取失败，来源网站没有数据可抓取！请点击<a href='" +  urlStr +"'>"
                            + "<font color='#FF0000'>阿里巴巴-有色金属"+"("+typeId+")"+"</font></a>查看来源网站<br />";
                    LogUtil.getInstance().log(
                            "caiji-auto", PRICE_OPERTION, null, "{'title':'有色金属("+typeId+")','type':'failure','url':'<a href='" + urlStr +"' target='_blank'>"
                                    + "阿里巴巴</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                    flagFailure++;
                    continue;
                }
                
                String typeName = operate.queryTypeNameByTypeId(typeId);
                
                for(String aStr:list){
                    String linkStr = operate.getAlink(aStr);
                    if(StringUtils.isEmpty(linkStr)){
                        continue;
                    }else{
                        String[] alink = linkStr.split("\"");
                        if(alink.length>0){
                            String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
                            resultTitle = resultTitle.replace("行情","价格");
                            urlmap.put(resultTitle, urlStr);
                            String resultContent ="";
                            if(urlStr.indexOf("s.1688.com")!=-1){
                                resultContent = operate.getContent(ALBB_YS_MAP, alink[1]);
                            }else{
                                resultContent = operate.getContent(ALBB_YS_MAP, ALBB_YS_MAP.get("contentUrl")+alink[1]);
                            }
                            int finalStart;
                            if (resultContent.contains("&nbsp;<br/><br/>")) {
                                finalStart = resultContent.indexOf("&nbsp;<br/><br/>");
                            } else if (resultContent.contains("<table border=\"1\"")) {
                                finalStart = resultContent.indexOf("<table border=\"1\"");
                            } else {
                                finalStart = resultContent.indexOf(ALBB_YS_MAP.get("finalContentStart"));
                            }
                            
                            int finalEnd = resultContent.indexOf(ALBB_YS_MAP.get("finalContentEnd"));
                            resultContent = operate.subContent(resultContent, finalStart, finalEnd);
                            // 获取内容
                            resultContent = Jsoup.clean(resultContent, Whitelist.none().addTags("p","ul","li","table","th","tr","td").addAttributes("td","colspan"));
                          
                            
                            Price price = new Price();
                            price.setTitle(resultTitle);
                            price.setTypeId(typeId);
                            price.setTags(typeName);
                            price.setContent(resultContent);
                            price.setGmtOrder(baseDate);
                            
                            Integer flg = operate.doInsert(price,false);
                            //执行插入并判断插入是否成功
                            if (flg != null && flg > 0) {
                                String catchTime = DateUtil.toString(new Date(), DATE_FORMAT).substring(11,16);
                                LogUtil.getInstance().log(
                                        "caiji-auto", PRICE_OPERTION, null, "{'title':'有色金属("+typeId+")','type':'success','url':'<a href='" + urlmap.get(resultTitle) +"' target='_blank'>"
                                                + "阿里巴巴</a>','defaultTime':'"+defaultTime+"','catchTime':'"+catchTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                                /*LogUtil.getInstance().log(
                                        "caiji-auto", PRICE_OPERTION, null, "{'title':'"+resultTitle.substring(5, resultTitle.length())+"("+typeId+")','type':'success','url':'<a href='" + urlmap.get(resultTitle) +"'>"
                                                + "阿里巴巴</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");*/
                            } else if (flg == null){
                                errContentUrl = errContentUrl + "数据存数据库失败！请联系网页抓取开发者！<a href='" + urlmap.get(resultTitle) +"'>"
                                        + "<font color='#FF0000'>" +resultTitle+"</font></a><br />";
                                LogUtil.getInstance().log(
                                        "caiji-auto", PRICE_OPERTION, null, "{'title':'有色金属("+typeId+")','type':'failure','url':'<a href='" + urlmap.get(resultTitle) +"' target='_blank'>"
                                                + "阿里巴巴</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                                /*LogUtil.getInstance().log(
                                        "caiji-auto", PRICE_OPERTION, null, "{'title':'"+resultTitle.substring(5, resultTitle.length())+"("+typeId+")','type':'failure','url':'<a href='" + urlmap.get(resultTitle) +"'>"
                                                + "阿里巴巴</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");*/
                                flagFailure++;
                            }
                        }
                    }
                }
            }
        } while (false);
        if (flagFailure != 0){
            String content = "有色金属"+"("+typeId+")"+"未抓取"+flagFailure+"条。分别为：<br />"
                    +errContentUrl + "请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_albb_ys.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("content", content);
            MailUtil.getInstance().sendMail(
                    "有色金属自动抓取报错", 
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
        CaijiColorJsTask js=new CaijiColorJsTask();
        try {
            js.exec(DateUtil.getDate("2013-10-29", "yyyy-MM-dd"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
