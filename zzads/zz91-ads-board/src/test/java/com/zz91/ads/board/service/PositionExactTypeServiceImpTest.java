/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-7.
 */
package com.zz91.ads.board.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.annotation.Resource;

import com.zz91.ads.board.domain.ad.PositionExactType;
import com.zz91.ads.board.service.ad.PositionExactTypeService;
import com.zz91.ads.board.test.BaseTestCase;

/**
 * @author Rolyer(rolyer.live@gmail.com)
 * 
 */
public class PositionExactTypeServiceImpTest extends BaseTestCase {
	@Resource
	private PositionExactTypeService service;

	/**
	 * 测试保存
	 */

	/**
	 * 测试保存一条记录
	 */
	public void test_save_position_exact_type() {
		clean();
		Boolean b = service.savePositionExactType(getPositionExactType(1, 1));

		assertNotNull(b);
		assertEquals(true, (boolean) b);
	}
	
	/**
	 * 测试保存一条记录
	 */
	public void test_save_position_exact_type_with_same_id() {
		clean();
		insert(6, 6);
		int i = count();
		assertEquals(1, i);
		
		Boolean b = service.savePositionExactType(getPositionExactType(6, 6));
		
		assertNotNull(b);
		assertEquals(true, (boolean) b);
		
		i = count();
		assertEquals(1, i);
	}
	
	/**
	 * 测试删除
	 */

	public void test_delete_position_exact_type_by_position_id() {
		clean();
		for (int i = 1; i <= 10; i++) {
			if(i%2==0) {
				insert(100, i);
			} else {
				insert(i, i);
			}
		}
		assertEquals(10, count());
		
		Integer num = service.deletePositionExactTypeByPositionId(100);
		
		assertNotNull(num);
		assertEquals(5,num.intValue());
		assertEquals(5, count());
	}
	
	public void test_delete_position_exact_type_by_exact_type_id() {
		clean();
		for (int i = 1; i <= 10; i++) {
			if(i%2==0) {
				insert(i, 101);
			} else {
				insert(i, i);
			}
		}
		assertEquals(10, count());
		
		Integer num = service.deletePositionExactTypeByExactTypeId(101);
		
		assertNotNull(num);
		assertEquals(5,num.intValue());
		assertEquals(5, count());
	}
	
	public void test_delete_position_exact_type_by_position_id_and_exact_type_id() {
		clean();
		for (int i = 1; i <= 10; i++) {
			if(i==2) {
				insert(102, 102);
			} else {
				insert(i, i);
			}
		}
		assertEquals(10, count());
		
		Integer num = service.deletePositionExactTypeByPositionIdAndExactTypeId(102, 102);
		
		assertNotNull(num);
		assertEquals(1,num.intValue());
		assertEquals(9, count());
	}

	public void test_demo() {
		clean();
		insert(null, null);
		int i = count();
		assertEquals(1, i);
		clean();
	}

	/**
	 * 获得实例
	 * 
	 * @param adPositionId
	 * @param exactTypeId
	 * @return
	 */
	public PositionExactType getPositionExactType(Integer adPositionId, Integer exactTypeId) {
		if (adPositionId == null) {
			adPositionId = 0;
		}
		if (exactTypeId == null) {
			exactTypeId = 0;
		}
		return new PositionExactType(0,adPositionId, exactTypeId, new Date(), null);
	}

	/**
	 * 插入记录
	 * 
	 * @param adPositionId
	 * @param exactTypeId
	 * @return
	 */
	public void insert(Integer adPositionId, Integer exactTypeId) {
		if (adPositionId == null) {
			adPositionId = 0;
		}
		if (exactTypeId == null) {
			exactTypeId = 0;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO position_exact_type ");
		sb.append("(ad_position_id,exact_type_id,gmt_created,creator) ");
		sb.append("VALUES ");
//		sb.append("('"+adPositionId+"','"+exactTypeId+"',NOW(),'creator')");
		sb.append("(").append(adPositionId).append(",").append(exactTypeId).append(
				",NOW(),'creator')");
		try {
			connection.prepareStatement(sb.toString()).execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 统计记录总数
	 * @return
	 */
	public int count() {
		try {
			ResultSet rs  = connection.prepareStatement("SELECT COUNT(0) FROM position_exact_type").executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			} else {
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public void clean() {
		try {
			connection.prepareStatement("DELETE FROM position_exact_type").execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
