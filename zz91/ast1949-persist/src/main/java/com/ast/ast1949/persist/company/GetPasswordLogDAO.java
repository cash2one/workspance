package com.ast.ast1949.persist.company;

import java.util.List;

import com.ast.ast1949.domain.company.GetPasswordLog;

public interface GetPasswordLogDAO {
    
    public List<GetPasswordLog> queryPasswordLogByCompanyId(Integer companyId , String type , String date);
    
    public Integer insertPasswordLog (GetPasswordLog getPasswordLog);

}
