/**
 * @author kongsj
 * @date 2014年12月1日
 * 
 */
package com.ast.ast1949.persist.bbs;

import java.util.List;

import com.ast.ast1949.domain.bbs.BbsSystemMessage;
import com.ast.ast1949.dto.PageDto;

public interface BbsSystemMessageDao {

	public Integer updateForRead(Integer id);

	public List<BbsSystemMessage> queryForList(BbsSystemMessage bbsSystemMessage, PageDto<BbsSystemMessage> page);

	public Integer queryForListCount(BbsSystemMessage bbsSystemMessage);

}
