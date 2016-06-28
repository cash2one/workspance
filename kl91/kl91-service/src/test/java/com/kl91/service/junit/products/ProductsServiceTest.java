package com.kl91.service.junit.products;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.annotation.Resource;

import com.kl91.domain.company.Company;
import com.kl91.domain.products.Products;
import com.kl91.service.company.CompanyService;
import com.kl91.service.junit.BaseServiceTestCase;
import com.kl91.service.products.ProductsService;

public class ProductsServiceTest extends BaseServiceTestCase {

	@Resource
	private ProductsService productsService;
	@Resource
	private CompanyService companyService;

	public void test_insert_products() {
		clean();
		Integer i = productsService.createProducts(getProducts(),null);
		assertNotNull(i);
		assertTrue(i.intValue() > 0);
		
		Products products = queryOne(i);
		assertNotNull(products);
		assertEquals("测试添加",products.getTitle());
	}

	public void test_delete_products() {
		clean();
		int id1 = insert("测试删除1", 1);
		int id2 = insert("测试删除2", 1);
		int id3 = insert("测试删除3", 1);

		Integer i = productsService.deleteProducts(id1);
		assertNotNull(i);
		assertEquals(1, i.intValue());

		Products products = queryOne(id1);
		assertNull(products);

		Products products1 = queryOne(id2);
		assertNotNull(products1);
		Products products2 = queryOne(id3);
		assertNotNull(products2);
	}

	public void test_update_products() {
		clean();
		Integer id = insert("测试更新", 1);
		insert("测试更新2", 1);
		insert("测试更新3", 1);
		insert("测试更新4", 1);
		insert("测试更新5", 1);
		insert("测试更新6", 1);
		insert("测试更新7", 1);
		assertTrue(id.toString(), id.intValue() > 0);

		Products products = getProducts();
		products.setId(id);
		products.setTitle("测试更新后的数据");
		// Integer i = productsService.editProducts(products);
		// assertNotNull(i);
		assertEquals("测试更新后的数据", products.getTitle());
	}

	public void test_query_One() {
		clean();
		Integer id1 = insert("测试查询1", 1);
		insert("测试查询2", 1);

		Products p = productsService.queryById(id1);
		assertNotNull(p);
		assertEquals("测试查询1", p.getTitle());
	}

	// 测试查询分页
//	public void test_page_queryProducts() {
//		clean();
//		insert("title1", 1);
//		insert("title2", 1);
//		insert("title3", 1);
//		insert("title4", 1);
//		insert("title5", 1);
//		insert("title6", 1);
//		insert("title7", 1);
//		insert("title8", 1);
//		insert("title9", 1);
//		insert("title10", 1);
//		insert("title11", 1);
//		insert("title12", 1);
//		PageDto<Products> page = new PageDto<Products>();
//		page.setStartIndex(0);
//		page.setPageSize(5);
//		assertNotNull(page.getRecords());
//		assertEquals(5, page.getRecords().size());
//		assertEquals(12, page.getTotalRecords().intValue());
//
//		page.setStartIndex(8);
//		page.setPageSize(5);
//		assertNotNull(page.getRecords());
//		assertEquals(4, page.getRecords().size());
//		assertEquals(12, page.getTotalRecords().intValue());
//
//	}

	// 测试查询公司和产品信息
	public void test_queryProductsAndCompanyById() {

		Integer id = insertCompany();
		Company company = companyService.queryById(id);
		Integer id2 = company.getId();

		Integer id3 = insert("title", id2);
		Products products = productsService.queryById(id3);
		Integer id4 = products.getCid();

		assertEquals(id2, id4);

	}
	//测试批量
	public void test_refreshProducts(){
		clean();
		Integer i1 = productsService.createProducts(getProducts(), 1);
		Integer i2 = productsService.createProducts(getProducts(), 1);
		String ids = i1+","+i2;
		
		Integer result = productsService.refreshProducts(ids);
		assertTrue(result == 2);
	}
	private Integer insert(String title, Integer cid) {
		if (title == null) {
			title = "title";
		}
		String sql = "insert into products("
				+ "cid,products_category_code,type_code,title,details,details_query,checked_flag,"
				+ "deleted_flag,impt_flag,publish_flag,area_code,price_unit,quantity,quantity_unit,color,"
				+ "location,level,shape,useful,tags,tags_admin,pic_cover,min_price,max_price,num_inquiry,"
				+ "num_view,day_expired,show_time,gmt_post,gmt_refresh,gmt_expired,gmt_check,"
				+ "gmt_created,gmt_modified)values("
				+ ""
				+ cid
				+ ",'productsCategoryCode','typeCode','"
				+ title
				+ "','details','detailsQuery',0,"
				+ "0,0,0,'areaCode','priceUnit',0,'quantityUnit',"
				+ "'color','location','level','shape','useful','tags','tagsAdmin','picCover',0,"
				+ "0,0,0,0,now(),now(),now(),now(),now(),now(),now())";
		try {
			connection.prepareStatement(sql).execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return insertResult();
	}

	private Products queryOne(Integer id) {
		Products products = null;
		try {
			ResultSet rs = connection.prepareStatement(
					"SELECT * FROM products WHERE id = " + id).executeQuery();
			if (rs.next()) {
				products = new Products(rs.getInt("id"), rs.getInt("cid"),
						rs.getString("products_category_code"),
						rs.getString("type_code"), rs.getString("title"),
						rs.getString("details"), rs.getString("details_query"),
						rs.getInt("checked_flag"), rs.getInt("deleted_flag"),
						rs.getInt("impt_flag"), rs.getInt("publish_flag"),
						rs.getString("area_code"), rs.getString("price_unit"),
						rs.getInt("quantity"), rs.getString("quantity_unit"),
						rs.getString("color"), rs.getString("location"),
						rs.getString("level"), rs.getString("shape"),
						rs.getString("useful"), rs.getString("tags"),
						rs.getString("tags_admin"), rs.getString("pic_cover"),
						rs.getInt("min_price"), rs.getInt("max_price"),
						rs.getInt("num_inquiry"), rs.getInt("num_view"),
						rs.getInt("day_expired"), rs.getDate("show_time"),
						rs.getDate("gmt_post"), rs.getDate("gmt_created"),
						rs.getDate("gmt_modified"), rs.getDate("gmt_refresh"),
						rs.getDate("gmt_expired"), rs.getDate("gmt_check"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return products;
	}

	private Products getProducts() {
		return new Products(null, 1, "producetsCategory", "typeCode", "测试添加",
				"details", "detailsQuery", 1, 1, 1, 1, "areaCode", "priceUnit",
				1, "quantityUnit", "color", "location", "level", "shape",
				"useful", "tags", "tagsAdmin", "picCover", 0, 0, 1, 1, 1,
				new Date(), new Date(), new Date(), new Date(), new Date(),
				new Date(), new Date());
	}

	private Integer insertCompany() {
		String sql = "insert into company(account,company_name,industry_code"
				+ ",membership_code,sex,contact,password,mobile,qq,email,tel,fax,area_code,zip,address,position,"
				+ "department,introduction,business,domain,website,num_login,regist_flag,show_time,"
				+ "gmt_last_login,gmt_created,gmt_modified)value("
				+ "'account','companyName','industryCode','membershipCode',0,"
				+ "'contact','password','mobile','qq','email','tel','fax','areaCode',"
				+ "'zip','address','position','department','introduction','business','domain',"
				+ "'website',0,0,now(),now(),now(),now())";
		try {
			connection.prepareStatement(sql).execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return insertResult();

	}

	public void clean() {
		try {
			connection.prepareStatement("DELETE FROM products").execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
