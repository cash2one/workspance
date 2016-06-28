package com.ast.ast1949.service.other;

import com.ast.ast1949.domain.other.SubjectBaoming;

public interface SubjectBaomingService {
	
	/**
	 * 添加一条最新报名记录
	 * @param title 固定为：申请再生通
	 * @param content 格式为 ：
				  			*姓名:朱俊伟
							*先生
							*手机号码:13111111111
							国家:
							区号:
							电话号码:
	 * @return
	 */
	public Integer createNewBaoming(String title,String content);
	
}
