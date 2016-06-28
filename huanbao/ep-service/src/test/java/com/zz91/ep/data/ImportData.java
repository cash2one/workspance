package com.zz91.ep.data;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpException;
import org.junit.Test;

import com.zz91.ep.domain.comp.CompAccount;
import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.ep.domain.trade.TradeBuy;
import com.zz91.ep.domain.trade.TradeSupply;
import com.zz91.ep.service.comp.CompAccountService;
import com.zz91.ep.service.comp.CompProfileService;
import com.zz91.ep.service.trade.TradeBuyService;
import com.zz91.ep.service.trade.TradeSupplyService;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.http.HttpUtils;

public class ImportData extends BaseServiceTestCase {

	private final String DEFAUTL_ENCODING = "UTF-8";
	
	private final String API_URL_COMPANY = "http://huanbaoadmin.zz91.com:8081/ep-admin/api/maxCompanyId.htm";
	
	private final String API_URL_ACCOUNT = "http://huanbaoadmin.zz91.com:8081/ep-admin/api/maxAccountId.htm";
	
	private final String API_URL_SUPPLY = "http://huanbaoadmin.zz91.com:8081/ep-admin/api/maxSupplyId.htm";
	
	private final String API_URL_BUY = "http://huanbaoadmin.zz91.com:8081/ep-admin/api/maxBuyId.htm";
	
	@Resource
	private CompProfileService compProfileService;
	
	@Resource
	private CompAccountService compAccountService;
	
	@Resource
	private TradeSupplyService tradeSupplyService;
	
	@Resource
	private TradeBuyService tradeBuyService;

	@Test
	public void testCompanyImport() throws Exception {
		impCompany();
	}
	
	@Test
	public void testAccountImport() throws Exception {
		impAccount();
	}
	
	@Test
	public void testSupplyImport() throws Exception {
		//impSupply();
	}
	
	@Test
	public void testBuyImport() throws Exception {
		impBuy();
	}

	/**
	 * 导入公司信息
	 * @throws IOException 
	 * @throws HttpException 
	 */
	private void impCompany() throws HttpException, IOException {
		// 插入数据前缀
		String preSql = "INSERT INTO `ep`.`comp_profile` (`id`,`name`,`details`,`industry_code`,"
				+ "`main_buy`,`main_product_buy`,`main_supply`,`main_product_supply`,"
				+ "`member_code`,`member_code_block`,`register_code`,`business_code`,"
				+ "`area_code`,`province_code`,`legal`,`funds`,`main_brand`,`address`,"
				+ "`address_zip`,`domain`,`domain_two`,`message_count`,`view_count`,"
				+ "`tags`,`details_query`,`gmt_created`,`gmt_modified`,`del_status`,"
				+ "`process_method`,`process`,`employee_num`,`developer_num`,"
				+ "`plant_area`,`main_market`,`main_customer`,"
				+ "`month_output`,`year_turnover`,`year_exports`,`"
				+ "quality_control`,`register_area`,`enterprise_type`) VALUES ";
		// 获取远程导数数据最大ID
		String responseText = HttpUtils.getInstance().httpGet(API_URL_COMPANY, DEFAUTL_ENCODING);
		JSONObject object = JSONObject.fromObject(responseText);
		Integer maxId = Integer.valueOf(object.getString("maxid"));
		// 查询本地数据库需要导入的数据
		List<CompProfile> companys = compProfileService.queryImpCompProfile(maxId);
		// 生成SQL语句，并添加到文件中
		for (CompProfile company : companys) {
			String buildSql = "(";
			buildSql = buildSql + buildString(company.getId()) + ",";
			buildSql = buildSql + buildString(company.getName()) + ",";
			buildSql = buildSql + buildString(company.getDetails()) + ",";
			buildSql = buildSql + buildString(company.getIndustryCode()) + ",";
			buildSql = buildSql + buildString(company.getMainBuy()) + ",";
			buildSql = buildSql + buildString(company.getMainProductBuy()) + ",";
			buildSql = buildSql + buildString(company.getMainSupply()) + ",";
			buildSql = buildSql + buildString(company.getMainProductSupply()) + ",";
			buildSql = buildSql + buildString(company.getMemberCode()) + ",";
			buildSql = buildSql + buildString(company.getMemberCodeBlock()) + ",";
			buildSql = buildSql + buildString(company.getRegisterCode()) + ",";
			buildSql = buildSql + buildString(company.getBusinessCode()) + ",";
			buildSql = buildSql + buildString(company.getAreaCode()) + ",";
			buildSql = buildSql + buildString(company.getProvinceCode()) + ",";
			buildSql = buildSql + buildString(company.getLegal()) + ",";
			buildSql = buildSql + buildString(company.getFunds()) + ",";
			buildSql = buildSql + buildString(company.getMainBrand()) + ",";
			buildSql = buildSql + buildString(company.getAddress()) + ",";
			buildSql = buildSql + buildString(company.getAddressZip()) + ",";
			buildSql = buildSql + buildString(company.getDomain()) + ",";
			buildSql = buildSql + buildString(company.getDomainTwo()) + ",";
			buildSql = buildSql + buildString(company.getMessageCount()) + ",";
			buildSql = buildSql + buildString(company.getMessageCount()) + ",";
			buildSql = buildSql + buildString(company.getTags()) + ",";
			buildSql = buildSql + buildString(company.getDetailsQuery()) + ",";
			buildSql = buildSql + buildString(company.getGmtCreated()) + ",";
			buildSql = buildSql + buildString(company.getGmtModified()) + ",";
			buildSql = buildSql + buildString(company.getDelStatus()) + ",";
			buildSql = buildSql + buildString(company.getProcessMethod()) + ",";
			buildSql = buildSql + buildString(company.getProcess()) + ",";
			buildSql = buildSql + buildString(company.getEmployeeNum()) + ",";
			buildSql = buildSql + buildString(company.getDeveloperNum()) + ",";
			buildSql = buildSql + buildString(company.getPlantArea()) + ",";
			buildSql = buildSql + buildString(company.getMainMarket()) + ",";
			buildSql = buildSql + buildString(company.getMainCustomer()) + ",";
			buildSql = buildSql + buildString(company.getMonthOutput()) + ",";
			buildSql = buildSql + buildString(company.getYearTurnover()) + ",";
			buildSql = buildSql + buildString(company.getYearExports()) + ",";
			buildSql = buildSql + buildString(company.getQualityControl())+ ",";
			buildSql = buildSql + buildString(company.getRegisterArea()) + ",";
			buildSql = buildSql + buildString(company.getEnterpriseType()) + ");";
			outfile("/root/桌面/comp_profile.sql", preSql + buildSql);
		}
	}

	/**
	 * 导入帐号信息
	 * @throws IOException 
	 * @throws HttpException 
	 */
	private void impAccount() throws HttpException, IOException {
		// 插入数据前缀
		String preSql = "INSERT INTO `ep`.`comp_account` (`id`,`cid`,`account`,`email`,`password`," +
				"`password_clear`,`name`,`sex`,`mobile`,`phone_country`,`phone_area`," +
				"`phone`,`fax_country`,`fax_area`,`fax`,`dept`,`contact`,`position`," +
				"`login_count`,`login_ip`,`gmt_login`,`gmt_register`,`gmt_created`,`gmt_modified`) VALUES ";
		// 获取远程导数数据最大ID
		String responseText = HttpUtils.getInstance().httpGet(API_URL_ACCOUNT, DEFAUTL_ENCODING);
		JSONObject object = JSONObject.fromObject(responseText);
		Integer maxId = Integer.valueOf(object.getString("maxid"));
		// 查询本地数据库需要导入的数据
		List<CompAccount> accounts = compAccountService.queryImpCompAccount(maxId);
		// 生成SQL语句，并添加到文件中
		for (CompAccount account : accounts) {
			String buildSql = "(";
			buildSql = buildSql + buildString(account.getId()) + ",";
			buildSql = buildSql + buildString(account.getCid()) + ",";
			buildSql = buildSql + buildString(account.getAccount()) + ",";
			buildSql = buildSql + buildString(account.getEmail()) + ",";
			buildSql = buildSql + buildString(account.getPassword()) + ",";
			buildSql = buildSql + buildString(account.getPasswordClear()) + ",";
			buildSql = buildSql + buildString(account.getName()) + ",";
			buildSql = buildSql + buildString(account.getSex()) + ",";
			buildSql = buildSql + buildString(account.getMobile()) + ",";
			buildSql = buildSql + buildString(account.getPhoneCountry()) + ",";
			buildSql = buildSql + buildString(account.getPhoneArea()) + ",";
			buildSql = buildSql + buildString(account.getPhone()) + ",";
			buildSql = buildSql + buildString(account.getFaxCountry()) + ",";
			buildSql = buildSql + buildString(account.getFaxArea()) + ",";
			buildSql = buildSql + buildString(account.getFax()) + ",";
			buildSql = buildSql + buildString(account.getDept()) + ",";
			buildSql = buildSql + buildString(account.getContact()) + ",";
			buildSql = buildSql + buildString(account.getPosition()) + ",";
			buildSql = buildSql + buildString(account.getLoginCount()) + ",";
			buildSql = buildSql + buildString(account.getLoginIp()) + ",";
			buildSql = buildSql + buildString(account.getGmtLogin()) + ",";
			buildSql = buildSql + buildString(account.getGmtRegister()) + ",";
			buildSql = buildSql + buildString(account.getGmtCreated()) + ",";
			buildSql = buildSql + buildString(account.getGmtModified()) + ");";
			outfile("/root/桌面/comp_account.sql", preSql + buildSql);
		}
	}

	/**
	 * 导入供应信息
	 * @throws IOException 
	 * @throws HttpException 
	 */
	private void impSupply() throws HttpException, IOException {
		// 插入数据前缀
		String preSql = "INSERT INTO `ep`.`trade_supply` (`id`,`uid`,`cid`,`title`," +
				"`details`,`category_code`,`group_id`,`photo_cover`,`province_code`," +
				"`area_code`,`total_num`,`total_units`,`price_num`,`price_units`,`price_from`," +
				"`price_to`,`use_to`,`used_product`,`tags`,`tags_sys`,`details_query`,`property_query`," +
				"`message_count`,`view_count`,`favorite_count`,`plus_count`,`html_path`,`integrity`,`gmt_publish`," +
				"`gmt_refresh`,`valid_days`,`gmt_expired`,`del_status`,`pause_status`," +
				"`check_status`,`check_admin`,`check_refuse`,`gmt_check`,`gmt_created`,`gmt_modified`,`info_come_from`) VALUES ";
		// 获取远程导数数据最大ID
		String responseText = HttpUtils.getInstance().httpGet(API_URL_SUPPLY, DEFAUTL_ENCODING);
		JSONObject object = JSONObject.fromObject(responseText);
		Integer maxId = Integer.valueOf(object.getString("maxid"));
		// 更新数据显示时间（5分钟后，1秒添加一条数据，solr5分钟更新一次，相当于一次更新300条信息）
		//update trade_supply set gmt_modified = DATE_ADD(now(),INTERVAL (id - maxid + 300) SECOND) where id > maxid
		tradeSupplyService.updateImpTradeSupply(maxId);
		// 查询本地数据库需要导入的数据
		List<TradeSupply> supplys = tradeSupplyService.queryImpTradeSupply(maxId);
		// 生成SQL语句，并添加到文件中
		for (TradeSupply supply : supplys) {
			String buildSql = "(";
			buildSql = buildSql + buildString(supply.getId()) + ",";
			buildSql = buildSql + buildString(supply.getUid()) + ",";
			buildSql = buildSql + buildString(supply.getCid()) + ",";
			buildSql = buildSql + buildString(supply.getTitle()) + ",";
			buildSql = buildSql + buildString(supply.getDetails()) + ",";
			buildSql = buildSql + buildString(supply.getCategoryCode()) + ",";
			buildSql = buildSql + buildString(supply.getGroupId()) + ",";
			buildSql = buildSql + buildString(supply.getPhotoCover()) + ",";
			buildSql = buildSql + buildString(supply.getProvinceCode()) + ",";
			buildSql = buildSql + buildString(supply.getAreaCode()) + ",";
			buildSql = buildSql + buildString(supply.getTotalNum()) + ",";
			buildSql = buildSql + buildString(supply.getTotalUnits()) + ",";
			buildSql = buildSql + buildString(supply.getPriceNum()) + ",";
			buildSql = buildSql + buildString(supply.getPriceUnits()) + ",";
			buildSql = buildSql + buildString(supply.getPriceFrom()) + ",";
			buildSql = buildSql + buildString(supply.getPriceTo()) + ",";
			buildSql = buildSql + buildString(supply.getUseTo()) + ",";
			buildSql = buildSql + buildString(supply.getUsedProduct()) + ",";
			buildSql = buildSql + buildString(supply.getTags()) + ",";
			buildSql = buildSql + buildString(supply.getTagsSys()) + ",";
			buildSql = buildSql + buildString(supply.getDetailsQuery()) + ",";
			buildSql = buildSql + buildString(supply.getPropertyQuery()) + ",";
			buildSql = buildSql + buildString(supply.getMessageCount()) + ",";
			buildSql = buildSql + buildString(supply.getViewCount()) + ",";
			buildSql = buildSql + buildString(supply.getFavoriteCount()) + ",";
			buildSql = buildSql + buildString(supply.getPlusCount()) + ",";
			buildSql = buildSql + buildString(supply.getHtmlPath()) + ",";
			buildSql = buildSql + buildString(supply.getIntegrity()) + ",";
			buildSql = buildSql + buildString(supply.getGmtPublish()) + ",";
			buildSql = buildSql + buildString(supply.getGmtRefresh()) + ",";
			buildSql = buildSql + buildString(supply.getValidDays()) + ",";
			buildSql = buildSql + buildString(supply.getGmtExpired()) + ",";
			buildSql = buildSql + buildString(supply.getDelStatus()) + ",";
			buildSql = buildSql + buildString(supply.getPauseStatus()) + ",";
			buildSql = buildSql + buildString(supply.getCheckStatus()) + ",";
			buildSql = buildSql + buildString(supply.getCheckAdmin()) + ",";
			buildSql = buildSql + buildString(supply.getCheckRefuse()) + ",";
			buildSql = buildSql + buildString(supply.getGmtCheck()) + ",";
			buildSql = buildSql + buildString(supply.getGmtCreated()) + ",";
			buildSql = buildSql + buildString(supply.getGmtModified()) + ",";
			buildSql = buildSql + buildString(supply.getInfoComeFrom()) + ");";
			outfile("/root/桌面/trade_supply.sql", preSql + buildSql);
		}
	}

	/**
	 * 导入求购信息
	 * @throws IOException 
	 * @throws HttpException 
	 */
	private void impBuy() throws HttpException, IOException {
		// 插入数据前缀
		String preSql = "INSERT INTO `ep`.`trade_buy` (`id`,`uid`,`cid`,`title`," +
				"`details`,`category_code`,`photo_cover`,`province_code`,`area_code`," +
				"`buy_type`,`quantity`,`quantity_year`,`quantity_untis`,`supply_area_code`," +
				"`use_to`,`gmt_confirm`,`gmt_receive`,`gmt_publish`,`gmt_refresh`,`valid_days`," +
				"`gmt_expired`,`tags_sys`,`details_query`,`message_count`,`view_count`,`favorite_count`," +
				"`plus_count`,`html_path`,`del_status`,`pause_status`,`check_status`,`check_admin`," +
				"`check_refuse`,`gmt_check`,`gmt_created`,`gmt_modified`) VALUES ";
		// 获取远程导数数据最大ID
		String responseText = HttpUtils.getInstance().httpGet(API_URL_BUY, DEFAUTL_ENCODING);
		JSONObject object = JSONObject.fromObject(responseText);
		Integer maxId = Integer.valueOf(object.getString("maxid"));
		// 更新数据显示时间（5分钟后，2秒添加一条数据，solr 10分钟更新一次，相当于一次更新300条信息）
		//update trade_buy set gmt_modified = DATE_ADD(now(),INTERVAL ((id - maxid)*2 + 300) SECOND) where id > maxid
		tradeBuyService.updateImpTradeBuy(maxId);
		// 查询本地数据库需要导入的数据
		List<TradeBuy> buys = tradeBuyService.queryImpTradeBuy(maxId);
		// 生成SQL语句，并添加到文件中
		for (TradeBuy buy : buys) {
			String buildSql = "(";
			buildSql = buildSql + buildString(buy.getId()) + ",";
			buildSql = buildSql + buildString(buy.getUid()) + ",";
			buildSql = buildSql + buildString(buy.getCid()) + ",";
			buildSql = buildSql + buildString(buy.getTitle()) + ",";
			buildSql = buildSql + buildString(buy.getDetails()) + ",";
			buildSql = buildSql + buildString(buy.getCategoryCode()) + ",";
			buildSql = buildSql + buildString(buy.getPhotoCover()) + ",";
			buildSql = buildSql + buildString(buy.getProvinceCode()) + ",";
			buildSql = buildSql + buildString(buy.getAreaCode()) + ",";
			buildSql = buildSql + buildString(buy.getBuyType()) + ",";
			buildSql = buildSql + buildString(buy.getQuantity()) + ",";
			buildSql = buildSql + buildString(buy.getQuantityYear()) + ",";
			buildSql = buildSql + buildString(buy.getQuantityUntis()) + ",";
			buildSql = buildSql + buildString(buy.getSupplyAreaCode()) + ",";
			buildSql = buildSql + buildString(buy.getUseTo()) + ",";
			buildSql = buildSql + buildString(buy.getGmtConfirm()) + ",";
			buildSql = buildSql + buildString(buy.getGmtReceive()) + ",";
			buildSql = buildSql + buildString(buy.getGmtPublish()) + ",";
			buildSql = buildSql + buildString(buy.getGmtRefresh()) + ",";
			buildSql = buildSql + buildString(buy.getValidDays()) + ",";
			buildSql = buildSql + buildString(buy.getGmtExpired()) + ",";
			buildSql = buildSql + buildString(buy.getTagsSys()) + ",";
			buildSql = buildSql + buildString(buy.getDetailsQuery()) + ",";
			buildSql = buildSql + buildString(buy.getMessageCount()) + ",";
			buildSql = buildSql + buildString(buy.getViewCount()) + ",";
			buildSql = buildSql + buildString(buy.getFavoriteCount()) + ",";
			buildSql = buildSql + buildString(buy.getPlusCount()) + ",";
			buildSql = buildSql + buildString(buy.getHtmlPath()) + ",";
			buildSql = buildSql + buildString(buy.getDelStatus()) + ",";
			buildSql = buildSql + buildString(buy.getPauseStatus()) + ",";
			buildSql = buildSql + buildString(buy.getCheckStatus()) + ",";
			buildSql = buildSql + buildString(buy.getCheckAdmin()) + ",";
			buildSql = buildSql + buildString(buy.getCheckRefuse()) + ",";
			buildSql = buildSql + buildString(buy.getGmtCheck()) + ",";
			buildSql = buildSql + buildString(buy.getGmtCreated()) + ",";
			buildSql = buildSql + buildString(buy.getGmtModified()) + ");";
			outfile("/root/桌面/trade_buy.sql", preSql + buildSql);
		}
	}

	public String buildString(Short feild) {
		if (feild == null) {
			return "0";
		} else {
			return String.valueOf(feild);
		}
	}

	public String buildString(Integer feild) {
		if (feild == null) {
			return "0";
		} else {
			return String.valueOf(feild);
		}
	}

	public String buildString(String feild) {
		if (feild == null) {
			return "NULL";
		} else {
			return "'" + feild.replace("'", "''") + "'";
		}
	}
	
	public String buildString(Date feild) {
		if (feild == null) {
			return "NULL";
		} else {
				return "'" + DateUtil.toString(feild, "yyyy-MM-dd HH:mm:ss") + "'";
		}
	}

	/**
	 * 将内容到输出文件
	 * 
	 * @param filename
	 * @param sql
	 */
	private void outfile(String filename, String sql) {
		FileWriter fw = null;
		try {
			// 第二个参数 true 表示写入方式是追加方式
			fw = new FileWriter(filename, true);
			fw.write(sql + "\r\n");
		} catch (Exception e) {
			System.out.println("书写SQL发生错误：" + e.toString());
		} finally {
			try {
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
