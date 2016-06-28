/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-25
 */
package com.ast.ast1949.service.information.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ast.ast1949.domain.information.Periodical;
import com.ast.ast1949.domain.information.PeriodicalDetails;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.service.BaseServiceTestCase;
import com.ast.ast1949.service.information.PeriodicalDetailsService;

/**
 * @author Mays (x03570227@gmail.com)
 *
 */
public class PeriodicalDetailsServiceImplTest extends BaseServiceTestCase{

	@Autowired
	private PeriodicalDetailsService periodicalDetailsService;
	/**
	 * Test method for {@link com.ast.ast1949.service.information.impl.PeriodicalDetailsServiceImpl#deleteDetails(java.lang.Integer[])}.
	 * @throws SQLException
	 */
	@Test
	public void testDeleteDetails() throws SQLException {
		clean();
		int x=createPeriodical(
			new Periodical(null,"testname","cycle",null,null,"http://www.google.com",(float)16.8,null,null,null,null,null)
		);
		Integer[] y={
			createPeriodicalDetails(new PeriodicalDetails(null,x,"test-name","test-image-name.jpg","http://www.google.com",null,2,null,null))
			,createPeriodicalDetails(new PeriodicalDetails(null,x,"test-name","test-image-name.jpg","http://www.google.com",null,2,null,null))
			,createPeriodicalDetails(new PeriodicalDetails(null,x,"test-name","test-image-name.jpg","http://www.google.com",null,2,null,null))
			,createPeriodicalDetails(new PeriodicalDetails(null,x,"test-name","test-image-name.jpg","http://www.google.com",null,2,null,null))
			,createPeriodicalDetails(new PeriodicalDetails(null,x,"test-name","test-image-name.jpg","http://www.google.com",null,2,null,null))
		};
		periodicalDetailsService.deleteDetails(y);
		assertEquals(countPeriodicalDetailsByPeriodicalId(x).intValue(), 0);
	}

	/**
	 * Test method for {@link com.ast.ast1949.service.information.impl.PeriodicalDetailsServiceImpl#listPreviewDetailsByPeriodicalId(java.lang.Integer)}.
	 * @throws SQLException
	 */
	@Test
	public void testListPreviewDetailsByPeriodicalId() throws SQLException {
		clean();
		int x=createPeriodical(
			new Periodical(null,"testname","cycle",null,null,"http://www.google.com",(float)16.8,null,null,null,null,null)
		);
		createPeriodicalDetails(new PeriodicalDetails(null,x,"test-name","test-image-name.jpg","http://www.google.com",null,2,null,null));
		createPeriodicalDetails(new PeriodicalDetails(null,x,"test-name","test-image-name.jpg","http://www.google.com",null,2,null,null));
		createPeriodicalDetails(new PeriodicalDetails(null,x,"test-name","test-image-name.jpg","http://www.google.com",null,2,null,null));
		createPeriodicalDetails(new PeriodicalDetails(null,x,"test-name","test-image-name.jpg","http://www.google.com",null,2,null,null));
		createPeriodicalDetails(new PeriodicalDetails(null,x,"test-name","test-image-name.jpg","http://www.google.com",null,2,null,null));
		createPeriodicalDetails(new PeriodicalDetails(null,x,"test-name","test-image-name.jpg","http://www.google.com",null,2,null,null));
		createPeriodicalDetails(new PeriodicalDetails(null,x,"test-name","test-image-name.jpg","http://www.google.com",null,2,null,null));
		createPeriodicalDetails(new PeriodicalDetails(null,x,"test-name","test-image-name.jpg","http://www.google.com",null,2,null,null));
		createPeriodicalDetails(new PeriodicalDetails(null,x,"test-name","test-image-name.jpg","http://www.google.com",null,2,null,null));

		List<PeriodicalDetails> list=periodicalDetailsService.listPreviewDetailsByPeriodicalId(x);
		assertEquals(list.size(), 9);
	}

	/**
	 * Test method for {@link com.ast.ast1949.service.information.impl.PeriodicalDetailsServiceImpl#pageDetailsByPeriocidalId(java.lang.Integer, com.ast.ast1949.dto.PageDto)}.
	 * @throws SQLException
	 */
	@Test
	public void testPageDetailsByPeriocidalId() throws SQLException {
		clean();
		int x=createPeriodical(
			new Periodical(null,"testname","cycle",null,null,"http://www.google.com",(float)16.8,null,null,null,null,null)
		);
		createPeriodicalDetails(new PeriodicalDetails(null,x,"test-name","test-image-name.jpg","http://www.google.com",null,2,null,null));
		createPeriodicalDetails(new PeriodicalDetails(null,x,"test-name","test-image-name.jpg","http://www.google.com",null,2,null,null));
		createPeriodicalDetails(new PeriodicalDetails(null,x,"test-name","test-image-name.jpg","http://www.google.com",null,2,null,null));
		createPeriodicalDetails(new PeriodicalDetails(null,x,"test-name","test-image-name.jpg","http://www.google.com",null,2,null,null));
		createPeriodicalDetails(new PeriodicalDetails(null,x,"test-name","test-image-name.jpg","http://www.google.com",null,2,null,null));
		createPeriodicalDetails(new PeriodicalDetails(null,x,"test-name","test-image-name.jpg","http://www.google.com",null,2,null,null));
		createPeriodicalDetails(new PeriodicalDetails(null,x,"test-name","test-image-name.jpg","http://www.google.com",null,2,null,null));
		createPeriodicalDetails(new PeriodicalDetails(null,x,"test-name","test-image-name.jpg","http://www.google.com",null,2,null,null));

		PageDto page=new PageDto();
		page.setPageSize(6);

		page.setStartIndex(0);

		page=periodicalDetailsService.pageDetailsByPeriocidalId(x, page);
		assertEquals(page.getRecords().size(),6);
		assertEquals(page.getTotalRecords().intValue(),8);

		page.setStartIndex(6);
		page=periodicalDetailsService.pageDetailsByPeriocidalId(x, page);
		assertEquals(page.getRecords().size(),2);
		assertEquals(page.getTotalRecords().intValue(),8);
	}

	/**
	 * Test method for {@link com.ast.ast1949.service.information.impl.PeriodicalDetailsServiceImpl#updateBaseDetails(com.ast.ast1949.domain.information.PeriodicalDetails)}.
	 * @throws SQLException
	 */
	@Test
	public void testUpdateBaseDetails() throws SQLException {
		clean();
		Periodical np=new Periodical(null,"testname","cycle",null,null,"http://www.google.com",(float)16.8,null,null,null,null,null);
		int x=createPeriodical(np);

		PeriodicalDetails pd=new PeriodicalDetails(null,x,"test-name","test-image-name.jpg","http://www.google.com",null,2,null,null);
		int y=createPeriodicalDetails(pd);

		pd.setName("new-test-name");
		pd.setId(y);
		Integer i=periodicalDetailsService.updateBaseDetails(pd);
		assertTrue(i>0);

		PeriodicalDetails npd=listOnePeriodicalDetails(y);
		assertEquals("new-test-name", npd.getName());
	}

	/**
	 * Test method for {@link com.ast.ast1949.service.information.impl.PeriodicalDetailsServiceImpl#updatePageType(java.lang.Integer, java.lang.Integer)}.
	 * @throws SQLException
	 */
	@Test
	public void testUpdatePageType() throws SQLException {
		clean();
		Periodical np=new Periodical(null,"testname","cycle",null,null,"http://www.google.com",(float)16.8,null,null,null,null,null);
		int x=createPeriodical(np);

		PeriodicalDetails pd=new PeriodicalDetails(null,x,"test-name","test-image-name.jpg","http://www.google.com",null,2,null,null);
		int y=createPeriodicalDetails(pd);

		periodicalDetailsService.updatePageType(y, 1);

		PeriodicalDetails npd=listOnePeriodicalDetails(y);
		assertEquals(1, npd.getPageType().intValue());
	}

	/**********************************/

	int TEST_DATA=5;
	private void clean() throws SQLException{
		connection.prepareStatement("delete from periodical_details").execute();
		connection.prepareStatement("delete from periodical").execute();
	}

	private List<Integer> preparePeriodical(boolean withDetails) throws SQLException{
		List<Integer> impact=new ArrayList<Integer>();
		for(int i=0;i<TEST_DATA;i++){
			int x=createPeriodical(
				new Periodical(null,"testname","cycle",null,null,"http://www.google.com",(float)16.8,null,null,null,null,null)
			);
			impact.add(x);
			if(withDetails){
				for(int j=0;j<TEST_DATA;j++){
					createPeriodicalDetails(new PeriodicalDetails(null,x,"test-name","test-image-name.jpg","http://www.google.com",null,2,null,null));
				}
			}
		}
		return impact;
	}

	private Integer createPeriodical(Periodical periodical) throws SQLException{
		String sql="";
		sql="insert into periodical(name,cycle,num_view,num_up,pdf_download,size,gmt_release,num_release,release_no,gmt_created) ";
		sql=sql+" values('"+
			periodical.getName()+"','"+
			periodical.getCycle()+"',"+
			periodical.getNumView()+","+
			periodical.getNumUp()+",'"+
			periodical.getPdfDownload()+"',"+
			periodical.getSize()+","+
			periodical.getGmtRelease()+","+
			periodical.getNumRelease()+",'"+
			periodical.getReleaseNo()+"',now())";

		connection.prepareStatement(sql).execute();
		ResultSet rs=connection.createStatement().executeQuery("select last_insert_id()");
		if(rs.next()){
			return rs.getInt(1);
		}

		return 0;
	}

	private Integer createPeriodicalDetails(PeriodicalDetails details) throws SQLException{
		String sql="";
		sql="insert into periodical_details(periodical_id,name,image_name,preview_url,orders,page_type,gmt_created) ";
		sql=sql+" values("+
			details.getPeriodicalId()+",'"+
			details.getName()+"','"+
			details.getImageName()+"','"+
			details.getPreviewUrl()+"',"+
			details.getOrders()+","+
			details.getPageType()+",now())";

		connection.prepareStatement(sql).execute();
		ResultSet rs=connection.createStatement().executeQuery("select last_insert_id()");
		if(rs.next()){
			return rs.getInt(1);
		}

		return 0;
	}

	private Integer countPeriodicalDetailsByPeriodicalId(Integer periodicalId) throws SQLException{
		String sql = "select count(*) from periodical_details where periodical_id = "+periodicalId;
		ResultSet rs=connection.createStatement().executeQuery(sql);
		if(rs.next()){
			return rs.getInt(1);
		}
		return null;
	}

	private Integer countPeriodicalDetails() throws SQLException{
		String sql = "select count(*) from periodical_details ";
		ResultSet rs=connection.createStatement().executeQuery(sql);
		if(rs.next()){
			return rs.getInt(1);
		}
		return null;
	}

	public PeriodicalDetails listOnePeriodicalDetails(Integer pdid) throws SQLException{
		String sql = "select id,periodical_id,name,image_name,preview_url,orders,page_type" +
				" from periodical_details where id="+pdid;

		ResultSet rs=connection.createStatement().executeQuery(sql);
		if(rs.next()){
			return new PeriodicalDetails(
					rs.getInt("id"),
					rs.getInt("periodical_id"),
					rs.getString("name"),
					rs.getString("image_name"),
					rs.getString("preview_url"),
					rs.getInt("orders"),
					rs.getInt("page_type"),
					null,
					null
					);
		}
		return null;

	}

}
