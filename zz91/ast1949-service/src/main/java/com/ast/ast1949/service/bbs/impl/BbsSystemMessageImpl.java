/**
 * @author kongsj
 * @date 2014年12月1日
 * 
 */
package com.ast.ast1949.service.bbs.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.bbs.BbsSystemMessage;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.bbs.BbsSystemMessageDao;
import com.ast.ast1949.service.bbs.BbsSystemMessageService;

@Component("bbsSystemMessageService")
public class BbsSystemMessageImpl implements BbsSystemMessageService {

	@Resource
	private BbsSystemMessageDao bbsSystemMessageDao;

	@Override
	public Integer read(Integer id) {
		if (id == null) {
			return 0;
		}
		return bbsSystemMessageDao.updateForRead(id);
	}

	@Override
	public PageDto<BbsSystemMessage> pageList(
			BbsSystemMessage bbsSystemMessage, PageDto<BbsSystemMessage> page) {
		page.setSort("id");
		page.setDir("desc");
		page.setRecords(bbsSystemMessageDao.queryForList(bbsSystemMessage, page));
		page.setTotalRecords(bbsSystemMessageDao.queryForListCount(bbsSystemMessage));
		return page;
	}

	@Override
	public Integer getNoReadCount(Integer companyId) {
		if (companyId==null) {
			return 0;
		}
		BbsSystemMessage bbsSystemMessage = new BbsSystemMessage();
		bbsSystemMessage.setCompanyId(companyId);
		bbsSystemMessage.setIsRead(0);
		return bbsSystemMessageDao.queryForListCount(bbsSystemMessage);
	}

}
