package com.zz91.mission.trust;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.zz91.task.common.ZZTask;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;

public class TrustCrmStarTask implements ZZTask{

	final static String DB = "ast";
	
	@Override
	public boolean init() throws Exception {
		return false;
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		// 获取所有的crm公司信息 select
		final Map<Integer, Integer> resultMap = new HashMap<Integer, Integer>();
		String sql = "select company_id from trust_crm limit 0,1000";
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					// 分组统计 循环
					resultMap.put(rs.getInt(1), 0);
				}
			}
		});
		// 获取最新的 星级 select 循环 比较
		for (Integer companyId :resultMap.keySet()) {
			resultMap.put(companyId, getStar(companyId));
		}
		// 更新 update
		for (Integer companyId :resultMap.keySet()) {
			update(companyId, resultMap.get(companyId));
		}
		return true;
	}
	
	private Integer getStar(Integer companyId){
		final Map<Long, Integer> starMap = new TreeMap<Long, Integer>().descendingMap();
		Integer star = 0;
		// 获取公司最新小计和星级
		String sql = "select star,gmt_created from  trust_company_log  where company_id = "+companyId + " order by id desc limit 0,1";
		DBUtils.select(DB, sql, new  IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					if (rs.getDate(2)!=null) {
						starMap.put(rs.getDate(2).getTime(), rs.getInt(1));
					}
				}
			}
		});
		// 获取公司所有采购，获取采购小计最新的星级和时间
		sql = "select p.star,p.gmt_created from  trust_buy_log p where exists( select id from trust_buy where p.buy_id = id and company_id = "+companyId+")";
		DBUtils.select(DB, sql, new  IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					if (rs.getDate(2)!=null) {
						starMap.put(rs.getDate(2).getTime(), rs.getInt(1));
					}
				}
			}
		});
		for (Long l :starMap.keySet()) {
			if (starMap.get(l)!=null&&starMap.get(l)>0) {
				star = starMap.get(l);
				break;
			}
		}
		return star;
	}
	
	private void update(Integer companyId,Integer star){
		String sql = "update trust_crm set star="+star+", gmt_modified = now() where company_id ="+companyId;
		DBUtils.insertUpdate(DB, sql);
	}

	@Override
	public boolean clear(Date baseDate) throws Exception {
		return false;
	}
	
	public static void main(String[] args) throws Exception{
		DBPoolFactory.getInstance().init("file:/mnt/tools/config/db/db-zztask-jdbc.properties");
		TrustCrmStarTask obj = new TrustCrmStarTask();
		obj.exec(new Date());
		System.out.println("ye");
	}

}
