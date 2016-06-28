/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-12-10
 */
package com.zz91.crm.dao;

import com.zz91.crm.domain.CrmCompanyBackup;

/**
 * @author totly created on 2011-12-10
 */
public interface CrmCompanyBackupDao {

    /**
     * 添加公司信息
     * @param company
     * @return
     */
    public Integer createCompany(CrmCompanyBackup company);
}
