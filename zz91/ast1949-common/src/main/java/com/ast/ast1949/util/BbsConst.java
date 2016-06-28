/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-5-25 by liulei
 */
package com.ast.ast1949.util;

/**
 * @author liulei
 *
 */
public class BbsConst {

	//本周牛贴
	public final static Integer BBS_STASTISTIC_TYPE1=1;
	//本周牛人
	public final static Integer BBS_STASTISTIC_TYPE2=2;
	//最牛网商
	public final static Integer BBS_STASTISTIC_TYPE3=3;
	//昨日头条
	public final static String DEFAULT_POST_TYPE2 = "2";
	//表示头条
	public final static String DEFAULT_POST_TYPE3 = "3";
	
	//表示头条默认大小为2
	public final static Integer DEFAULT_POST_TYPE_SIZE3 = 2;
	//表示最新动态
	public final static String DEFAULT_POST_TYPE4 = "4";
	//表示头条默认大小为8
	public final static Integer DEFAULT_POST_TYPE_SIZE4 = 8;
	//表示热门话题
	public final static String DEFAULT_POST_TYPE5 = "5";
	//表示头条默认大小为30
	public final static Integer DEFAULT_POST_TYPE_SIZE5 = 33;
	//表示排序主贴修改时间
	public final static String DEFAULT_POST_MODIFIED_SORT = "a.gmt_modified";
	//表示排序主贴发布时间
	public final static String DEFAULT_POST_TIME_SORT = "a.post_time";
	//表示排序顺序为降序
	public final static String DEFAULT_POST_DIR = "desc";
	//表示发布一条
	public final static Integer DEFAULT_POST_ONE = 1;
	//统计排序(被访问总次数)
	public final static String DEFAULT_POST_SUM_VISITED_COUNT_SORT = "visited_count";
	//统计排序(总积分)
	public final static String DEFAULT_POST_SUM_INTEGRAL_SORT = "integral";
	//统计默认大小为6
	public final static Integer DEFAULT_SUM_SIZE = 6;
	//统计默认大小为7
	public final static Integer DEFAULT_SUM_SIZE7 = 7;
	//表示我发表和回复的帖子默认大小为8
	public final static Integer DEFAULT_POST_MY_SIZE = 10;
	
	public final static Integer DEFAULT_BBS_CATEGORY_PIZE = 29;
	//表示排序主贴发布时间
	public final static String DEFAULT_MESSAGE_TIME_SORT = "massage_time";
	//表示企业签名修改时间
	public final static String DEFAULT_MODIFIED_TIME_SORT = "gmt_modified";
	//个人头像路径
	public final static String UPLOAD_MODEL_TOUXIANG="bbsTouxiang";
	//个人头像路径
	public final static String UPLOAD_MODEL_POSTED="bbsPosted";
	//默认查询起始位置
	public final static Integer DEFAULT_START_INDEX=0;
	//默认查询起始位置
	public final static Integer DEFAULT_SIZE=6;
	//最新动态默认大小为4
	public final static Integer DEFAULT_NEWS_SIZE = 4;
	//等于1表示24小时热帖
	public final static Integer DEFAULT_ONE_DAY_BEFORE = 1;
	//等于7表示一周内的热帖
	public final static Integer DEFAULT_ONE_WEEK_BEFORE = 7;
	//一周内的热帖默认大小为5
	public final static Integer DEFAULT_ONE_WEEK_SIZE=5;
	//等于week表示该标签为互助周报
	public final static String DEFAULT_SIGN_TYPE1="week";
	//等于month表示该标签为互助周月刊
	public final static String DEFAULT_SIGN_TYPE2="month";
	//等于1表示该标签为管理员标签
	public final static String DEFAULT_TAGS_IS_ADMIN="1";
	//等于0表示该标签不为管理员标签
	public final static String DEFAULT_TAGS_IS_NOT_ADMIN="0";
	//表示标签排序
	public final static String DEFAULT_TAGS_SORT="sort";
	//按照刷新时间排序
	public final static String REPLY_TIME_SORT = "a.reply_time";
	//按照审核时间排序
	public final static String CHECK_TIME_SORT = "a.post_time";
	//按照被访问次数排序
	public final static String VISITED_COUNT_SORT = "a.visited_count";
	//表示默认大小为11
	public final static Integer DEFAULT_SIZE11 = 11;
	//表示默认大小为10
	public final static Integer DEFAULT_SIZE10 = 10;


	/**
	 * 保存登录用户的权限信息
	 */
	public final static String SESSION_AUTH			= "zz91_UserOperation";
	public final static String SESSION_USER			= "zz91_LoginUserName";

	//默认的时间格式
	public final static String DEAFULT_DATE_FORMAT	= "yyyy-MM-dd hh:mm:ss";

	public final static String SESSION_CODE			= "zz91_ValidationCode";

	public final static int SESSION_TIME_OUT		= 1200;

	public final static String MYSESSIONID			= "zz91_front_sessionid";
//	public final static String MYDOMAIN				= null;

	public final static String MEMBERSHIP_CODE_ROOT = "1005";

}
