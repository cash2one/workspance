/**
 * Project name: zz91-mail
 * File name: AccountServiceTest.java
 * Copyright: 2005-2011 ASTO Info TechCo.,Ltd. All rights reserved
 */
package com.zz91.mail.test;

import javax.annotation.Resource;

import com.zz91.mail.domain.AccountDomain;
import com.zz91.mail.service.AccountService;

/**
 * @author kongsj
 * @email kongsj@zz91.net
 * @date 2011-11-16
 */
public class AccountServiceTest extends BaseServiceTestCase {
    @Resource
    private AccountService accountService;

    private Integer init() {
        AccountDomain ao = new AccountDomain();
        ao.setEmail("kxzjzjzzkx@gmail.com");
        ao.setHost("mail.zz91.cn");
        ao.setIsDelete(0);
        ao.setCode("1001");
        Integer i = accountService.insertAccount(ao);
        return i;
    }

    private void delete(Integer id) {
        accountService.deleteAccountById(id);
    }

    public void test_selectById() {
        Integer i = init();
        AccountDomain tdo = accountService.queryAccountByEmail("kxzjzjzzkx@gmail.com");
        assertTrue(tdo!=null);
        delete(i);
    }

    public void test_selectByCode() {
        Integer i = init();
        AccountDomain tdo = accountService.queryAccountByCode("1001");
        assertTrue(tdo!=null);
        delete(i);
    }

    public void test_update() {
        init();
        AccountDomain tdo = accountService.queryAccountByCode("1001");
        tdo.setCode("2");
        accountService.updateAccount(tdo);
        tdo = accountService.queryAccountByCode("2");
        assertEquals("update error", "2", tdo.getCode());
    }

    public void test_deleteByHost() {
        init();
        accountService.deleteAccountByHost("mail.zz91.cn");
        AccountDomain tdo = accountService.queryAccountByCode("1001");
        assertTrue(tdo == null);
    }
}
