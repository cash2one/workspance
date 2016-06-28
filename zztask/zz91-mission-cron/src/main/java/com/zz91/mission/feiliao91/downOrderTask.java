package com.zz91.mission.feiliao91;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import net.sf.json.JSONObject;

import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;

public class downOrderTask implements ZZTask {
	private final static String DB = "feiliao91";

	@Override
	public boolean init() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	// 超过3天　系统自动关闭订单
	@Override
	public boolean exec(Date baseDate) throws Exception {
		String date = DateUtil.toString(
				DateUtil.getDateAfterDays(baseDate, -5), "yyyy-MM-dd");
		String sql = "select id,details,gmt_created from orders where status =0 and gmt_created>"
				+ date;
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					Integer id = rs.getInt(1);
					String detail = rs.getString(2);
					Date time = rs.getDate(3);
					long oldTime = time.getTime();
					long newTime = System.currentTimeMillis();
					if (newTime > oldTime) {
						if ((newTime - oldTime) >= (3 * 24 * 60 * 60 * 1000)) {
							updateStatus(id, detail);
						}
					}
				}
			}

			private void updateStatus(Integer id, String detail) {
				JSONObject obj = JSONObject.fromObject(detail);
				obj.put("cancelReason", "付款超时,系统自动关闭订单");
				String tr=(String) JSONObject.fromObject(obj.get("sellCompany")).get("introduce");
				String br="";
				
				String tr2=(String) JSONObject.fromObject(obj.get("buyCompany")).get("introduce");
				String br2="";
				if(tr.contains("\r\n")){
					br=tr.replaceAll("\r\n", "").replaceAll("\t", "");
				}else{
					br=tr;
				}
				
				if(tr2.contains("\r\n")){
					br2=tr2.replaceAll("\r\n", "").replaceAll("\t", "");
				}else{
					br2=tr2;
				}
				JSONObject obj2 = JSONObject.fromObject(obj.get("sellCompany"));
				obj2.put("introduce", br);
				obj.put("sellCompany", obj2.toString());
				
				JSONObject obj3 = JSONObject.fromObject(obj.get("buyCompany"));
				obj3.put("introduce", br2);
				obj.put("buyCompany", obj3.toString());
				
				String del=obj.toString();
				String sql2="update orders set gmt_modified=now(),status=99,details='"+del+"'where id="+id;
				DBUtils.insertUpdate(DB, sql2);
			}
		});
		return true;
	}

	@Override
	public boolean clear(Date baseDate) throws Exception {

		return false;
	}

	
	public static void main(String[] args) throws Exception {

		DBPoolFactory.getInstance().init(
				"file:/usr/tools/config/db/db-zztask-jdbc.properties");
		long start = System.currentTimeMillis();

		downOrderTask task = new downOrderTask();
		task.exec(new Date());

		long end = System.currentTimeMillis();
		System.out.println("共耗时：" + (end - start));
	}
}
