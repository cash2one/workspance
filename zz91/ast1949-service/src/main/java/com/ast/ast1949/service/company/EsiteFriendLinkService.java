/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-2-18
 */
package com.ast.ast1949.service.company;

import java.util.List;

import com.ast.ast1949.domain.company.EsiteFriendLinkDo;

/**
 * @author mays (mays@zz91.com)
 * 
 *         created on 2011-2-18
 */
public interface EsiteFriendLinkService {

	public Integer insertFriendLink(EsiteFriendLinkDo friendLink);
	
	public Integer updateFriendLinkById(EsiteFriendLinkDo friendLink);
	
	public Integer deleteFriendLinkByIdAndCompany(Integer id, Integer companyId);
	
	public boolean isFriendLinkNumOverLimit(Integer companyId);
	
	public List<EsiteFriendLinkDo> queryFriendLinkByCompany(Integer companyId, Integer limit);
	
	public EsiteFriendLinkDo queryOneFriendLinkById(Integer id);
}
