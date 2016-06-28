/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-6-26 下午07:27:45
 */
package com.zz91.ep.service.comp;

import java.util.List;

import com.zz91.ep.domain.comp.CompProfile;

public interface SubnetCompRecommendService {
	
	/**
	 * 
	 * 函数名称：queryCompBySubRec
	 * 功能描述：[查询子网推荐公司]
	 * 输入参数：@param subnetCategory 子网类别
	 * 　　　　　.......
	 * 　　　　　@param size 显示数量
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 齐振杰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<CompProfile> queryCompBySubRec(String subnetCategory,Integer size);

}
