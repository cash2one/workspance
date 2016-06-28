package com.zz91.mission.huzhu.caiji.policies;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.zz91.mission.domain.huzhu.BbsPostCaiJi;
import com.zz91.mission.huzhu.caiji.HuZhuCaiJiCommonOperate;
import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.pool.DBPoolFactory;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.log.LogUtil;
import com.zz91.util.mail.MailUtil;
/**
 * 互助抓取 中再交易网 对应bbs_post 中的类别:废料学院-政策法规
 * 采集地址:http://www.crra010.com/Article/articleList/cateId/10.html
 * @author root
 *
 */
public class Caijicrra010PoliciesTask implements ZZTask {
	final static String PRICE_OPERTION = "huzhu_caiji";
	final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	HuZhuCaiJiCommonOperate operate = new HuZhuCaiJiCommonOperate();
	final String DB = "ast";
	final static Map<String, String> QQZSS_MAP = new HashMap<String, String>();
	static {
		// httpget 使用的编码 不然会有乱码
		QQZSS_MAP.put("charset", "gb2312");

		// 采集地址 正式
		QQZSS_MAP.put("url","http://www.crra010.com/Article/articleList/cateId/10.html");
		QQZSS_MAP.put("contentUrl","http://www.crra010.com/");
		QQZSS_MAP.put("listStart", "<div class=\"mainNewsList\">"); // 列表页开头
		QQZSS_MAP.put("listEnd", "<div class=\"nextPage\">"); // 列表页结尾
		QQZSS_MAP.put("split", "<li>");

		QQZSS_MAP.put("contentStart", "<var><b>");
		QQZSS_MAP.put("contentEnd", "<span id=\"sourceRight\">");

	}

	@Override
	public boolean init() throws Exception {
		return false;
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		String defaultTime = DateUtil.toString(baseDate, DATE_FORMAT)
				.substring(11, 16);
		int flagFailure = 0;
		Integer typeId = 24;
		String url = "";
		Map<String, String> urlmap = new HashMap<String, String>();
		String tagsString = "政策法规";
		String errContentUrl = "";
		do {
				for (Integer j = 1; j < 35; j++) {
					String content = "";
					try {
						url ="http://www.crra010.com/Article/articleList/cateId/10/p/"+j;
						content = operate.httpClientHtml(url,
								QQZSS_MAP.get("charset"));
					} catch (IOException e) {
						content = null;
					}

					Integer start = content.indexOf(QQZSS_MAP.get("listStart"));
					if (start == -1) {
						sendEmail(url, typeId, defaultTime,tagsString);
						continue;
					}
					Integer end = content.indexOf(QQZSS_MAP.get("listEnd"));
					if (end == -1) {
						sendEmail(url, typeId, defaultTime,tagsString);
						continue;
					}
					String result = content.substring(start, end);
					String[] str = result.split(QQZSS_MAP.get("split"));

					// 检查日期是否对应今天
					// String formatDate = DateUtil.toString(baseDate,
					// "yyyy-MM-dd");
					List<String> list = new ArrayList<String>();

					for (String s : str) {
						if (s.indexOf("<a") != -1) {
							list.add(s);
						}
					}

					if (list.size() < 1) {
						continue;

					}
					
					for (String aStr : list) {
						
						
						if (aStr != null) {
							String linkStr = "";
							linkStr = operate.getAlink(aStr);
							if (StringUtils.isEmpty(linkStr)) {
								sendEmail(url, typeId, defaultTime,tagsString);
								continue;
							} else {
								String resultTitle = Jsoup.clean(linkStr,
										Whitelist.none());
								Integer starts = linkStr.indexOf("href=");
								if (starts == -1) {
									sendEmail(url, typeId, defaultTime,tagsString);
									continue;
								}
								Integer ends = linkStr.indexOf(".html");
								if (ends == -1) {
									sendEmail(url, typeId, defaultTime,tagsString);
									continue;
								}
								linkStr = QQZSS_MAP.get("contentUrl")
										+ linkStr.substring(starts + 6, ends+5);
								String resultContent = "";
								try {
									resultContent = operate.httpClientHtml(
											linkStr, QQZSS_MAP.get("charset"));
								} catch (IOException e) {
									resultContent = null;
								}
								Integer contentstart = resultContent.indexOf(QQZSS_MAP.get("contentStart"));
								
								if (contentstart == -1) {
									sendEmail(url, typeId, defaultTime,tagsString);
									continue;
								}

								Integer contentend = resultContent.indexOf(QQZSS_MAP.get("contentEnd"));

								if (contentend == -1) {
									sendEmail(url, typeId, defaultTime,tagsString);
									continue;
								}
								resultContent = resultContent.substring(
										contentstart, contentend);
								resultContent = Jsoup
										.clean(resultContent,
												Whitelist
														.none()
														.addTags("p", "ul",
																"br", "li",
																"th", "tr",
																"td")
														.addAttributes("td",
																"colspan"));

								String tags = tagsString;
								BbsPostCaiJi bbsPostCaiJi = new BbsPostCaiJi();
								bbsPostCaiJi.setBbsPostCategoryId(3);
								bbsPostCaiJi.setBbsPostAssistId(24);
								bbsPostCaiJi.setCategory("政策法规,废料学院");
								bbsPostCaiJi.setContent(resultContent);
								bbsPostCaiJi.setTitle(resultTitle);
								bbsPostCaiJi.setTags(tags);

								boolean flg = operate.insert(bbsPostCaiJi);
								// 执行插入并判断插入是否成功
								if (!flg) {
									errContentUrl = errContentUrl
											+ "数据存数据库失败！请联系网页抓取开发者！<a href='"
											+ urlmap.get(resultTitle) + "'>"
											+ "<font color='#FF0000'>"
											+ resultTitle + "</font></hosta><br />";
									flagFailure++;
								}
							}
						}

					}
					
				}
			

		} while (false);
		if (flagFailure != 0) {
			sendEmail(url, typeId, defaultTime,tagsString);
			return false;
		} else {
			String catchTime = DateUtil.toString(new Date(), DATE_FORMAT)
					.substring(11, 16);
			LogUtil.getInstance()
					.log("caiji-auto",
							PRICE_OPERTION,
							null,
							"{'title':'中再交易网-("
									+tagsString+ typeId
									+ ")','type':'success','url':'<a href='"
									+ url
									+ "' target='_blank'>"
									+ "中再交易网</a>','defaultTime':'"
									+ defaultTime
									+ "','catchTime':'"
									+ catchTime
									+ "','date':'"
									+ DateUtil
											.toString(new Date(), DATE_FORMAT)
									+ "'}");
			return true;
		}
	}

	public void sendEmail(String url, Integer typeId, String defaultTime,String tagsString)
			throws ParseException {
		String errContentUrl = " 抓取失败，来源网站没有数据可抓取！请点击<a href='"
				+ url
				+ "'>"
				+ "<font color='#FF0000'>互助废料学院-政策法规 中再交易网"
				+ "("+tagsString
				+ typeId
				+ ")"
				+ "</font></a>查看来源网站<br />"
				+ "如果有，请<a href='http://apps2.zz91.com/task/job/definition/doTask.htm?jobName=CaijiQqzsslPlasticTask&start="
				+ DateUtil.getDate(new Date(), "yyyy-MM-dd") + " 00:00:00'>"
				+ "<font color='#FF0000'>抓取</font></a>！";
		LogUtil.getInstance().log(
				"caiji-auto",
				PRICE_OPERTION,
				null,
				"{'title':'互助废料学院-政策法规 中再交易网("+tagsString + typeId
						+ ")','type':'failure','url':'<a href='" + url
						+ "' target='_blank'>" + "中再交易网</a>','defaultTime':'"
						+ defaultTime + "','date':'"
						+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("content", errContentUrl);
		MailUtil.getInstance().sendMail("互助废料学院-政策法规 中再交易网自动抓取报错"+tagsString,
				"zz91.price.caiji.auto@asto.mail", null, null, "zz91", "blank",
				map, MailUtil.PRIORITY_DEFAULT);

	}

	@Override
	public boolean clear(Date baseDate) throws Exception {
		return false;
	}

	public static void main(String[] args) {

		DBPoolFactory.getInstance().init(
				"file:/usr/tools/config/db/db-zztask-jdbc.properties");
		long start = System.currentTimeMillis();
		Caijicrra010PoliciesTask js = new Caijicrra010PoliciesTask();
		try {
			js.exec(DateUtil.getDate(new Date(), "yyyy-MM-dd"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}
}
