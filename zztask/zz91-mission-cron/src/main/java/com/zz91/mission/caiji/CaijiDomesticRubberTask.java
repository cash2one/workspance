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
import com.zz91.util.lang.StringUtils;
import com.zz91.util.log.LogUtil;
import com.zz91.util.mail.MailUtil;

public class CaijiDomesticRubberTask implements ZZTask{
    
    final static String PRICE_OPERTION = "price_caiji";
    final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    CaiJiCommonOperate operate = new CaiJiCommonOperate();

    final static Map<String, String> DOMESTICRUBBER_MAP = new HashMap<String, String>();
    static{
        // httpget 使用的编码 不然会有乱码
        DOMESTICRUBBER_MAP.put("charset", "GBK");
        //采集地址 
        DOMESTICRUBBER_MAP.put("url", "http://www.l-zzz.com/chemical/list.jsp?nChannelID=1992");
        DOMESTICRUBBER_MAP.put("contentUrl", "http://www.l-zzz.com");
        DOMESTICRUBBER_MAP.put("listStart", "<td align=\"left\" valign=\"top\" style=\"border:#CCCCCC 1px solid;\">"); // 列表页开头
        DOMESTICRUBBER_MAP.put("listEnd", "<td height=\"25\" align=\"center\" style=\"padding-top:4px;\">"); // 列表页结尾
        
        DOMESTICRUBBER_MAP.put("contentStart", "<DIV class=NewsContent>");
        DOMESTICRUBBER_MAP.put("contentEnd", "（责任编辑");
        DOMESTICRUBBER_MAP.put("split", "</table>");
        DOMESTICRUBBER_MAP.put("splitLink", "\"");
        
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
        Integer typeId = 30;
        String url = "";
        do {
            String content ="";
            try {
                url = DOMESTICRUBBER_MAP.get("url");
                content = operate.httpClientHtml(url, DOMESTICRUBBER_MAP.get("charset"));
            } catch (HttpException e) {
                content = null;
            } catch (IOException e) {
                content = null;
            }
            if(content==null){
                errContentUrl = errContentUrl + " 抓取来源网站内容失败！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>中塑资讯-国内橡胶"+"("+typeId+")"+"</font></a>查看来源网站是否更改地址！<br />"
                                + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_lzzz_domesticrubber.htm?typeId="+typeId +"'>"
                                        + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'国内橡胶("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "中塑资讯</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "国内橡胶自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
            Integer start = content.indexOf(DOMESTICRUBBER_MAP.get("listStart"));
            Integer end = content.indexOf(DOMESTICRUBBER_MAP.get("listEnd"));
            String result = content.substring(start, end);
            String[] str = result.split(DOMESTICRUBBER_MAP.get("split"));

            // 检查日期是否对应今天
            String formatDate = DateUtil.toString(baseDate, "yyyy-MM-dd");
            String format = DateUtil.toString(baseDate, "MM月dd日");
            List<String> list = new ArrayList<String>();
            for (String s : str) {
                if (s.indexOf(formatDate) != -1) {
                        list.add(s);
                        if (list.size()>7) {
                            break;
                        }
                }
            }
            if(list.size()<1){
                errContentUrl = errContentUrl + " 抓取失败，来源网站没有数据可抓取！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>中塑资讯-国内橡胶"+"("+typeId+")"+"</font></a>查看来源网站<br />"
                                + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_lzzz_domesticrubber.htm?typeId="+typeId +"'>"
                                        + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'国内橡胶("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "中塑资讯</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "国内橡胶自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
            if (list.size() < 8) {
                flagFailure = 8 - list.size();
                errContentUrl = errContentUrl + "来源网站未抓取到："+flagFailure+"条----请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>中塑资讯-国内橡胶"+"("+typeId+")"+"</font></a> 查看来源网站<br />-------------------------------------------------"
                        + "<br />";
            }
            String typeName = operate.queryTypeNameByTypeId(typeId);
            
            for(String aStr:list){
                String linkStr = operate.getAlink(aStr);
                if(StringUtils.isEmpty(linkStr)){
                    continue;
                }else{
                    String[] alink = linkStr.split(DOMESTICRUBBER_MAP.get("splitLink"));
                    if(alink.length>0){
                        String resultTitle =format + Jsoup.clean(linkStr, Whitelist.none());
                        urlmap.put(resultTitle,  DOMESTICRUBBER_MAP.get("url"));
                        String resultContent ="";
                        String newUrl = DOMESTICRUBBER_MAP.get("contentUrl") + alink[3]; 
                        resultContent = operate.getContent(DOMESTICRUBBER_MAP, newUrl);
                        // 获取内容
//                      resultContent = resultContent.toLowerCase();
                        resultContent = Jsoup.clean(resultContent, Whitelist.none().addTags("p","ul","li","table","th","tr","td").addAttributes("td","rowspan"));
                        resultContent = resultContent.replace("<p></p>", "");
                        Price price = new Price();
                        price.setTitle(resultTitle);
                        price.setTypeId(typeId);
                        price.setTags(typeName);
                        price.setContent(resultContent);
                        price.setGmtOrder(baseDate);
                        
                        Integer flg = operate.doInsert(price,false);
                        //执行插入并判断插入是否成功
                       /* if (flg != null && flg > 0) {
                            LogUtil.getInstance().log(
                                    "caiji-auto", PRICE_OPERTION, null, "{'title':'"+resultTitle.substring(5, resultTitle.length())+"("+typeId+")','type':'success','url':'<a href='" + urlmap.get(resultTitle) +"'>"
                                            + "中塑资讯</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                        } else*/ if (flg == null){
                            errContentUrl = errContentUrl + "数据存数据库失败！请联系网页抓取开发者！<a href='" + urlmap.get(resultTitle) +"'>"
                                    + "<font color='#FF0000'>" +resultTitle+"</font></a><br />";
                            /*LogUtil.getInstance().log(
                                    "caiji-auto", PRICE_OPERTION, null, "{'title':'"+resultTitle.substring(5, resultTitle.length())+"("+typeId+")','type':'failure','url':'<a href='" + urlmap.get(resultTitle) +"'>"
                                            + "中塑资讯</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");*/
                            flagFailure++;
                        }
                    }
                }
            }
            
        } while (false);
        if (flagFailure != 0){
            String content = "国内橡胶"+"("+typeId+")"+"未抓取"+flagFailure+"条。分别为：<br />"
                    +errContentUrl + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_lzzz_domesticrubber.htm?typeId="+typeId +"'><font color='#FF0000'>抓取</font></a>！";
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'国内橡胶("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                            + "中塑资讯</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("content", content);
            MailUtil.getInstance().sendMail(
                    "国内橡胶自动抓取报错", 
                    "zz91.price.caiji.auto@asto.mail", null,
                    null, "zz91", "blank",
                    map, MailUtil.PRIORITY_DEFAULT);
            return false;
        } else {
            String catchTime = DateUtil.toString(new Date(), DATE_FORMAT).substring(11,16);
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'国内橡胶("+typeId+")','type':'success','url':'<a href='" + url +"' target='_blank'>"
                            + "中塑资讯</a>','defaultTime':'"+defaultTime+"','catchTime':'"+catchTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            return true;
        }
    }

    @Override
    public boolean clear(Date baseDate) throws Exception {
        return false;
    }

    public static void main(String[] args) {
        
        DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
        CaijiDomesticRubberTask js=new CaijiDomesticRubberTask();
        try {
            js.exec(DateUtil.getDate("2013-10-29", "yyyy-MM-dd"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
