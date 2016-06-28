package com.zz91.ep.admin.service.comp;

import com.zz91.ep.domain.comp.CompAccount;

/**
 * 
 * @author leon
 *
 */
public interface CompAccountService {

    public CompAccount validateAccount(String account, String password, String ip) throws Exception;

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
     * 通过会员账户查询公司会员信息
     * @param account
     * @return
     */
//    public CompAccount getCompAccountByAccount(String account);

	/**
	 * 通过公司ID查询会员账户信息
	 */
	public CompAccount getCompAccountByCid(Integer cid);

    /**
     * 更新公司联系人信息
     * @param account
     * @return
     */
//    public Integer updateCompAccount(CompAccount account);
    

    /**
     * 修改密码
     * @param account
     * @param password
     * @param newPassword
     * @return
     */
//    public Integer updateAccountPwd(Integer uid, String password, String newPassword);
    
    
    
//    public CompAccount queryAccountDetails(String account);
    
    public Integer queryCidByAccount(String account);
    
    /**
     * 根据邮箱查询用户是否存在
     * @param email
     * @return
     */
//    public Integer queryCountCompAcountByEmail(String email);
    
    /**
     * 创建用户信息
     * @param compAccount
     * @return
     */
//    public Integer createCompAccount(CompAccount compAccount);
    /**
     * 根据公司id查询公司账户信息
     * @return
     */
//    public String queryAccountNameByCid(Integer cid);
    
//    public String queryNameByUid(Integer uid);

//	public Integer queryUidByCid(Integer cid);

//	public Integer queryWeekAddAccount();
	
	/**
	 * 更新账户信息(完整)
	 * @param account
	 * @return
	 */
	public Integer updateAccount(CompAccount account);

//	public Integer queryAllAddAccount();

//	public String queryAccountById(Integer uid);

//	public Integer countUserByEmail(String email);

//	public String generateResetPwdKey(String emailOrAccount);

//	public ResetPwd listResetPwdByKey(String k);

//	public Boolean validatePwdKey(String am, String k) throws NoSuchAlgorithmException, UnsupportedEncodingException;

//	public void sendResetPasswordMail(String key, String email, String url) throws NoSuchAlgorithmException, UnsupportedEncodingException ;

//	public Boolean resetPasswordFromRetPwdKey(String k, String newPwd);

//	public boolean validatePwdKeyForSetPwd(String key);

	public Integer queryMaxId();

	/**
	 * 查询密码(crm数据导出专用)
	 * @param cid
	 * @return
	 */
	public String queryPasswordClearByCid(Integer cid);

	/**
	 * 查询账户一些简单信息
	 * @param email
	 * @return
	 */
	public CompAccount queryAccountByEmail(String email);
	
}
