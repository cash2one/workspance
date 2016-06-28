/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-2-13
 */
package com.ast.ast1949.persist.company;

import com.ast.ast1949.domain.company.EsiteConfigDo;

/**
 * @author mays (mays@zz91.com)
 *
 * created on 2011-2-13
 */
public interface EsiteConfigDao {

	
	public EsiteConfigDo queryColumnConfigByCompanyId(Integer companyId, String columnCode);
	
	public Integer insertColumnConfig(EsiteConfigDo config);
	
	public Integer updateColumnConfigById(EsiteConfigDo config);
	
	public Integer deleteColumnConfigByCompany(Integer companyId, String columnCode);
	
	public Integer updateBannelPicByCompanyId(String pic,Integer cid);
	
	public String queryBannelPic(Integer cid);
	
	public Integer updateIsShowForHeadPic(Integer cid,Integer isShow);
	
}
