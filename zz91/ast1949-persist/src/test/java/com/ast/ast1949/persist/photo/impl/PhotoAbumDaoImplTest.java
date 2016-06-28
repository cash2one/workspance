package com.ast.ast1949.persist.photo.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.ast.ast1949.domain.photo.PhotoAbum;
import com.ast.ast1949.persist.BasePersistTestCase;
import com.ast.ast1949.persist.photo.PhotoAbumDao;

public class PhotoAbumDaoImplTest extends BasePersistTestCase{
	
	@Resource
	private PhotoAbumDao photoAbumDao;
	
	@Test 
	public void test_insert() throws Exception{
		
		clean();
		PhotoAbum photoAbum=new PhotoAbum();
		photoAbum.setCompanyId(14254);
		photoAbum.setAlbumId(0);
		photoAbum.setPicAddress("products/2014/12/30/jlsaf.pig");
		photoAbum.setName("jlsaf");
		int i=photoAbumDao.insert(photoAbum);
		int j=insertResult();
		assertEquals(i, j);
		
		PhotoAbum p=querykw(i);
		assertEquals(photoAbum.getCompanyId(), p.getCompanyId());
		assertEquals(photoAbum.getAlbumId(), p.getAlbumId());
		assertEquals(photoAbum.getPicAddress(), p.getPicAddress());
		assertEquals(photoAbum.getName(), p.getName());
	
	}
	
	@Test
	public void test_delPhotoAbum() throws Exception{
		clean();
		PhotoAbum photoAbum=new PhotoAbum();
		photoAbum.setCompanyId(14254);
		photoAbum.setAlbumId(0);
		photoAbum.setPicAddress("products/2014/12/30/jlsaf.pig");
		photoAbum.setName("jlsaf");
		create(photoAbum);
		Integer id=insertResult();
		int i=photoAbumDao.delPhotoAbum(id);
		assertEquals(1, i);
		
		int j=insertResult();
		assertEquals(0, j);
	}
	
	@Test
	public void test_queryPhotoAbumList() throws Exception{
		clean();
		for (int i = 0; i < 10; i++) {
			PhotoAbum photoAbum=new PhotoAbum();
			
			if(i>5){
				photoAbum.setAlbumId(0);
				photoAbum.setCompanyId(14254);
			}else{
				photoAbum.setCompanyId(14255);
				photoAbum.setAlbumId(1);
			}
			
			photoAbum.setPicAddress("products/2014/12/30/jlsaf.pig");
			photoAbum.setName("jlsaf");
			create(photoAbum);
		}
		Integer albumId=0;
		Integer companyId=14254;
		List<PhotoAbum> list=photoAbumDao.queryPhotoAbumList(null,albumId, companyId);
		//int i=photoAbumDao.queryPhotoAbumListCount(albumId, companyId);
		assertNotNull(list);
		assertEquals(4, list.size());
		
	}
	private void clean() throws Exception{
		connection.prepareStatement("truncate photo_abum").execute();
	}
	
	private void create(PhotoAbum kw) throws SQLException {
		String sql = "INSERT INTO photo_abum(company_id,album_id,pic_address,name,gmt_created,gmt_modified) "
		+ "VALUES ("+kw.getCompanyId()+","+kw.getAlbumId()+",'"+kw.getPicAddress()+"','"+kw.getName()+"',now(),now())";
		connection.prepareStatement(sql).execute();
	}
	
	private PhotoAbum querykw(Integer id) throws SQLException{
		PhotoAbum kw=new PhotoAbum();
		String sql="select company_id,album_id,pic_address,name from photo_abum where id="+id;
		ResultSet rs=connection.createStatement().executeQuery(sql);
		if(rs.next()){
			kw.setCompanyId(rs.getInt(1));;
			kw.setAlbumId(rs.getInt(2));
			kw.setPicAddress(rs.getString(3));
			kw.setName(rs.getString(4));
		}
		return kw;
	}
	
	@Override
	public int insertResult() {
		try {
			ResultSet rs = connection.createStatement().executeQuery(
					"select id from photo_abum order by id desc limit 1");
			if (rs.next()) {
				
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
