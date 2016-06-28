/**
 * @author shiqp
 * @date 2016-01-11
 */
package com.ast.feiliao91.service.company.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;

import com.ast.feiliao91.domain.common.DataIndexDO;
import com.ast.feiliao91.domain.company.CompanyAccount;
import com.ast.feiliao91.domain.company.CompanyInfo;
import com.ast.feiliao91.domain.company.CompanySearch;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.dto.company.CompanyDto;
import com.ast.feiliao91.persist.company.CompanyAccountDao;
import com.ast.feiliao91.persist.company.CompanyInfoDao;
import com.ast.feiliao91.persist.company.CompanyServiceDao;
import com.ast.feiliao91.persist.company.JudgeDao;
import com.ast.feiliao91.persist.goods.GoodsDao;
import com.ast.feiliao91.service.company.CompanyInfoService;
import com.ast.feiliao91.service.company.CompanyServiceService;
import com.ast.feiliao91.service.facade.CategoryFacade;
import com.zz91.util.lang.StringUtils;

@Component("companyInfoService")
public class CompanyInfoServiceImpl implements CompanyInfoService {
	@Resource
	private CompanyInfoDao companyInfoDao;
	@Resource
	private CompanyAccountDao companyAccountDao;
	@Resource
	private JudgeDao judgeDao;
	@Resource
	private GoodsDao goodsDao;
	@Resource
	private CompanyServiceDao companyServiceDao;

	@Override
	public Integer insertCompanyInfo(CompanyInfo companyInfo) {
		return companyInfoDao.insertCompanyInfo(companyInfo);
	}
	@Override
	public CompanyInfo queryInfoByid(Integer id) {
		return companyInfoDao.queryById(id);
	}
	@Override
	public CompanyDto queryCompanyDtoById(Integer id) {
		if (id==null||id<1) {
			return null;
		}
		CompanyDto company = new CompanyDto();
		CompanyInfo c = queryInfoByid(id);
		if (c==null) {
			return null;
		}
		CompanyAccount ca = companyAccountDao.queryByCompanyId(c.getId());
		if (ca==null) {
			return null;
		}
		company.setTradeNum(judgeDao.countTradeNum(c.getId()));
		company.setAddress(getArea(c.getArea())+c.getAddress());
		company.setCompanyAccount(ca);
		company.setCompanyInfo(c);
		return company;
	}
	
	@Override
	public Integer updateValidate(CompanyInfo companyInfo) {
		return companyInfoDao.updateValidate(companyInfo);
	}
	private String getArea(String areaCode){
		String str ="";
		Integer i = 8;
		String tempCode = "";
		do {
			String fix ="";
			if (StringUtils.isEmpty(areaCode)) {
				break;
			}
			i = i + 4;
			if (areaCode.length()<i) {
				break;
			}
			tempCode = areaCode.substring(0, i);
			if (i==12) {
				fix = "省";
			}else if(i==16){
				fix = "市";
			}
			str = str + CategoryFacade.getInstance().getValue(tempCode) + fix;
		} while (true);
		return str;
	}
	@Override
	public List<CompanyDto> bulidCompanyDtoListForIndex(List<DataIndexDO> list) {
		List<CompanyDto> nlist =new ArrayList<CompanyDto>();
		for (DataIndexDO obj:list) {
			CompanyDto dto = new CompanyDto();
			CompanyInfo c = queryInfoByid(Integer.valueOf(obj.getTitle()));
			if (c==null) {
				continue;
			}
			CompanyAccount ca = companyAccountDao.queryByCompanyId(c.getId());
			if (ca==null) {
				ca = new CompanyAccount();
			}
			dto.setTradeNum(judgeDao.countTradeNum(c.getId()));
			dto.setAddress(getArea(c.getArea())+c.getAddress());
			dto.setCompanyAccount(ca);
			dto.setCompanyInfo(c);
			dto.setTotalSuccessNum(goodsDao.querySuccessOrder(null, c.getId()));
			dto.setDegreeSatisfaction(judgeDao.avgGoodStar(c.getId()));
			// 检索服务 7天包退 保证金
			Integer i = companyServiceDao.queryServiceCount(c.getId(), CompanyServiceService.SEVEN_DAY_SERVICE);
			if (i>0) {
				dto.setSevenDayFlag(1);
			}
			Integer j = companyServiceDao.queryServiceCount(c.getId(), CompanyServiceService.BZJ_SERVICE);
			if (j>0) {
				dto.setBzjFlag(1);
			}
			// 封面logo
			dto.setLogo(obj.getPic());
			nlist.add(dto);
		}
		return nlist;
	}
	@Override
	public PageDto<CompanyDto> pageBySearch(PageDto<CompanyDto> page, CompanySearch search) {
		List<CompanyInfo> list = companyInfoDao.queryCompanyList(page, search);
		List<CompanyDto> resultList = new ArrayList<CompanyDto>();
		for(CompanyInfo info : list){
			CompanyDto dto = new CompanyDto();
			CompanyAccount account = companyAccountDao.queryByCompanyId(info.getId());
			if(account == null){
				account = new CompanyAccount();
			}
			if(StringUtils.isNotEmpty(info.getCreditInfo())){
				JSONObject json = JSONObject.fromObject(info.getCreditInfo());
				if(json!=null && json.get("bus")!=null){
					Object bus = json.get("bus");
					JSONObject compObj = JSONObject.fromObject(bus);
					if(compObj!=null && compObj.get("companyName")!=null){
						dto.setCompanyName(compObj.get("companyName").toString());
					}
				}
				if(json!=null && json.get("one")!=null){
					Object one = json.get("one");
					JSONObject compObj = JSONObject.fromObject(one);
					if(compObj!=null && compObj.get("companyName")!=null){
						dto.setOneName(compObj.get("companyName").toString());
					}
				}
			}
			if(StringUtils.isNotEmpty(info.getArea())){
				if(	info.getArea().length()==8){
					dto.setAddress(CategoryFacade.getInstance().getValue(info.getArea()));
				}else if(info.getArea().length()>8&&info.getArea().length()<13){
					dto.setAddress(CategoryFacade.getInstance().getValue(info.getArea().substring(0, 8)) + " " + CategoryFacade.getInstance().getValue(info.getArea()));
				}else if(info.getArea().length()>12){
					dto.setAddress(CategoryFacade.getInstance().getValue(info.getArea().substring(0, 12)) + " " + CategoryFacade.getInstance().getValue(info.getArea().substring(0, 16)));
				}
			}else{
				dto.setAddress("中国");
			}
			dto.setCompanyInfo(info);
			dto.setCompanyAccount(account);
			resultList.add(dto);
		}
		page.setRecords(resultList);
		page.setTotalRecords(companyInfoDao.countCompanyList(search));
		return page;
	}
	
	@Override
	public PageDto<CompanyDto> pageBySearchAdmin(PageDto<CompanyDto> page, CompanySearch search) {
		List<CompanyInfo> list = companyInfoDao.queryCompanyList(page, search);
		List<CompanyDto> resultList = new ArrayList<CompanyDto>();
		for(CompanyInfo info : list){
			CompanyDto dto = new CompanyDto();
			CompanyAccount account = companyAccountDao.queryByCompanyId(info.getId());
			if(account == null){
				account = new CompanyAccount();
			}
			if(StringUtils.isNotEmpty(info.getCreditInfo())){
				JSONObject json = JSONObject.fromObject(info.getCreditInfo());
				if(json!=null && json.get("bus")!=null){
					Object bus = json.get("bus");
					JSONObject compObj = JSONObject.fromObject(bus);
					if(compObj!=null && compObj.get("companyName")!=null){
						dto.setCompanyName(compObj.get("companyName").toString());
					}
				}
				if(json!=null && json.get("one")!=null){
					Object one = json.get("one");
					JSONObject compObj = JSONObject.fromObject(one);
					if(compObj!=null && compObj.get("companyName")!=null){
						dto.setOneName(compObj.get("companyName").toString());
					}
				}
			}
			if(StringUtils.isNotEmpty(info.getArea())){
				if(	info.getArea().length()==8){
					dto.setAddress(CategoryFacade.getInstance().getValue(info.getArea()));
				}else if(info.getArea().length()>8 && info.getArea().length()<13){
					dto.setAddress(CategoryFacade.getInstance().getValue(info.getArea().substring(0, 8)) + " " + CategoryFacade.getInstance().getValue(info.getArea()));
				}else if(info.getArea().length()>12 && info.getArea().length()<17){
					dto.setAddress(CategoryFacade.getInstance().getValue(info.getArea().substring(0, 12)) + " " + CategoryFacade.getInstance().getValue(info.getArea()));
				}else{
					dto.setAddress(CategoryFacade.getInstance().getValue(info.getArea().substring(0, 12)) + " " + CategoryFacade.getInstance().getValue(info.getArea().substring(0, 16)) + " " + CategoryFacade.getInstance().getValue(info.getArea()));
				}
//				if(	info.getArea().length()==8){
//					dto.setAddress(CategoryFacade.getInstance().getValue(info.getArea()));
//				}else if(info.getArea().length()>8&&info.getArea().length()<13){
//					dto.setAddress(CategoryFacade.getInstance().getValue(info.getArea().substring(0, 8)) + " " + CategoryFacade.getInstance().getValue(info.getArea()));
//				}else if(info.getArea().length()>12){
//					dto.setAddress(CategoryFacade.getInstance().getValue(info.getArea().substring(0, 12)) + " " + CategoryFacade.getInstance().getValue(info.getArea().substring(0, 16)));
//				}
			}else{
				dto.setAddress("中国");
			}
			dto.setCompanyInfo(info);
			dto.setCompanyAccount(account);
			resultList.add(dto);
		}
		page.setRecords(resultList);
		page.setTotalRecords(companyInfoDao.countCompanyList(search));
		return page;
	}
	
	@SuppressWarnings({"unused" })
	@Override
	public String updateStatus(String ids,Integer checkStatus){
		StringBuffer sb = new StringBuffer();
		String[] str = ids.split(",");
		for (String s : str) {
			Integer i = companyInfoDao.updateStatus(Integer.valueOf(s),checkStatus);
			if (Integer.valueOf(1).equals(i)) {
				sb.append(s);
			}
		}
		if (sb == null) {
			return null;
		}
		return sb.toString();
	}
	
	@SuppressWarnings({"unused" })
	@Override
	public String updateDelStatus(String ids,Integer checkStatus){
		StringBuffer sb = new StringBuffer();
		String[] str = ids.split(",");
		for (String s : str) {
			Integer i = companyInfoDao.updateDelStatus(Integer.valueOf(s),checkStatus);
			if (Integer.valueOf(1).equals(i)) {
				sb.append(s);
			}
		}
		if (sb == null) {
			return null;
		}
		return sb.toString();
	}
	
	//后台获得公司信息
	@Override
	public CompanyDto queryCompanyDtoByIdAdmin(Integer id) {
		if (id==null||id<1) {
			return null;
		}
		CompanyDto company = new CompanyDto();
		CompanyInfo c = queryInfoByid(id);
		if (c==null) {
			return null;
		}
		CompanyAccount ca = companyAccountDao.queryByCompanyId(c.getId());
		if (ca==null) {
			return null;
		}
		company.setTradeNum(judgeDao.countTradeNum(c.getId()));
		company.setAddress(c.getAddress());
		company.setCompanyAccount(ca);
		company.setCompanyInfo(c);
		company.setIndustry("20162016");//后台默认的主营行业
		if (StringUtils.isNotEmpty(c.getArea())) {
			company.setAreaLabel(getCity(c.getArea()));
		}
		return company;
	}
	
	//获得地区
	public String getCity(String code) {
		if(code.length()<12){
			String area = CategoryFacade.getInstance().getValue(code);
			return area;
		}
		String area = CategoryFacade.getInstance().getValue(
				code.substring(0, 12));
		if(code.length()>=16){
			if (!"香港".equals(area) && !"澳门".equals(area) && !"台湾".equals(area)
					&& !"北京".equals(area) && !"天津".equals(area)
					&& !"上海".equals(area) && !"重庆".equals(area)) {
				area = area
						+ " "
						+ CategoryFacade.getInstance().getValue(
								code.substring(0, 16));
			}
		}
		return area;
	}
	
	@Override
	public Integer updateCompanyByAdmin(CompanyInfo companyInfo){
		return companyInfoDao.updateCompanyByAdmin(companyInfo);
	}
}
