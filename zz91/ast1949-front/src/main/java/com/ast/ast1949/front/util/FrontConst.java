/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-3-23
 */
package com.ast.ast1949.front.util;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
public class FrontConst {
	/**处理结果：成功**/
	public final static boolean SUCCESS=true;
	/**处理结果：失败**/
	public final static boolean FAILDED=false;
	public final static String MYSESSIONID			= "zz91_front_sessionid";
	/**
	 * 保存登录用户的权限信息
	 */
	public final static String SESSION_AUTH			= "zz91_UserOperation";
	public final static String SESSION_USER			= "zz91_LoginUserName";

	public final static String SESSION_CODE			= "zz91_ValidationCode";

	//默认的时间格式
	public final static String DEAFULT_DATE_FORMAT	= "yyyy-MM-dd hh:mm:ss";

	public final static int SESSION_TIME_OUT		= 1200;

	//生意管家里的每个页面的标题变量名，用于vm里的显示
	public final static String MYRC_SUBTITLE        = "subTitle";

	//上传相关
    public final static String UPLOAD_MODEL_MYRC	= "myrc";
    public final static String UPLOAD_MODEL_PRODUCTS= "products";
    public final static String UPLOAD_MODEL_SUBJECT = "subject";
    
//	public final static String PAGE_CUSTOMER_SERVICE_PHONE="customerServicePhone";

	//网站栏目
//	public final static String MYWEBSITE_NAV_COLUMN_CONFIG="[{'id':'js','t':'公司介绍','d':true},{'id':'gy','t':'最新供求','d':true},{'id':'dt','t':'公司动态','d':true},{'id':'ly','t':'在线留言','d':true},{'id':'lx','t':'联系方式','d':true},{'id':'c1','t':'自定义一','d':false},{'id':'c2','t':'自定义二','d':false}]";
	//当前栏目
//	public final static String MYCOLUMN				= "sy";
	//整体样式
//	public final static String STYLE_CONTENT		= "[{'cn':'bodyCont','sl':{'border-color':'#94c5fc','background':'#955d36','border-style':'none','zoom':'1'}},{'cn':'bodyContTitle','sl':{'background':'url(http://www.zz91.com/myrc/esite/images/topic/17.gif) repeat'}},{'cn':'imgBorder','sl':{'border-color':'#ffc784','border-style':'dotted'}},{'cn':'mainTextColor','sl':{'color':'#ffffff'}},{'cn':'titleLinkColor','sl':{'color':'#ffc784'}},{'cn':'topicLink','sl':{'color':'#f9f9c3'}},{'cn':'headerMenuBorder','sl':{'border-color':'#94c5fc','background':'#955d36','border-style':'none'}},{'cn':'headerMenuList','sl':{'color':'#f9f9c3'}},{'cn':'headerMenuLiCheck','sl':{'border-color':'#572000','background':'url(http://www.zz91.com/myrc/esite/images/topic/17.gif) repeat-x','color':'#ffff00'}},{'cn':'headerMenuBottom','sl':{'border-bottom-color':'#572000'}},{'cn':'topbaner','sl':{'background':'#ffffff'}},{'cn':'headTopic','sl':{'height':'200px','background':'url(http://www.zz91.com/myrc/esite/images/topic/zt1.jpg) repeat'}},{'cn':'inBg','sl':{'background':'none'}},{'cn':'bodyBg','sl':{'background':'url(http://www.zz91.com/myrc/esite/images/topic/bg15.jpg) no-repeat center fixed #94371e'}},{'cn':'description','sl':{'padding-left':'50px','padding-top':'40px'}},{'cn':'chinaname','sl':{'font-family':'楷体_GB2312','font-size':'26px','font-weight':'bold','color':'#000000','font-style':'italic'}},{'cn':'enname','sl':{'font-family':'Verdana','font-size':'20px','font-weight':'bold','color':'#000000','font-style':'italic'}},{'cn':'topDesc','sl':{'font-family':'楷体_GB2312','font-size':'26px','font-weight':'bold','color':'#000000','font-style':'italic'}},{'cn':'bottomDesc','sl':{'font-family':'Verdana','font-size':'20px','font-weight':'bold','color':'#000000','font-style':'italic'}}]";
	
}
