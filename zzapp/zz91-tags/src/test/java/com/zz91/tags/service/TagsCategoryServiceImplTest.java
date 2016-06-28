package com.zz91.tags.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.junit.Test;

import com.zz91.tags.BaseTestCase;
import com.zz91.tags.domain.TagsCategory;


public class TagsCategoryServiceImplTest extends BaseTestCase{
	
	@Resource
	private TagsCategoryService tagsCategoryService;
	
	@Test
	public void testCreateTagsCategory(){
		clean();
		Integer id=tagsCategoryService.createTagsCategory((new TagsCategory("互助动态", null, "牛人牛贴", null, null)), "互助");
		TagsCategory category=queryTestRecord(id);
		assertNotNull("互助",category.getCode() );
		assertNotNull("牛人牛贴", category.getName());
	}
	
	@Test
	public void testUpdateTagsCategory(){
		clean();
		
	}
	public Integer createOneRecord(TagsCategory category){
		String sql="insert into `tags_category`(code,index_key,name,gmt_created,gmt_modified)" +
		" values('"
		+ category.getCode()
		+ "','"
		+ category.getIndexKey()
		+ "','"
		+ category.getName()
		+ "',now(),now())";;
		
		try {
			connection.prepareStatement(sql).execute();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;
	}
	
	public TagsCategory queryTestRecord(Integer id){
		String sql="select `code`,`index_key`,`name`,`gmt_created`," +
		"`gmt_modified` from `delivery_style` where id=" + id;
		try {
			ResultSet rs=connection.prepareStatement(sql).executeQuery();
			return new TagsCategory(rs.getString(1), rs.getString(2), rs.getString(3), null, null);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;
		
	}
	
	public void clean(){
		try {
			connection.prepareStatement("delete from tags_category").execute();
		} catch (SQLException e) {
	
			e.printStackTrace();
		}
	}
	
}
