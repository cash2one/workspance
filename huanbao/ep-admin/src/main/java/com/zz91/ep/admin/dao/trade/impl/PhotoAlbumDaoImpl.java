/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-9-13
 */
package com.zz91.ep.admin.dao.trade.impl;

import org.springframework.stereotype.Component;

import com.zz91.ep.admin.dao.BaseDao;
import com.zz91.ep.admin.dao.trade.PhotoAlbumDao;

/**
 * @author totly
 *
 * created on 2011-9-13
 */
@Component("photoAlbumDao")
public class PhotoAlbumDaoImpl extends BaseDao implements PhotoAlbumDao {

//    final static String SQL_PREFIX = "photoAlbum";

//    @SuppressWarnings("unchecked")
//	@Override
//    public List<PhotoAlbumDto> queryPhotoAlbumByUser(Integer uid, Integer cid) {
//        Map<String, Object> root=new HashMap<String, Object>();
//        root.put("uid", uid);
//        root.put("cid", cid);
//        return getSqlMapClientTemplate().queryForList(buildId(SQL_PREFIX, "queryPhotoAlbumByUser"), root);
//    }

}