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
import com.ast.ast1949.service.information.PeriodicalService;
import com.ast.ast1949.util.StringUtils;

/**
 * @author Mays (x03570227@gmail.com)
 *
 */
public class PeriodicalServiceImplTest extends BaseServiceTestCase{

	@Autowired
	private PeriodicalService periodicalService;

	/**
	 * Test method for {@link com.ast.ast1949.service.information.impl.PeriodicalServiceImpl#batchDeletePeriodical(java.lang.Integer[])}.
	 * @throws SQLException
	 */
	@Test
	public void testBatchDeletePeriodical() throws SQLException {
		clean();
		List<Integer> ids=preparePeriodical(true);

		Integer impact = periodicalService.batchDeletePeriodical(StringUtils.StringToIntegerArray(ids.toString().replace("[","").replace("]","").replace(" ", "")));
		assertEquals(impact.intValue(), ids.size());
		assertEquals(0,countPeriodicalDetails().intValue());

	}

	/**
	 * Test method for {@link com.ast.ast1949.service.information.impl.PeriodicalServiceImpl#batchDeletePeriodicalWithDetails(java.lang.Integer[])}.
	 * @throws SQLException
	 */
	@Test
	public void testBatchDeletePeriodicalOnlyDetails() throws SQLException {
		clean();
		List<Integer> ids=preparePeriodical(true);
		Integer[] pids={ids.get(0),ids.get(1)};
		periodicalService.batchDeletePeriodicalOnlyDetails(pids);
		assertEquals(0, countPeriodicalDetailsByPeriodicalId(ids.get(0)).intValue());
		assertEquals(0, countPeriodicalDetailsByPeriodicalId(ids.get(1)).intValue());
	}

	/**
	 * Test method for {@link com.ast.ast1949.service.information.impl.PeriodicalServiceImpl#createPeriodical(com.ast.ast1949.domain.information.Periodical)}.
	 * @throws SQLException
	 */
	@Test
	public void testCreatePeriodical() throws SQLException {
		clean();

		try {
			periodicalService.createPeriodical(null);
			fail();
		} catch (Exception e) {
		}
		Periodical p=new Periodical();
		p.setName("test-name");
		Integer i=periodicalService.createPeriodical(p);
		assertTrue(i>0);
	}

	/**
	 * Test method for {@link com.ast.ast1949.service.information.impl.PeriodicalServiceImpl#listOnePeriodicalById(java.lang.Integer)}.
	 * @throws SQLException
	 */
	@Test
	public void testListOnePeriodicalById() throws SQLException {
		clean();
		int x=createPeriodical(
			new Periodical(null,"testname","cycle",null,null,"http://www.google.com",(float)16.8,null,null,null,null,null)
		);

		Periodical p=periodicalService.listOnePeriodicalById(x);
		assertEquals(p.getName(), "testname");
		assertEquals(p.getSize().floatValue(), (float)16.8);
	}

	/**
	 * Test method for {@link com.ast.ast1949.service.information.impl.PeriodicalServiceImpl#pagePeriodicalWithoutSearch(com.ast.ast1949.dto.PageDto)}.
	 * @throws SQLException
	 */
	@Test
	public void testPagePeriodicalWithoutSearch() throws SQLException {
		clean();
		TEST_DATA = 12;
		preparePeriodical(false);
		TEST_DATA=5;

		PageDto page=new PageDto();
		page.setPageSize(10);


		page.setStartIndex(0);

		page=periodicalService.pagePeriodicalWithoutSearch(page);
		assertEquals(page.getRecords().size(),10);
		assertEquals(page.getTotalRecords().intValue(),12);

		page.setStartIndex(10);
		page=periodicalService.pagePeriodicalWithoutSearch(page);
		assertEquals(page.getRecords().size(),2);
		assertEquals(page.getTotalRecords().intValue(),12);
	}

	/**
	 * Test method for {@link com.ast.ast1949.service.information.impl.PeriodicalServiceImpl#unzipUploadedDetails(java.lang.String, java.lang.Integer)}.
	 */
//	@Test
//	public void testUnzipUploadedDetails() {
//		fail("Not yet implemented");
//	}

	/**
	 * Test method for {@link com.ast.ast1949.service.information.impl.PeriodicalServiceImpl#updatePeriodical(com.ast.ast1949.domain.information.Periodical)}.
	 * @throws SQLException
	 */
	@Test
	public void testUpdatePeriodical() throws SQLException {
		clean();
		Periodical np=new Periodical(null,"testname","cycle",null,null,"http://www.google.com",(float)16.8,null,null,null,null,null);
		int x=createPeriodical(np);

		np.setName("new-test-name");
		np.setSize((float)18.8);
		np.setId(x);
		Integer i=periodicalService.updatePeriodical(np);
		assertTrue(i.intValue()>0);

		Periodical up=periodicalService.listOnePeriodicalById(x);
		assertEquals("new-test-name", up.getName());
		assertEquals((float)18.8, up.getSize());
	}

	@Test
	public void test_updateNumView() throws SQLException{
		clean();
		Periodical np=new Periodical(null,"testname","cycle",0,0,"http://www.google.com",(float)16.8,null,null,null,null,null);
		int x=createPeriodical(np);

		periodicalService.updateNumViewById(x);

		Periodical up=periodicalService.listOnePeriodicalById(x);

		assertEquals(1, up.getNumView().intValue());

	}

	@Test
	public void test_updateNumUp() throws SQLException{
		clean();
		Periodical np=new Periodical(null,"testname","cycle",0,0,"http://www.google.com",(float)16.8,null,null,null,null,null);
		int x=createPeriodical(np);

		periodicalService.updateNumUpById(x);

		Periodical up=periodicalService.listOnePeriodicalById(x);

		assertEquals(1, up.getNumUp().intValue());
	}

	/**
	 * Test method for {@link com.ast.ast1949.service.information.impl.PeriodicalServiceImpl#zipPeriodicalDetails(java.lang.Integer)}.
	 */
//	@Test
//	public void testZipPeriodicalDetails() {
//		fail("Not yet implemented");
//	}

	/****************************************/

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
}
