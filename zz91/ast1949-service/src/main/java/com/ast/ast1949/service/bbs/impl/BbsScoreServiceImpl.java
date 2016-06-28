package com.ast.ast1949.service.bbs.impl;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.bbs.BbsPostDO;
import com.ast.ast1949.domain.bbs.BbsPostReplyDO;
import com.ast.ast1949.domain.bbs.BbsScore;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.bbs.BbsScoreDto;
import com.ast.ast1949.persist.bbs.BbsPostDAO;
import com.ast.ast1949.persist.bbs.BbsPostReplyDao;
import com.ast.ast1949.persist.bbs.BbsScoreDao;
import com.ast.ast1949.service.bbs.BbsScoreService;
import com.ast.ast1949.util.DateUtil;
import com.zz91.util.lang.StringUtils;

@Component("bbsScoreService")
public class BbsScoreServiceImpl implements BbsScoreService {

	@Resource
	private BbsScoreDao bbsScoreDao;
	@Resource
	private BbsPostDAO bbsPostDAO;
	@Resource
	private BbsPostReplyDao bbsPostReplyDao;

	@Override
	public Integer insert(BbsScore bbsScore) {

		if (bbsScore.getBbsPostId() == null) {
			bbsScore.setBbsPostId(0);
		}
		if (bbsScore.getQaPostId() == null) {
			bbsScore.setQaPostId(0);
		}
		if (bbsScore.getBbsReplyId() == null) {
			bbsScore.setBbsReplyId(0);
		}
		if (bbsScore.getQaReplyId() == null) {
			bbsScore.setQaReplyId(0);
		}

		return bbsScoreDao.insert(bbsScore);
	}

	@Override
	public PageDto<BbsScoreDto> page(BbsScoreDto bbsScoreDto,
			PageDto<BbsScoreDto> page) {
		page.setTotalRecords(bbsScoreDao.queryCount(bbsScoreDto));

		List<BbsScore> list = bbsScoreDao.query(bbsScoreDto, page);
		List<BbsScoreDto> resultList = new ArrayList<BbsScoreDto>();
		for (BbsScore obj : list) {
			BbsScoreDto dto = new BbsScoreDto();
			dto.setBbsScore(obj);
			do {
				// 帖子
				if (obj.getBbsPostId() != null && obj.getBbsPostId() > 0) {
					BbsPostDO bbsPostDO = bbsPostDAO.queryPostById(obj
							.getBbsPostId());
					if (bbsPostDO != null) {
						dto.setVisitedCount(bbsPostDO.getVisitedCount());
						dto.setReplyCount(bbsPostDO.getReplyCount());
						dto.setTitle(bbsPostDO.getTitle());
						String content = bbsPostDO.getContent();
						content = Jsoup.clean(content, Whitelist.none());
						try {
							dto.setContent(StringUtils.controlLength(content,
									15));
						} catch (UnsupportedEncodingException e) {
						}
						dto.setBbsPostId(obj.getBbsPostId());
						break;
					}
				}

				// 提问
				if (obj.getQaPostId() != null && obj.getQaPostId() > 0) {
					BbsPostDO bbsPostDO = bbsPostDAO.queryPostById(obj
							.getQaPostId());
					if (bbsPostDO != null) {
						dto.setVisitedCount(bbsPostDO.getVisitedCount());
						dto.setReplyCount(bbsPostDO.getReplyCount());
						dto.setTitle(bbsPostDO.getTitle());
						String content = bbsPostDO.getContent();
						content = Jsoup.clean(content, Whitelist.none());
						try {
							dto.setContent(StringUtils.controlLength(content,
									15));
						} catch (UnsupportedEncodingException e) {
						}
						dto.setBbsPostId(obj.getBbsPostId());
						break;
					}
				}

				// 回帖
				if (obj.getBbsReplyId() != null && obj.getBbsReplyId() > 0) {
					BbsPostReplyDO bbsPostReplyDO = bbsPostReplyDao
							.queryById(obj.getBbsReplyId());
					if (bbsPostReplyDO != null) {
						BbsPostDO bbsPostDO = bbsPostDAO
								.queryPostById(bbsPostReplyDO.getBbsPostId());
						if (bbsPostDO != null) {
							dto.setTitle(bbsPostDO.getTitle());
							dto.setBbsPostId(bbsPostDO.getId());
						}
					}
					if (bbsPostReplyDO != null) {
						String content = bbsPostReplyDO.getContent();
						content = Jsoup.clean(content, Whitelist.none());
						try {
							dto.setContent(StringUtils.controlLength(content,
									15));
						} catch (UnsupportedEncodingException e) {
						}
					}
					break;
				}

				// 回答
				if (obj.getQaReplyId() != null && obj.getQaReplyId() > 0) {
					BbsPostReplyDO bbsPostReplyDO = bbsPostReplyDao
							.queryById(obj.getQaReplyId());
					if (bbsPostReplyDO != null) {
						BbsPostDO bbsPostDO = bbsPostDAO
								.queryPostById(bbsPostReplyDO.getBbsPostId());
						if (bbsPostDO != null) {
							dto.setTitle(bbsPostDO.getTitle());
							dto.setBbsPostId(bbsPostDO.getId());
						}
					}
					if (bbsPostReplyDO != null) {
						String content = bbsPostReplyDO.getContent();
						content = Jsoup.clean(content, Whitelist.none());
						try {
							dto.setContent(StringUtils.controlLength(content,
									15));
						} catch (UnsupportedEncodingException e) {
						}
					}
					break;
				}

			} while (false);

			resultList.add(dto);
		}
		page.setRecords(resultList);

		return page;
	}

	@Override
	public Integer sumScore(BbsScore bbsScore) {
		if (bbsScore.getCompanyId()==null) {
			return 0;
		}
		Integer i = bbsScoreDao.sumScore(bbsScore);
		if (i == null) {
			return 0;
		}
		return i;
	}

	@Override
	public Integer postScore(BbsPostDO bbsPostDO) {
		Integer i = 0;
		do {

			BbsScore bbsScore = new BbsScore();
			String remarkFix = "";
			bbsScore.setCompanyId(bbsPostDO.getCompanyId());
			bbsScore.setCompanyAccount(bbsPostDO.getAccount());

			// 判断是帖子还是问题
			if (bbsPostDO.getBbsPostCategoryId().equals(11)) {
				remarkFix = "问题";
				bbsScore.setQaPostId(bbsPostDO.getId());
			} else {
				remarkFix = "帖子";
				bbsScore.setBbsPostId(bbsPostDO.getId());
			}

			// 判断是否 有过该积分记录
			i = sumScore(bbsScore);

			// 一般帖子类型
			bbsScore.setPostType(0);

			// 计算本次帖子得分 主要判断图片张数
			Integer pubScore = 5;
			String content = bbsPostDO.getContent();
			Pattern p = Pattern.compile("<img.*src=(.*?)[^>]*?>");
			Matcher m = p.matcher(content);
			while (m.find()) {
				if (StringUtils.isNotEmpty(m.group())) {
					bbsScore.setPostType(POST_PIC);
					pubScore = pubScore + 2;
				}
				if (pubScore >= 15) {
					break;
				}
			}

			// 文字贴修改 分数不变
			if (5 == i && 5 == pubScore) {
				break;
			}

			if (pubScore > 5) {
				remarkFix = "图片" + remarkFix;
			}

			if (i > 15) {
				i = i - 15;
			}

			// 没有积分记录
			if (i == 0) {
				bbsScore.setScore(pubScore);
				bbsScore.setRemark(remarkFix);
				insert(bbsScore);
				i = pubScore;
				break;
			}

			// 有积分记录
			Integer resultScore = pubScore - i;
			// 积分差为 0 ，积分不变
			if (resultScore == 0) {
				break;
			}
			// 设置积分
			bbsScore.setScore(resultScore);
			i = resultScore;
			// 判断图片增减情况
			Integer picNum = 0;
			picNum = Math.abs(resultScore) / 2;
			if (resultScore < 0) {
				remarkFix = remarkFix + " - 减少" + picNum + "张图片";
			} else {
				remarkFix = remarkFix + " - 增加" + picNum + "张图片";
			}
			// 设置原因
			bbsScore.setRemark(remarkFix);
			insert(bbsScore);
		} while (false);
		return i;
	}

	@Override
	public Integer replyScore(BbsPostReplyDO bbsPostReplyDO) {
		Integer i = 0;
		do {

			if (bbsPostReplyDO == null || bbsPostReplyDO.getBbsPostId() == null
					|| bbsPostReplyDO.getBbsPostId() < 1) {
				break;
			}

			// 回贴 还是 回答
			BbsPostDO bbsPostDO = bbsPostDAO.queryPostById(bbsPostReplyDO
					.getBbsPostId());
			if (bbsPostDO == null) {
				break;
			}
			Integer size = 3;
			BbsScore bbsScore = new BbsScore();
			// 回答 || 回帖
			if (bbsPostDO.getBbsPostCategoryId() == 11) {
				size = 1;
				bbsScore.setQaReplyId(bbsPostReplyDO.getId());
			} else {
				bbsScore.setBbsReplyId(bbsPostReplyDO.getId());
			}
			// 判断是不是这个时间内做活动
			Date now = new Date();
			Integer n = -1;
			try {
				n = DateUtil.getIntervalDays(now,
						DateUtil.getDate("2014-06-16", "yyyy-MM-dd"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Integer m = -1;
			try {
				m = DateUtil.getIntervalDays(
						DateUtil.getDate("2014-06-20", "yyyy-MM-dd"), now);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 做活动特定头条积分加倍
			if (bbsPostReplyDO.getBbsPostId() == 635830
					&& (n != null && n.intValue() >= 0)
					&& (m != null && m.intValue() >= 0)) {
				List<BbsPostReplyDO> list = bbsPostReplyDao.queryReplyByPostId(
						bbsPostDO.getId(), size);
				Integer num = 1;
				Integer findNum = 0;
				for (BbsPostReplyDO obj : list) {
					if (obj.getId().equals(bbsPostReplyDO.getId())) {
						findNum = num;
						break;
					}
					num++;
				}

				if (num > 3) {
					Integer j = bbsPostReplyDao
							.queryReplyByAdminCount(bbsPostReplyDO);
					if (j > 1) {

						bbsScore.setScore(2);
						bbsScore.setReplyType(0);
						bbsScore.setRemark("一般回帖");
					} else {
						bbsScore.setScore(6);
						bbsScore.setReplyType(0);
						bbsScore.setRemark("一般回帖");
					}
				} else if (num == 2 && findNum == 0) {
					bbsScore.setScore(2);
					bbsScore.setReplyType(0);
					bbsScore.setRemark("一般回答");
				} else {
					Integer j = bbsPostReplyDao
							.queryReplyByAdminCount(bbsPostReplyDO);
					if (j > 1) {
						bbsScore.setScore(6 - num);
						bbsScore.setReplyType(num);
					} else {

						bbsScore.setScore((6 - num) * 3);
						bbsScore.setReplyType(num);
					}
					if (num == 1) {
						if (bbsScore.getQaReplyId() != null
								&& bbsScore.getQaReplyId() > 0) {
							bbsScore.setRemark("最快回答");
						} else {
							bbsScore.setRemark("沙发");
						}
					} else if (num == 2) {
						bbsScore.setRemark("板凳");
					} else {
						bbsScore.setRemark("地板");
					}
				}

			} else {
				List<BbsPostReplyDO> list = bbsPostReplyDao.queryReplyByPostId(
						bbsPostDO.getId(), size);
				Integer num = 1;
				Integer findNum = 0;

				for (BbsPostReplyDO obj : list) {
					if (obj.getId().equals(bbsPostReplyDO.getId())) {
						findNum = num;
						break;
					}
					num++;
				}

				if (num > 3) {
					bbsScore.setScore(2);
					bbsScore.setReplyType(0);
					bbsScore.setRemark("一般回帖");
				} else if (num == 2 && findNum == 0) {
					bbsScore.setScore(2);
					bbsScore.setReplyType(0);
					bbsScore.setRemark("一般回答");
				} else {
					bbsScore.setScore(6 - num);
					bbsScore.setReplyType(num);
					if (num == 1) {
						if (bbsScore.getQaReplyId() != null
								&& bbsScore.getQaReplyId() > 0) {
							bbsScore.setRemark("最快回答");
						} else {
							bbsScore.setRemark("沙发");
						}
					} else if (num == 2) {
						bbsScore.setRemark("板凳");
					} else {
						bbsScore.setRemark("地板");
					}
				}
			}
			// 返回获得积分
			i = bbsScore.getScore();
			bbsScore.setCompanyAccount(bbsPostReplyDO.getAccount());
			bbsScore.setCompanyId(bbsPostReplyDO.getCompanyId());

			insert(bbsScore);

		} while (false);
		return i;
	}

	@SuppressWarnings("null")
	@Override
	public Integer checkPost(Integer id, Integer isPass) {
		Integer i = 0;
		do {

			BbsPostDO obj = bbsPostDAO.queryPostById(id);
			BbsScore bbsScore = new BbsScore();

			if (obj == null) {
				break;
			}

			BbsScore searchBbsScore = new BbsScore();
			if (obj.getBbsPostCategoryId() == 11) {
				searchBbsScore.setQaPostId(id);
				bbsScore.setQaPostId(id);
			} else {
				searchBbsScore.setBbsPostId(id);
				bbsScore.setBbsPostId(id);
			}

			i = bbsScoreDao.sumScore(searchBbsScore);
			// 积分小于0 跳出
			if (i != null) {
				if (i < 0) {
					break;
				}

				// 积分等于0 审核不通过 不扣分
				if (i == 0 && isPass.equals(0)) {
					break;
				}

				bbsScore.setCompanyId(obj.getCompanyId());
				bbsScore.setCompanyAccount(obj.getAccount());
				bbsScore.setPostType(0);
				bbsScore.setReplyType(0);

				// 积分和变动原因
				if (isPass.equals(1)) {
					bbsScore = getScoreAndRemark(i, obj, bbsScore);

					if ("3".equals(obj.getPostType())) {
						if(bbsScore!=null){
							bbsScore.setScore(bbsScore.getScore() + 15);
						}
					}
				} else {
					bbsScore.setScore(-i);
					bbsScore.setRemark("审核-退回-扣除所有积分");
				}

				if (bbsScore != null) {
					insert(bbsScore);
				}
			}

		} while (false);
		return i;
	}

	@Override
	public Integer checkReply(Integer id, Integer isPass) {
		Integer i = 0;
		do {
			Integer postId = bbsPostReplyDao.queryBbsPostByReplyId(id);
			if (postId == null) {
				break;
			}
			BbsPostDO post = bbsPostDAO.queryPostById(postId);
			if (post == null) {
				break;
			}
			BbsScore searchBbsScore = new BbsScore();
			BbsScore bbsScore = new BbsScore();

			bbsScore.setCompanyAccount(post.getAccount());
			bbsScore.setCompanyId(post.getCompanyId());

			Integer size = 3;
			if (post.getBbsPostCategoryId() == 11) {
				size = 1;
				searchBbsScore.setQaReplyId(id);
				bbsScore.setQaReplyId(id);
			} else {
				searchBbsScore.setBbsReplyId(id);
				bbsScore.setBbsReplyId(id);
			}

			i = bbsScoreDao.sumScore(searchBbsScore);
          if(i!=null){
			// 积分不变动
			if (isPass.equals(1) && i > 0) {
				i = 0;
				break;
			}

			// 积分不变动
			if (isPass.equals(0) && i < 0) {
				i = 0;
				break;
			}
			if (isPass.equals(1)) {

				List<BbsPostReplyDO> list = bbsPostReplyDao.queryReplyByPostId(
						post.getId(), size);
				Integer num = 1;
				Integer findNum = 0;
				for (BbsPostReplyDO obj : list) {
					if (obj.getId().equals(id)) {
						findNum = num;
						break;
					}
					num++;
				}

				bbsScore.setReplyType(0);
				if (num > 3) {
					bbsScore.setScore(2);
				} else if (num == 2 && findNum == 0) {
					bbsScore.setScore(2);
				} else {
					bbsScore.setScore(6 - num);
				}
				bbsScore.setRemark("审核-通过");
			} else {
				bbsScore.setScore(-i);
				bbsScore.setRemark("审核-不通过");
			}

			insert(bbsScore);
          }
		} while (false);
		

		return i;
	}

	@Override
	public Integer delPost(Integer id, Integer isDel) {
		Integer i = 0;
		do {

			BbsPostDO obj = bbsPostDAO.queryPostById(id);
			BbsScore bbsScore = new BbsScore();

			if (obj == null) {
				break;
			}

			BbsScore searchBbsScore = new BbsScore();
			if (obj.getBbsPostCategoryId() == 11) {
				searchBbsScore.setQaPostId(id);
				bbsScore.setQaPostId(id);
			} else {
				searchBbsScore.setBbsPostId(id);
				bbsScore.setBbsPostId(id);
			}

			BbsScore score = bbsScoreDao.querybyId(searchBbsScore);
			if (score == null) {
				break;
			}
			i = bbsScoreDao.sumScore(searchBbsScore);

			// 积分小于0 跳出
			if (i <= 0) {
				break;
			}

			// 积分等于0 删除 不扣分
			if (i == 0 && isDel.equals(1)) {
				break;
			}

			bbsScore.setCompanyId(obj.getCompanyId());
			bbsScore.setCompanyAccount(obj.getAccount());
			bbsScore.setPostType(0);
			bbsScore.setReplyType(0);

			// 积分和变动原因
			if (isDel.equals(0)) {
				bbsScore = getScoreAndRemark(i, obj, bbsScore);
				bbsScore.setRemark(bbsScore.getRemark().replace("审核-通过-", "恢复"));
			} else {
				bbsScore.setScore(-i);
				bbsScore.setRemark("删除-扣除所有积分");
			}

			if (bbsScore != null) {
				insert(bbsScore);
			}

		} while (false);
		return i;
	}

	@Override
	public Integer delreply(Integer id, Integer isDel) {
		Integer i = 0;
		do {

			BbsPostReplyDO bbsPostReplyDO = bbsPostReplyDao.queryById(id);
			if (bbsPostReplyDO == null) {
				break;
			}
			BbsPostDO obj = bbsPostDAO.queryPostById(bbsPostReplyDO
					.getBbsPostId());
			BbsScore bbsScore = new BbsScore();

			if (obj == null) {
				break;
			}

			BbsScore searchBbsScore = new BbsScore();
			if (obj.getBbsPostCategoryId() == 11) {
				searchBbsScore.setQaReplyId(id);
				bbsScore.setQaReplyId(id);
			} else {
				searchBbsScore.setBbsReplyId(id);
				bbsScore.setBbsReplyId(id);
			}
			BbsScore score = bbsScoreDao.querybyId(searchBbsScore);
			if (score == null) {
				break;
			}
			i = bbsScoreDao.sumScore(searchBbsScore);

			// 积分小于0 跳出
			if (i < 0) {
				break;
			}

			// 积分等于0 删除 不扣分
			if (i == 0 && isDel.equals(1)) {
				break;
			}

			bbsScore.setCompanyId(bbsPostReplyDO.getCompanyId());
			bbsScore.setCompanyAccount(bbsPostReplyDO.getAccount());
			bbsScore.setPostType(0);
			bbsScore.setReplyType(0);

			// 积分和变动原因
			if (isDel.equals(0)) {
				bbsScore = getScoreAndRemark(i, obj, bbsScore);
				bbsScore.setRemark(bbsScore.getRemark().replace("审核-通过-", "恢复"));
			} else {
				bbsScore.setScore(-i);
				bbsScore.setRemark("删除-扣除所有积分");
			}

			if (bbsScore != null) {
				insert(bbsScore);
			}

		} while (false);
		return i;
	}

	private BbsScore getScoreAndRemark(Integer i, BbsPostDO bbsPostDO,
			BbsScore bbsScore) {
		String remarkFix = "";
		// 判断是帖子还是问题
		if (bbsPostDO.getBbsPostCategoryId().equals(11)) {
			remarkFix = "问题";
		} else {
			remarkFix = "帖子";
		}
		// 计算本次帖子得分 主要判断图片张数
		Integer pubScore = 5;
		String content = bbsPostDO.getContent();
		Pattern p = Pattern.compile("<img.*src=(.*?)[^>]*?>");
		Matcher m = p.matcher(content);
		while (m.find()) {
			if (StringUtils.isNotEmpty(m.group())) {
				bbsScore.setPostType(POST_PIC);
				pubScore = pubScore + 2;
			}
			if (pubScore >= 15) {
				break;
			}
		}
		// 文字贴修改 分数不变
		if (5 == i && 5 == pubScore) {
			return null;
		}

		if (pubScore > 5) {
			remarkFix = "图片" + remarkFix;
		}

		if (i > 15) {
			i = i - 15;
		}

		// 没有积分记录
		if (i == 0) {
			bbsScore.setScore(pubScore);
			bbsScore.setRemark("审核-通过-" + remarkFix);
			return bbsScore;
		}

		// 有积分记录
		Integer resultScore = pubScore - i;
		// 积分差为 0 ，积分不变
		if (resultScore == 0) {
			return null;
		}
		// 设置积分
		bbsScore.setScore(resultScore);
		i = resultScore;
		// 判断图片增减情况
		Integer picNum = 0;
		picNum = Math.abs(resultScore) / 2;
		if (resultScore < 0) {
			remarkFix = remarkFix + " - 减少" + picNum + "张图片";
		} else {
			remarkFix = remarkFix + " - 增加" + picNum + "张图片";
		}
		// 设置原因
		bbsScore.setRemark("审核-通过-" + remarkFix);
		return bbsScore;
	}

}
