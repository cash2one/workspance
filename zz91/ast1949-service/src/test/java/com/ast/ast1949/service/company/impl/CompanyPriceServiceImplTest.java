package com.ast.ast1949.service.company.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.ast.ast1949.domain.company.CompanyPriceDO;
import com.ast.ast1949.service.BaseServiceTestCase;
import com.ast.ast1949.service.company.CompanyPriceService;

public class CompanyPriceServiceImplTest extends BaseServiceTestCase{
	@Resource
	CompanyPriceService companyPriceService;
	
	public void test_queryNewestCompPrice(){
		clean();
		init("7134", "account", "1", "title", "details");
		List<CompanyPriceDO> list =companyPriceService.queryNewestVipCompPrice(null, 10);
		assertTrue(list.size()>0);
	}
	
	public void clean(){
		try {
			connection.prepareStatement("delete from company_price").execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void init(String companyId,String account,String productId,String title,String details){
		String sql = "INSERT INTO company_price(company_id,account,product_id,title,details,is_checked,post_time,expired_time,gmt_created,gmt_modified,check_time,refresh_time) VALUES("
		+companyId+","+account+","+productId+","+title+","+details+",1,now(),now(),now(),now(),now(),now())";
		try {
			connection.prepareStatement(sql).execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
