package com.zz91.ep.admin.service.trade;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import org.junit.Test;
import com.zz91.ep.domain.trade.TradeKeyword;
import com.zz91.ep.dto.PageDto;

public class TradeKeywordServiceTest extends BaseServiceTestCase {

	@Resource
	private TradeKeywordService tradeKeywordService;
	
	@Test
	public void testUpdateTradeKeywordById() throws  Exception {
		clean();
		
		Integer id=createdOneRecord(onetgestRecord(1));
		
		TradeKeyword keyword = new TradeKeyword();
		keyword.setId(id);
		keyword.setCname("修改后的名字111");
		Integer i = tradeKeywordService.updateTradeKeywordById(keyword);
		
		TradeKeyword key = queryTradeKeywordById(id);
		
		assertEquals(1, i.intValue());
		assertNotNull(key);
		assertEquals("修改后的名字111", key.getCname());
		
	}

	@Test
	public void testUpdateStatusById() throws  Exception {
		clean();
		Integer id=createdOneRecord(onetgestRecord(1));
		TradeKeyword key=queryTradeKeywordById(id);
		assertNotNull(key);
		Integer a=tradeKeywordService.updateStatusById(id, (short)2);
		assertEquals(1, a.intValue());
	}

	@Test
	public void testPageTradeKeyword() throws SQLException, Exception {
		clean();
		for(int i = 0;i<7;i++){
			createdOneRecord(onetgestRecord(1));
		}
		
		PageDto<TradeKeyword> page = new PageDto<TradeKeyword>();
		page.setStart(5);
		page.setLimit(10);
		
		page=tradeKeywordService.pageTradeKeyword("aaa", page, (short)1, "2012-01-01 12:00:00", "2013-01-01 12:00:00");
		assertNotNull(page.getRecords());
		assertEquals(2, page.getRecords().size());
		assertEquals(7, page.getTotals().intValue());
		
		page.setStart(0);
		page.setLimit(5);
		
		page=tradeKeywordService.pageTradeKeyword("aaa", page, (short)1, "2012-01-01 12:00:00", "2013-01-01 12:00:00");
		assertNotNull(page.getRecords());
		assertEquals(5, page.getRecords().size());
		assertEquals(7, page.getTotals().intValue());
		
	}

	@Test
	public void testQueryTradekeywordById() throws Exception {
		//清除数据
		clean();
		//插入测试数据 id=5
		Integer id=createdOneRecord(onetgestRecord(1));
		//开始测试方法
		TradeKeyword key=tradeKeywordService.queryTradekeywordById(id);
//		判断方法是否正确
		assertNotNull(key);
		assertEquals("jinan",key.getCname());
		
	}
	private TradeKeyword onetgestRecord(Integer id) throws Exception{
		TradeKeyword tradekeyword=new TradeKeyword();
		tradekeyword.setId(id);
		tradekeyword.setCid(1);
		tradekeyword.setTargetId(1);
		tradekeyword.setKeywords("aaa");
		tradekeyword.setType((short)0);
		tradekeyword.setStatus((short)1);
		tradekeyword.setCname("jinan");
		tradekeyword.setTitle("aaa");
		tradekeyword.setPhotoCover("");
		tradekeyword.setDetailsQuery("详细");
		tradekeyword.setAreaCode("");
		tradekeyword.setProvinceCode("111");
		tradekeyword.setPriceNum(0);
		tradekeyword.setPriceUnits("");
		tradekeyword.setMemberCode("1111");
		tradekeyword.setCreditFile(0);
		tradekeyword.setDomainTwo("");
		return tradekeyword;

	}
	
	public void clean(){
		try {
			connection.prepareStatement("delete from trade_keyword").execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Integer createdOneRecord(TradeKeyword keyword) throws SQLException{
			String sql="insert into `trade_keyword` (`cid`,`target_id`,`start`,`end`,`keywords`,`type`,`status`,"
			+"`cname`,`title`,`photo_cover`,`details_query`,`area_code`,"
			+"`province_code`,`price_num`,`price_units`,`member_code`,`gmt_register`,"
			+"`credit_file`,`domain_two`,`gmt_created`,`gmt_modified`) VALUES ("+
			+ keyword.getCid()+","+keyword.getTargetId()+",now(),now()"
			+",'"+keyword.getKeywords()+"',"+keyword.getType()+","
			+keyword.getStatus()+",'"+keyword.getCname()+"','"+keyword.getTitle()+"','"
			+keyword.getPhotoCover()+"','"+keyword.getDetailsQuery()+"','"+keyword.getAreaCode()+"','"
			+keyword.getProvinceCode()+"',"+keyword.getPriceNum()+",'"+keyword.getPriceUnits()+"','"
			+keyword.getMemberCode()+"',now(),"+keyword.getCreditFile()+",'"
			+keyword.getDomainTwo()+"',now(),now())";
		try {
			connection.createStatement().execute(sql);
			return insertResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public TradeKeyword queryTradeKeywordById(Integer id){
		String sql="select id,cid,target_id,start,end,keywords,type,status,cname,title,photo_cover,"+
				   "details_query,area_code,province_code,price_num,price_units,member_code,gmt_register,"+
				   "credit_file,domain_two,gmt_created,gmt_modified from trade_keyword where id="+id;
		try {
			ResultSet rs= connection.createStatement().executeQuery(sql);
			if(rs.next()){
				return new TradeKeyword(rs.getInt("id"), rs.getInt("cid"), rs.getInt("target_id"), rs.getDate("start"),rs.getDate("end") ,rs.getString("keywords"),
						rs.getShort("type"),rs.getShort("status"), rs.getString("cname"), rs.getString("title"), rs.getString("photo_cover"),rs.getString("details_query"), 
						rs.getString("area_code"), rs.getString("province_code"),rs.getInt("price_num"), rs.getString("price_units"), rs.getString("member_code"), 
						rs.getDate("gmt_register"), rs.getInt("credit_file"), rs.getString("domain_two"), rs.getDate("gmt_created"), rs.getDate("gmt_modified"));
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
		
	}
    
}
