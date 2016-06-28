/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-6-26 下午07:27:45
 */
package com.zz91.ep.admin.service.comp;


public interface SubnetCompRecommendService {
	
	/**
	 * 创建一条子网推荐信息
	 * @param recommend
	 * @return
	 */
	public Integer createdSubRecComp(Integer cid,String type);
	
	/**
	 * 删除子网推荐信息
	 * @param cid
	 * @return
	 */
	public Integer deleteSubRecComp(Integer cid);
}
