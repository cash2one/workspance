/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-6-25 上午11:09:23
 */
package com.zz91.ep.service.trade;

import java.util.List;

import com.zz91.ep.domain.trade.SubnetLink;

public interface SubnetLinkService {
	
	/**
	 * 
	 * 函数名称：querySubnetLink
	 * 功能描述：[查询所有子类友情链接]
	 * 输入参数：@param parentId 子类Id1
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 齐振杰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<SubnetLink> querySubnetLink(Integer parentId);

}
