/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-10-13
 */
package com.ast.ast1949.check.thread;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ast.ast1949.domain.products.ProductsAutoCheck;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.dto.products.ProductsDto;
import com.ast.ast1949.service.products.ProductsAutoCheckService;
import com.ast.ast1949.service.products.ProductsService;
import com.zz91.util.lang.NumberUtil;
import com.zz91.util.lang.StringUtils;


/**
 * 审核发送线程，将邮件信息提交给邮件服务器
 * 
 * @author kongsj
 * @date 2012-12-25
 */

public class CheckSendThread extends Thread {

	
	private ProductsAutoCheckService productsAutoCheckService;
	private ProductsService productsService;
	private Integer id;
	
	final static String NEXT_LINE_CONTENT = "<br/><img src='http://img0.zz91.com/front/images/global/icon/question-mark.gif' align='absmiddle' title='未通过原因'>";
	final static String CHECK_PERSON = "admin-auto-check";

	public CheckSendThread() {
		
	}

	public CheckSendThread(Integer id,ProductsAutoCheckService productsAutoCheckService,ProductsService productsService) {
		this.productsAutoCheckService = productsAutoCheckService;
		this.productsService = productsService;
		this.id = id;
	}

	final static Map<String, String> UNPASS_REASON = new HashMap<String, String>();
	static {
		UNPASS_REASON.put("1", "系统审核");
		UNPASS_REASON.put("2", "您的产品名称与信息描述不吻合,建议您修改相关内容。");//系统审核-信息标题和产品描述不一致
		UNPASS_REASON.put("3", "您的产品名称与信息描述不吻合,建议您修改相关内容。");//系统审核-供求类型和信息描述不一致
		UNPASS_REASON.put("4", "供求信息重复发布，重复包括：标题、产品描述等。重复发布信息将十分不利于您的产品的排名，相同的产品我们只建议您发布一次。");//系统审核-供求信息重复发布
		UNPASS_REASON.put("5", "供求信息包含联系方式（如QQ，电话，手机，其他网址等）。信息描述仅允许填写您产品的详细情况及您的合作诚意。");//系统审核-供求信息包含联系方式
	}

	@Override
	public void run() {
		
		// 供求审核
		do{
			productsAutoCheckService.updateByStatus(id, ProductsAutoCheckService.CHECKED); // 已审
			ProductsAutoCheck obj = productsAutoCheckService.queryById(id);
			if(obj==null){
				break;
			}

			ProductsDto dto = productsService.queryProductsDetailsById(obj.getProductId());
			if(dto==null||dto.getProducts()==null){
				break;
			}

			ProductsDO productsDO = dto.getProducts();

			String unPassReason = productsDO.getUnpassReason();
			// 标题重复
			if(!productsService.countProuductsByTitleAndAccount(productsDO.getTitle(),productsDO.getProductsTypeCode(),productsDO.getAccount())){
//				productsDO.setUnpassReason(bulidUnpassReason(productsDO.getUnpassReason(),UNPASS_REASON.get("4")));
				unPassReason = bulidUnpassReason(unPassReason,UNPASS_REASON.get("4"));
				productsService.updateProductsCheckStatusByAdmin(ProductsService.CHECK_FAILD, unPassReason, "auto-check", productsDO.getId());
			}else{
				// 自动审核如果标题不重复，则删除标题重复的原因
				if(StringUtils.isNotEmpty(unPassReason)&&unPassReason.indexOf(UNPASS_REASON.get("4"))!=-1){
					unPassReason = unPassReason.replace(UNPASS_REASON.get("4"), "");
					productsService.updateProductsCheckStatusByAdmin(productsDO.getCheckStatus(),unPassReason, "auto-check", productsDO.getId());
				}
			}

			// 验证是否包含电话号码
			if(checkMobile(productsDO.getDetails())||checkMobile(productsDO.getTags())||checkMobile(productsDO.getTitle())){
//				productsDO.setUnpassReason(bulidUnpassReason(productsDO.getUnpassReason(),UNPASS_REASON.get("5")));
				unPassReason = bulidUnpassReason(unPassReason,UNPASS_REASON.get("5"));
				productsService.updateProductsCheckStatusByAdmin(ProductsService.CHECK_FAILD, unPassReason, "auto-check", productsDO.getId());
			}else{
				// 自动审核如果标题不重复，则删除标题重复的原因
				if(StringUtils.isNotEmpty(unPassReason)&&unPassReason.indexOf(UNPASS_REASON.get("5"))!=-1){
					unPassReason = unPassReason.replace(UNPASS_REASON.get("5"), "");
					productsService.updateProductsCheckStatusByAdmin(productsDO.getCheckStatus(), unPassReason, "auto-check", productsDO.getId());
				}
			}

			// 标题处理
			productsDO.setTitle(checkTitle(productsDO.getTitle()));
			
			// 废塑料 标题特别处理
			if(StringUtils.isNotEmpty(productsDO.getCategoryProductsMainCode())&&productsDO.getCategoryProductsMainCode().startsWith("1001")){
				// 英文大小写转化
				productsDO.setTitle(productsDO.getTitle().toUpperCase());
			}

			// 报价处理
			if(productsDO.getMinPrice()!=null){
				productsDO.setMinPrice(checkPrice(productsDO.getMinPrice()));
			}
			if(productsDO.getMaxPrice()!=null){
				productsDO.setMaxPrice(checkPrice(productsDO.getMaxPrice()));
			}
			
			// 更新 供求
			productsService.updateProductByAdmin(productsDO);
		}
		while(false);
	}
	
	private String checkTitle(String title){
		
		title = title.trim();
		
		// 屏蔽标题  “求购”、“供应”、“回收”、“出售”、“收购”，“采购”
		title = title.replace("求购", "");
		title = title.replace("供应", "");
		title = title.replace("回收", "");
		title = title.replace("出售", "");
		title = title.replace("收购", "");
		title = title.replace("采购", "");
		
		// 逗号中文变英文
		title = title.replace("，", ",");
		
		// 顿号变英文逗号
		title = title.replace("、", ",");
		
		// 去掉标题 头尾 英文 逗号
		if(title.startsWith(",")){
			title = title.substring(1,title.length());
		}
		if(title.endsWith(",")){
			title = title.substring(0, title.length()-1);
		}
		
		return title;
	}
	
	private Float checkPrice(Float price){
		Integer tmpPrice = price.intValue();
		// 重复数字返回 0
		if(NumberUtil.validateSameNumber(tmpPrice)){
			return 0f;
		}
		return price;
	}
	
	// 验证电话号码
	private Boolean checkMobile(String content){
		Pattern p = Pattern.compile("[\\d]+");
		Matcher m = p.matcher(content);
		Set<String> numSet = new HashSet<String>();
		while(m.find()) {
			numSet.add(m.group());
		}
		
		String check ="^((0\\d{2,3})?\\d{7,8})$|1[3,5,8]{1}[0-9]{9}$";
		for (String num:numSet) {
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(num);
			if(matcher.matches()){
				return true;
			}
		}
//		System.out.println(isMatched);
		return false;
	}
	
	// 设置审核不过原因
	private String bulidUnpassReason(String unpassReason,String aReason){
		String str = "";
		if(StringUtils.isEmpty(unpassReason)){
			return aReason;
		}
		Set<String> reasonSet = new HashSet<String>();
		if(unpassReason.indexOf(NEXT_LINE_CONTENT)!=-1){
			String[] reasonArray = unpassReason.split(NEXT_LINE_CONTENT);
			for (String reason:reasonArray) {
				if(StringUtils.isNotEmpty(reason)){
					reasonSet.add(reason);
				}
			}
		}else{
			reasonSet.add(unpassReason);
		}
		
		if(StringUtils.isNotEmpty(aReason)){
			reasonSet.add(aReason);
		}
		
		for(String reason:reasonSet){
			if(StringUtils.isEmpty(str)){
				str += reason;
			}else{
				str += NEXT_LINE_CONTENT + reason;
			}
		}
		
		return str;
	}
}
