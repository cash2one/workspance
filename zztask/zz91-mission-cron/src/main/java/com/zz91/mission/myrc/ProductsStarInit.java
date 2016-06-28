package com.zz91.mission.myrc;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.log.LogUtil;

/**
 * 后台处理未有星级的供求数据
 * 
 * @author zhengruipeng
 *
 */
public class ProductsStarInit implements ZZTask {

	private int score; // 星级分数
	private Integer startId;// 查询供求表数据定时器的起始ID
	private static final String DB = "ast";
	private String timeOp = "2015-05-12";// 用于供求其实ID计算使用

	/**
	 * 查找供求列表信息（原版本只针对普会处理）（现在信息都处理）
	 * 
	 * @param star
	 * @return
	 * @throws Exception
	 */
	private List<Integer> productsSelect(Integer star) throws Exception {
		int end = star + 100;
		final List<Integer> list = new ArrayList<Integer>();
		String sql = "select company_id  from products where  id >='" + star
				+ "' and id<'" + end + "'";
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					list.add(rs.getInt(1));
				}
			}
		});
		// 剔除查询到数组中重复公司ID
		HashSet<Integer> h = new HashSet<Integer>(list);
		list.clear();
		list.addAll(h);
		return list;
	}

	/**
	 * 去高级会员处理（停用）
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	// private List<Integer> removeVip(List<Integer> list) throws Exception{
	// final List<Integer> listr=new ArrayList<Integer>();
	// for(final Integer companyid:list){
	// //10051000代表普通会员
	// String
	// sql="select id  from company  where membership_code='10051000' and id="+companyid;
	// DBUtils.select(DB, sql, new IReadDataHandler() {
	// @Override
	// public void handleRead(ResultSet rs) throws SQLException {
	// while (rs.next()) {
	// listr.add(rs.getInt(1));
	// }
	// }
	// });
	// }
	// return listr;
	// }

	/**
	 * 查询复合条件的供求ID
	 * 
	 * @param list
	 * @param star
	 * @return
	 * @throws Exception
	 */
	private List<Integer> afterProcessing(List<Integer> list, Integer star)
			throws Exception {
		int end = star + 100;
		final List<Integer> lista = new ArrayList<Integer>();
		for (final Integer companyid : list) {
			// 去除删除 审核不通过 根据普会员list查找符合条件的供求数据ID
			String sql = "select  id from  products where check_status!='2' and  is_del='0'  and   id >='"
					+ star
					+ "' and id<'"
					+ end
					+ "'  and company_id="
					+ companyid;
			DBUtils.select(DB, sql, new IReadDataHandler() {
				@Override
				public void handleRead(ResultSet rs) throws SQLException {
					while (rs.next()) {
						lista.add(rs.getInt(1));
					}
				}
			});
		}
		return lista;
	}

	/**
	 * 查询星级重复的数据并去除
	 * 
	 * @param lista
	 * @return
	 * @throws Exception
	 */
	private List<Integer> starData(List<Integer> lista) throws Exception {
		final List<Integer> lists = new ArrayList<Integer>();
		for (final Integer id : lista) {
			String sql = "select  products_id from products_star where products_id="
					+ id;
			DBUtils.select(DB, sql, new IReadDataHandler() {
				@Override
				public void handleRead(ResultSet rs) throws SQLException {
					while (rs.next()) {
						lists.add(rs.getInt(1));
					}
				}
			});
		}
		// 剔除查询到数组中重复
		lista.removeAll(lists);
		return lista;
	}

	/**
	 * 查询信息明细
	 * 
	 * @param lista
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private List<Map> Detailed(List<Integer> lista) throws Exception {
		final List<Map> listd = new ArrayList<Map>();
		for (final Integer id : lista) {
			String sql = "select  id,category_products_main_code,title,tags,quantity,price,location,ship_day,source,origin,manufacture,specification,color,appearance,impurity,details,useful  from products where id  ="
					+ id;
			DBUtils.select(DB, sql, new IReadDataHandler() {
				@Override
				public void handleRead(ResultSet rs) throws SQLException {
					Map<String, Object> map = new HashMap<String, Object>();
					while (rs.next()) {
						map.put("id", rs.getInt(1));// ID
						map.put("cpmc", rs.getString(2).replace(" ", ""));// 类别ID
						map.put("title", rs.getString(3).replace(" ", ""));// 标题
						map.put("tags", rs.getString(4).replace(" ", ""));//
						map.put("quantity", rs.getString(5).replace(" ", ""));// 数量
						map.put("price", rs.getString(6).replace(" ", ""));// 价格
						map.put("location", rs.getString(7).replace(" ", ""));// //地址
						map.put("shipday", rs.getInt(8));// 发货时间
						map.put("source", rs.getString(9).replace(" ", ""));// 货源地
						map.put("origin", rs.getString(10).replace(" ", ""));// 采原产品
						map.put("manufacture", rs.getString(11));// 加工说明
						map.put("specification",
								rs.getString(12).replace(" ", ""));// 产品规格
						map.put("color", rs.getString(13).replace(" ", ""));// 颜色
						map.put("appearance", rs.getString(14).replace(" ", ""));// 外观
						map.put("impurity", rs.getString(15).replace(" ", ""));// 杂质含量
						map.put("details", rs.getString(16).replace(" ", ""));// 详细描述
						map.put("useful", rs.getString(16).replace(" ", ""));// 用途
						listd.add(map);
					}
				}
			});
		}
		return listd;
	}

	/**
	 * starFraction方法内调用（根据ID查询附属信息）
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	private List<String> productAddProperties(Integer id) throws Exception {
		final List<String> listp = new ArrayList<String>();
		String sql = "select  property,content from product_addproperties where pid="
				+ id;
		DBUtils.select(DB, sql, new IReadDataHandler() {
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					listp.add(rs.getString(1));
					listp.add(rs.getString(2));
				}
			}
		});
		return listp;
	}

	/**
	 * 条件算分
	 * 
	 * @param listd
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private void starFraction(List<Map> listd) throws Exception {
		/*
		 * 公共属性算分
		 */
		for (int i = 0; i < listd.size(); i++) {
			score = 0;
			// 名称（标题）
			if (listd.get(i).get("title") != null
					&& !listd.get(i).get("title").equals("")) {
				score = score + 5;
			}
			// 标签
			if (listd.get(i).get("tags") != null
					&& !listd.get(i).get("tags").equals("")) {
				score = score + 5;
			}
			// 数量
			if (listd.get(i).get("quantity") != null
					&& !listd.get(i).get("quantity").equals("")) {
				score = score + 5;
			}
			// 价格
			if (listd.get(i).get("price") != null
					&& !listd.get(i).get("price").equals("")) {
				score = score + 5;
			}
			// 货源地
			if (listd.get(i).get("source") != null
					&& !listd.get(i).get("source").equals("")) {
				score = score + 5;
			}
			// 来源产品
			if (listd.get(i).get("origin") != null
					&& !listd.get(i).get("origin").equals("")) {
				score = score + 5;
			}
			// 加工说明
			if (listd.get(i).get("manufacture") != null
					&& !listd.get(i).get("manufacture").equals("")) {
				score = score + 5;
			}
			// 产品规格
			if (listd.get(i).get("specification") != null
					&& !listd.get(i).get("specification").equals("")) {
				score = score + 5;
			}
			// 根据ID获得附属字段信息
			List<String> listp = productAddProperties((Integer) listd.get(i)
					.get("id"));

			/*
			 * 交易条件非必填（交替说明，发货时间，发货）
			 */
			Integer shipDay = 0;
			if (listd.get(i).get("shipday") != null
					&& !listd.get(i).get("shipday").equals("")) {
				shipDay = (Integer) listd.get(i).get("shipday");
			}
			String location = null;
			if (listd.get(i).get("location") != null
					&& !listd.get(i).get("location").equals("")) {
				location = (String) listd.get(i).get("location");
			}
			score = score + tradingTerms(listp, shipDay, location);
			// 获得主CODE编号
			String cpmcs = "";
			String scode = ""; // 存放求得的String 0-4
			if (listd.get(i).get("cpmc") != null
					&& !listd.get(i).get("cpmc").equals("")) {
				cpmcs = (String) listd.get(i).get("cpmc");
				if (cpmcs.length() > 4) {
					scode = cpmcs.substring(0, 4);
				}
				else {
					scode = "notscode";
				}
			} 
			// 金属算法
			if (scode.equals("1000")) {
				score = score + Metal(listp);
			}
			// 塑料算法
			else if (scode.equals("1001")) {
				// 颜色
				if (listd.get(i).get("color") != null
						&& !listd.get(i).get("color").equals("")) {
					score = score + 3;
				}
				// 外观
				if (listd.get(i).get("appearance") != null
						&& !listd.get(i).get("appearance").equals("")) {
					score = score + 3;
				}
				// 杂质含量
				if (listd.get(i).get("impurity") != null
						&& !listd.get(i).get("impurity").equals("")) {
					score = score + 3;
				}
				// 用途
				if (listd.get(i).get("useful") != null
						&& !listd.get(i).get("useful").equals("")) {
					score = score + 3;
				}
				// 调用塑料方法
				score = score + Plastic(listp);
			}
			// 其他类别算法
			else if (scode.equals("1002") | scode.equals("1003")
					| scode.equals("1004") | scode.equals("1005")
					| scode.equals("1006") | scode.equals("1007")
					| scode.equals("1008") | scode.equals("1009")) {
				// 杂质含量
				if (listd.get(i).get("impurity") != null
						&& !listd.get(i).get("impurity").equals("")) {
					score = score + 3;
				}
				// 用途
				if (listd.get(i).get("useful") != null
						&& !listd.get(i).get("useful").equals("")) {
					score = score + 3;
				}
				// 调用其他类别算法
				score = score + otherCategories(listp);
			} else {
				score = score + 0;
			}
			// 图片算分
			if (listd.get(i).get("id") != null
					&& !listd.get(i).get("id").equals("")) {
				score = score + pic((Integer) listd.get(i).get("id"));
			}
			// 详细描述
			String details = (String) listd.get(i).get("details");
			score = score + Detailed(details);
			if (listd.get(i).get("id") != null
					&& !listd.get(i).get("id").equals("")) {
				starAdd((Integer) listd.get(i).get("id"), score);
			}
		}
	}

	/**
	 * 交易条件非必填算分
	 * 
	 * @param listp
	 * @param shipday
	 * @param location
	 * @return
	 */
	private int tradingTerms(List<String> listp, int shipday, String location) {
		score = 0;
		for (int i = 0; i < listp.size(); i++) {
			if (listp.get(i) != null && !listp.get(i).equals("")) {
				if (listp.get(i).equals("交易说明")) {
					if (listp.get(i + 1) != null
							&& !listp.get(i + 1).equals("") && shipday > 0
							&& location != null && !location.equals("")) {
						score = score + 5;
					}
				}
			} else {
				score = score + 0;
			}
		}
		return score;
	}

	/**
	 * 金属方法算分
	 * 
	 * @param listp
	 * @return
	 */
	private int Metal(List<String> listp) {
		int j = 0;
		score = 0;
		for (int i = 0; i < listp.size(); i++) {
			if (listp.get(i) != null && listp.get(i).equals("")) {
				if (listp.get(i).equals("品味") || listp.get(i).equals("形态")) {
					if (listp.get(i + 1) != null
							&& !listp.get(i + 1).equals("")) {
						score = score + 3;
					}
				} else if (listp.get(i).equals("属性")) {
					if (listp.get(i + 1) != null
							&& !listp.get(i + 1).equals("")) {
						j++;
						if (j < 5) {
							score = score + 3;
						}
					}
				}
			}
		}
		return score;
	}

	/**
	 * 塑料方法算分
	 * 
	 * @param listp
	 * @return
	 */
	private int Plastic(List<String> listp) {
		score = 0;
		for (int i = 0; i < listp.size(); i++) {
           if( listp.get(i) != null&& !listp.get(i).equals("")){
        		if (listp.get(i).equals("形态")||listp.get(i).equals("级别")){
        			if(listp.get(i + 1) != null&& !listp.get(i + 1).equals("")){
        				score = score + 3;
        			}        		
        		}
           }
		}
		return score;
	}

	/**
	 * 其他类型算分
	 * 
	 * @param listp
	 * @return
	 */
	private int otherCategories(List<String> listp) {
		score = 0;
		int j = 0;
		for (int i = 0; i < listp.size(); i++) {
			if (listp.get(i) != null && !listp.get(i).equals("")) {
				if (listp.get(i).equals("此废料可用于")) {
					if (listp.get(i + 1) != null
							&& !listp.get(i + 1).equals("")) {
						score = score + 3;
					}
				} else if (listp.get(i).equals("属性")) {
					if (listp.get(i + 1) != null
							&& !listp.get(i + 1).equals("")) {
						j++;
						if (j < 5) {
							score = score + 3;
						}
					}
				}
			}
		}

		return score;
	}

	/**
	 * 图片算分
	 * 
	 * @param id
	 * @return
	 */
	private int pic(Integer id) {
		score = 0;
		String sql = "select pic_address from products_pic where check_status!='2'   and  product_id="
				+ id;
		final List<String> list = new ArrayList<String>();
		DBUtils.select(DB, sql, new IReadDataHandler() {
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					list.add(rs.getString(1));
				}
			}
		});
		if (list.size() > 5) {
			score = score + 25;
		} else {
			score = score + list.size() * 5;
		}
		return score;
	}

	/**
	 * 详细描述算分
	 * 
	 * @param details
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private int Detailed(String details) throws UnsupportedEncodingException {
		score = 0;
		@SuppressWarnings("unused")
		String str;
		int i = 0;
		do {
			if (StringUtils.isEmpty(details)) {
				break;
			}
			if (!StringUtils.isContainCNChar(details)) {
				str = decryptUrlParameter(details);
			}
			String strCN = Jsoup.clean(details, Whitelist.none());
			String[] strIMG = details.split("<img");
			int j = lengthContainCNChar(strCN);

			if (j >= 50) {
				i = i + 10;
			} else if (j >= 30 && j < 50) {
				i = i + 5;
			}
			if (strIMG.length >= 3) {
				i = i + 10;
			} else {
				i = i + 5 * (strIMG.length - 1);
			}
			if (i > 20) {
				i = 20;
			}
		} while (false);
		score = score + i;
		return score;
	}

	/**
	 * 详细描述调用获取中文字长度
	 * 
	 * @param s
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static int lengthContainCNChar(String s)
			throws UnsupportedEncodingException {
		Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]+");
		Matcher matcher = pattern.matcher(new String(s.getBytes(), "UTF-8"));
		int i = 0;
		while (matcher.find()) {
			i = i + matcher.group().length();
		}
		return i;
	}

	/**
	 * 编码URL带的参数
	 * 
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String decryptUrlParameter(String str)
			throws UnsupportedEncodingException {
		if (isEmpty(str)) {
			return "";
		}
		return new String(str.getBytes("ISO-8859-1"), "UTF-8");
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return (str == null) || (str.trim().length() == 0);
	}

	/**
	 * 求得的分数后台写入 by productsId
	 * 
	 * @param id
	 * @param score
	 */
	private void starAdd(Integer id, Integer score) {
		if (score < 0) {
			score = 0;
		} else if (score > 100) {
			score = 100;
		} else {
			score = this.score;
		}
		String sql = "insert into  products_star  (products_id,score,gmt_created,gmt_modified)  values  ("
				+ id + "," + score + ",now(),now())";
		DBUtils.insertUpdate(DB, sql);
	}

	/**
	 * ZZTask的方法调用
	 */
	@Override
	public boolean init() throws Exception {
		return false;
	}

	/**
	 * ZZTask的方法调用 自动运行方法处理
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public boolean exec(Date oDate) throws Exception {

		// 根据时间计算出供求表开始运行的ID编号
		int starId = timeVariable(oDate);
		// 取得endid值
		int endId = endId(starId);
		// 根据starId和endId范围循环处理每次处理100条数据
		for (int star = starId; star < endId; star = star + 100) {
			// 根据starId编号查找出符合条件的公司编号
			List<Integer> list = productsSelect(star);
			// 查找出符合供求ID的lsitp
			List<Integer> lista = afterProcessing(list, star);
			// 根据lista与原有的星级表查的不重复的数据
			List<Integer> lists = starData(lista);
			// 根据条件筛选后的lists查找各个供求ID字段的明细
			List<Map> listd = Detailed(lists);
			// 明细分数判断后写入
			starFraction(listd);
		}
		return true;
	}

	/**
	 * ZZTask的方法调用
	 */
	@Override
	public boolean clear(Date baseDate) throws Exception {
		return false;
	}

	/**
	 * 根据时间球起始ID
	 * 
	 * @param oDate
	 * @return
	 * @throws Exception
	 */
	private int timeVariable(Date oDate) throws Exception {
		Date fDate = DateUtil.getDate(timeOp, "yyyy-MM-dd");
		// 计算出固定时间与当前时间的时间差
		int diffDays = DateUtil.getIntervalDays(oDate, fDate);
		// 算得起始供求Id
		int star = (int) ((diffDays - 1) * 10000);
		startId = star;
		return startId;
	}

	/**
	 * 根据起始时间算得结束id
	 * 
	 * @param starId
	 * @return
	 */
	private int endId(int starId) {
		// 取值范围增加10000
		int endId = starId + 10000;
		return endId;
	}

	/**
	 * main方法测试
	 * 
	 * @param args
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws Exception {
		DBPoolFactory.getInstance().init(
				"file:/usr/tools/config/db/db-zztask-jdbc.properties");
		LogUtil.getInstance().init("web.properties");
		ProductsStarInit myproductsStar = new ProductsStarInit();
		Date now = new Date();
		int starId = myproductsStar.timeVariable(now);
		int endId = myproductsStar.endId(starId);
		// 每100数据循环处理
		for (int star = starId; star < endId; star = star + 100) {
			// 根据starId编号查找出符合条件的公司编号
			List<Integer> list = myproductsStar.productsSelect(star);
			// 除去高级Listr后查找出符合供求ID的lsitp
			List<Integer> lista = myproductsStar.afterProcessing(list, star);
			// 根据lista与原有的星级表查的不重复的数据
			List<Integer> lists = myproductsStar.starData(lista);
			// 根据条件筛选后的lists查找各个供求ID字段的明细
			List<Map> listd = myproductsStar.Detailed(lists);
			// 明细分数判断后写入
			myproductsStar.starFraction(listd);
		}
	}

}
