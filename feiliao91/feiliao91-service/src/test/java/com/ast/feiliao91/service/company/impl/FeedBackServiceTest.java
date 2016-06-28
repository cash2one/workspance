package com.ast.feiliao91.service.company.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ast.feiliao91.domain.company.FeedBack;
import com.ast.feiliao91.service.BaseServiceTestCase;
import com.ast.feiliao91.service.company.FeedBackService;



public class FeedBackServiceTest extends BaseServiceTestCase {
	@Autowired
	private FeedBackService FeedBackService;

	@Test
	public void test_insert() {
		clean();
		FeedBack b = new FeedBack();
		b.setCompanyId(132);
		b.setDetail("你好");
		b.setTargetcompanyid(111);
		Integer count = FeedBackService.insert(b);
		Integer count2 = insertResult();
		/**
		 * 断言信息是否插入
		 */
		assertEquals(count, count2);
	}

	@Test
	public void tset_selectById() {
		Integer count2 = insertResult();
		FeedBack d = (FeedBack) FeedBackService.selectById(count2);
		FeedBack f = queryById(count2);
		/**
		 * 断言是否相等
		 */
		assertEquals(d.getDetail(), f.getDetail());
		assertEquals(d.getId(), f.getId());
	}

	/**
	 * 清除表信息
	 */
	public void clean() {
		try {
			connection.prepareStatement("delete from feedback").execute();
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
					"select id from feedback order by id desc limit 1");
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
	public FeedBack queryById(Integer id) {
		FeedBack b = new FeedBack();
		ResultSet rs = null;
		try {
			rs = connection.createStatement().executeQuery(
					"select * from feedback where id='" + id + "'");
			if (rs.next()) {
				b.setId(rs.getInt(1));
				b.setCompanyId(rs.getInt(2));
				b.setTargetcompanyid(rs.getInt(3));
				b.setDetail(rs.getString(4));
				b.setIsdel(rs.getInt(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

}
