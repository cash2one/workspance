/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-16
 */
package com.zz91.ep.admin.service.trade.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.zz91.ep.admin.dao.trade.PhotoDao;
import com.zz91.ep.admin.service.trade.PhotoService;
import com.zz91.ep.domain.trade.Photo;
import com.zz91.util.lang.StringUtils;

/**
 * @author totly
 *
 * created on 2011-9-16
 */
@Component("photoService")
public class PhotoServiceImpl implements PhotoService {

    @Resource
    private PhotoDao photoDao;

    @Override
    public Integer createPhoto(Photo photo) {
        Assert.notNull(photo, "the photo can not be null");
        Assert.notNull(photo.getUid(), "the uid can not be null");
        Assert.notNull(photo.getCid(), "the uid can not be null");
        if(photo.getTargetId()==null){
        	photo.setTargetId(0);
        }
        return photoDao.insertPhoto(photo);
    }

    @Override
    public Integer deletePhotoById(Integer id) {
        Assert.notNull(id, "the id can not be null");
        return photoDao.deletePhotoById(id);
    }

    @Override
    public Integer deletePhotoStatusById(Integer id) {
        Assert.notNull(id, "the id can not be null");
        return photoDao.deletePhotoStatusById(id);
    }
    @Override
    public Integer cancelDeletePhotoStatusById(Integer id) {
        Assert.notNull(id, "the id can not be null");
        return photoDao.cancelDeletePhotoStatusById(id);
    }
//    @Override
//    public List<Photo> queryPhotoByAlbumId(Integer photoAlbumId, Integer uid,
//            Integer cid) {
//        Assert.notNull(cid, "the cid can not be null");
//        return photoDao.queryPhotoByAlbumId(photoAlbumId, uid, cid);
//    }

    @Override
    public List<Photo> queryPhotoByTargetType(String type, Integer id) {
        Assert.notNull(type, "the type can not be null");
        Assert.notNull(id, "the id can not be null");
        return photoDao.queryPhotoByTargetType(type, id);
    }

	@Override
	public Integer updateNewsPhotoByTargetId(Integer targetId) {
		if (targetId!=null&&targetId>0) {
			return photoDao.updateNewsPhotoByTargetId(targetId);
		}
		return null;
	}

	@Override
	public Integer batchUpdatePicStatus(String ids, String unpassReason,
			String checkStatus) {
		Assert.notNull(ids, "id must not be null");
		return photoDao.batchUpdatePicStatus(
				StringUtils.StringToIntegerArray(ids), checkStatus,
				unpassReason);
	}

	/**
	 * 使用productId 批量审核图片
	 */
	@Override
	public Integer queryPhotoByTargetType(String type,Integer productId,	String unpassReason, String checkStatus) {
		List<Photo> list = photoDao.queryPhotoByTargetType(type,productId);
		String ids = "";
		for (Photo obj : list) {
			ids = ids + obj.getId() + ",";
		}
		return batchUpdatePicStatus(ids, unpassReason, checkStatus);
	}
	
    
//    @Override
//    public PageDto<Photo> pagePhotosByCid(String type, Integer cid, PageDto<Photo> page) {
//        Assert.notNull(type, "the type can not be null");
//        Assert.notNull(cid, "the cid can not be null");
//        page.setRecords(photoDao.queryPhotosByCid(type, cid, page));
//		page.setTotals(photoDao.queryPhotosByCidCount(type, cid));
//        return page;
//    }
    
//    @Override
//    public Integer queryPhotosByCidCount(String type, Integer cid) {
//        Assert.notNull(type, "the type can not be null");
//        Assert.notNull(cid, "the cid can not be null");
//        return photoDao.queryPhotosByCidCount(type, cid);
//    }

//	@Override
//	public Integer updatePhotoTargetIdById(Integer id, Integer sid) {
//		Assert.notNull(id, "the id can not be null");
//        Assert.notNull(sid, "the sid can not be null");
//        return photoDao.updatePhotoTargetIdById(id, sid);
//	}

//	@Override
//	public Integer deletePhotoTargetIdById(String type, Integer id) {
//		Assert.notNull(type, "the type can not be null");
//		Assert.notNull(id, "the id can not be null");
//        return photoDao.deletePhotoTargetIdById(type, id);
//	}
}