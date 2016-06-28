package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelImportData {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		File file = new File("/root/data.xls");
		if (file.exists()) {
			FileInputStream in = new FileInputStream(file);
			HSSFWorkbook wb=new HSSFWorkbook(in);
			HSSFSheet sheet=wb.getSheetAt(0);
			int f=sheet.getFirstRowNum();
			int l=sheet.getLastRowNum();
			in.close();
			HSSFRow row;
			for(int i=f+1;i<=l;i++){
				row = sheet.getRow(i);
				if (row.getCell(0) != null) {
					String keyword=String.valueOf(row.getCell(0).getRichStringCellValue());
					if (row.getCell(1) != null) {
						String string = String.valueOf(row.getCell(1).getRichStringCellValue());
						if (StringUtils.isNotEmpty(keyword) && StringUtils.isNotEmpty(string)) {
							System.out.println("update trade_supply set use_to='"+string+"' where title like concat('%','"+keyword+"','%') and id < 10000000;");
						}
					}
				}
			}
		}

	}

}
