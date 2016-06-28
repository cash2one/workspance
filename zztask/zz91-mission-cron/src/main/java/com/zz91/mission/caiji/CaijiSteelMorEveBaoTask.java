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

public class CaijiSteelMorEveBaoTask implements ZZTask{
    
    final static String PRICE_OPERTION = "price_caiji";
    final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    CaiJiCommonOperate operate = new CaiJiCommonOperate();
    final static Map<String, String> XGX_MAP = new HashMap<String, String>();
    static{
        // httpget 使用的编码 不然会有乱码
        XGX_MAP.put("charset", "utf-8");
        // 采集地址
        XGX_MAP.put("url", "http://www.96369.net");
        // 后台类别对应采集地址 的key
        XGX_MAP.put("216", "/news/news_list.aspx?channelid=2&columnid=9"); //全国废金属行情
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
        String url = "";
        Map<String, String> urlmap = new HashMap<String, String>();
        String errContentUrl = "";
        do {
            String content = "";
            url = XGX_MAP.get("url")+ XGX_MAP.get(String.valueOf(typeId));
			content = HttpUtils.getInstance().httpGet(url,XGX_MAP.get("charset"));
            if(content==null){
                errContentUrl = errContentUrl + " 抓取来源网站内容失败！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>新干线-钢铁早晚评"+"("+typeId+")"+"</font></a>查看来源网站是否更改地址！<br />"
                                + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_XGX.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'钢铁早晚评("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "新干线</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "钢铁早晚评自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
            Integer start = content.indexOf("每日分析</strong> 共");
            Integer end = content.indexOf("PagerList");
            String result = operate.subContent(content, start, end);
            String[] str = result.split("<div class=\"lb_c_banner\">");

            // 检查日期是否对应今天
            String format = DateUtil.toString(baseDate, "MM月dd日");
            String formatZero = DateUtil.toString(baseDate, "M月d日");
            List<String> list = new ArrayList<String>();
            
            
            for (String s : str) {
                if (s.indexOf(format) != -1 || s.indexOf(formatZero) != -1) {
                    if(s.indexOf("-早报")!=-1||s.indexOf("-早报-晚报")!=-1){
                        list.add(s);
                    }
                }
            }

            if(list.size()<1){
                errContentUrl = errContentUrl + " 抓取失败，来源网站没有数据可抓取！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>新干线-钢铁早晚评"+"("+typeId+")"+"</font></a>查看来源网站<br />"
                                + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_XGX.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'钢铁早晚评("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "新干线</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "钢铁早晚评自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }

            
            String typeName = operate.queryTypeNameByTypeId(typeId);
            
            for(String aStr:list){
                String linkStr = operate.getAlink(aStr);
                if(StringUtils.isEmpty(linkStr)){
                    continue;
                }else{
                    String[] alink = linkStr.split("\"");
                    if(alink.length>0){
                        String resultLink = XGX_MAP.get("url")+alink[3];
                        String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
                        String resultContent ="";
                        resultContent = HttpUtils.getInstance().httpGet(resultLink, XGX_MAP .get("charset"));
                        start = resultContent.indexOf("<div id=\"context\">");
                        end = resultContent.indexOf("<div id=\"Disclaimer\">");
                        resultContent = operate.subContent(resultContent,start, end);
                        
                        // 内容重点摘取
                        String tempContent = Jsoup.clean(resultContent, Whitelist.none().addTags("strong"));
                        String[] tempArray = tempContent.split("strong");
                        String tempFix = "：";
                        String tempTitle="";
                        if(resultTitle.indexOf("晚报")!=-1){
                            for(String tStr :tempArray){
                                if(tStr.indexOf("<")!=-1&&tStr.indexOf(">")!=-1&&tStr.indexOf("[")!=-1&&tStr.indexOf("]")!=-1){
                                    String[] tempArrayZKH = tStr.split("]");
                                    if(tempArrayZKH.length>=2){
                                        tempArrayZKH = tempArrayZKH[1].split("<");
                                        tempTitle = tempArrayZKH[0];
                                    }
                                }
                            }
                            resultTitle = formatZero+"钢材市场日评";
                            if(StringUtils.isNotEmpty(tempTitle)){
                                resultTitle = resultTitle + tempFix+tempTitle;
                            }
                        }else if(resultTitle.indexOf("早报")!=-1){
                            for(String tStr :tempArray){
                                if(tStr.indexOf("<")!=-1&&tStr.indexOf(">")!=-1&&tStr.indexOf("[")!=-1&&tStr.indexOf("]")!=-1){
                                    String[] tempArrayZKH = tStr.split("]");
                                    if(tempArrayZKH.length>=2){
                                        tempArrayZKH = tempArrayZKH[1].split("<");
                                        tempTitle = tempTitle +" "+ tempArrayZKH[0];
                                    }
                                }
                            }
                            resultTitle = formatZero + "钢材市场早间评论";
                            if(StringUtils.isNotEmpty(tempTitle.trim())){
                                resultTitle = resultTitle + tempFix+tempTitle;
                            }
                        }
                        
                        resultContent = Jsoup.clean(resultContent, Whitelist.none().addTags("p","ul","li","br","table","th","tr","td").addAttributes("td","rowspan","colspan"));
                        resultContent = resultContent.replace("知名钢材现货交易平台——西本新干线", "").replace("西本新干线", "");
                        
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
                                           + "新干线</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                       } else */if (flg == null){
                           errContentUrl = errContentUrl + "数据存数据库失败！请联系网页抓取开发者！<a href='" + urlmap.get(resultTitle) +"'>"
                                   + "<font color='#FF0000'>" +resultTitle+"</font></a><br />";
                           /*LogUtil.getInstance().log(
                                   "caiji-auto", PRICE_OPERTION, null, "{'title':'"+resultTitle.substring(5, resultTitle.length())+"("+typeId+")','type':'failure','url':'<a href='" + urlmap.get(resultTitle) +"'>"
                                           + "新干线</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");*/
                           flagFailure++;
                       }
                    }
                }
            }

        } while (false);
        if (flagFailure != 0){
            String content = "钢铁早晚评"+"("+typeId+")"+"未抓取"+flagFailure+"条。分别为：<br />"
                    +errContentUrl + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_XGX.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'钢铁早晚评("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                            + "新干线</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("content", content);
            MailUtil.getInstance().sendMail(
                    "钢铁早晚评自动抓取报错", 
                    "zz91.price.caiji.auto@asto.mail", null,
                    null, "zz91", "blank",
                    map, MailUtil.PRIORITY_DEFAULT);
            return false;
        } else {
            String catchTime = DateUtil.toString(new Date(), DATE_FORMAT).substring(11,16);
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'钢铁早晚评("+typeId+")','type':'success','url':'<a href='" + url +"' target='_blank'>"
                            + "新干线</a>','defaultTime':'"+defaultTime+"','catchTime':'"+catchTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            return true;
        }
    }

    @Override
    public boolean clear(Date baseDate) throws Exception {
        return false;
    }

    
    public static void main(String[] args) {

        DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
        CaijiSteelMorEveBaoTask js=new CaijiSteelMorEveBaoTask();
        try {
            js.exec(DateUtil.getDate("2015-10-29", "yyyy-MM-dd"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
