package com.ast.ast1949.service.company;

import com.ast.ast1949.domain.company.CompanyAttest;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyAttestDto;

/**
 * 公司工商或个人注册信息
 * 
 * @author zhouzk
 * 
 */
public interface CompanyAttestService {
	
	final static String STATE_PASS = "1";
    
    public PageDto<CompanyAttestDto> pageCompanyAttest(CompanyAttest companyAttest, String compName,PageDto<CompanyAttestDto> page);
    
    public CompanyAttest queryOneInfo(CompanyAttest companyAttest);
    
    public Integer addOneInfo (CompanyAttest companyAttest);
    
    public CompanyAttest queryAttestByCid (Integer companyId);
    
    public CompanyAttest queryAttestById (Integer id);
    
    public Integer updateInfoByFront(CompanyAttest companyAttest);
    
    public Integer updateInfoByAdmin(CompanyAttest companyAttest);
    
    public Integer updateCheckStatus (Integer id, String checkStatus, String checkPerson);
    
    public Integer deleteCompanyAttest (Integer id);
    
    public String buildShow(CompanyAttest companyAttest);
    
    public CompanyAttest splitShow(String showStatus);
    
    public String replaceStar(String str);
    
    /**
     * 验证是否属于认证的用户
     * 分两种情况，高级会员 百度优化 普会
     * 1.高级会员(再生通，百度优化，来电宝) 给予标记认证
     * 2.百度优化有可能存在是普会的情况 给予标记认证
     * 3.普会判断是否认证通过(company_attest) 通过给予标记认证，不通过不标记
     * @param companyId
     * @return
     */
    public boolean validatePassOrNot(Integer companyId);

    /**
     * feiliao91使用的接口，请勿变动，获取用户的资质认证。
     * @param companyAttest
     * @return
     */
    public 	CompanyAttest queryOneInfoForFeiliao91(Integer companyId);

}
