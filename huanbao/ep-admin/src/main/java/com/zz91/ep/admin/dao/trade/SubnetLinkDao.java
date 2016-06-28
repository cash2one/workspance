/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-6-25 上午11:56:25
 */
package com.zz91.ep.admin.dao.trade;

import java.util.List;

import com.zz91.ep.domain.trade.SubnetLink;
import com.zz91.ep.dto.PageDto;

public interface SubnetLinkDao {

	/**
	 * @param subnetLink
	 * @return
	 */
	public Integer insertSubnetLink(SubnetLink subnetLink);

	/**
	 * @param id
	 * @return
	 */
	public Integer deleteLinkById(Integer id);

	/**
	 * @param parentId
	 * @param page
	 * @return
	 */
	public List<SubnetLink> queryChildLinkByParentId(Integer parentId,
			PageDto<SubnetLink> page);

	/**
	 * @param parentId
	 * @return
	 */
	public Integer queryChildCountByParentId(Integer parentId);

	/**
	 * @return
	 */
	public List<SubnetLink> queryParentLink();

	/**
	 * @param subnetLink
	 * @return
	 */
	public Integer updateSubnetLink(SubnetLink subnetLink);

}
