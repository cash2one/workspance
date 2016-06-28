package com.ast.ast1949.front.statichtml;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
@Component("htmlWriter")
public class ModuleToHtmlStringWriterImpl implements ModuleToHtml {

	@Resource
	private VelocityConfigurer velocityConfig;
	private VelocityEngine velocityEngine;

	@Override
	public void toHtml(String templateFile, Map<String, Object> contextMap, String toHtmlFile)
			throws VelocityException, IOException {
		velocityEngine = velocityConfig.createVelocityEngine();
		VelocityContext context = new VelocityContext();
//		for (String key : contextMap.keySet()) {
//			context.put(key, contextMap.get(key));
//		}
		//设置模板加载路径，这里设置的是class下  
		//		VelocityEngine ve = velocityEngine;
		//		ve.setProperty(Velocity.RESOURCE_LOADER, "class");
		//		ve.setProperty("class.resource.loader.class",
		//				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		//		ve.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, "D:\\workspace\\fwk\\velocity\\src");

		try {
			//进行初始化操作  
			//			ve.init();
			//加载模板，设定模板编码  
			templateFile="root/index.vm";
			Template t = velocityEngine.getTemplate(templateFile, "UTF-8");//"root/index.vm"
			//设置初始化数据  
			context.put("name", "张三");
			context.put("project", "Jakarta");
			//设置输出  
			Writer writer = new StringWriter();
			//将环境数据转化输出  
			t.merge(context, writer);
			//简化操作  
			//ve.mergeTemplate("test/velocity/simple1.vm", "gbk", context, writer );  
			System.out.println(writer.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
