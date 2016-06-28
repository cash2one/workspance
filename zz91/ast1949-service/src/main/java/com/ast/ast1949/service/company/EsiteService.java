/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-4-23 by liulei.
 */
package com.ast.ast1949.service.company;

import java.util.Map;


/**
 * 这是一个用户商管处理的基础接口，提供商铺页面公共数据初始化方法
 * 
 * @author liulei
 * 
 */
public interface EsiteService {
	/**
	 * 
	 * @param companyId
	 *            :待初始化的公司ID，不能为空或null
	 * @param pageCode
	 *            :需要初始化的页面编号，比如首页(sy)，产品信息(info)等，默认为sy
	 */
	public void initBaseConfig(Integer companyId, String pageCode,
			Map<String, Object> out);

	/**
	 * 根据版块编号查找该版块对应的数据
	 * 
	 * @param columnId
	 * @return
	 */
	public Object queryDataByColumnId(String columnId, Integer companyId);

	public Integer initCompanyIdFromDomain(String domain);

	public void initServerAddress(String serverName, int port,
			String contextPath, Map<String, Object> out);
	
	/**
	 * 初始化所有二级域名并存放入缓存域名
	 */
	public void initDomain();
	
	public Integer getCompanyIdByDomain(String domain);
	
	/**
	 * 判断该高会是否存在死链xml
	 * @param cid
	 * @param fileName
	 * @return
	 */
	public boolean isExistXML(Integer cid, String fileName);
	
	/**
	 * 获得改高会下所有的SilianXML
	 * @param cid
	 * @param fileName
	 * @return
	 */
//	public SilianXML getAllSilianXML(Integer cid, String fileName);
}
