package com.zz91.ep.dao.mblog;

import java.util.List;

import com.zz91.ep.domain.mblog.MBlogGroup;

/**
 * author:fangchao 
 * date: 2013-10-23
 */
public interface MBlogGroupDao {
	/**
	 * 函数名称：insert
	 * 功能描述：用户创建祖
	 * 输入参数：
	 * 		   @param MBlogGroup group组名 
	 * 		   
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2013/10/23　　 fangchao 　　 　　 1.0.0　　 　　 创建方法函数
	 */
	 public Integer insert(MBlogGroup group);
	 /**
	 * 函数名称：delete
	 * 功能描述：删除用户创建祖
	 * 输入参数：
	 * 		   @param Integer id 用户id
	 *         @param String isDelete 删除状态 
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2013/10/23　　 fangchao 　　 　　 1.0.0　　 　　 创建方法函数
	 */
     public Integer delete(Integer id,String isDelete);
     /**
 	 * 函数名称：queryById
 	 * 功能描述：根据用户查找分组
 	 * 输入参数：
 	 * 		   @param Integer infoId 用户id
 	 * 异　　常：无
 	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
 	 * 　　　　　2013/10/23　　 fangchao 　　 　　 1.0.0　　 　　 创建方法函数
 	 */
 	 public List<MBlogGroup> queryById(Integer infoId);
 	 /**
	 * queryOneByNameAndId
	 * 根据名字和id查询
	 */
 	 public MBlogGroup queryOneByConditions(Integer infoId,String groupName,Integer id); 
}
