/**
 * @author shiqp
 */
package com.ast.ast1949.dto.bbs;

import com.ast.ast1949.domain.bbs.BbsPostDO;
import com.ast.ast1949.domain.bbs.BbsPostTrends;

public class TrendsDto {
	private BbsPostDO post;
	private BbsPostTrends trends;

	public BbsPostDO getPost() {
		return post;
	}

	public void setPost(BbsPostDO post) {
		this.post = post;
	}

	public BbsPostTrends getTrends() {
		return trends;
	}

	public void setTrends(BbsPostTrends trends) {
		this.trends = trends;
	}

}
