package com.ast.ast1949.service.market.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.MyrcMessage;
import com.ast.ast1949.persist.market.MyrcMessageDao;
import com.ast.ast1949.service.market.MyrcMessageService;
@Component("myrcMessageService")
public class MyrcMessageServiceImpl implements MyrcMessageService {
	@Resource
	private MyrcMessageDao myrcMessageDao;

	@Override
	public Integer insertMyrcMessage(MyrcMessage myrcMessage) {
		return myrcMessageDao.insertMyrcMessage(myrcMessage);
	}

	@Override
	public Integer queryMyrcMessageByCIdAndType(Integer companyId, String type, String content) {
		return myrcMessageDao.queryMyrcMessageByCIdAndType(companyId, type, content);
	}

}
