package com.ast.ast1949.service.market;

import com.ast.ast1949.domain.MyrcMessage;

public interface MyrcMessageService {
	public Integer insertMyrcMessage(MyrcMessage myrcMessage);
	public Integer queryMyrcMessageByCIdAndType(Integer companyId,String type,String content);

}
