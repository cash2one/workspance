package com.zz91.mission.caiji;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.zz91.mission.domain.subscribe.Price;
import com.zz91.mission.domain.subscribe.PriceCategory;
import com.zz91.mission.domain.subscribe.PriceTemp;
import com.zz91.util.analzyer.IKAnalzyerUtils;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;

public class CaiJiCommonOperate {
    
    public static String DB="ast";
    public static String file = "file:/usr/tools/config/address/zz91.properties";

    /**
     * 插入报价 通用接口
     * @param priceDO
     * @return
     * @throws ParseException
     * @throws IOException 
     * @throws URISyntaxException 
     * @throws ClientProtocolException 
     */
    
    public Integer doInsert(Price price,Boolean isUpDown) throws ParseException, ClientProtocolException, URISyntaxException, IOException{
        // 标题 MM月dd日 格式构建
        price.setTitle(getDateFormatTitle(price.getTitle()));
//        String priceSearchKey = null;
//        String zaobaoSearchKeyString = null;
//        String wanbaoSearchKeyString =null;
//        String code = queryPriceTtpePlasticOrMetal(price.getTypeId());
//        if (StringUtils.isNotEmpty(code)) {
//            if ("废金属".equals(code)) {
//                priceSearchKey = "金属";
//                zaobaoSearchKeyString = "金属早参";
//                wanbaoSearchKeyString ="金属晚报";
//            } else {
//                if ("废塑料".equals(code)) {
//                    priceSearchKey = "塑料";
//                }
//                if ("废纸".equals(code)) {
//                    priceSearchKey = "废纸";
//                }
//                if ("废橡胶".equals(code)) {
//                    priceSearchKey = "橡胶";
//                }
//                if ("原油".equals(code)) {
//                    priceSearchKey = "原油";
//                }
//                zaobaoSearchKeyString = "塑料早参";
//                wanbaoSearchKeyString = "废塑料晚报";
//            }
//        }
        
        // 标签构建tags
        List<String> list = IKAnalzyerUtils.getAnalzyerList(price.getTitle());
        Set<String> sList = new HashSet<String>();
        for (String s : list) {
            String str = s.toUpperCase();
            sList.add(str);
        }
        for (String string : sList) {
            if (string.indexOf("月") == -1 && string.indexOf("日") == -1
                    && !StringUtils.isNumber(string) && string.length() > 1 && string.indexOf(".")==-1 && string.indexOf("年") ==-1) {
                price.setTags(price.getTags() + "," + string);
            }
        }

      //插入辅助类别 
        String sql="select parent_id from price_category where id="+price.getTypeId(); 
        final PriceCategory priceCategory = new PriceCategory();
        DBUtils.select(DB, sql, new IReadDataHandler() {
            
            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                if(rs.next()){
                    priceCategory.setParentId(rs.getInt(1));
                    
                }
            }
        });
        Integer parentId = priceCategory.getParentId();
        while (parentId != null && parentId != 0 && parentId != 5 && parentId != 6 ) {
            sql="select parent_id from price_category where id="+parentId;
            DBUtils.select(DB, sql, new IReadDataHandler() {
                
                @Override
                public void handleRead(ResultSet rs) throws SQLException {
                    if(rs.next()){
                        priceCategory.setParentId(rs.getInt(1));
                        
                    }
                }
            });
            parentId = priceCategory.getParentId();
        }
        if (parentId != null && parentId != 0) {
            if (parentId == 5) {
                parentId = 3;
            } else if (parentId == 6) {
                parentId = 4;
            }
           
          
          String listSql="select id,parent_id,name,is_delete,show_index from price_category where '0'>=is_delete " +
                  "and parent_id="+parentId+"  order by show_index";
          final List<PriceCategory> categoryList=new ArrayList<PriceCategory>();
          DBUtils.select("ast", listSql, new IReadDataHandler() {
              
              @Override
              public void handleRead(ResultSet rs) throws SQLException {
                  while (rs.next()) {
                      PriceCategory priceCategory =new PriceCategory();
                      priceCategory.setId(rs.getInt(1));
                      priceCategory.setParentId(rs.getInt(2));
                      priceCategory.setName(rs.getString(3));
                      categoryList.add(priceCategory);
                  }
              }
          });
            String tag = price.getTags();
            String[] tags = tag.split(",");
            String tmpStr = "";
            for (String str : tags) {
                for (PriceCategory catdo : categoryList) {

                    
                    if (parentId == 3) {
                        if (str.contains("浙江") || str.contains("上海")
                                || str.contains("江苏") || str.contains("沪") || str.contains("无锡")) {
                            price.setAssistTypeId(53);
                            break;
                        } else if (str.contains("佛山") || str.contains("广州")) {
                            price.setAssistTypeId(180);
                            break;
                        } else if (str.contains("河南")) {
                            price.setAssistTypeId(58);
                            break;
                        } else if (str.contains("西安")) {
                            price.setAssistTypeId(253);
                            break;
                        } else if (str.contains("昆明")) {
                            price.setAssistTypeId(252);
                            break;
                        } else if (str.contains("沈阳")) {
                            price.setAssistTypeId(254);
                            break;
                        } else if (str.contains("济南")) {
                            price.setAssistTypeId(314);
                            break;
                        } else if (str.contains("武汉")) {
                            price.setAssistTypeId(262);
                            break;
                        } else if (str.equals("临沂") || str.equals("山东")) {
                            tmpStr = tmpStr + str;
                            if (str.contains("临沂")) {
                                price.setAssistTypeId(55);
                            } else if (str.contains("山东")){
                                price.setAssistTypeId(314);
                            }
                            if (tmpStr.contains("临沂") && tmpStr.contains("山东")) {
                                price.setAssistTypeId(55);
                                tmpStr = "";
                            }
                            break;
                        } else if (str.contains("天津") || str.contains("山东")) {
                            tmpStr = tmpStr + str;
                            if (str.contains("天津")) {
                                price.setAssistTypeId(239);
                            } else if (str.contains("山东")){
                                price.setAssistTypeId(314);
                            }
                            if (tmpStr.contains("天津") && tmpStr.contains("山东")) {
                                price.setAssistTypeId(181);
                                tmpStr = "";
                            }
                            break;
                        } else if (str.contains("华东") || str.contains("华北")) {
                            price.setAssistTypeId(179);
                            break;
                        }
                    }
                    if (parentId == 4) {
                        if (str.equals("HDPE")) {
                            price.setAssistTypeId(295);
                            break;
                        } else if (str.equals("CPP")) {
                            price.setAssistTypeId(323);
                            break;
                        } else if (str.equals("LLDPE")) {
                            price.setAssistTypeId(304);
                            break;
                        } else if (str.equals("LDPE")) {
                            price.setAssistTypeId(292);
                            break;
                        } else if (str.equals("PET")) {
                            price.setAssistTypeId(290);
                            break;
                        } else if (str.equals("ABS") || str.equals("PS")) {
                            tmpStr = tmpStr + str;
                            if (str.equals("ABS")) {
                                price.setAssistTypeId(296);
                            } else if (str.equals("PS")){
                                price.setAssistTypeId(294);
                            }
                            if (tmpStr.contains("ABS") && tmpStr.contains("PS")) {
                                price.setAssistTypeId(313);
                                tmpStr = "";
                            }
                            break;
                        }
                    }
                    if (str.contains(catdo.getName())) {
                        price.setAssistTypeId(catdo.getId());
                        break;
                    }
                }
            }
        }
        
        // 去除多余的空格
        price.setContent(price.getContent().replace("&nbsp;",""));
        price.setContent(price.getContent().replace("　",""));
        price.setContent(price.getContent().replace("<p>\n\t&nbsp;</p>\n",""));
        
        // br换行处理
        price.setContent(price.getContent().replace("<br>", "<br>　　"));
        price.setContent(price.getContent().replace("<br/>", "<br/>　　"));
        price.setContent(price.getContent().replace("<br />", "<br />　　"));

//        priceSearchKey = URLEncoder.encode(priceSearchKey,HttpUtils.CHARSET_UTF8);
//        zaobaoSearchKeyString = URLEncoder.encode(zaobaoSearchKeyString,HttpUtils.CHARSET_UTF8);
//        wanbaoSearchKeyString = URLEncoder.encode(wanbaoSearchKeyString,HttpUtils.CHARSET_UTF8);
        
//        if(isUpDown){
//            code = queryTypeNameByTypeId(price.getTypeId());
//            code = URLEncoder.encode(code,HttpUtils.CHARSET_UTF8);
//            
//            /*String url = "http://test.zz9l.com:8084/apps/buildTemplateContent.htm?priceSearchKey="+priceSearchKey+"&zaobaoSearchKeyString="+
//                    zaobaoSearchKeyString+"&wanbaoSearchKeyString="+wanbaoSearchKeyString+"&code="+code;*/
//            String url = readPropertiesForUrl(file) + "/apps/buildTemplateContent.htm?priceSearchKey="+priceSearchKey+"&zaobaoSearchKeyString="+
//                    zaobaoSearchKeyString+"&wanbaoSearchKeyString="+wanbaoSearchKeyString+"&code="+code;
//            String content = httpClientHtml(url, "utf-8");
//            if (StringUtils.isNotEmpty(content)) {
//            	  JSONObject jsonContent = JSONObject.fromObject(content);
//                  content = jsonContent.getString("content");
//                  content = content.replace("\'", "\\'");
//                  price.setContent(price.getContent()+content);
//            }
//        }else{
//           
//            /*String url = "http://test.zz9l.com:8084/apps/queryContentByPageEngine.htm?priceSearchKey="+priceSearchKey+"&zaobaoSearchKeyString="+
//                    zaobaoSearchKeyString+"&wanbaoSearchKeyString="+wanbaoSearchKeyString;*/
//            String url = readPropertiesForUrl(file) + "/apps/queryContentByPageEngine.htm?priceSearchKey="+priceSearchKey+"&zaobaoSearchKeyString="+
//                    zaobaoSearchKeyString+"&wanbaoSearchKeyString="+wanbaoSearchKeyString;
//            String content = httpClientHtml(url, "utf-8");
//            if (StringUtils.isNotEmpty(content)) {
//            	  JSONObject jsonContent = JSONObject.fromObject(content);
//                  content = jsonContent.getString("content");
//                  content = content.replace("\'", "\\'");
//                  price.setContent(price.getContent()+content);
//            }
//        }

        // 判断重复添加报价
        if(forbidDoublePub(price.getTitle(), null)){
            return 0;
        }
        if(price.getGmtCreated()==null){
        	price.setGmtCreated(new Date());
        }

        Integer impress = insertPrice(price);

        
        if(isUpDown){
            // 添加报价成功，添加数据进入关系表
            if (impress != null) {
                insert(impress);
            }
        }
        return impress;
    }
    public String queryPriceTtpePlasticOrMetal(Integer id) {
        String code = "";
        String sql="select parent_id,name from price_category where id="+id; 
        final PriceCategory priceCategoryById=new PriceCategory();
        DBUtils.select(DB, sql, new IReadDataHandler() {
            
            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                if(rs.next()){
                    priceCategoryById.setParentId(rs.getInt(1));
                    priceCategoryById.setName(rs.getString(2));
                    
                }
            }
        });
        
        if (id != null && id <= 8) {
            if (priceCategoryById != null) {
                code = priceCategoryById.getName();
            } 
        } else if (priceCategoryById != null) {
            Integer parentId = priceCategoryById.getParentId();
            for (int i = 0; i < 6; i++) {
                sql="select parent_id,name from price_category where id="+parentId;
                final PriceCategory priceCategoryByParentId=new PriceCategory();
                DBUtils.select(DB, sql, new IReadDataHandler() {
                    
                    @Override
                    public void handleRead(ResultSet rs) throws SQLException {
                        if(rs.next()){
                            priceCategoryByParentId.setParentId(rs.getInt(1));
                            priceCategoryByParentId.setName(rs.getString(2));
                            
                        }
                    }
                });
                if (priceCategoryByParentId != null) {
                    if (priceCategoryByParentId.getParentId() != null) {
                        if (priceCategoryByParentId.getParentId() == 1) {
                            code = priceCategoryByParentId.getName();
                            break;
                        } else {
                            parentId = priceCategoryByParentId.getParentId();
                        }
                    }
                }
            }
        }
        return code;
    }
    public String queryTypeNameByTypeId(Integer id) {
        String sql="select name from price_category where id="+id; 
        final PriceCategory priceCategory=new PriceCategory();
        DBUtils.select(DB, sql, new IReadDataHandler() {
            
            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                if(rs.next()){
                    priceCategory.setName(rs.getString(1));
                }
            }
        });
        return priceCategory.getName();
    };
    public Boolean forbidDoublePub(String title, Date date) {
        do {
            // 默认起始时间为 发报价当天零点
            if (date == null) {
                try {
                    date = DateUtil.getDate(new Date(), "yyyy-MM-dd");
                } catch (ParseException e) {
                    date = new Date();
                }
            }

            // 空判断
            if (StringUtils.isEmpty(title)) {
                break;
            }
            
            // 根据标题检索
            String time = DateUtil.toString(date,"yyyy-MM-dd");
            String sql="select id from price where title ='" +
            title+
            "' and gmt_created >= '"+time+"'"; 
            final Price price=new Price();
            DBUtils.select(DB, sql, new IReadDataHandler() {
                
                @Override
                public void handleRead(ResultSet rs) throws SQLException {
                    if(rs.next()){
                        price.setId(rs.getInt(1));
                    }
                }
            });
            Integer id = price.getId();
            if (id == null || id <= 0) {
                break;
            }
            return true;
        } while (false);
        return false;
    }
    public Integer insertPrice(Price price) throws IllegalArgumentException, ParseException {
        if (price.getIsChecked() == null) {
            price.setIsChecked("0");
        }
        if (price.getIsIssue() == null) {
            price.setIsIssue("0");
        }
        if (price.getAssistTypeId() == null) {
            price.setAssistTypeId(0);
        }
        if (price.getContent() != null && price.getContent().length() > 65535) {
            price.setContent(Jsoup.clean(price.getContent(), Whitelist
                    .relaxed()));
        }
        String date = DateUtil.toString(new Date(), "yyyy-MM-dd HH:mm:ss");
        String dateSFM = DateUtil.toString(new Date(), "HH:mm:ss");
        String gmtOrder = DateUtil.toString(price.getGmtOrder(), "yyyy-MM-dd "+dateSFM);
        String gmtCreated = DateUtil.toString(price.getGmtCreated(), "yyyy-MM-dd HH:mm:ss");
        String queryTime = DateUtil.toString(price.getGmtCreated(), "yyyy-MM-dd");
        String insertSql = "insert into price (title,type_id,"
                + "assist_type_id,content,"
                + "gmt_order,gmt_created,"
                + "is_checked,is_issue,"
                + "tags,gmt_modified)"
                + "values" + "('"
                        +price.getTitle()+"','"+price.getTypeId()+"',"
                        + "'"+price.getAssistTypeId()+"','"+price.getContent()+"',"
                        + "'"+gmtOrder+"','"+gmtCreated+"',"
                        + "'"+price.getIsChecked()+"','"+price.getIsIssue()+"',"
                        + "'"+price.getTags()+"','"+date+"')"; 
        DBUtils.insertUpdate(DB, insertSql);
     // 根据标题检索"
        String sql="select max(id) from price where title ='" +
                price.getTitle()+
        "' and gmt_created > '"+queryTime +
        "' and type_id = '"+price.getTypeId() +"'"; 
        final Price price1=new Price();
        DBUtils.select(DB, sql, new IReadDataHandler() {
            
            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                if(rs.next()){
                    price1.setId(rs.getInt(1));
                }
            }
        });
        Integer id = null ;
        if (price1.getId() != null) {
            id = price1.getId();
        }
        return id ;
    }
    public Boolean insert(Integer priceId) {
        do {
            if (priceId == null) {
                break;
            }
            String sql = "select id,price_id,gmt_created,gmt_modified from price_template where price_id ='" +priceId+"'limit 1";
            final PriceTemp priceTemplate=new PriceTemp();
            DBUtils.select(DB, sql, new IReadDataHandler() {
                
                @Override
                public void handleRead(ResultSet rs) throws SQLException {
                    if(rs.next()){
                        priceTemplate.setId(rs.getInt(1));
                        priceTemplate.setPriceId(rs.getInt(2));
                        priceTemplate.setGmtCreated(rs.getDate(3));
                        priceTemplate.setGmtModified(rs.getDate(4));
                    }
                }
            });
            if (priceTemplate.getPriceId() != null) {
                break;
            }
            return insertTemplate(priceId);
        } while (false);
        return false;
    }
    private Boolean insertTemplate(Integer priceId) {
        final PriceTemp priceTemplate = new PriceTemp();
        priceTemplate.setPriceId(priceId);
        
        String insertSql = "insert into price_template (price_id,gmt_created,"
                + "gmt_modified)"
                + "values" + "('"
                        +priceTemplate.getPriceId()+"',now(),now())";
               
                
         Boolean b = DBUtils.insertUpdate(DB, insertSql);
        
        
        return b ;
    }
    public String getListContent(Map<String, String>map,Integer typeId){
        String content = "";
        String url = map.get("url")+ map.get(""+typeId);
		content = HttpUtils.getInstance().httpGet(url,map.get("charset"));
        if (content == null) {
            return null;
        }
        Integer start = content.indexOf(map.get("listStart"));
        Integer end = content.indexOf(map.get("listEnd"));
        String result = subContent(content, start, end);
        if(StringUtils.isEmpty(result)){
            return null;
        }
        return result;
    }
    
    public String getContent(Map<String, String>map,String url){
        String content = "";
        content = HttpUtils.getInstance().httpGet(url,map.get("charset"));
        if (content == null) {
            return null;
        }
        Integer start = content.indexOf(map.get("contentStart"));
        Integer end = content.indexOf(map.get("contentEnd"));
        content = subContent(content,start, end);
        if(StringUtils.isEmpty(content)){
            return null;
        }
        return content;
    }
    
    public String subContent(String content,Integer start,Integer end){
        if(StringUtils.isEmpty(content)||start>=end){
            return null;
        }
        String result = content.substring(start, end);
        return result;
    }
    
    public String getAlink(String str){
        str = str.trim();
        Integer start = str.indexOf("<a");
        Integer end = str.indexOf("</a>");
        str = subContent(str, start, end+4);
        if(StringUtils.isEmpty(str)){
            return null;
        }
        return str;
    }
    
    public String httpClientHtml(String url , String strCharset) throws URISyntaxException, ClientProtocolException, IOException{
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpProtocolParams.setUserAgent(httpclient.getParams(),"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.9) Gecko/20100315 Firefox/3.5.9");
        String charset = strCharset;
//      if (null != params && params.length >= 1) {
//          charset = params[0];
//      }
        HttpGet httpget = new HttpGet();
        String content = "";
        httpget.setURI(new java.net.URI(url));
        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            // 使用EntityUtils的toString方法，传递默认编码，在EntityUtils中的默认编码是ISO-8859-1
            content = EntityUtils.toString(entity, charset);
            httpget.abort();
            httpclient.getConnectionManager().shutdown();
        }
        return content;
    }
    //读取网页中list全部的内容
    public Map<String,String> httpClientAllContent(Map<String, String>map , String formatDate , String tagsTitle) throws URISyntaxException, ClientProtocolException, IOException{
        DefaultHttpClient httpclient = new DefaultHttpClient();
        Map<String , String>  mapContent = new HashMap<String, String>();
        HttpProtocolParams.setUserAgent(httpclient.getParams(),"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.9) Gecko/20100315 Firefox/3.5.9");
        String charset = map.get("charset");
//      if (null != params && params.length >= 1) {
//          charset = params[0];
//      }
        HttpGet httpget = new HttpGet();
        String content = "";
        
        List<String> list = new ArrayList<String>();
        
        httpget.setURI(new java.net.URI(map.get("url")));
        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            // 使用EntityUtils的toString方法，传递默认编码，在EntityUtils中的默认编码是ISO-8859-1
            content = EntityUtils.toString(entity, charset);
            httpget.abort();
            httpclient.getConnectionManager().shutdown();
        }
        Integer start = content.indexOf(map.get("listStart"));
        Integer end = content.indexOf(map.get("listEnd"));
        String result = content.substring(start, end);
        String [] str = result.split(map.get("split"));
        
        for (String s:str) {
            if(s.indexOf(formatDate)!=-1){
                list.add(s);
            }
        }
        if(list.size()<1){
            return null;
        }
        String resultContent ="";
        for (String link:list) {
            String linkStr = getAlink(link);
            String[] alink = linkStr.split(map.get("splitLink"));
            if(alink.length>0){
                String resultTitle = Jsoup.clean(linkStr, Whitelist.none());
                String contentLink = "";
                for (String trueLink :alink) {
                    if (trueLink.contains("</a>")) {
                        trueLink = trueLink.replace("</a>", "");
                    }
                    if (trueLink.contains("/")) {
                        contentLink = trueLink;
                    }
                }
                String resultLink = urlFormat(map.get("contentLink") + contentLink);
                
                try {
                    resultContent = httpClientHtml(resultLink, map.get("charset"));
                } catch (IOException e) {
                }
                Integer cStart = resultContent.indexOf(map.get("contentStart"));
                Integer cEnd = resultContent.indexOf(map.get("contentEnd"));
                resultContent = resultContent.substring(cStart, cEnd);
                if (resultContent.contains(tagsTitle)) {
                    mapContent.put(resultTitle, resultContent);
                    mapContent.put("resultTitle", resultTitle);
                    return mapContent;
                }
            }
        }
        return null;
    }
    private String getDateFormatTitle(String title){
        do {
            if(title.indexOf("日")==-1||title.indexOf("月")==-1){
                break;
            }
            String strSplit = "";
            for (int k = 1 ; k < title.length() ; k++) {
                if (title.charAt(k)=='日' && StringUtils.isNumber(String.valueOf(title.charAt(k-1)))) {
                    strSplit = String.valueOf(title.charAt(k-1))+"日";
                    break;
                }
            }
            if (StringUtils.isEmail(strSplit)) {
                break;
            }
            String[] strArray = title.split(strSplit);
            strArray[0] += strSplit.substring(0,1);
            String[] resultArray = strArray[0].split("月");
            for (int i=0;i<resultArray.length;i++) {
                if(resultArray[i].length()==1){
                    resultArray[i] = "0"+resultArray[i];
                } else  if (resultArray[0].length() > 6 && !resultArray[0].contains("【")) {   //判断如果日期在后面则直接返回title。开发者：周宗坤 修改日期：2013-09-11
                	String months = resultArray[i].substring(resultArray[i].length()-2, resultArray[i].length());
                	String day = resultArray[i+1];
                	if(resultArray[i+1].length()==1) {
                		day = "0"+resultArray[i+1];
                	}
                	if (!StringUtils.isNumber(months)) {
                		months = "0" + resultArray[i].substring(resultArray[i].length()-1, resultArray[i].length());
                		title = resultArray[i].substring(0, resultArray[i].length()-1) + months +"月" + day + "日";
                		return title;
                	}
                	return resultArray[i] +"月" + day + "日";
                }else{
                	String dateStr = resultArray[i];
                	if (!StringUtils.isNumber(dateStr)) {
                		if (!StringUtils.isNumber(dateStr.substring(dateStr.length()-2, dateStr.length()))) {
							resultArray[i] = dateStr.substring(0, dateStr.length()-1) + "0" + dateStr.substring(dateStr.length()-1,dateStr.length());
						}
					}
                }
            }
            String result = "";
            if(resultArray.length==2&&strArray.length>=2){
                result = resultArray[0]+"月"+resultArray[1]+"日"+strArray[1];
            }
            if(strArray.length>2){
                int i=0;
                do {
                    if(i>=strArray.length){
                        break;
                    }
                    if(i>1){
                        result = result + "日"+strArray[i];
                    }
                    i++;
                } while (true);
            }
            return result;
        } while (false);
        return title;
    }
    private String urlFormat (String url) {
        String newUrl = url;
        if (newUrl.contains("/./")){
            newUrl = newUrl.replace("/./", "");
        }
        return newUrl;
    }
    public String dateFormat(String title) {
        do {
            if(title.indexOf("日")==-1 || title.indexOf("月")==-1){
                break;
            }
           int monthIndex = title.indexOf("月");
           int dayIndex = title.indexOf("日");
           String month = title.substring(monthIndex-2, monthIndex);
           String day = title.substring(dayIndex-2,dayIndex);
           if (!month.matches("-?\\d+\\.?\\d*")) {
               month = title.substring(monthIndex-1, monthIndex);
           }
           if (!day.matches("-?\\d+\\.?\\d*")) {
               day ="0" + title.substring(dayIndex-1, dayIndex);
           }
           String result = month + "月" + day + "日";
           return result;
        } while (false);
        return title;
    }
    
//    private String readPropertiesForUrl (String properties) {
//        InputStream is =null;
//        if(properties.startsWith("file:"))
//            try {
//                is=new FileInputStream(new File(properties.substring(5)));
//            } catch (FileNotFoundException e1) {
//                e1.printStackTrace();
//            }
//        else
//            is=FileUtils.class.getClassLoader().getResourceAsStream(properties);
//        Properties p = new Properties();
//        try {
//            p.load(is);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String host = p.getProperty("price-caiji");
//        return host;
//    }
    
    //判断字符串中是否含有英文
    public boolean hasLetter(String str){ 
              Pattern pattern = Pattern.compile("[a-zA-Z]+"); 
              Matcher m = pattern.matcher(str);
              return m.find();     
     }
    //将标题中的小写字母转成大写字母
    public String changeLetter(String str) {
        return str.toUpperCase();
    }
    //截取标题中最后出现的数字或字母,并替换标题内容。注：截取数字因为“生意社”的CaijiSocialBusinessTask任务的标题塑料后带有数字
    public String splitTitle(String title , String repTitle) {
        StringBuilder sb = new StringBuilder(title);
        sb = sb.reverse();
        Pattern pattern = Pattern.compile("[a-zA-Z0-9]");
        Matcher matcher = pattern.matcher(sb);
        if (matcher.find()) {
          return title.replace(title.substring(title.lastIndexOf(matcher.group())+1), repTitle);
        }
        return null;
    }
}
