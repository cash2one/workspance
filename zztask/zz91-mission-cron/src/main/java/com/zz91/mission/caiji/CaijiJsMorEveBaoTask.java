package com.zz91.mission.caiji;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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

public class CaijiJsMorEveBaoTask implements ZZTask{
    
    final static String PRICE_OPERTION = "price_caiji";
    final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    CaiJiCommonOperate operate = new CaiJiCommonOperate();
    final static Map<String, String> CS_MAP = new HashMap<String, String>();
    static{
        // httpget 使用的编码 不然会有乱码
        CS_MAP .put("charset", "gb2312");
        // 采集地址
        CS_MAP .put("url", "http://www.cs.com.cn/qhsc/zzqh/");
        // 后台类别对应采集地址 的key
        CS_MAP .put("216", ""); //全国废金属行情
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
        Integer typeId = 216;
        String url = "";
        Map<String, String> urlmap = new HashMap<String, String>();
        String time = DateUtil.toString(baseDate, DATE_FORMAT).substring(11, 13);
        String errContentUrl = "";
        do {
            String content ="";
            url = CS_MAP .get("url") + CS_MAP.get(String.valueOf(typeId));
			content = HttpUtils.getInstance().httpGet(url, CS_MAP .get("charset"));
            if(content==null){
                errContentUrl = errContentUrl + " 抓取来源网站内容失败！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>中证期货-金属早晚评"+"("+typeId+")"+"</font></a>查看来源网站是否更改地址！<br />"
                                + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_cs.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'金属早晚评("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "中证期货</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "金属早晚评自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
            Integer start = content.indexOf("<!-- 左侧 -->");
            Integer end = content.indexOf("<!-- 翻页 -->");
            String result = content.substring(start, end);
            String [] str = result.split("<li><span class=\"ctime\">");
            
            // 检查日期是否对应今天
            String format = DateUtil.toString(baseDate, "MM-dd");
            String formatZero = DateUtil.toString(baseDate, "M-d");
            List<String> list = new ArrayList<String>();
            for (String s :str) {
                if(s.indexOf(format)!=-1||s.indexOf(formatZero)!=-1){
                    list.add(s);
                }
            }
            

            if(list.size()<1){
                errContentUrl = errContentUrl + " 抓取失败，来源网站没有数据可抓取！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>中证期货-金属早晚评"+"("+typeId+")"+"</font></a>查看来源网站<br />"
                                + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_cs.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'金属早晚评("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "中证期货</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "金属早晚评自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
            String typeName = operate.queryTypeNameByTypeId(typeId);
            Map<Integer , Object> moMap = new TreeMap<Integer, Object>().descendingMap(); // 12点之前的资讯
            Map<Integer , Object> evMap = new TreeMap<Integer, Object>().descendingMap(); // 12点之后的资讯
            
            for (String s:list) {
                do {
                    
                Pattern pattern = Pattern.compile("<a(?:\\s+.+?)*?\\s+href=\".([^\"]*?)\".*>(.*?)</a>");
                
                Matcher matcher = pattern.matcher(s);
                
                String linkStr = "";
                if(matcher.find()){
                    linkStr = matcher.group();
                }
                if(StringUtils.isEmpty(linkStr)){
                    break;
                }
                
                String[] alink = linkStr.split("\"");
                
                String resultLink = CS_MAP.get("url")+alink[1].substring(2,alink[1].length());
                String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
                String resultContent ="";
                resultContent = HttpUtils.getInstance().httpGet(resultLink, CS_MAP .get("charset"));
                start = resultContent.indexOf("<div class=\"Dtext z_content\" id=ozoom1 style=\"ZOOM: 100%\">");
                end = resultContent.indexOf("<!-- 附件列表 -->");
                resultContent = resultContent.substring(start, end);
//              resultContent = resultContent.toLowerCase();
                resultContent = Jsoup.clean(resultContent, Whitelist.none().addTags("div","p","ul","li","br","table","th","tr","td").addAttributes("td","rowspan"));
                
                
                Price price = new Price();
                price.setTitle(resultTitle);
                price.setTypeId(typeId);
                price.setTags(typeName);
                price.setContent(resultContent);
                price.setGmtOrder(baseDate);
                // 判断资讯时间段 12点前还是12点之后
                String[] timeStr = s.split("\\)");
                Integer hour = Integer.valueOf(timeStr[0].substring(7,9));
                if(hour<=12){
                    moMap.put(price.getContent().length(), price);
                }else{
                    evMap.put(price.getContent().length(), price);
                }
                
                } while (false);
            }
            if (Integer.valueOf(time) < 12) {
                if (moMap.size() < 4) {
                    flagFailure = 4 - moMap.size();
                    errContentUrl = errContentUrl + "来源网站未抓取到："+flagFailure+"条----请点击<a href='" +  url +"'>"
                            + "<font color='#FF0000'>中证期货-金属早晚评"+"("+typeId+")"+"</font></a> 查看来源网站<br />-------------------------------------------------"
                            + "<br />";
                }
            } else {
                if (evMap.size() < 4) {
                    flagFailure = 4 - evMap.size();
                    errContentUrl = errContentUrl + "来源网站未抓取到："+flagFailure+"条----请点击<a href='" +  url +"'>"
                            + "<font color='#FF0000'>中证期货-金属早晚评"+"("+typeId+")"+"</font></a> 查看来源网站<br />-------------------------------------------------"
                            + "<br />";
                }
            }
            String dateStr = DateUtil.toString(new Date(), "MM月dd日");
            //执行循环插入
            Integer j = 0;
            for(Integer i:moMap.keySet()){
                do {
                    Price price = (Price) moMap.get(i);
                    if(price==null){
                        break;
                    }
                    boolean flag = false;
                    if(j==0){
                        //期货市场
                        j++;
                        flag = true;
                        price.setTitle(dateStr+"期货市场早评："+price.getTitle());
                    }else{
                        //期铜、铝、铅、锌
                        //06月27日期铝市场早间评论：美国经济数据拖累 期铝继续走低
                        String title = price.getTitle();
                        if(title.indexOf("铜")!=-1){
                            flag = true;
                            price.setTitle(dateStr+"期铜市场早间评论："+price.getTitle());
                        }
                        if(title.indexOf("铝")!=-1){
                            flag = true;
                            price.setTitle(dateStr+"期铝市场早间评论："+price.getTitle());
                        }
                        if(title.indexOf("铅")!=-1){
                            flag = true;
                            price.setTitle(dateStr+"期铅市场早间评论："+price.getTitle());
                        }
                        if(title.indexOf("锌")!=-1){
                            flag = true;
                            price.setTitle(dateStr+"期锌市场早间评论："+price.getTitle());
                        }
                    }
                    if(flag){
                        urlmap.put(price.getTitle(), url);
                        Integer flg = operate.doInsert(price,false);
                        //执行插入并判断插入是否成功
                       /* if (flg != null && flg > 0) {
                            LogUtil.getInstance().log(
                                    "caiji-auto", PRICE_OPERTION, null, "{'title':'"+price.getTitle().substring(5, price.getTitle().length())+"("+typeId+")','type':'success','url':'<a href='" + urlmap.get(price.getTitle()) +"'>"
                                            + "中证期货</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                        } else*/ if (flg == null){
                            errContentUrl = errContentUrl + "数据存数据库失败！请联系网页抓取开发者！<a href='" + urlmap.get(price.getTitle()) +"'>"
                                    + "<font color='#FF0000'>" +price.getTitle()+"</font></a><br />";
                            /*LogUtil.getInstance().log(
                                    "caiji-auto", PRICE_OPERTION, null, "{'title':'"+price.getTitle().substring(5, price.getTitle().length())+"("+typeId+")','type':'failure','url':'<a href='" + urlmap.get(price.getTitle()) +"'>"
                                            + "中证期货</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");*/
                            flagFailure++;
                        }
                    }
                } while (false);
            }
            j = 0;
            for(Integer i:evMap.keySet()){
                do {
                    Price price = (Price) evMap.get(i);
                    if(price==null){
                        break;
                    }
                    boolean flag = false;
                    if(j==0){
                        //期货市场
                        j++;
                        flag = true;
                        price.setTitle(dateStr+"期货市场日评："+price.getTitle());
                    }else{
                        //期铜、铝、铅、锌
                        //06月27日期铝市场早间评论：美国经济数据拖累 期铝继续走低
                        String title = price.getTitle();
                        if(title.indexOf("铜")!=-1){
                            flag = true;
                            price.setTitle(dateStr+"期铜市场日评："+price.getTitle());
                        }
                        if(title.indexOf("铝")!=-1){
                            flag = true;
                            price.setTitle(dateStr+"期铝市场日评："+price.getTitle());
                        }
                        if(title.indexOf("铅")!=-1){
                            flag = true;
                            price.setTitle(dateStr+"期铅市场日评："+price.getTitle());
                        }
                        if(title.indexOf("锌")!=-1){
                            flag = true;
                            price.setTitle(dateStr+"期锌市场日评："+price.getTitle());
                        }
                    }
                    if(flag){
                    	price.setGmtOrder(baseDate);
                        urlmap.put(price.getTitle(), url);
                        Integer flg = operate.doInsert(price,false);
                        //执行插入并判断插入是否成功
                        /*if (flg != null && flg > 0) {
                            LogUtil.getInstance().log(
                                    "caiji-auto", PRICE_OPERTION, null, "{'title':'"+price.getTitle().substring(5, price.getTitle().length())+"("+typeId+")','type':'success','url':'<a href='" + urlmap.get(price.getTitle()) +"'>"
                                            + "中证期货</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                        } else*/ if (flg == null){
                            errContentUrl = errContentUrl + "数据存数据库失败！请联系网页抓取开发者！<a href='" + urlmap.get(price.getTitle()) +"'>"
                                    + "<font color='#FF0000'>" +price.getTitle()+"</font></a><br />";
                            /*LogUtil.getInstance().log(
                                    "caiji-auto", PRICE_OPERTION, null, "{'title':'"+price.getTitle().substring(5, price.getTitle().length())+"("+typeId+")','type':'failure','url':'<a href='" + urlmap.get(price.getTitle()) +"'>"
                                            + "中证期货</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");*/
                            flagFailure++;
                        }
                    }
                } while (false);
            }
            
        } while (false);
        if (flagFailure != 0){
            String content = "金属早晚评"+"("+typeId+")"+"未抓取"+flagFailure+"条。分别为：<br />"
                    +errContentUrl + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_cs.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'金属早晚评("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                            + "中证期货</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("content", content);
            MailUtil.getInstance().sendMail(
                    "金属早晚评自动抓取报错", 
                    "zz91.price.caiji.auto@asto.mail", null,
                    null, "zz91", "blank",
                    map, MailUtil.PRIORITY_DEFAULT);
            return false;
        } else {
            String catchTime = DateUtil.toString(new Date(), DATE_FORMAT).substring(11,16);
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'金属早晚评("+typeId+")','type':'success','url':'<a href='" + url +"' target='_blank'>"
                            + "中证期货</a>','defaultTime':'"+defaultTime+"','catchTime':'"+catchTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            return true;
        }
    }

    @Override
    public boolean clear(Date baseDate) throws Exception {
        return false;
    }

    public static void main(String[] args) {

        DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
        CaijiJsMorEveBaoTask js=new CaijiJsMorEveBaoTask();
        try {
            js.exec(DateUtil.getDate("2014-03-12", "yyyy-MM-dd"));
            System.out.println("FDFDFF");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
