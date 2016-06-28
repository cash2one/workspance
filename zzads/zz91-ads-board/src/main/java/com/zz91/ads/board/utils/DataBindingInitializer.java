/**
 * 
 */
package com.zz91.ads.board.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

/**
 * 用于处理页面提交数据的数据类型转换
 * @author mays (mays@zz91.net)
 *
 */
public class DataBindingInitializer implements WebBindingInitializer {

	final static String DEFAULT_DATE_FORMATE="yyyy-MM-dd HH:mm:ss";
	
	@Override
	public void initBinder(WebDataBinder binder, WebRequest request) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMATE);
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,true));
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
	}

}
