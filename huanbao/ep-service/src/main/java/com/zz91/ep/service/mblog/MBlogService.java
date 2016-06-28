package com.zz91.ep.service.mblog;




import java.util.List;
import java.util.Map;

import com.zz91.ep.domain.mblog.MBlog;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.mblog.MBlogDto;

/**
 * author:fangchao 
 * date: 2013-10-22
 */
public interface MBlogService {
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
      public PageDto<MBlogDto> queryAllmBlogById(Integer infoId,String type,PageDto<MBlogDto> page);
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
  	  * 函数名称：queryBykeywords
  	  * 功能描述：根据关键字查询
  	  * 输入参数：
  	  * 		   @param String keywords 博文id 
  	  * 异　　常：无
  	  * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
  	  * 　　　　　2013/10/23　　 fangchao 　　 　　 1.0.0　　 　　 创建方法函数
  	  */
      public PageDto<MBlog> queryBykeywords(String keywords,PageDto<MBlog> page);
      /**
	  * 函数名称：queryAllCountmBlogById
	  * 功能描述：查询一个用户的所有博文
	  * 输入参数：
	  * 		   @param Integer infoId 用户 
	  * 		   @param PageDto<MBlog> page
	  * 异　　常：无
	  * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
	  * 　　　　　2013/10/23　　 fangchao 　　 　　 1.0.0　　 　　 创建方法函数
      */
      public Integer queryAllCountMBlogById(Integer infoId);
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
 	/**
 	 * 根据infoId查询出对应的博文
 	 * */ 
 	public List<MBlog> queryAllmBlogByInfoId(Integer infoId,Integer size);
 	/**
 	 * 根据id修改转发量
 	 * */ 
 	public Integer updateDiscussCount(Integer id);
 	/**
 	 * 查询出话题的标题
 	 * */
 	public List<MBlog> queryTopicTitle(Integer size);
 	/**
 	 * 根据infoId,gtoupId查询出我关注的人的博文
 	 * */
 	public PageDto<MBlogDto> queryMyFollowBlog(Integer infoId,Integer groupId,PageDto<MBlogDto> page);
 	/**
 	 * 查询出所有博文的信息
 	 * */
 	public PageDto<MBlogDto> queryAllBlog(PageDto<MBlogDto> page);
 	
 	public Integer queryCountBlogByTime(String gmtCreated);
 	//查询是否是第一个发布的话题
  	public MBlog querymblogByTitle(String title);
    //查询所有用户发布的话题
  	public List<MBlogDto> querytopicbyInfo(String title,Integer size);
    //查询所有用户发布的话题
  	public Integer querytopicCountByInfo(String title);
  	//查询出图片的集合
	public List queryMblogPhoto(String photos); 
	//根据id查询出mblog
	public MBlogDto queryOneBymblogId(Integer id);
	//截取出@的人
	public Map<String, Object> replceMblogAnte(String contents );
	//根据id查询出mblog
	public Integer updateMblogIsDeleteStatus(Integer id,String isDelete);

	public PageDto<MBlogDto> pageBySearchEngine(String keywords,PageDto<MBlogDto> page);
	
	public MBlog quyerOneMblogById(Integer id);
	/**
 	 * 查询出所有博文的信息
 	 * */
 	public PageDto<MBlogDto> queryMBlog(PageDto<MBlogDto> page);
}
