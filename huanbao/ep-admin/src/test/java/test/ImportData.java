package test;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zz91.ep.domain.comp.CompProfile;
import com.zz91.util.datetime.DateUtil;

/**
 * 外网数据导入SQL文生成
 * @author root
 *
 */
public class ImportData {

	public static void main(String[] args) {
		ImportData imp = new ImportData();
		imp.impCompany();
		imp.impAccount();
		imp.impBuy();
		imp.impSupply();
	}
	
	/**
	 * 导入公司信息
	 */
	private void impCompany() {
		// 插入数据前缀
		String preSql ="INSERT INTO `comp_profile` (`id`,`name`,`details`,`industry_code`," +
				"`main_buy`,`main_product_buy`,`main_supply`,`main_product_supply`," +
				"`member_code`,`member_code_block`,`register_code`,`business_code`," +
				"`area_code`,`province_code`,`legal`,`funds`,`main_brand`,`address`," +
				"`address_zip`,`domain`,`domain_two`,`message_count`,`view_count`," +
				"`tags`,`details_query`,`gmt_created`,`gmt_modified`,`del_status`," +
				"`process_method`,`process`,`employee_num`,`developer_num`," +
				"`plant_area`,`main_market`,`main_customer`," +
				"`month_output`,`year_turnover`,`year_exports`,`" +
				"quality_control`,`register_area`,`enterprise_type`) VALUES ";
		//获取远程导数数据最大ID
		Integer maxId = 10;
		System.out.println(maxId);
		//查询本地数据库需要导入的数据
		List<CompProfile> companys = new ArrayList<CompProfile>();
		String buildSql = "(";
		//生成SQL语句，并添加到文件中
		for (CompProfile company:companys) {
			buildSql = buildSql + buildString(company.getId())+",";
			buildSql = buildSql + buildString(company.getName())+",";
			buildSql = buildSql + buildString(company.getDetails())+",";
			buildSql = buildSql + buildString(company.getIndustryCode())+",";
			buildSql = buildSql + buildString(company.getMainBuy())+",";
			buildSql = buildSql + buildString(company.getMainProductBuy())+",";
			buildSql = buildSql + buildString(company.getMainSupply())+",";
			buildSql = buildSql + buildString(company.getMainProductSupply())+",";
			buildSql = buildSql + buildString(company.getMemberCode())+",";
			buildSql = buildSql + buildString(company.getMemberCodeBlock())+",";
			buildSql = buildSql + buildString(company.getRegisterCode())+",";
			buildSql = buildSql + buildString(company.getBusinessCode())+",";
			buildSql = buildSql + buildString(company.getAreaCode())+",";
			buildSql = buildSql + buildString(company.getProvinceCode())+",";
			buildSql = buildSql + buildString(company.getLegal())+",";
			buildSql = buildSql + buildString(company.getFunds())+",";
			buildSql = buildSql + buildString(company.getMainBrand())+",";
			buildSql = buildSql + buildString(company.getAddress())+",";
			buildSql = buildSql + buildString(company.getAddressZip())+",";
			buildSql = buildSql + buildString(company.getDomain())+",";
			buildSql = buildSql + buildString(company.getDomainTwo())+",";
			buildSql = buildSql + buildString(company.getMessageCount())+",";
			buildSql = buildSql + buildString(company.getMessageCount())+",";
			buildSql = buildSql + buildString(company.getTags())+",";
			buildSql = buildSql + buildString(company.getDetailsQuery())+",";
			buildSql = buildSql + buildString(company.getGmtCreated())+",";
			buildSql = buildSql + buildString(company.getGmtModified())+",";
			buildSql = buildSql + buildString(company.getDelStatus())+",";
			buildSql = buildSql + buildString(company.getProcessMethod())+",";
			buildSql = buildSql + buildString(company.getProcess())+",";
			buildSql = buildSql + buildString(company.getEmployeeNum())+",";
			buildSql = buildSql + buildString(company.getDeveloperNum())+",";
			buildSql = buildSql + buildString(company.getPlantArea())+",";
			buildSql = buildSql + buildString(company.getMainMarket())+",";
			buildSql = buildSql + buildString(company.getMainCustomer())+",";
			buildSql = buildSql + buildString(company.getMonthOutput())+",";
			buildSql = buildSql + buildString(company.getYearTurnover())+",";
			buildSql = buildSql + buildString(company.getYearExports())+",";
			buildSql = buildSql + buildString(company.getQualityControl())+",";
			buildSql = buildSql + buildString(company.getRegisterArea())+",";
			buildSql = buildSql + buildString(company.getEnterpriseType())+ ");";
			outfile("/root/sql/comp_profile.sql", preSql+buildSql);
		}
	}
	
	/**
	 * 导入帐号信息
	 */
	private void impAccount() {
		String insertSql ="";
		outfile("/root/sql/comp_account.sql", insertSql);
	}
	
	/**
	 * 导入供应信息
	 */
	private void impSupply() {
		
	}
	
	/**
	 * 导入求购信息
	 */
	private void impBuy() {
		
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
			return "'"+feild+"'";
		}
	}
	
	public String buildString(Date feild) {
		if (feild == null) {
			return "NULL";
		} else {
			try {
				return "'"+DateUtil.getDate(feild, "yyyy-MM-dd HH:mm:ss")+"'";
			} catch (ParseException e) {
				return "now()";
			}
		}
	}

	/**
	 * 将内容到输出文件
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