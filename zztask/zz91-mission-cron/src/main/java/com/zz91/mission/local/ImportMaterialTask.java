package com.zz91.mission.local;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.zz91.task.common.ZZTask;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;
import com.zz91.util.file.FileUtils;
import com.zz91.util.lang.StringUtils;

public class ImportMaterialTask implements ZZTask{	

	final static Map<String , String > CATEGORYMAP = new HashMap<String, String>();
	final static Map<String , String > AREA_MAP = new HashMap<String, String>();
	final static Map<String , String > YUANLIAO_MAP = new HashMap<String, String>();
	final static Map<String , String > YUANLIAO_PROPERTIES_MAP = new HashMap<String, String>();
	final static Map<String, Integer> SALES_MODE_MAP = new HashMap<String, Integer>();
	static{
		SALES_MODE_MAP.put("品牌销售", 0);
		SALES_MODE_MAP.put("自产销售", 1);
	}
	final static Map<String, Integer> EXPIRED_MAP = new HashMap<String, Integer>();
	static {
		EXPIRED_MAP.put("长期有效", 365);
		EXPIRED_MAP.put("3个月", 90);
		EXPIRED_MAP.put("1个月", 30);
		EXPIRED_MAP.put("20天", 20);
		EXPIRED_MAP.put("10天", 10);
	}
	
	final static String DB = "ast";
	
	static Integer ADDRESS_W = 0;
	static Integer ADDRESS_Q = 0;
	static Integer ADDRESS_B = 0;
	
	final static String RESOURCE_ADD = "/mnt/data/resources/yuanliao";
	
	final static String TABLE = "PVC";
	
	@Override
	public boolean init() throws Exception {
		return false;
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		
		// 获取 excel 数据
		File file = new File("/home/sj/1.xls");
		String[][] result = getData(file, 1);  // result 为数据内容
		int rowLength = result.length;
		List<Map<String,Object>> companyList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < rowLength; i++) {
			// 组装公司信息list
			Map<String, Object> companyMap = new HashMap<String, Object>();
			Map<String, String> companyAccountMap = new HashMap<String, String>();
			companyMap.put("name", result[i][18]); //name
			companyMap.put("address", result[i][22]); //address
			companyMap.put("business", result[i][23]); //主营业务
			companyMap.put("introduction", result[i][24]); //公司简介
			if (StringUtils.isNotEmpty(result[i][25]) ) {
				companyMap.put("areaCode", result[i][25]); //地区编号
			}else{
				companyMap.put("areaCode", "中国"); //地区编号
			}
			companyAccountMap.put("contact", result[i][19]); // 联系人
			companyAccountMap.put("mobile", result[i][20]); // 手机
			companyAccountMap.put("tel", result[i][21]); // 电话
			companyMap.put("companyAccountMap", companyAccountMap);
			
			// 原料信息list
			Map<String, Object> materialMap = new HashMap<String, Object>();
			materialMap.put("title", result[i][0]); // 标题
			materialMap.put("categoryYuanliaoCode", result[i][1]); // 类别中文名
			if (StringUtils.isNumber(result[i][2])) {
				materialMap.put("price", result[i][2]); // 价格
			}else{
				materialMap.put("price", 0); // 价格
			}
			materialMap.put("priceUnit", result[i][3]); // 价格单位
			if (StringUtils.isNumber(result[i][4])) {
				materialMap.put("quantity", result[i][4]); // 数量
			}else{
				materialMap.put("quantity", 0); // 数量
			}
			materialMap.put("unit", result[i][5]); // 数量单位
			materialMap.put("location", result[i][6]); // 现货所在地
			materialMap.put("sendTime", result[i][7]); // 发货时间
			materialMap.put("postTime", result[i][8]); // 发布时间
			if (EXPIRED_MAP.get(result[i][9])!=null) {
				materialMap.put("expireTime", EXPIRED_MAP.get(result[i][9])); // 过期时间
			}else{
				materialMap.put("expireTime", 0); // 过期时间
			}
			materialMap.put("categoryMainDesc", result[i][10]); // 厂家
			materialMap.put("tradeMark", result[i][11]); // 品牌号
			materialMap.put("type", result[i][12]); //产品类型
			if (StringUtils.isEmpty(result[i][13])) {
				materialMap.put("salesMode", "品牌销售");
			}else{
				materialMap.put("salesMode", result[i][13]); // 0品牌销售；1自产销售
			}
			materialMap.put("processLevel", result[i][14]); // 加工级别
			materialMap.put("charaLevel", result[i][15]); // 特性级别
			materialMap.put("usefulLevel", result[i][16]); // 用途级别
			materialMap.put("description", result[i][17]); // 信息描述
			if (StringUtils.isEmpty(result[i][26])) {
				materialMap.put("pic", ""); // 图片名
			}else{
				materialMap.put("pic", result[i][26]); // 图片名
			}
			companyMap.put("materialMap", materialMap);
			
			companyList.add(companyMap);
		}
		
		// 生成公司 公司账户 登录账户  导入原料 和 图片
		initCompany(companyList);
		
		return true;
	}
	
	@SuppressWarnings("unchecked")
	private void insertMaterial(Integer companyId,String account,Map<String,Object> companyMap){
		Map<String, Object> materialMap = (Map<String, Object>) companyMap.get("materialMap");
		//类型都是 供应 10331000
		String sql = "INSERT INTO `yuanliao`"
				+ "("
				+ "`company_id`,"
				+ "`account`,"
				+ "`title`,"
				+ "`tags`,"
				+ "`sales_mode`,"
				+ "`category_yuanliao_code`,"
				+ "`category_main_desc`,"
				+ "`yuanliao_type_code`,"
				+ "`trade_mark`,"
				+ "`process_level`,"
				+ "`chara_level`,"
				+ "`useful_level`,"
				+ "`type`,"
				+ "`quantity`,"
				+ "`unit`,"
				+ "`price`,"
				+ "`price_unit`,"
				+ "`location`,"
				+ "`description`,"
				+ "`send_time`,"
				+ "`check_status`,"
				+ "`is_del`,"
				+ "`is_pause`,"
				+ "`check_person`,"
				+ "`post_time`,"
				+ "`check_time`,"
				+ "`expire_time`,"
				+ "`refresh_time`,"
				+ "`gmt_created`,"
				+ "`gmt_modified`"
				+ ")"
				+ "VALUES"
				+ "("
				+ companyId+",'"
				+ account + "','"
				+ materialMap.get("title")+"','"
				+ materialMap.get("categoryYuanliaoCode") + "',"
				+ SALES_MODE_MAP.get(materialMap.get("salesMode")) + ",'"
				+ YUANLIAO_MAP.get(materialMap.get("categoryYuanliaoCode"))+"','"
				+ YUANLIAO_MAP.get(materialMap.get("categoryMainDesc"))+"',"
				+ "'10331000','"
				+ materialMap.get("tradeMark")+"','"
				+ YUANLIAO_PROPERTIES_MAP.get(materialMap.get("processLevel"))+"','"
				+ YUANLIAO_PROPERTIES_MAP.get(materialMap.get("charaLevel"))+"','"
				+ YUANLIAO_PROPERTIES_MAP.get(materialMap.get("usefulLevel"))+"','"
				+ materialMap.get("type")+"',"
				+ materialMap.get("quantity")+",'"
				+ materialMap.get("unit")+"',"
				+ materialMap.get("price")+",'"
				+ materialMap.get("priceUnit")+"','"
				+ getAreaCode(materialMap.get("location").toString())+"','"
				+ materialMap.get("description")+"',"
				+ materialMap.get("sendTime") + ","
				+ "1,"
				+ "0,"
				+ "0,"
				+ "'0',"
				+ "now(),"
				+ "now(),'"
				+ DateUtil.toString(DateUtil.getDateAfterDays(new Date(), (Integer) materialMap.get("expireTime")), "yyyy-MM-dd")+"',"
				+ "now(),"
				+ "now(),"
				+ "now()"
				+ ")";
		DBUtils.insertUpdate(DB, sql);
		
		sql = "select id from yuanliao order by id desc limit 1";
		final Integer[] yuanliaoId = new Integer[1];
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					yuanliaoId[0] = rs.getInt(1);
				}
			}
		});
		
		insertPic(yuanliaoId[0],materialMap.get("pic").toString());
	}
	
	private void insertPic(Integer yuanliaoId,String pic){
		if (StringUtils.isEmpty(pic)) {
			return ;
		}
		if (yuanliaoId==null||yuanliaoId==0) {
			return ;
		}
		String [] picArray = pic.split("\\|");
		for (String picStr:picArray) {
			
//		FileUtils.createDir("/mnt/data/resources/yuanliao", true);
			// 创建文件夹  001-099：  1/1/1/
			//			 101-199:   1/1/2/
			//			 201:       1/1/3/
			//			 1001:      1/2/1/
			//		 	10001:      2/1/1/
			ADDRESS_W = yuanliaoId/10000;
			ADDRESS_Q = (yuanliaoId%10000)/1000;
			ADDRESS_B = (yuanliaoId%10000%1000)/100;
			
			if (!picStr.endsWith(".jpg")) {
				picStr = picStr + ".jpg";
			}
			FileUtils.makedir(RESOURCE_ADD+"/"+ADDRESS_W+"");
			FileUtils.makedir(RESOURCE_ADD+"/"+ADDRESS_W+"/"+ADDRESS_Q);
			FileUtils.makedir(RESOURCE_ADD+"/"+ADDRESS_W+"/"+ADDRESS_Q+"/"+ADDRESS_B);
			
			String address = ADDRESS_W+"/"+ADDRESS_Q+"/"+ADDRESS_B+"/"+picStr;
			
			FileInputStream fis = null;
			FileOutputStream fos = null;
			
			if(!FileUtils.exists("/home/sj/jicui/"+TABLE+"/"+picStr)){
				return ;
			}
			
			try {
				fis = new FileInputStream("/home/sj/jicui/"+TABLE+"/"+picStr);
				fos = new FileOutputStream(RESOURCE_ADD+"/"+address);
				byte buf[] = new byte[1024];
				int n = 0;
				while ((n=fis.read(buf))!=-1) {
					fos.write(buf);
				}
			} catch (Exception e) {
				System.out.println("/home/sj/jicui/"+picStr);
				try {
					fis = new FileInputStream("/home/sj/pic/");
				} catch (FileNotFoundException e1) {
				}
			}finally{
				try {
					fis.close();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			String sql = "INSERT INTO `yuanliao_pic`"
					+ "("
					+ "`yuanliao_id`,"
					+ "`pic_address`,"
					+ "`is_del`,"
					+ "`check_status`,"
					+ "`gmt_created`,"
					+ "`gmt_modified`)"
					+ "VALUES"
					+ "("
					+ yuanliaoId+",'"
					+ "yuanliao/"+address+"',"
					+ "0,"
					+ "1,"
					+ "now(),"
					+ "now()"
					+ ")";
			DBUtils.insertUpdate(DB, sql);
		}
	}
	
	
	private String getAreaCode(String areaStr){
		Integer i =2;
		if (areaStr.indexOf("黑龙江")!=-1||areaStr.indexOf("内蒙古")!=-1) {
			i = 3;
		}
		String areaFixStr = areaStr.substring(i, areaStr.length());
		if (StringUtils.isEmpty(areaFixStr)) {
			areaFixStr = areaStr;
		}
		return AREA_MAP.get(areaFixStr);
	}
	
	private void initArea(){
		String sql = "SELECT code,label FROM  `category` WHERE code LIKE  '10011000%'";
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					AREA_MAP.put(rs.getString(2), rs.getString(1));
				}
			}
		});
	}
	
	private void initProperties(){
		String sql = "SELECT code,label FROM  `category` WHERE code LIKE  '20091000%'";
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					YUANLIAO_PROPERTIES_MAP.put(rs.getString(2), rs.getString(1));
				}
			}
		});
	}
	
	private void initYuanliaoMap(){
		String sql = "SELECT code,label FROM category_yuanliao";
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					YUANLIAO_MAP.put(rs.getString(2), rs.getString(1));
				}
			}
		});
	}
	
	private String getInitAccount(){
		String sql ="select account from company_account order by id desc limit 1";
		final String [] account = new String[1]; 
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					account[0] = rs.getString(1);
				}
			}
		});
		Integer count = 1;
		if (account[0].indexOf("slyl")!=-1) {
			account[0] = account[0].replace("slyl", "");
			if (StringUtils.isNumber(account[0])) {
				count = Integer.valueOf(account[0])+1;
			}
		}
		String accountFix = ""+count;
		while (accountFix.length()<4) {
			accountFix = "0" + accountFix;
		}
		return "slyl" + accountFix;
	}
	
	@SuppressWarnings("unchecked")
	private void initCompany(List<Map<String,Object>> companyList){
		// 以“slyl0001”为例，一个公司分配一个账号，范围为：“slyl0001-slyl9999”。密码123456
		
		
		for (Map<String, Object> companyMap:companyList) {
			Integer companyId ;
			// 当公司名称、手机同时相同时 判定为同一个公司
			String name = (String) companyMap.get("name");
			Map<String, String> companyAccountMap = (Map<String, String>) companyMap.get("companyAccountMap");
			String mobile = companyAccountMap.get("mobile");
			String tel = companyAccountMap.get("tel");
			String sql = "select id from company where name ='"+name.trim()+"' limit 1";
			final Integer[] hasCompany  =new Integer[1];
			DBUtils.select(DB, sql, new IReadDataHandler() {
				@Override
				public void handleRead(ResultSet rs) throws SQLException {
					while (rs.next()) {
						hasCompany[0]=rs.getInt(1);
					}
				}
			});
			
			if (hasCompany[0]==null) {
				companyId = insertCompany(companyMap);// insert 公司表
			}else{
				companyId = hasCompany[0];
			}
			
			final String[] accountStr  =new String[1];
			sql = "select account from company_account where company_id ="+hasCompany[0] + " and tel = '"+mobile+"|"+tel+"'";
			DBUtils.select(DB, sql, new IReadDataHandler() {
				@Override
				public void handleRead(ResultSet rs) throws SQLException {
					while (rs.next()) {
						accountStr[0] = rs.getString(1);
					}
				}
			});
			
			String account ;
			if (StringUtils.isEmpty(accountStr[0])) {
				// 帐号表
				account = insertCompanyAccount(companyId,companyMap);
				// 登录表
				insertAuth(account);
			}else{
				account = accountStr[0];
			}
			
			// 导入 原料 和图片
			insertMaterial(companyId,account,companyMap);
		}
		
	}
	
	private void insertAuth(String account){
		if (StringUtils.isEmpty(account)) {
			return ;
		}
		String sql = "select count(0) from auth_user where username = '"+account+"'";
		final Integer[] hasAuth = new Integer[1]; 
		DBUtils.select(DB, sql, new IReadDataHandler() {
			
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					hasAuth[0] = rs.getInt(1);
				}
			}
		});
		
		if (hasAuth[0]>0) {
			return ;
		}
		
		sql = "INSERT INTO `auth_user`"
				+ "("
				+ "`username`,"
				+ "`password`,"
				+ "`email`,"
				+ "`gmt_created`,"
				+ "`gmt_modified`"
				+ ")"
				+ "VALUES"
				+ "('"
				+ account + "',"
				+ "'49ba59abbe56e057',"
				+ "'',"
				+ "now(),"
				+ "now()"
				+ ")";
		DBUtils.insertUpdate(DB, sql);
	}
	
	private Integer insertCompany(Map<String, Object> companyMap){
		/**
		 * 1、 主营行业：塑料原料 10001010 industry_code
		 * 2、 服务类型：国内供应商 10201004 service_code
		 * 3、 公司归类：普通客户 10101002 membership_code
		 * 4、 会员类型：普通会员 10051000 classified_code
		*/
		
		String sql = "INSERT INTO `company`"
				+ "("
				+ "`name`,"
				+ "`industry_code`,"
				+ "`business`,"
				+ "`service_code`,"
				+ "`area_code`,"
				+ "`membership_code`,"
				+ "`classified_code`,"
				+ "`regfrom_code`,"
				+ "`regtime`,"
				+ "`gmt_created`,"
				+ "`gmt_modified`,"
				+ "`address`,"
				+ "`introduction`)"
				+ "VALUES"
				+ "('"
				+ companyMap.get("name")+"',"
				+ "'10001010','"
				+ companyMap.get("business").toString().replace("'", "’")+"',"
				+ "'10201004','"
				+ getAreaCode(companyMap.get("areaCode").toString())+"',"
				+ "'10051000',"
				+ "'10101002',"
				+ "'10031040',"
				+ "now(),"
				+ "now(),"
				+ "now(),'"
				+ companyMap.get("address")+"','"
				+ companyMap.get("introduction").toString().replace("'", "‘")
				+ "')";
		try {
			DBUtils.insertUpdate(DB, sql);
		} catch (Exception e) {
			System.out.println(sql);
		}
		sql = "select id from company order by id desc limit 1";
		final Integer [] lastCompanyId = new Integer[1];
		DBUtils.select(DB, sql,  new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					lastCompanyId[0] = rs.getInt(1);
				}
			}
		});
		return lastCompanyId[0];
	}
	
	@SuppressWarnings("unchecked")
	private String  insertCompanyAccount(Integer companyId,Map<String, Object> companyMap){
		String account = getInitAccount();
		Map<String, String > accountMap = (Map<String, String>) companyMap.get("companyAccountMap");
		if (accountMap==null) {
			return null;
		}
		
		String sql = "select account from company_account where company_id = "+companyId + " limit 1";
		final String[] hasAccount = new String[1];
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					hasAccount[0] = rs.getString(1);
				}
			}
		});
		// 已经存在，不插入
		if (StringUtils.isNotEmpty(hasAccount[0])) {
			return hasAccount[0];
		}
		
		sql = "INSERT INTO `company_account`"
				+ "("
				+ "`account`,"
				+ "`company_id`,"
				+ "`contact`,"
				+ "`email`,"
				+ "`tel`,"
				+ "`gmt_modified`,"
				+ "`gmt_created`)"
				+ "VALUES"
				+ "('"
				+ account + "',"
				+ companyId+",'"
				+ accountMap.get("contact")+"','"
				+ "','"
				+ accountMap.get("mobile")+"|"+accountMap.get("tel")+"',"
				+ "now(),"
				+ "now()"
				+ ")";
		
		DBUtils.insertUpdate(DB, sql);
		
		
		return account;
	}
	
	

	@Override
	public boolean clear(Date baseDate) throws Exception {
		return false;
	}
	
	/**
	 * 
	 * 读取Excel的内容，第一维数组存储的是一行中格列的值，二维数组存储的是多少个行
	 * 
	 * @param file
	 *            读取数据的源Excel
	 * 
	 * @param ignoreRows
	 *            读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1
	 * 
	 * @return 读出的Excel中数据的内容
	 * 
	 * @throws FileNotFoundException
	 * 
	 * @throws IOException
	 * 
	 */

	public static String[][] getData(File file, int ignoreRows)

	throws FileNotFoundException, IOException {

		List<String[]> result = new ArrayList<String[]>();
		int rowSize = 0;
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));

		// 打开HSSFWorkbook
		POIFSFileSystem fs = new POIFSFileSystem(in);
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		HSSFCell cell = null;
		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
			HSSFSheet st = wb.getSheetAt(sheetIndex);
			// 第一行为标题，不取
			for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
				HSSFRow row = st.getRow(rowIndex);
				if (row == null) {
					continue;
				}
				int tempRowSize = row.getLastCellNum() + 1;
				if (tempRowSize > rowSize) {
					rowSize = tempRowSize;
				}
				String[] values = new String[rowSize];
				Arrays.fill(values, "");
				boolean hasValue = false;
				for (int columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
					String value = "";
					cell = row.getCell(columnIndex);
					if (cell != null) {
						// 注意：一定要设成这个，否则可能会出现乱码
						// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
						switch (cell.getCellType()) {
						case HSSFCell.CELL_TYPE_STRING:
							value = cell.getStringCellValue();
							break;
						case HSSFCell.CELL_TYPE_NUMERIC:
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								Date date = cell.getDateCellValue();
								if (date != null) {
									value = new SimpleDateFormat("yyyy-MM-dd").format(date);
								} else {
									value = "";
								}
							} else {
								value = new DecimalFormat("0").format(cell.getNumericCellValue());
							}
							break;
						case HSSFCell.CELL_TYPE_FORMULA:
							// 导入时如果为公式生成的数据则无值
							if (!cell.getStringCellValue().equals("")) {
								value = cell.getStringCellValue();
							} else {
								value = cell.getNumericCellValue() + "";
							}
							break;
						case HSSFCell.CELL_TYPE_BLANK:
							break;
						case HSSFCell.CELL_TYPE_ERROR:
							value = "";
							break;
						case HSSFCell.CELL_TYPE_BOOLEAN:
							value = (cell.getBooleanCellValue() == true ? "Y" : "N");
							break;
						default:
							value = "";
						}
					}
					if (columnIndex == 0 && value.trim().equals("")) {
						break;
					}
					values[columnIndex] = rightTrim(value);
					hasValue = true;
				}
				if (hasValue) {
					result.add(values);
				}
			}
		}
		in.close();
		String[][] returnArray = new String[result.size()][rowSize];
		for (int i = 0; i < returnArray.length; i++) {
			returnArray[i] = (String[]) result.get(i);
		}
		return returnArray;
	}

	/**
	 * 
	 * 去掉字符串右边的空格
	 * 
	 * @param str
	 *            要处理的字符串
	 * 
	 * @return 处理后的字符串
	 * 
	 */

	public static String rightTrim(String str) {
		if (str == null) {
			return "";
		}
		int length = str.length();
		for (int i = length - 1; i >= 0; i--) {
			if (str.charAt(i) != 0x20) {
				break;
			}
			length--;
		}
		return str.substring(0, length);
	}
	
	public static void main(String[] args) throws Exception {
		
		DBPoolFactory.getInstance().init("file:/mnt/tools/config/db/db-zztask-jdbc.properties");
		ImportMaterialTask obj = new ImportMaterialTask();
		obj.initArea();
		obj.initProperties();
		obj.initYuanliaoMap();
		obj.exec(new Date());
		System.out.println("ye ok");
//		String str = "昆山给力塑胶原料有限公司成立于2000年，主要专业从事工程塑胶原料及有色金属的销售和代理为一体的商事公司，公司在工业原料行业享有良好的信誉，并与多家知名生产厂商建立长期稳定的代理经销合作关系，提供售前服务和售后服务。本公司的服务理念是“顾客第一、赢利第二、真诚服务、售后到位”，以完善的服务网络、热情的服务态度、严格的服务规范、满意的服务效果为标准。'用户至上、服务满意'，为用户提供一个从售前到售后的全程服务，以满足用户的需要，我公司全体员工同心协力，为各界朋友的事业发展共助微薄之力";
//		str = str.replace("'", "\'");
//		System.out.println(str);
	}
	
}
