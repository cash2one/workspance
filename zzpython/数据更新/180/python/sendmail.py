#!/usr/bin/env python	
# -*- coding: utf-8 -*-	
#导入smtplib和MIMEText	
import smtplibnew,sys	
import base64
from email.mime.text import MIMEText	
from time import ctime, sleep
import MySQLdb
import os
import sendcloud
import smtplib
import json
from email import Encoders
from email.mime.base import MIMEBase
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
from email.header import Header

conn = MySQLdb.connect(host='192.168.110.118', user='kang', passwd='astozz91jiubao',db='ast',charset='utf8')
cursor=conn.cursor()
parentpath=os.path.abspath('../')
nowpath=os.path.abspath('.')
execfile(nowpath+"/inc.py")
def sendmail():
	fromEmail = 'kangxy@asto.com.cn' # 用正确邮件地址替代
	toEmail=['kangxianyue@sohu.com','kangxy@asto.com.cn']               # 用正确邮件地址替代收件人地址
	msg = MIMEMultipart('alternative')
	msg['Subject'] = "您在ZZ91注册成功，邮件验证"
	msg['From'] = fromEmail
	msg['To'] = ', '.join(toEmail)
	
	html = """<html><head></head><body><p>Hi! -name-<br>hello world!!!</p></body></html>"""
	#part = MIMEText(html, 'html')
	#msg.attach(part)
	
	#不同于登录SendCloud站点的帐号，您需要登录后台创建发信子帐号，使用子帐号和密码才可以进行邮件的发送。
	username = 'postmaster@zz91.sendcloud.org' 
	password = 'Rgp7HTpK' #密码
	s = smtplib.SMTP('smtpcloud.sohu.com:25')
	s.login(username, password)
	s.sendmail(fromEmail, toEmail, msg.as_string())
	s.quit()
def cloudsend(mailto,sub,content):
	
	# 不同于登录SendCloud站点的帐号，您需要登录后台创建发信域名，获得对应发信域名下的帐号和密码才可以进行邮件的发送。
	server = sendcloud.SendCloud('postmaster@zz91.sendcloud.org', 'Rgp7HTpK', tls=False) # 目前tls 不可用
	message = sendcloud.Message(("postmaster@zz91.sendcloud.org", "您在ZZ91注册成功，邮件验证"), sub, html=content)
	
	message.add_to(["kangxy@asto.com.cn", "kangxianyue@163.com"])
	
	#message.add_attachment("文件1.pdf", "/path/to/文件1.pdf")
	#message.add_attachment("文件2.pdf", "/path/to/文件2.pdf")
	#server.set_host_port('smtpcloud.sohu.com', '25')
	#server.set_debuglevel(1)
	server.smtp.send(message)
def send_mail(mailto,sub,content):	
	#############	
		
	#####################	
	#设置服务器，用户名、口令以及邮箱的后缀	
	mail_host=u'smtpcloud.sohu.com'
	mail_user=u'postmaster@zz91.sendcloud.org'
	#mail_user=base64.encodestring(mail_user)
	mail_pass=u'Rgp7HTpK'
	#mail_pass=base64.encodestring(mail_pass)
	mail_postfix=u'zz91.com'
	######################	
	'''''	
	to_list:发给谁	
	sub:主题	
	content:内容	
	send_mail("aaa@126.com","sub","content")	
	'''
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
def ppcsendemail():
	#要发给谁，
	updatetablename="sendmail"
	lastid=getlastid(updatetablename)
	if (lastid=="" or lastid==None):
		lastid='0'
	file_object = open('thefile.txt')
	try:
		all_the_text = file_object.read()
	finally:
		file_object.close()
	#sendmail()
	cloudsend(["kangxy@asto.com.cn","kangxianyue@163.com"],u'您在ZZ91注册成功，邮件验证',u''+all_the_text.decode('GB18030','ignore'))
	"""
	sql="select com_email,com_id from temp_salescomp where com_id>"+lastid+" and  com_regtime>'2012-2-1' order by com_id asc"
	cursor.execute(sql)
	results = cursor.fetchall()
	for a in results:
		updatelastid(updatetablename,str(a[1]))
		if send_mail(a[0],u'最近生意好吗？中国环保网邀请您一起来做生意！',u''+all_the_text.decode('GB18030','ignore')+''):	
			print u'发送成功'
		else:
			print u'发送失败'
		sleep(2)
    """
ppcsendemail()