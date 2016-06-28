package com.zz91.mission.phone;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;
/**
 * 更新phone表过期标志 expire_flag 0:未过期 1:已过期
 * @author root
 *
 */
public class UpdatephoneExpireFlagTask implements ZZTask {
	final List<Integer> list=new ArrayList<Integer>();
	@Override
	public boolean init() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
	    DBUtils.select("ast", "select id from phone where front_tel='信息已过期'", new IReadDataHandler() {
			
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					list.add(rs.getInt(1));
				}
				
			}
		});
	    for(Integer i:list){
	    	updatePhone(i);
	    }
	    return true;
	}
    private void updatePhone (Integer id){
    	DBUtils.insertUpdate("ast", "update phone set expire_flag=1 where id="+id);
    }
	@Override
	public boolean clear(Date baseDate) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}
    public static void main(String [] args) throws ParseException, Exception{
    	DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
    	long start=System.currentTimeMillis();
    	UpdatephoneExpireFlagTask phoneExpireFlagTask= new UpdatephoneExpireFlagTask();
    	phoneExpireFlagTask.exec(DateUtil.getDate("2014-11-07 12:12:12", "yyyy-mm-dd"));
    	long end=System.currentTimeMillis();
    	System.out.println("共耗时："+(end-start));
    	
    }
}
