package com.zz91.mission.ep;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;

public class EpUpdateTradeSupplyUntilsTesk implements ZZTask{

public static void main(String[] args) {
	 //将环保机器发布的错误信息改为审核不通过(2014-10-1到10-8)
		DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
		EpUpdateTradeSupplyUntilsTesk task=new EpUpdateTradeSupplyUntilsTesk();
		long start=DateUtil.getSecTimeMillis();
		try {
			task.exec(DateUtil.getDate("2014-10-09", "yyyy-MM-dd"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		long end=DateUtil.getSecTimeMillis();
		System.out.println("总耗时："+(end-start)+"秒");
	}

	@Override
	public boolean clear(Date baseDate) throws Exception {
		return false;
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		
		int max=queryMaxSize();
		int limit=1000;
		for(int i=0;i<(max/limit+1);i++){
			batchSync(i, limit);
		}
		return true;
	}

	private int queryMaxSize() throws Exception{
		int max=0;
		Connection conn=null;
		ResultSet rs=null;
		try {
			conn=DBUtils.getConnection("ep");
			rs=conn.createStatement().executeQuery
					("select count(0) from trade_supply where total_units in ('纸箱' ,'普通','木箱', '电议', '泡沫', '箱 ','瓶', '1','盒装', '桶', '按合同', '木架','支','卷','无','有','配套','盒式','坡口' ,'袋装','精装') and check_status=0");
			while(rs.next()){
				max=rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new Exception("没有获取到trade_supply总数 Exception:"+e.getMessage());
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
	
	private void batchSync(Integer i, Integer limit) throws Exception{
		final List<Integer> tradeSupplyList=new ArrayList<Integer>();
		DBUtils.select("ep", "select id from trade_supply  where total_units in ('纸箱' ,'普通','木箱', '电议', '泡沫', '箱 ','瓶','1', '盒装', '桶', '按合同', '木架','支','卷','无','有','配套','盒式','坡口' ,'袋装','精装') and  check_status=0 limit "+(i*limit)+","+limit, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while(rs.next()){
					tradeSupplyList.add(rs.getInt(1));
				}
			}
		});
		
		for(Integer id:tradeSupplyList){
			  // trade_supply表 更新
            DBUtils.insertUpdate("ep", "update trade_supply set check_status=2,gmt_check=now(),gmt_modified=now(),check_refuse='产品数量单位不对，请填写正确的数量单位',check_admin='yangxj' where id="+id);
		}
		
	}

	@Override
	public boolean init() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
