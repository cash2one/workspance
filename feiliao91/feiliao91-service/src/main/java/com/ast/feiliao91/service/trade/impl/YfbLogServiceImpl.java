package com.ast.feiliao91.service.trade.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.feiliao91.domain.trade.YfbLog;
import com.ast.feiliao91.persist.trade.YfbLogDao;
import com.ast.feiliao91.service.trade.YfbLogService;

@Component("yfbLogService")
public class YfbLogServiceImpl implements YfbLogService{
	@Resource
	private YfbLogDao yfbLogDao;
	
	@Override
	public Integer insert(YfbLog yfbLog) {
		return yfbLogDao.insert(yfbLog);
	}

	@Override
	public List<YfbLog> queryByCompanyId(Integer companyId) {
		return yfbLogDao.queryByCompanyId(companyId);
	}

	@Override
	public Integer update(String payOrder) {
		return yfbLogDao.update(payOrder);
	}
}
