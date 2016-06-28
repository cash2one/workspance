package com.ast.ast1949.service.log.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.log.LogOperation;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.log.LogOperationDao;
import com.ast.ast1949.service.log.LogOperationService;
import com.zz91.util.Assert;

@Component("logOperationService")
public class LogOperationServiceImpl implements LogOperationService {

	@Resource
	private LogOperationDao logOperationDao;

	@Override
	public Integer addOneOperation(Integer targetId, String operator,
			String operation, String remark) {
		Assert.notNull(targetId, "targetId can't be null");
		Assert.notNull(operator, "operator can't be null");
		Assert.notNull(operation, "operation can't be null");
		Assert.notNull(remark, "remark can't be null");
		do {
			LogOperation logOperation = new LogOperation();
			logOperation.setOperator(operator);
			logOperation.setOperation(operation);
			logOperation.setTargetId(targetId);
			logOperation.setRemark(remark);
			Integer i = logOperationDao.insert(logOperation);
			if (i <= 0) {
				break;
			}
			return i;
		} while (false);
		return 0;
	}

	@Override
	public PageDto<LogOperation> pageLogOperationByTargetIdAndOperation(
			Integer id, String operation, PageDto<LogOperation> page) {
		Assert.notNull(id, "id can not be null");
		page.setTotalRecords(logOperationDao.queryCountByTargetIdAndOperation(
				id, operation));
		page.setRecords(logOperationDao.queryByTargetIdAndOperation(id,
				operation, page));
		return page;
	}

	@Override
	public String queryRemarskByCompanyId(Integer companyId) {
		String str = logOperationDao.queryRemarskByCompanyId(companyId);
		return str;
	}

	@Override
	public String queryRemarkByCId(Integer cid) {
		return logOperationDao.queryRemarkByCId(cid);
	}

}
