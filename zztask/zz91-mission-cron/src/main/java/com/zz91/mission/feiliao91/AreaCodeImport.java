package com.zz91.mission.feiliao91;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * @author shiqp
 * @date 2016-01-09
 */
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;

public class AreaCodeImport implements ZZTask {

	public static void main(String[] args) throws Exception {
		DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
		AreaCodeImport task = new AreaCodeImport();
		task.exec(DateUtil.getDate("2016-01-09", "yyyy-MM-dd"));
		System.out.println("任务结束");

	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		// 获取国家级地区列表
		List<Map<String, String>> list = getCategoryList("1001");
		for (Map<String, String> map : list) {
			//更新到feiliao91类别库中
			insert(map);
		}
		return true;
	}

	// 获取类别 parentCode：1001 地区
	public List<Map<String, String>> getCategoryList(String parentCode) {
		String sql = "select code, parent_code, label from category where left(code,4)='" + parentCode
				+ "' order by gmt_created asc";
		final List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		DBUtils.select("ast", sql, new IReadDataHandler() {

			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("code", rs.getString(1));
					map.put("parent_code", rs.getString(2));
					map.put("label", rs.getString(3));
					list.add(map);
				}
			}
		});
		return list;
	}

	// 导入到feiliao91类别库
	public void insert(Map<String, String> map) {
		String sql = "insert into category (code,parent_code,label,gmt_created,gmt_modified)" + "values('"
				+ map.get("code") + "','" + map.get("parent_code") + "','" + map.get("label") + "',now(),now())";
		DBUtils.insertUpdate("feiliao91", sql);
	}

	@Override
	public boolean init() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean clear(Date baseDate) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
