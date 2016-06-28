package com.ast.ast1949.service.yuanliao.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.yuanliao.YuanLiaoSearch;
import com.ast.ast1949.domain.yuanliao.Yuanliao;
import com.ast.ast1949.domain.yuanliao.YuanliaoPic;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.yuanliao.SearchDto;
import com.ast.ast1949.dto.yuanliao.YuanliaoDto;
import com.ast.ast1949.persist.company.CompanyAccountDao;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.yuanliao.YuanliaoDao;
import com.ast.ast1949.persist.yuanliao.YuanliaoPicDao;
import com.ast.ast1949.service.company.CompanyService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.service.facade.YuanliaoFacade;
import com.ast.ast1949.service.yuanliao.YuanLiaoService;
import com.zz91.util.datetime.DateUtil;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.search.SearchEngineUtils;
import com.zz91.util.search.SphinxClient;
import com.zz91.util.search.SphinxException;
import com.zz91.util.search.SphinxMatch;
import com.zz91.util.search.SphinxResult;

@Component("yuanLiaoService")
public class YuanLiaoServiceImpl implements YuanLiaoService {
	@Resource
	private YuanliaoDao yuanliaoDao;
	@Resource
	private YuanliaoPicDao yuanliaoPicDao;
	@Resource
	private CompanyDAO companyDAO;
	@Resource
	private CompanyAccountDao companyAccountDao;
	@Resource
	private CompanyService companyService;

	@Override
	public Integer insertYuanliao(Yuanliao yuanliao) {
		return yuanliaoDao.insertYuanliao(yuanliao);
	}

	@Override
	public PageDto<YuanliaoDto> pageYaunliao(PageDto<YuanliaoDto> page, Yuanliao yuanliao, YuanLiaoSearch search) {
		List<YuanliaoDto> listy = new ArrayList<YuanliaoDto>();
		List<Yuanliao> list = yuanliaoDao.queryYuanliaoList(yuanliao, page, search);
		for (Yuanliao yl : list) {
			YuanliaoDto dto = new YuanliaoDto();
			dto.setYuanliao(yl);
			// 图片数
			YuanliaoPic pic = new YuanliaoPic();
			pic.setYuanliaoId(yl.getId());
			pic.setIsDel(0);
			List<YuanliaoPic> picList = yuanliaoPicDao.queryYuanliaoPicByYuanliaoId(pic, null);
			if (picList.size() == 0) {
				picList = new ArrayList<YuanliaoPic>();
			} else {
				dto.setPicAddress(picList.get(0).getPicAddress());
			}
			dto.setPicNum(picList.size());
			// 是否过期
			try {
				long expireTime = DateUtil.getDate(yl.getExpireTime(), "yyyy-MM-dd HH:mm:ss").getTime();
				long now = new Date().getTime();
				if (expireTime > now) {
					// 未过期
					dto.setIsExpire(1);
				} else {
					// 已过期
					dto.setIsExpire(0);
				}
				// 还有多天过期
				Integer expire = DateUtil.getIntervalDays(yl.getExpireTime(), new Date());
				if (expire < 0) {
					expire = 0;
				}
				dto.setExpire(expire);
			} catch (ParseException e) {
			}
			listy.add(dto);
		}
		page.setRecords(listy);
		page.setTotalRecords(yuanliaoDao.countYuanliaoList(yuanliao, search));
		return page;
	}

	@Override
	public Yuanliao queryYuanliaoById(Integer id) {
		return yuanliaoDao.queryYuanliaoById(id);
	}

	@Override
	public Integer updateYuanliao(Yuanliao yuanliao) {
		return yuanliaoDao.updateYuanliao(yuanliao);
	}

	@Override
	public Map<String, String> queryYuanliaoCategory(Integer companyId) {
		List<String> list = yuanliaoDao.queryYuanliaoForCategory(companyId);
		Map<String, String> map = new HashMap<String, String>();
		for (String s : list) {
			map.put(s.substring(0, 16),
					YuanliaoFacade.getInstance().getValue(s.substring(0, 8)) + "&nbsp;>&nbsp;"
							+ YuanliaoFacade.getInstance().getValue(s.substring(0, 12)) + "&nbsp;>&nbsp;"
							+ YuanliaoFacade.getInstance().getValue(s.substring(0, 16)));
		}
		return map;
	}

	@Override
	public PageDto<YuanliaoDto> pageYuanliaoListByAdmin(PageDto<YuanliaoDto> page, YuanLiaoSearch search) {
		List<Yuanliao> listyl = yuanliaoDao.queryYuanliaoListByAdmin(page, search);
		List<YuanliaoDto> list = new ArrayList<YuanliaoDto>();
		for (Yuanliao yl : listyl) {
			YuanliaoDto dto = new YuanliaoDto();
			long expireTime = 0, nowTime = 0;
			try {
				if (yl.getExpireTime() != null) {
					expireTime = DateUtil.getDate(yl.getExpireTime(), "yyyy-MM-dd HH:mm:ss").getTime();
				}
				nowTime = new Date().getTime();
			} catch (ParseException e) {
			}
			if (yl.getIsDel() == 1) {
				yl.setTitle(yl.getTitle() + "(已删除)");
			} else if (yl.getIsPause() == 1) {
				yl.setTitle(yl.getTitle() + "(暂不发布)");
			} else if (expireTime < nowTime) {
				yl.setTitle(yl.getTitle() + "(已过期)");
			}
			if (StringUtils.isNotEmpty(yl.getDescription())) {
				yl.setDescription(Jsoup.clean(yl.getDescription(), Whitelist.none()));
			}
			dto.setYuanliao(yl);
			Company company = companyDAO.querySimpleCompanyById(yl.getCompanyId());
			if (company == null) {
				company = new Company();
			}
			dto.setCompany(company);
			list.add(dto);
		}
		page.setRecords(list);
		page.setTotalRecords(yuanliaoDao.countYunaliaoListByAdmin(search));
		return page;
	}

	@Override
	public List<YuanliaoDto> queryYuanLiaoByCondition(YuanLiaoSearch search, Integer size) {
		List<Yuanliao> list = yuanliaoDao.queryYuanLiaoByCondition(search, size);
		List<YuanliaoDto> listResult = new ArrayList<YuanliaoDto>();
		YuanliaoPic pic = new YuanliaoPic();
		pic.setCheckStatus(1);
		pic.setIsDel(0);
		for (Yuanliao yl : list) {
			YuanliaoDto dto = new YuanliaoDto();
			yl.setDescription(Jsoup.clean(yl.getDescription(), Whitelist.none()));
			dto.setYuanliao(yl);
			// 公司信息
			Company company = companyDAO.queryCompanyById(yl.getCompanyId());
			if (company == null) {
				company = new Company();
			}
			dto.setCompany(company);
			// 图片
			pic.setYuanliaoId(yl.getId());
			List<YuanliaoPic> listPic = yuanliaoPicDao.queryYuanliaoPicByYuanliaoId(pic, 1);
			if (listPic.size() > 0) {
				dto.setPicAddress(listPic.get(0).getPicAddress());
			}
			// 厂家（产地）名称
			String label = "";
			if (StringUtils.isNotEmpty(yl.getCategoryMainDesc())) {
				label = YuanliaoFacade.getInstance().getValue(yl.getCategoryMainDesc());
			} else {
				label = yl.getCategoryAssistDesc();
			}
			dto.setCategoryMainLabel(label);
			listResult.add(dto);
		}
		return listResult;
	}

	@Override
	public List<Yuanliao> queryYuanliaoBYCompanyId(Integer companyId) {
		return yuanliaoDao.queryYuanliaoBYCompanyId(companyId);
	}

	@Override
	public PageDto<YuanliaoDto> searchByEngine(YuanLiaoSearch search,
			PageDto<YuanliaoDto> page) {
		if (page.getPageSize() == null) {
			page.setPageSize(10);
		}
		List<YuanliaoDto> list = new ArrayList<YuanliaoDto>();
		// 初始化StringBuffer
		StringBuffer sb = new StringBuffer();
		// 获取搜索引擎连接client
		SphinxClient cl = SearchEngineUtils.getInstance().getClient();

		// 供求类别
		if (StringUtils.isNotEmpty(search.getType())) {
			sb.append(" @(yuanliao_type_code) ").append(search.getType());
		}
		// 类别
		if (StringUtils.isNotEmpty(search.getCode())) {
			sb.append(" @(label1,label2,label3,category_main_desc) ")
					.append(search.getCode());
		}
		// 地区
		if (StringUtils.isNotEmpty(search.getProvince())) {
			sb.append(" @(province) ").append(search.getProvince());
		}
		// 关键字
		if (StringUtils.isNotEmpty(search.getKeyword())) {
			sb.append(" @(title) ").append(search.getKeyword());
		}
		// 设置是否要图片
		if (search.getHasPic() != null && search.getHasPic().equals(1)) {
			try {
				cl.SetFilterRange("havepic", 1, 5000, false);
			} catch (SphinxException e) {
			}
		}
		// 设置指定公司 companyId
		if (search.getCompanyId() != null) {
			try {
				cl.SetFilter("company_id", search.getCompanyId(), false);
			} catch (SphinxException e) {
			}
		}

		// 默认条件
		try {
			cl.SetFilter("is_del", 0, false); // 不删除
			cl.SetFilter("check_status", 1, false); // 审核通过
			cl.SetFilter("is_pause", 0, false); // 已发布状态

		cl.SetMatchMode(SphinxClient.SPH_MATCH_BOOLEAN);
		cl.SetLimits(0, page.getPageSize(), page.getPageSize());
		cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "rrefresh_time desc");
		} catch (SphinxException e) {
		} 
		SphinxResult res=null;
		try {
			res = cl.Query(sb.toString(), "yuanliao");
		} catch (SphinxException e) {
			res =null;
		}
		if (res != null) {
			for (int i = 0; i < res.matches.length; i++) {
				SphinxMatch info = res.matches[i];
				YuanliaoDto dto = new YuanliaoDto();
				try {
					Yuanliao yl = yuanliaoDao.queryYuanliaoById(Integer.valueOf(String.valueOf("" + info.docId)));
					yl.setDescription(Jsoup.clean(yl.getDescription(),Whitelist.none()));
					dto.setYuanliao(yl);
					// 原料供求货源地
					String address = "";
					if (StringUtils.isNotEmpty(yl.getLocation())) {
						if (yl.getLocation().length() > 12) {
							address = CategoryFacade.getInstance()
									.getValue(
											yl.getLocation().substring(0,
													12))
									+ " "
									+ CategoryFacade.getInstance()
									.getValue(
											yl.getLocation()
											.substring(0,
													16));
						} else if (yl.getLocation().length() > 8
								&& yl.getLocation().length() < 13) {
							address = CategoryFacade.getInstance()
									.getValue(
											yl.getLocation()
											.substring(0, 8))
									+ " "
									+ CategoryFacade.getInstance()
									.getValue(
											yl.getLocation()
											.substring(0,
													12));
						} else if (yl.getLocation().length() == 8) {
							address = CategoryFacade.getInstance()
									.getValue(
											yl.getLocation()
											.substring(0, 8));
						}
					}
					dto.setAddress(address);
					// 加工级别名称
					dto.setProcessLabel(getLabel(yl.getProcessLevel()));
					// 特性级别名称
					dto.setCharaLabel(getLabel(yl.getCharaLevel()));
					// 用途级别名称
					dto.setUsefulLabel(getLabel(yl.getUsefulLevel()));
					// 厂家（产地）
					if (StringUtils.isNotEmpty(yl.getCategoryMainDesc())) {
						dto.setCategoryMainLabel(YuanliaoFacade.getInstance().getValue(yl.getCategoryMainDesc()));
					} else {
						dto.setCategoryMainLabel(yl.getCategoryAssistDesc());
					}
					// 类型名称
					dto.setTypeLabel(getLabel(yl.getType()));
					Company company = companyDAO.queryCompanyById(yl
							.getCompanyId());
					company.setIntroduction(Jsoup.clean(
							company.getIntroduction(), Whitelist.none()));
					dto.setCompany(company);
					String location = "";
					if (StringUtils.isNotEmpty(company.getAreaCode())) {
						if (company.getAreaCode().length() > 12) {
							location = CategoryFacade.getInstance().getValue(company.getAreaCode().substring(0, 12))
									+ " "
									+ CategoryFacade.getInstance().getValue(company.getAreaCode().substring(0,16));
						} else if (company.getAreaCode().length() > 8) {
							location = CategoryFacade.getInstance()
									.getValue(company.getAreaCode().substring(0, 8))
									+ " "
									+ CategoryFacade.getInstance()
									.getValue(company.getAreaCode().substring(0,12));
						} else if (company.getAreaCode().length() == 8) {
							location = CategoryFacade.getInstance()
									.getValue(
											company.getAreaCode()
											.substring(0, 8));
						}
					}
					dto.setLocation(location);
					// 图片
					YuanliaoPic pic = new YuanliaoPic();
					pic.setYuanliaoId(yl.getId());
					pic.setIsDel(0);
					pic.setCheckStatus(1);
					List<YuanliaoPic> listPic = yuanliaoPicDao.queryYuanliaoPicByYuanliaoId(pic, 1);
					if (listPic.size() > 0) {
						dto.setPicAddress(listPic.get(0).getPicAddress());
					}
					// qq
					CompanyAccount account = companyAccountDao.queryAccountByCompanyId(yl.getCompanyId());
					if (account != null	&& StringUtils.isNotEmpty(account.getQq())) {
						dto.setQq(account.getQq());
					}
					list.add(dto);
				} catch (Exception e) {
					list.add(dto);
				}
			}
			page.setRecords(list);
			page.setTotalRecords(res.totalFound);
		} 
		return page;
	}

	@Override
	public PageDto<YuanliaoDto> pageSearchYuanliaoList(YuanLiaoSearch search, PageDto<YuanliaoDto> page) {
		page.setPageSize(10000);
		// 记录供求id
		List<Integer> listyl = new ArrayList<Integer>();
		// 标志公司第多少轮
		Map<Integer, String> map = new HashMap<Integer, String>();
		// 已经到了多少轮
		Map<String, Integer> mapQ = new HashMap<String, Integer>();
		// 目标集
		TreeMap<String, Integer> mapR = new TreeMap<String, Integer>();
		List<YuanliaoDto> list = new ArrayList<YuanliaoDto>();
		StringBuffer sb = new StringBuffer();
		SphinxClient cl = SearchEngineUtils.getInstance().getClient();
		SphinxClient cll = SearchEngineUtils.getInstance().getClient();
		if (StringUtils.isNotEmpty(search.getType())) {
			sb.append(" @(yuanliao_type_code) ").append(search.getType());
		}
		if (StringUtils.isNotEmpty(search.getCode())) {
			sb.append(" @(label1,label2,label3,category_main_desc) ").append(search.getCode());
		}
		if (StringUtils.isNotEmpty(search.getProvince())) {
			sb.append(" @(province) ").append(search.getProvince());
		}
		if (StringUtils.isNotEmpty(search.getKeyword())) {
			sb.append(" @(title) ").append(search.getKeyword());
		}
		try {
			// 设置是否要图片
			if (search.getHasPic() != null && search.getHasPic().equals(1)) {
				cl.SetFilterRange("havepic", 1, 5000, false);
			}

			cl.SetFilter("is_del", 0, false);
			cl.SetFilter("check_status", 1, false);
			cl.SetFilter("is_pause", 0, false);
			if (search.getLimit() != null) {
				try {
					cl.SetFilterRange("rrefresh_time",
							DateUtil.getDateAfterDays(DateUtil.getDate(new Date(), "yyyy-MM-dd"), -search.getLimit())
									.getTime() / 1000,
							new Date().getTime() / 1000, false);
				} catch (ParseException e) {
				}
			}
			if (StringUtils.isEmpty(search.getDir())) {
				cl.SetFilterRange("havepic", 1, 5000, false);
				cl.SetMatchMode(SphinxClient.SPH_MATCH_EXTENDED2);
				cl.SetLimits(page.getStartIndex(), page.getPageSize(), 100000);
				cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "rrefresh_time desc");
				SphinxResult res = cl.Query(sb.toString(), "yuanliao");
				if (res != null) {
					for (int i = 0; i < res.matches.length; i++) {
						SphinxMatch info = res.matches[i];
						// 是否已经有该公司
						if (map.keySet().contains(Integer.valueOf("" + info.attrValues.get(0)))) {
							// 增加轮数标志
							map.put(Integer.valueOf("" + info.attrValues.get(0)),
									map.get(Integer.valueOf("" + info.attrValues.get(0))) + "a");
						} else {
							map.put(Integer.valueOf("" + info.attrValues.get(0)), "a");
						}
						// 增加当前轮的数字
						if (!mapQ.keySet().contains(map.get(Integer.valueOf("" + info.attrValues.get(0))))) {
							mapQ.put(map.get(Integer.valueOf("" + info.attrValues.get(0))), 1);
						} else {
							mapQ.put(map.get(Integer.valueOf("" + info.attrValues.get(0))),
									mapQ.get(map.get(Integer.valueOf("" + info.attrValues.get(0)))) + 1);
						}
						// 放入最终集
						mapR.put(
								map.get(Integer.valueOf("" + info.attrValues.get(0)))
										+ mapQ.get(map.get(Integer.valueOf("" + info.attrValues.get(0)))),
								Integer.valueOf("" + info.docId));

					}
					for (String s : mapR.keySet()) {
						listyl.add(mapR.get(s));
					}
				}
				// 所有标记清空
				map = new HashMap<Integer, String>();
				// 已经到了多少轮
				mapQ = new HashMap<String, Integer>();
				// 目标集
				mapR = new TreeMap<String, Integer>();
				cll.SetFilter("is_del", 0, false);
				cll.SetFilter("check_status", 1, false);
				cll.SetFilter("is_pause", 0, false);
				cll.SetFilterRange("havepic", 1, 5000, true);
				if (search.getLimit() != null) {
					cll.SetFilterRange("rrefresh_time",
							DateUtil.getDateAfterDays(new Date(), -search.getLimit()).getTime() / 1000,
							new Date().getTime() / 1000, false);
				}
				cll.SetMatchMode(SphinxClient.SPH_MATCH_EXTENDED2);
				cll.SetLimits(page.getStartIndex(), page.getPageSize(), 100000);
				cll.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "rrefresh_time desc");
				SphinxResult res1 = cll.Query(sb.toString(), "yuanliao");
				if (res1 != null) {
					for (int i = 0; i < res1.matches.length; i++) {
						SphinxMatch info = res1.matches[i];
						// 是否已经有该公司
						if (map.keySet().contains(Integer.valueOf("" + info.attrValues.get(0)))) {
							// 增加轮数标志
							map.put(Integer.valueOf("" + info.attrValues.get(0)),
									map.get(Integer.valueOf("" + info.attrValues.get(0))) + "a");
						} else {
							map.put(Integer.valueOf("" + info.attrValues.get(0)), "a");
						}
						// 增加当前轮的数字
						if (!mapQ.keySet().contains(map.get(Integer.valueOf("" + info.attrValues.get(0))))) {
							mapQ.put(map.get(Integer.valueOf("" + info.attrValues.get(0))), 1);
						} else {
							mapQ.put(map.get(Integer.valueOf("" + info.attrValues.get(0))),
									mapQ.get(map.get(Integer.valueOf("" + info.attrValues.get(0)))) + 1);
						}
						// 放入最终集
						mapR.put(
								map.get(Integer.valueOf("" + info.attrValues.get(0)))
										+ mapQ.get(map.get(Integer.valueOf("" + info.attrValues.get(0)))),
								Integer.valueOf("" + info.docId));

					}
					for (String s : mapR.keySet()) {
						listyl.add(mapR.get(s));
					}
				}
			} else {
				cl.SetMatchMode(SphinxClient.SPH_MATCH_EXTENDED2);
				page.setStartIndex((page.getCurrentPage() - 1) * 10);
				cl.SetLimits(page.getStartIndex(), 10, 100000);
				if ("down".equals(search.getDir())) {
					cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "pprice desc,min_price desc,max_price desc");
				} else if ("up".equals(search.getDir())) {
					cl.SetSortMode(SphinxClient.SPH_SORT_EXTENDED, "pprice asc,min_price asc,max_price asc");
				}
				SphinxResult res = cl.Query(sb.toString(), "yuanliao");
				if (res != null) {
					for (int i = 0; i < res.matches.length; i++) {
						SphinxMatch info = res.matches[i];
						listyl.add(Integer.valueOf(String.valueOf("" + info.docId)));
					}
					page.setTotalRecords(res.totalFound);
				}
			}
		} catch (SphinxException e) {
		}
		if (StringUtils.isEmpty(search.getDir())) {
			if (page.getCurrentPage() * 10 < listyl.size()) {
				for (int i = (page.getCurrentPage() - 1) * 10; i < page.getCurrentPage() * 10; i++) {
					YuanliaoDto dto = new YuanliaoDto();
					Yuanliao yl = yuanliaoDao.queryYuanliaoById(listyl.get(i));
					if (yl != null) {
						if (StringUtils.isNotEmpty(yl.getDescription())) {
							yl.setDescription(Jsoup.clean(yl.getDescription(), Whitelist.none()));
						}
						dto.setYuanliao(yl);
						Company company = companyDAO.queryCompanyById(yl.getCompanyId());
						dto.setCompany(company);
						String location = "";
						if (company != null) {
							if (company.getIntroduction() != null) {
								company.setIntroduction(Jsoup.clean(company.getIntroduction(), Whitelist.none()));
							}
							if (StringUtils.isNotEmpty(company.getAreaCode())) {
								if (company.getAreaCode().length() > 12) {
									location = CategoryFacade.getInstance()
											.getValue(company.getAreaCode().substring(0, 12)) + " "
											+ CategoryFacade.getInstance()
													.getValue(company.getAreaCode().substring(0, 16));
								} else if (company.getAreaCode().length() > 8) {
									location = CategoryFacade.getInstance()
											.getValue(company.getAreaCode().substring(0, 8)) + " "
											+ CategoryFacade.getInstance()
													.getValue(company.getAreaCode().substring(0, 12));
								} else if (company.getAreaCode().length() == 8) {
									location = CategoryFacade.getInstance()
											.getValue(company.getAreaCode().substring(0, 8));
								}
							}
							List<YuanliaoDto> yuanliao = new ArrayList<YuanliaoDto>();
							List<Yuanliao> yuan = yuanliaoDao.queryYuanliaoBYCompanyId(company.getId());
							if (yuan.size() > 0) {
								int count = 2;
								if (yuan.size() < 2) {
									count = 1;
								}
								for (int b = 0; b < count; b++) {
									YuanliaoDto dto2 = new YuanliaoDto();
									dto2.setYuanliao(yuan.get(b));
									YuanliaoPic pic = new YuanliaoPic();
									pic.setYuanliaoId(yuan.get(b).getId());
									pic.setIsDel(0);
									pic.setCheckStatus(1);
									List<YuanliaoPic> listPic = yuanliaoPicDao.queryYuanliaoPicByYuanliaoId(pic, 1);
									if (listPic.size() > 0) {
										dto2.setPicAddress(listPic.get(0).getPicAddress());
									}
									yuanliao.add(dto2);
								}
							}
							dto.setList(yuanliao);
						}
						dto.setLocation(location);
						// 原料供求货源地
						String address = "";
						if (StringUtils.isNotEmpty(yl.getLocation())) {
							if (yl.getLocation().length() > 12) {
								address = CategoryFacade.getInstance().getValue(yl.getLocation().substring(0, 12)) + " "
										+ CategoryFacade.getInstance().getValue(yl.getLocation().substring(0, 16));
							} else if (yl.getLocation().length() > 8 && yl.getLocation().length() < 13) {
								address = CategoryFacade.getInstance().getValue(yl.getLocation().substring(0, 8)) + " "
										+ CategoryFacade.getInstance().getValue(yl.getLocation().substring(0, 12));
							} else if (yl.getLocation().length() == 8) {
								address = CategoryFacade.getInstance().getValue(yl.getLocation().substring(0, 8));
							}
						}
						dto.setAddress(address);
						// 加工级别名称
						dto.setProcessLabel(getLabel(yl.getProcessLevel()));
						// 特性级别名称
						dto.setCharaLabel(getLabel(yl.getCharaLevel()));
						// 用途级别名称
						dto.setUsefulLabel(getLabel(yl.getUsefulLevel()));
						// 类型名称
						dto.setTypeLabel(getLabel(yl.getType()));
						// 厂家（产地）
						if (StringUtils.isNotEmpty(yl.getCategoryMainDesc())) {
							dto.setCategoryMainLabel(YuanliaoFacade.getInstance().getValue(yl.getCategoryMainDesc()));
						} else {
							dto.setCategoryMainLabel(yl.getCategoryAssistDesc());
						}
						// 图片
						YuanliaoPic pic = new YuanliaoPic();
						pic.setYuanliaoId(yl.getId());
						pic.setIsDel(0);
						pic.setCheckStatus(1);
						List<YuanliaoPic> listPic = yuanliaoPicDao.queryYuanliaoPicByYuanliaoId(pic, 1);
						if (listPic.size() > 0) {
							dto.setPicAddress(listPic.get(0).getPicAddress());
						}
						// qq
						CompanyAccount account = companyAccountDao.queryAccountByCompanyId(yl.getCompanyId());
						if (account != null && StringUtils.isNotEmpty(account.getQq())) {
							dto.setQq(account.getQq());
						}
					}
					list.add(dto);
				}
			} else {
				for (int i = (page.getCurrentPage() - 1) * 10; i < listyl.size(); i++) {
					YuanliaoDto dto = new YuanliaoDto();
					Yuanliao yl = yuanliaoDao.queryYuanliaoById(listyl.get(i));
					if (yl != null) {
						if (StringUtils.isNotEmpty(yl.getDescription())) {
							yl.setDescription(Jsoup.clean(yl.getDescription(), Whitelist.none()));
						}
						dto.setYuanliao(yl);
						// 原料供求货源地
						String address = "";
						if (StringUtils.isNotEmpty(yl.getLocation())) {
							if (yl.getLocation().length() > 12) {
								address = CategoryFacade.getInstance().getValue(yl.getLocation().substring(0, 12)) + " "
										+ CategoryFacade.getInstance().getValue(yl.getLocation().substring(0, 16));
							} else if (yl.getLocation().length() > 8 && yl.getLocation().length() < 13) {
								address = CategoryFacade.getInstance().getValue(yl.getLocation().substring(0, 8)) + " "
										+ CategoryFacade.getInstance().getValue(yl.getLocation().substring(0, 12));
							} else if (yl.getLocation().length() == 8) {
								address = CategoryFacade.getInstance().getValue(yl.getLocation().substring(0, 8));
							}
						}
						Company company = companyDAO.queryCompanyById(yl.getCompanyId());
						if (company != null) {
							if (company.getIntroduction() != null) {
								company.setIntroduction(Jsoup.clean(company.getIntroduction(), Whitelist.none()));
							}
							List<YuanliaoDto> yuanliao = new ArrayList<YuanliaoDto>();
							List<Yuanliao> yuan = yuanliaoDao.queryYuanliaoBYCompanyId(company.getId());
							if (yuan.size() > 0) {
								int count = 2;
								if (yuan.size() < 2) {
									count = 1;
								}
								for (int b = 0; b < count; b++) {
									YuanliaoDto dto2 = new YuanliaoDto();
									dto2.setYuanliao(yuan.get(b));
									YuanliaoPic pic = new YuanliaoPic();
									pic.setYuanliaoId(yuan.get(b).getId());
									pic.setIsDel(0);
									pic.setCheckStatus(1);
									List<YuanliaoPic> listPic = yuanliaoPicDao.queryYuanliaoPicByYuanliaoId(pic, 1);
									if (listPic.size() > 0) {
										dto2.setPicAddress(listPic.get(0).getPicAddress());
									}
									yuanliao.add(dto2);
								}
							}
							dto.setList(yuanliao);
						}
						dto.setAddress(address);
						// 加工级别名称
						dto.setProcessLabel(getLabel(yl.getProcessLevel()));
						// 特性级别名称
						dto.setCharaLabel(getLabel(yl.getCharaLevel()));
						// 用途级别名称
						dto.setUsefulLabel(getLabel(yl.getUsefulLevel()));
						// 厂家（产地）
						if (StringUtils.isNotEmpty(yl.getCategoryMainDesc())) {
							dto.setCategoryMainLabel(YuanliaoFacade.getInstance().getValue(yl.getCategoryMainDesc()));
						} else {
							dto.setCategoryMainLabel(yl.getCategoryAssistDesc());
						}
						// 类型名称
						dto.setTypeLabel(getLabel(yl.getType()));
						dto.setCompany(company);
						String location = "";
						if (company != null) {
							if (StringUtils.isNotEmpty(company.getAreaCode())) {
								if (company.getAreaCode().length() > 12) {
									location = CategoryFacade.getInstance()
											.getValue(company.getAreaCode().substring(0, 12)) + " "
											+ CategoryFacade.getInstance()
													.getValue(company.getAreaCode().substring(0, 16));
								} else if (company.getAreaCode().length() > 8) {
									location = CategoryFacade.getInstance()
											.getValue(company.getAreaCode().substring(0, 8)) + " "
											+ CategoryFacade.getInstance()
													.getValue(company.getAreaCode().substring(0, 12));
								} else if (company.getAreaCode().length() == 8) {
									location = CategoryFacade.getInstance()
											.getValue(company.getAreaCode().substring(0, 8));
								}
							}
						}
						dto.setLocation(location);
						// 图片
						YuanliaoPic pic = new YuanliaoPic();
						pic.setYuanliaoId(yl.getId());
						pic.setIsDel(0);
						pic.setCheckStatus(1);
						List<YuanliaoPic> listPic = yuanliaoPicDao.queryYuanliaoPicByYuanliaoId(pic, 1);
						if (listPic.size() > 0) {
							dto.setPicAddress(listPic.get(0).getPicAddress());
						}
						// qq
						CompanyAccount account = companyAccountDao.queryAccountByCompanyId(yl.getCompanyId());
						if (account != null && StringUtils.isNotEmpty(account.getQq())) {
							dto.setQq(account.getQq());
						}
					}
					list.add(dto);
				}
			}
		} else {
			for (Integer in : listyl) {
				YuanliaoDto dto = new YuanliaoDto();
				Yuanliao yl = yuanliaoDao.queryYuanliaoById(in);
				if (yl != null) {
					dto.setYuanliao(yl);
					// 原料供求货源地
					String address = "";
					if (StringUtils.isNotEmpty(yl.getLocation())) {
						if (yl.getLocation().length() > 12) {
							address = CategoryFacade.getInstance().getValue(yl.getLocation().substring(0, 12)) + " "
									+ CategoryFacade.getInstance().getValue(yl.getLocation().substring(0, 16));
						} else if (yl.getLocation().length() > 8 && yl.getLocation().length() < 13) {
							address = CategoryFacade.getInstance().getValue(yl.getLocation().substring(0, 8)) + " "
									+ CategoryFacade.getInstance().getValue(yl.getLocation().substring(0, 12));
						} else if (yl.getLocation().length() == 8) {
							address = CategoryFacade.getInstance().getValue(yl.getLocation().substring(0, 8));
						}
					}
					dto.setAddress(address);
					// 加工级别名称
					dto.setProcessLabel(getLabel(yl.getProcessLevel()));
					// 特性级别名称
					dto.setCharaLabel(getLabel(yl.getCharaLevel()));
					// 用途级别名称
					dto.setUsefulLabel(getLabel(yl.getUsefulLevel()));
					// 厂家（产地）
					if (StringUtils.isNotEmpty(yl.getCategoryMainDesc())) {
						dto.setCategoryMainLabel(YuanliaoFacade.getInstance().getValue(yl.getCategoryMainDesc()));
					} else {
						dto.setCategoryMainLabel(yl.getCategoryAssistDesc());
					}
					// 类型名称
					dto.setTypeLabel(getLabel(yl.getType()));
					Company company = companyDAO.queryCompanyById(yl.getCompanyId());
					dto.setCompany(company);
					String location = "";
					if (StringUtils.isNotEmpty(company.getAreaCode())) {
						if (company.getAreaCode().length() > 12) {
							location = CategoryFacade.getInstance().getValue(company.getAreaCode().substring(0, 12))
									+ " "
									+ CategoryFacade.getInstance().getValue(company.getAreaCode().substring(0, 16));
						} else if (company.getAreaCode().length() > 8) {
							location = CategoryFacade.getInstance().getValue(company.getAreaCode().substring(0, 8))
									+ " "
									+ CategoryFacade.getInstance().getValue(company.getAreaCode().substring(0, 12));
						} else if (company.getAreaCode().length() == 8) {
							location = CategoryFacade.getInstance().getValue(company.getAreaCode().substring(0, 8));
						}
					}
					dto.setLocation(location);
					// 图片
					YuanliaoPic pic = new YuanliaoPic();
					pic.setYuanliaoId(yl.getId());
					pic.setIsDel(0);
					pic.setCheckStatus(1);
					List<YuanliaoPic> listPic = yuanliaoPicDao.queryYuanliaoPicByYuanliaoId(pic, 1);
					if (listPic.size() > 0) {
						dto.setPicAddress(listPic.get(0).getPicAddress());
					}
					// qq
					CompanyAccount account = companyAccountDao.queryAccountByCompanyId(yl.getCompanyId());
					if (account != null && StringUtils.isNotEmpty(account.getQq())) {
						dto.setQq(account.getQq());
					}
				}
				list.add(dto);
			}
		}
		page.setRecords(list);
		if (StringUtils.isEmpty(search.getDir())) {
			page.setTotalRecords(listyl.size());
		}
		return page;
	}

	public String getLabel(String code) {
		String label = "";
		if (StringUtils.isNotEmpty(code)) {
			for (String s : code.split(",")) {
				if (StringUtils.isNotEmpty(label)) {
					label = label + "," + CategoryFacade.getInstance().getValue(s);
				} else {
					label = CategoryFacade.getInstance().getValue(s);
				}
			}
		}
		return label;

	}

	@Override
	public Integer countYuanliaoList(Yuanliao yuanliao, YuanLiaoSearch search) {
		return yuanliaoDao.countYuanliaoList(yuanliao, search);
	}

	@Override
	public Integer updateRefreshTime(Integer id) {
		// 获取塑料原料信息
		Yuanliao yuanliao = yuanliaoDao.queryYuanliaoById(id);
		if (yuanliao == null) {
			return 0;
		}
		// 有效天数
		Integer limit = 0;
		try {
			limit = DateUtil.getIntervalDays(yuanliao.getExpireTime(), yuanliao.getRefreshTime());
			String expireTime = DateUtil.toString(DateUtil.getDateAfterDays(new Date(), limit), "yyyy-MM-dd HH:mm:ss");
			return yuanliaoDao.updateRefreshTime(id, expireTime);
		} catch (ParseException e) {
			return 0;
		}
	}

	@Override
	public PageDto<YuanliaoDto> queryYuanliaoSearchDto(SearchDto search, PageDto<YuanliaoDto> page) {
		Integer size = page.getPageSize();
		page.setPageSize(2000);
		page.setSort("refresh_time");
		CategoryFacade cate = CategoryFacade.getInstance();
		List<Yuanliao> list = yuanliaoDao.queryYuanliaoSearchDto(search, page);
		if (size == null) {
			size = 20;
		}
		page.setPageSize(size);
		List<YuanliaoDto> record = new ArrayList<YuanliaoDto>();
		if ((page.getCurrentPage() - 1) * page.getPageSize() < list.size()
				&& (page.getCurrentPage()) * page.getPageSize() < list.size()) {
			for (int i = (page.getCurrentPage() - 1) * page.getPageSize(); i < (page.getCurrentPage())
					* page.getPageSize(); i++) {
				YuanliaoDto dto = new YuanliaoDto();
				Company company = companyService.queryCompanyById(list.get(i).getCompanyId());
				dto.setYuanliao(list.get(i));
				dto.setCompany(company);
				record.add(dto);
			}
		} else {
			for (int i = (page.getCurrentPage() - 1) * page.getPageSize(); i < list.size(); i++) {
				YuanliaoDto dto = new YuanliaoDto();
				Company company = companyService.queryCompanyById(list.get(i).getCompanyId());
				dto.setCompany(company);
				dto.setYuanliao(list.get(i));
				record.add(dto);
			}
		}
		for (YuanliaoDto yl : record) {
			// id不存在
			if (yl.getYuanliao().getId() == null) {
				continue;
			}
			// 地址中文
			if (StringUtils.isNotEmpty(yl.getYuanliao().getLocation())) {
				String location = "";
				if (StringUtils.isNotEmpty(yl.getYuanliao().getLocation())) {
					if (yl.getYuanliao().getLocation().length() > 12) {
						location = CategoryFacade.getInstance()
								.getValue(yl.getYuanliao().getLocation().substring(0, 12))
								+ CategoryFacade.getInstance()
										.getValue(yl.getYuanliao().getLocation().substring(0, 16));
					} else if (yl.getYuanliao().getLocation().length() > 8
							&& yl.getYuanliao().getLocation().length() < 13) {
						location = CategoryFacade.getInstance().getValue(yl.getYuanliao().getLocation().substring(0, 8))
								+ CategoryFacade.getInstance()
										.getValue(yl.getYuanliao().getLocation().substring(0, 12));
					} else {
						if (yl.getYuanliao().getLocation().length() == 8) {
							location = CategoryFacade.getInstance()
									.getValue(yl.getYuanliao().getLocation().substring(0, 8));
						}
					}
					yl.setLocation(location);
				}
			}
			// 获取图片
			YuanliaoPic pic = new YuanliaoPic();
			pic.setYuanliaoId(yl.getYuanliao().getId());
			pic.setIsDel(0);
			pic.setCheckStatus(1);
			List<YuanliaoPic> listPic = yuanliaoPicDao.queryYuanliaoPicByYuanliaoId(pic, 1);
			if (listPic.size() > 0) {
				yl.setPicAddress(listPic.get(0).getPicAddress());
			}
			//
			if (StringUtils.isNotEmpty(yl.getYuanliao().getType())) {
				String lovation = cate.getValue(yl.getYuanliao().getType());
				yl.getYuanliao().setType(lovation);
			}
			//
			if (StringUtils.isNotEmpty(yl.getYuanliao().getDescription())) {
				yl.getYuanliao().setDescription(Jsoup.clean(yl.getYuanliao().getDescription(), Whitelist.none()));
			}
		}
		page.setRecords(record);
		page.setTotalRecords(list.size());
		return page;
	}

	@Override
	public Integer queryYuanliaoSearchDtoCount(SearchDto search) {
		return yuanliaoDao.queryYuanliaoSearchDtoCount(search);
	}

	@Override
	public List<YuanliaoDto> queryYuanliaoBYCompanyIdPic(Integer companyId) {
		List<Yuanliao> yuan = yuanliaoDao.queryYuanliaoBYCompanyId(companyId);
		List<YuanliaoDto> yuanliao = new ArrayList<YuanliaoDto>();
		for (Yuanliao yu : yuan) {
			YuanliaoDto dto2 = new YuanliaoDto();
			dto2.setYuanliao(yu);
			YuanliaoPic pic = new YuanliaoPic();
			pic.setYuanliaoId(yu.getId());
			pic.setIsDel(0);
			pic.setCheckStatus(1);
			List<YuanliaoPic> listPic = yuanliaoPicDao.queryYuanliaoPicByYuanliaoId(pic, 1);
			if (listPic.size() > 0) {
				dto2.setPicAddress(listPic.get(0).getPicAddress());
			}
			yuanliao.add(dto2);
		}
		return yuanliao;
	}

	@Override
	public List<Yuanliao> queryNewSize(Integer size) {
		return yuanliaoDao.queryNewSize(size);
	}
}
