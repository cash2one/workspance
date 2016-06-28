package com.ast.ast1949.persist.company;

import java.util.List;

import com.ast.ast1949.domain.company.CompanyAttest;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyAttestDto;

/**
 * 公司工商或个人注册信息
 * 
 * @author zhouzk
 * 
 */
public interface CompanyAttestDAO {
    
    public List<CompanyAttestDto> queryCompanyAttest(CompanyAttest CompanyAttest,String compName, PageDto<CompanyAttestDto> page);
    
    public Integer queryCompanyAttestCount (CompanyAttest CompanyAttest ,String compName);
    
    public CompanyAttest queryByCondition (CompanyAttest companyAttest);
    
    public Integer insertCompanyAttest (CompanyAttest companyAttest);
    
    public CompanyAttest queryAttestByCid (Integer companyId);
    
    public CompanyAttest queryAttestById (Integer id);
    
    public Integer updateCompanyAttest (CompanyAttest companyAttest);
    
    public Integer updateCheckStatusById (Integer id, String checkStatus, String checkPerson);
    
    public Integer deleteCompanyAttest (Integer id);

}
