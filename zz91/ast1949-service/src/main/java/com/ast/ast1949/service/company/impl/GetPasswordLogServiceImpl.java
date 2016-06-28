package com.ast.ast1949.service.company.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.GetPasswordLog;
import com.ast.ast1949.persist.company.GetPasswordLogDAO;
import com.ast.ast1949.service.company.GetPasswordLogService;
import com.zz91.util.datetime.DateUtil;

@Component("getPasswordLogService")
public class GetPasswordLogServiceImpl implements GetPasswordLogService {
    
    @Resource
    private GetPasswordLogDAO getPasswordLogDAO;

    @Override
    public Integer numOfType(Integer companyId , String type) {
        String date = DateUtil.toString(new Date(), "yyyy-MM-dd");
        List<GetPasswordLog> list = getPasswordLogDAO.queryPasswordLogByCompanyId(companyId , type , date);
        return list.size();
    }

    @Override
    public Integer insertPasswordLog(GetPasswordLog getPasswordLog) {

        return getPasswordLogDAO.insertPasswordLog(getPasswordLog);
    }

}
