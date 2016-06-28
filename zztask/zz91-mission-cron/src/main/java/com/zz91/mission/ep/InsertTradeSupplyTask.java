package com.zz91.mission.ep;

import java.util.Date;

import com.zz91.task.common.ZZTask;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.pool.DBPoolFactory;

public class InsertTradeSupplyTask implements ZZTask {
	private String DB = "ep";

	@Override
	public boolean init() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		Integer count = 10;
		Integer li = 0;
		for (int i = 0; i < 10; i++) {
			li = i * count;
			String sql = "INSERT INTO trade_supply SELECT * FROM trade_supply1 p order by id desc limit "
					+ li + ",10";
			try {
				DBUtils.insertUpdate(DB, sql);
			} catch (Exception e) {
				continue;
			}
		}

		return true;
	}

	@Override
	public boolean clear(Date baseDate) throws Exception {
		return true;
	}

	public static void main(String[] args) throws Exception {
		DBPoolFactory.getInstance().init(
				"file:/usr/tools/config/db/db-zztask-jdbc.properties");
		InsertTradeSupplyTask sq = new InsertTradeSupplyTask();
		Date date = new Date();
		sq.exec(date);
	}

}
