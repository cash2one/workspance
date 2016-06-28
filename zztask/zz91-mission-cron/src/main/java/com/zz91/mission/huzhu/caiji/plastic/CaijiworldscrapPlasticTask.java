package com.zz91.mission.huzhu.caiji.plastic;

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
 * 互助抓取 世界再生网 对应bbs_post 中的类别:废料学院-行业知识-废塑料 采集10个网页链接
 * 采集地址:http://china.worldscrap.com/modules/cn/plastic/cndick_index.php?sort=7-1-1-1
 * http://china.worldscrap.com/modules/cn/plastic/cndick_index.php?sort=7-1-1-2
 * http://china.worldscrap.com/modules/cn/plastic/cndick_index.php?sort=7-1-2
 * http://china.worldscrap.com/modules/cn/plastic/cndick_index.php?sort=7-1-3
 * http://china.worldscrap.com/modules/cn/plastic/cndick_index.php?sort=7-1-4
 * http://china.worldscrap.com/modules/cn/plastic/cndick_index.php?sort=7-1-5
 * http://china.worldscrap.com/modules/cn/plastic/cndick_index.php?sort=7-1-6-1
 * http://china.worldscrap.com/modules/cn/plastic/cndick_index.php?sort=7-1-6-5
 * http://china.worldscrap.com/modules/cn/plastic/cndick_index.php?sort=7-1-6-4
 * http://china.worldscrap.com/modules/cn/plastic/cndick_index.php?sort=7-1-7-1
 * @author root
 *
 */
public class CaijiworldscrapPlasticTask implements ZZTask{
	final static String PRICE_OPERTION = "huzhu_caiji";
	final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	HuZhuCaiJiCommonOperate operate = new HuZhuCaiJiCommonOperate();
	final String DB = "ast";
	final static Map<String, String> QQZSS_MAP = new HashMap<String, String>();
	static {
		// httpget 使用的编码 不然会有乱码
		QQZSS_MAP.put("charset", "utf-8");

		// 采集地址 正式
		QQZSS_MAP
				.put("url",
						"http://china.worldscrap.com/modules/cn/metal/cndick_index.php?sort=8-1-3");
		QQZSS_MAP.put("contentUrl",
				"http://china.worldscrap.com/modules/cn/plastic/");
		QQZSS_MAP.put("listStart", "<table width=\"96%\""); // 列表页开头
		QQZSS_MAP.put("listEnd", "<td height=\"30\" bgcolor=\"#f4f4f4\">"); // 列表页结尾
		QQZSS_MAP.put("split", "<tr>");

		QQZSS_MAP.put("contentStart", "<td class=\"line_height_new\">");
		QQZSS_MAP.put("contentEnd", "<div style=\"text-align:center;\">");

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
		Integer typeId = 21;
		String url = "";
		Map<String, String> urlmap = new HashMap<String, String>();
		String tagsString = "";
		String errContentUrl = "";
		Integer max=0;
		do {
			for (Integer i = 1; i < 11; i++) {
				
				switch (i) {
				case 1:
					tagsString = "塑料知识";
					url="http://china.worldscrap.com/modules/cn/plastic/cndick_index.php?sort=7-1-1-1&groupid=&start=";
					max=2;
					break;
				case 2:
					tagsString = "塑料知识";
					url="http://china.worldscrap.com/modules/cn/plastic/cndick_index.php?sort=7-1-1-2&groupid=&start=";
					max=23;
					break;
				case 3:
					tagsString = "塑料鉴别";
					url="http://china.worldscrap.com/modules/cn/plastic/cndick_index.php?sort=7-1-2&groupid=&start=";
					max=4;
					break;
				case 4:
					tagsString = "塑料设备";
					url="http://china.worldscrap.com/modules/cn/plastic/cndick_index.php?sort=7-1-3&groupid=&start=";
					max=2;
					break;
				case 5:
					tagsString = "清洗干燥";
					url="http://china.worldscrap.com/modules/cn/plastic/cndick_index.php?sort=7-1-4&groupid=&start=";
					max=2;
					break;
				case 6:
					tagsString = "粉塑增密";
					url="http://china.worldscrap.com/modules/cn/plastic/cndick_index.php?sort=7-1-5&groupid=&start=";
					max=1;
					break;
				case 7:
					tagsString = "塑料助剂";
					url="http://china.worldscrap.com/modules/cn/plastic/cndick_index.php?sort=7-1-6-1&groupid=&start=";
					max=10;
					break;
				case 8:
					tagsString = "造粒";
					url="http://china.worldscrap.com/modules/cn/plastic/cndick_index.php?sort=7-1-6-5&groupid=&start=";
					max=2;
					break;
				case 9:
					tagsString = "回收挤出机";
					url="http://china.worldscrap.com/modules/cn/plastic/cndick_index.php?sort=7-1-6-4&groupid=&start=";
					max=1;
					break;
				case 10:
					tagsString = "加工工艺";
					url="http://china.worldscrap.com/modules/cn/plastic/cndick_index.php?sort=7-1-7-1&groupid=&start=";
					max=4;
					break;
				default:
					break;
				}

				for (Integer j = 1; j < max+1; j++) {
					String content = "";
					String url1="";
					try {
						url1 = url + (j - 1) * 40;
						content = operate.httpClientHtml(url1,
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
								Integer starts = aStr.indexOf("href=");
								if (starts == -1) {
									sendEmail(url, typeId, defaultTime,tagsString);
									continue;
								}
								Integer ends = aStr.indexOf("target");
								if (ends == -1) {
									sendEmail(url, typeId, defaultTime,tagsString);
									continue;
								}
								linkStr = QQZSS_MAP.get("contentUrl")
										+ aStr.substring(starts + 6, (ends - 2));
								String resultContent = "";
								try {
									resultContent = operate.httpClientHtml(
											linkStr, QQZSS_MAP.get("charset"));
								} catch (IOException e) {
									resultContent = null;
								}
								Integer contentstart = resultContent
										.indexOf(QQZSS_MAP.get("contentStart"));

								if (contentstart == -1) {
									sendEmail(url, typeId, defaultTime,tagsString);
									continue;
								}

								Integer contentend = resultContent
										.indexOf(QQZSS_MAP.get("contentEnd"));

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

								String tags = "废塑料," + tagsString;
								BbsPostCaiJi bbsPostCaiJi = new BbsPostCaiJi();
								bbsPostCaiJi.setBbsPostCategoryId(3);
								bbsPostCaiJi.setBbsPostAssistId(21);
								bbsPostCaiJi.setCategory("废塑料,行业知识,废料学院");
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
							"{'title':'世界再生网-("
									+tagsString+ typeId
									+ ")','type':'success','url':'<a href='"
									+ url
									+ "' target='_blank'>"
									+ "世界再生网</a>','defaultTime':'"
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
				+ "<font color='#FF0000'>互助废料学院-废塑料 世界再生网"
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
				"{'title':'互助废料学院-废塑料 世界再生网("+tagsString + typeId
						+ ")','type':'failure','url':'<a href='" + url
						+ "' target='_blank'>" + "世界再生网</a>','defaultTime':'"
						+ defaultTime + "','date':'"
						+ DateUtil.toString(new Date(), DATE_FORMAT) + "'}");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("content", errContentUrl);
		MailUtil.getInstance().sendMail("互助废料学院-废塑料 世界再生网自动抓取报错"+tagsString,
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
		CaijiworldscrapPlasticTask js = new CaijiworldscrapPlasticTask();
		try {
			js.exec(DateUtil.getDate(new Date(), "yyyy-MM-dd"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}
}
