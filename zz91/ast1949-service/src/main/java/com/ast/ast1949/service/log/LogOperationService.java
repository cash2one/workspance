package com.ast.ast1949.service.log;

import com.ast.ast1949.domain.log.LogOperation;
import com.ast.ast1949.dto.PageDto;

public interface LogOperationService {
	final static String BLACK_OPERATION = "black_operation"; // 拉黑
	final static String UN_BLACK_OPERATION = "un_black_operation"; // 取消拉黑

	public Integer addOneOperation(Integer targetId, String operator,
			String operation, String remark);

	public PageDto<LogOperation> pageLogOperationByTargetIdAndOperation(
			Integer id, String operation, PageDto<LogOperation> page);

	public String queryRemarskByCompanyId(Integer companyId);
	/**
	 * 拉黑的理由
	 * @param cid
	 * @return
	 */
	public String queryRemarkByCId(Integer cid);

}
