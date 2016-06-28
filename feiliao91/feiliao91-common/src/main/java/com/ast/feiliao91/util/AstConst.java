package com.ast.feiliao91.util;

import java.text.ParseException;

import com.zz91.util.datetime.DateUtil;

public class AstConst {
	
	public final static String COOKIE_ACCOUNT="loginaccount";
	public final static String COOKIE_DOMAIN="zz91.com";
	public final static Integer COOKIE_AGE=null;
	public final static int PAGE_SIZE = 20;
	public final static int TAGS_PAGE_SIZE = 10;
	public final static int ADMIN_PAGE_SIZE=15;
	public final static String COMMON_MEMBERSHIP_CODE="10051000";
	public final static String ZST_MEMBERSHIP_CODE="10051000";//普通会员
	public final static String EXHIBIT_CODE="1038";
	public final static String EXHIBIT_NEWS="10371004"; //会展资讯
	public final static String EXHIBIT_NOTICE="10371002";//会展预告
	public final static String EXHIBIT_TOPICS="10371003";//会展专题
	public final static String EXHIBIT_TEARMWORK="10371001";//合作展会
	public final static String EXHIBIT_INDUSTRY="10371006";//行业展会
	public final static Integer EXHIBIT_PAGE_SIZE=7;
	public final static String NATION_CODE="1001";//国家

	// 标记是否删除
	public final static short IS_DELETE_TRUE = 1;
	public final static short IS_DELETE_FALSE = 0;

	// 标记是否删除
	public final static String HAVE_DELETE_TRUE = "1";
	public final static String HAVE_DELETE_FALSE = "0";

	// 标记禁用与否
	public final static int ISUSE_TRUE = 1;
	public final static int ISUSE_FALSE = 0;

	public final static String IS_SHOWED_TRUE = "1";
	public final static String IS_SHOWED_FALSE = "0";
	
	public final static String UPLOAD_FILETYPE_IMG = "img";
	public final static String UPLOAD_FILETYPE_DOC = "doc";

	// 否是审核
	public final static String IS_CHECKED_TRUE = "1";
	public final static String IS_CHECKED_FALSE = "0";

	public final static String DATE_FORMATE_DEFAULT = "yyyy-M-d";
	public final static String DATE_FORMATE_WITH_TIME = "yyyy-M-d HH:mm:ss";
	public final static String MAX_TIMT="9999-12-31 23:59:59";
	// 存放sessionId的cookie名称
	public final static String COOKIE_SESSIONID = "cookie_sessionid";
	// 存放 最近搜索关键词 的cookie名称
	public final static String COOKIE_MY_SEARCH = "cookie_my_search";

	/**
	 * 出错信息
	 */
	public final static String ERROR_TEXT = "errorText";
	/**
	 * 生成静态文件的扩展名
	 */
	public final static String HTML_SUFFIX = ".html";
	
	public final static String VALIDATE_CODE_KEY="vcodes";
	
	public static void main(String args[]) throws ParseException{
		long refreshTime = DateUtil.getDate("2011-01-12 23:59:59",
				"yyyy-MM-dd HH:mm:ss").getTime();
		long expiredTime = DateUtil.getDate("2999-12-31 23:59:59",
				"yyyy-MM-dd HH:mm:ss").getTime();
		long second = (expiredTime - refreshTime) / (1000); // 共计秒数
		// int MM = (int)ss/60; //共计分钟数
		int hours = (int) second / 3600; // 共计小时数
		int days = (int) hours / 24; // 共计天数
		System.out.println(second);
		System.out.println(hours);
		System.out.println(days);
	} 
}
