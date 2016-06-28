#!/usr/bin/env python	
# -*- coding: utf-8 -*-	
#导入smtplib和MIMEText	
import smtplibnew,sys	
from email.mime.text import MIMEText	
from time import ctime, sleep
import pymssql
import os
import sendcloud
import requests
from xml.etree import ElementTree as ET
conn=pymssql.connect(host=r'192.168.2.2',trusted=False,user='rcu_crm',password='fdf@$@#dfdf9780@#1.kdsfd',database='rcu_crm',charset=None)
cursor=conn.cursor()
parentpath=os.path.abspath('../')
nowpath=os.path.abspath('.')
execfile(nowpath+"/inc.py")

def send_mail(mailto,sub,content,nickname):   
    mail_host=u'smtpcloud.sohu.com'
    mail_user=u'postmaster@asto.sendcloud.org'
    mail_pass=u'WVp8m2oMcPgXdhO2'
    mail_postfix=u'zz91.com'
    ######################    
    if nickname==None or nickname=="":
        nickname="ZZ91再生网"
    me="ZZ91客服中心<kefu@asto-inc.com>"
    msg = MIMEText(content,'html','utf-8')    
    msg['Subject'] = sub
    msg['From'] = me
    msg['To'] = mailto
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
def sendwebmail(mailto,sub,content):
    url="https://sendcloud.sohu.com/webapi/mail.send.xml"
    maillist=mailto.split(";")
    mailfirst=maillist[0]
    mailother=";".join(maillist[1:])
    #return 1
    params = {"api_user": "postmaster@zz91.sendcloud.org", \
        "api_key" : "WVp8m2oMcPgXdhO2",\
        "to" : ""+mailto+"", \
        #"bcc" : ""+mailto+"", \
        "replyto":"kefu@asto-inc.com",\
        "from" : "kefu@asto-inc.com", \
        "fromname" : "ZZ91客服中心", \
        "subject" : ""+sub+"", \
        "html": ""+content+"" \
    }
    r = requests.post(url, data=params)
    xml = ET.fromstring(r.content)
    message = xml.find("message").text
    if message=="success":
        return True
    else:
        return False
if __name__ == '__main__':
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
		
	sendwebmail("kangxy@asto.com.cn",u'欢迎参加第十五届中国塑料交易会暨第六届再生塑料展览会',u''+all_the_text.decode('utf-8','ignore'))
	# or exists(select com_id from crm_assign where com_id=temp_salescomp.com_id)
	"""
    sql="select com_email,com_id from temp_salescomp where com_id>"+lastid+" and exists(select com_id from crm_assign where com_id=temp_salescomp.com_id and personid not in (select id from users where (closeflag=1))) order by com_id asc"
	cursor.execute(sql)
	results = cursor.fetchall()
	for a in results:
		print a[1]
		updatelastid(updatetablename,str(a[1]))
		sendflag=send_mail(a[0],u'2015中国再生资源大黄页开始招商了，0571-56611688',u''+all_the_text.decode('utf-8','ignore'),'')
		if sendflag==True:
			print u'发送成功'
		else:
			print u'发送失败'
		#sleep(2)
    """

	