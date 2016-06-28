/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-1-11
 */
package com.ast.ast1949.service.analysis;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-1-11
 */
public interface AnalysisOptNumDailyService {

	/**
	 *  查看没有付费的用户联系信息
	 */
	final static String OPT_VIEW_CONTACT_PAID_FALSE = "200200020001";
	
	/**
	 * 联系信息被查看
	 */
	final static String OPT_BE_VIEWED_CONTACT = "200200020002";
	
	/**
	 * 增加用户操作
	 * <br />当当天同一个操作已经存在时，不需要再新增加操作，而是在存在的操作统计中增加操作数量
	 * @param optCode:操作编号,不能为null
	 * @param account:操作者的账号信息，不能为null
	 */
	void insertOptNum(String optCode, String account, Integer companyId);
	
	/**
	 * 查找用户当天某个操作的操作次数
	 * @param optCode:操作编号 不能为null
	 * @param account:用户的账户 不能为null
	 * @retun 
	 * 			正整数表示操作次数，如果没有操作返回0
	 */
	Integer queryOptNumByAccountToday(String optCode, String account);
	
}
