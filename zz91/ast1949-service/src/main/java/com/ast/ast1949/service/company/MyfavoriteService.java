/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-6-29
 */
package com.ast.ast1949.service.company;

import java.util.List;

import com.ast.ast1949.domain.company.MyfavoriteDO;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.MyfavoriteDTO;

/**
 * @author yuyonghui
 *
 */
public interface MyfavoriteService {
	
	final static String TYPE_CODE_XIANHUO = "10091009";
	
	/**
	 *  分页查询我的篮子
	 * @param myfavoriteDTO 不能为空
	 * @return  成功返回 List<MyfavoriteDO>  失败返回null 
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
	 *        productsDO   不能为空 
	 * @return  添加成功则>0   添加失败则<0
	 */
	public Integer insertMyfavorite(MyfavoriteDO myfavoriteDO,ProductsDO productsDO);
	
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
	public Integer bathDeleteMyfavoriteById(int entities[]);
	
	

	/**
	 * 判断收藏信息是否存在
	 */
	public Boolean isExist(Integer companyId,Integer contentId, String favoriteTypeCode);
	
	/**
	 *  分页 检索
	 * 
	 */
	public PageDto<MyfavoriteDTO> pageMyCollect(PageDto<MyfavoriteDTO> page,String keywrods,Integer companyId,String favoriteTypeCode,Integer theday);
	
	/**
	 * 插入 收藏夹
	 */
	public Integer insertMyCollect(MyfavoriteDO myfavoriteDO);
	
	/**
	 * 删除 收藏夹
	 */
	public Integer deleteMyCollect(Integer compnyId,Integer id);
	
	/**
	 * 根据code 和content_id ，检索某信息被收藏的数量
	 */
	public Integer countByCodeAndContentId(String favoriteTypeCode,Integer contentId);
}
