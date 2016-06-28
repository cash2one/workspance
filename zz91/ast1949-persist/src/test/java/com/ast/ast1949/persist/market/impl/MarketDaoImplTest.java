package com.ast.ast1949.persist.market.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import com.ast.ast1949.persist.BasePersistTestCase;
import com.ast.ast1949.persist.market.MarketDao;

public class MarketDaoImplTest extends BasePersistTestCase{
	@Resource
	private MarketDao marketDao;
	
//	public void test_insert() throws Exception{
//		Market market=new Market();
//		market.setName("安徽界首光武塑料市场");
//		market.setArea("安徽 阜阳");
//		market.setIndustry("废塑料");
//		market.setCategory("废塑料加工设备");
//		market.setBusiness("塑料再生机械、粉碎机、洗料机");
//		market.setIntroduction("界首市光武镇塑料机械加工厂是塑料再生机械、粉碎机、洗料机、上料机、甩干机、烘干机、聚酯清洗机、打包机、造粒机、切粒机、沉料清洗分离机、磨刀机、粉碎清洗机、提料机、鞋底洗料机、脱水机、粉碎清洗上料机、漂料沉料分离机、挤出造粒机等产品专业生产加工的个体经营,公司总部设在安徽省界首市光武镇万福南路18号,界首市光武镇塑料机械加工厂拥有完整、科学的质量管理体系。界首市光武镇塑料机械加工厂的诚信、实力和产品质量获得业界的认可。欢迎各界朋友莅临界首市光武镇塑料机械加工厂参观、指导和业务洽谈。");
//		market.setAddress("安徽省阜阳市界首市光武镇");
//		Integer i=marketDao.insertMarket(market);
//		Integer j=insertResult();
//		assertEquals(i, j);
//	}
	public void test_count(){
		Integer i=marketDao.countMarketByCondition("浙江", null, null);
		Integer j=0;
		assertEquals(i, j);
	}
	@Override
	public int insertResult() {
		try {
			ResultSet rs = connection.createStatement().executeQuery(
					"select id from market order by id desc limit 1");
			if (rs.next()) {
				
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
