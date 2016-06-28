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

public class CaijiHuanTongSpotTask implements ZZTask{
    
    final static String PRICE_OPERTION = "price_caiji";
    final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    CaiJiCommonOperate operate = new CaiJiCommonOperate();

    final static Map<String, String> HUATONGSPOT_MAP = new HashMap<String, String>();
    static{
        // httpget 使用的编码 不然会有乱码
        HUATONGSPOT_MAP.put("charset", "gb2312");
        // 采集地址
        HUATONGSPOT_MAP.put("url", "http://www.ometal.com/bin/new/searchkey.asp?type=%BB%AA%CD%A8%D3%D0%C9%AB");
        HUATONGSPOT_MAP.put("contentUrl", "http://www.ometal.com");
        HUATONGSPOT_MAP.put("listStart", "搜索结果："); // 列表页开头
        HUATONGSPOT_MAP.put("listEnd", "上一页"); // 列表页结尾
        
        HUATONGSPOT_MAP.put("contentStart", "<div id=\"fontzoom\">");
        HUATONGSPOT_MAP.put("contentEnd", "OMETAL.COM)");
        
    }

    @Override
    public boolean init() throws Exception {
        return false;
    }

    @Override
    public boolean exec(Date baseDate) throws Exception {
        String defaultTime = DateUtil.toString(baseDate, DATE_FORMAT).substring(11,16);
        int flagFailure = 0 ;
        String time = DateUtil.toString(baseDate, DATE_FORMAT).substring(11, 13);
        String url = "";
        Map<String, String> urlmap = new HashMap<String, String>();
        String errContentUrl = "";
        Integer typeId = 83;
        do {
            String content ="";
            url = HUATONGSPOT_MAP.get("url");
			content = HttpUtils.getInstance().httpGet(url, HUATONGSPOT_MAP.get("charset"));
            if(content==null){
                errContentUrl = errContentUrl + " 抓取来源网站内容失败！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>全球金属网-华通现货"+"("+typeId+")"+"</font></a>查看来源网站是否更改地址！<br />"
                                + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_ometal_huatongspot.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'华通现货("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "全球金属网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "华通现货自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
            Integer start = content.indexOf(HUATONGSPOT_MAP.get("listStart"));
            Integer end = content.indexOf(HUATONGSPOT_MAP.get("listEnd"));
            String result = content.substring(start, end);
            String[] str = result.split("</tr>");

            // 检查日期是否对应今天
            String format = DateUtil.toString(new Date(), "yyyy-M-d");
            String formatDate = DateUtil.toString(new Date(), "MM月dd日");
            List<String> list = new ArrayList<String>();
            
            for (String s : str) {
                if (s.indexOf(format) != -1) {
                    if (Integer.valueOf(time) < 13 && s.contains("上午")) {
                        list.add(s);
                    } else if (s.contains("下午")){
                        list.add(s);
                    }
                    
                }
            }
            if(list.size()<1){
                errContentUrl = errContentUrl + " 抓取失败，来源网站没有数据可抓取！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>全球金属网-华通现货"+"("+typeId+")"+"</font></a>查看来源网站<br />"
                                + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_ometal_huatongspot.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'华通现货("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "全球金属网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "华通现货自动抓取报错", 
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
                        String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
                        if (resultTitle.contains("上午")) {
                            resultTitle = formatDate +"华通有色金属价格" +"(上午)";
                        } else if (resultTitle.contains("下午")) {
                            resultTitle = formatDate +"华通有色金属价格" +"(下午)";
                        }
                        String resultContent ="";
                        String newUrl = HUATONGSPOT_MAP.get("contentUrl") + alink[1]; 
                        resultContent = operate.getContent(HUATONGSPOT_MAP, newUrl);
                        // 获取内容
//                      resultContent = resultContent.toLowerCase();
                        resultContent = Jsoup.clean(resultContent, Whitelist.none().addTags("div","p","ul","br","li","table","th","tr","td").addAttributes("td","rowspan"));
                        
                        if (resultContent.indexOf("单位:元/吨") != -1) {
                            resultContent = resultContent.replace("单位:元/吨", "单位：元/吨");
                        } 
                        int resultEnd= resultContent.indexOf("单位：元/吨");
                        resultContent = resultContent.replace(resultContent.substring(5, resultEnd), "");
                        resultContent = resultContent.replace("(全球金属网", "");
                        resultContent = resultContent.replace("-----------------------------------------------------------------------", "");
                        resultContent = resultContent.replace("&nbsp;", "");
                        resultContent = resultContent.replace("\n", "");
                        resultContent = resultContent.replace("  ", " ");
                        resultContent = resultContent.replace("<div>", "<table><tr><td>");
                        resultContent = resultContent.replace("<br />", "</td></tr><tr><td>");
                        resultContent = resultContent.replace(" ", "</td><td>");
                        resultContent = resultContent.replace("</div>", "</td></tr></table>");
                        resultContent = resultContent.replace("<td></td>", "");
                        resultContent = resultContent.replace("<tr></tr>", "");
                        resultContent = resultContent.replace("<td>单位：元/吨</td>", "<td colspan=\"8\">单位：元/吨</td>");
                        
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
                                           + "全球金属网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                       } else*/ if (flg == null){
                           errContentUrl = errContentUrl + "数据存数据库失败！请联系网页抓取开发者！<a href='" + urlmap.get(resultTitle) +"'>"
                                   + "<font color='#FF0000'>" +resultTitle+"</font></a><br />";
                           /*LogUtil.getInstance().log(
                                   "caiji-auto", PRICE_OPERTION, null, "{'title':'"+resultTitle.substring(5, resultTitle.length())+"("+typeId+")','type':'failure','url':'<a href='" + urlmap.get(resultTitle) +"'>"
                                           + "全球金属网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");*/
                           flagFailure++;
                       }
                    }
                }
            }
            
        } while (false);
        if (flagFailure != 0){
            String content = "华通现货"+"("+typeId+")"+"未抓取"+flagFailure+"条。分别为：<br />"
                    +errContentUrl + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_ometal_huatongspot.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'华通现货("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                            + "全球金属网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("content", content);
            MailUtil.getInstance().sendMail(
                    "华通现货自动抓取报错", 
                    "zz91.price.caiji.auto@asto.mail", null,
                    null, "zz91", "blank",
                    map, MailUtil.PRIORITY_DEFAULT);
            return false;
        } else {
            String catchTime = DateUtil.toString(new Date(), DATE_FORMAT).substring(11,16);
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'华通现货("+typeId+")','type':'success','url':'<a href='" + url +"' target='_blank'>"
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
        CaijiHuanTongSpotTask js=new CaijiHuanTongSpotTask();
        try {
            js.exec(DateUtil.getDate("2014-09-03", "yyyy-MM-dd"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
