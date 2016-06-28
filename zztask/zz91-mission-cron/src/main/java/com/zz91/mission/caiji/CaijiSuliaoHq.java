package com.zz91.mission.caiji;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.zz91.mission.domain.subscribe.Price;
import com.zz91.task.common.ZZTask;
import com.zz91.util.db.pool.DBPoolFactory;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.mail.MailUtil;

public class CaijiSuliaoHq implements ZZTask {
	final static Map<String, String> map = new HashMap<String, String>();
	static {
		map.put("charset", "utf-8");
		// 请求的url ABS PP PE PVC
		map.put("url",
				"http://www.dgs6.com/Information/List148__.html,http://www.dgs6.com/Information/List143__.html,http://www.dgs6.com/Information/List144__.html,http://www.dgs6.com/Information/List146__.html");

		map.put("listStart", "<div class=\"infor_left_rept\">");// 列表开始
		map.put("listEnd", "<div class=\"paging\">");// 列表结束
		map.put("typeId", "333,334,335,336");// 类别列表

		map.put("detailStart", "<div class=\"content\">");// 文本开始
		map.put("detailEnd", "<div class=\"near_topic\">"); // 文本结束
	}

	@Override
	public boolean init() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		CaiJiCommonOperate oprate = new CaiJiCommonOperate();
		String url = map.get("url");
		String typeId = map.get("typeId");
		String[] type = typeId.split(",");
		String[] ur = url.split(",");
		for (int i = 0; i < ur.length; i++) {
			Integer typeId2 = Integer.valueOf(type[i]);
			url = ur[i];
			String title = "";
			String errContentUrl = "";
			String content = "";
			SimpleDateFormat formart = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formart2 = new SimpleDateFormat("MM月dd日");
			String time = formart.format(baseDate);
			String time2 = formart2.format(baseDate);
			content = HttpUtils.getInstance().httpGet(url, map.get("charset"));
			content=content.substring(content.indexOf(map.get("listStart")), content.indexOf(map.get("listEnd")));
			if (content == null) {
				errContentUrl = errContentUrl
						+ " 抓取来源网站内容失败！请点击<a href='"
						+ url
						+ "'>"
						+ "<font color='#FF0000'>行情报价"
						+ "("
						+ typeId2
						+ ")"
						+ "</font></a>查看来源网站是否更改地址！<br />"
						+ "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_feijs_scrap.htm?typeId="
						+ typeId2 + "'>"
						+ "<font color='#FF0000'>抓取</font></a>！";
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("content", errContentUrl);
				MailUtil.getInstance().sendMail("行情报价自动抓取报错",
						"785395338@qq.com", null, null, "zz91", "blank", map,
						MailUtil.PRIORITY_DEFAULT);
				return false;
			}
			String[] str = content.split("<li>");
			for (String cont : str) {
				if (cont.contains(time)) {
					title = cont.substring(cont.indexOf("title=")+"title=".length(),
							cont.indexOf("target")).replaceAll("\"", "");
					url = "http://www.dgs6.com"
							+ cont.substring(cont.indexOf("href=")+"href=".length(),
									cont.indexOf("title=")).replaceAll("\"", "");
					content = HttpUtils.getInstance().httpGet(url,
							map.get("charset"));
					if (content == null) {
						errContentUrl = errContentUrl
								+ " 抓取来源网站内容失败！请点击<a href='"
								+ url
								+ "'>"
								+ "<font color='#FF0000'>行情报价"
								+ "("
								+ typeId2
								+ ")"
								+ "</font></a>查看来源网站是否更改地址！<br />"
								+ "如果有，请<a href='http://admin1949.zz91.com/web/zz91/auto/caiji/caiji_feijs_scrap.htm?typeId="
								+ typeId2 + "'>"
								+ "<font color='#FF0000'>抓取</font></a>！";
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("content", errContentUrl);
						MailUtil.getInstance().sendMail("行情报价自动抓取报错",
								"785395338@qq.com", null, null, "zz91",
								"blank", map, MailUtil.PRIORITY_DEFAULT);
						return false;
					}
					// 获取正文文本
					content = content.substring(
							content.indexOf(map.get("detailStart")),
							content.indexOf(map.get("detailEnd")));
					String typeName = oprate.queryTypeNameByTypeId(typeId2);
					Price price = new Price();
					price.setTitle(time2 + title);
					price.setTypeId(typeId2);
					price.setTags(typeName);
					price.setContent(content);
					price.setGmtOrder(baseDate);
					Integer flg = oprate.doInsert(price, false);
					if (flg == null) {
						errContentUrl = errContentUrl + "数据存数据库失败！请联系网页抓取开发者!";
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("content", errContentUrl);
						MailUtil.getInstance().sendMail("行情报价自动抓取报错",
								"785395338@qq.com", null, null, "zz91",
								"blank", map, MailUtil.PRIORITY_DEFAULT);
						return false;
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean clear(Date baseDate) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public static void main(String[] args) throws Exception {
		DBPoolFactory.getInstance().init(
				"file:/usr/tools/config/db/db-zztask-jdbc.properties");
		CaijiSuliaoHq sq = new CaijiSuliaoHq();
		Date date = new Date();
		sq.exec(date);
	}

}
