package com.zz91.ep.dao.mblog;

import java.util.List;

import com.zz91.ep.domain.mblog.MBlogSystem;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.mblog.MBlogSystemDto;

/**
 * author:fangchao 
 * date: 2013-10-23
 */
public interface MBlogSystemDao {
	/**
	 * 函数名称：insert
	 * 功能描述：用户系统信息
	 * 输入参数：
	 * 		   @param MBlogSystem system系统数据 
	 * 		   
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2013/10/23　　 fangchao 　　 　　 1.0.0　　 　　 创建方法函数
	 */
	 public Integer insert(MBlogSystem system);
	 /**
	 * 函数名称：updateIsReadStatus
	 * 功能描述：更新用户读的状态
	 * 输入参数：
	 * 		   @param MBlogSystem system系统数据 
	 * 		   
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2013/10/23　　 fangchao 　　 　　 1.0.0　　 　　 创建方法函数
	 */
	 public Integer updateIsReadStatus(Integer id);
	 /**
	 * 函数名称：queryById;
	 * 功能描述：根据id查询类型不同,是否已读的信息
	 * 输入参数：
	 * 		   @param MBlogSystem system系统数据 
	 * 		   
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2013/10/23　　 fangchao 　　 　　 1.0.0　　 　　 创建方法函数
	 */
	 public List<MBlogSystem> queryById(Integer toId,String type,String isRead,Integer start,Integer size);
	 /**
	 * 函数名称：queryById;
	 * 功能描述：根据id查询类型不同,是否已读的信息的计数
	 * 输入参数：
	 * 		   @param MBlogSystem system系统数据 
	 * 		   
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2013/10/23　　 fangchao 　　 　　 1.0.0　　 　　 创建方法函数
	 */
	 public Integer queryCountById(Integer toId,String type,String isRead);
	 
	 public List<MBlogSystemDto> querySystemById(Integer toId,String type,String isRead,PageDto<MBlogSystemDto> page);
	 
	 public List<MBlogSystem> querySystemByisReadAndType(Integer toId,String type,String isRead);
	 //查询出@最多的人
	 
	 public List<MBlogSystem> queryAnteAndCountByfromId(Integer fromId,Integer size);
	 
	 //查询出系统删除的文章
	 public List<MBlogSystem> queryMessageByConditions(Integer toId,String type,String isRead,PageDto<MBlogSystem> page);
}
