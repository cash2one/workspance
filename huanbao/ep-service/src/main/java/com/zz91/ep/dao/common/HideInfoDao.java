package com.zz91.ep.dao.common;



import com.zz91.ep.domain.common.HideInfo;

/**
 * author:fangchao 
 * date: 2013-8-6
 */
public interface HideInfoDao {
	
	public  Integer insert(HideInfo hideInfo);
	
	public Integer update(HideInfo hideInfo);
	
	public  Integer delete(Integer targetId,String targetType);
	
	public Integer querycount(Integer targetId,String targetType);
	
	public HideInfo queryHideInfoByIdAndType(Integer targetId,String targetType);
} 
