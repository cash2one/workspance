package com.ast.ast1949.persist.phone.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.ast.ast1949.domain.phone.PhoneSeoKeyWords;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.persist.BasePersistTestCase;
import com.ast.ast1949.persist.phone.PhoneSeoKeyWordDao;

public class PhoneSeoKeyWordDaoImplTest extends BasePersistTestCase{
	@Resource
	private PhoneSeoKeyWordDao phoneSeoKeyWordDao;
	
	@Test 
	public void test_updateSeoKeyWord() throws Exception{
		clean();
		PhoneSeoKeyWords seo=new PhoneSeoKeyWords();
		seo.setTitle("bb");
		seo.setPinYin("ww");
		seo.setClickCount(4);
		create(seo);
		int id =insertResult();
		PhoneSeoKeyWords kw=new PhoneSeoKeyWords();
		kw.setId(id);
		kw.setTitle("66");
		kw.setPinYin("uu");
		kw.setClickCount(3);
		int i=phoneSeoKeyWordDao.updateSeoKeyWord(kw);
		assertEquals(1, i);
		
		//判断插入的数据是否相同
		PhoneSeoKeyWords root=querykw(id);
		assertEquals(kw.getTitle(), root.getTitle());
		assertEquals(kw.getPinYin(), root.getPinYin());
		assertEquals(kw.getClickCount(), root.getClickCount());
	}
	@Test
	public void test_insert() throws Exception{
		clean();
		PhoneSeoKeyWords  kw=new PhoneSeoKeyWords();
		kw.setClickCount(3);
		kw.setTitle("luo2");
		kw.setPinYin("luo3");
		int i =phoneSeoKeyWordDao.insert(kw);
		int j=insertResult();
		assertTrue(i==j);
		//判断插入的数据是否相同
		PhoneSeoKeyWords root=querykw(i);
		assertEquals(kw.getTitle(), root.getTitle());
		assertEquals(kw.getPinYin(), root.getPinYin());
		assertEquals(kw.getClickCount(), root.getClickCount());
	
	}
	
	@Test
	public void test_queryPhoneSeoKeyWordById() throws Exception{
		clean();
		PhoneSeoKeyWords seo=new PhoneSeoKeyWords();
		seo.setTitle("bb");
		seo.setPinYin("ww");
		seo.setClickCount(4);
		create(seo);
		Integer id =insertResult();
		PhoneSeoKeyWords kwKeyWords= phoneSeoKeyWordDao.queryPhoneSeoKeyWordById(id);
		assertNotNull(kwKeyWords);
		//判断插入的数据是否相同
		assertEquals(seo.getTitle(), kwKeyWords.getTitle());
		assertEquals(seo.getPinYin(), kwKeyWords.getPinYin());
		assertEquals(seo.getClickCount(), kwKeyWords.getClickCount());
	}
	@Test
	public void test_queryList() throws Exception {
		clean();
		PhoneSeoKeyWords seo=new PhoneSeoKeyWords();
		for (int i = 0; i < 10; i++) {
			String title="bb"+i;
			String pinyin="ww"+i;
			Integer clickcount=i;
			seo.setTitle(title);
			seo.setPinYin(pinyin);
			seo.setClickCount(clickcount);
			create(seo);
		}
		
		PageDto<PhoneSeoKeyWords> page=new PageDto<PhoneSeoKeyWords>();
		List<PhoneSeoKeyWords> list=new ArrayList<PhoneSeoKeyWords>();
		PhoneSeoKeyWords kw=new PhoneSeoKeyWords();
		list=phoneSeoKeyWordDao.queryList(kw, page);
		assertNotNull(list);
		int i=list.size();
		assertEquals(10, i);
	}
	@Test
	public void test_queryListCount() throws Exception{
		clean();
		PhoneSeoKeyWords seo=new PhoneSeoKeyWords();
		for (int i = 0; i < 10; i++) {
			String title="bb"+i;
			String pinyin="ww"+i;
			Integer clickcount=i;
			seo.setTitle(title);
			seo.setPinYin(pinyin);
			seo.setClickCount(clickcount);
			create(seo);
		}
		PhoneSeoKeyWords kw=new PhoneSeoKeyWords();
		int i=phoneSeoKeyWordDao.queryListCount(kw);
		assertEquals(10, i);
	}
	private void clean() throws Exception{
		connection.prepareStatement("truncate phone_seo_keywords").execute();
	}
	
	private void create(PhoneSeoKeyWords kw) throws SQLException {
		String sql = "INSERT INTO phone_seo_keywords(title,pinyin,click_count,gmt_created,gmt_modified) "
		+ "VALUES ('"+kw.getTitle()+"','"+kw.getPinYin()+"',"+kw.getClickCount()+",now(),now())";
		connection.prepareStatement(sql).execute();
	}
	
	private PhoneSeoKeyWords querykw(Integer id) throws SQLException{
		PhoneSeoKeyWords kw=new PhoneSeoKeyWords();
		String sql="select title,pinyin,click_count from phone_seo_keywords where id="+id;
		ResultSet rs=connection.createStatement().executeQuery(sql);
		if(rs.next()){
			kw.setTitle(rs.getString(1));
			kw.setPinYin(rs.getString(2));
			kw.setClickCount(rs.getInt(3));
		}
		return kw;
	}
	@Override
	public int insertResult() {
		try {
			ResultSet rs = connection.createStatement().executeQuery(
					"select id from phone_seo_keywords order by id desc limit 1");
			if (rs.next()) {
				
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
