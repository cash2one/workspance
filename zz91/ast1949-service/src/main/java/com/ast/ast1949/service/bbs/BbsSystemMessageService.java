/**
 * @author kongsj
 * @date 2014年12月1日
 * 
 */
package com.ast.ast1949.service.bbs;

import com.ast.ast1949.domain.bbs.BbsSystemMessage;
import com.ast.ast1949.dto.PageDto;

public interface BbsSystemMessageService {

	public Integer read(Integer id);

	public PageDto<BbsSystemMessage> pageList(BbsSystemMessage bbsSystemMessage,PageDto<BbsSystemMessage> page);
	
	public Integer getNoReadCount(Integer companyId);

}