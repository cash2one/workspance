/*
 * 文件名称：PhotoServiceImpl.java
 * 创建者　：涂灵峰
 * 创建时间：2012-6-18 下午2:32:44
 * 版本号　：1.0.0
 */
package com.zz91.ep.service.trade.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zz91.ep.dao.trade.PhotoDao;
import com.zz91.ep.domain.trade.Photo;
import com.zz91.ep.dto.PageDto;
import com.zz91.ep.dto.trade.SupplyMessageDto;
import com.zz91.ep.service.trade.PhotoService;
import com.zz91.util.Assert;

/**
 * 项目名称：中国环保网
 * 模块编号：业务逻辑Service层
 * 模块描述：相片操作类
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-05-05　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
@Service("photoService")
public class PhotoServiceImpl implements PhotoService {

    @Resource
    private PhotoDao photoDao;

	@Override
	public List<Photo> queryPhotoByTargetType(String targetType, Integer targetId, Integer size) {
		if (size != null && size > 100) {
			size = 100;
		}
		return photoDao.queryPhotoByTargetType(targetType, targetId, size);
	}

	@Override
	public List<Photo> queryPhotoByTargetType(String targetType, Integer targetId) {
		List<Photo> allPhotos = photoDao.queryPhotoByTargetType(targetType, targetId, 20);
		List<Photo> newPhotos = new ArrayList<Photo>();
		for (Photo photo : allPhotos) {
		    if ("0".equals(photo.getIsDel())) {
		        newPhotos.add(photo);
		    }
		} 
		return newPhotos;
	}
	
	@Override
	public List<Photo> queryPhotoByTargetTypePass(String targetType, Integer targetId) {
		List<Photo> allPhotos = photoDao.queryPhotoByTargetType(targetType, targetId, 20);
		List<Photo> newPhotos = new ArrayList<Photo>();
		for (Photo photo : allPhotos) {
			if ("0".equals(photo.getIsDel()) && "1".equals(photo.getCheckStatus()) ) {
				newPhotos.add(photo);
			}
		} 
		return newPhotos;
	}
	
	@Override
	public Photo queryPhotoByTypeAndId(String type,Integer id){
		return photoDao.queryPhotoByTypeAndId(type, id);
	}
	
//	@Override
//    public Integer queryPhotosByCidCount( Integer cid,Integer albumId) {
//        Assert.notNull(cid, "the cid can not be null");
//        return photoDao.queryPhotosByCidCount(cid,albumId);
//    }
	
	 @Override
	    public Integer createPhoto(Photo photo) {
	        Assert.notNull(photo, "the photo can not be null");
	        Assert.notNull(photo.getUid(), "the uid can not be null");
	        Assert.notNull(photo.getCid(), "the uid can not be null");
	        return photoDao.insertPhoto(photo);
	    }
	 
	 @Override
		public Integer deletePhotoTargetIdById(String type, Integer id) {
			Assert.notNull(type, "the type can not be null");
			Assert.notNull(id, "the id can not be null");
	        return photoDao.deletePhotoTargetIdById(type, id);
		}
	 
	 @Override
		public Integer updatePhotoTargetIdById(Integer id, Integer targetId, String targetType) {
			Assert.notNull(id, "the id can not be null");
			if(targetId==null){
				targetId = 0;
			}
	        return photoDao.updatePhotoTargetIdById(id, targetId, targetType);
		}
	 
	 @Override
	    public PageDto<Photo> pagePhotosByCid(Integer cid, PageDto<Photo> page,Integer albumId, Integer queryTarget) {
	        Assert.notNull(cid, "the cid can not be null");
	        page.setRecords(photoDao.queryPhotosByCid(cid, page, albumId, queryTarget));
			page.setTotals(photoDao.queryPhotosByCidCount(cid, albumId, queryTarget));
	        return page;
	    }

	@Override
	public Integer deletePhotoById(Integer id, String path) {
		Assert.notNull(id, "the id can not be null");
        return photoDao.deletePhotoById(id);
	}
	
	

	@Override
	public Integer queryPhotosByPalidCount(Integer palid,Integer cid){
		 Assert.notNull(palid, "the palid can not be null");
	     Integer num = photoDao.queryPhotosByPalidCount(palid,cid);
	     if(num==null){
	    	 return 0;
	     }
	     return num;
	}

	@Override
	public void updatePhotoAlbumId(Integer albumId, Integer pid,String type) {
		 Assert.notNull(albumId, "the albumId can not be null");
		 Assert.notNull(pid, "the albumId can not be null");
		 Assert.isTrue(1==photoDao.updatePhotoAlbumId(albumId, pid,type), "false");
		 
	}

//	@Override
//	public Map<String, Object> queryPhotoAlbumByCid(Integer cid,Integer albumId,String str) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		String path = photoDao.queryPathByAlbumId(cid, albumId);
//		Integer count = photoDao.queryPhotosByCidCount(cid, albumId);
//		
////		if(path==null){
////			path = "/huanbao/images/myesite/nopic.jpg";
////		}
//		map.put("path", path);
//		map.put("count", count);
//		map.put("name", str);
//		map.put("id", albumId);
//		return map;
//	}

	/* (non-Javadoc)
	 * @see com.zz91.ep.service.trade.PhotoService#updateTargetId(java.lang.String[], java.lang.Integer)
	 */
	@Override
	public void updateTargetId(Integer[] photoIdArr, Integer targetId) {
		
		for(Integer pid: photoIdArr){
			photoDao.updatePhotoTargetIdById(pid, targetId, null);
		}
		
	}

	@Override
	public Photo queryPhotoById(Integer id) {
		// TODO Auto-generated method stub
		 Assert.notNull(id, "the id can not be null");
		return photoDao.queryPhotoById(id);
	}

	@Override
	public Map<Integer, Integer>  countPhotoBySupplyId(List<SupplyMessageDto> records) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		Integer i = 0;
		for (SupplyMessageDto obj : records) {
			if (obj != null && obj.getTradeSupply().getId() != null) {
				i = photoDao.queryPhotoCountByTargetType("supply",obj.getTradeSupply().getId());
				map.put(obj.getTradeSupply().getId() , i);
			}
		}
		return map;
	}

	@Override
	public List<Photo> queryPhotoListByTypeAndId(String type, Integer id, String checkStatus) {
		return photoDao.queryPhotoListByTypeAndId(type, id,checkStatus);
	}

	@Override
	public Integer  updateCheckStatus(Integer id, String checkStatus) {
		return photoDao.updateCheckStatus(id,checkStatus);
	}
}
