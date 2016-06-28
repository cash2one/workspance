/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-6-25 上午11:56:25
 */
package com.zz91.ep.dao.trade;

import java.util.List;

import com.zz91.ep.domain.trade.SubnetLink;

public interface SubnetLinkDao {

	List<SubnetLink> querySubnetLinkByparentId(Integer parentId);

}
