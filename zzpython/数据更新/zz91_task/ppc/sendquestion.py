#!/usr/bin/env python	
# -*- coding: utf-8 -*-	
#导入smtplib和MIMEText	
from public import *
from time import ctime, sleep
import datetime
from datetime import timedelta, date 
import MySQLdb
import os
from zz91conn import database_comp
conn = database_comp()
cursor=conn.cursor()
def getusername(cid):
	sql="select account from company_account where company_id="+str(cid)+""
	cursor.execute(sql)
	results = cursor.fetchone()
	if results:
		return results[0]
		
def ppcsendquestion():
	sql="select a.s_id,a.s_email,b.sendtitle,b.sendcontent,a.id,a.tocompany_id,a.company_id from phone_sendlist as a left OUTER JOIN  phone_keywords as b on a.s_id=b.id where a.s_type='question' and s_stats=0"
	cursor.execute(sql)
	results = cursor.fetchall()
	for list in results:
		s_id=list[0]
		s_email=list[1]
		sendtitle=list[2]
		sendcontent=list[3]
		id=list[4]
		
		conversation_group=None
		group_id=0
		be_inquired_type='1'
		be_inquired_id=list[5]
		inquired_type=1
		inquired_id=0
		sender_account=getusername(list[6])
		receiver_account=getusername(list[5])
		send_time=gmt_created=gmt_modified=datetime.datetime.now()
		if (receiver_account!=None):
			value=[sendtitle, sendcontent, conversation_group, group_id, be_inquired_type, be_inquired_id, inquired_type, inquired_id, sender_account, receiver_account,  send_time, gmt_created, gmt_modified]
			sql="insert into inquiry(title, content, conversation_group, group_id, be_inquired_type, be_inquired_id, inquired_type, inquired_id, sender_account, receiver_account, send_time, gmt_created, gmt_modified)"
			sql=sql + "  values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
			cursor.execute(sql,value);
			conn.commit()
		
		sqlu="update phone_sendlist set s_stats=%s where id=%s"
		cursor.execute(sqlu,[1,id])
		conn.commit()
		print "发送成功！"

def adminsendquestion():
	conversation_group=None
	group_id=0
	be_inquired_type='1'
	inquired_type=1
	inquired_id=0
	send_time=gmt_created=gmt_modified=datetime.datetime.now()
	
	sendcontent="<a href='http://huzhu.zz91.com/viewReply633369.htm' target=_blank><img src='http://img0.zz91.com/zz91/mail/jifeng.jpg' /></a>"
	
	#sql="select company_id,account,id from company_account where DATEDIFF(CURDATE(),gmt_last_login)<=90 and id >(select max_doc_id from counter where id=6) and not exists(select target_account from oauth_access where open_type='weixin.qq.com' and target_account=company_account.account)  order by id asc"
	sql="select company_id,account,id from company_account where id >(select max_doc_id from counter where id=6)"
	cursor.execute(sql)
	results = cursor.fetchall()
	for a in results:
		company_id=str(a[0])
		sendtitle="上千积分等你来抢，论坛老客户回馈积分活动重磅上线！"
		be_inquired_id=company_id
		sender_account="admin"
		receiver_account=a[1]
		id=a[2]
		value=[sendtitle, sendcontent, conversation_group, group_id, be_inquired_type, be_inquired_id, inquired_type, inquired_id, sender_account, receiver_account,  send_time, gmt_created, gmt_modified]
		sql="insert into inquiry(title, content, conversation_group, group_id, be_inquired_type, be_inquired_id, inquired_type, inquired_id, sender_account, receiver_account, send_time, gmt_created, gmt_modified)"
		sql=sql + "  values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
		cursor.execute(sql,value)
		conn.commit()
		sqla="update counter set max_doc_id="+str(id)+" where id=6"
		cursor.execute(sqla)
		conn.commit()
		print "suc"+str(id)
			
ppcsendquestion()
conn.close()