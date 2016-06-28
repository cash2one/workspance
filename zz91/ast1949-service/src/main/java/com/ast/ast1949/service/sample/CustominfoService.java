package com.ast.ast1949.service.sample;

import java.util.List;

import com.ast.ast1949.domain.sample.Custominfo;

public interface CustominfoService {
	Integer insert(Custominfo custominfo);

	int updateByPrimaryKey(Custominfo record);

	int updateByPrimaryKeySelective(Custominfo record);

	Custominfo selectByPrimaryKey(Integer id);
	
	Custominfo selectByOrderSeq(String  orderSeq);
	
	
	/**
	 * 
	 * @param orderSeq  订单序列
	 * @param flag  "S" 卖方; "B" 买方;  
	 * @param type  0:备忘  1：留言
	 * @return
	 */
	Custominfo selectByOrderSeqAndFlagAndType(String  orderSeq,String flag,String type);
	
	public List<Custominfo>selectByOrderSeqAndType(String  orderSeq,String type);

	int deleteByPrimaryKey(Integer id);
}
