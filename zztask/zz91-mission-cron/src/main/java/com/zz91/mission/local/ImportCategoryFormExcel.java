package com.zz91.mission.local;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.db.pool.DBPoolFactory;
import com.zz91.util.lang.StringUtils;

public class ImportCategoryFormExcel implements ZZTask {

	final static String DB = "feiliao91";
	final static Map<String, String> MAIN_MAP = new HashMap<String, String>();
	final static Map<String, String> SEC_MAP = new HashMap<String, String>();
	
	@Override
	public boolean init() throws Exception {
		String sql ="SELECT code,label FROM  `category` WHERE parent_code =  '1002'";
		DBUtils.select(DB, sql, new  IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					MAIN_MAP.put(rs.getString(2), rs.getString(1));
				}
			}
		});
		return false;
	}

	@Override
	public boolean exec(Date baseDate) throws Exception {
		File file = new File("/home/sj/1.xls");
		String[][] data = getData(file, 0);
		int rowLength = data.length;
		for (int i = 0; i < rowLength; i++) {
			String mainLabel = data[i][0];
			String mainCode = "";
			if (MAIN_MAP.containsKey(mainLabel)) {
				mainCode = MAIN_MAP.get(mainLabel);
			}else{
				mainCode = create("1002",mainLabel);
				MAIN_MAP.put(mainLabel, mainCode);
			}
			String secLabel = data[i][1];
			String secCode = "";
			if (SEC_MAP.containsKey(secLabel)) {
				secCode = SEC_MAP.get(secLabel);
			}else{
				secCode = create(mainCode, secLabel);
				SEC_MAP.put(secLabel, secCode);
			}
			
			String lastLabel = data[i][2];
			lastLabel = lastLabel.replace("，", ",");
			String[] trdArray = lastLabel.split(",");
			for (String label : trdArray) {
				create(secCode, label);
			}
		}
		return true;
	}
	
	private String create(String parentCode,String label){
		String code = validateExists(parentCode,label);
		boolean lastFlag = false;
		if(StringUtils.isEmpty(code)){
			code = getLastCode(parentCode);
			lastFlag =true;
		}
		if(StringUtils.isNotEmpty(code)&&lastFlag){
			insert(code, parentCode, label);
		}
		return code;
	}
	
	private String getLastCode(String code){
		final String[] result={""};
		String sql = "SELECT code,label FROM  `category` WHERE parent_code =  '"+code+"' order by id desc limit 0,1";
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					result[0] = rs.getString(1);
				}
			}
		});
		String lastCode = "";
		if (StringUtils.isEmpty(result[0])) {
			lastCode = code+ "1000";
		}else{
			String tempStr = result[0].substring(result[0].length()-4, result[0].length());
			if (StringUtils.isNumber(result[0])) {
				Integer i = Integer.valueOf(tempStr);
				i++;
				lastCode = result[0].substring(0, result[0].length()-4)+i;
			}
		}
		return lastCode; 
	}
	
	private String validateExists(String parentCode,String label){
		String sql = "select code from category where label = '"+label+"' and parent_code= '"+parentCode+"'";
		final String[] sqlResult = {""};
		DBUtils.select(DB, sql, new IReadDataHandler() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					sqlResult[0] = rs.getString(1);
				}
			}
		});
		return sqlResult[0];
	}
	
	private void insert(String code,String parentCode,String label){
		String sql = "INSERT INTO `feiliao91`.`category`(`code`,`parent_code`,`label`,`is_del`,`gmt_created`,`gmt_modified`)"
			+ "VALUES"
			+ "('"+code+"','"+parentCode+"','"+label+"',0,now(),now())";
		DBUtils.insertUpdate(DB, sql);
	}

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

	@Override
	public boolean clear(Date baseDate) throws Exception {
		return false;
	}

	public static void main(String[] args) throws Exception {
		DBPoolFactory.getInstance().init("file:/mnt/tools/config/db/db-zztask-jdbc.properties");
		ImportCategoryFormExcel obj = new ImportCategoryFormExcel();
		obj.exec(new Date());
		System.out.println("success");
	}

}
