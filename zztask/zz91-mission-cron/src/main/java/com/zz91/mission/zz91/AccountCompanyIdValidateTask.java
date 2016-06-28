package com.zz91.mission.zz91;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.zz91.task.common.ZZTask;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;

public class AccountCompanyIdValidateTask implements ZZTask{

	final static String DB = "ast" ;
	
	@Override
	public boolean init() throws Exception {
		return false;
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		String sql = "select id,website from company where id>123459306 and id< 123459713 ";
		final Map<Integer,Integer> idMap = new HashMap<Integer,Integer>();
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					idMap.put(rs.getInt(1),rs.getInt(2));
				}
			}
		});
		
		final Map<Integer, Integer> resultMap = new HashMap<Integer, Integer>();
		for (final Integer id :idMap.keySet()) {
			sql = "SELECT id FROM company_account where company_id = "+idMap.get(id)+" order by id desc limit 1";
			DBUtils.select(DB, sql, new IReadDataHandler() {
				@Override
				public void handleRead(ResultSet rs) throws SQLException {
					while (rs.next()) {
						resultMap.put(rs.getInt(1), id);
					}
				}
			});
		}

		for (Integer id : resultMap.keySet()) {
			sql = "update company_account set gmt_modified = now(), tel_country_code = id, company_id = " + resultMap.get(id) + " where id = " + id ;
			DBUtils.insertUpdate(DB, sql);
		}

		return true;
	}

	@Override
	public boolean clear(Date baseDate) throws Exception {
		return false;
	}
	
	public static void main(String[] args) throws Exception {
		DBPoolFactory.getInstance().init("file:/mnt/tools/config/db/db-zztask-jdbc.properties");
		AccountCompanyIdValidateTask obj = new AccountCompanyIdValidateTask();
		obj.exec(new Date());
		System.out.println("ye");
	}

}
