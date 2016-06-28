package com.ast.ast1949.service.company;

import java.util.List;

import com.ast.ast1949.domain.company.ConfigNotifyDO;

public interface ConfigNotifyService {
	
	/**
	 * 添加邮件发送确认
	 * @param companyId
	 * @param notifyCode选择服务的类别
	 * @param status是否确定要发送默认都是确定
	 * @return
	 */
	public Integer addConfigNotify(Integer companyId,String notifyCode,Integer status);
	
	/**
	 * 改变status状态，如果1表示不发送，0表示确认发送
	 * @param companyId
	 * @param notifyCode
	 * @param status
	 * @return
	 */
	public Integer updateConfigNotifyForSend(Integer companyId,
			String notifyCode, Integer status);
	
	public List<ConfigNotifyDO> queryConfigNotify(Integer companyId,String notifyCode,Integer status);
	
	public Integer countConfigByCode(String notifyCode,Integer companyId);
	
	public Integer deleteConfig(String notifyCode,Integer companyId,Integer status);
}
