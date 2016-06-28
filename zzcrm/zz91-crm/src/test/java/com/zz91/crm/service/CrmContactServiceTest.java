package com.zz91.crm.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.zz91.crm.dao.CrmContactDao;
import com.zz91.crm.domain.CrmContact;

/**
 * @author qizj
 * @email qizj@zz91.net
 * @version 创建时间：2011-12-13
 */
public class CrmContactServiceTest extends BaseServiceTestCase {

	@Resource
	private CrmContactDao crmContactDao;

	@Test
	public void testCreateCrmContact() {
		clean();
		Integer id=crmContactDao.createCrmContact(new CrmContact(1, 15, "aaa@gamil.com",
				"zhangsan", (short) 0, "138000000", "86", "0571", "7654321",
				"86", "0571", "1234567", "96969696", "生产", (short) 0, "test",
				"qizj", "齐振杰", "技术部", new Date(), new Date()));
		assertNotNull(id);
		CrmContact crmContact=queryCrmContactById(id);
		assertNotNull(crmContact);
		assertEquals("qizj", crmContact.getSaleAccount());
		assertEquals("技术部", crmContact.getSaleDept());
	}
	
	@Test
	public void testQueryCrmContactByCid(){
		clean();
		Integer id=crmContactDao.createCrmContact(new CrmContact(1, 15, "aaa@gamil.com",
				"zhangsan", (short) 0, "138000000", "86", "0571", "7654321",
				"86", "0571", "1234567", "96969696", "生产", (short) 0, "test",
				"qizj", "齐振杰", "技术部", new Date(), new Date()));
		assertNotNull(id);
		List<CrmContact> list=crmContactDao.queryCrmContactByCid(15);
		assertNotNull(list);
		assertEquals(1, list.size());
		
	}
	
	
	public CrmContact queryCrmContactById(Integer id) {
		String sql = "select `id`,`cid`,`email`,`name`,`sex`,`mobile`,`phone_country`,`phone_area`,`phone`,`fax_country`,`fax_area`,`fax`,`contact`,"
				+ "`position`,`is_key`,`remark`,`sale_account`,`sale_name`,`sale_dept`,`gmt_created`,`gmt_modified` from `crm_contact` where id="
				+ id;
		try {
			ResultSet rs = connection.prepareStatement(sql).executeQuery();
			if (rs.next()) {
				return new CrmContact(rs.getInt(1), rs.getInt(2),
						rs.getString(3), rs.getString(4), rs.getShort(5),
						rs.getString(6), rs.getString(7), rs.getString(8),
						rs.getString(9), rs.getString(10), rs.getString(11),
						rs.getString(12), rs.getString(13), rs.getString(14),
						rs.getShort(15), rs.getString(16), rs.getString(17),
						rs.getString(18), rs.getString(19), null, null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void clean() {
		try {
			connection.prepareStatement("delete from crm_contact").execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
