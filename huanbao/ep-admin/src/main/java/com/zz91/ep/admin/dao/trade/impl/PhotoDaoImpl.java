/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-13
 */
package com.zz91.ep.admin.dao.trade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.trade.PhotoDao;
import com.zz91.ep.domain.trade.Photo;

/**
 * @author totly
 *
 * created on 2011-9-13
 */
@Component("photoDao")
public class PhotoDaoImpl extends BaseDao implements PhotoDao {

    final static String SQL_PREFIX="photo";

    @Override
    public Integer deletePhotoById(Integer id) {
        return getSqlMapClientTemplate().delete(buildId(SQL_PREFIX, "deletePhotoById"), id);
    }

    @Override
    public Integer deletePhotoStatusById(Integer id) {
        return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "deletePhotoStatusById"), id);
    }
    
    @Override
    public Integer cancelDeletePhotoStatusById(Integer id) {
        return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "cancelDeletePhotoStatusById"), id);
    }
    @Override
    public Integer insertPhoto(Photo photo) {
        return (Integer) getSqlMapClientTemplate().insert(buildId(SQL_PREFIX, "insertPhoto"), photo);
    }

//    @SuppressWarnings("unchecked")
//	@Override
//    public List<Photo> queryPhotoByAlbumId(Integer photoAlbumId, Integer uid, Integer cid) {
//        Map<String, Object> root=new HashMap<String, Object>();
//        root.put("aid", photoAlbumId);
//        root.put("uid", uid);
//        root.put("cid", cid);
//        return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryPhotoByAlbumId"), root);
//    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Photo> queryPhotoByTargetType(String type, Integer id) {
        Map<String, Object> root=new HashMap<String, Object>();
        root.put("id", id);
        root.put("type", type);
        return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryPhotoByTargetType"), root);
    }

    @Override
    public Integer updateNewsPhotoByTargetId(Integer targetId) {
        return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updateNewsPhotoByTargetId"), targetId);
    }

	
	final private int DEFAULT_BATCH_SIZE = 20;
	
	@Override
	public Integer batchUpdatePicStatus(Integer[] entities, String checkStatus,
			String unpassReason) {
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
					impacted += updatePicStatus(checkStatus, unpassReason,
							entities[i]);
				}
				getSqlMapClient().executeBatch();
			}
		} catch (Exception e) {}
		return impacted;
	}

	public Integer updatePicStatus(String checkStatus, String unpassReason,
			Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("checkStatus", checkStatus);
		map.put("unpassReason", unpassReason);
		map.put("id", id);
		return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updatePicStatus"), map);
	}

//	@Override
//	public Integer updatePhotoTargetIdById(Integer id, Integer targetId) {
//		Map<String, Object> root=new HashMap<String, Object>();
//        root.put("id", id);
//        root.put("targetId", targetId);
//        return getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "updatePhotoTargetIdById"), root);
//	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Photo> queryPhotosByCid(String type, Integer cid,
//			PageDto<Photo> page) {
//		Map<String,Object> root =new HashMap<String, Object>();
//        root.put("type", type);
//        root.put("page", page);
//        root.put("cid", cid);
//        return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryPhotosByCid"),root);
//	}

//	@Override
//	public Integer queryPhotosByCidCount(String type, Integer cid) {
//		Map<String,Object> root =new HashMap<String, Object>();
//        root.put("type", type);
//        root.put("cid", cid);
//        return (Integer) getSqlMapClientTemplate().queryForObject(buildId(SQL_PREFIX, "queryPhotosByCidCount"),root);
//	}

//	@Override
//	public Integer deletePhotoTargetIdById(String type, Integer id) {
//		Map<String,Object> root =new HashMap<String, Object>();
//        root.put("type", type);
//        root.put("id", id);
//		return (Integer) getSqlMapClientTemplate().update(buildId(SQL_PREFIX, "deletePhotoTargetIdById"), root);
//	}
}