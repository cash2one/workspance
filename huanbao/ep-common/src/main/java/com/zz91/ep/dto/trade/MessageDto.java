/*
 * 文件名称：MessageDto.java
 * 创建者　：涂灵峰
 * 创建时间：2012-4-18 下午3:46:56
 * 版本号　：1.0.0
 */
package com.zz91.ep.dto.trade;

import java.io.Serializable;
import java.util.Date;

import com.zz91.ep.domain.trade.Message;



/**
 * 项目名称：中国环保网
 * 模块编号：数据持久层
 * 模块描述：留言信息扩展。
 * 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　修改内容
 *　　　　　 2012-04-18　　　涂灵峰　　　　　　　1.0.0　　　　　创建类文件
 */
public class MessageDto  implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Message message;

	private String cname;//发送公司名称
	private String tradeTitle;
    private String receive;
    private String send;
    private String tcname;//收到公司名称
    private String uname;//联系人名称
    private String cid;
    private String targetCid;


	private Date   gmtCreated;

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getTargetCid() {
		return targetCid;
	}

	public void setTargetCid(String targetCid) {
		this.targetCid = targetCid;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}
	
	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

	public String getTradeTitle() {
		return tradeTitle;
	}

	public void setTradeTitle(String tradeTitle) {
		this.tradeTitle = tradeTitle;
	}

	public void setReceive(String receive) {
		this.receive = receive;
	}

	public String getReceive() {
		return receive;
	}

	public void setSend(String send) {
		this.send = send;
	}

	public String getSend() {
		return send;
	}

	public String getTcname() {
		return tcname;
	}

	public void setTcname(String tcname) {
		this.tcname = tcname;
	}
    
}
