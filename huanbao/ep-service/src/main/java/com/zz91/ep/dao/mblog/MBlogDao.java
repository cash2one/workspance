package com.zz91.ep.dao.mblog;

import java.util.List;

import com.zz91.ep.domain.mblog.MBlog;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.mblog.MBlogDto;

/**
 * author:fangchao date:2013-10-22
*/
public interface MBlogDao {
	/**
	 * 函数名称：insert
	 * 功能描述：发布博文
	 * 输入参数：
	 * 		   @param MBlog mBlog 博文 
	 * 		   
	 * 异　　常：无
	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	 * 　　　　　2013/10/22　　 fangchao 　　 　　 1.0.0　　 　　 创建方法函数
	 */
	public Integer insert(MBlog mBlog);
	/**
  	 * 函数名称：delete
  	 * 功能描述：删除博文
  	 * 输入参数：
  	 * 		   @param MBlog mBlog 博文 
  	 * 		   
  	 * 异　　常：无
  	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
  	 * 　　　　　2013/10/22　　 fangchao 　　 　　 1.0.0　　 　　 创建方法函数
  	 */
      public Integer delete(Integer id);
      /**
 	  * 函数名称：queryAllmBlogById
 	  * 功能描述：查询一个用户的所有博文
 	  * 输入参数：
 	  * 		   @param Integer infoId 用户 
 	  * 		   @param PageDto<MBlog> page
 	  * 异　　常：无
 	  * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
 	  * 　　　　　2013/10/22　　 fangchao 　　 　　 1.0.0　　 　　 创建方法函数
 	  */
       public List<MBlogDto> queryAllmBlogById(Integer infoId,PageDto<MBlogDto> page);
       /**
   	  * 函数名称：queryAllCountmBlogById
   	  * 功能描述：查询一个用户的所有博文
   	  * 输入参数：
   	  * 		   @param Integer infoId 用户 
   	  * 		   @param PageDto<MBlog> page
   	  * 异　　常：无
   	  * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
   	  * 　　　　　2013/10/22　　 fangchao 　　 　　 1.0.0　　 　　 创建方法函数
   	  */
       public Integer queryAllMBlogCountById(Integer infoId);
       /**
   	  * 函数名称：queryOneById
   	  * 功能描述：查询一个用户的一条博文
   	  * 输入参数：
   	  * 		   @param Integer id 博文id 
   	  * 异　　常：无
   	  * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
   	  * 　　　　　2013/10/22　　 fangchao 　　 　　 1.0.0　　 　　 创建方法函数
   	  */
      public MBlog queryOneById(Integer id);
	  /**
 	  * 函数名称：queryByContent
 	  * 功能描述：根据关键字查询
 	  * 输入参数：
 	  * 		   @param String Content 博文
 	  * 异　　常：无
 	  * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
 	  * 　　　　　2013/10/23　　 fangchao 　　 　　 1.0.0　　 　　 创建方法函数
	  */
      public List<MBlog> queryByContent(String Content,PageDto<MBlog> page);
      /**
  	  * 函数名称：queryCountByContent
  	  * 功能描述：根据关键字查询统计数据
  	  * 输入参数：
  	  * 		   @param String Content 博文id 
  	  * 异　　常：无
  	  * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
  	  * 　　　　　2013/10/23　　 fangchao 　　 　　 1.0.0　　 　　 创建方法函数
 	  */
      public Integer queryCountByContent(String Content);
      /**
   	 * 函数名称：updateSentCount
   	 * 功能描述：更改统计转发数量
   	 * 输入参数：
   	 * 		   @param Integer id 博文id
   	 * 		   
   	 * 异　　常：无
   	 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
   	 * 　　　　　2013/10/24　　 fangchao 　　 　　 1.0.0　　 　　 创建方法函数
   	 */
   	 public Integer updateSentCount(Integer id);
   	 
   	 public List<MBlog> queryAllmBlogByInfoId(Integer infoId,Integer size);
   	 
   	 public Integer updateDiscussCount(Integer id);  
   	
   	 public List<MBlog> queryTopicTitle(Integer size);
   	
   	 public List<MBlogDto> queryMyFollowBlog(Integer infoId,Integer groupId,PageDto<MBlogDto> page);
   	 
   	 public Integer queryMyFollowBlogCount(Integer infoId,Integer groupId);
   	 
   	 public List<MBlogDto> queryAllBlog(PageDto<MBlogDto> page);
   	 
   	 public Integer queryAllBlogCount();
   	 
   	 public Integer queryCountBlogByTime(String gmtCreated);
   	 
   	 public List<MBlogDto> queryMyBlog(Integer infoId,PageDto<MBlogDto> page);
   	 
   	 public Integer queryMyBlogCount(Integer infoId);
   	 
   	 //查询是否是第一个发布的话题
   	 public MBlog querymblogByTitle(String title);
   	 //查询所有用户发布的话题
   	 public List<MBlogDto> querytopicbyInfo(String title,Integer size);
     //查询所有用户发布的话题
   	 public Integer querytopicCountByInfo(String title);
   	 //查询出最新发布的人的话题id
   	 public List<MBlog> queryInfoBytopIcTitle(String title,Integer size);
   	 //根据博文id查博文
   	 public MBlogDto queryOneBymblogId(Integer id);
   	 
   	 public Integer updateMblogIsDeleteStatus(Integer id,String isDelete);
   	 
   	 public Integer queryPhotoCountByInfo(Integer infoId);
   	 
}
