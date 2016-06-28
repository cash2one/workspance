/*
 * 文件名称：CreditFileDao.java
 * 创建者　：涂灵峰
 * 创建时间：2012-6-19 上午11:03:35
 * 版本号　：1.0.0
 */
package com.zz91.ep.dao.comp;

import java.util.List;

import com.zz91.ep.domain.comp.CreditFile;


/**
 * 项目名称：中国环保网
 * 模块编号：数据操作DAO层
 * 模块描述：荣誉证书
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-05-05　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public interface CreditFileDao {

	/**
	 * 函数名称：queryCreditFileByCid
	 * 功能描述：根据ID查询荣誉证书
	 * 输入参数：@param cid 公司ID
	 * 　　　　　@param category 类别
	 * 　　　　　@param checkStatus 审核状态
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 涂灵峰 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	List<CreditFile> queryCreditFileByCid(Integer cid, String category, Short checkStatus);
	
	/**
	 * 函数名称：updateCreditFileName
	 * 功能描述：根据公司ID修改公司信息 
	 * 输入参数：@param cid 公司ID
	 * 　　　　　@param id  
	 * 　　　　　@param fikeName
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林 　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	Integer updateCreditFileName(Integer id, Integer cid, String fileName);
	
	/**
	 * 函数名称：deleteCreditById
	 * 功能描述：根据公司ID删除公司信息
	 * 输入参数：@param cid 公司ID
	 * 异　　常：[按照异常名字的字母顺序]
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2012/05/05　　 陈庆林　　 　　 　 1.0.0　　 　　 创建方法函数
	 */
	Integer deleteCreditById(Integer id, Integer companyId);
	
	
	Integer createCreditFile(CreditFile creditFile);

}
