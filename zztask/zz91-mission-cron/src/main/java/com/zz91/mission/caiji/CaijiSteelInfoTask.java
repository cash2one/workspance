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
import com.zz91.util.lang.StringUtils;
import com.zz91.util.log.LogUtil;
import com.zz91.util.mail.MailUtil;

public class CaijiSteelInfoTask implements ZZTask{
    
    final static String PRICE_OPERTION = "price_caiji";
    final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    CaiJiCommonOperate operate = new CaiJiCommonOperate();
    final static Map<String, String> STEELCN_MAP = new HashMap<String, String>();
    static{
        // httpget 使用的编码 不然会有乱码
        STEELCN_MAP.put("charset", "gb2312");
        // 采集地址
        STEELCN_MAP.put("url", "http://www.opsteel.cn/news/ynsd.html");
        // 后台类别对应采集地址 的key
        STEELCN_MAP.put("216", ""); //全国废金属行情
        
        STEELCN_MAP.put("listStart", "<form name=\"frmAction\" id=\"frmAction\" method=\"POST\" action=\"/opsteel/news/list.jsp\">"); // 列表页开头
        STEELCN_MAP.put("listEnd", "<link href=\"/opsteel/css/page.css\" rel=\"stylesheet\" type=\"text/css\" />"); // 列表页结尾
        
        STEELCN_MAP.put("contentStart", "<div id=\"articlebody\">");
        STEELCN_MAP.put("contentEnd", "<input type=\"hidden\" id=\"hidNewstype\" value=\"<a href='/news/ynsd.html'>业内视点</a>\"/>");
        
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
        String url = STEELCN_MAP.get("url");
        Map<String, String> urlmap = new HashMap<String, String>();
        String errContentUrl = "";
        do {
            // 获取list 列表
            String result = operate.getListContent(STEELCN_MAP, typeId);
            if (result == null) {
                errContentUrl = errContentUrl + " 抓取来源网站内容失败！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>欧浦钢网-钢铁资讯"+"("+typeId+")"+"</font></a>查看来源网站是否更改地址！<br />"
                                + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_steelcn.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'钢铁资讯("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "欧浦钢网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "钢铁资讯自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
            // 分解list 为多个元素
            String[] str = result.split("</span></li>");

            // 检查日期是否对应今天
            String format = DateUtil.toString(baseDate, "MM-dd");
            String formatZero = DateUtil.toString(baseDate, "M-d");
            List<String> list = new ArrayList<String>();
            
            for (String s : str) {
            	s=s.replaceAll(",", "");
                if (s.lastIndexOf(format) != -1 || s.lastIndexOf(formatZero) != -1) {
                    list.add(s);
                    // 最新10条
                    if(list.size()>=10){
                        break;
                    }
                }
            }
            if(list.size()<1){
                errContentUrl = errContentUrl + " 抓取失败，来源网站没有数据可抓取！请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>欧浦钢网-钢铁资讯"+"("+typeId+")"+"</font></a>查看来源网站<br />"
                                + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_steelcn.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
                LogUtil.getInstance().log(
                        "caiji-auto", PRICE_OPERTION, null, "{'title':'钢铁资讯("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                                + "欧浦钢网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("content", errContentUrl);
                MailUtil.getInstance().sendMail(
                        "钢铁资讯自动抓取报错", 
                        "zz91.price.caiji.auto@asto.mail", null,
                        null, "zz91", "blank",
                        map, MailUtil.PRIORITY_DEFAULT);
                return false;
            }
            if (list.size() < 10) {
                flagFailure = 10 - list.size();
                errContentUrl = errContentUrl + "来源网站未抓取到："+flagFailure+"条----请点击<a href='" +  url +"'>"
                        + "<font color='#FF0000'>欧浦钢网-钢铁资讯"+"("+typeId+")"+"</font></a> 查看来源网站<br />-------------------------------------------------"
                        + "<br />";
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
                        String resultContent ="";
                        //重新组合资讯的地址
                        String addr="http://www.opsteel.cn"+alink[1];
                        resultContent = operate.getContent(STEELCN_MAP,addr);
                        // 获取内容
//                      resultContent = resultContent.toLowerCase();
                        resultContent = Jsoup.clean(resultContent, Whitelist.none().addTags("p","ul","li","br","table","th","tr","td","img","div").addAttributes("td","rowspan").addAttributes("img", "src"));
                        resultContent=resultContent.replace("<div>", "<p>").replace("</div>", "</p>");
                        
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
                                           + "中国钢材网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
                       } else */if (flg == null){
                           errContentUrl = errContentUrl + "数据存数据库失败！请联系网页抓取开发者！<a href='" + urlmap.get(resultTitle) +"'>"
                                   + "<font color='#FF0000'>" +resultTitle+"</font></a><br />";
                          /* LogUtil.getInstance().log(
                                   "caiji-auto", PRICE_OPERTION, null, "{'title':'"+resultTitle.substring(5, resultTitle.length())+"("+typeId+")','type':'failure','url':'<a href='" + urlmap.get(resultTitle) +"'>"
                                           + "中国钢材网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");*/
                           flagFailure++;
                       }
                    }
                }
            }
            
        } while (false);
        if (flagFailure != 0){
            String content = "钢铁资讯"+"("+typeId+")"+"未抓取"+flagFailure+"条。分别为：<br />"
                    +errContentUrl + "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_steelcn.htm?typeId="+typeId +"'>"
                            + "<font color='#FF0000'>抓取</font></a>！";
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'钢铁资讯("+typeId+")','type':'failure','url':'<a href='" + url +"' target='_blank'>"
                            + "欧浦钢网</a>','defaultTime':'"+defaultTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("content", content);
            MailUtil.getInstance().sendMail(
                    "钢铁资讯自动抓取报错", 
                    "zz91.price.caiji.auto@asto.mail", null,
                    null, "zz91", "blank",
                    map, MailUtil.PRIORITY_DEFAULT);
            return false;
        } else {
            String catchTime = DateUtil.toString(new Date(), DATE_FORMAT).substring(11,16);
            LogUtil.getInstance().log(
                    "caiji-auto", PRICE_OPERTION, null, "{'title':'钢铁资讯("+typeId+")','type':'success','url':'<a href='" + url +"' target='_blank'>"
                            + "欧浦钢网</a>','defaultTime':'"+defaultTime+"','catchTime':'"+catchTime+"','date':'"+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
            return true;
        }
    }

    @Override
    public boolean clear(Date baseDate) throws Exception {
        return false;
    }

    public static void main(String[] args) {
        DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
        CaijiSteelInfoTask js=new CaijiSteelInfoTask();
        try {
            js.exec(DateUtil.getDate("2015-06-25", "yyyy-MM-dd"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
