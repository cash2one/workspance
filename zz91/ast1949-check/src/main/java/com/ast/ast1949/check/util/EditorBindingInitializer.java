package com.ast.ast1949.check.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

public class EditorBindingInitializer implements WebBindingInitializer {

	public void initBinder(WebDataBinder webBinder, WebRequest request) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(true);
		webBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,true));
		webBinder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
	}

}
