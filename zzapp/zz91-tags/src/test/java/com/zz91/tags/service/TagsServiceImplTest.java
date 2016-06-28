package com.zz91.tags.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.junit.Test;

import com.zz91.tags.BaseTestCase;
import com.zz91.tags.domain.Tags;
import com.zz91.tags.dto.Pager;

public class TagsServiceImplTest extends BaseTestCase{
	
	@Resource
	private TagsService tagsService;
	
	@Test
	public void testDeleteTags(){
		clean();
		Integer id=createTestRecord(new Tags("杭州",null,0,0,0,null,null));
		Integer i=tagsService.deleteTags(id.toString());
		assertEquals(1, i.intValue());
		Tags tags=queryOneTestRecord(id);
		assertNull(tags);
	}
	@Test
	public void testInsertTags(){
		clean();
		Integer id=tagsService.insertTags(new Tags("杭州",null,0,0,0,null,null));
		Tags tags=queryOneTestRecord(id);
		assertNotNull("杭州", tags.getTags());
	}
	@Test
	public void testPageTags(){
		clean();
		manyTestRecord(7, "杭州");
		Pager<Tags> page=new Pager<Tags>();
		page.setStart(5);
		page.setLimit(5);
		page = tagsService.pageTags("杭州", page);
		assertNotNull(page.getRecords());
		assertEquals(2, page.getRecords().size());
		assertEquals(7, page.getTotals().intValue());
		
		page.setStart(0);
		page.setLimit(5);
		page = tagsService.pageTags("杭州", page);
		assertNotNull(page);
		assertEquals(5, page.getRecords().size());
		assertEquals(7, page.getTotals().intValue());
	}
	private Integer createTestRecord(Tags tags)	{
		String sql="insert into `tags`(tags,tags_encode,click_count,search_count,cited_count," +
				"gmt_created,gmt_modified)" +
				" values('"
				+ tags.getTags()
				+ "','"
				+ tags.getTagsEncode()
				+ "',"
				+ tags.getClickCount()
				+ ","
				+ tags.getSearchCount()
				+ ","
				+ tags.getCitedCount()
				+ ",now(),now())";
		try {
			connection.prepareStatement(sql).execute();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	private Tags queryOneTestRecord(Integer id){
		String sql="select `tags`,`tags_encode`,`click_count`,`search_count`,`cited_count`,`gmt_created`," +
				"`gmt_modified` from `delivery_style` where id=" + id;
		try {
			ResultSet rs=connection.createStatement().executeQuery(sql);
			return new Tags(rs.getString(1), rs.getString(2), rs.getInt(3)
					, rs.getInt(4), rs.getInt(5),null,null); 
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	public Tags oneTestRecord(String tags){
		return new Tags("杭州",null,0,0,0,null,null);
	}
	
	public void manyTestRecord(int num,String tags) {
		for (int i = 0; i < num; i++) {
			createTestRecord(oneTestRecord(tags));
		}
	}
	
	private void clean(){
		try {
			connection.prepareStatement("delete from tags").execute();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
}
