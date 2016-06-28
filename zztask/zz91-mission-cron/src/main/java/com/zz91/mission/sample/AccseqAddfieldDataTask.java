package com.zz91.mission.sample;

import java.util.Date;

import com.zz91.task.common.ZZTask;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.pool.DBPoolFactory;

/**
 * 账户 流水表 添加字段 company_id 数据补全
 * 
 */
public class AccseqAddfieldDataTask implements ZZTask {

	final static String DB = "ast";

	@Override
	public boolean clear(Date baseDate) throws Exception {
		return false;
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		String sql = "update sample_accountseq s set s.company_id = ( SELECT  a.company_id  from  sample_account a WHERE a.id = s.account_id  )";
		try {
			DBUtils.insertUpdate(DB, sql);
		} catch (Exception e) {
		}

		return true;
	}

	@Override
	public boolean init() throws Exception {
		return false;
	}

	public static void main(String[] args) throws Exception {
		DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
		AccseqAddfieldDataTask obj = new AccseqAddfieldDataTask();
		obj.exec(new Date());
	}

}
