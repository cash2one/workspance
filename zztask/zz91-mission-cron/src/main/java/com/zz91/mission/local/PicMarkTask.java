package com.zz91.mission.local;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.zz91.task.common.ZZTask;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;
import com.zz91.util.file.PicMarkUtils;

public class PicMarkTask implements ZZTask {

	final static String DB = "194";
	final static String URL= "/home/sj/jicui/";
	final static String TABLE = "TPU";
	
	@Override
	public boolean init() throws Exception {
		return false;
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		String sql = "select picname from "+TABLE;
		final List<String> list = new ArrayList<String>();
		DBUtils.select(DB, sql, new IReadDataHandler() {
			
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					list.add(rs.getString(1));
				}
			}
		});
		
		List<String> resultList = new ArrayList<String>();
		for (String str : list) {
//			if (str.indexOf("|")==-1) {
//				continue;
//			}
			String [] pic = str.split("\\|");
			List<String> tempList = Arrays.asList(pic);
			resultList.addAll(tempList);
		}
		
		for (String str : resultList) {
			if (!str.endsWith(".jpg")) {
				str = str + ".jpg";
			}
			PicMarkUtils.pressTextRightButtom(URL+TABLE+"/"+str, "http://www.zz91.com", 60, new Color(255, 150, 0));
		}
		
		return true;
	}

	@Override
	public boolean clear(Date baseDate) throws Exception {
		return false;
	}
	
	public static void main(String[] args) throws Exception {
		DBPoolFactory.getInstance().init("file:/mnt/tools/config/db/db-zztask-jdbc.properties");
		PicMarkTask obj = new PicMarkTask();
		obj.exec(new Date());
		System.out.println("ye");
//		PicMarkUtils.pressTextRightButtom("/home/sj/100391316826_0.jpg", "www.zz91.com", 60,  new Color(255, 200, 0));
	}

}
