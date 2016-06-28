package com.ast.ast1949.bbs.util;

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
		SimpleDateFormat dateFormat = new SimpleDateFormat(AstConst.DATE_FORMATE_DEFAULT);
		dateFormat.setLenient(false);
		webBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,false));
		webBinder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
	}

}
