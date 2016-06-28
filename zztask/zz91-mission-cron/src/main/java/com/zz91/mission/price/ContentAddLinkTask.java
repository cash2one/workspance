package com.zz91.mission.price;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.zz91.task.common.ZZTask;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;
import com.zz91.util.lang.StringUtils;

/**
 * 报价文本添加 链接替换
 * @author kongsj
 *
 */
public class ContentAddLinkTask implements ZZTask{

	final static String DB = "ast";
	final static Map<String, String> METAL_MAP = new LinkedHashMap<String,String>();
	final static Map<String, String> PLASTIC_MAP = new LinkedHashMap<String,String>();
	final static Map<String, String> WASTE_MAP = new LinkedHashMap<String,String>();
	
	static {
		METAL_MAP.put("铜","http://price.zz91.com/moreList_p3_t40_metal.htm");
		METAL_MAP.put("锡","http://price.zz91.com/priceList_t308_metal.htm");
		METAL_MAP.put("铝","http://price.zz91.com/moreList_p3_t41_metal.htm");
		METAL_MAP.put("钢采购","http://price.zz91.com/priceList_t279_metal.htm");
		METAL_MAP.put("铁","http://price.zz91.com/moreList_p3_t42_metal.htm");
		METAL_MAP.put("钢","http://price.zz91.com/moreList_p3_t45_metal.htm");
		METAL_MAP.put("铅","http://price.zz91.com/moreList_p3_t43_metal.htm");
		METAL_MAP.put("锌","http://price.zz91.com/moreList_p3_t43_metal.htm");
		METAL_MAP.put("不锈钢","http://price.zz91.com/moreList_p3_t44_metal.htm");
		METAL_MAP.put("国外废","http://price.zz91.com/moreList_p3_t44_metal.htm");
		METAL_MAP.put("镍","http://price.zz91.com/priceList_t47_metal.htm");
		METAL_MAP.put("钼","http://price.zz91.com/priceList_t49_metal.htm");
		METAL_MAP.put("钛","http://price.zz91.com/priceList_t49_metal.htm");
		METAL_MAP.put("钼钛","http://price.zz91.com/priceList_t49_metal.htm");
		METAL_MAP.put("网上","http://price.zz91.com/priceList_t51_metal.htm");
		METAL_MAP.put("电瓶","http://price.zz91.com/priceList_t52_metal.htm");
		METAL_MAP.put("江浙沪","http://price.zz91.com/priceList_a53_metal.htm");
		METAL_MAP.put("江苏","http://price.zz91.com/priceList_a53_metal.htm");
		METAL_MAP.put("浙江","http://price.zz91.com/priceList_a53_metal.htm");
		METAL_MAP.put("上海","http://price.zz91.com/priceList_a53_metal.htm");
		METAL_MAP.put("江西","http://price.zz91.com/priceList_a260_metal.htm");
		METAL_MAP.put("湖北","http://price.zz91.com/priceList_a262_metal.htm");
		METAL_MAP.put("河北","http://price.zz91.com/priceList_a271_metal.htm");
		METAL_MAP.put("东北","http://price.zz91.com/priceList_a266_metal.htm");
		METAL_MAP.put("山西","http://price.zz91.com/priceList_a267_metal.htm");
		METAL_MAP.put("南海","http://price.zz91.com/priceList_a54_metal.htm");
		METAL_MAP.put("天津","http://price.zz91.com/priceList_a239_metal.htm");
		METAL_MAP.put("长葛","http://price.zz91.com/priceList_a58_metal.htm");
		METAL_MAP.put("广东","http://price.zz91.com/priceList_a180_metal.htm");
		METAL_MAP.put("重庆","http://price.zz91.com/priceList_a251_metal.htm");
		METAL_MAP.put("陕西","http://price.zz91.com/priceList_a253_metal.htm");
		METAL_MAP.put("山东","http://price.zz91.com/priceList_a314_metal.htm");
		METAL_MAP.put("汨罗","http://price.zz91.com/priceList_a56_metal.htm");
		METAL_MAP.put("临沂","http://price.zz91.com/priceList_a55_metal.htm");
		METAL_MAP.put("清远","http://price.zz91.com/priceList_a59_metal.htm");
		METAL_MAP.put("早参","http://price.zz91.com/priceList_t32_metal.htm?mainCode=1000&code=metal&priceTypeId=216&typeId=216&nowTime=Fri+Feb+14+10%3A45%3A15+CST+2014");
		METAL_MAP.put("晚报","http://price.zz91.com/priceList_t32_metal.htm?mainCode=1000&code=metal&priceTypeId=216&typeId=216&nowTime=Fri+Feb+14+10%3A45%3A15+CST+2014");
		METAL_MAP.put("价格","http://price.zz91.com/");
		METAL_MAP.put("行情","http://price.zz91.com/priceList_t216_metal.htm");
		METAL_MAP.put("贵金属","http://price.zz91.com/moreList_p68_metal.htm");
		METAL_MAP.put("有色","http://price.zz91.com/moreList_p67_metal.htm");
		METAL_MAP.put("现货","http://price.zz91.com/moreList_p67_metal.htm");
		METAL_MAP.put("生铁","http://price.zz91.com/priceList_t66_metal.htm?mainCode=1000&code=metal&priceTypeId=216&typeId=216&nowTime=Fri+Feb+14+10%3A46%3A20+CST+2014");
		METAL_MAP.put("周报","http://www.zz91.com/xiazai/");
		
		PLASTIC_MAP.put("行情","http://price.zz91.com/priceList_t217_metal.htm");
		PLASTIC_MAP.put("评论","http://price.zz91.com/priceList_t34_plastic.htm");
		PLASTIC_MAP.put("周报","http://www.zz91.com/xiazai/");
		PLASTIC_MAP.put("油价","http://price.zz91.com/priceList_t190_paper.htm");
		PLASTIC_MAP.put("油","http://price.zz91.com/priceList_t190_paper.htm");
		PLASTIC_MAP.put("全国","http://price.zz91.com/priceList_t20_plastic.htm");
		PLASTIC_MAP.put("各地","http://price.zz91.com/moreList_p22_plastic.htm");
		PLASTIC_MAP.put("再生料","http://price.zz91.com/priceList_t98_plastic.htm");
		PLASTIC_MAP.put("期货","http://price.zz91.com/priceList_t233_plastic.htm");
		PLASTIC_MAP.put("美国","http://price.zz91.com/priceList_t62_plastic.htm");
		PLASTIC_MAP.put("欧洲","http://price.zz91.com/priceList_t63_plastic.htm");
		PLASTIC_MAP.put("新料市场","http://price.zz91.com/moreList_p60_plastic.htm");
		PLASTIC_MAP.put("新料出厂","http://price.zz91.com/priceList_t61_plastic.htm");
		PLASTIC_MAP.put("余姚","http://price.zz91.com/priceList_t110_plastic.htm");
		PLASTIC_MAP.put("上海","http://price.zz91.com/priceList_t115_plastic.htm");
		PLASTIC_MAP.put("山东","http://price.zz91.com/priceList_t132_plastic.htm");
		PLASTIC_MAP.put("河南","http://price.zz91.com/priceList_t142_plastic.htm");
		PLASTIC_MAP.put("齐鲁","http://price.zz91.com/priceList_t126_plastic.htm");
		PLASTIC_MAP.put("东莞","http://price.zz91.com/priceList_t111_plastic.htm");
		PLASTIC_MAP.put("顺德","http://price.zz91.com/priceList_t120_plastic.htm");
		PLASTIC_MAP.put("临沂","http://price.zz91.com/priceList_t121_plastic.htm");
		PLASTIC_MAP.put("杭州","http://price.zz91.com/priceList_t119_plastic.htm");
		PLASTIC_MAP.put("汕头","http://price.zz91.com/priceList_t118_plastic.htm");
		PLASTIC_MAP.put("上海","http://price.zz91.com/priceList_t115_plastic.htm");
		PLASTIC_MAP.put("广州","http://price.zz91.com/priceList_t113_plastic.htm");
		PLASTIC_MAP.put("北京","http://price.zz91.com/priceList_t112_plastic.htm");
		PLASTIC_MAP.put("浙江","http://price.zz91.com/priceList_t127_plastic.htm");
		PLASTIC_MAP.put("南京","http://price.zz91.com/priceList_t320_plastic.htm");
		PLASTIC_MAP.put("汕头","http://price.zz91.com/priceList_t317_plastic.htm");
		PLASTIC_MAP.put("成都","http://price.zz91.com/priceList_t316_plastic.htm");
		PLASTIC_MAP.put("河北","http://price.zz91.com/priceList_t138_plastic.htm");
		PLASTIC_MAP.put("网上","http://price.zz91.com/priceList_t137_metal.htm");
		PLASTIC_MAP.put("山东","http://price.zz91.com/priceList_t132_plastic.htm");
		PLASTIC_MAP.put("广东","http://price.zz91.com/priceList_t130_plastic.htm");
		PLASTIC_MAP.put("PE","http://price.zz91.com/priceList_a300_plastic.htm");
		PLASTIC_MAP.put("PA","http://price.zz91.com/priceList_a298_plastic.htm");
		PLASTIC_MAP.put("PP","http://price.zz91.com/priceList_a291_plastic.htm");
		PLASTIC_MAP.put("PC","http://price.zz91.com/priceList_a293_plastic.htm");
		PLASTIC_MAP.put("PS","http://price.zz91.com/priceList_a294_plastic.htm");
		
		PLASTIC_MAP.put("EPS","http://price.zz91.com/priceList_a301_plastic.htm");
		PLASTIC_MAP.put("EVA","http://price.zz91.com/priceList_a302_plastic.htm");
		PLASTIC_MAP.put("TPU","http://price.zz91.com/priceList_a307_plastic.htm");
		PLASTIC_MAP.put("PTA","http://price.zz91.com/priceList_a309_plastic.htm");
		PLASTIC_MAP.put("ABS","http://price.zz91.com/priceList_a313_plastic.htm");
		PLASTIC_MAP.put("POM","http://price.zz91.com/priceList_a289_plastic.htm");
		PLASTIC_MAP.put("PET","http://price.zz91.com/priceList_a290_plastic.htm");
		PLASTIC_MAP.put("ABS","http://price.zz91.com/priceList_a296_plastic.htm");
		PLASTIC_MAP.put("PVC","http://price.zz91.com/priceList_a297_plastic.htm");
		PLASTIC_MAP.put("CPP","http://price.zz91.com/priceList_a323_plastic.htm");
		
		PLASTIC_MAP.put("PMMA","http://price.zz91.com/priceList_a299_plastic.htm");
		PLASTIC_MAP.put("HIPS","http://price.zz91.com/priceList_a305_plastic.htm");
		PLASTIC_MAP.put("GPPS","http://price.zz91.com/priceList_a306_plastic.htm");
		PLASTIC_MAP.put("PP-R","http://price.zz91.com/priceList_a303_plastic.htm");
		PLASTIC_MAP.put("LDPE","http://price.zz91.com/priceList_a292_plastic.htm");
		PLASTIC_MAP.put("HDPE","http://price.zz91.com/priceList_a295_plastic.htm");
		PLASTIC_MAP.put("LLDPE","http://price.zz91.com/priceList_a304_plastic.htm");

		WASTE_MAP.put("橡胶","http://price.zz91.com/priceList_t30_paper.htm");
		WASTE_MAP.put("纸","http://price.zz91.com/moreList_p13_paper.htm");
		WASTE_MAP.put("行情","http://price.zz91.com/priceList_t220_paper.htm");
		WASTE_MAP.put("综述","http://price.zz91.com/priceList_t220_paper.htm");
		WASTE_MAP.put("行情综述","http://price.zz91.com/priceList_t220_paper.htm");
	}
	
	@Override
	public boolean clear(Date baseDate) throws Exception {
		return false;
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		String sql = "select id,type_id,content from price where is_remark = '0' order by id desc limit 500";
		final Map<String, Map<String, String>> idMap = new HashMap<String, Map<String, String>>();
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while(rs.next()){
					Map<String, String> map =new HashMap<String, String>();
					map.put("typeId", rs.getString(2));
					map.put("content",rs.getString(3));
					idMap.put(rs.getString(1), map);
				}
			}
		});
		
		final Map<String, String> labelMap = new HashMap<String, String>();
		
		for (String id:idMap.keySet()) {
			
			Map<String, String> map = idMap.get(id);
			final String typeId = map.get("typeId");
			String content = map.get("content");
			String key =labelMap.get(typeId);
			if(key==null||StringUtils.isEmpty(key)){
				sql = "select search_label from price_category where id = "+typeId;
				DBUtils.select(DB, sql, new IReadDataHandler() {
					@Override
					public void handleRead(ResultSet rs) throws SQLException {
						while (rs.next()) {
							labelMap.put(typeId, rs.getString(1));
						}
					}
				});
				key = labelMap.get(typeId);
			}
			
			if(key==null||StringUtils.isEmpty(content)){
				continue;
			}
			
			String contentQuery = "";
			String[] contentArray=null;
			if(content.indexOf("相关文章：")!=-1){
				contentQuery = content.substring(0,content.indexOf("相关文章："));
				contentArray = content.split("相关文章：");
				content = contentQuery;
			}
			
			if(key.length()>=3&&key.startsWith("废金属")){
				content = coverContent(content, METAL_MAP);
			}else if(key.length()>=3&&key.startsWith("废塑料")){
				content = coverContent(content, PLASTIC_MAP);
			}else{
				content = coverContent(content, WASTE_MAP);
			}
			
			content = Jsoup.clean(content, Whitelist.none().addTags("div","p","ul","li","br","table","th","tr","td","u").addAttributes("a", "href","target","style").addAttributes("td","rowspan","colspan").addAttributes("img", "src","style").addAttributes("span", "style"));
			
			if(StringUtils.isNotEmpty(contentQuery)&&contentArray!=null&&contentArray.length>=2){
				content =content + "相关文章：" + contentArray[1];
			}
			
			content = content.replace("'", "\"");
			
			if(StringUtils.isNotEmpty(content)){
				sql = "update price set content='"+content+"' ,is_remark='1'  where id="+id;
				DBUtils.insertUpdate(DB, sql);
			}
		}
		
		return true;
	}
	
	private String coverContent(String content,Map<String, String> map){
		int count = 0;
		for (String key:map.keySet()) {
			int mark = content.indexOf(key);
			if(content.indexOf(key)!=-1){
				content = content.substring(0,mark)+"<a target=\"_blank\" href=\""+map.get(key)+"\" style=\"color:blue\"><u>"+key+"</u></a>"+content.substring(mark+key.length(),content.length());
				count++;
			}
			if(count>=5){
				break;
			}
		}
		return content;
	}

	@Override
	public boolean init() throws Exception {
		return false;
	}
	
	public static void main(String[] args) throws Exception {
		DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
		ContentAddLinkTask obj = new ContentAddLinkTask();
		obj.exec(new Date());
	}
}
