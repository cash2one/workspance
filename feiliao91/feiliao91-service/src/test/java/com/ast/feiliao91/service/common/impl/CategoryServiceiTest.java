package com.ast.feiliao91.service.common.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ast.feiliao91.domain.common.Category;
import com.ast.feiliao91.service.BaseServiceTestCase;
import com.ast.feiliao91.service.commom.CategoryService;

public class CategoryServiceiTest extends BaseServiceTestCase{
	@Autowired
	private CategoryService categoryService;

	@Test
	public void test_insert() {
		clean();
		Category b = new Category();
		b.setLabel("标题");
		b.setPinyin("hangzhou");
		Integer count = categoryService.insert(b);
		Integer count2 = insertResult();
		/**
		 * 断言信息是否插入
		 */
		assertEquals(count, count2);
	}

	@Test
	public void tset_selectById() {
		Integer count2 = insertResult();
		Category d =  categoryService.selectById(count2);
		Category f = queryById(count2);
		/**
		 * 断言是否相等
		 */
		assertEquals(d.getPinyin(), f.getPinyin());
		assertEquals(d.getLabel(), f.getLabel());
	}

	/**
	 * 清除表信息
	 */
	public void clean() {
		try {
			connection.prepareStatement("delete from category").execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询插入的最后一条数据ID
	 */
	public int insertResult() {
		try {
			ResultSet rs = connection.createStatement().executeQuery(
					"select id from category order by id desc limit 1");
			if (rs.next()) {

				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 根据ID查询信息
	 * 
	 * @param id
	 * @return
	 */
	public Category queryById(Integer id) {
		Category b = new Category();
		ResultSet rs = null;
		try {
			rs = connection.createStatement().executeQuery(
					"select * from category where id='" + id + "'");
			if (rs.next()) {
				b.setId(rs.getInt(1));
				b.setLabel(rs.getString(4));
				b.setPinyin(rs.getString(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}
    
}
