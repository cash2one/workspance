/**
 * @author qizj
 * @email  qizhenj@gmail.com
 * @create_time  2012-6-26 下午07:44:14
 */
package com.zz91.ep.dao.comp;

import java.util.List;

import com.zz91.ep.domain.comp.CompProfile;

public interface SubnetCompRecommendDao {

	List<CompProfile> queryCompBySubRec(String subnetCategory, Integer size);

}
