/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-1
 */
package com.ast.ast1949.persist.site;

import java.util.List;

import com.ast.ast1949.domain.site.FriendLinkDO;
import com.ast.ast1949.dto.site.FriendLinkDTO;

/**
 * @author yuyonghui
 *
 */
public interface FriendLinkDAO {

	/**
	 * 友情链接列表
	 *
	 * @param friendLinkDTO
	 * @return 有记录：返回所有符合条件的记录集 无记录：返回null
	 */
	public List<FriendLinkDTO> queryFriendLinkByCondition(
			FriendLinkDTO friendLinkDTO);

	/**
	 * 查询按Id
	 *
	 * @param id
	 * @return 有记录：返回所有符合条件的记录集 无记录：返回null
	 */
	public FriendLinkDO queryFriendLinkById(Integer id);

	/**
	 * 获取记录总数
	 *
	 * @param friendLinkDTO
	 * @return 成功：返回影响行数；<br/>
	 *         失败：返回0.
	 */
	public int getFriendLinkRecordCountByCondition(FriendLinkDTO friendLinkDTO);

	/**
	 * 修改友情链接
	 *
	 * @param friendLinkDO
	 * @return 成功：返回影响行数；<br/>
	 *         失败：返回0.
	 */
	public int updateFriendLink(FriendLinkDO friendLinkDO);

	/**
	 * 添加友情链接
	 *
	 * @param friendLinkDO
	 * @return 成功：返回影响行数；<br/>
	 *         失败：返回0.
	 */
	public int insertFriendLink(FriendLinkDO friendLinkDO);

	/**
	 * 批量删除
	 *
	 * @param entities
	 * @return 0表示没有删除任何数据,大于0表示删除的行数
	 */
	public int batchDeleteFriendLinkById(int[] entities);

	/**
	 * 批量审核
	 *
	 * @param entities
	 * @return 0表示没有审核任何数据,大于0表示审核的行数
	 */
	public int batchCancelCheckedFriendLinkById(int[] entities);

	/**
	 * 批量取消审核
	 *
	 * @param entities
	 * @return 0表示没有取消审核任何数据,大于0表示取消审核的行数
	 */
	public int batchCheckedFriendLinkById(int[] entities);

}
