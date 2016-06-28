package com.zz91.mission.sample;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;

/**
 * <br />
 * 任务描述： <br />
 * 1.下单后，3日之内未付款，系统将取消订单。 2.收货之后，未确认收货，将在15天后系统自动确认收货
 * 
 * created on 2014-7-15
 */
public class OrderExpiredTask implements ZZTask {

	public static String DB = "ast";
	final static String DATE_FORMATE = "yyyy-MM-dd";

	@Override
	public boolean clear(Date baseDate) throws Exception {
		return false;
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {

		baseDate = DateUtil.getDate(baseDate, DATE_FORMATE);
		
		/**
		 * 下单后，3日之内未付款，系统将取消订单
		 */
		String baseDateStr = DateUtil.toString(DateUtil.getDateAfterDays(baseDate, -3), "yyyy-MM-dd HH:mm:ss");
		final Set<int[]> expiredOrder = new HashSet<int[]>();
		// 下单后，3日之内未付款订单
		String sql = "select  id,sample_id,number   from sample_orderbill where (state = '00' or state = '01') and  '" + baseDateStr+ "' > gmt_created  ";
		queryCid(sql, expiredOrder);
		// 更新所有过期订单
		for (int[] data : expiredOrder) {
			// 更新sample_orderbill的状态
			DBUtils.insertUpdate(DB,"update sample_orderbill  set  state = '20',    close_reason='order timeout', gmt_modified=now() where id=" + data[0]);
			// 样品数量自动增加
			DBUtils.insertUpdate(DB, "update sample  set amount =  amount +  " + data[2] + "  where id=" + data[1]);
		}
		
		
		/**
		 * 收货之后，未确认收货，将在15天后系统自动确认收货
		 */
		String baseDateStr2 = DateUtil.toString(DateUtil.getDateAfterDays(baseDate, -15), "yyyy-MM-dd HH:mm:ss");
		final Set<Map<String, Object>> expiredOrder2 = new HashSet<Map<String, Object>>();
		// 收货之后，未确认收货，将在15天后系统自动确认收货
		String sql2 = "select  id,seller_id,amount,orderid,tran_type  from sample_orderbill where  state = '11'  and  '" + baseDateStr2+ "' > gmt_modified  ";
		queryCid2(sql2, expiredOrder2);
		// 更新所有过期订单
		for (Map<String, Object>  data : expiredOrder2) {
			
			BigDecimal  changeAmount = (BigDecimal) data.get("amount");//订单金额
			Integer seller_id  = (Integer) data.get("seller_id");//卖方公司ID
			Integer id  = (Integer) data.get("id");//订单ID
			String orderid  = (String) data.get("orderid");//订单编号
			String tran_type  = (String) data.get("tran_type"); //交易类型
			
			/**
			 * 总交易金额 !=0，记账处理
			 */
			if (changeAmount.compareTo(new BigDecimal(0)) == 1) {
				
				/**
				 * 卖家账户增加金额 ，记录流水表
				 */
				//sellerAcc 信息
				String sql3 = "select id,amount  from sample_account  where company_id='"+ seller_id + "'";
				Map<String, Object>  sellerAcc = new HashMap<String, Object>();
				queryCid3(sql3, sellerAcc);
				
				Integer sellerAccId = (Integer) sellerAcc.get("id"); //账户ID
				BigDecimal  amountPre = (BigDecimal) sellerAcc.get("amount");//账户金额
				BigDecimal  amountNew = amountPre.add(changeAmount);
				//记录流水表
				saveToAccountseqDB( sellerAccId,  "0",  tran_type,  amountPre,  amountNew, changeAmount,  id,  orderid,  "客户账户--虚拟户--来账--交易成功--timeout") ;
				//更新账户
				DBUtils.insertUpdate(DB, "update sample_account  set amount =  amount +  " +changeAmount+ " , gmt_modified=now()    where id=" + sellerAccId );
				
				
				/**
				 * 平台内部资金虚拟账户 减少金额 ，记录流水表
				 */
				//sellerAcc 信息
				String sql4 = "select id,amount  from sample_account  where id= 1 ";
				Map<String, Object>  innerAcc = new HashMap<String, Object>();
				queryCid3(sql4, innerAcc);
				
				Integer innerAccId = (Integer) innerAcc.get("id"); //账户ID
				BigDecimal  innerAmountPre = (BigDecimal) innerAcc.get("amount");//账户金额
				BigDecimal  InnerAmountNew = innerAmountPre.subtract(changeAmount);//减少金额
				//记录流水表
				saveToAccountseqDB( innerAccId,  "1",  tran_type,  innerAmountPre,  InnerAmountNew, changeAmount,  id,  orderid,  "平台账户--虚拟户--往账--交易成功--timeout") ;
				//更新账户
				DBUtils.insertUpdate(DB, "update sample_account  set amount =  amount -  " +changeAmount+ " , gmt_modified=now()   where id=" + innerAccId );
			}
			
			// 更新sample_orderbill的状态
			DBUtils.insertUpdate(DB,"update sample_orderbill  set  state = '13',    ordernote='order timeout 15day ',  gmt_modified=now() where id=" + data.get("id"));
		}
		
		
		return true;
	}
	
	private void saveToAccountseqDB(Integer accId, String seqFlag, String changeType, BigDecimal amountPre, BigDecimal amountNew,
			BigDecimal changeAmount, Integer tranSn, String orderId, String note) {
		String insertSql = "INSERT INTO sample_accountseq (`account_id`,`seqflag`,`change_type`,`preamount`,`amount`,`change_amount`,`refsn`,`orderid`,`note`,`gmt_created`)"
				+ "values"
				+ "('"
				+ accId
				+ "','"
				+ seqFlag
				+ "','"
				+ changeType
				+ "','"
				+ amountPre
				+ "','"
				+ amountNew
				+ "','"
				+ changeAmount 
				+ "','" 
				+ tranSn 
				+ "','" 
				+ orderId 
				+ "'," 
				+ "'" 
				+ note 
				+ "',now())";
		DBUtils.insertUpdate(DB, insertSql);
	}
	

	private void queryCid3(String sql, final Map<String, Object>  acc) {
		DBUtils.select(DB, sql, new IReadDataHandler() {

			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					acc.put("id", rs.getInt(1));
					acc.put("amount", rs.getBigDecimal(2));
				}
			}
		});
	}
	
	private void queryCid2(String sql, final Set<Map<String, Object>> set) {
		DBUtils.select(DB, sql, new IReadDataHandler() {
			
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", rs.getInt(1));
					map.put("seller_id", rs.getInt(2));
					map.put("amount", rs.getBigDecimal(3));
					map.put("orderid", rs.getString(4));
					map.put("tran_type", rs.getString(5));
					set.add(map);
				}
			}
		});
	}
	
	
	private void queryCid(String sql, final Set<int[]> set) {
		DBUtils.select(DB, sql, new IReadDataHandler() {
			
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					int[] arr = new int[3];
					arr[0] = rs.getInt(1);
					arr[1] = rs.getInt(2);
					arr[2] = rs.getInt(3);
					set.add(arr);
				}
			}
		});
	}

	@Override
	public boolean init() throws Exception {
		return false;
	}

	public static void main(String[] args) {
		DBPoolFactory.getInstance().init("file:/usr/tools/config/db/db-zztask-jdbc.properties");

		OrderExpiredTask task = new OrderExpiredTask();
		try {
			task.exec(DateUtil.getDate("2014-8-15", "yyyy-MM-dd"));
		} catch (ParseException e) {
		} catch (Exception e) {
		}
	}
}