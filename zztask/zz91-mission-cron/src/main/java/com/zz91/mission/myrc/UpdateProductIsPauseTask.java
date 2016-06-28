package com.zz91.mission.myrc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;
/**
 * 将普会的长期已过期的供求信息隐藏 is_pause=2  
 * @author root
 *长期标准:刷新时间为2014-01-01 之前
 */
public class UpdateProductIsPauseTask implements ZZTask{
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
		 final List<Integer>list1=new ArrayList<Integer>();
		 final Map<Integer, Map<String, String>> maps=new HashMap<Integer, Map<String,String>>();
		 final Map<String, String>root=new HashMap<String, String>();
		DBUtils.select("ast", "select id ,company_id,is_pause from products  where check_status='1' and is_del='0' and refresh_time<'2014-01-01' and expire_time<'"+targetDate+"' order by id desc limit "+(i*limit)+","+limit, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while(rs.next()){
					Map<String, String>map=new HashMap<String, String>();
					map.put("id", rs.getString(1));
					map.put("companyId", rs.getString(2));
					map.put("isPause", rs.getString(3));
					maps.put(rs.getInt(1), map);
				}
			}
		});
		
		//判断发布该供求的客户是否为普会(排除百度优化，高会,来电宝)
		for (final Integer id: maps.keySet()) {
			if(maps.get(id).get("isPause").equals("0")){
				if(root.get(maps.get(id).get("companyId"))==null){
					DBUtils.select(DB, "select id from crm_company_service where  apply_status=1 and gmt_end>'"+targetDate+"' and company_id= "+maps.get(id).get("companyId"), new IReadDataHandler() {
						@Override
						public void handleRead(ResultSet rs) throws SQLException {
							if (!rs.next()) {
								//将root中companyId 值置为0 表示是普会
								root.put(maps.get(id).get("companyId"),"0");
								list1.add(id);
							} 
							while(rs.next()){
								List<Integer>list=new ArrayList<Integer>();
								list.add(rs.getInt(1));
								if(list!=null&&list.size()<=0){
									root.put(maps.get(id).get("companyId"),"0");
									list1.add(id);
								}else {
									root.put(maps.get(id).get("companyId"),"1");
								}
							}
						}
					});
				}else if(root.get(maps.get(id).get("companyId")).equals("0")){
					list1.add(id);
				}
			}
		}
		//更改 发布状态 isPause=2
		for (Integer productId : list1) {
			updateIsPauseById(productId);
		}
	}
	/**
	 * 更改product 发布状态 isPause=2 表示已隐藏
	 * @param productId
	 */
	private void updateIsPauseById(Integer productId){
		
		 String sql = "update products set is_pause=2 ,check_person='luog',gmt_modified = now() where id = " + productId;
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
			rs=conn.createStatement().executeQuery("select count(0) from products where check_status='1' and is_del='0'  and refresh_time<'2014-01-01' and expire_time<'"+targetDate+"'");
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
	    	UpdateProductIsPauseTask updateProductIsPauseTask= new UpdateProductIsPauseTask();
	    	updateProductIsPauseTask.exec(DateUtil.getDate("2014-11-07 12:12:12", "yyyy-MM-dd"));
	    	long end=System.currentTimeMillis();
	    	System.out.println("共耗时："+(end-start));
	    	
	    }

}
