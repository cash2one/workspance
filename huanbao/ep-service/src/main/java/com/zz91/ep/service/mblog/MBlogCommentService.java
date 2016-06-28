package com.zz91.ep.service.mblog;



import java.util.List;

import com.zz91.ep.domain.mblog.MBlogComment;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.mblog.MBlogCommentDto;

/**
 * author:fangchao 
 * date: 2013-10-23
 */
public interface MBlogCommentService {
	/**
	 * 函数名称：sentComment
	 * 功能描述：添加用户评论
	 * 输入参数：
	 * 		   @param MBlogComment comment 评论信息
	 * 		   
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2013/10/23　　 fangchao 　　 　　 1.0.0　　 　　 创建方法函数
	*/
	public Integer sentComment(MBlogComment comment);
	/**
	 * 函数名称：delete
	 * 功能描述：删除添加用户评论
	 * 输入参数：
	 * 		   @param Integer id 用户id
	 * 		   
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2013/10/23　　 fangchao 　　 　　 1.0.0　　 　　 创建方法函数
	*/
	public Integer delete(Integer id,String isDelete);
	/**
	 * 函数名称：queryCommentBymblogId
	 * 功能描述：查询一条博文的评论
	 * 输入参数：
	 * 		   @param Integer id 用户id
	 * 		   
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2013/10/23　　 fangchao 　　 　　 1.0.0　　 　　 创建方法函数
	*/
     public PageDto<MBlogCommentDto> queryCommentBymblogId(Integer mblogId,PageDto<MBlogCommentDto> page);
     /**
 	 * 函数名称：queryCommentCountBymblogId
 	 * 功能描述：统计查询一条博文的评论
 	 * 输入参数：
 	 * 		   @param Integer mblogId 用户id
 	 * 		   
 	 * 异　　常：无
 	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
 	 * 　　　　　2013/10/23　　 fangchao 　　 　　 1.0.0　　 　　 创建方法函数
 	 */
     public Integer queryCommentCountBymblogId(Integer mblogId);
     
     public List<MBlogCommentDto> queryMblogCommentBymblogId(Integer mblogId);
     
     public MBlogComment queryOneCommentById(Integer id);

}
