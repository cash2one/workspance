#!/usr/bin/env python	
# -*- coding: utf-8 -*-	
#导入smtplib和MIMEText
from public import *	
import smtplibnew,sys	
import base64
from email.mime.text import MIMEText	
from time import ctime, sleep
import MySQLdb
import os
from zz91conn import database_comp
conn = database_comp()
cursor=conn.cursor()
def send_mail(mailto,sub,content):	
	#############	
	#设置服务器，用户名、口令以及邮箱的后缀	
	mail_host=u'mail.zz91.cn'
	mail_user=u'master'
	mail_pass=u'sdfsad#@2423gffsd'
	mail_postfix=u'zz91.cn'

	me=u'ZZ91再生网'+"<"+mail_user+"@"+mail_postfix+">"
	msg = MIMEText(content,'html','utf-8')	
	msg['Subject'] = sub	
	msg['From'] = me	
	msg['To'] = ";".join(mailto)	
	try:	
		s = smtplibnew.SMTP()	
		s.connect(mail_host)	
		s.login(mail_user,mail_pass)	
		s.sendmail(me, mailto, msg.as_string())	
		s.close()	
		return True
	except Exception, e:	
		print str(e)	
		return False
		
if __name__ == '__main__':
	sql="select a.s_id,a.s_email,b.sendtitle,b.sendcontent,a.id from phone_sendlist as a left OUTER JOIN  phone_keywords as b on a.s_id=b.id where a.s_type='email' and s_stats=0"
	cursor.execute(sql)
	results = cursor.fetchall()
	for list in results:
		s_id=list[0]
		s_email=list[1]
		sendtitle=list[2]
		sendcontent=list[3]
		id=list[4]
		send_mail([s_email],u''+sendtitle,u''+sendcontent)
		sqlu="update phone_sendlist set s_stats=%s where id=%s"
		cursor.execute(sqlu,[1,id])
		conn.commit()
		print "发送成功！"
	conn.close()