/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-6-25 上午11:09:23
 */
package com.zz91.ep.admin.service.trade;

import java.util.List;

import com.zz91.ep.domain.trade.SubnetLink;
import com.zz91.ep.dto.PageDto;

public interface SubnetLinkService {
	
	/**
	 * 添加子网链接
	 * @param subnetLink
	 * @return
	 */
	public Integer createSubnetLink(SubnetLink subnetLink);
	
	/**
	 * 更新链接
	 * @param subnetLink
	 * @return
	 */
	public Integer updateSubnetLink(SubnetLink subnetLink);
	
	/**
	 * 查询所有父类(parentId=0)
	 * @return
	 */
	public List<SubnetLink> queryParentLink();
	
	/**
	 * 子类所有友情链接
	 * @param parentId
	 * @param page
	 * @return
	 */
	public PageDto<SubnetLink> pageSubnetLink(Integer parentId,PageDto<SubnetLink> page);
	
	/**
	 * 删除友情链接
	 * @param id
	 * @return
	 */
	public Integer deleteLinkById(Integer id);

}
