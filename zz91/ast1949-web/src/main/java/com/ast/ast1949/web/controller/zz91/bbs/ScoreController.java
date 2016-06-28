package com.ast.ast1949.web.controller.zz91.bbs;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ast.ast1949.domain.bbs.BbsPostDO;
import com.ast.ast1949.domain.bbs.BbsScore;
import com.ast.ast1949.domain.bbs.BbsUserProfilerDO;
import com.ast.ast1949.dto.ExtResult;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.bbs.BbsScoreDto;
import com.ast.ast1949.dto.bbs.BbsUserProfilerDto;
import com.ast.ast1949.service.bbs.BbsPostService;
import com.ast.ast1949.service.bbs.BbsScoreService;
import com.ast.ast1949.service.bbs.BbsUserProfilerService;
import com.ast.ast1949.web.controller.BaseController;
import com.zz91.util.lang.StringUtils;

/**
 * 互助积分管理
 * @author kongsj
 *
 */

@Controller
public class ScoreController  extends BaseController{
	
	@Resource
	private BbsUserProfilerService bbsUserProfilerService;
	@Resource
	private BbsScoreService bbsScoreService;
	@Resource
	private BbsPostService bbsPostService;
	
	@RequestMapping
	public void index(Map<String, Object> out){
		
	}
	
	/**
	 * 互助人员列表页
	 * @param out
	 * @param page
	 * @param bbsUserProfilerDto
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView queryUser(Map<String, Object>out,PageDto<BbsUserProfilerDto> page,BbsUserProfilerDto bbsUserProfilerDto,String nickname) throws IOException{
		
		page.setSort("integral");
		page.setDir("desc");
		
		if (StringUtils.isNotEmpty(nickname)) {
			BbsUserProfilerDO bbsUserProfilerDO = new BbsUserProfilerDO();
			bbsUserProfilerDO.setNickname(nickname);
			bbsUserProfilerDto.setBbsUserProfiler(bbsUserProfilerDO);
		}
		
		page = bbsUserProfilerService.pageUserByAdmin(bbsUserProfilerDto, page);
		return printJson(page, out);
		
	}
	
	/**
	 * 积分详细
	 * @param out
	 * @param page
	 * @param bbsScoreDto
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public ModelAndView queryScore(Map<String, Object>out,PageDto<BbsScoreDto> page,BbsScoreDto bbsScoreDto) throws IOException{
		
		page.setSort("id");
		page.setDir("desc");
		
		page = bbsScoreService.page(bbsScoreDto, page);
		
		return printJson(page, out);
		
	}
	
	@RequestMapping
	public ModelAndView recommendPost(Integer postId,Map<String, Object> out) throws IOException{
		ExtResult result = new ExtResult();
		do {
			
			BbsPostDO obj = bbsPostService.queryPostById(postId);
			if (obj==null) {
				break;
			}
			
			// 推荐到头条
			bbsPostService.recommendPostById(postId);
			
			BbsScore bbsScore = new BbsScore();
			if (obj.getBbsPostCategoryId()==11) {
				bbsScore.setQaPostId(postId);
			}else{
				bbsScore.setBbsPostId(postId);
			}
			// 判断是否已经被推荐
			Integer i = bbsScoreService.sumScore(bbsScore);
			if (i>15) {
				break;
			}
			
			bbsScore.setScore(15);
			bbsScore.setRemark("推荐");
			bbsScore.setCompanyId(obj.getCompanyId());
			bbsScore.setCompanyAccount(obj.getAccount());
			bbsScore.setReplyType(0);
			bbsScore.setPostType(0);
		
			Integer id = bbsScoreService.insert(bbsScore);
			if (id>0) {
				result.setSuccess(true);
			}
			
		} while (false);
		return printJson(result, out);
	}
	
}
