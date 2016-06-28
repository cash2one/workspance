/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-5-28
 */
package com.ast.ast1949.web.servlet;

/**
 * @author Rolyer (rolyer.live@gmail.com)
 *
 */
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
  
public class BuildExcelServlet extends HttpServlet {  
  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,  
            HttpServletResponse response) throws ServletException, IOException {  
//        StudentResultSet stuResultSet = new StudentResultSet();  
        ResultSet rs = null; //stuResultSet.query();  
        String xlsName = "test.xls";  
        String sheetName = "sheetName";  
  
        HSSFWorkbook workbook = new HSSFWorkbook();  
        HSSFSheet sheet = workbook.createSheet();  
        workbook.setSheetName(0, sheetName);  
        HSSFRow row = sheet.createRow((short) 0);  
        HSSFCell cell;  
        try {  
            ResultSetMetaData md = rs.getMetaData();  
            int nColumn = md.getColumnCount();  
  
            for (int i = 1; i <= nColumn; i++) {  
                cell = row.createCell((short) (i - 1));  
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell.setCellValue(new HSSFRichTextString(md.getColumnLabel(i)));  
            }  
            int iRow = 1;  
            while (rs.next()) {  
                row = sheet.createRow((short) iRow);  
                for (int j = 1; j <= nColumn; j++) {  
                    cell = row.createCell((short) (j - 1));  
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
                    cell.setCellValue(new HSSFRichTextString(rs.getObject(j)  
                            .toString()));  
                }  
                iRow++;  
            }  
            String filename = request.getRealPath("/") + xlsName;  
            request.setAttribute("filename", filename);  
            FileOutputStream fOut = new FileOutputStream(filename);  
            workbook.write(fOut);  
            fOut.flush();  
            fOut.close();  
            request.getRequestDispatcher("OpenExcelServlet").forward(request,  
                    response);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    protected void doPost(HttpServletRequest request,  
            HttpServletResponse response) throws ServletException, IOException {  
        this.doGet(request, response);  
    }  
}  
