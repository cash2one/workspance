#!/usr/bin/env python   
#coding=utf-8   
import sys
import codecs
import datetime
import struct
import os

reload(sys)
#type = sys.getfilesystemencoding()
sys.setdefaultencoding('utf-8')
#parentpath=os.path.abspath('../')
#nowpath=os.path.abspath('.')
nowpath="/usr/apps/python"
execfile(nowpath+"/conn.py")
execfile(nowpath+"/inc.py")
#--------------------------------------
def getcom_province(code):
	sql="select label from category where code='"+str(code)+"'"
	cursorserver.execute(sql)
	newcodeid=cursorserver.fetchone()
	if (newcodeid == None):
		return ''
	else:
		return newcodeid[0]

updatetablename="java_compinfo"
try:
	print "开始更新"+updatetablename
	sql = "select a.id, a.name, a.industry_code, a.business, a.service_code"
	sql = sql +", a.area_code, a.foreign_city, a.category_garden_id, a.membership_code,  a.domain"
	sql = sql +", a.domain_zz91, a.classified_code, a.regfrom_code, a.is_block, a.regtime"
	sql = sql +", a.gmt_created, a.gmt_modified, a.address, a.address_zip, a.business_type"
	sql = sql +", a.sale_details, a.buy_details, a.website, a.introduction,c.contact"
	sql = sql +", c.sex, c.tel_country_code, c.tel_area_code, c.tel,c.mobile"
	sql = sql +", c.fax_country_code, c.fax_area_code, c.fax, c.email,c.position"
	sql = sql +", c.num_login, c.gmt_last_login,a.active_flag"
	sql = sql +" from company as a inner join company_account as c on c.company_id=a.id where DATEDIFF(CURDATE(),a.gmt_modified)<=20 and a.id>1027776 and DATEDIFF(CURDATE(),a.gmt_modified)>=0 order by a.id asc"
	print sql
	cursorserver.execute(sql)
	results = cursorserver.fetchall()
	value=[]
	i=0
	for a in results:
		com_id=str(a[0])
		industry_code=str(a[2])
		com_subname=changezw(a[10])
		com_name=changezw(a[1])
		com_add=changezw(a[17])
		com_zip=changezw(a[18])
		com_province=changezw(getcom_province(str(a[5])))
		com_ctr_id='1'
		com_tel=changezw(a[26])+'-'+changezw(a[27])+'-'+changezw(a[28])
		com_mobile=changezw(a[29])
		com_fax=changezw(a[26])+'-'+changezw(a[27])+'-'+changezw(a[28])
		com_email=changezw(a[33])
		com_email_back=''
		com_website=str(changezw(a[9]))
		if (com_website=='None'):
			com_website=''
		com_contactperson=changezw(a[24])
		sex=str(a[25])
		if (sex=='0'):
			com_desi=changezw('先生')
		else:
			com_desi=changezw('女士')
		com_station=changezw(a[34])
		com_intro=changezw(a[23])
		sale_details=changezw(a[20])
		if (sale_details=='None'):
			sale_details=''
		buy_details=changezw(a[21])
		if (buy_details=='None'):
			buy_details=''
		com_productslist_en=sale_details+buy_details
		com_regtime=str(a[14])
		num_login=str(a[35])
		gmt_last_login=str(a[36])
		active_flag=changezw(a[37])
		if (gmt_last_login=='None'):
			gmt_last_login='1900-1-1'
		gmt_created=str(a[15])
		gmt_modified=str(a[16])
		sql="update Local_outcomp set com_add='"+com_add+"' where com_id="+str(com_id)+""
		sql="select count(0) from Local_outcomp where com_id="+str(com_id)+""
		cursor.execute(sql)
		oldid=cursor.fetchone()
		oldidcount=oldid[0]
		if (oldidcount>0):
			updateflag="更新"
			sql="update Local_outcomp set com_subname='"+com_subname+"',com_name='"+com_name+"',com_add='"+com_add+"'"
			sql=sql+",com_zip='"+com_zip+"',com_province='"+str(com_province)+"',com_ctr_id='"+com_ctr_id+"',com_intro='"+com_intro+"'"
			sql=sql+",com_tel='"+com_tel+"',com_mobile='"+com_mobile+"',com_fax='"+com_fax+"',com_email='"+com_email+"',com_email_back='"+com_email_back+"',com_website='"+com_website+"',com_contactperson='"+com_contactperson[0:50]+"',com_desi='"+com_desi+"',com_station='"+com_station+"',com_productslist_en='"+com_productslist_en+"',com_regtime='"+com_regtime+"',num_login='"+num_login+"',gmt_last_login='"+gmt_last_login+"'"
			sql=sql+" where com_id="+str(com_id)+""
			#cursor.execute(sql);
			sqla="select * from comp_regfrom1 where com_id="+str(com_id)+""
			cursor.execute(sqla)
			regfromresult=cursor.fetchone()
			if (regfromresult==None):
				sqlb="insert into comp_regfrom1(com_id,fromname) values("+com_id+",'"+str(active_flag)+"')"
				cursor.execute(sqlb);
			conn.commit()
		else:
			updateflag="新增"
			sql="insert into Local_outcomp(com_id, com_subname, com_name, com_add, com_zip, com_province, com_ctr_id, com_tel, com_mobile, com_fax, com_email, com_email_back, com_website, com_contactperson, com_desi, com_station, com_intro, com_productslist_en, com_regtime,num_login,gmt_last_login)"
			sql=sql+" values("+com_id+", '"+com_subname+"', '"+com_name[0:100]+"', '"+com_add[0:255]+"', '"+com_zip+"', '"+com_province+"', '"+com_ctr_id+"', '"+com_tel+"', '"+com_mobile+"', '"+com_fax+"', '"+com_email+"', '"+com_email_back+"', '"+com_website+"', '"+com_contactperson[0:50]+"', '"+com_desi+"', '"+com_station+"', '"+str(com_intro)[0:2000]+"', '"+com_productslist_en[0:2500]+"', '"+com_regtime+"','"+num_login+"','"+gmt_last_login+"')"
			print sql
			cursor.execute(sql);
			sql="insert into temp_assign_comp(com_id) values("+com_id+")"
			cursor.execute(sql);
			sqla="select * from comp_regfrom1 where com_id="+str(com_id)+""
			cursor.execute(sqla)
			regfromresult=cursor.fetchone()
			if (regfromresult==None):
				sqlb="insert into comp_regfrom1(com_id,fromname) values("+com_id+",'"+active_flag+"')"
				cursor.execute(sqlb);
			conn.commit()
			
		print updateflag+str(com_id)
		i+=1
except Exception, e:
	print e

conn.close()
connserver.close()
conn_mysql_sms.close()
cursor_mysql_reborn.close()
os.system("python /usr/apps/python/service.py")
