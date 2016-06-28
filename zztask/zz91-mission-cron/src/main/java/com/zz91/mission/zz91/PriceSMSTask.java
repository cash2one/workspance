package com.zz91.mission.zz91;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zz91.mission.caiji.CaiJiCommonOperate;
import com.zz91.mission.domain.subscribe.Price;
import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;
import com.zz91.util.lang.StringUtils;

public class PriceSMSTask implements ZZTask {

	// JZH(1, "江浙沪","废金属"), GDNH(2, "广东南海","废金属"), TJ(3, "天津","废金属"), SDLY(4,
	// "山东临沂","废金属"), HNML(5, "湖南汨罗","废金属"), HNCG(6, "河南长葛","废金属"), GDQY(7,
	// "广东清远","废金属"), USA(8, "美国",""), SH(9,
	// "上海","期货"), LONDON(10, "伦敦","期货"), SLGD(11, "广东","废塑料"), SLZJ(12,
	// "浙江","废塑料"), SLJS(13, "江苏","废塑料"), SLSD(14, "山东","废塑料");
	//http://apps2.zz91.com/task/job/definition/doTask.htm?jobName=analysis_operation&start=2013-11-02%2000:00:00

	final static String DB_REBORN = "reborn";
	static Map<String, String > CATEGORY_MAP =new HashMap<String, String>();

	@Override
	public boolean clear(Date baseDate) throws Exception {
		return false;
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		CaiJiCommonOperate caiJiCommonOperate = new CaiJiCommonOperate();
		String dateFormat = DateUtil.toString(baseDate, "yyyy-MM-dd");
		String sql = "SELECT id,max_price,min_price,category_code,area_node_id FROM sms_price where gmt_post = '"
				+ dateFormat + "'";
		final Map<String, List<Map<String, String>>> areaMap = new HashMap<String, List<Map<String, String>>>();
		
		DBUtils.select(DB_REBORN, sql, new IReadDataHandler() {

			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					String areaCode = rs.getString(5);
					String categoryCode = rs.getString(4);
					if(StringUtils.isEmpty(areaCode)||StringUtils.isEmpty(categoryCode)){
						continue;
					}
					if(categoryCode.length()<8){
						continue;
					}
					if(!"1000".equals(categoryCode.substring(0, 4))){
						continue;
					}
					if("100010041004".equals(categoryCode)||"100010041005".equals(categoryCode)){
						continue;
					}
					if("10001006".equals(categoryCode.substring(0,8))){
						categoryCode = "10001005";
					}
					if("10001008".equals(categoryCode.substring(0,8))){
						if("100010081001".equals(categoryCode)||"100010081000".equals(categoryCode)){
							categoryCode = "a00010081000";
						}
						if("100010081002".equals(categoryCode)||"100010081003".equals(categoryCode)||"100010081004".equals(categoryCode)){
							categoryCode = "b00010081000";
						}
						if("100010081005".equals(categoryCode)){
							categoryCode = "c00010081000";
						}
					}
					
					List<Map<String, String>> list = areaMap.get(areaCode+"|"+categoryCode.substring(0,8));
					if(list==null){
						list = new ArrayList<Map<String,String>>();
					}
					Map<String, String> rsMap = new HashMap<String, String>();
					rsMap.put("max_price", rs.getString(2));
					rsMap.put("min_price", rs.getString(3));
					rsMap.put("category_code", rs.getString(4));
					rsMap.put("area_node_id", areaCode);
					list.add(rsMap);
					areaMap.put(areaCode+"|"+categoryCode.substring(0,8), list);
				}
			}
		});
		
//		Map<String , String> map = new HashMap<String, String>();
		if(areaMap.size()>0){
			CATEGORY_MAP = queryCategoryMap();
			for (String key:areaMap.keySet()) {
				if("13|10001004".equals(key)||"12|10001004".equals(key)||"1|10001004".equals(key)||"2|10001004".equals(key)||"4|10001005".equals(key)||"1|10001002".equals(key)||"4|10001004".equals(key)||"5|10001003".equals(key)){
					continue;
				}
				String content = getContent(areaMap.get(key));
				
				String titleDate = DateUtil.toString(baseDate, "MM月dd日");
				Price price = new Price();
				String typeId = TYPE_ID_MAP.get(key);
				String typeName = caiJiCommonOperate.queryTypeNameByTypeId(Integer.valueOf(typeId));
				price.setTitle(titleDate+TITLE_MAP.get(key));
				price.setTypeId(Integer.valueOf(typeId));
				price.setTags(typeName);
				price.setContent(content);
				price.setGmtOrder(baseDate);
				//执行插入 
				caiJiCommonOperate.doInsert(price,false);
				
//				System.out.println(key+":"+TITLE_MAP.get(key));
//				System.out.println(content+"<br/>");
			}
		}else{
			return false;
		}
		return true;
	}

	@Override
	public boolean init() throws Exception {
		return false;
	}
	
	final static Map<String, String> TYPE_ID_MAP = new HashMap<String, String>();
	static {
		TYPE_ID_MAP.put("13|10001004", "40");
		TYPE_ID_MAP.put("1|a0001008", "49");
		TYPE_ID_MAP.put("6|10001001", "41");
		TYPE_ID_MAP.put("6|10001000", "40");
		TYPE_ID_MAP.put("12|10001004", "40");
		TYPE_ID_MAP.put("3|10001000", "40");
		TYPE_ID_MAP.put("3|10001001", "41");
		TYPE_ID_MAP.put("3|10001002", "45");
		TYPE_ID_MAP.put("3|10001004", "42");
		TYPE_ID_MAP.put("3|10001005", "43");
		TYPE_ID_MAP.put("4|c0001008", "47");
		TYPE_ID_MAP.put("4|a0001008", "49");
		TYPE_ID_MAP.put("1|b0001008", "308");
		TYPE_ID_MAP.put("1|10001004", "40");
		TYPE_ID_MAP.put("5|10001001", "41");
		TYPE_ID_MAP.put("1|10001003", "44");
		TYPE_ID_MAP.put("2|10001004", "40");
		TYPE_ID_MAP.put("5|10001000", "40");
		TYPE_ID_MAP.put("2|10001005", "43");
		TYPE_ID_MAP.put("1|10001005", "43");
		TYPE_ID_MAP.put("1|10001000", "40");
		TYPE_ID_MAP.put("2|10001002", "45");
		TYPE_ID_MAP.put("2|10001003", "44");
		TYPE_ID_MAP.put("4|10001002", "45");
		TYPE_ID_MAP.put("2|10001000", "40");
		TYPE_ID_MAP.put("4|10001005", "40");
		TYPE_ID_MAP.put("1|10001002", "40");
		TYPE_ID_MAP.put("1|c0001008", "47");
		TYPE_ID_MAP.put("4|10001004", "40");
		TYPE_ID_MAP.put("2|10001001", "41");
		TYPE_ID_MAP.put("1|10001001", "41");
		TYPE_ID_MAP.put("2|a0001008", "49");
		TYPE_ID_MAP.put("4|10001001", "41");
		TYPE_ID_MAP.put("2|c0001008", "47");
		TYPE_ID_MAP.put("4|10001000", "40");
		TYPE_ID_MAP.put("5|10001003", "40");
	}
	
	final static Map<String, String> TITLE_MAP = new HashMap<String, String>();
	static {
		TITLE_MAP.put("1|a0001008", "江浙沪废钼钛价格");
		TITLE_MAP.put("6|10001001", "河南长葛废铝价格");
		TITLE_MAP.put("6|10001000", "河南长葛废铜价格");
		TITLE_MAP.put("3|10001000", "天津废铜价格");
		TITLE_MAP.put("3|10001001", "天津废铝价格");
		TITLE_MAP.put("3|10001002", "天津废钢价格");
		TITLE_MAP.put("3|10001004", "天津地区废铁价格");
		TITLE_MAP.put("3|10001005", "天津废锌和铅价格");
		TITLE_MAP.put("4|c0001008", "山东临沂废镍价格");
		TITLE_MAP.put("4|a0001008", "山东临沂废钼钛价格");
		TITLE_MAP.put("1|b0001008", "江浙沪废锡价格行情");
		TITLE_MAP.put("5|10001001", "湖南汨罗废铝价格");
		TITLE_MAP.put("1|10001003", "江浙沪废不锈钢价格");
		TITLE_MAP.put("5|10001000", "湖南汨罗废铜价格");
		TITLE_MAP.put("2|10001005", "广东南海废锌和铅价格");
		TITLE_MAP.put("1|10001005", "江浙沪废锌和铅价格");
		TITLE_MAP.put("1|10001000", "江浙沪废铜价格");
		TITLE_MAP.put("2|10001002", "广东南海废钢价格");
		TITLE_MAP.put("2|10001003", "广东南海废不锈钢价格");
		TITLE_MAP.put("4|10001002", "山东临沂废钢价格");
		TITLE_MAP.put("2|10001000", "广东南海废铜价格");
		TITLE_MAP.put("1|c0001008", "江浙沪各地废镍价格");
		TITLE_MAP.put("2|10001001", "广东南海废铝价格");
		TITLE_MAP.put("1|10001001", "江浙沪废铝价格");
		TITLE_MAP.put("2|a0001008", "广东南海废钼价格");
		TITLE_MAP.put("4|10001001", "山东临沂废铝价格");
		TITLE_MAP.put("2|c0001008", "广东南海废镍价格");
		TITLE_MAP.put("4|10001000", "山东临沂废铜价格");
	}
	
	final static Map<String, String> AREA_MAP = new HashMap<String, String>();
	static{
		AREA_MAP.put("1", "江浙沪");
		AREA_MAP.put("2", "广东南海");
		AREA_MAP.put("3", "天津");
		AREA_MAP.put("4", "山东临沂");
		AREA_MAP.put("5", "湖南汨罗");
		AREA_MAP.put("6", "河南长葛");
		AREA_MAP.put("7", "广东清远");
		AREA_MAP.put("8", "美国");
		AREA_MAP.put("9", "上海");
		AREA_MAP.put("10", "伦敦");
		AREA_MAP.put("11", "广东");
		AREA_MAP.put("12", "浙江");
		AREA_MAP.put("13", "江苏");
		AREA_MAP.put("14", "山东");
	}
	
	private Map<String, String> queryCategoryMap(){
		final Map<String, String> map = new HashMap<String, String>();
		String sql = "SELECT name,code FROM `sms_category`";
		DBUtils.select(DB_REBORN, sql, new IReadDataHandler() {
			
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					map.put(rs.getString(2), rs.getString(1));
				}
			}
		});
		return map;
	}

	private String getContent(List<Map<String, String>> list){
		String head = "<table border=\"1\" cellpadding=\"0\" cellspacing=\"3\"><tbody><tr><td>品名</td><td>地区</td><td colspan=\"2\">价格（元/吨）</td></tr>";
		String foot = "</tbody></table>";
		String body = "";
		for (Map<String, String> map:list) {
			String maxPrice = map.get("max_price");
			String minPrice = map.get("min_price");
			String categoryCode = map.get("category_code");
			String areaCode = map.get("area_node_id");
			body += "<tr><td>"+CATEGORY_MAP.get(categoryCode) +
					"</td><td>"+AREA_MAP.get(areaCode) + "地区</td><td bgcolor=\"#ffffff\">"+minPrice+
					"</td><td bgcolor=\"#ffffff\">"+maxPrice+"</td></tr>";
		}
		return head+body+foot;
	}
	
	public static void main(String[] args) throws Exception {
		DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
		PriceSMSTask obj = new PriceSMSTask();
		Date date = DateUtil.getDate("2013-12-09", "yyyy-MM-dd");
		obj.exec(date);
	}

}