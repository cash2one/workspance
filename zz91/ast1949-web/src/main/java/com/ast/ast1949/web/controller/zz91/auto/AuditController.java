package com.ast.ast1949.web.controller.zz91.auto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.lang.SensitiveUtils;

@Controller
public class AuditController extends BaseController {
	
	@RequestMapping
	public void index(Map<String,Object>out){
		StringBuffer sb=new StringBuffer();
		BufferedReader br=null;
		try {
			br = new BufferedReader(new FileReader("/usr/data/keylimit/limit"));
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}finally{
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
				}
			}
		}
		out.put("list", sb.toString());
	}
	
	@RequestMapping
	public ModelAndView doUpdate(HttpServletRequest request, Map<String, Object> out, String list) throws IOException {
		
		String[] items=list.split("\n");
		BufferedWriter bw=null;
		try {
			bw = new BufferedWriter(new FileWriter("/usr/data/keylimit/limit"));
			
			for(String item:items){
				bw.write(item);
				bw.newLine();// 换行
			}
			
			bw.flush();
		} catch (IOException e) {
		}finally{
			if(bw!=null){
				try {
					bw.close();
				} catch (IOException e) {
				}
			}
		}
		// 更新缓存
		SensitiveUtils.updateCache();
		
		return new ModelAndView("redirect:index.htm");
	}

}
