package com.zz91.mission.huzhu.caiji;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;

public class UpdatebbsCaijiTimeTask implements ZZTask{
	 private final static String LOG_DATE_FORMAT = "yyyy-MM-dd";
	 private final static String DB="ast";
	@Override
	public boolean init() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
	
		String targetDate=DateUtil.toString(new Date(), LOG_DATE_FORMAT);
		int max=queryMaxSize(targetDate);
		int limit=1000;
		for(int i=0;i<(max/limit+1);i++){
			queryCompanyId(i,limit,targetDate);
		}
		return true;
	}
	private void queryCompanyId(Integer i,Integer limit,String targetDate)throws Exception{
		
		 //获取最终需要的数据的list
		 final List<Integer>list=new ArrayList<Integer>();
		
		DBUtils.select("ast", "select id from bbs_post_caiji order by id desc limit "+(i*limit)+","+limit, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while(rs.next()){
					list.add(rs.getInt(1));
				}
			}
		});
		
		
		//更改 发布状态 isPause=2
		for (Integer id : list) {
			updateTimeById(id);
		}
	}
	/**
	 * 更改bbs_psot_caiji 表的审核时间
	 * @param productId
	 */
	private void updateTimeById(Integer id){
		
		 String sql = "update bbs_post_caiji set check_time=now() ,post_time=now(),reply_time=now() where id = "+id;
		 DBUtils.insertUpdate(DB, sql);
	}
	/**
	 * 获取总数
	 * @param targetDate
	 * @return
	 * @throws Exception
	 */
	private int queryMaxSize(String targetDate) throws Exception{
		int max=0;
		Connection conn=null;
		ResultSet rs=null;
		try {
			conn=DBUtils.getConnection(DB);
			rs=conn.createStatement().executeQuery("select count(0) from bbs_post_caiji ");
			while(rs.next()){
				max=rs.getInt(1);
			}
		} catch (Exception e) {
			throw new Exception("更新失败 Exception:"+e.getMessage());
		}finally{
			try {
				if(conn!=null){
					conn.close();
				}
				if(rs!=null){
					rs.close();
				}
			} catch (Exception e2) {
				throw new Exception("数据库连接没有关闭 Exception:"+e2.getMessage());
			}
		}
		return max;
		
	}
	@Override
	public boolean clear(Date baseDate) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}
	 public static void main(String [] args) throws ParseException, Exception{
	    	DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
	    	long start=System.currentTimeMillis();
	    	UpdatebbsCaijiTimeTask updatebbsCaijiTimeTask= new UpdatebbsCaijiTimeTask();
	    	updatebbsCaijiTimeTask.exec(DateUtil.getDate("2014-11-07 12:12:12", "yyyy-MM-dd"));
	    	long end=System.currentTimeMillis();
	    	System.out.println("共耗时："+(end-start));
	    	
	    }
}
