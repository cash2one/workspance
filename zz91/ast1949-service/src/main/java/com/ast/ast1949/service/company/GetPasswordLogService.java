package com.ast.ast1949.service.company;

import com.ast.ast1949.domain.company.GetPasswordLog;

public interface GetPasswordLogService {
    
    public Integer numOfType(Integer companyId , String type);
    
    public Integer insertPasswordLog (GetPasswordLog getPasswordLog);

}
