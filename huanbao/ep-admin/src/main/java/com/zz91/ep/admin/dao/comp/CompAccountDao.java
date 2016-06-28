package com.zz91.ep.admin.dao.comp;

import com.zz91.ep.domain.comp.CompAccount;

public interface CompAccountDao {

    /**
     * 通过会员账户查询会员账户ID
     * @param account
     * @return
     */
    public Integer getAccountIdByAccount(String account);

    /**
     * 通过会员账户ID查询公司ID
     * @param accountId
     * @return
     */
//    public Integer getCompanyIdByAccountId(Integer accountId);

    /**
     * 通过会员账户查询会员信息
     * @param account
     * @return
     */
    public CompAccount queryCompAccountByAccount(String account);

    /**
     * query count of CompAccount by email
     * @param email
     * @return
     */
//    public Integer queryCountCompAcountByEmail(String email);

    /**
     * query CompAccount by email
     * @param email
     * @return
     */
    public CompAccount queryCompAccountByEmail(String email);

    /**
     * update CompAccount
     * @param compAccount
     */
    public Integer updateCompAccount(CompAccount compAccount);

    /**
     * query count of CompAccount by account
     * @param account
     * @return
     */
//    public Integer queryCountCompAcountByAccount(String account);

    /**
     * 插入CompAccount对象
     */
    public Integer insertCompAccount(CompAccount compAccount);
    
    /**
     * 更新公司联系人信息
     * @param account
     * @return
     */
    public Integer updateBaseCompAccount(CompAccount account);

    /**
     * 修改密码
     * @param uid
     * @param password
     * @param newPassword
     * @return
     */
//    public Integer updateAccountPwd(Integer uid, String password, String newPassword);
    
    /**
	 * 通过cid查询公司账户信息
	 * @param cid
	 * @return
	 */
	public CompAccount queryCompAccountByCid(Integer cid);

	/**
	 * 通过手机号查询公司账户信息
	 * @param mobile
	 * @return
	 */
	public Integer queryCompAccountByMobile(String mobile);
	
	public Integer countUser(String account, String password);
	
	public CompAccount queryAccountDetails(String account);
	
	public Integer queryCidByAccount(String account);
	
//	public String queryAccountNameByCid(Integer cid);
	
//	public String queryNameByUid(Integer uid);

//	public Integer queryUidByCid(Integer cid);

//	public Integer queryWeekAddAccount();
	
	public Integer updateAccount(CompAccount account);

//	public Integer queryAllAddAccount();

//	public String queryAccountById(Integer uid);

	public Integer countUserByEmail(String email);

//	public Integer generateResetPwdKey(ResetPwd resetPwd);

//	public ResetPwd listResetPwdByKey(String key);

//	public Integer resetPassword(String account, String pwd1);

//	public void deleteResetPwdByKey(String k);

	public Integer updateEmailByAccount(String account, String email);

	public Integer updateEmailToBlank(String email, String tmpEmail);

	public Integer countUserByEmailAndAccount(String email, String account);

	/**
	 * 根据公司ID查询手机和电话
	 * @param id
	 * @return
	 */
	public CompAccount queryMobileAndPhoneByCid(Integer id);

	public Integer queryMaxId();
	
	/**
	 * 根据公司cid查询登录次数
	 * @param cid
	 * @return
	 */
	public Integer queryLoginCountByCid(Integer cid);

	/**
	 * 根据公司Id查询账户密码
	 * @param cid
	 * @return
	 */
	public String queryPasswordClearByCid(Integer cid);

	/**
	 * @param email
	 * @return
	 */
	public CompAccount queryAccountByEmail(String email);
}
