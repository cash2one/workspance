package com.zz91.mission.company;

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
import com.zz91.util.lang.StringUtils;

public class HideZhuCeJiCompanyTask implements ZZTask {
	
	public static void main(String[] args) throws Exception {
		DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");
		HideZhuCeJiCompanyTask obj = new HideZhuCeJiCompanyTask();
		obj.exec(new Date());
	}
	
	@Override
	public boolean exec(Date baseDate) throws Exception {
		Map<Integer,String> map = getList();
		for(Integer in : map.keySet()){
			if(isBlock(in)){
				if(!StringUtils.isNumber(map.get(in).substring(0, 4))&&StringUtils.isNumber(map.get(in).substring(4, 8))){
					if("1234".equals(map.get(in).substring(4, 8))||"4321".equals(map.get(in).substring(4, 8))||"5678".equals(map.get(in).substring(4, 8))){
						updateBlock(in);
						insertReason(in);
					}
				}
			}
		}
		return true;
	}
	//获取没有拉黑的注册机帐号
	public Map<Integer,String> getList() throws Exception{
		String date = DateUtil.toString(DateUtil.getDateAfterDays(new Date(), -2), "yyyy-MM-dd");
		String sql ="select company_id,password from company_account where length(password)=8 and gmt_created>='"+date+"'";
		final Map<Integer,String> map = new HashMap<Integer,String>();
		DBUtils.select("ast", sql, new IReadDataHandler(){
			@Override
			public void handleRead(ResultSet rs)throws SQLException {
				while(rs.next()){
					map.put(rs.getInt(1), rs.getString(2));
				}
			}
		});
		return map;
	}
	
	//判断是否拉黑过
	public boolean isBlock(Integer companyId){
		String sql = "select id from company where id='"+companyId+"' and is_block='1'";
		final List<Integer> list = new ArrayList<Integer>();
		DBUtils.select("ast", sql, new IReadDataHandler(){
			@Override
			public void handleRead(ResultSet rs)throws SQLException {
				while(rs.next()){
					list.add(rs.getInt(1));
				}
			}
		});
		//已经拉黑
		if(list.size()>1){
			return false;
		}
		//没有被拉黑
		return true;
	}
	
	//拉黑
	public void updateBlock(Integer companyId){
		String sql = "update company set is_block='1',gmt_modified=now() where id='"+companyId+"'";
		DBUtils.insertUpdate("ast", sql);
	}
	
	//标记拉黑原因
	public void insertReason(Integer companyId){
		String sql = "insert into log_operation(target_id,operator,operation,remark,gmt_created,gmt_modified)"
				+ "VALUES('"+companyId+"','shiqp','black_operation','该客户发布的信息与本网站不符',now(),now())";
		DBUtils.insertUpdate("ast", sql);
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
