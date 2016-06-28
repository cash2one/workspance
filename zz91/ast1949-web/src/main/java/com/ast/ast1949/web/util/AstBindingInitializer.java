package com.ast.ast1949.web.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import com.ast.ast1949.util.AstConst;

public class AstBindingInitializer implements WebBindingInitializer {

	public void initBinder(WebDataBinder webBinder, WebRequest request) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(AstConst.DATE_FORMATE_WITH_TIME);
		dateFormat.setLenient(true);
		webBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,true));
//		webBinder.registerCustomEditor(Date.class, new PropertyEditorSupport() {  
//	        public void setAsText(String value) {  
//                try {
//					setValue(new SimpleDateFormat("yyyy-MM-dd").parse(value));
//				} catch (ParseException e) {
//					e.printStackTrace();
//				}  
//	        }  
//	  
//	        public String getAsText() {
//	            return new SimpleDateFormat(AstConst.DATE_FORMATE_WITH_TIME).format((Date) getValue());  
//	        }          
//	    }); 
		webBinder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
	}

}
