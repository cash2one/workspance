package com.zz91.ads.board.service.ad.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;
import com.zz91.ads.board.domain.ad.DeliveryStyle;
import com.zz91.ads.board.service.ad.DeliveryStyleService;
import com.zz91.ads.board.test.BaseTestCase;

public class DeliveryStyleServiceImplTest extends BaseTestCase	{
	
	@Resource
	private DeliveryStyleService deliveryStyleService;
	
	@Test
	public void testQueryDeliveryStyle()	{
		clean();
		createOneTestRecord(new DeliveryStyle("test name1", "test jsFunction", null, null, "test modifier"));
		createOneTestRecord(new DeliveryStyle("test name2", "test jsFunction", null, null, "test modifier"));
		createOneTestRecord(new DeliveryStyle("test name3", "test jsFunction", null, null, "test modifier"));
		List<DeliveryStyle> list=deliveryStyleService.queryDeliveryStyle();
		assertNotNull(list);
		assertEquals(3, list.size());
	}
	
	@Test
	public void testCreateStyle()	{
		clean();
		Integer id=deliveryStyleService.createStyle(new DeliveryStyle("test name", "test jsFunction", null, null, "test modifier"));
		DeliveryStyle style=queryOneTestRecord(id);
		assertNotNull(style);
		assertEquals("test name", style.getName());
	} 
	
	@Test
	public void testUpdateStyle()	{
		clean();
		Integer id = createOneTestRecord(new DeliveryStyle("old name", "old jsFunction", null, null, "old modifier"));
		
		DeliveryStyle style= new DeliveryStyle();
		style.setId(id);
		style.setName("new name");
		style.setJsFunction("new jsFunction");
		style.setModifier("new modifier");
		Integer i=	deliveryStyleService.updateStyle(style);
		assertEquals(1, i.intValue());
		
		DeliveryStyle nstyle=queryOneTestRecord(id);
		assertEquals("new name",nstyle.getName());
		assertEquals("new jsFunction", nstyle.getJsFunction());
		assertEquals("new modifier", nstyle.getModifier());
	}

	@Test
	public void testDeleteStyle()	{
		clean();
		Integer id=createOneTestRecord(new DeliveryStyle("test name", "test jsFunction", null, null, "test modifier"));
		Integer i=deliveryStyleService.deleteStyle(id);
		assertEquals(1, i.intValue());
		
		DeliveryStyle style=queryOneTestRecord(id);
		assertNull(style);
	}
	
	private Integer createOneTestRecord(DeliveryStyle style)	{
		String sql="insert into `delivery_style`(`name`,`js_function`,`modifier`,`gmt_created`,`gmt_modified`)" +
		"values('"
		+ style.getName()
		+ "','"
		+ style.getJsFunction()
		+ "','"
		+ style.getModifier()
		+ "',now(),now())";
		try {
			connection.prepareStatement(sql).execute();

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;
	}
	
	private DeliveryStyle queryOneTestRecord(Integer id)	{
		String sql="select `name`,`js_function`,`gmt_created`,`gmt_modified`,`modifier` from `delivery_style` where id=" + id;
		try {
			ResultSet rs=connection.createStatement().executeQuery(sql);
			if(rs.next()){
				return new DeliveryStyle(rs.getString(1), rs.getString(2), null, null, rs.getString(5));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;
	}
	
	private void clean()	{
		try {
			connection.prepareStatement("delete from delivery_style").execute();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	
}
