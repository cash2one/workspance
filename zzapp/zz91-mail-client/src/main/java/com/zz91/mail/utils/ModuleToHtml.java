package com.zz91.mail.utils;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class ModuleToHtml {

	public static String toHtml(Map<String, Object> module, String moduleFile) {
		String result="";
		// 创建引擎   
		VelocityEngine ve = new VelocityEngine();
		//设置模板加载路径，这里设置的是class下   
		//ve.setProperty(Velocity.RESOURCE_LOADER, "class");   
		//ve.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");   
		//ve.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, "/root/dev/mail/trunk/zz91-mail-client/src/main/resources/");
		try {
			//进行初始化操作
			ve.init("/root/dev/mail/trunk/zz91-mail-client/src/main/resources/velocity.propertis");
			//加载模板，设定模板编码
			Template t = ve.getTemplate(moduleFile);
			//设置初始化数据   
			VelocityContext context = new VelocityContext();
			if (module != null)
				for (Entry<String, Object> entry : module.entrySet()) {
					context.put(entry.getKey(), entry.getValue());
				}
			//context.put("name", "张三");
			//设置输出   
			Writer writer = new StringWriter();
			//将环境数据转化输出   
			t.merge(context, writer);
			//简化操作
			//ve.mergeTemplate("test.vm", "gbk", context, writer );
			result=writer.toString();
			System.out.println(result);
			 
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
