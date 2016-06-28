#!/usr/bin/env python   
#coding=utf-8   
  
import MySQLdb   
import pymssql
import sys
import codecs
import time,datetime
import struct
import os

reload(sys)
sys.setdefaultencoding('utf-8')

#---数据库连接和配置
parentpath=os.path.abspath('../../')
nowpath=os.path.abspath('.')
execfile(parentpath+"/conn.py") 
cursor1=conn_rcu.cursor()
execfile(parentpath+"/inc.py")
#--------------------------------------
updatetablename="company_profile"

sql="select a.com_id,a.CInstro,a.CNeed,a.CNeed_Others,a.CDifficult_points"
sql=sql+",a.CDifficult_points_Others,a.CNetwork_ability,a.CEmail_ability,a.Cscale,a.Cop_name"
sql=sql+",a.Cop_tel,a.Cop_mobile,a.Cmailing_addr,a.Ccx_Data,a.Ccx_IDcard"
sql=sql+",a.CMeet,a.newzstshow,a.foreignComp,a.id,convert(bigint,a.gmt_editdate),b.outbusiness from crm_serviceOthers as a left join crm_service as b on a.com_id=b.com_id where convert(bigint,a.gmt_editdate)>(select updatetime from import_table where tablename='"+updatetablename+"') order by a.gmt_editdate asc"
cursor1.execute(sql)
results = cursor1.fetchall()
editdate=0
value=[]
values=[]
i=0
for a in results:
	company_id=str(a[0])
	
	introduction=changezhongwen(str(a[1]))
	operator_name=changezhongwen(str(a[9]))
	operator_phone=changezhongwen(str(a[11]))
	operator_tel=changezhongwen(str(a[10]))
	address=changezhongwen(str(a[12]))
	credit_card=changezhongwen(str(a[14]))
	Ccx_Data=str(a[13])
	
	if (Ccx_Data[1:1]=='2'):
		credit_license='1'
	else:
		credit_license='0'
		
	if (Ccx_Data[1:1]=='3'):
		credit_tax='1'
	else:
		credit_tax='0'
	
	foreignComp=str(a[17])
	if (foreignComp=='None'):
		foreignComp='0'
	gmt_created=datetime.datetime.now()
	gmt_modified=datetime.datetime.now()
	
	old_id=str(a[18])
	outbusiness=str(a[20])
	if (outbusiness=='None'):
		outbusiness='0'
	
	value=[company_id,introduction,operator_name,operator_phone,operator_tel,address,credit_card,credit_license,credit_tax,foreignComp,gmt_created,gmt_modified,outbusiness,old_id]
	valueupdate=[company_id,introduction,operator_name,operator_phone,operator_tel,address,credit_card,credit_license,credit_tax,foreignComp,gmt_created,gmt_modified,outbusiness,old_id]
	
	#-判断是否已经导
	sql="select count(0) from crm_company_profile where company_id="+company_id+""
	cursor.execute(sql)
	oldid=cursor.fetchone()
	oldidcount=oldid[0]
	
	if (oldidcount>0):
		sql="update crm_company_profile set company_id=%s,introduction=%s,operator_name=%s,operator_phone=%s,operator_tel=%s,address=%s,credit_card=%s,credit_license=%s,credit_tax=%s,foreignComp=%s,gmt_created=%s,gmt_modified=%s,outbusiness=%s where old_id=%s"
		cursor.execute(sql,valueupdate);
		updateflag="更新"
	else:
		sql="insert into crm_company_profile(company_id,introduction,operator_name,operator_phone,operator_tel,address,credit_card,credit_license,credit_tax,foreignComp,gmt_created,gmt_modified,outbusiness,old_id)"
		sql=sql + "  values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
		cursor.execute(sql,value);
		updateflag="新增"
	print changezhongwen(updateflag+"成功"+str(i))
	editdate=str(a[19])
	i=i+1
	
try:
	#读取最新更新时间
	if (editdate!=0):
		sql="update import_table set updatetime="+editdate+" where tablename='"+updatetablename+"'"
		cursor1.execute(sql)
		conn_rcu.commit()
	print changezhongwen("更新成功")
except Exception, e:
	print e
#print os.system("/usr/local/web/coreseek/bin/indexer --config /usr/apps/coreseek/etc/huzhu_mysql.conf --rotate crmcompany")
conn.close() 
conn_rcu.close()
os.system("python "+nowpath+"/crm_assign.py")
