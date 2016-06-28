/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-4-12.
 */
package com.zz91.ads.board.controller.ad;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ads.board.controller.BaseController;
import com.zz91.ads.board.domain.ad.Advertiser;
import com.zz91.ads.board.dto.ExtResult;
import com.zz91.ads.board.dto.Pager;
import com.zz91.ads.board.service.ad.AdvertiserService;
import com.zz91.util.db.DBUtils;
import com.zz91.util.db.IReadDataHandler;
import com.zz91.util.lang.StringUtils;

/**
 * 广告主控制器
 * 
 * @author Rolyer(rolyer.live@gmail.com)
 */
@Controller
public class AdvertiserController extends BaseController {

	final static String DB_AST = "ast";
	
	@Resource
	AdvertiserService advertiserService;

	/**
	 * 初始化页面
	 */
	@RequestMapping
	public void index() {

	}

	/**
	 * 读取列表
	 * 
	 * @param model
	 * @param advertiser
	 * @param delete
	 * @param pager
	 * @return
	 */
	@RequestMapping
	public ModelAndView query(Map<String, Object> model, Advertiser advertiser, Boolean delete,
			Pager<Advertiser> pager) {
		// 分页排序设置
		if (pager != null) {
			if (pager.getSort() == null) {
				pager.setSort("id");
			}
			if (pager.getDir() == null) {
				pager.setDir("DESC");
			}
		} else {
			pager = new Pager<Advertiser>();
			pager.setDir("DESC");
			pager.setSort("id");
		}
		// 读取数据
		pager = advertiserService.pageAdvertiserByConditions(advertiser, delete, pager);
		
		// 使用邮箱自动补全功能
		if (pager.getRecords().size()<1 && StringUtils.isNotEmpty(advertiser.getEmail())) {
			final Advertiser obj = new Advertiser();
			obj.setCategory(0); //			category 广告主类别 0：阿思拓 1：再生通用户 2：其他
			obj.setRemark("系统自动匹配");//			remark 备注
			obj.setDeleted("N"); // 删除状态
			String sql = "select company_id,contact,mobile,email from company_account where email = '"+advertiser.getEmail()+"' limit 1";
			final Integer[] companyId = new Integer[1];
			DBUtils.select(DB_AST, sql, new IReadDataHandler() {
				@Override
				public void handleRead(ResultSet rs) throws SQLException {
					while (rs.next()) {
						companyId[0] = rs.getInt(1);
						obj.setContact(rs.getString(2));//	contact 联系人	
						obj.setPhone(rs.getString(3));//	phone 联系电话，可以是多个	
						obj.setEmail(rs.getString(4));//	email 联系email，可以是多个
					}
				}
			});
			//	name 广告主名称，可以是公司名称也可以是个人名称 广告主可以是自己（阿思拓），也可以是再生能客户，或者其他网站或个人
			sql = "select name from company where id = " + companyId[0];
			DBUtils.select(DB_AST, sql, new  IReadDataHandler() {
				
				@Override
				public void handleRead(ResultSet rs) throws SQLException {
					while (rs.next()) {
						obj.setName(rs.getString(1));
					}
				}
			});
			if (StringUtils.isEmpty(obj.getName())) {
				obj.setName("系统导入");
			}
			Integer i = advertiserService.insertAdvertiser(obj); // 新增用户
			if (i>0) {
				pager = advertiserService.pageAdvertiserByConditions(advertiser, delete, pager); // 搜索并返回
			}
		}
		
		return printJson(pager, model);
	}

	/**
	 * 添加
	 * 
	 * @param model
	 * @param advertiser
	 * @return
	 */
	@RequestMapping
	public ModelAndView add(Map<String, Object> model, Advertiser advertiser) {
		ExtResult result = new ExtResult();

		if (advertiser != null) {
			Integer id = advertiserService.insertAdvertiser(advertiser);
			if (id != null && id.intValue() > 0) {
				result.setSuccess(true);
			}
		}
		return printJson(result, model);
	}

	/**
	 * 更新
	 * 
	 * @param model
	 * @param advertiser
	 * @return
	 */
	@RequestMapping
	public ModelAndView update(Map<String, Object> model, Advertiser advertiser) {
		ExtResult result = new ExtResult();

		if (advertiser != null) {
			Integer im = advertiserService.updateAdvertiser(advertiser);
			if (im != null && im.intValue() > 0) {
				result.setSuccess(true);
			}
		}
		return printJson(result, model);
	}

	/**
	 * 删除
	 * 
	 * @param model
	 * @param items
	 *            如：1,2,3,4
	 * @return
	 */
	@RequestMapping
	public ModelAndView delete(Map<String, Object> model, String items) {
		ExtResult result = new ExtResult();

		if (items != null) {
			String[] i = items.split(",");
			Integer im = 0;
			for (String s : i) {
				if (StringUtils.isNumber(s)
						&& advertiserService.signDeleted(Integer.parseInt(s)).intValue() > 0) {
					im++;
				}
			}
			if (im != null && im.intValue() == i.length) {
				result.setSuccess(true);
			}
		}
		return printJson(result, model);
	}

	/**
	 * 读取指定记录
	 * 
	 * @param model
	 * @param id
	 *            记录编号
	 * @return
	 */
	@RequestMapping
	public ModelAndView queryById(Map<String, Object> model, String id) {
		Pager<Advertiser> pager = new Pager<Advertiser>();
		if (StringUtils.isNumber(id)) {
			Advertiser advertiser = advertiserService.queryAdvertiserById(Integer.parseInt(id));
			if (advertiser != null && advertiser.getId().intValue() > 0) {
				List<Advertiser> list = new ArrayList<Advertiser>();
				list.add(advertiser);
				pager.setRecords(list);
			}
		}
		return printJson(pager, model);
	}
}
