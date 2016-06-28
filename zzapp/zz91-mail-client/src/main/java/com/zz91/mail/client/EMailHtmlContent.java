package com.zz91.mail.client;

import java.util.HashMap;
import java.util.Map;

import com.zz91.mail.utils.ModuleToHtml;

public class EMailHtmlContent {
	private String contentModule;//邮件模板
	private Map<String, Object> contentValueMap;//邮件内容

	public String getContentModule() {
		return contentModule;
	}

	public void setContentModule(String contentModule) {
		this.contentModule = contentModule;
	}

	public Map<String, Object> getContentValueMap() {
		return contentValueMap;
	}

	public void setContentValueMap(Map<String, Object> contentValueMap) {
		this.contentValueMap = contentValueMap;
	}

	public void addContentValueToMap(String key, Object value) {
		if (contentValueMap == null)
			contentValueMap = new HashMap<String, Object>();
		contentValueMap.put(key, value);
	}

	public String getHtmlContentString() {
		return ModuleToHtml.toHtml(contentValueMap, contentModule);
	}

	//	private Map<String, String> valueMap = new HashMap<String, String>();
	//	private Map<String, String> imgMap = new HashMap<String, String>();
	//	private String content;
	//
	//	public EMailHtmlContent(String content) {
	//		this.content = content;
	//	}
	//
	//	public String getKeyWordValue(String key) {
	//		return valueMap.get(key);
	//	}
	//
	//	public void addKeyWordToMap(String key, String value) {
	//		valueMap.put(key, value);
	//	}
	//
	//	public String getContent() {
	//		StringBuffer buf = new StringBuffer();
	//		if (StringUtils.isNotEmpty(content)) {
	//			replaceKeyWords();
	//			//processImgTag();
	//			buf.append(content);
	//		}
	//		return buf.toString();
	//	}
	//
	//	public void setContent(String content) {
	//		this.content = content;
	//	}
	//
	//	public Map<String, String> getImgMap() {
	//		return imgMap;
	//	}
	//
	//	private void replaceKeyWords() {
	//		for (String key : valueMap.keySet()) {
	//			if (getKeyWordValue(key) != null){
	//				content=content.replaceAll("#:"+key+"#", getKeyWordValue(key));
	//			}
	//		}
	//	}
	//
	//	@Override
	//	public String toString() {
	//		return  getContent();
	//	}
	//
	//	@SuppressWarnings("unused")
	//	private void processImgTag() {
	//		// content中<img src=”c:/test.jpg”></img>这段代码变成<img src=”cid:IMG”></img>
	//		Document doc = Jsoup.parse(content);
	//		Elements imgs = doc.getElementsByTag("img");
	//		int count = 1;
	//		for (Element e : imgs) {
	//			imgMap.put("IMG" + count, e.attr("src"));
	//			//Content-ID:IMG
	//			e.attr("src", "imgid" + count + ":" + e.attr("src"));
	//			count++;
	//		}
	//		content = doc.toString();
	//	}
}
