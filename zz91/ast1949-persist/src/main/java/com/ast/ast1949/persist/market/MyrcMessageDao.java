package com.ast.ast1949.persist.market;

import com.ast.ast1949.domain.MyrcMessage;

public interface MyrcMessageDao {
	public Integer insertMyrcMessage(MyrcMessage myrcMessage);
	public Integer queryMyrcMessageByCIdAndType(Integer companyId,String type,String content);

}
