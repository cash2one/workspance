package com.zz91.mission.analysis;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;
import com.zz91.util.log.LogUtil;

import net.sf.json.JSONObject;

/**
 * 获取mongodb里的点击数据，与phone_log表中的电话话单进行比较
 * 点击过后两分钟内拨打的且地区相同的。属于有效拨打设置有效字段为1
 * @author kongsj
 *
 */
public class AnalysisPhoneYHClick implements ZZTask{
	
	final static String DB = "ast";
	final static String DB_OTHER="zzother";

	@Override
	public boolean init() throws Exception {
		return false;
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		Date gmtTarget = DateUtil.getDateAfterDays(baseDate, -1);
		// 检索今日优化进来的ip
		String sql = "select id,ip from analysis_phone_optimization where gmt_target='"+ DateUtil.toString(gmtTarget, "yyyy-MM-dd")+"'";
		final Map<Integer, String> idMap = new HashMap<Integer, String>();
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					idMap.put(rs.getInt(1), rs.getString(2));
				}
			}
		});
		
		if (idMap.size()<=0) {
			return true;
		}
		
		for (Integer id :idMap.keySet()) {
			Map<String, Object> param=new HashMap<String, Object>();
			String from = ""+DateUtil.getDate(DateUtil.getDateAfterDays(gmtTarget, 0), "yyyy-MM-dd").getTime();
			String to = ""+DateUtil.getDate(DateUtil.getDateAfterDays(gmtTarget, 1), "yyyy-MM-dd").getTime();
			param.put("time",LogUtil.getInstance().mgCompare(">=",from,"<",to));	//逻辑运算符查询
			param.put("operator", "phone_400");
			param.put("ip", idMap.get(id));
			JSONObject res = LogUtil.getInstance().readMongo(param, 0, 1000);
			@SuppressWarnings("unchecked")
			List<JSONObject> list =res.getJSONArray("records");
			if (list.size()>0) {
//				System.out.println(list.size()+":"+idMap.get(id));
			}
			//{"operation":"clickToShowNumber","time":"1449730297673","data":{"date":"2015-12-10 14:51:37","time":1449730297673,"tel":"400-873-9969"},"appCode":"zz91","operator":"phone_400","ip":"66.249.69.118"}
			for ( JSONObject obj : list ) {
				try {
					JSONObject dataObj = JSONObject.fromObject(obj.get("data"));
					long dFrom = (Long) dataObj.get("time")/1000;
					long dTo = (dFrom + (120000*30*24))/1000;
					String tel = dataObj.getString("tel").replace("-", "");
					String ip = obj.getString("ip");
					//System.out.println(DateUtil.toString(new Date(dFrom), "yyyy-MM-dd HH:mm:ss")+":"+DateUtil.toString(new Date(dTo), "yyyy-MM-dd HH:mm:ss"));
					sql = "select id from phone_log where unix_timestamp(start_time) >="+dFrom+" and unix_timestamp(start_time) <= " + dTo +" and tel = '"+tel+"' and city='"+getAreaByIp(ip) +"'";
					final Integer[] phoneLogId = new Integer[]{0};
					DBUtils.select(DB, sql, new IReadDataHandler() {
						@Override
						public void handleRead(ResultSet rs) throws SQLException {
							while (rs.next()) {
								phoneLogId[0] = rs.getInt(1);
							}
						}
					});
					if (phoneLogId[0]>0) {
						sql = "update analysis_phone_optimization set gmt_modified=now,is_valid=1,phone_log_id = " +phoneLogId[0] + "where id = "+ id;
						DBUtils.insertUpdate(DB, sql);
						break;
					}
				} catch (Exception e) {
					continue;
				}
			}
		}

		
		return true;
	}
	
	private String getAreaByIp(String ip){
		final String area[] = new String[]{""};
		String sql = "SELECT area FROM  `ip_area` WHERE inet_aton(ip) <=  inet_aton('"+ip+"') AND inet_aton(ip2) >=  inet_aton('"+ip+"') order by id desc limit 1 ";
		DBUtils.select(DB_OTHER, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					String str = rs.getString(1);
					if (str.indexOf("省")!=-1&&str.indexOf("市")!=-1) {
						str = str.substring(str.indexOf("省")+1, str.indexOf("市"));
						area[0] = str;
					}else if(str.indexOf("省")==-1&&str.indexOf("市")!=-1){
						str = str.substring(0, str.indexOf("市"));
						area[0] = str;
					}else{
						area[0] = str;
					}
				}
			}
		});
		return area[0];
	}

	@Override
	public boolean clear(Date baseDate) throws Exception {
		return false;
	}
	
	public static void main(String[] args) throws Exception {
		DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
		AnalysisPhoneYHClick obj = new AnalysisPhoneYHClick();
		LogUtil.getInstance().init("web.properties");
		obj.exec(DateUtil.getDate("2015-12-10", "yyyy-MM-dd"));
		
//		String str = "北京市朝阳区";
//		str = str.substring(0, str.indexOf("市"));
//		System.out.println(str);
	}

}
