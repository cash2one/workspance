package com.ast.ast1949.persist.log;

import java.util.List;

import com.ast.ast1949.domain.log.LogOperation;
import com.ast.ast1949.dto.PageDto;

public interface LogOperationDao {

	public Integer insert(LogOperation logOperation);

	public List<LogOperation> queryByTargetIdAndOperation(Integer id,
			String operation, PageDto<LogOperation> page);

	public Integer queryCountByTargetIdAndOperation(Integer id, String operation);

	public String queryRemarskByCompanyId(Integer companyId);
	/**
	 * 拉黑的理由
	 * @param cid
	 * @return
	 */
	public String queryRemarkByCId(Integer cid);
}