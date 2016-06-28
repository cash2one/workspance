package com.ast.ast1949.service.company.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.ConfigNotifyDO;
import com.ast.ast1949.persist.company.ConfigNotifyDAO;
import com.ast.ast1949.service.company.ConfigNotifyService;

@Component("configNotifyService")
public class ConfigNotifyServiceImpl implements ConfigNotifyService{
	
	@Resource
	private ConfigNotifyDAO configNotifyDAO;

	@Override
	public Integer addConfigNotify(Integer companyId, String notifyCode,
			Integer status) {
		return configNotifyDAO.insertConfigNotify(companyId, notifyCode, status);
	}

	@Override
	public Integer updateConfigNotifyForSend(Integer companyId,
			String notifyCode, Integer status) {
		return configNotifyDAO.updateConfigNotifyForSend(companyId, notifyCode, status);
	}

	@Override
	public List<ConfigNotifyDO> queryConfigNotify(Integer companyId,String notifyCode,Integer status) {
		return configNotifyDAO.selectConfigNotify(companyId, notifyCode, status);
	}

	@Override
	public Integer countConfigByCode(String notifyCode, Integer companyId) {
		return configNotifyDAO.countConfigByCode(notifyCode, companyId);
	}

	@Override
	public Integer deleteConfig(String notifyCode, Integer companyId,Integer status) {
		return configNotifyDAO.deleteConfigByCode(notifyCode, companyId,status);
	}
}
