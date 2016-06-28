/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-12-10
 */
package com.zz91.crm.dao;

import java.util.List;

import com.zz91.crm.domain.CrmContact;

/**
 * @author totly created on 2011-12-10
 */
public interface CrmContactDao {

    /**
     * 新建联系人
     * @param crmContact
     * @return
     */
    public Integer createCrmContact(CrmContact crmContact);
    
    /**
     * 查看联系人信息
     * @param cid
     * @return
     */
    public List<CrmContact> queryCrmContactByCid(Integer cid);
}