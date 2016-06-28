package com.ast.ast1949.persist.oauth;

import java.util.List;

import com.ast.ast1949.domain.oauth.OauthAccess;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyDto;

public interface OauthAccessDao {
	public OauthAccess queryAccessByOpenIdAndType(String openId,String openType);
	
	public OauthAccess queryAccessByAccountAndType(String account,String openType);

	public Integer insert(OauthAccess oauthAccess);

	public Integer updateByOpenId(String openId, String targetAccount);

	public Integer countAccessByOpenId(String openId);

	public Integer queryByWXCode(String code, String gmtLimit);
	
	public Integer updateWXTargetAccountById(Integer id , String targetAccount);
	
	//统计绑定了微信的公司
	public List<OauthAccess> queryTargetAccount(PageDto<CompanyDto> page);
	//统计绑定了微信的公司数量
	public Integer queryTargetAccountCount();
	//解绑微信
	public void deleteByTargetAccount(String targetAccount);
}
