package com.ast.ast1949.front.statichtml;

import java.io.IOException;
import java.util.Map;

import org.apache.velocity.exception.VelocityException;


public interface ModuleToHtml {
	void toHtml(String templateFile, Map<String,Object> context, String toHtmlFile) throws VelocityException, IOException;
}
