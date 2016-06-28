package com.zz91.ep.service.mblog;

import java.util.List;
import java.util.Map;

import com.zz91.ep.domain.mblog.MBlogSystem;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.mblog.MBlogSystemDto;

/**
 * author:fangchao 
 * date: 2013-10-23
 */
public interface MBlogSystemService {
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
	 /**
	  * 根据id,类型,已读的状态查询
	  */
	 public PageDto<MBlogSystemDto> querySystemById(Integer toId,String type,String isRead,PageDto<MBlogSystemDto> page);
	 
	 public List<MBlogSystem> querySystemByisReadAndType(Integer toId,String type,String isRead);
	 /**
	  * 根据fromid查询出对应的数据
	  */
	 public List<MBlogSystem> queryAnteAndCountByfromId(Integer fromId,Integer size);
	 /**
	  * 根据参数查询出@的人
	  * */
	 public PageDto<MBlogSystemDto> queryAnteSystemById(Integer toId,String type,String isRead,PageDto<MBlogSystemDto> page);
	 /**
	  * 根据参数查询出系统给出的数据
	  * */
	 public PageDto<MBlogSystem> queryMessageByInfoId(Integer toId,String type,String isRead,PageDto<MBlogSystem> page);
	 
	
}
