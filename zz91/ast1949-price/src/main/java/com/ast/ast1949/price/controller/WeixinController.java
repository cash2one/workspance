package com.ast.ast1949.price.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.impl.client.DefaultHttpClient;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ast.ast1949.domain.price.PriceCategoryDO;
import com.ast.ast1949.domain.price.PriceDO;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.service.price.PriceCategoryService;
import com.ast.ast1949.service.products.ProductsService;
import com.ast.ast1949.service.wechat.WXReplyService;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;

/**
 * author:kongsj date:2013-5-22
 */
@Controller
public class WeixinController extends BaseController {

	private static final String TOKEN = "weixin";

	// private static final String AppId = "wx2891ef70c5a770d6";
	//
	// private static final String AppSecret =
	// "d3f9436cfc50cd9e4f62f96893a1ee0c";

	private static final String PIC_PATH = "http://img1.zz91.com/";

	private static final String MOBILE_PRODUCT_PATH = "http://m.zz91.com/standard/productdetail/?pdtid=";

	private static final String MOBILE_PRICE_PATH = "http://m.zz91.com/standard/priceviews/?id=";

	private static final String NO_PIC_PATH = "http://img0.zz91.com/front/images/global/noimage.gif";

	private static final String MOBILE_PRICE_PIC_PATH = "http://img0.zz91.com/zz91/weixin/images/objectLogo/";

	private static final String MORE_PIC_PATH = "http://img0.zz91.com/zz91/weixin/images/more.jpg";

	private static final String MORE_PRODUCTS_LINK = "http://m.zz91.com/standard/productslist/?";

	private static final String RESPONSE_TXT = "<xml>"
			+ "<ToUserName><![CDATA[%s]]></ToUserName><FromUserName><![CDATA[%s]]></FromUserName><CreateTime>%s</CreateTime><MsgType><![CDATA[%s]]></MsgType>"
			+ "<Content><![CDATA[%s]]></Content><FuncFlag>0</FuncFlag></xml>";

	private static final String RESPONSE_HEAD_NEWS = "<xml>"
			+ "<ToUserName><![CDATA[%s]]></ToUserName><FromUserName><![CDATA[%s]]></FromUserName><CreateTime>%s</CreateTime><MsgType><![CDATA[%s]]></MsgType>"
			+ "<ArticleCount>%s</ArticleCount>" + "<Articles>";
	private static final String RESPONSE_BODY_NEWS = "<item>"
			+ "<Title><![CDATA[%s]]></Title><Description><![CDATA[%s]]></Description>"
			+ "<PicUrl><![CDATA[%s]]></PicUrl><Url><![CDATA[%s]]></Url>"
			+ "</item>";
	private static final String RESPONSE_FOOT_NEWS = "</Articles></xml>";

	// http客户端
	public static DefaultHttpClient httpclient;

	@Resource
	private WXReplyService wxReplyService;

	@Resource
	private PriceCategoryService priceCategoryService;

	@RequestMapping
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// // 验证
		// String signature = request.getParameter("signature");
		// String timestamp = request.getParameter("timestamp");
		// String nonce = request.getParameter("nonce");
		//
		// PrintWriter out = response.getWriter();
		// if (checkSignature(signature, timestamp, nonce)) {
		// out.print(request.getParameter("echostr"));
		// }
		// out.close();
		// out = null;
		// // 验证结束

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		Document doc = null;
		SAXReader reader = new SAXReader();
		InputStream in = request.getInputStream();
		try {
			doc = reader.read(in);
			Element root = doc.getRootElement();
			String toUserName = root.element("ToUserName").getTextTrim();
			String fromUserName = root.element("FromUserName").getTextTrim();
			String fromUserContent = "";
			String content = "";

			String msgType = root.element("MsgType").getTextTrim();
			if ("event".equals(msgType)) {
				fromUserContent = root.element("EventKey").getTextTrim();
			} else if ("text".equals(msgType)) {
				fromUserContent = root.element("Content").getTextTrim();
			} else {
				fromUserContent = "notice";
			}
			do {
				if ("1".equals(fromUserContent)) {
					content = "请回复 \n报价+地区+产品 或者 地区+产品+报价 \n如：\n报价广东废铜\n上海废铝报价\n国内PP报价\n报价余姚PVC";
				}
				if ("2".equals(fromUserContent)) {
					content = "请回复 \n求购+产品名/供应+产品名 \n如：\n求购PP\n供应废铜";
				}
				if ("3".equals(fromUserContent)) {
					content = "ZZ91现有以下几种服务类型，请回复具体类型了解详情。再生通；品牌通；黄金展位；百度优化；来电宝";
				}
				// 查看 再生通 事件
				if ("View_zst".equals(fromUserContent)) {
					content = "“再生通”服务是基于再生行业中小企业的原材料 采购和产品推广需求而推出的一款网络营销服务。\n1.无限量查阅最新供求信息\n2.及时产品行情报价\n3.企业独立商铺展示产品\n4.移动生意管家手机服务\n5.供求信息优先排名\n6.线下展会推广和客户资源独享\n7.全网广告推广\n8.QQ群人脉和商机\n\n服务申请热线 0571-56611111";
				}
				// 查看 品牌通 事件
				if ("View_ppt".equals(fromUserContent)) {
					content = "“品牌通”会员是ZZ91.COM的一种会员身份，享受再生通的全部服务，另外享受网上24小时旺铺、黄金地段品牌广告以及其他例如排名优先，邮件和短信推广服务！品牌通会员服务的目的是帮助您解决有店无客的难题，实现从您找客户到客户找您的转变!服务申请热线 0571-56611111";
				}
				// 查看 百度推广 事件
				if ("View_baidu".equals(fromUserContent)) {
					content = "1、八年专业运作团队，让您的商机在百度排名中持续靠前\n2、免费获赠独立精美商铺，让更多人可以找到你，最重要的是不用你多花一分钱\n3、不达标不收费，达标稳定后才计费，一次低投入，畅享全年高效回报\n4、持续改进效果，一年价格享有超过13月服务，每月提供专业优化月报，如实报告排名情况及给出优化建议\n服务申请热线 0571-56611111";
				}

				// 查看 品牌推广 事件
				if ("View_ads".equals(fromUserContent)) {
					content = "品牌推广是针对再生通客户和有实力的新客户推出的一个会员广告服务体系。以个性化、动态大图片的形式，固定展示在网站首页、公司页、搜索页和产品页等，并且拥有高贵的黄金门市部。每位品牌会员企业网站和供求信息都有特色标志，突显企业实力。服务申请热线 0571-56611111";
				}

				// 查看 最新活动 事件
				if ("View_Latest_Events".equals(fromUserContent)) {
					out
							.printf(
									RESPONSE_HEAD_NEWS + RESPONSE_BODY_NEWS
											+ RESPONSE_FOOT_NEWS,
									fromUserName,
									toUserName,
									System.currentTimeMillis(),
									"news",
									"1",
									"废料双十一，错过一天，后悔一年",
									"还在守着淘宝，京东过双十一，今年你有更好的选择。ZZ91再生网，废料行业首个双十一等你来抢。\n"
											+ "5号-10号，每天早上10点整与下午15点整，原价200的黄页大全，19.9元秒杀，一天只有50本哦！\n"
											+ "11号当天，更有再生通1年，再生通2年、品牌通1年，统统给你最低冰点价，限购100份，错过这个价格，绝对让你后悔一年。\n"
											+ "还在等什么？赶紧上www.zz91.com，加入废料双十一的抢购大军吧!",
									"http://img0.zz91.com/zz91/images/1111.jpg",
									"http://subject.zz91.com/1111/index/");
					break;
				}

				// 通用介绍 没有正确匹配信息则显示此信息
				if ("notice".equals(fromUserContent)) {
					content = "您好！欢迎您关注ZZ91再生网/服务号，请回复“1”了解资讯报价，回复“2”了解如何查看ZZ91产品供求信息，回复“3”了解ZZ91现有会员服务。";
				}

				if (StringUtils.isNotEmpty(content)) {
					out.printf(RESPONSE_TXT, fromUserName, toUserName, System
							.currentTimeMillis(), "text", content);
					break;
				}

				content = "您好！欢迎您关注ZZ91再生网/服务号，请回复“1”了解资讯报价，回复“2”了解如何查看ZZ91产品供求信息，回复“3”了解ZZ91现有会员服务。";

				// 求购 | 供应
				if (fromUserContent.indexOf("求购") != -1
						|| fromUserContent.indexOf("供应") != -1) {
					List<ProductsDto> list = wxReplyService
							.replyProducts(fromUserContent);
					if (list == null || list.size() < 1) {
						break;
					}
					String pType = "";
					String pName = "";
					if (ProductsService.PRODUCTS_TYPE_OFFER.equals(list.get(0)
							.getProducts().getProductsTypeCode())) {
						pType = "1";
						fromUserContent = fromUserContent.replace("求购", "");
						pName = "供应";
					} else if (ProductsService.PRODUCTS_TYPE_BUY.equals(list
							.get(0).getProducts().getProductsTypeCode())) {
						pType = "2";
						fromUserContent = fromUserContent.replace("供应", "");
						pName = "求购";
					}
					String body = "";
					for (int i = 0; i <= list.size(); i++) {
						body += RESPONSE_BODY_NEWS;
					}
					if (list.size() == 1) {
						out.printf(RESPONSE_HEAD_NEWS + body
								+ RESPONSE_FOOT_NEWS, fromUserName, toUserName,
								System.currentTimeMillis(), "news", "2", pName
										+ list.get(0).getProducts().getTitle(),
								list.get(0).getProducts().getDetails(),
								PIC_PATH + list.get(0).getCoverPicUrl(),
								MOBILE_PRODUCT_PATH
										+ list.get(0).getProducts().getId(),
								"更多", "更多", MORE_PIC_PATH, MORE_PRODUCTS_LINK
										+ "keywords=" + fromUserContent
										+ "&ptype=" + pType);
					}
					if (list.size() == 2) {
						out.printf(RESPONSE_HEAD_NEWS + body
								+ RESPONSE_FOOT_NEWS, fromUserName, toUserName,
								System.currentTimeMillis(), "news", "3", pName
										+ list.get(0).getProducts().getTitle(),
								list.get(0).getProducts().getDetails(),
								PIC_PATH + list.get(0).getCoverPicUrl(),
								MOBILE_PRODUCT_PATH
										+ list.get(0).getProducts().getId(),
								pName + list.get(1).getProducts().getTitle(),
								list.get(1).getProducts().getDetails(),
								PIC_PATH + list.get(1).getCoverPicUrl(),
								MOBILE_PRODUCT_PATH
										+ list.get(1).getProducts().getId(),
								"更多", "更多", MORE_PIC_PATH, MORE_PRODUCTS_LINK
										+ "keywords=" + fromUserContent
										+ "&ptype=" + pType);
					}
					if (list.size() == 3) {
						out.printf(RESPONSE_HEAD_NEWS + body
								+ RESPONSE_FOOT_NEWS, fromUserName, toUserName,
								System.currentTimeMillis(), "news", "4", pName
										+ list.get(0).getProducts().getTitle(),
								list.get(0).getProducts().getDetails(),
								PIC_PATH + list.get(0).getCoverPicUrl(),
								MOBILE_PRODUCT_PATH
										+ list.get(0).getProducts().getId(),
								pName + list.get(1).getProducts().getTitle(),
								list.get(1).getProducts().getDetails(),
								PIC_PATH + list.get(1).getCoverPicUrl(),
								MOBILE_PRODUCT_PATH
										+ list.get(1).getProducts().getId(),
								pName + list.get(2).getProducts().getTitle(),
								list.get(2).getProducts().getDetails(),
								PIC_PATH + list.get(2).getCoverPicUrl(),
								MOBILE_PRODUCT_PATH
										+ list.get(2).getProducts().getId(),
								"更多", "更多", MORE_PIC_PATH, MORE_PRODUCTS_LINK
										+ "keywords=" + fromUserContent
										+ "&ptype=" + pType);
					}
					if (list.size() == 4) {
						out.printf(RESPONSE_HEAD_NEWS + body
								+ RESPONSE_FOOT_NEWS, fromUserName, toUserName,
								System.currentTimeMillis(), "news", "5", pName
										+ list.get(0).getProducts().getTitle(),
								list.get(0).getProducts().getDetails(),
								PIC_PATH + list.get(0).getCoverPicUrl(),
								MOBILE_PRODUCT_PATH
										+ list.get(0).getProducts().getId(),
								pName + list.get(1).getProducts().getTitle(),
								list.get(1).getProducts().getDetails(),
								PIC_PATH + list.get(1).getCoverPicUrl(),
								MOBILE_PRODUCT_PATH
										+ list.get(1).getProducts().getId(),
								pName + list.get(2).getProducts().getTitle(),
								list.get(2).getProducts().getDetails(),
								PIC_PATH + list.get(2).getCoverPicUrl(),
								MOBILE_PRODUCT_PATH
										+ list.get(2).getProducts().getId(),
								pName + list.get(3).getProducts().getTitle(),
								list.get(3).getProducts().getDetails(),
								PIC_PATH + list.get(3).getCoverPicUrl(),
								MOBILE_PRODUCT_PATH
										+ list.get(3).getProducts().getId(),
								"更多", "更多", MORE_PIC_PATH, MORE_PRODUCTS_LINK
										+ "keywords=" + fromUserContent
										+ "&ptype=" + pType);
					}
					if (list.size() == 5) {
						out.printf(RESPONSE_HEAD_NEWS + body
								+ RESPONSE_FOOT_NEWS, fromUserName, toUserName,
								System.currentTimeMillis(), "news", "6", pName
										+ list.get(0).getProducts().getTitle(),
								list.get(0).getProducts().getDetails(),
								PIC_PATH + list.get(0).getCoverPicUrl(),
								MOBILE_PRODUCT_PATH
										+ list.get(0).getProducts().getId(),
								pName + list.get(1).getProducts().getTitle(),
								list.get(1).getProducts().getDetails(),
								PIC_PATH + list.get(1).getCoverPicUrl(),
								MOBILE_PRODUCT_PATH
										+ list.get(1).getProducts().getId(),
								pName + list.get(2).getProducts().getTitle(),
								list.get(2).getProducts().getDetails(),
								PIC_PATH + list.get(2).getCoverPicUrl(),
								MOBILE_PRODUCT_PATH
										+ list.get(2).getProducts().getId(),
								pName + list.get(3).getProducts().getTitle(),
								list.get(3).getProducts().getDetails(),
								PIC_PATH + list.get(3).getCoverPicUrl(),
								MOBILE_PRODUCT_PATH
										+ list.get(3).getProducts().getId(),
								pName + list.get(4).getProducts().getTitle(),
								list.get(4).getProducts().getDetails(),
								PIC_PATH + list.get(4).getCoverPicUrl(),
								MOBILE_PRODUCT_PATH
										+ list.get(4).getProducts().getId(),
								"更多", "更多", MORE_PIC_PATH, MORE_PRODUCTS_LINK
										+ "keywords=" + fromUserContent
										+ "&ptype=" + pType);
					}
					break;
				}

				// 报价资讯
				if (fromUserContent.indexOf("报价") != -1) {
					String keywords = fromUserContent.replace("报价", "");
					String pic = "";
					pic = returnPicName(keywords);

					List<PriceDO> list = wxReplyService.replyPrice(keywords);
					if (list != null && list.size() > 0) {
						if (StringUtils.isEmpty(pic)) {
							List<PriceCategoryDO> categorylist = priceCategoryService
									.getAllParentPriceCategoryByParentId(list
											.get(0).getTypeId());
							for (PriceCategoryDO obj : categorylist) {
								pic = returnPicName(obj.getName());
								if (StringUtils.isNotEmpty(pic)) {
									break;
								}
							}
						}
						String returnPic = "";
						if (StringUtils.isEmpty(pic)) {
							returnPic = NO_PIC_PATH;
						} else {
							returnPic = MOBILE_PRICE_PIC_PATH + pic;
						}
						out.printf(RESPONSE_HEAD_NEWS + RESPONSE_BODY_NEWS
								+ RESPONSE_FOOT_NEWS, fromUserName, toUserName,
								System.currentTimeMillis(), "news", "1", list
										.get(0).getTitle(), list.get(0)
										.getContent(), returnPic,
								MOBILE_PRICE_PATH + list.get(0).getId());
					}
				}
				out.printf(RESPONSE_TXT, fromUserName, toUserName, System
						.currentTimeMillis(), "text", content);
			} while (false);
		} catch (DocumentException e) {
		}
		in.close();
		in = null;
		out.close();
		out = null;
	}

	@RequestMapping
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		NameValuePair[] data = {
				new NameValuePair(
						"access_token",
						"n-nT70OmFruTzbI_OyBPMDsRjKasaeXvIseqTQbGwEyDCA1iY1jO1nYJGpTSL9iIjTk9Y0vamygw_OgVh44qHmBGVlPLdaz0-iiIh-95NefK_xaqKo24V6l9fZk4hdukAJFu0rfcjhAD2vj88xDjKQ"),
				new NameValuePair(
						"data",
						"{\"button\":[{\"type\":\"click\",\"name\":\"项目管理\",\"key\":\"20_PROMANAGE\"},{\"type\":\"click\",\"name\":\"机构运作\",\"key\":\"30_ORGANIZATION\"},{\"name\":\"日常工作\",\"sub_button\":[{\"type\":\"click\",\"name\":\"待办工单\",\"key\":\"01_WAITING\"},{\"type\":\"click\",\"name\":\"已办工单\",\"key\":\"02_FINISH\"},{\"type\":\"click\",\"name\":\"我的工单\",\"key\":\"03_MYJOB\"},{\"type\":\"click\",\"name\":\"公告消息箱\",\"key\":\"04_MESSAGEBOX\"},{\"type\":\"click\",\"name\":\"签到\",\"key\":\"05_SIGN\"}]}]}") };
		HttpUtils.getInstance().httpPost(
				"http://api.weixin.qq.com/cgi-bin/menu/create", data,
				HttpUtils.CHARSET_UTF8);
	}

	@SuppressWarnings("unused")
	private static boolean checkSignature(String signature, String timestamp,
			String nonce) {
		String[] arr = new String[] { TOKEN, timestamp, nonce };
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		MessageDigest md = null;
		String tmpStr = null;

		try {
			md = MessageDigest.getInstance("SHA-1");
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		content = null;
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
	}

	// 将字节转换为十六进制字符串
	private static String byteToHexStr(byte ib) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		char[] ob = new char[2];
		ob[0] = Digit[(ib >>> 4) & 0X0F];
		ob[1] = Digit[ib & 0X0F];

		String s = new String(ob);
		return s;
	}

	// 将字节数组转换为十六进制字符串
	private static String byteToStr(byte[] bytearray) {
		String strDigest = "";
		for (int i = 0; i < bytearray.length; i++) {
			strDigest += byteToHexStr(bytearray[i]);
		}
		return strDigest;
	}

	private String returnPicName(String keywords) {
		String pic = "";
		if (keywords.indexOf("钢") != -1) {
			pic = "gang.jpg";
		}
		if (keywords.indexOf("金属") != -1) {
			pic = "jinshu.jpg";
		}
		if (keywords.indexOf("pta") != -1) {
			pic = "pta.jpg";
		}
		if (keywords.indexOf("pvc") != -1) {
			pic = "pvc.jpg";
		}
		if (keywords.indexOf("塑料") != -1) {
			pic = "suliao.jpg";
		}
		if (keywords.indexOf("铜") != -1) {
			pic = "tong.jpg";
		}
		if (keywords.indexOf("橡胶原") != -1) {
			pic = "xiangjiao.jpg";
		}

		if (keywords.indexOf("原油") != -1) {
			pic = "yuanyou.jpg";
		}
		if (keywords.indexOf("纸") != -1) {
			pic = "zhi.jpg";
		}
		return pic;
	}

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		// 获取微信 access_token
		// https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
		String url = "http://api.weixin.qq.com/cgi-bin/menu/create?access_token="
				+ "Zqkr78ND5b-5agInNkXZDTBra25jHq3J5lagwfM3UeX4JAAGgQ800seaF4rjtoKIjmE9ISlW-An7XnpmJucNu7ewv0RgnnSQosnrW6ZkThMxVw-IbiceR5V5sHfvm3AAmyAIW0VZvVj8tW-m5XqE-w";
		/**
		 * 设置菜单 在为什么用\"你懂得,这是java代码
		 */
		String responeJsonStr = "{" + "\"button\":[" + "{\"name\":\"行情报价\","
				+ "\"type\":\"click\"," + "\"key\":\"1\"" + "},"
				+ "{\"name\":\"商机查询\"," + "\"type\":\"click\","
				+ "\"key\":\"2\"" + "}," +

//				"{\"name\":\"废料双十一\"," + "\"type\":\"click\","
//				+ "\"key\":\"View_Latest_Events\"" + "}" +
				 "{\"name\":\"ZZ91服务\","+
				 "\"sub_button\":["+
				 "{"+
				 "\"type\":\"click\","+
				 "\"name\":\"再生通\","+
				 "\"key\":\"View_zst\""+
				 "},"+
				 "{"+
				 "\"type\":\"click\","+
				 "\"name\":\"品牌通\","+
				 "\"key\":\"View_ppt\""+
				 "},"+
				 "{"+
				 "\"type\":\"click\","+
				 "\"name\":\"百度优化\","+
				 "\"key\":\"View_baidu\""+
				 "},"+
				 "{"+
				 "\"type\":\"click\","+
				 "\"name\":\"品牌推广\","+
				 "\"key\":\"View_ads\""+
				 "}]"+
				 "}"+
				"]" + "}";
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(url);
		post.setRequestBody(responeJsonStr);
		post.getParams().setContentCharset("utf-8");
		// 发送http请求
		String respStr = "";
		try {
			client.executeMethod(post);
			respStr = post.getResponseBodyAsString();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(responeJsonStr);
		System.out.println(respStr);
	}
}
