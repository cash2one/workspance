/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-6-29
 */
package com.ast.ast1949.persist.company;

import java.util.List;

import com.ast.ast1949.domain.company.MyfavoriteDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.MyfavoriteDTO;

/**
 * @author yuyonghui
 *
 */
public interface MyfavoriteDAO {
    
	/**
	 *  分页查询我的篮子
	 * @param myfavoriteDTO 不能为空
	 * @return  成功返回 List<MyfavoriteDTO>  失败返回null 
	 */
	public List<MyfavoriteDTO> queryMyfavoriteByCondition(MyfavoriteDTO myfavoriteDTO);
	
	/**
	 *  查询我的篮子 总记录数
	 * @param myfavoriteDTO 不能为空
	 * @return 成功返回记录总数  失败返回0
	 */
	public Integer queryMyfavoriteCountByCondition(MyfavoriteDTO myfavoriteDTO);
	/**
	 *   加入我的篮子
	 * @param myfavoriteDO 不能为空
	 * @return  添加成功则>0   添加失败则<0
	 */
	public Integer insertMyfavorite(MyfavoriteDO myfavoriteDO);
	
	/**
	 *   查询我的篮子，通过下面条件判断该条信息是否已在我的篮子中
	 * @param contentId  收藏的信息ID不能为空  
 	 * @param companyId  公司Id  不能为空
 	 * @param favoriteTypeCode 收藏夹类型  可以为空
	 * @return  
	 */
	public MyfavoriteDO queryMyfavoriteByMap(Integer contentId,Integer companyId);
     /**
      *    按Id删除我的篮子中信息
      * @param id 不能为空
      * @return  成功则>0  失败则<0
      */
	public Integer bathDeleteMyfavoriteById(int  entities[]);
	
	/**
	 * 判断用户是否已经收藏改信息
	 */
	public Integer isExist(Integer companyId,Integer contentId, String favoriteTypeCode);
	
	///****************2012-10-11****************/
	
	
	/********
	 * 分页查询 我的收藏夹
	 */
	public List<MyfavoriteDTO> pageMyCollect(PageDto<MyfavoriteDTO> page,String keywrods,Integer companyId,String favoriteTypeCode,Integer theday);

	/********
	 * 分页查询的记录条数
	 */
	public Integer pageMyCollectCount(String keywrods,Integer companyId,String favoriteTypeCode,Integer theday);
	
	/*********
	 * 删除我的收藏
	 * @param companyId
	 * @param contentId
	 * @return
	 */
	public Integer deleteMyCollect(Integer companyId,Integer contentId);
	
	
	public Integer countByCodeAndContentId(String favoriteTypeCode,Integer contentId);
}
