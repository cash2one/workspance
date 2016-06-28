/*
 * 文件名称：CompProfileDao.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.dao.comp;

import java.util.List;

import com.zz91.ep.domain.comp.CompAccount;
import com.zz91.ep.domain.comp.ResetPwd;

/**
 * 项目名称：中国环保网
 * 模块编号：数据操作DAO层
 * 模块描述：公司信息接口。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public interface CompAccountDao {

	/**
	 * 函数名称：queryCompAccountByEmail
	 * 功能描述：根据邮箱查询帐号信息
	 * 输入参数：
	 * 		  @param emailOrAccount String 帐号
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public CompAccount queryCompAccountByEmail(String emailOrAccount);

	/**
	 * 函数名称：queryCompAccountByAccount
	 * 功能描述：根据账号查询公司信息
	 * 输入参数：
	 * 		  @param emailOrAccount String 帐号
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public CompAccount queryCompAccountByAccount(String emailOrAccount);

	/**
	 * 函数名称：insertResetPwd
	 * 功能描述：插入密码重置信息
	 * 输入参数：
	 * 		  @param resetPwd ResetPwd
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer insertResetPwd(ResetPwd resetPwd);

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
	 * 函数名称：resetPassword
	 * 功能描述：重置密码
	 * 输入参数：
	 * 		  @param key String
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer resetPassword(String account, String newPwd);

	/**
	 * 函数名称：deleteResetPwdByKey
	 * 功能描述：删除重置密码信息
	 * 输入参数：
	 * 		  @param key String
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public void deleteResetPwdByKey(String k);

	/**
	 * 函数名称：根据邮箱验证用户名是否存在
	 * 功能描述：删除重置密码信息
	 * 输入参数：
	 * 		  @param key String
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer queryCountCompAcountByEmail(String email);

	/**
	 * 函数名称：根据邮箱验证用户名是否存在
	 * 功能描述：删除重置密码信息
	 * 输入参数：
	 * 		  @param key String
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/04/18　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer getAccountIdByAccount(String account);
	
	/**
	 * 
	 * 函数名称：insertCompAccount
	 * 功能描述:注册
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer insertCompAccount(CompAccount compAccount);
	
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
	 * 
	 * 函数名称：updateBaseCompAccount
	 * 功能描述：修改用户信息
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer updateBaseCompAccount(CompAccount account) ;
	/**
	 * 
	 * 函数名称：queryCompAccountByCid
	 * 功能描述：根据id查询账号信息
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public CompAccount queryCompAccountByCid(Integer cid);
	/**
	 * 根据id查询注册时间
	 * 函数名称：differenceTime
	 * 功能描述：[对此类的描述，....................
	 *         .............可以引用系统设计中的描述]
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer differenceTime(Integer id);
	
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
	public Integer updateAccountPwd(Integer uid, String password, String newPassword);
	
	public Integer countUser(String account, String password);
	 /**
     * update CompAccount
     * @param compAccount
     */
    public Integer updateCompAccount(CompAccount compAccount);
    
    /**
	 * 
	 * 函数名称：validateByEmail
	 * 功能描述：
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/08/17　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
    public Integer validateByEmail(String username, String password);
	 /**
		 * 
		 * 函数名称：validateByEmail
		 * 功能描述：
		 * 输入参数：@param test1 参数1
		 * 　　　　　.......
		 * 　　　　　@param test2 参数2
		 * 异　　常：[按照异常名字的字母顺序]
		 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
		 * 　　　　　2012/08/17　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
		 */
	public Integer validateByAccount(String username, String password);
	
	/**
	 * api 登陆所需查询的信息
	 * @param cid
	 * @return
	 */
	public CompAccount getCompAccountByCid(Integer cid);
	
	/**
	 * 根据cid查询QQ
	 * @param cid
	 * @return
	 */
	public String queryQq(Integer cid);

	/**
	 * @param cid
	 * @return
	 */
	public Integer queryUidByCid(Integer cid);
	
	/**
	 * 获取登录次数
	 * @param cid
	 * @return
	 */
	public Integer queryLoginCount(Integer cid);
	
	/**
	 * 查询电话
	 * @param cid
	 * @return
	 */
	@Deprecated
	public CompAccount queryAccountInfoByCid(Integer cid);

	/**
	 * @param cid
	 * @return
	 */
	public Integer queryCountCompAcountByCid(Integer cid);
	
	public CompAccount queryContactByCid(Integer cid);
	
	/**
	 * 
	 * 函数名称：insertCompAccounts
	 * 功能描述:注册
	 * 输入参数：@param test1 参数1
	 * 　　　　　.......
	 * 　　　　　@param test2 参数2
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2013/08/27　　 方潮 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer insertCompAccounts(CompAccount compAccount);
	
	/**
	 * 函数名称：根据手机验证用户名是否存在
	 * 功能描述：删除重置密码信息
	 * 输入参数：
	 * 		  @param key String
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2013/08/27　　 方潮　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer queryCountCompAcountByMobile(String  mobile);
	/**
	 * 函数名称：根据帐号验证用户名是否存在
	 * 功能描述：删除重置密码信息
	 * 输入参数：
	 * 		  @param key String
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2013/08/27　　 方潮　 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	public Integer queryCountCompAcountByAccount(String  account);
	
	
	
}