package com.zz91.mission.caiji;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
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

public class CaijiShanghaiCopperTask implements ZZTask{
    
    final static String PRICE_OPERTION = "price_caiji";
    final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    CaiJiCommonOperate operate = new CaiJiCommonOperate();

    final static Map<String, String> SHFE_MAP = new HashMap<String, String>();
    static{
        // httpget 使用的编码 不然会有乱码
        SHFE_MAP.put("charset", "GBK");
        SHFE_MAP.put("72", "http://www.shfe.com.cn/statements/delaymarket_cu.html");
        
        SHFE_MAP.put("contentStart", "<table width=\"100%\" border=\"1\" bordercolor=\"#000000\" class=\"mytable\" align=\"center\">");
        SHFE_MAP.put("contentEnd", "</table>");
        
        
    }

    @Override
    public boolean init() throws Exception {
        return false;
    }

    @Override
    public boolean exec(Date baseDate) throws Exception {
        String defaultTime = DateUtil.toString(baseDate, DATE_FORMAT).substring(11,16);
        int flagFailure = 0 ;
        Integer typeId = 72;
        String url = "";
        Map<String, String> urlmap = new HashMap<String, String>();
        String errContentUrl = "";
        do {
            String content ="";
            try {
                url = SHFE_MAP.get(String.valueOf(typeId));
                content = operate.httpClientHtml(url, SHFE_MAP.get("charset"));
            } catch (HttpException e) {
                content = null;
            } catch (IOException e) {
                content = null;
            }
            if(content==null){
                errContentUrl = errContentUrl + " 抓取来源网站内容失败！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>上期所-沪铜"+"("+typeId+")"+"</font></a>查看来源网站是否更改地址！<br />"
                                + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_shfe.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'沪铜("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "上期所</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "沪铜自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
            String contentResult = "";
            Integer contentStart = content.indexOf(SHFE_MAP.get("contentStart"));
            Integer contentEnd = content.indexOf(SHFE_MAP.get("contentEnd"));
            contentResult = operate.subContent(content, contentStart, contentEnd+8);
            if (StringUtils.isEmpty(contentResult)) {
                break;
            }

            // 检查日期是否对应今天
            String format = DateUtil.toString(baseDate, "yyyy-MM-dd");
            String formatCN = DateUtil.toString(baseDate, "MM月dd日");
            
            if (contentResult.indexOf(format)!= -1) {
                String resultTitle = "";
                int indexStart = contentResult.indexOf(format);
                String time = contentResult.substring(indexStart + 11, indexStart + 13);
                if (!StringUtils.isNumber(time)) {
                    time = "09:30";
                } else {
                    if(Integer.parseInt(time) >= 11){
                        time = Integer.parseInt(time)-1 +":30";
                    } else {
                        time = "09:30";
                    }
                }
                
                resultTitle = formatCN + "沪铜最新报价" + time;
                
                String typeName = operate.queryTypeNameByTypeId(typeId);
                
                contentResult = contentResult.replace("\n", "");
                contentResult = contentResult.replace("<tr>     <td colspan=\"7\">", "<tr><td colspan=\"7\">");
                Integer start = contentResult.indexOf("<td colspan=\"7\">");
                Integer end = contentResult.indexOf("</font></a>");
                String resultContentFirst = contentResult.substring(0, start-4);
                String resultContentEnd = contentResult.substring(end, contentResult.length());
                String resultContent = resultContentFirst + resultContentEnd;
                resultContent = Jsoup.clean(
                        resultContent,Whitelist
                                .none()
                                .addTags("p", "table", "th", "tr","td","b","br")
                                .addAttributes("td", "rowspan"));
                resultContent = resultContent.replace("<td> </td>", "");
                resultContent = resultContent.replace("<td>【走势图】</td>", "");
                
                Price price = new Price();
                price.setTitle(resultTitle);
                price.setTypeId(typeId);
                price.setTags(typeName);
                price.setContent(resultContent);
                price.setGmtOrder(baseDate);

                urlmap.put(resultTitle, url);

                Integer flg = operate.doInsert(price,true);
               //执行插入并判断插入是否成功
               /*if (flg != null && flg > 0) {
                   LogUtil.getInstance().log(
                           "caiji-auto", PRICE_OPERTION, null, "{'title':'"+resultTitle.substring(5, resultTitle.length())+"("+typeId+")','type':'success','url':'<a href='" + urlmap.get(resultTitle) +"'>"
                                   + "上期所</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
               } else*/ if (flg == null){
                   errContentUrl = errContentUrl + "数据存数据库失败！请联系网页抓取开发者！<a href='" + urlmap.get(resultTitle) +"'>"
                           + "<font color='#FF0000'>" +resultTitle+"</font></a><br />";
                   /*LogUtil.getInstance().log(
                           "caiji-auto", PRICE_OPERTION, null, "{'title':'"+resultTitle.substring(5, resultTitle.length())+"("+typeId+")','type':'failure','url':'<a href='" + urlmap.get(resultTitle) +"'>"
                                   + "上期所</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");*/
                   flagFailure++;
               }
            } else {
                errContentUrl = errContentUrl + " 抓取失败，来源网站没有数据可抓取！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>上期所-沪铜"+"("+typeId+")"+"</font></a>查看来源网站<br />"
                                + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_shfe.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'沪铜("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "上期所</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "沪铜自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
        } while (false);
        if (flagFailure != 0){
            String content = "沪铜"+"("+typeId+")"+"未抓取"+flagFailure+"条。分别为：<br />"
                    +errContentUrl + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_shfe.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'沪铜("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                            + "上期所</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("content", content);
            MailUtil.getInstance().sendMail(
                    "沪铜自动抓取报错", 
                    "zz91.price.caiji.auto@asto.mail", null,
                    null, "zz91", "blank",
                    map, MailUtil.PRIORITY_DEFAULT);
            return false;
        } else {
            String catchTime = DateUtil.toString(new Date(), DATE_FORMAT).substring(11,16);
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'沪铜("+typeId+")','type':'success','url':'<a href='" + url +"' target='_blank'>"
                            + "上期所</a>','defaultTime':'"+defaultTime+"','catchTime':'"+catchTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            return true;
        }
    }

    @Override
    public boolean clear(Date baseDate) throws Exception {
        return false;
    }

    public static void main(String[] args) {
        
        DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
        CaijiShanghaiCopperTask js=new CaijiShanghaiCopperTask();
        try {
            js.exec(DateUtil.getDate("2014-3-11", "yyyy-MM-dd"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}