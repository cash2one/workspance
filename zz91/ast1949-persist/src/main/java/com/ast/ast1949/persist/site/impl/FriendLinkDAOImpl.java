/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-2-1
 */
package com.ast.ast1949.persist.site.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.site.FriendLinkDO;
import com.ast.ast1949.dto.site.FriendLinkDTO;
import com.ast.ast1949.exception.PersistLayerException;
import com.ast.ast1949.persist.site.FriendLinkDAO;
import com.ast.ast1949.util.Assert;

/**
 * @author yuyonghui
 *
 */
@Component("FriendLinkDAO")
public class FriendLinkDAOImpl extends SqlMapClientDaoSupport implements
		FriendLinkDAO {

	public int getFriendLinkRecordCountByCondition(FriendLinkDTO friendLinkDTO) {
		Assert.notNull(friendLinkDTO, "friendLinkDTO code can not be null");
		return Integer.valueOf(getSqlMapClientTemplate()
				.queryForObject(
						"friendLink.getFriendLinkRecordCountByCondition",
						friendLinkDTO).toString());
	}

	@SuppressWarnings("unchecked")
	public List<FriendLinkDTO> queryFriendLinkByCondition(
			FriendLinkDTO friendLinkDTO) {
		Assert.notNull(friendLinkDTO, "friendLinkDTO code can not be null");
		return getSqlMapClientTemplate().queryForList(
				"friendLink.queryFriendLinkByCondition", friendLinkDTO);
	}

	public FriendLinkDO queryFriendLinkById(Integer id) {
		Assert.notNull(id, "id is not null");
		return (FriendLinkDO) getSqlMapClientTemplate().queryForObject(
				"friendLink.queryFriendLinkById", id);
	}

	final private int DEFAULT_BATCH_SIZE = 20;

	public int batchDeleteFriendLinkById(int[] entities) {
		Assert.notNull(entities, "entities code can not be null");
		int impacted = 0;
		int batchNum = (entities.length + DEFAULT_BATCH_SIZE - 1)
				/ DEFAULT_BATCH_SIZE;
		try {
			for (int currentBatch = 0; currentBatch < batchNum; currentBatch++) {
				getSqlMapClient().startBatch();
				int beginIndex = currentBatch * DEFAULT_BATCH_SIZE;
				int endIndex = (currentBatch + 1) * DEFAULT_BATCH_SIZE;
				endIndex = endIndex > entities.length ? entities.length
						: endIndex;
				for (int i = beginIndex; i < endIndex; i++) {
					impacted += getSqlMapClientTemplate()
							.delete("friendLink.batchDeleteFriendLinkById",
									entities[i]);
				}
				getSqlMapClient().executeBatch();
			}
		} catch (Exception e) {
			throw new PersistLayerException("batch delete user failed.", e);
		}
		return impacted;
	}

	public int insertFriendLink(FriendLinkDO friendLinkDO) {
		Assert.notNull(friendLinkDO, "friendLinkDO is not null");
		return Integer.valueOf(getSqlMapClientTemplate().insert(
				"friendLink.insertFriendLink", friendLinkDO).toString());
	}

	public int updateFriendLink(FriendLinkDO friendLinkDO) {
		Assert.notNull(friendLinkDO, "friendLinkDO is not null");
		return getSqlMapClientTemplate().update("friendLink.updateFriendLink",
				friendLinkDO);
	}
	public int batchCancelCheckedFriendLinkById(int[] entities) {
		Assert.notNull(entities, "entities code can not be null");
		int impacted = 0;
		int batchNum = (entities.length + DEFAULT_BATCH_SIZE - 1)
				/ DEFAULT_BATCH_SIZE;
		try {
			for (int currentBatch = 0; currentBatch < batchNum; currentBatch++) {
				getSqlMapClient().startBatch();
				int beginIndex = currentBatch * DEFAULT_BATCH_SIZE;
				int endIndex = (currentBatch + 1) * DEFAULT_BATCH_SIZE;
				endIndex = endIndex > entities.length ? entities.length
						: endIndex;
				for (int i = beginIndex; i < endIndex; i++) {
					impacted += getSqlMapClientTemplate()
							.update("friendLink.batchCancelCheckedFriendLinkById",
									entities[i]);
				}
				getSqlMapClient().executeBatch();
			}
		} catch (Exception e) {
			throw new PersistLayerException("batch cancel checking friend links failed.", e);
		}
		return impacted;

	}

	public int batchCheckedFriendLinkById(int[] entities) {
		Assert.notNull(entities, "entities code can not be null");
		int impacted = 0;
		int batchNum = (entities.length + DEFAULT_BATCH_SIZE - 1)
				/ DEFAULT_BATCH_SIZE;
		try {
			for (int currentBatch = 0; currentBatch < batchNum; currentBatch++) {
				getSqlMapClient().startBatch();
				int beginIndex = currentBatch * DEFAULT_BATCH_SIZE;
				int endIndex = (currentBatch + 1) * DEFAULT_BATCH_SIZE;
				endIndex = endIndex > entities.length ? entities.length
						: endIndex;
				for (int i = beginIndex; i < endIndex; i++) {
					impacted += getSqlMapClientTemplate()
							.update("friendLink.batchCheckedFriendLinkById",
									entities[i]);
				}
				getSqlMapClient().executeBatch();
			}
		} catch (Exception e) {
			throw new PersistLayerException("batch check friend links failed.", e);
		}
		return impacted;

	}

}
