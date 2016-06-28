package com.ast.ast1949.persist.company;

import java.util.List;

import com.ast.ast1949.domain.company.ConfigNotifyDO;

public interface ConfigNotifyDAO {
	
	public Integer insertConfigNotify(Integer companyId,String notifyCode,Integer status);
	
	public Integer updateConfigNotifyForSend(Integer companyId,String notifyCode,Integer status);
	
	public List<ConfigNotifyDO> selectConfigNotify(Integer companyId,String notifyCode,Integer status);
	
	public Integer countConfigByCode(String notifyCode,Integer companyId);
	
	public Integer deleteConfigByCode(String notifyCode,Integer companyId,Integer status);
	
}
