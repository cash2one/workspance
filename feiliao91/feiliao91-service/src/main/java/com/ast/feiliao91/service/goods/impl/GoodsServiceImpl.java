/**
 * @author shiqp
 * @date 2016-01-14
 */
package com.ast.feiliao91.service.goods.impl;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Component;

import com.ast.feiliao91.domain.common.DataIndexDO;
import com.ast.feiliao91.domain.company.CompanyAccount;
import com.ast.feiliao91.domain.company.CompanyInfo;
import com.ast.feiliao91.domain.goods.Goods;
import com.ast.feiliao91.domain.goods.GoodsAddProperties;
import com.ast.feiliao91.domain.goods.GoodsDto;
import com.ast.feiliao91.domain.goods.GoodsSearchDto;
import com.ast.feiliao91.domain.goods.Picture;
import com.ast.feiliao91.dto.PageDto;
import com.ast.feiliao91.persist.company.CompanyAccountDao;
import com.ast.feiliao91.persist.company.CompanyInfoDao;
import com.ast.feiliao91.persist.company.CompanyServiceDao;
import com.ast.feiliao91.persist.company.JudgeDao;
import com.ast.feiliao91.persist.goods.GoodsCategoryDao;
import com.ast.feiliao91.persist.goods.GoodsDao;
import com.ast.feiliao91.persist.goods.PictureDao;
import com.ast.feiliao91.service.company.CompanyServiceService;
import com.ast.feiliao91.service.facade.CategoryFacade;
import com.ast.feiliao91.service.goods.GoodsService;
import com.ast.feiliao91.service.goods.PictureService;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.search.SearchEngineUtils;
import com.zz91.util.search.SphinxClient;
import com.zz91.util.search.SphinxException;
import com.zz91.util.search.SphinxMatch;
import com.zz91.util.search.SphinxResult;

@Component("goodsService")
public class GoodsServiceImpl implements GoodsService {

	final static Integer TOTAL_RECORDS = 100000;

	@Resource
	private GoodsDao goodsDao;
	@Resource
	private CompanyInfoDao companyInfoDao;
	@Resource
	private CompanyAccountDao companyAccountDao;
	@Resource
	private JudgeDao judgeDao;
	@Resource
	private CompanyServiceDao companyServiceDao;
	@Resource
	private PictureDao pictureDao;
	@Resource
	private GoodsCategoryDao goodsCategoryDao;

	@Override
	public List<String> queryCategoryByCompanyId(Integer companyId, Integer size) {
		return goodsDao.queryCategoryByCompanyId(companyId, size);
	}

	@Override
	public Integer createGoods(Goods good) {
		// 价格不可为空
		if(StringUtils.isEmpty(good.getPrice())){
			return 0;
		}
		good.setDetail(Jsoup.clean(good.getDetail(), Whitelist.relaxed())); // 清除html格式
		good.setDetail(good.getDetail().replace("\n", "")); // 清楚换行
		good.setIsDel(0);
		good.setIsGarbage(0);
		good.setIsSell(1);
		// 价格转换
		if (good.getPrice().indexOf("万")!=-1) {
			String price = good.getPrice().substring(0, good.getPrice().indexOf("万"));
			Float f = Float.valueOf(price);
			f = f*10000;
			good.setPrice(""+f.intValue());
		}
		if (good.getPrice().indexOf("千")!=-1) {
			String price = good.getPrice().substring(0, good.getPrice().indexOf("千"));
			Float f = Float.valueOf(price);
			f = f*1000;
			good.setPrice(""+f.intValue());
		}
		// 价格中含有中文，则发布失败
		try {
			if (StringUtils.isContainCNChar(good.getPrice())) {
				return 0;
			}
		} catch (UnsupportedEncodingException e) {
		}
		
		Integer i = goodsDao.insertGoods(good);
		return i;
	}

	@Override
	public Goods queryGoodById(Integer id) {
		return goodsDao.queryGoodById(id);
	}

	@Override
	public Integer updateGoods(Goods goods) {
		// 价格转换
		if (goods.getPrice().indexOf("万")!=-1) {
			String price = goods.getPrice().substring(0, goods.getPrice().indexOf("万"));
			Float f = Float.valueOf(price);
			f = f*10000;
			goods.setPrice(""+f.intValue());
		}
		if (goods.getPrice().indexOf("千")!=-1) {
			String price = goods.getPrice().substring(0, goods.getPrice().indexOf("千"));
			Float f = Float.valueOf(price);
			f = f*1000;
			goods.setPrice(""+f.intValue());
		}
		// 价格中含有中文，则发布失败
		try {
			if (StringUtils.isContainCNChar(goods.getPrice())) {
				return 0;
			}
		} catch (UnsupportedEncodingException e) {
		}
		return goodsDao.updateGoods(goods);
	}

	@Override
	public PageDto<GoodsDto> pageBySearch(GoodsSearchDto searchDto,
			PageDto<GoodsDto> page) {
		if (page.getSort() == null) {
			page.setSort("id");
		}
		if (page.getDir() == null) {
			page.setDir("desc");
		}
		List<Goods> list = goodsDao.queryBySearchDto(searchDto, page);
		List<GoodsDto> nlist = new ArrayList<GoodsDto>();
		for (Goods obj : list) {
			if (obj == null || obj.getId() < 1) {
				continue;
			}
			GoodsDto dto = bulidGoodsDto(obj.getId());
			// dto.setGoods(obj);
			// dto.setArea(getSimpleArea(obj.getLocation()));
			// dto.setTradeNum(judgeDao.countTradeNum(obj.getCompanyId()));
			if (dto == null) {
				continue;
			}
			nlist.add(dto);
		}
		page.setRecords(nlist);
		page.setTotalRecords(goodsDao.queryCountBySearchDto(searchDto));
		return page;
	}
	
	@Override
	public PageDto<GoodsDto> pageBySearchAdmin(GoodsSearchDto searchDto,
			PageDto<GoodsDto> page) {
		List<Goods> list = goodsDao.queryBySearchDto(searchDto, page);
		List<GoodsDto> nlist = new ArrayList<GoodsDto>();
		for (Goods obj : list) {
			if (obj == null || obj.getId() < 1) {
				continue;
			}
			GoodsDto dto = bulidGoodsDto(obj.getId());
			// dto.setGoods(obj);
			// dto.setArea(getSimpleArea(obj.getLocation()));
			// dto.setTradeNum(judgeDao.countTradeNum(obj.getCompanyId()));
			if (dto == null) {
				continue;
			}
			nlist.add(dto);
		}
		page.setRecords(nlist);
		page.setTotalRecords(goodsDao.queryCountBySearchDto(searchDto));
		return page;
	}

	/**
	 * 以下地区只需要显示到省即可 <option value="100110001008">上海</option> <option
	 * value="100110001021">重庆</option> <option value="100110001000">北京</option>
	 * <option value="100110001001">天津</option> <option
	 * value="100110001031">台湾</option> <option value="100110001032">香港</option>
	 * <option value="100110001033">澳门</option>
	 * 
	 * @param areaCode
	 * @return
	 */
	private String getSimpleArea(String areaCode) {
		String str = "";
		Integer i = 8;
		String tempCode = "";
		do {
			String fix = "";
			if (StringUtils.isEmpty(areaCode)) {
				break;
			}
			if (areaCode.indexOf("100110001008") != -1
					|| areaCode.indexOf("100110001021") != -1
					|| areaCode.indexOf("100110001000") != -1
					|| areaCode.indexOf("100110001001") != -1
					|| areaCode.indexOf("100110001031") != -1
					|| areaCode.indexOf("100110001032") != -1
					|| areaCode.indexOf("100110001033") != -1) {
				return CategoryFacade.getInstance().getValue(
						areaCode.substring(0, 12));
			}
			i = i + 4;
			if (areaCode.length() < i) {
				break;
			}
			if (i > 16) {
				break;
			}
			tempCode = areaCode.substring(0, i);
			str = str + " " + CategoryFacade.getInstance().getValue(tempCode)
					+ fix;
		} while (true);
		return str;
	}

	@Override
	public PageDto<GoodsDto> pageBySearchEngine(GoodsSearchDto searchDto,
			PageDto<GoodsDto> page) {

		if (page.getPageSize() == null) {
			page.setPageSize(10);
		}

		// 限制最大页数
		if (page.getStartIndex() != null
				&& page.getStartIndex() >= TOTAL_RECORDS - page.getPageSize()) {
			page.setStartIndex(TOTAL_RECORDS - page.getPageSize());
		}

		StringBuffer sb = new StringBuffer();
		SphinxClient cl = SearchEngineUtils.getInstance().getClient();

		List<GoodsDto> list = new ArrayList<GoodsDto>();
		try {

			if (StringUtils.isNotEmpty(searchDto.getCategory())) {
				sb.append(
						" @(category_label1,category_label2,category_label3,category_label4) ")
						.append(searchDto.getCategory());
			}
			String tempStr = "";
			if (StringUtils.isNotEmpty(searchDto.getColor())) {
				tempStr = searchDto.getColor();
				if ("其他".equals(searchDto.getColor())) {
					tempStr = "(color !本色 !白色 !黑色 !透明 !杂色 !灰色 !绿色 !黄色 !蓝色 !红色)"; // ""
				} else {
					tempStr = delteMutiStr(tempStr);
					searchDto.setColor(tempStr);
				}
				sb.append(" @(color) ").append(getSearchKey(tempStr));
			}
			if (StringUtils.isNotEmpty(searchDto.getForm())) {
				tempStr = searchDto.getForm();
				if ("其他".equals(searchDto.getForm())) {
					tempStr = "(form !颗粒 !破碎 !片状)"; // !(颗粒&破碎&片状)
				} else {
					tempStr = delteMutiStr(tempStr);
					searchDto.setForm(tempStr);
				}
				tempStr = delteMutiStr(tempStr);
				sb.append(" @(form) ").append(getSearchKey(tempStr));
			}
			if (StringUtils.isNotEmpty(searchDto.getLevel())) {
				tempStr = searchDto.getLevel();
				if ("其他".equals(searchDto.getLevel())) {
					tempStr = "(level !一级 !二级 !三级 !特级)";
				} else {
					tempStr = delteMutiStr(tempStr);
					searchDto.setLevel(tempStr);
				}
				sb.append(" @(level) ").append(getSearchKey(tempStr));
			}
			if (StringUtils.isNotEmpty(searchDto.getTitle())) {
				sb.append(
						" @(title,category_label1,category_label2,category_label3,category_label4,level,color,form) ")
						.append(searchDto.getTitle());
			}

			// System.out.println(sb.toString());

			cl.SetFilter("is_del", 0, false);
			cl.SetFilter("is_sell", 1, false);
			cl.SetFilter("check_status", 1, false);

			cl.SetMatchMode(SphinxClient.SPH_MATCH_BOOLEAN);

			cl.SetLimits(page.getStartIndex(), page.getPageSize(), 100000);
			if (StringUtils.isNotEmpty(page.getSort())) {
				cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, page.getSort()
						+ " desc");
			} else {
				cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "id desc");
			}

			// 判断是否高会
			// if (product.getIsVip() != null) {
			// if (product.getIsVip()) {
			// cl.SetFilterRange("viptype", 1, 5, false);
			// } else {
			// // 普会
			// cl.SetFilter("viptype", 0, false);
			// }
			// }

			// cl.SetFilter("check_status", 1, false);
			// cl.SetFilter("is_del", 0, false);
			// cl.SetFilter("is_pause", 0, false);

			SphinxResult res = cl.Query(sb.toString(), "goods");
			if (res == null) {
				page.setTotalRecords(0);
				System.out.println(cl.GetLastError());
			} else {
				page.setTotalRecords(res.totalFound);
				for (int i = 0; i < res.matches.length; i++) {
					do {
						GoodsDto dto = new GoodsDto();
						SphinxMatch info = res.matches[i];
						dto = bulidGoodsDto(Integer.valueOf("" + info.docId));
						if (dto == null) {
							continue;
						}
						list.add(dto);
					} while (false);
				}
			}
			page.setRecords(list);
		} catch (SphinxException e) {
			e.printStackTrace();
		}
		return page;
	}

	/**
	 * 取出重复的 关键字 例如 ： 白色,红色,白色
	 * 
	 * @param str
	 * @return 白色,红色
	 */
	private String delteMutiStr(String str) {
		String result = "";
		if (str.indexOf(",") == -1) {
			return str;
		}
		String[] arrayStr = str.split(",");
		Set<String> keySet = new HashSet<String>();
		for (String obj : arrayStr) {
			keySet.add(obj);
		}
		for (String obj : keySet) {
			result = result + obj + ",";
		}
		if (result.endsWith(",")) {
			result.substring(0, result.length() - 1);
		}
		return result;
	}

	private String getSearchKey(String str) {
		String result = "(";
		if (str.indexOf(",") == -1) {
			return str;
		}
		String[] arrayStr = str.split(",");
		for (String obj : arrayStr) {
			result = result + obj + "|";
		}
		if (result.endsWith(",")) {
			result.substring(0, result.length() - 1);
		}
		result = result + ")";
		result = result.replace("|)", ")");
		return result;
	}

	@Override
	public GoodsDto queryGoodInfoById(Integer id) {
		GoodsDto dto = new GoodsDto();
		// 产品信息
		Goods good = goodsDao.queryGoodById(id);

		// 检索服务 7天包退 保证金
		Integer i = companyServiceDao.queryServiceCount(good.getCompanyId(),
				CompanyServiceService.SEVEN_DAY_SERVICE);
		if (i > 0) {
			dto.setSevenDayFlag(1);
		}
		Integer j = companyServiceDao.queryServiceCount(good.getCompanyId(),
				CompanyServiceService.BZJ_SERVICE);
		if (j > 0) {
			dto.setBzjFlag(1);
		}
		// 封面图片
		List<Picture> list = pictureDao.queryPictureByCondition(good.getId(),
				PictureService.TYPE_GOOD, good.getCompanyId(), 1);
		if (list != null && list.size() > 0) {
			dto.setPicAddress(list.get(0).getPicAddress());
		}
		if (good != null) {
			dto.setGoods(good);
			// 公司信息
			CompanyInfo info = companyInfoDao.queryById(good.getCompanyId());
			dto.setCompanyInfo(info);
			// 帐号信息
			CompanyAccount account = companyAccountDao.queryByCompanyId(good
					.getCompanyId());
			dto.setAccount(account);
			// 货物所在地
			if (StringUtils.isNotEmpty(good.getLocation())
					&& good.getLocation().length() > 12) {
				dto.setArea(getCity(good.getLocation()));
			}
			// 公司所在地
			if (info != null && StringUtils.isNotEmpty(info.getArea())
					&& info.getArea().length() > 12) {
				dto.setLocation(getCity(info.getArea()));
			}
			// 成交数
			dto.setSuccessNum(goodsDao.querySuccessOrder(good.getId(), null));
			// 信誉分值
			dto.setTradeNum(judgeDao.countTradeNum(good.getCompanyId()));
			// 评价数
			Integer judgeNum = judgeDao.countJudgeByGoodId(good.getId());
			dto.setJudgeNum(judgeNum);
			// 用途级别
			dto.setUseLabel(getLabel(dto.getGoods().getUseLevel(), dto
					.getGoods().getUseIntro()));
			// 加工级别
			dto.setProcessLabel(getLabel(dto.getGoods().getProcessLevel(), dto
					.getGoods().getProcessIntro()));
			// 特性级别
			dto.setCharaLabel(getLabel(dto.getGoods().getCharLevel(), dto
					.getGoods().getCharIntro()));
			// 货物详细
			if (StringUtils.isNotEmpty(good.getDetail())) {
				dto.setDetail(good.getDetail());
			}
			
			//类别label
			String goodsCategoryLabel = goodsCategoryDao.queryCategoryByCode(good.getMainCategory()).getLabel();
			dto.setGoodsCategoryLabel(goodsCategoryLabel);
		}
		return dto;
	}
	
	@Override
	public GoodsDto queryGoodInfoByIdAdmin(Integer id) {
		GoodsDto dto = new GoodsDto();
		// 产品信息
		Goods good = goodsDao.queryGoodById(id);
		if (good != null) {
			dto.setGoods(good);
			// 公司信息
			CompanyInfo info = companyInfoDao.queryById(good.getCompanyId());
			dto.setCompanyInfo(info);
			// 帐号信息
			CompanyAccount account = companyAccountDao.queryByCompanyId(good
					.getCompanyId());
			dto.setAccount(account);
			// 货物所在地
			if (StringUtils.isNotEmpty(good.getLocation())
					&& good.getLocation().length() > 12) {
				dto.setArea(getCityAdmin(good.getLocation()));
			}
			// 成交数
			dto.setSuccessNum(goodsDao.querySuccessOrder(good.getId(), null));
			// 信誉分值
			dto.setTradeNum(judgeDao.countTradeNum(good.getCompanyId()));
			// 评价数
			Integer judgeNum = judgeDao.countJudgeByGoodId(good.getId());
			dto.setJudgeNum(judgeNum);
			// 用途级别
			dto.setUseLabel(getLabel(dto.getGoods().getUseLevel(), dto
					.getGoods().getUseIntro()));
			// 加工级别
			dto.setProcessLabel(getLabel(dto.getGoods().getProcessLevel(), dto
					.getGoods().getProcessIntro()));
			// 特性级别
			dto.setCharaLabel(getLabel(dto.getGoods().getCharLevel(), dto
					.getGoods().getCharIntro()));
			// 货物详细
			if (StringUtils.isNotEmpty(good.getDetail())) {
				dto.setDetail(good.getDetail());
			}
			
			//类别label
			String goodsCategoryLabel = goodsCategoryDao.queryCategoryByCode(good.getMainCategory()).getLabel();
			dto.setGoodsCategoryLabel(goodsCategoryLabel);
		}
		return dto;
	}

	/**
	 * 获取解码后的地区
	 * 
	 * @param code
	 * @return
	 */
	@Override
	public String getCity(String code) {
		String area = CategoryFacade.getInstance().getValue(
				code.substring(0, 12));
		if (!"香港".equals(area) && !"澳门".equals(area) && !"台湾".equals(area)
				&& !"北京".equals(area) && !"天津".equals(area)
				&& !"上海".equals(area) && !"重庆".equals(area)) {
			area = area
					+ " "
					+ CategoryFacade.getInstance().getValue(
							code.substring(0, 16));
		}
		return area;
	}
	
	/**
	 * 获取解码后的地区全名（后台）
	 * @param code
	 * @return
	 */
//	@Override
	private String getCityAdmin(String code) {
		String area = CategoryFacade.getInstance().getValue(
				code.substring(0, 12));
		if (!"香港".equals(area) && !"澳门".equals(area) && !"台湾".equals(area)
				&& !"北京".equals(area) && !"天津".equals(area)
				&& !"上海".equals(area) && !"重庆".equals(area)) {
			area = area
					+ " "
					+ CategoryFacade.getInstance().getValue(
							code.substring(0, 16));
			if (code.length()>16) {
				area = area
						+ " "
						+ CategoryFacade.getInstance().getValue(
								code.substring(0, 20));
			}
		}
		return area;
	}

	public String getLabel(String level, String intro) {
		String label = "";
		if (StringUtils.isNotEmpty(level)) {
			label = label + CategoryFacade.getInstance().getValue(level);
		}
		if (StringUtils.isNotEmpty(intro)) {
			if (StringUtils.isNotEmpty(label)) {
				label = label + "  " + intro;
			} else {
				label = intro;
			}
		}
		return label;
	}

	public List<GoodsDto> buildDtoForIndex(List<DataIndexDO> list) {
		if (list == null || list.size() < 1) {
			return null;
		}
		List<GoodsDto> nlist = new ArrayList<GoodsDto>();
		for (DataIndexDO obj : list) {
			if (!StringUtils.isNumber(obj.getTitle())) {
				continue;
			}
			GoodsDto dto = bulidGoodsDto(Integer.valueOf(obj.getTitle()));
			if (dto == null) {
				continue;
			}
			nlist.add(dto);
		}
		return nlist;
	}

	private GoodsDto bulidGoodsDto(Integer id) {
		if (id == null || id < 1) {
			return null;
		}
		GoodsDto dto = new GoodsDto();
		Goods obj = goodsDao.queryById(id);
		if (obj == null) {
			return null;
		}
		try {
			if (!StringUtils.isNumber(obj.getPrice())) {
				if (obj.getPrice().indexOf("-") != -1) {
					String[] priceArray = obj.getPrice().split("-");
					obj.setPrice(""
							+ (Integer.valueOf(priceArray[1]) + Integer
									.valueOf(priceArray[0])) / 2);
				}
			}
		} catch (Exception e) {
			obj.setPrice("0");
		}
		dto.setGoods(obj);
		dto.setArea(getSimpleArea(obj.getLocation()));
		CompanyInfo ci = companyInfoDao.queryById(obj.getCompanyId());
		dto.setCompanyInfo(ci);
		dto.setTradeNum(judgeDao.countTradeNum(obj.getCompanyId())); // 信誉分值
		dto.setSuccessNum(goodsDao.querySuccessOrder(id, null)); // 成交数
		// 检索服务 7天包退 保证金
		Integer i = companyServiceDao.queryServiceCount(obj.getCompanyId(),CompanyServiceService.SEVEN_DAY_SERVICE);
		if (i > 0) {
			dto.setSevenDayFlag(1);
		}
		Integer j = companyServiceDao.queryServiceCount(obj.getCompanyId(),CompanyServiceService.BZJ_SERVICE);
		if (j > 0) {
			dto.setBzjFlag(1);
		}
		// 封面图片
		List<Picture> list = pictureDao.queryPictureByCondition(obj.getId(),PictureService.TYPE_GOOD, obj.getCompanyId(), 1);
		if (list != null && list.size() > 0) {
			dto.setPicAddress(list.get(0).getPicAddress());
		}
		// 手机站时间格式获取 例如 7天前
		try {
			dto.setCnDate(DateUtil.getCNDate(obj.getRefreshTime()));
		} catch (ParseException e) {
			dto.setCnDate("");
		}
		return dto;
	}

	@Override
	public List<GoodsDto> listNewGoods(Integer companyId, String mainCategory,
			Integer size, Integer goodsId) {
		List<GoodsDto> listResult = new ArrayList<GoodsDto>();
		// 产品列表
		List<Goods> list = goodsDao.queryGoodsByCategory(null, mainCategory,
				size, goodsId);
		for (Goods gd : list) {
			GoodsDto dto = new GoodsDto();
			dto.setGoods(gd);
			List<Picture> piclist = pictureDao.queryPictureByCondition(
					gd.getId(), PictureService.TYPE_GOOD, null, 1);
			if (piclist != null && piclist.size() > 0) {
				dto.setPicAddress(piclist.get(0).getPicAddress());
			}
			// 成交量
			dto.setSuccessNum(goodsDao.querySuccessOrder(gd.getId(), null));
			listResult.add(dto);
		}
		return listResult;
	}

	@Override
	public Integer updateStatusByUser(Integer id, Integer type) {
		if (type == null || id == null) {
			return 0;
		}
		Integer i = 0;
		// 还原
		if (type == 1) {
			i = goodsDao.updateStatusByUser(id, 0, null,null);
		}
		// 删除
		if (type == 2) {
			i = goodsDao.updateStatusByUser(id, 1, null,null);
		}
		// 下架
		if (type == 3) {
			i = goodsDao.updateStatusByUser(id, null, 0,null);
		}
		// 上架
		if (type == 4) {
			i = goodsDao.updateStatusByUser(id, null, 1,null);
		}
		// 清空回收站
		if (type == 5) {
			goodsDao.updateStatusByUser(id,null,null,1);
		}
		return i;
	}

	public GoodsDto queryGoodsDtoById(Integer id) {
		return bulidGoodsDto(id);
	}

	@SuppressWarnings({ "unused" })
	@Override
	public String updateStatus(String ids, String checkPerson,
			Integer checkStatus) {
		StringBuffer sb = new StringBuffer();
		String[] str = ids.split(",");
		for (String s : str) {
			Integer i = goodsDao.updateStatus(Integer.valueOf(s), checkPerson,
					checkStatus);
			if (i == 1) {
				sb.append(s);
			}
		}
		if (sb == null) {
			return null;
		}
		return sb.toString();
	}

	@Override
	public List<GoodsDto> queryTypeBySameCategory(String mainCategory,
			Integer size, Integer goodsId) {
		List<Goods> list = new ArrayList<Goods>();
		List<GoodsDto> listResult = new ArrayList<GoodsDto>();
		// list1取最新发布的
		List<Goods> list1 = goodsDao.queryNewGoodsBySameCategory(mainCategory,
				size, goodsId);
		// list2取销量最高的
		List<Object> l = goodsDao.queryHighSalesGoodsBySameCategory(
				mainCategory, size, goodsId);
		List<Goods> list2 = new ArrayList<Goods>();
		for (Object object : l) {
			JSONObject obj = JSONObject.fromObject(object);
			// 根据id查询产品
			Goods g = goodsDao.queryGoodById((Integer) obj.get("g_id"));
			list2.add(g);
		}
		// list3取随机的
		List<Goods> list3 = goodsDao.queryRandomGoodsBySameCategory(
				mainCategory, size, goodsId);
		for (Goods goods : list1) {
			// System.out.println("最新的 "+goods.getId());
			list.add(goods);
		}
		for (Goods goods : list2) {
			// System.out.println("销量的 "+goods.getId());
			list.add(goods);
		}
		for (Goods goods : list3) {
			// System.out.println("随机的 "+goods.getId());
			list.add(goods);
		}
		for (Goods gd : list) {
			GoodsDto dto = new GoodsDto();
			dto.setGoods(gd);
			List<Picture> piclist = pictureDao.queryPictureByCondition(
					gd.getId(), PictureService.TYPE_GOOD, null, 1);
			if (piclist != null && piclist.size() > 0) {
				dto.setPicAddress(piclist.get(0).getPicAddress());
			}
			// 成交量
			dto.setSuccessNum(goodsDao.querySuccessOrder(gd.getId(), null));
			listResult.add(dto);
		}
		return listResult;
	}

	// @Override
	// public List<Object> queryH(String mainCategory,Integer size, Integer
	// goodsId){
	// return goodsDao.queryHighSalesGoodsBySameCategory(mainCategory,
	// size, goodsId);
	// }
	public List<GoodsDto> queryRandomGoods(String mainCategory, Integer size,
			Integer goodsId) {
		List<GoodsDto> listResult = new ArrayList<GoodsDto>();
		// 产品列表
		List<Goods> list = goodsDao.queryRandomGoodsBySameCategory(
				mainCategory, size, goodsId);
		for (Goods gd : list) {
			GoodsDto dto = new GoodsDto();
			dto.setGoods(gd);
			List<Picture> piclist = pictureDao.queryPictureByCondition(
					gd.getId(), PictureService.TYPE_GOOD, null, 1);
			if (piclist != null && piclist.size() > 0) {
				dto.setPicAddress(piclist.get(0).getPicAddress());
			}
			// 成交量
			dto.setSuccessNum(goodsDao.querySuccessOrder(gd.getId(), null));
			listResult.add(dto);
		}
		return listResult;
	}
	
	@Override
	public List<GoodsAddProperties> queryByGoodsAddProperties(Integer id){
		Goods goods = goodsDao.queryGoodById(id);
		if (goods == null) {
			return null;
		}
		String goodsAttribute = goods.getGoodAttribute();
		if (StringUtils.isEmpty(goodsAttribute)) {
			return null;
		}
		String[] arr = goodsAttribute.split("\\|");
		List<GoodsAddProperties> list = new ArrayList<GoodsAddProperties>();
		for (int i = 0; i < arr.length; i++) {
			if (StringUtils.isEmpty(arr[i])) {
				continue;
			}
			String[] arr2 = arr[i].split("：");
			GoodsAddProperties goodsAddProperties = new GoodsAddProperties();
			for (int j = 0; j < arr2.length; j++) {
				if (StringUtils.isEmpty(arr2[j])) {
					continue;
				}
				goodsAddProperties.setGid(id);
				if (j==0) {
					goodsAddProperties.setProperty(arr2[j]);
				}
				if(j==1){
					goodsAddProperties.setContent(arr2[j]);
				}
			}
			list.add(goodsAddProperties);
		}
		return list;
	}

}
