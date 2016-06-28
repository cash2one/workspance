package com.ast.ast1949.service.spot;

import java.util.List;

import com.ast.ast1949.domain.spot.SpotTrust;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.spot.SpotTrustDto;

/**
 *	author:kongsj
 *	date:2013-5-20
 */
public interface SpotTrustService {
	
	final static String CHECK_WAIT = "0";
	final static String CHECK_PASS = "1";
	final static String CHECK_NOPASS = "2";
	
	final static String DELETE_YES = "1";
	final static String DELETE_NO = "0";
	
	public SpotTrust queryById(Integer id);

	public Integer insert(SpotTrust spotTrust);

	public Integer update(SpotTrust spotTrust);

	public List<SpotTrust> queryListForFront(Integer start,Integer size);
	
	public PageDto<SpotTrustDto> pageList(SpotTrustDto spotTrustDto,PageDto<SpotTrustDto> page);

	// 更新删除状态
	public Integer updateDelete(String isDelete, Integer id);

	// 更新审核状态
	public Integer updateChecked(String isChecked, Integer id);
	
}
