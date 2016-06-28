/**
 * 
 */
package com.zz91.ep.admin.controller.data;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.admin.controller.BaseController;
import com.zz91.ep.admin.service.sys.DataGatherService;
import com.zz91.ep.admin.service.sys.ParamService;
import com.zz91.ep.dto.data.CompAccountMakeMap;
import com.zz91.ep.dto.data.CompMakeMap;
import com.zz91.util.domain.Param;
import com.zz91.util.lang.RandomUtils;
import com.zz91.util.lang.StringUtils;

/**
 * @author mays (mays@zz91.net)
 *
 * created by 2011-10-25
 */
@Controller
public class CompMakeController extends BaseController {
	
	@Resource
	private DataGatherService dataGatherService;
	@Resource
	private ParamService paramService;
	
	@RequestMapping
	public ModelAndView initImpt(HttpServletRequest request, Map<String, Object> out){
		List<Param> list=paramService.queryParamByType(REGISTER_TYPE);
		out.put("list", list);
		return null;
	}
	
	@RequestMapping
	public ModelAndView impt(HttpServletRequest request, Map<String, Object> out, 
			CompAccountMakeMap accountMap, CompMakeMap compMap,
			String sourceFlag, String excelFlag, Integer isRight,Integer mainUse){
		
//		if("1".equals(excelFlag)){
//			imptAccount(request, accountMap, compMap, sourceFlag, isRight);
//		}else{
//		}
		long start=System.currentTimeMillis();
		Map<String, String> importlog=imptComp(request, accountMap, compMap, sourceFlag, isRight,mainUse);
		long end=System.currentTimeMillis();
		out.put("importLog", importlog);
		out.put("costTime", end-start);
		return null;
	}
	
	/**
	 * 阿里巴巴邮箱导入方法
	 * @param request
	 * @param out
	 * @param account
	 * @param email
	 * @return
	 */
	@RequestMapping
	public ModelAndView imptEmail(HttpServletRequest request, Map<String, Object> out, 
			Integer accountInt, Integer emailInt){
		
		long start=System.currentTimeMillis();
		//帐号为空的去掉
		MultipartRequest multipartRequest = (MultipartRequest) request;
		MultipartFile file = multipartRequest.getFile("excel");
		Map<String, String> errorInfo=new HashMap<String, String>();
		InputStream in=null;
		try {
			in=new BufferedInputStream(file.getInputStream());
			
			HSSFWorkbook wb=new HSSFWorkbook(in);
			HSSFSheet sheet=wb.getSheetAt(0);
			
			int f=sheet.getFirstRowNum();
			int l=sheet.getLastRowNum();
			
			HSSFRow row;
			for(int i=f+1;i<=l;i++){
				row = sheet.getRow(i);
				if(row==null){
					break;
				}
				
				String account=null;
				String email = null;
				if (row.getCell(accountInt)!=null && row.getCell(accountInt).getRichStringCellValue()!=null){
					account = row.getCell(accountInt).getRichStringCellValue().toString();
				}
				if(row.getCell(emailInt) != null && row.getCell(emailInt).getRichStringCellValue() != null){
					email=row.getCell(emailInt).getRichStringCellValue().getString();
				}
				if(StringUtils.isNotEmpty(account) && StringUtils.isNotEmpty(email)){
					Integer uid = dataGatherService.updateAlibabaEmail(account, email);
					if(uid==null){
						//没有保存的信息
						errorInfo.put(row.getCell(accountInt).getRichStringCellValue().toString(), "Error:email已经存在");
						continue;
					}
				}
				
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(in!=null){
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		long end=System.currentTimeMillis();
		out.put("costTime", end-start);
		return null;
	}
	
	private Map<String, String> imptComp(HttpServletRequest request, 
			CompAccountMakeMap accountMap, CompMakeMap compMap, String sourceFlag, Integer isRight,Integer mainUse){
		MultipartRequest multipartRequest = (MultipartRequest) request;
		
		MultipartFile file = multipartRequest.getFile("excel");
		
		if(StringUtils.isEmpty(sourceFlag)){
			sourceFlag="ep";
		}
		
		Map<String, String> errorInfo=new HashMap<String, String>();
		InputStream in=null;
		try {
			in=new BufferedInputStream(file.getInputStream());
			
			HSSFWorkbook wb=new HSSFWorkbook(in);
			HSSFSheet sheet=wb.getSheetAt(0);
			
			int f=sheet.getFirstRowNum();
			int l=sheet.getLastRowNum();
			
			HSSFRow row;
//			short cf=0;
//			short cl=0;
			for(int i=f+1;i<=l;i++){
				row = sheet.getRow(i);
				if(row==null){
					continue;
				}

				/*if(!row.getCell(isRight).getBooleanCellValue()){
					continue;
				}*/
				
				String account=null;
				if (row.getCell(accountMap.getAccount())!=null){
					try {
						Double d=row.getCell(accountMap.getAccount()).getNumericCellValue();
						NumberFormat format = new DecimalFormat("#");
						account=format.format(d);
					} catch (Exception e) {
						account=row.getCell(accountMap.getAccount()).getRichStringCellValue().toString();
					}
				}
				
				if(StringUtils.isEmpty(account)){
					break;
				}
				
				Integer cid = dataGatherService.createCompProfile(compMap, account, row, sourceFlag,mainUse);
				if(cid==null){
					//没有保存的信息
					errorInfo.put(row.getCell(accountMap.getAccount()).getRichStringCellValue().toString(), "Error:Company");
					continue;
				}
				
				if(dataGatherService.createCompAccount(cid, account, accountMap, row)){
					errorInfo.put(account, "Success");
				}else{
					errorInfo.put(account, "Error:Account");
				}
//				System.out.println(row.getLastCellNum()+":"+row.getCell(row.getLastCellNum()));
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(in!=null){
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return errorInfo;
	}
	
//	private void imptAccount(HttpServletRequest request, 
//			CompAccountMakeMap accountMap, CompMakeMap compMap, String sourceFlag, Integer isRight){
//		
//	}
	
//	private Integer createCompProfile(CompMakeMap compMap, String account, HSSFRow row){
//		CompAccount compAccount=compAccountService.queryAccountDetails(account);
////		CompProfile compProfile = null;
//		Integer cid=null;
//		if(compAccount==null){
//			//创建新公司信息
//			CompProfile compProfile = new CompProfile();
//			compProfile = buildCompProfile(compProfile, compMap, row);
//			cid=dataGatherService.createProfile(compProfile);
//			if(cid==null){
//				return null;
//			}
//		}else{
//			//获取公司信息，并更新公司信息
//			CompProfile compProfile = compProfileService.queryFullProfile(compAccount.getCid());
//			compProfile = buildCompProfile(compProfile, compMap, row);
//			dataGatherService.updateCompProfile(compProfile);
//			cid=compProfile.getId();
//		}
//		return cid;
//	}
	
//	private Boolean createCompAccount(Integer cid, String account, CompAccountMakeMap accountMap, HSSFRow row){
//		
//		CompAccount compAccount=compAccountService.queryAccountDetails(account);
//		Integer impact=null;
//		if(compAccount==null){
//			compAccount = new CompAccount();
//			compAccount.setAccount(account);
//			compAccount.setCid(cid);
//			compAccount = buildCompAccount(compAccount, accountMap, row);
//			impact=dataGatherService.createAccount(compAccount);
//		}else{
//			compAccount = buildCompAccount(compAccount, accountMap, row);
//			impact=dataGatherService.updateAccount(compAccount);
//		}
//		if(impact!=null && impact.intValue()>0){
//			return true;
//		}
//		return false;
//	}
	
//	private CompProfile buildCompProfile(CompProfile profile, CompMakeMap compMap, HSSFRow row){
//		
//		profile.setMemberCode("10011000");
//		profile.setMemberCodeBlock("");
//		profile.setMessageCount(0);
//		profile.setViewCount(0);
//		
//		if(compMap.getCompName()!=null){
//			profile.setName(row.getCell(compMap.getCompName()).getRichStringCellValue().getString());
//		}
//		if(StringUtils.isEmpty(profile.getName())){
//			profile.setName("");
//		}
//		
//		if(compMap.getAddress()!=null){
//			String tmp=row.getCell(compMap.getAddress()).getRichStringCellValue().getString();
//			profile.setAddress(Jsoup.clean(tmp, Whitelist.none()));
//		}
//		
//		if(compMap.getAddressZip()!=null){
//			profile.setAddressZip(row.getCell(compMap.getAddressZip()).getRichStringCellValue().getString());
//		}
//		
////		if(compMap.getAreaName()!=null){
////			
////		}
//		
//		if(compMap.getDetails()!=null){
//			String tmp=row.getCell(compMap.getDetails()).getRichStringCellValue().getString();
//			String tmpquery=Jsoup.clean(tmp, Whitelist.none());
//			if(tmpquery.length()>990){
//				tmpquery=tmpquery.substring(0, 990);
//			}
//			if(tmp.length()>4800){
//				tmp=tmp.substring(0, 4800);
//			}
//			profile.setDetailsQuery(tmpquery);
//			profile.setDetails(tmp);
//		}
//		
//		if(compMap.getFounds()!=null){
//			profile.setFunds(row.getCell(compMap.getFounds()).getRichStringCellValue().getString());
//		}
//		
//		if(compMap.getLegal()!=null){
//			profile.setLegal(row.getCell(compMap.getLegal()).getRichStringCellValue().getString());
//		}
//		
//		if(compMap.getMainBrand()!=null){
//			profile.setMainBrand(row.getCell(compMap.getMainBrand()).getRichStringCellValue().getString());
//		}
//		
//		profile.setMainBuy(LOGIC_TRUE);
//		if(compMap.getMainProductBuy()!=null){
//			profile.setMainProductBuy(row.getCell(compMap.getMainProductBuy()).getRichStringCellValue().getString());
//		}
//		
//		profile.setMainSupply(LOGIC_TRUE);
//		if(compMap.getMainProductSupply()!=null){
//			profile.setMainProductSupply(row.getCell(compMap.getMainProductSupply()).getRichStringCellValue().getString());
//		}
//		
//		return profile;
//	}
	
//	private CompAccount buildCompAccount(CompAccount account, CompAccountMakeMap accountMap, HSSFRow row){
//		account.setPassword(PWD_ENCODE);
//		account.setPasswordClear(PWD_CLEAR);
//		account.setGmtLogin(new Date());
//		account.setGmtRegister(new Date());
//		
//		if(accountMap.getAccountName()!=null){
//			String tmp=row.getCell(accountMap.getAccountName()).getRichStringCellValue().getString();
//			if(StringUtils.isNotEmpty(tmp)){
//				String[] np=tmp.split(" ");
//				if(np.length>=2){
//					account.setPosition(np[1]);
//					account.setName(np[0]);
//				}else{
//					account.setName(tmp);
//				}
//			}
//		}
//		
//		if(StringUtils.isEmpty(account.getName())){
//			account.setName("");
//		}
//		
//		if(accountMap.getEmail()!=null){
//			account.setEmail(row.getCell(accountMap.getEmail()).getRichStringCellValue().getString());
//		}
//		if(StringUtils.isEmpty(account.getEmail())){
//			account.setEmail(account.getAccount()+EMAIL_DOMAIN);
//		}
//		
//		if(accountMap.getPhone()!=null){
//			account.setPhone(row.getCell(accountMap.getPhone()).getRichStringCellValue().getString());
//		}
//		
//		if(accountMap.getFax()!=null){
//			account.setFax(row.getCell(accountMap.getFax()).getRichStringCellValue().getString());
//		}
//		
//		if(accountMap.getMobile()!=null){
//			account.setMobile(row.getCell(accountMap.getMobile()).getRichStringCellValue().getString());
//		}
//		
//		if(accountMap.getContact()!=null){
//			account.setContact(row.getCell(accountMap.getContact()).getRichStringCellValue().getString());
//		}
//		
//		return account;
//	}
	
	
	public static void main(String[] args) {
		System.out.println("广州市天河玉阀机电经营部位于广东省广州市天河区棠下五金阀门批发市场，厂部位于上海市松江泗泾工业园区。公司是一家生产工业阀门、仪表阀门及给排水阀门的专业制造及销售公司，具有丰富的设计、制造、管理与服务的经验，并取得ISO9001国际管理体系，是中石化物资源市场成员厂、中石油一级供应网络企业和国家电力公司电站配件供应网络成员厂，建立了较先进的企业管理系统和完美的质量保证体系。 公司具有雄厚的国际管理经验和汇聚业界技术精英，现有员工875人，工程技术人员56人，高级工程师21人。生产规模化、集约化、现代化，现有厂房占地面积102300平方米，建筑面积76000平方米。厂间设有热模锻、精密铸造、焊接、热处理、机械加工装配等车间。拥有加工中心、数控机床、等离子堆焊、全自动气体保护焊、超频真空热处理设备、车球专用数控机床、阀门加工专机、水帘节能自动喷漆线等先进设备。 公司采用Pro/E设计软件和先进的设备设计、制造美国API、ANSI、英国BS、德国DIN、法国NF、日本JIS、中国GB等标准的各类的各类ALISON产品，其中金属密封球阀、大口径长输管线球阀、大口径三维偏心蝶阀等产品，获得一系列国家专利。阀门口径：1/4”-80”(6mm-2000mm),压力级：150LB-2500LB（0.1MPa-42MPa），工作温度：-196℃—680℃。 连接形式：螺纹、法兰、焊接及对夹， 材质有碳钢、不锈钢、合金钢、铬钼钢、低温钢、球墨铸铁及其他特殊钢。 驱动方式有手动、蜗轮传动、齿轮传动、气动、液动、电动等产品广泛应用于石油、化工、电力、冶炼、制药、纺织、给排水、城建等行业及长输管线等工程，畅销全国各地，同时出口美国、英国、日本及其他国家和地区。 “创新无止境”是埃里森人的精神、在流体控制领域我们取得了卓越的成绩，产品科技含量高。我们将继续努力、设计制造出更好的产品呈献给广大用户，并做好售后与服务技术协助等方面的工作。秉承“一切为了用户满意”的经营理念，以“可用性、可信性、可靠性”三性为目标，创建“精一阀门.精益求精”的一个品牌，以合理的价位、完善的售前、售中、售后服务满足广大用户的需求，热忱欢迎国内外客商光临指导洽谈，并希望与社会各界与我们携手并进，加强合作、共创辉煌。本公司主要经营阀门．机电产品,等．送货上门质量保证。本公司秉承“顾客至上，锐意进取”的经营理念，坚持“客户第一”的原则为广大客户提供优质的服务。欢迎广大客户惠顾！".length());
	}
}
