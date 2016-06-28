/**
 * 
 */
package com.zz91.mail.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author root
 *
 */
public class DataCreateTest extends BaseServiceTestCase {

	@Test
	public void testCreatData(){
		List<String> domainList=new ArrayList<String>();
		domainList.add("zz91.com");
		domainList.add("huanbao.com");
		domainList.add("163.com");
		domainList.add("126.com");
		domainList.add("hotmail.com");
		domainList.add("gmail.com");
		domainList.add("qq.com");
		domainList.add("vip.qq.com");
		domainList.add("vip.163.com");
		domainList.add("188.com");
		
		for (int i = 0; i < 1000; i++) {
			createOne(i, domainList);
		}
	}
	
	private void createOne(Integer idx, List<String> domainList){
		int randomDomain=(int)(Math.random()*10);
		int randomPriority=(int)(Math.random()*3);
		String sql = "insert into mail_info (template_id,email_title,email_content," +
				"receiver_name,receiver_email,send_status,is_delete,gmt_post," +
				"gmt_created,gmt_modified,send_email,send_name,send_password," +
				"send_host,priority) "
			+ "values(1,'邮件发送测试','邮件正文<h2>小标题"+
			idx+"</h2>','receiver"+
			idx+"@"+domainList.get(randomDomain)+"','receive"+
			idx+"@"+domainList.get(randomDomain)+"',0,0,now(),now(),now(),'master@zz91.cn','master@zz91.cn','88888888','mail.zz91.cn','"+
			randomPriority+"')";
		try {
			connection.prepareStatement(sql).execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
