/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-2-13
 */
package com.ast.ast1949.persist.company;

import java.util.List;

import com.ast.ast1949.domain.company.EsiteFriendLinkDo;


/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-2-13
 */
public interface EsiteFriendLinkDao {

	
	public List<EsiteFriendLinkDo> queryFriendLinkByCompany(Integer companyId, Integer limit);
	
	public Integer insertFriendLink(EsiteFriendLinkDo friendlink);
	
	public Integer updateFriendLinkByCompany(EsiteFriendLinkDo friendlink);
	
	public Integer deleteFriendLinkByCompany(Integer id, Integer companyId);
	
	public Integer countFriendLinkNumByCompany(Integer companyId);
	
	public EsiteFriendLinkDo queryOneFriendLinkById(Integer id);
	
}
