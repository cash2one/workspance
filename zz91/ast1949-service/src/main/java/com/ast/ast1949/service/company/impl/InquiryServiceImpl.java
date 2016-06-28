package com.ast.ast1949.service.company.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.Face;
import com.ast.ast1949.domain.company.Company;
import com.ast.ast1949.domain.company.CompanyAccount;
import com.ast.ast1949.domain.company.Inquiry;
import com.ast.ast1949.domain.products.ProductsDO;
import com.ast.ast1949.domain.products.ProductsExportInquiry;
import com.ast.ast1949.domain.yuanliao.YuanLiaoExportInquiry;
import com.ast.ast1949.domain.yuanliao.Yuanliao;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.company.InquiryDto;
import com.ast.ast1949.exception.ServiceLayerException;
import com.ast.ast1949.persist.company.CompanyAccountDao;
import com.ast.ast1949.persist.company.CompanyDAO;
import com.ast.ast1949.persist.company.InquiryDao;
import com.ast.ast1949.persist.products.ProductsDAO;
import com.ast.ast1949.persist.products.ProductsExportInquiryDao;
import com.ast.ast1949.persist.yuanliao.YuanliaoDao;
import com.ast.ast1949.service.company.CrmCompanySvrService;
import com.ast.ast1949.service.company.InquiryService;
import com.ast.ast1949.service.facade.CategoryFacade;
import com.ast.ast1949.util.Assert;
import com.ast.ast1949.util.DateUtil;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.sms.SmsUtil;

@Component("inquiryService")
public class InquiryServiceImpl implements InquiryService {

	@Resource
	private InquiryDao inquiryDao;
	@Resource
	private CompanyDAO companyDAO;
	@Resource
	private CompanyAccountDao companyAccountDao;
	@Resource
	private CrmCompanySvrService crmCompanySvrService;
	@Resource
	private ProductsDAO productsDAO;
	@Resource
	private ProductsExportInquiryDao productsExportInquiryDao;
	@Resource
	private YuanliaoDao yuanliaoDao;

	/**
	 * 0：时间 1：留言人姓名 2：被查看信息 3：留言人电话 4:地区
	 */
	final static String SMS_PRODUCT = "sms_product";
	final static String SMS_COMPANY = "sms_company";
	final static String SMS_INQUIRY = "sms_inquiry";

	@Override
	public Integer countUnviewedInquiry(String beInquiredType,
			String receiveAccount, Integer companyId) {
		Assert.notNull(receiveAccount, "the receive account can not be null");
		Integer i = inquiryDao.countUnviewedInquiry(beInquiredType,
				receiveAccount, companyId);
		if (i == null) {
			return 0;
		}
		return i;
	}

	@Override
	public Integer groupInquiry(Integer inquiryId, Integer groupId) {
		Assert.notNull(inquiryId, "the inquiryId can not be null");
		Assert.notNull(groupId, "the groupId can not be null");
		return inquiryDao.updateInquiryGroup(inquiryId, groupId);
	}

	final static String SMS_API = "http://admin1949.zz91.com/reborn-admin/sms/main/doSendFromEP.htm?account=kongsj&password=123456";

	@Override
	public Integer inquiryByUser(Inquiry inquiry, Integer companyId) {
		Assert.notNull(inquiry.getBeInquiredType(),
				"the inquiry.beinquredType can not be null");
		Assert.notNull(inquiry.getSenderAccount(),
				"the inquiry.senderAccount can not be null");
		Assert.notNull(inquiry.getReceiverAccount(),
				"the inquiry.receiverAccount can not be null");
		Assert.notNull(inquiry.getBeInquiredType(),
				"the inquiry.beinquredType can not be null");
		Assert.notNull(inquiry.getBeInquiredType(),
				"the inquiry.beinquredType can not be null");

		inquiry.setGroupId(0);
		inquiry.setInquiredType("0");
		inquiry.setBatchSendType("0");
		inquiry.setExportStatus("0");
		inquiry.setExportPerson("");
		inquiry.setIsViewed("0");
		inquiry.setIsSenderDel("0");
		inquiry.setIsReceiverDel("0");
		inquiry.setIsReplyed("0");

		if (inquiry.getConversationGroup() == null) {
			inquiry.setConversationGroup(UUID.randomUUID().toString());
		}
		// 判断是不是该设置为rubbish
		Integer i = inquiryDao.countBlackedCompany(
				inquiry.getReceiverAccount(), companyId);
		if (i != null && i.intValue() > 0) {
			inquiry.setIsRubbish("1");
		} else {
			inquiry.setIsRubbish("0");
		}

		if (BE_INQUIRYED_TYPE_INQUIRY.equals(inquiry.getBeInquiredType())) {
			inquiryDao.updateIsReplyed(inquiry.getBeInquiredId(), "1");
		}

		Integer result = inquiryDao.insertInquiry(inquiry);
		if (result != null && result.intValue() > 0
				&& !inquiry.getBeInquiredType().equals("5")) {
			// 判断是否开通移动生意管家，如果开通则发送短信
			Integer inquiredCompanyId = companyAccountDao
					.queryCompanyIdByAccount(inquiry.getReceiverAccount());

			if (crmCompanySvrService.validatePeriod(inquiredCompanyId,
					"10001000")) {
				// 0：时间 1：留言人姓名 2：被查看信息 3：留言人电话 4:地区

				String msg0 = DateUtil.toString(new Date(), "HH:mm");
				CompanyAccount receiver = companyAccountDao
						.queryAccountByAccount(inquiry.getReceiverAccount());
				CompanyAccount sender = companyAccountDao
						.queryAccountByAccount(inquiry.getSenderAccount());
				Company company = companyDAO.querySimpleCompanyById(sender
						.getCompanyId());

				String msg1 = sender.getContact();
				String msg2 = "";
				String msg3 = sender.getMobile();
				String msg4 = "";
				// MessageFormat format= null;
				String smsTemplateCode = null;

				if (StringUtils.isNotEmpty(company.getAreaCode())
						&& company.getAreaCode().length() >= 12) {
					msg4 = CategoryFacade.getInstance().getValue(
							company.getAreaCode());
				}

				// 发送短信 获取模板code

				if (BE_INQUIRYED_TYPE_DEFAULT.equals(inquiry
						.getBeInquiredType())) {
					if (inquiry.getBeInquiredId() != null) {
						msg2 = productsDAO.queryTitleById(inquiry
								.getBeInquiredId());
					}
					smsTemplateCode = SMS_PRODUCT;
					// format=new MessageFormat(SMS_PRODUCT);
				}

				if (BE_INQUIRYED_TYPE_COMPANY.equals(inquiry
						.getBeInquiredType())) {
					smsTemplateCode = SMS_COMPANY;
					// format=new MessageFormat(SMS_COMPANY);
				}

				if (BE_INQUIRYED_TYPE_INQUIRY.equals(inquiry
						.getBeInquiredType())) {
					smsTemplateCode = SMS_INQUIRY;
					// format=new MessageFormat(SMS_INQUIRY);
				}

				SmsUtil.getInstance().sendSms(smsTemplateCode,
						receiver.getMobile(), null, null,
						new String[] { msg0, msg1, msg2, msg3, msg4 });

				// System.out.println(format.format(new
				// String[]{msg0,msg1,msg2,msg3,msg4}));
				// String sms=format.format(new
				// String[]{msg0,msg1,msg2,msg3,msg4});
				// try {
				// sms=URLEncoder.encode(sms, HttpUtils.CHARSET_UTF8);
				// HttpUtils.getInstance().httpGet(SMS_API+"&mobile="+receiver.getMobile()+"&content="+sms,
				// HttpUtils.CHARSET_UTF8);
				// } catch (UnsupportedEncodingException e) {
				// } catch (HttpException e) {
				// } catch (IOException e) {
				// }
			}
		}

		return result;
	}

	@Override
	public Integer makeAsViewed(Integer inquiryId, Boolean viewFlag) {
		String view = "1";
		if (!viewFlag) {
			view = "0";
		}
		return inquiryDao.updateViewed(inquiryId, view);
	}

	@Override
	public boolean makeBlackList(Integer companyId, String account,
			Integer blackedCompanyId) {
		Integer i = inquiryDao.insertBlackList(companyId, account,
				blackedCompanyId);
		if (i != null && i.intValue() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public PageDto<InquiryDto> pageInquiryByUser(Inquiry inquiry,
			PageDto<InquiryDto> page) {
		page.setDir("desc");
		page.setSort("ir.send_time");
		List<InquiryDto> list = inquiryDao.queryInquiryByUser(inquiry, page);
		List<InquiryDto> nlist = new ArrayList<InquiryDto>();
		for (InquiryDto obj : list) {
			obj.setSenderCompanyName(companyDAO.queryCompanyById(
					companyAccountDao.queryCompanyIdByAccount(obj.getInquiry()
							.getSenderAccount())).getName());
			nlist.add(obj);
		}
		page.setRecords(nlist);
		page.setTotalRecords(inquiryDao.queryInquiryByUserCount(inquiry));
		return page;
	}

	@Override
	public List<InquiryDto> queryInquiryListByUser(Inquiry inquiry) {
		List<InquiryDto> dtoList = inquiryDao.queryInquiryListByUser(inquiry);
		return dtoList;
	}

	@Override
	public List<InquiryDto> queryConversation(String conversation) {
		if (StringUtils.isEmpty(conversation)) {
			return new ArrayList<InquiryDto>();
		}
		List<InquiryDto> dtoList = inquiryDao.queryConversation(conversation,
				20);
		for (InquiryDto inquiryDto : dtoList) {
			String content = Face.getFace(inquiryDto.getInquiry().getContent());
			inquiryDto.getInquiry().setContent(content);
		}
		return dtoList;
	}

	@Override
	public boolean removeBlackList(Integer companyId, String account,
			Integer blackedCompanyId) {
		Integer i = inquiryDao.deleteBlackList(companyId, account,
				blackedCompanyId);
		if (i != null && i.intValue() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Integer removeReceivedInquiry(Integer inquiryId, Boolean removeFlag) {
		String remove = "1";
		if (!removeFlag) {
			remove = "0";
		}
		return inquiryDao.updateReceiverDel(inquiryId, remove);
	}

	@Override
	public Integer removeSentInquiry(Integer inquiryId, Boolean removeFlag) {
		String remove = "1";
		if (!removeFlag) {
			remove = "0";
		}
		return inquiryDao.updateSenderDel(inquiryId, remove);
	}

	@Override
	public Integer replyInquiry(Inquiry inquiry, Integer companyId) {
		inquiry.setBeInquiredType(BE_INQUIRYED_TYPE_INQUIRY);
		Integer i = inquiryByUser(inquiry, companyId);
		return i;
	}

	@Override
	public Integer spamInquiry(Integer inquiryId, Boolean spamFlag) {
		String spam = "1";
		if (!spamFlag) {
			spam = "0";
		}
		return inquiryDao.updateIsRubbish(inquiryId, spam);
	}

	@Override
	public Integer ungroupInquiry(Integer inquiryId) {
		return inquiryDao.updateInquiryGroup(inquiryId, 0);
	}

	@Override
	public InquiryDto viewInquiry(Integer inquiryId) {
		InquiryDto dto = new InquiryDto();
		dto.setInquiry(inquiryDao.queryInquiry(inquiryId));
		inquiryDao.updateViewed(inquiryId, "1");
		return dto;
	}

	// @SuppressWarnings("unchecked")
	public List<InquiryDto> queryScrollInquiry() {

		// List<InquiryDto> list = (List<InquiryDto>)
		// MemcachedUtils.getInstance().getClient().get("zz91_index_scroll_inquiry");
		// MemcachedUtils.getInstance().getClient().set("zz91_index_scroll_inquiry",
		// 1800, list);
		// List<Inquiry> inquiryList = inquiryDao.queryScrollInquiry("0", 20);
		// List<InquiryDto> list = new ArrayList<InquiryDto>();
		// for (Inquiry inquiry : inquiryList) {
		// InquiryDto dto = new InquiryDto();
		// dto.setReceiverCompanyName(companyDAO.queryNameByAccount(inquiry
		// .getReceiverAccount()));
		// dto.setSenderCompanyName(companyAccountDao
		// .queryContactByAccount(inquiry.getSenderAccount()));
		// dto.setInquiry(inquiry);
		// list.add(dto);
		// }

		// MemcachedUtils.getInstance().getClient().set("zz91_index_scroll_inquiry",
		// 1800, list);

		// return list;
		return null;
	}

	@Override
	public Inquiry queryOneInquiry(Integer id) {
		Assert.notNull(id, "the id can not null");
		return inquiryDao.queryInquiry(id);
	}

	@Override
	public PageDto<Inquiry> pageAllConversation(String senderAccount,
			String receiverAccount, PageDto<Inquiry> page) {
		page.setSort("ir.send_time");
		page.setDir("desc");

		if (page.getPageSize() == null) {
			page.setPageSize(20);
		}

		if (page.getStartIndex() == null) {
			page.setStartIndex(0);
		}

		page.setRecords(inquiryDao.queryAllConversation(senderAccount,
				receiverAccount, page));
		page.setTotalRecords(inquiryDao.queryAllConversationCount(
				senderAccount, receiverAccount));

		return page;
	}

	@Override
	public PageDto<InquiryDto> pageInquiryBySender(String senderAccount,
			PageDto<InquiryDto> page) {

		page.setSort("send_time");
		page.setDir("desc");
		List<Inquiry> inquiryList = inquiryDao.queryInquiryBySenderUser(
				senderAccount, page);
		List<InquiryDto> resultList = new ArrayList<InquiryDto>();
		for (Inquiry ir : inquiryList) {
			InquiryDto dto = new InquiryDto();
			dto.setInquiry(ir);
			dto.setReceiverContact(companyAccountDao.queryContactByAccount(ir
					.getReceiverAccount()));
			dto.setReceiverCompanyName(companyDAO.queryNameByAccount(ir
					.getReceiverAccount()));
			resultList.add(dto);
		}
		page.setRecords(resultList);
		page.setTotalRecords(inquiryDao
				.queryInquiryBySenderUserCount(senderAccount));
		return page;
	}

	@Override
	public PageDto<InquiryDto> pageInquiryByList(InquiryDto inquiryDto,
			PageDto<InquiryDto> page) {
		if (page.getSort() == null) {
			page.setSort("send_time");
		}
		if (page.getSort() == null) {
			page.setDir("desc");
		}
		List<Inquiry> list = inquiryDao.queryInquiryByList(inquiryDto, page);
		List<InquiryDto> nlist = new ArrayList<InquiryDto>();
		for (Inquiry obj : list) {
			InquiryDto dto = new InquiryDto();
			dto.setInquiry(obj);
			nlist.add(dto);
		}
		page.setTotalRecords(inquiryDao.countInquiryByList(inquiryDto));
		page.setRecords(nlist);
		return page;
	}

	@Override
	public Inquiry queryDownMessageById(Integer id, String receiverAccount,
			String sendAccount, String type) {

		return inquiryDao.queryDownMessageById(id, receiverAccount,
				sendAccount, type);
	}

	@Override
	public Inquiry queryOnMessageById(Integer id, String receiverAccount,
			String sendAccount, String type) {

		return inquiryDao.queryOnMessageById(id, receiverAccount, sendAccount,
				type);
	}

	@Override
	public PageDto<Inquiry> pageInquiryByAdmin(Inquiry inquiry,
			PageDto<Inquiry> page) {
		if (page.getSort() == null) {
			page.setSort("id");
		}
		page.setRecords(inquiryDao.queryInquiryByAdmin(inquiry, page));
		page.setTotalRecords(inquiryDao.queryInquiryByAdminCount(inquiry));
		return page;
	}

	/**************** 以下老代码 *******************************/

	// public final static String EXPORT_STATUS = "export_status";
	//
	// @Override
	// public Integer insertInquiry(InquiryDO inquiryDO) {
	// inquiryDO.setSendTime(new Date());
	// inquiryDO.setIsSenderDel(InquiryService.UNDELETED);
	// inquiryDO.setIsReceiverDel(InquiryService.UNDELETED);
	// inquiryDO.setIsRubbish(InquiryService.DISRUBBISH);
	// inquiryDO.setIsViewed(InquiryService.UNVIEWED);
	// inquiryDO.setGroupId(InquiryService.GROUP_ID_DEFAULT);
	// inquiryDO.setExportStatus(InquiryService.EXPORT_STATUS_UNPROCESS);
	// if (inquiryDO.getBatchSendType() == null
	// || StringUtils.isEmpty(inquiryDO.getBatchSendType())) {
	// inquiryDO.setBatchSendType(InquiryService.BATCH_SEND_TYPE_DEFAULT);
	// }
	// if (StringUtils.isEmpty(inquiryDO.getInquiredType())) {
	// inquiryDO.setInquiredType(InquiryService.INQUIRYED_TYPE_DEFAULT);
	// }
	// // if(StringUtils.isEmpty(inquiryDO.getBeInquiredType())){
	// // inquiryDO.setBeInquiredType(InquiryService.BE_INQUIRYED_TYPE_INQUIRY);
	// // }
	// return inquiryDao.insertInquiry(inquiryDO);
	// }
	//
	// // @Override
	// // public Integer insertInquiryReply(InquiryDO inquiryDO) {
	// // if (InquiryService.BE_INQUIRYED_TYPE_INQUIRY.equals(inquiryDO
	// // .getBeInquiredType())) {
	// // InquiryDO inqBeReplyed = new InquiryDO();
	// // inqBeReplyed.setId(inquiryDO.getBeInquiredId());
	// // inqBeReplyed.setIsReplyed("1");
	// // inquiryDao.updateInquiry(inqBeReplyed);
	// // }
	// // return inquiryDao.insertInquiry(inquiryDO);
	// // }
	//
	// @Override
	// public Integer deleteInquiryById(Integer id) {
	// Assert.notNull(id, "the id must not be null");
	// return inquiryDao.deleteInquiryById(id);
	// }
	//
	// private List<Integer> getIdListFromArrayStr(String inquiryArrayStr) {
	// Assert.state(StringUtils.isNotEmpty(inquiryArrayStr), "询盘信息ID为空。");
	// String[] ids = inquiryArrayStr.split(",");
	// ids = StringUtils.distinctStringArray(ids);
	// List<Integer> inquiryIds = new ArrayList<Integer>();
	// for (String id : ids) {
	// if (StringUtils.isNumber(id))
	// inquiryIds.add(Integer.valueOf(id));
	// }
	// return inquiryIds;
	// }
	//
	// @Override
	// public Integer updateInquiryGroup(String inquiryArrayStr, Integer
	// groupId) {
	// Assert.notNull(groupId, "the groupId must not be null");
	// return inquiryDao.updateInquiryGroup(
	// getIdListFromArrayStr(inquiryArrayStr), groupId);
	// }
	//
	// @Override
	// public Integer updateInquirysDeleteStatus(String delType,
	// String inquiryArrayStr, String isDel) {
	// Assert.notNull(isDel, "the isDel must not be null");
	// return inquiryDao.updateInquiryIsDel(delType,
	// getIdListFromArrayStr(inquiryArrayStr), isDel);
	//
	// }
	//
	// @Override
	// public Integer updateInquiryIsRubbish(String inquiryArrayStr,
	// String isRubbish) {
	// Assert.notNull(isRubbish, "the isRubbish must not be null");
	// return inquiryDao.updateInquiryIsRubbish(
	// getIdListFromArrayStr(inquiryArrayStr), isRubbish);
	// }
	//
	// @Override
	// public void updateInquirySenderToBlackList(String inquiryArrayStr) {
	// inquiryDao
	// .updateInquirySenderToBlackList(getIdListFromArrayStr(inquiryArrayStr));
	// }
	//
	// @Override
	// public void deleteInquiryBlackListByInquiryIds(String inquiryIdsStr) {
	// inquiryDao
	// .deleteInquiryBlackListByInquiryIds(getIdListFromArrayStr(inquiryIdsStr));
	// }
	//
	// @Override
	// public Integer updateInquiryExportStatus(String inquiryArrayStr,
	// String exportStatus, String exportPerson) {
	// Assert.notNull(exportStatus, "the exportStatus must not be null");
	// return inquiryDao.updateInquiryExportStatus(
	// getIdListFromArrayStr(inquiryArrayStr), exportStatus,
	// exportPerson);
	// }
	//
	// @Override
	// public Integer updateInquiryIsViewed(String inquiryArrayStr, String
	// isViewed) {
	// Assert.notNull(isViewed, "the isViewed must not be null");
	// return inquiryDao.updateInquiryIsViewed(
	// getIdListFromArrayStr(inquiryArrayStr), isViewed);
	// }
	//
	@Override
	public void insertInquiryForProductsByInquiry(Integer productId,Inquiry inquiry, String productsArray,String account) throws ServiceLayerException {
		List<Inquiry> inquiryDOList = new ArrayList<Inquiry>();
		List<ProductsExportInquiry> exportList = new ArrayList<ProductsExportInquiry>();
		String[] strArr = StringUtils.distinctStringArray(productsArray.split(","));
		for (String str : strArr) {
			String[] val = str.split("\\|");
			if (val.length == 2) {
				Inquiry inq = new Inquiry();
				inq.setTitle(inquiry.getTitle());
				inq.setContent(inquiry.getContent());
				
				inq.setInquiredType("2"); // 后台系统导出
				inq.setInquiredId(inquiry.getId());
				
				inq.setSendTime(new Date());// 导出时间
				inq.setSenderAccount(inquiry.getSenderAccount()); // 导出发送帐号
				
				CompanyAccount companyAccount = companyAccountDao.queryAccountByCompanyId(Integer.valueOf(val[1]));
				if (companyAccount==null) {
					continue;
				}
				inq.setReceiverAccount(companyAccount.getAccount()); //导出接收帐号
				
				inq.setBeInquiredType(InquiryService.BE_INQUIRYED_TYPE_DEFAULT); // 询盘给供求
				inq.setBeInquiredId(Integer.valueOf(val[0])); // 目标询盘供求id
				
				inq.setIsRubbish("0");// 是否为垃圾询盘
				inq.setIsViewed("0"); // 是否已读
				inq.setIsSenderDel("0"); // 发送人删除
				inq.setIsReceiverDel("0"); // 收件人删除
				inq.setIsReplyed("0"); //是否回复
				
				inq.setExportPerson(account);// 导出人
				
				inq.setGroupId(0); // 组id
				inq.setExportStatus("0"); //导出状态
				inq.setBatchSendType("0"); //群发标志
				
				inquiryDOList.add(inq);
				
				// 导出日志 数据 装载
				ProductsExportInquiry obj = new ProductsExportInquiry();
				ProductsDO productsDO= productsDAO.queryProductsById(productId);
				obj.setFromCompanyId(productsDO.getCompanyId());
				obj.setProductId(productId);
				obj.setTargetId(Integer.valueOf(val[0]));
				obj.setToCompanyId(Integer.valueOf(val[1]));
				exportList.add(obj);
			}
		}
		
		inquiryDao.batchInsert(inquiryDOList);
		// 执行导出
		productsExportInquiryDao.batchInsert(exportList);
		
	}
	
	@Override
	public void insertInquiryForYuanLiaoByInquiry(Integer productId,
			Inquiry inquiry, String productsArray, String account){
		List<Inquiry> inquiryDOList = new ArrayList<Inquiry>();
		List<YuanLiaoExportInquiry> exportList = new ArrayList<YuanLiaoExportInquiry>();
		String[] strArr = StringUtils.distinctStringArray(productsArray.split(","));
		for (String str : strArr) {
			String[] val = str.split("\\|");
			if (val.length == 2) {
				Inquiry inq = new Inquiry();
				inq.setTitle(inquiry.getTitle());
				inq.setContent(inquiry.getContent());
				
				inq.setInquiredType("2"); // 后台系统导出
				inq.setInquiredId(inquiry.getId());
				
				inq.setSendTime(new Date());// 导出时间
				inq.setSenderAccount(inquiry.getSenderAccount()); // 导出发送帐号
				
				CompanyAccount companyAccount = companyAccountDao.queryAccountByCompanyId(Integer.valueOf(val[1]));
				if (companyAccount==null) {
					continue;
				}
				inq.setReceiverAccount(companyAccount.getAccount()); //导出接收帐号
				
				inq.setBeInquiredType(InquiryService.BE_INQUIRYED_TYPE_YUANLIAO); // 询盘给供求
				inq.setBeInquiredId(Integer.valueOf(val[0])); // 目标询盘供求id
				
				inq.setIsRubbish("0");// 是否为垃圾询盘
				inq.setIsViewed("0"); // 是否已读
				inq.setIsSenderDel("0"); // 发送人删除
				inq.setIsReceiverDel("0"); // 收件人删除
				inq.setIsReplyed("0"); //是否回复
				
				inq.setExportPerson(account);// 导出人
				
				inq.setGroupId(0); // 组id
				inq.setExportStatus("0"); //导出状态
				inq.setBatchSendType("0"); //群发标志
				
				inquiryDOList.add(inq);
				
				// 导出日志 数据 装载
				YuanLiaoExportInquiry obj = new YuanLiaoExportInquiry();
				Yuanliao yuanliao = yuanliaoDao.queryYuanliaoById(productId);
				obj.setFromCompanyId(yuanliao.getCompanyId());
				obj.setYuanLiaoId(productId);
				obj.setTargetId(Integer.valueOf(val[0]));
				obj.setToCompanyId(Integer.valueOf(val[1]));
				exportList.add(obj);
			}
		}
		
		inquiryDao.batchInsert(inquiryDOList);
		// 执行导出
		productsExportInquiryDao.batchInsertYuanLiao(exportList);
	}
	//
	// // @Override
	// // public List<InquiryDO> queryInquiryListByReceiverId(Integer
	// receiverId) {
	// // Assert.notNull(receiverId, "receiverId is not null");
	// // return inquiryDao.queryInquiryListByReceiverId(receiverId);
	// // }
	//
	// // @Override
	// // public Integer queryInquiryListByReceiverIdCount(Integer receiverId) {
	// // Assert.notNull(receiverId, "receiverId is not null");
	// // return inquiryDao.queryInquiryListByReceiverIdCount(receiverId);
	// // }
	//
	// // @Override
	// // public List<InquiryDO> queryInquiryListByBeInquiredId(
	// // String beInquiredType, Integer beInquiredId) {
	// // Assert.notNull(beInquiredId, "the beInquiredType must not be null");
	// // return inquiryDao.queryInquiryListByBeInquiredId(beInquiredType,
	// // beInquiredId);
	// // }
	//
	// // @Override
	// // public Integer queryInquiryListByBeInquiredIdCount(String
	// beInquiredType,
	// // Integer beInquiredId) {
	// // Assert.notNull(beInquiredId, "the beInquiredType must not be null");
	// // return inquiryDao.queryInquiryListByBeInquiredIdCount(beInquiredType,
	// // beInquiredId);
	// // }
	//
	// // @Override
	// // public InquiryDO queryInquiryById(Integer id) {
	// // Assert.notNull(id, "The id must not be null");
	// // return inquiryDao.queryInquiryById(id);
	// // }
	//
	// @Override
	// public InquiryDTO queryInquiryDetailById(Integer id) {
	// Assert.notNull(id, "The id must not be null");
	// return inquiryDao.queryInquiryDetailById(id);
	// }
	//
	// @Override
	// public PageDto queryPaginationInquiryListByReceiverAndSender(
	// Integer senderId, Integer receiverId, PageDto page) {
	// Assert.notNull(senderId, "The senderId must not be null");
	// Assert.notNull(receiverId, "The receiverId must not be null");
	// PaginationResult result = inquiryDao
	// .queryPaginationInquiryListByReceiverAndSender(senderId,
	// receiverId, page);
	// page.setRecords(result.getResultList());
	// page.setTotalRecords(result.getResultTotal());
	// return page;
	// }
	//
	// // @SuppressWarnings("unchecked")
	// // public PageDto listInquiryByDTO(InquiryDTO inquiryDTO) {
	// // Assert.notNull(inquiryDTO,
	// "The object of inquiryDTO must not be null");
	// //
	// inquiryDTO.getPage().setTotalRecords(inquiryDao.countInquiryByDTO(inquiryDTO));
	// //
	// // List<InquiryDTO> list = inquiryDao.listInquiryByDTO(inquiryDTO);
	// // for (InquiryDTO dto : list) {
	// //
	// // InquiryDTO inquiryDTO2 = dto;
	// // String status = inquiryDTO2.getInquiry().getExportStatus();
	// //
	// //
	// dto.setExportStatusName(ParamFacade.getInstance().getParamValue(EXPORT_STATUS,
	// // status));
	// // }
	// // inquiryDTO.getPage().setRecords(list);
	// //
	// // return inquiryDTO.getPage();
	// // }
	//
	// @Override
	// public Integer queryInquiryUnviewedByReciverIdCount(String
	// beInquiredType,
	// Integer receiverId) {
	// Assert.notNull(beInquiredType, "The beInquiredType must not be null");
	// Assert.notNull(receiverId, "The receiverId must not be null");
	// return inquiryDao.queryInquiryUnviewedByReciverIdCount(beInquiredType,
	// receiverId);
	// }
	//
	// // @Override
	// // public Integer countInquiryForFrontDTO(InquiryForFrontDTO
	// // inquiryForFrontDto) {
	// // return inquiryDao.countInquiryForFrontDTO(inquiryForFrontDto);
	// // }
	// //
	// // @Override
	// // public List<InquiryForFrontDTO>
	// // queryInquiryForFrontDTO(InquiryForFrontDTO inquiryForFrontDto) {
	// //
	// // List<InquiryForFrontDTO> list =
	// // inquiryDao.queryInquiryForFrontDTO(inquiryForFrontDto);
	// // for (InquiryForFrontDTO iq : list) {
	// // //BE_INQUIRYED_TYPE_INQUIRY
	// // Integer con = queryInquiryListByBeInquiredIdCount(null,
	// // iq.getInquiry().getId());
	// // iq.setHaveBeReply(con.toString());
	// // }
	// // return list;
	// //
	// // }
	//
	// public void createInquirysForProductsByProducts(
	// List<InquiryDO> inquiryDOList, String productsArray)
	// throws ServiceLayerException {
	// if (productsArray.length() > 0) {
	// String[] str = productsArray.split(",");
	//
	// List<InquiryDO> list = new ArrayList<InquiryDO>();
	// InquiryDO inquiry = new InquiryDO();
	// for (int i = 0; i < str.length; i++) {
	// for (int j = 0; j < inquiryDOList.size(); j++) {
	//
	// inquiry = inquiryDOList.get(j);
	// inquiry.setBatchSendType(BATCH_SEND_TYPE_2);
	// inquiry.setBeInquiredType(BE_INQUIRYED_TYPE_DEFAULT);
	// inquiry
	// .setInquiredType(InquiryService.INQUIRYED_TYPE_FROM_PRODUCTS);
	// inquiry
	// .setExportStatus(InquiryService.EXPORT_STATUS_UNPROCESS);
	// // inquiry.setSenderId(inquiry.getId());
	// // InquiryDO.receiverId
	// String[] val = str[i].split("\\|");
	// if (val.length == 2) {
	// inquiry.setBeInquiredId(Integer.valueOf(val[0]));
	// inquiry.setReceiverId(Integer.valueOf(val[1]));
	// list.add(inquiry);
	// }
	// }
	// }
	// inquiryDao.insertInquirys(list);
	// }
	// }
	//
	// @Override
	// public PageDto queryPaginationInquiryListByInquiryCondition(
	// InquiryDO inquiry, PageDto page) {
	// Assert.notNull(inquiry, "The inquiry must not be null");
	// //
	// Assert.state(inquiry.getSenderId()!=null||inquiry.getReceiverId()!=null,
	// // "");
	// PaginationResult result = inquiryDao
	// .queryPaginationInquiryListByInquiryCondition(inquiry, page);
	// page.setRecords(result.getResultList());
	// page.setTotalRecords(result.getResultTotal());
	// // for (Object obj : result.getResultList()) {
	// // InquiryForFrontDTO iq = (InquiryForFrontDTO) obj;
	// // Integer con = queryInquiryListByBeInquiredIdCount(null,
	// // iq.getInquiry().getId());
	// // iq.setHaveBeReply(con.toString());
	// // }
	// return page;
	// }
	//
	// @Override
	// public Integer countInquiryByConditions(InquiryDTO inquiryDto) {
	// return inquiryDao.countInquiryByConditions(inquiryDto);
	// }
	//
	// @Override
	// public List<InquiryDTO> queryInquiryByConditions(InquiryDTO inquiryDto) {
	// List<InquiryDTO> list = inquiryDao.queryInquiryByConditions(inquiryDto);
	//
	// for (InquiryDTO iq : list) {
	//
	// String status = iq.getInquiry().getExportStatus();
	// if (status != null) {
	// iq.setExportStatusName(ParamFacade.getInstance().getParamValue(
	// EXPORT_STATUS, status));
	// }
	// }
	//
	// return list;
	// }
	//
	// @Override
	// public InquiryDO queryInquiryContentById(Integer id) {
	// Assert.notNull(id, "The id can not be null");
	// return inquiryDao.queryInquiryContentById(id);
	// }
	//
	// @Override
	// public Integer setInquiryUngroupedByGroupId(Integer id) {
	// Assert.notNull(id, "The id can not be null");
	// return inquiryDao.setInquiryUngroupedByGroupId(id);
	// }

	@Override
	public Integer countByBeinquiredTypeAndTypeID(String beInquiredType, Integer beInquiredId) {
		return inquiryDao.countByBeinquiredTypeAndTypeID(beInquiredType, beInquiredId);
	}

}
