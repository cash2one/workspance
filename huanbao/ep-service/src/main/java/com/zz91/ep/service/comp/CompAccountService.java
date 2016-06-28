/*
 * 文件名称：CompProfileService.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.service.comp;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.zz91.ep.domain.comp.CompAccount;
import com.zz91.ep.domain.comp.ResetPwd;
import com.zz91.util.auth.ep.EpAuthUser;
import com.zz91.util.auth.frontsso.SsoUser;
import com.zz91.util.exception.AuthorizeException;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作Service层
 * 模块描述：公司信息接口。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public interface CompAccountService {
	
	/**
	 * 函数名称：generateResetPwdKey
	 * 功能描述：插入密码重置信息
	 * 输入参数：
	 * 		  @param categoryCode String 推荐类别
	 *        @param size Integer 数量
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public String insertResetPwdKey(String emailOrAccount);

	/**
	 * 函数名称：listResetPwdByKey
	 * 功能描述：根据key查询密码重置信息
	 * 输入参数：
	 * 		  @param key String
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public ResetPwd listResetPwdByKey(String key);

	/**
	 * 函数名称：sendResetPasswordMail
	 * 功能描述：发送密码重置邮件
	 * 输入参数：
	 * 		  @param key String
	 * 		  @param email String
	 * 		  @param url String
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public void sendResetPasswordMail(String key, String email, String url) throws NoSuchAlgorithmException, UnsupportedEncodingException;
	
	/**
	 * 函数名称：validatePwdKey
	 * 功能描述：验证Key是否正确并且未失效,若正确且没有失效,则跳转到密码修改页面,若失效或者不正确,则调整到错误提示页面
	 * 输入参数：
	 * 		  @param key String
	 * 		  @param email String
	 * 		  @param url String
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public boolean validatePwdKey(String am, String k) throws NoSuchAlgorithmException, UnsupportedEncodingException;

	/**
	 * 函数名称：resetPasswordFromRetPwdKey
	 * 功能描述：重置密码并删除重置密码信息
	 * 输入参数：
	 * 		  @param key String
	 * 		  @param email String
	 * 		  @param url String
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public boolean resetPasswordFromRetPwdKey(String k, String newPwd);
	/**
	 * 函数名称：queryCountCompAcountByEmail
	 * 功能描述：根据邮箱查询用户是否存在
	 * 输入参数：
	 * 		  @param key String
	 * 		  @param email String
	 * 		  @param url String
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer queryCountCompAcountByEmail(String email);
	/**
	 * 函数名称：getAccountIdByAccount
	 * 功能描述：判读用户名是否唯一
	 * 输入参数：
	 * 		  @param key String
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer getAccountIdByAccount(String account);
	
	/**
	 * 
	 * 函数名称：queryAccountById
	 * 功能描述：通过公司ID查询会员账户信息
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public CompAccount getCompAccountByCid(Integer cid);
	
	/**
	 * 
	 * 函数名称：queryAccountById
	 * 功能描述：根据id查询账号
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public String queryAccountById(Integer uid);
	
	/**
	 * 
	 * 函数名称：queryAccountDetails
	 * 功能描述：根据账号查询账号信息
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public CompAccount queryAccountDetails(String account);
	
	public Integer updateCompAccount(CompAccount comaccount);
	
	/**
	 * 
	 * 函数名称：updateAccountPwd
	 * 功能描述：修改密码
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer updateAccountPwd(Integer uid, String password,String newPassword);

	/**
	 * 函数名称：queryImpCompAccount
	 * 功能描述：查询外网数据导入数据
	 * 输入参数：
	 * 		  @param maxId String 外网导入数据最大ID
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public List<CompAccount> queryImpCompAccount(Integer maxId);
	
	/**
	 * @author 陈庆林
	 * @param account
	 * @param password
	 * @param ip
	 * @return
	 * @throws Exception
	 * api
	 * 验证帐户
	 */
	public CompAccount validateAccount(String account, String password, String ip) throws Exception;
	
	
	 /**
     * 验证登陆
     * @author 陈庆林 
     * @param account
     * @param password
	 * @throws AuthorizeException 
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
     * @returnString
     */
    public Integer validateUser(String account,String password) throws AuthorizeException, NoSuchAlgorithmException, UnsupportedEncodingException;
    
    /**
     * epauther初始化
     * @param account
     * @return
     */
    public EpAuthUser initEpAuthUser(Integer cid,String projectCode);
    
    /**
     * 
     * @param account
     * @param ip
     * @param cid 
     */
    public void updateLoginInfo(Integer uid, String ip, Integer cid);
    
    /**
     * 取得权限
     * @param companyId
     * @param memberCode
     * @param memberCodeBlock
     * @param project
     * @return
     */
    public String[] getAuthListByCompanyIdAndMemberCode(Integer companyId,
			String memberCode, String project);
    
    /**
     * 根据公司id查询QQ
     * @param cid
     * @return
     */
    public String queryQq(Integer cid);
    
    /**
	 * 函数名称：querySimplyCompAccountByCid
	 * 功能描述：查询账户一些简单信息
	 * 输入参数：
	 * 		  @param maxId String 外网导入数据最大ID
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/09/27　　 齐振杰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
    public CompAccount querySimplyCompAccountByCid(Integer cid);

	/**
	 * @param cid
	 * @return
	 */
	public Integer queryUidByCid(Integer cid);
	
	/**
	 * 
	 */
	public Integer queryLoginCountByCid(Integer cid);

	/**
	 * @param cid带你你
	 * @return
	 */
	public Integer queryCountCompAcountByCid(Integer cid);
	
	/**
	 * 函数名称：queryCountCompAcountByMobile
	 * 功能描述：根据手机查询用户是否存在
	 * 输入参数：
	 * 		  @param key String
	 * 		  @param email String
	 * 		  @param url String
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2013/08/27　　  方潮 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer queryCountCompAcountByMobile(String mobile);
	
	/**
	 * 函数名称：queryCountCompAcountByAccount
	 * 功能描述：根据帐号查询用户是否存在
	 * 输入参数：
	 * 		  @param key String
	 * 		  @param email String
	 * 		  @param url String
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2013/08/27　　  方潮 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer queryCountCompAcountByAccount(String account);
}

    