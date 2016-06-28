/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-1-6 by liulei.
 */
package com.ast.ast1949.web.controller.zz91.admin;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.auth.AuthUser;
import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.trust.TrustBuy;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.CompanyDto;
import com.ast.ast1949.exception.RegisterException;
import com.ast.ast1949.service.auth.AuthService;
import com.ast.ast1949.service.company.CompanyAccountService;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.trust.TrustBuyService;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.lang.StringUtils;

/**
 * 手动注册新客户
 * */
@Controller
public class AdminCompanyController extends BaseController {
	@Autowired
	private CompanyService companyService;
	@Resource
	private CompanyAccountService companyAccountService;
	@Resource
	private AuthService authService;
	@Resource
	private TrustBuyService trustBuyService;

//	Boolean success = true;// 标注操作成功
	final static String DEFAULT_PASS="135246";

	@RequestMapping
	public void index(HttpServletRequest request, Map<String, Object> out) {

	}

	@RequestMapping
	public ModelAndView queryCompany(HttpServletRequest request,
			Map<String, Object> out, Company company, CompanyAccount account,
			Date regfrom, Date regto, String activeFlag,
			PageDto<CompanyDto> page) throws IOException {

		page = companyService.pageCompanyByAdmin(company, account, regfrom,
				regto, activeFlag, page);

		return printJson(page, out);
	}
	
	@RequestMapping
	public ModelAndView registByAdmin(HttpServletRequest request, Map<String, Object> out){
		
		return null;
	}
	
	@RequestMapping
	public ModelAndView doRegistByAdmin(HttpServletRequest request, Map<String, Object> out,
			Company company, CompanyAccount account, String activeFlagCode
			) throws Exception{
		// 密码由系统生成（默认密码）
		
		ExtResult result=new ExtResult();
		do {
			try {
				companyAccountService.registerUser(account.getAccount(), account.getEmail(), DEFAULT_PASS, DEFAULT_PASS, account, company, "127.0.0.255");
			} catch (NoSuchAlgorithmException e) {
				result.setData(e.getMessage());
				break;
			} catch (UnsupportedEncodingException e) {
				result.setData(e.getMessage());
				break;
			} catch (RegisterException e) {
				result.setData(e.getMessage());
				break;
			}
			result.setData("新用户已注册！");
			result.setSuccess(true);
		} while (false);
		
		// 关联参与活动
		if (account.getCompanyId()!=null&&StringUtils.isNotEmpty(activeFlagCode)) {
			if (result.getData()!=null&&result.isSuccess()) {
				result.setData("新用户已注册！");
			}else{
				result.setData("ｏｋ，该信息已保存么");
			}
			companyService.assignActiveFlag(activeFlagCode.split(","), account.getCompanyId());
			result.setSuccess(true);
		}
		
		return printJson(result, out);
	}
	
	/**
	 * 采购频道帐号注册并关联
	 */
	@RequestMapping
	public ModelAndView doRegistByAdminForTrust(HttpServletRequest request, Map<String, Object> out,Company company, CompanyAccount account,Integer trustId) throws Exception{
		// 密码由系统生成（默认密码）
		ExtResult result=new ExtResult();
		do {
			try {
				companyAccountService.registerUser(account.getAccount(), account.getEmail(), DEFAULT_PASS, DEFAULT_PASS, account, company, "127.0.0.255");
			} catch (NoSuchAlgorithmException e) {
				result.setData(e.getMessage());
				break;
			} catch (UnsupportedEncodingException e) {
				result.setData(e.getMessage());
				break;
			} catch (RegisterException e) {
				result.setData(e.getMessage());
				break;
			}
			result.setData("新用户已注册！");
			
			TrustBuy tb = trustBuyService.queryTrustById(trustId);
			if (tb==null) {
				break;
			}
			
			Integer i = trustBuyService.relateCompanyByMobile(account.getCompanyId(), tb.getMobile());
			if (i>0) {
				result.setData(result.getData()+"绑定成功。");
			}
			
			result.setSuccess(true);
		} while (false);
		return printJson(result, out);
	}
	
	/**
	 * 根据电话号码匹配已注册的用户
	 * @throws IOException 
	 */
	@RequestMapping
	public ModelAndView initInfoByMobile(String mobile,Map<String, Object> out) throws IOException{
		CompanyDto dto = new CompanyDto();
		Integer companyId = null;
		do {
			if (StringUtils.isEmpty(mobile)) {
				break;
			}
			
			// 登陆表信息 
			AuthUser authUser = authService.queryAuthUserByMobile(mobile);
			if (authUser!=null) {
				companyId = companyAccountService.queryCompanyIdByAccount(authUser.getAccount());
				if (companyId!=null) {
					dto = companyService.queryCompanyDetailById(companyId);
					break;
				}
			}
		
			// 公司账户表
			companyId = companyAccountService.queryComapnyIdByMobile(mobile);
			if (companyId!=null) {
				dto = companyService.queryCompanyDetailById(companyId);
				break;
			}

		} while (false);
		return printJson(dto, out);
	}
	
	/**
	 * 根据email匹配已注册的用户
	 * @param email
	 */
	@RequestMapping
	public ModelAndView initInfoByEmail(String email,Map<String, Object> out) throws IOException{
		CompanyDto dto = new CompanyDto();
		Integer companyId = null;
		do {
			if (StringUtils.isEmpty(email)&&!StringUtils.isEmail(email)) {
				break;
			}
			
			// 登陆表信息 
			AuthUser authUser = authService.queryAuthUserByEmail(email);
			if (authUser!=null) {
				companyId = companyAccountService.queryCompanyIdByAccount(authUser.getAccount());
				if (companyId!=null) {
					dto = companyService.queryCompanyDetailById(companyId);
					break;
				}
			}
		
			// 公司账户表
			companyId = companyAccountService.queryCompanyIdByEmail(email);
			if (companyId!=null) {
				dto = companyService.queryCompanyDetailById(companyId);
				break;
			}

		} while (false);
		return printJson(dto, out);
	}
	
	/**
	 * 关联活动标记
	 * @throws IOException 
	 * */
	@RequestMapping
	public ModelAndView reAssignActiveFlag(HttpServletRequest request, Map<String, Object> out,
			String activeFlag, String activeFlagCode, Integer companyId) throws IOException{
		
		companyService.reAssignActiveFlag(activeFlag, activeFlagCode.split(","), companyId);
		
		ExtResult result =new ExtResult();
		result.setSuccess(true);
		return printJson(result, out);
	}
	
	/**
	 * 导出某一类活动的公司信息，导出格式为Excel
	 * @throws IOException 
	 * @throws WriteException 
	 * */
	@RequestMapping
	public ModelAndView exportCompany(HttpServletRequest request, HttpServletResponse response, Map<String, Object> out,
			String activeFlag) throws IOException, WriteException{
		List<CompanyDto> list=companyService.exportCompanyByAdmin(new Company(), new CompanyAccount(), null, null, activeFlag);
		
		//生成Excel
		//提供下载
		response.setContentType("application/msexcel");
		
		OutputStream os = response.getOutputStream();
		
		WritableWorkbook wwb = Workbook.createWorkbook(os);//创建可写工作薄   
		WritableSheet ws = wwb.createSheet("sheet1", 0);//创建可写工作表
		
		ws.addCell(new Label(0,0,"公司名称"));
		ws.addCell(new Label(1,0,"电话"));
		ws.addCell(new Label(2,0,"账号"));
		ws.addCell(new Label(3,0,"Email"));
		ws.addCell(new Label(4,0,"参与活动"));
		ws.addCell(new Label(5,0,"会员类型"));
		ws.addCell(new Label(6,0,"注册来源"));
		ws.addCell(new Label(7,0,"主营产品"));
		ws.addCell(new Label(8,0,"联系人"));
		int i=1;
		for(CompanyDto comp:list){
			ws.addCell(new Label(0,i,comp.getCompany().getName()));
			ws.addCell(new Label(1,i,comp.getAccount().getMobile()));
			ws.addCell(new Label(2,i,comp.getAccount().getAccount()));
			ws.addCell(new Label(3,i,comp.getAccount().getEmail()));
			ws.addCell(new Label(4,i,comp.getCompany().getActiveFlag()));
			ws.addCell(new Label(5,i,comp.getMembershipLabel()));
			ws.addCell(new Label(6,i,comp.getRegfromLabel()));
			ws.addCell(new Label(7,i,comp.getCompany().getBusiness()));
			ws.addCell(new Label(8,i,comp.getAccount().getContact()));
			i++;
		}
		
//		Label labelCF=new Label(0, 0, "hello");//创建写入位置和内容   
//		ws.addCell(labelCF);//将Label写入sheet中   
		//Label的构造函数Label(int x, int y,String aString)  xy意同读的时候的xy,aString是写入的内容.   
//		WritableFont wf = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, false);//设置写入字体   
//		WritableCellFormat wcfF = new WritableCellFormat(wf);//设置CellFormat   
		
//		Label labelCF=new Label(0, 0, "hello");//创建写入位置,内容和格式   
		//Label的另一构造函数Label(int c, int r, String cont, CellFormat st)可以对写入内容进行格式化,设置字体及其它的属性.   
		//现在可以写了   
		wwb.write();
		//写完后关闭   
		wwb.close();
		//输出流也关闭吧   
		os.close(); 
		
		return null;
	}

	/************************************/

//	@RequestMapping
//	public void list() {
//	}
//
//	@RequestMapping
//	public void collectList() throws IOException {
//	}

//	@RequestMapping
//	public ModelAndView query(Company company, CompanyAccount account,
//			PageDto page, Map<String, Object> model) throws IOException {
//		page = companyService.pageCompanyByAdmin(company, account, null, null,
//				null, page);
//
//		// companyDetails.setCompany(company);
//		// companyDetails.setCompanyContacts(companyContacts);
//		// companyDetails.setPageDto(page);
//		// page.setTotalRecords(companyService.selectCompanyCount(companyDetails));
//		// List<CompanyDetailsDTO> list = companyService.query(companyDetails);
//
//		// page.setRecords(list);
//		return printJson(page, model);
//	}

	// @RequestMapping
	// public ModelAndView add(CompanyContactsDO companyContactsDO,
	// CompanyDO companyDO, Map<String, Object> model,
	// HttpServletRequest req, HttpServletResponse res) throws IOException {
	//
	// ExtResult result = new ExtResult();
	// // 根据email和Mobile查询出CompanyContactsDO信息
	// List<CompanyContactsDO> list = companyService
	// .selectContactsByEmailAndMobileAndAccount(companyContactsDO);
	// if (list.size() == 0) {
	// // 添加companyDO信息
	// Integer i1 = companyService.insertCompany(companyDO);
	// // 把companyDO中id传给companyContactsDO表companyId
	// companyContactsDO.setCompanyId(i1);
	// // 添加CompanyContactsDO信息
	// Integer i2 = companyService
	// .insertCompanyContacts(companyContactsDO);
	// // 添加CompanyAccessgradeDO信息
	// CompanyAccessGradeDO companyAccessGrade = new CompanyAccessGradeDO();
	// companyAccessGrade.setAccessGradCode(AstConst.BLACK_CODE);
	// companyAccessGrade.setCompanyId(companyDO.getId());
	// companyAccessGrade.setAccount(companyContactsDO.getAccount());
	// // int i3 = companyService
	// // .insertCompanyAccessGrade(companyAccessGrade);
	// if (i1 > 0 && i2 > 0) {
	// result.setSuccess(true);
	// }
	// }
	// return printJson(result, model);
	// }

	// @RequestMapping
	// public ModelAndView addOrUpdate(CompanyContactsDO companyContactsDO,
	// CompanyDO companyDO, Map<String, Object> model,
	// HttpServletRequest req, HttpServletResponse res) throws IOException {
	//
	// Integer i1 = 0;
	// Integer i2 = 0;
	// ExtResult result = new ExtResult();
	// // 根据email和Mobile查询出CompanyContactsDO信息
	// List<CompanyContactsDO> list = companyService
	// .selectContactsByEmailAndMobileAndAccount(companyContactsDO);
	// if (list.size() == 0) {
	// // 添加companyDO信息
	// i1 = companyService.insertCompany(companyDO);
	// // 把companyDO中id传给companyContactsDO表companyId
	// companyContactsDO.setCompanyId(i1);
	// // 添加CompanyContactsDO信息
	// i2 = companyService.insertCompanyContacts(companyContactsDO);
	// // 添加CompanyAccessgradeDO信息
	// CompanyAccessGradeDO companyAccessGrade = new CompanyAccessGradeDO();
	// companyAccessGrade.setAccessGradCode(AstConst.BLACK_CODE);
	// companyAccessGrade.setCompanyId(companyDO.getId());
	// companyAccessGrade.setAccount(companyContactsDO.getAccount());
	// // int i3 = companyService
	// // .insertCompanyAccessGrade(companyAccessGrade);
	// if (i1 > 0 && i2 > 0) {
	// result.setSuccess(true);
	// }
	// }
	// // else if
	// // (companyContactsDO.getMobile().trim().equals(list.get(0).getMobile())
	// // &&
	// // companyContactsDO.getEmail().trim().equals(list.get(0).getEmail()))
	// else if (list.size() == 1) {
	// companyContactsDO.setCompanyId(companyDO.getId());
	// // 修改公司所有信息(搜集公司信息时判断是否存在，存在时修改)
	// i1 = companyService.updatePartCompanyByIdCollect(companyDO);
	// // 修改CompanyContactsDO信息(搜集公司联系信息时判断是否存在，存在时修改)
	// i2 = companyService
	// .updatePartContactsByIdCollect(companyContactsDO);
	// if (i1 > 0 && i2 > 0) {
	// result.setSuccess(true);
	// result.setData("1");
	// }
	// }
	// return printJson(result, model);
	// }

//	@RequestMapping
//	public ModelAndView edit(Map<String, Object> out, String account,
//			Integer companyId) throws UnsupportedEncodingException {
//		if (companyId == null) {
//			companyId = 0;
//		}
//		if (account != null && account.length() > 0) {
//			account = StringUtils.decryptUrlParameter(account);
//		} else {
//			// 找到默认联系人
//			CompanyAccount companyAccount = companyAccountService
//					.queryAdminAccountByCompanyId(companyId);
//			// CompanyContactsDO contact =
//			// companyContactsService.getDefaultCompanyContactsByCompanyId(companyId);
//			if (companyAccount != null && companyAccount.getId().intValue() > 0) {
//				account = companyAccount.getAccount();
//			}
//		}
//		out.put("companyId", companyId);
//		out.put("account", account);
//		return null;
//	}
//
//	@RequestMapping
//	public ModelAndView queryById(Company company, String account,
//			HttpServletRequest request, Map<String, Object> out)
//			throws IOException {
//		// 解决中文乱码
//		account = StringUtils.decryptUrlParameter(account);
//
//		CompanyDto companyDetails = companyService
//				.queryCompanyDetailById(company.getId());
//
//		companyDetails.setAccount(companyAccountService
//				.queryAccountByAccount(account));
//
//		List<CompanyDto> list = new ArrayList<CompanyDto>();
//		list.add(companyDetails);
//		PageDto<CompanyDto> page = new PageDto<CompanyDto>();
//		page.setRecords(list);
//		return printJson(page, out);
//	}
//
//	@RequestMapping(value = "update.htm", method = RequestMethod.POST)
//	public ModelAndView update(Integer companyContactsId,
//			CompanyAccount companyAccount, Company company,
//			Map<String, Object> model) throws IOException {
//		// TODO 重构
//		ExtResult result = new ExtResult();
//		companyService.updateCompanyByAdmin(company);
//		companyAccountService.updateAccountByAdmin(companyAccount);
//		result.setSuccess(true);
//		// 根据email查询出CompanyContactsDO信息
//		// List<CompanyAccount> list=companyAccountService.queryAccountBy
//		// List<CompanyContactsDO> list = companyService
//		// .selectContactsByEmailAndMobileAndAccount(companyContactsDO);
//		// if (list.size() == 0 || list.size() == 1) {
//		// TODO 是否需要更改公司账户信息
//		// i3 = companyAccountService.updateAccountByAdmin(companyContactsDO);
//
//		// if (companyDO.getClassifiedCode() == null) {
//		// // 修改companyDO信息
//		// i2 = companyService.updateCompanyById(companyDO);
//		// // 修改CompanyContactsDO信息
//		// i3 = companyService.updateContactsById(companyContactsDO);
//		// } else {
//		// // 修改公司所有信息(修改供求信息时，修改公司信息)
//		// i2 = companyService.updateCompanyByIdAndPro(companyDO);
//		// // 修改CompanyContactsDO信息(修改供求信息时，修改公司联系信息)
//		// i3 = companyService.updateContactsByIdAndPro(companyContactsDO);
//		// }
//		// 修改CompanyContactsDO信息
//		// if (i2 > 0 ) {
//		// result.setSuccess(success);
//		// }
//		// }
//		return printJson(result, model);
//	}

	// @RequestMapping
	// public ModelAndView delete(String ids, Map<String, Object> model)
	// throws IOException {
	// ExtResult result = new ExtResult();
	// // 分割从前台获得的ids字符串
	// String[] entities = ids.split(",");
	// // 根据companyDO中的id删除CompanyDO
	// int delCompany = companyService.deleteCompanyById(ids);
	// // 根据companyContactsDO中company_id的值删除CompanyContactsDO信息
	// int delCompanyContacts = companyService.deleteContactsById(ids);
	// if (delCompany != entities.length
	// && delCompanyContacts != entities.length) {
	// success = false;
	// }
	// result.setSuccess(success);
	// return printJson(result, model);
	// }

	// @RequestMapping
	// public ModelAndView gradeaccess(Integer ids, String account, String val,
	// Map<String, Object> model) throws IOException {
	// Integer i = null;
	// ExtResult result = new ExtResult();
	// //TODO 设置黑名单
	// // 查询CompanyAccessgradeDO信息
	// // CompanyAccessGradeDO companyAccessGradeDO = companyService
	// // .selectCompanyAccessGradeById(ids);
	// // if (companyAccessGradeDO == null) {
	// // CompanyAccessGradeDO companyAccessGrade = new CompanyAccessGradeDO();
	// // companyAccessGrade.setCompanyId(ids);
	// // companyAccessGrade.setAccount(account);
	// // companyAccessGrade.setAccessGradCode(val);
	// // // 添加信息
	// // i = companyAccessGradeService
	// // .insertCompanyAccessGrade(companyAccessGrade);
	// // } else {
	// // companyAccessGradeDO.setAccessGradCode(val);
	// // // 修改信息
	// // i = companyService
	// // .updateCompanyAccessGradeById(companyAccessGradeDO);
	// // }
	// // if (i > 0) {
	// // result.setSuccess(success);
	// // }
	// return printJson(result, model);
	// }

	// @RequestMapping
	// public ModelAndView queryByInfoSourceCode(CompanyDO company,
	// CompanyContactsDO companyContacts, PageDto page,
	// CompanyDetailsDTO companyDetails, Map<String, Object> model)
	// throws IOException {
	// if (page == null) {
	// page = new PageDto(AstConst.PAGE_SIZE);
	// }
	// companyDetails.setCompany(company);
	// companyDetails.setCompanyContacts(companyContacts);
	// companyDetails.setPageDto(page);
	// page.setTotalRecords(companyService
	// .queryByInfoSourceCodeCount(companyDetails));
	// List<CompanyListInAdminDTO> list = companyService
	// .queryByInfoSourceCode(companyDetails);
	// page.setRecords(list);
	// return printJson(page, model);
	// }
}
