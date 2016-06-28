package com.zz91.ep.admin.service.trade;

public class CompProfileServiceTest extends BaseServiceTestCase {
    
//    @Resource
//    private CompProfileService compProfileService;
//
//	@Test
//    public void testQueryCompProfileById() {
//		PageDto<WebsiteStatistics> page = new PageDto<WebsiteStatistics>();
//		page = compProfileService.pageWebsiteStatistics(page);
//		assertNotNull(page.getRecords());
//		System.out.println(page.getTotals());
//       
//    }
    
   /* @Test
    public void testQueryCompProfileById() {
        clean();
        createOneTestRecord(oneTestRecord(1));
        CompProfile comp = compProfileService.queryCompProfileById(1);
        assertNotNull(comp);
        assertEquals(comp.getId(), new Integer(1));
    }

    @Test
    public void testQueryCompDetailsById() {
        clean();
        createOneTestRecord(oneTestRecord(1));
        String details = compProfileService.queryCompDetailsById(1);
        assertNotNull(details);
        assertEquals(details, "简介");
    }
    
    @Test
    public void testUpdateBaseCompProfile() {
        clean();
        CompProfile comp = oneTestRecord(1);
        createOneTestRecord(comp);
        comp.setAddress("修改后地址");
        int count = compProfileService.updateBaseCompProfile(comp);
        assertEquals(count, 1);
    }
    
    @Test
    public void testPageCompDetails(){
    	clean();
    	manyRecord(7);
    	manyAccount(7);
		PageDto<CompProfileDto> page = new PageDto<CompProfileDto>();
		
		page.setStart(5);
		page.setLimit(5);
		page=compProfileService.pageCompDetails(page, "测试数据","","",null);
		assertNotNull(page.getRecords());
		assertEquals(2, page.getRecords().size());
		assertEquals(7, page.getTotals().intValue());
		
		page.setStart(0);
		page.setLimit(5);
		page=compProfileService.pageCompDetails(page, "测试数据","","",null);
		assertNotNull(page.getRecords());
		assertEquals(5, page.getRecords().size());
		assertEquals(7, page.getTotals().intValue());
    }
    
    @Test
    public void testUpdateMeberCodeBlockById(){
    	clean();
    	createOneTestRecord(oneTestRecord(1));
    	CompProfile profile=queryProfileById(1);
    	assertNotNull(profile);
    	Integer i=compProfileService.updateMeberCodeBlockById(1, "10000000");
    	assertEquals(1, i.intValue());
    	CompProfile profile1=queryProfileById(1);
    	assertNotNull(profile1);
    	assertEquals("10000000", profile1.getMemberCodeBlock());
    	
    }

    private void clean() {
        try {
            connection.prepareStatement("delete from comp_profile").execute();
            connection.prepareStatement("delete from comp_account").execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void manyRecord(Integer max){
    	for (int i = 0; i < max; i++) {
    		createOneTestRecord(new CompProfile(i+1, "测试数据", (short)1, (short)1, "10001000", "", 0, 0, new Date(), new Date()));
		}
    }
    
    private CompProfile queryProfileById(Integer id) {
		String sql ="select `id`,`name`,`main_buy`,`main_supply`," +
                "`member_code`,`member_code_block`,`message_count`,`view_count`," +
                "`gmt_created`,`gmt_modified` from `comp_profile` where id=" +id;
		try {
			ResultSet rs =connection.createStatement().executeQuery(sql);
			if(rs.next()){
				return new CompProfile(rs.getInt(1), rs.getString(2), rs.getShort(3), rs.getShort(4), 
						rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8), null, null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
    
    private Integer createOneTestRecord(CompProfile cp) {
        String sql="insert into comp_profile(`id`,`name`,`details`,`main_buy`,`main_supply`," +
                "`member_code`,`member_code_block`,`message_count`,`view_count`," +
                "`gmt_created`,`gmt_modified`)" +
                "values("                            
                + cp.getId()
                + ",'"
                + cp.getName()
                + "','"
                + cp.getDetails()
                + "',"
                + cp.getMainBuy()
                + ","
                + cp.getMainSupply()
                + ",'"
                + cp.getMemberCode()
                + "','"
                + cp.getMemberCodeBlock()
                + "',"
                + cp.getMessageCount()
                + ","
                + cp.getViewCount()
                + ", now(),now())";
        
        try {
            connection.prepareStatement(sql).execute();
            return insertResult();
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
        return null;
    }

    private CompProfile oneTestRecord(Integer id) {
        CompProfile compProfile = new CompProfile();
        compProfile.setId(id);
        compProfile.setName("阿思拓");
        compProfile.setDetails("简介");
        compProfile.setMainBuy((short) 1);
        compProfile.setMainSupply((short)1);
        compProfile.setMemberCode("1");
        compProfile.setMemberCodeBlock("");
        compProfile.setMessageCount(1);
        compProfile.setViewCount(1);
        return compProfile;
    }
    
    private void manyAccount(Integer max){
    	for (int i = 0; i < max; i++) {
			createOneTestRecord(new CompAccount(i+1, i+1, i+1+"test", i+1+"test", "", "", "测试", new Date(), new Date(), new Date(), new Date()));
		}
    }
    private Integer createOneTestRecord(CompAccount ca) {
        String sql="INSERT INTO comp_account"
                 + "(id,"
                 + "cid,"
                 + "account,"
                 + "email,"
                 + "password,"
                 + "password_clear,"
                 + "name,"
                 + "gmt_login,"
                 + "gmt_register,"
                 + "gmt_created,"
                 + "gmt_modified)"
                 + "VALUES("
                 + ca.getId() + ","
                 + ca.getCid() + ",'"
                 + ca.getAccount() + "','"
                 + ca.getEmail() + "','"
                 + ca.getPassword() + "','"
                 + ca.getPasswordClear() + "','"
                 + ca.getName() + "',now(),now(),now(),now())";
        try {
            connection.prepareStatement(sql).execute();
            return insertResult();
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
        return null;
    }*/
    
}