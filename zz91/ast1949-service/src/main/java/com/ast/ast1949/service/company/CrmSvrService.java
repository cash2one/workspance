package com.ast.ast1949.service.company;

import java.util.List;

import com.ast.ast1949.domain.company.CrmSvr;



public interface CrmSvrService {
	
	public static final String PT_CODE="10051000"; // 普通会员
	public static final String ZST_CODE="10051001"; // 再生通
	public static final String PPT_CODE="10051002"; // 品牌通
	public static final String PPT_SILVER_CODE="100510021000";//银牌品牌通
	public static final String PPT_GOLD_CODE="100510021001";//金牌品牌通
	public static final String PPT_DIAMOND_CODE="100510021002";//钻石品牌通
	
	public static final String CRM_SP = "10001002"; // 百度优化 code
	
	/**
	 * 查找全部服务
	 *
	 *  
	 */
	public List<CrmSvr> querySvr();
	
	public List<CrmSvr> querySvrByGroup(String group);
	
	public String queryOpenApi(String code);
	
	public String queryCloseApi(String code);
	
	public String queryRenewalsApi(String code);
}
 
