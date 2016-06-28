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
	public PageDto<MyfavoriteDO> pageMyCollect(PageDto<MyfavoriteDO> page,String keywrods,Integer companyId,String favoriteTypeCode,Integer theday);
	
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
	
	public Integer countByCompanyId(Integer companyId);

	/**
	 * 互助我的收藏所有的 帖子 问答 废料学院
	 * @param page
	 * @param keywrods
	 * @param companyId
	 * @param favoriteTypeCode
	 * @param theday
	 * @return
	 */
	public PageDto<MyfavoriteDO> pageMyCollectForMyhuzhu(PageDto<MyfavoriteDO> page,String keywrods, Integer companyId, String favoriteTypeCode,Integer theday);
	/**
	 * 收藏互助的问答、帖子、以及废料学院
	 * @param favorite
	 * @return
	 */
	public Integer insertMyCollectForNewhuzhuz(MyfavoriteDO favorite);
	/**
	 * 获取某用户关注的企业自主报价数
	 * @param companyId
	 * @param favoriteTypeCode
	 * @return
	 */
	public Integer countNoticeByCondition(Integer companyId,String favoriteTypeCode,String keywords);
	/**
	 * 删除收藏
	 * @param companyId
	 * @param favoriteTypeCode
	 * @param contentId
	 * @return
	 */
	public Integer deleteCollection(Integer companyId,String favoriteTypeCode,Integer contentId);
	
	/**
	 * 获取某用户某收藏类型的收藏
	 * @param companyId
	 * @param favoriteTypeCode
	 * @return
	 */
	public List<MyfavoriteDO> queryYuanliaoCollectList(Integer companyId,String favoriteTypeCode);
}
