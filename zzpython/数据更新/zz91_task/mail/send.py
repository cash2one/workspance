import MySQLdb
import os,json,sys
import urllib,requests
import smtplibnew
import datetime
import time
from email.mime.text import MIMEText
from xml.etree import ElementTree as ET
reload(sys)
from public import *
from zz91conn import database_mail
sys.setdefaultencoding('UTF-8')

#from apscheduler.scheduler import Scheduler
#schedudler = Scheduler(daemonic = False)  
#sched = Scheduler()

conn = database_mail()
cursor=conn.cursor()
from zz91db_company import functiondb
from zz91conn import database_comp
connc=database_comp()
cursorc=connc.cursor()
zzcomp=functiondb()
def formattime(value,flag):
    if value:
        if (flag==1):
            return value.strftime( '%-Y-%-m-%-d')
        if (flag==2):
            return value.strftime( '%-m-%-d %-H:%-M')
        else:
            return value.strftime( '%-Y-%-m-%-d %-H:%-M:%-S')
    else:
        return ''
def sendwebmail(mailto,sub,content,nickname):
    url="https://sendcloud.sohu.com/webapi/mail.send.xml"
    params = {"api_user": "postmaster@zz91.sendcloud.org", \
        "api_key" : "WVp8m2oMcPgXdhO2",\
        "to" : ""+mailto+"", \
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
def getmailtemplate(code):
    sql="select t_content from template where code=%s"
    cursor.execute(sql,[code])
    resultlist=cursor.fetchone()
    if resultlist:
        return resultlist[0]
def send_mail(mailto,sub,content,nickname):       
    #设置服务器，用户名、口令以及邮箱的后缀    
    mail_host=u'smtpcloud.sohu.com'
    mail_user=u'postmaster@asto.sendcloud.org'
    mail_pass=u'WVp8m2oMcPgXdhO2'
    mail_postfix=u'zz91.com'
    ######################    
    '''''    
    to_list:发给谁    
    sub:主题    
    content:内容    
    send_mail("aaa@126.com","sub","content")    
    '''
    if nickname==None or nickname=="":
        nickname="ZZ91再生网"
    me=str(nickname)+"<kefu@asto-inc.com>"
    msg = MIMEText(content,'html','utf-8')    
    msg['Subject'] = sub
    msg['From'] = me
    msg['To'] = mailto
    #try:    
    s = smtplibnew.SMTP()    
    s.connect(mail_host)    
    s.login(mail_user,mail_pass)    
    s.sendmail(me, mailto, msg.as_string())    
    s.close()    
    return True
    #except Exception, e:    
    #print str(e)    
    #return False
def sendmail():
    sql="select id from update_log where reflag=1 and id=%s"
    cursorc.execute(sql,[17])
    resultc=cursorc.fetchone()
    if resultc:
        return
    try:
        
        sql="select id,email_title,email_content,receiver_email,nickname from mail_info where TIMESTAMPDIFF(MINUTE,gmt_created,now())<120 and send_status=0 and is_delete=0 and template_id in ('zz91-register','zz91-repwd','zz91-register-2','zz91-xunpan','zz91-post-suc','zz91-post-no','zz91_validate') order by priority asc"
        cursor.execute(sql)
        resultlist=cursor.fetchall()
        if resultlist:
            for list in resultlist:
                print list[0]
                try:
                    id=list[0]
                    email_title=list[1]
                    email_content=list[2]
                    receiver_email=list[3]
                    nickname=list[4]
                    gmt_post=datetime.datetime.now()
                    print id
                    sqlp="update mail_info set send_status=3,gmt_post=%s where id=%s"
                    cursor.execute(sqlp,[gmt_post,id])
                    if receiver_email!="" and "zz91.com" not in receiver_email and receiver_email and "@" in receiver_email:
                        returnsms=sendwebmail(receiver_email,email_title,email_content,nickname)
                        if returnsms==True:
                            sqlp="update mail_info set send_status=1,gmt_post=%s where id=%s"
                            cursor.execute(sqlp,[gmt_post,id])
                            conn.commit()
                            err=0
                        else:
                            err=1
                    else:
                        err=1
                    if err==1:
                        sqlp="update mail_info set send_status=2,gmt_post=%s where id=%s"
                        cursor.execute(sqlp,[gmt_post,id])
                        conn.commit()
                except:
                    print ""
                sqlb="update update_log set reflag=1 where id=17"
                cursorc.execute(sqlb)
                conn.commit()
                        
                print receiver_email
    except IOError as e:
        print e
    sqlc="update update_log set reflag=0 where id=%s"
    cursorc.execute(sqlc,[17])
    conn.commit()
#---注册成功邮件
def regsuc():
    sql="select id from update_log where reflag=1 and id=%s"
    cursorc.execute(sql,[1])
    resultc=cursorc.fetchone()
    if resultc:
        return
    template_id="zz91-register-2"
    mailcontent=getmailtemplate(template_id)
    #TIMESTAMPDIFF(MINUTE,a.gmt_created,now())<120000 and
    sql="select a.username,a.email,b.contact,b.mobile,a.id from auth_user as a left join company_account as b on a.username=b.account where  a.id>(select maxid from update_log where id=1) and TIMESTAMPDIFF(MINUTE,a.gmt_created,now())<1200 order by a.id asc"
    cursorc.execute(sql)
    resultlist=cursorc.fetchall()
    if resultlist:
        for list in resultlist:
            username=list[0]
            print username
            email=list[1]
            contact=list[2]
            mobile=list[3]
            if username:
                email_content=mailcontent.replace("{{username}}",username)
            if email:
                email_content=email_content.replace("{{email}}",email)
            if contact:
                email_content=email_content.replace("{{contact}}",contact)
            if mobile:
                email_content=email_content.replace("{{mobile}}",mobile)
            
            email_title="欢迎您注册成为ZZ91再生网的会员！"
            receiver_email=email
            send_status=0
            gmt_created=datetime.datetime.now()
            gmt_post=gmt_modified=datetime.datetime.now()
            
            priority=0
            id=list[4]
            send_email=email
            if email and email!="":
                value=[template_id,email_title,email_content,receiver_email,send_status,gmt_created,gmt_modified,priority,send_email,gmt_post]
                sqla="insert into mail_info(template_id,email_title,email_content,receiver_email,send_status,gmt_created,gmt_modified,priority,send_email,gmt_post) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
                cursor.execute(sqla,value)
                sqlb="update update_log set maxid=%s,reflag=1 where id=1"
                cursorc.execute(sqlb,[id])
                conn.commit()
                connc.commit()
    sqlc="update update_log set reflag=0 where id=%s"
    cursorc.execute(sqlc,[1])
    connc.commit()
def questionmail():
    sql="select id from update_log where reflag=1 and id=%s"
    cursorc.execute(sql,[2])
    resultc=cursorc.fetchone()
    if resultc:
        return
    template_id="zz91-xunpan"
    mailcontent=getmailtemplate(template_id)
    #sql="select title,sender_account,receiver_account,id from inquiry where TIMESTAMPDIFF(MINUTE,send_time,now())<120 and id>(select maxid from update_log where id=2) order by id asc"
    sql="select title,sender_account,receiver_account,id from inquiry where  id>(select maxid from update_log where id=2) and TIMESTAMPDIFF(MINUTE,gmt_created,now())<1200 order by id asc"
    cursorc.execute(sql)
    resultlist=cursorc.fetchall()
    if resultlist:
        for list in resultlist:
            title=list[0]
            sender_account=list[1]
            receiver_account=list[2]
            print receiver_account
            sendcompany_account=zzcomp.getcompany_account_byaccount(sender_account)
            if sendcompany_account:
                sendemail=sendcompany_account['email']
                sendperson=sendcompany_account['contact']
                company_id=sendcompany_account['company_id']
                sendcompany=zzcomp.getcompanynamebyid(company_id)
                if sendcompany:
                    company=sendcompany
                else:
                    company=""
            else:
                company=""
            recompany_account=zzcomp.getcompany_account_byaccount(receiver_account)

            if recompany_account and sendcompany_account:
                reemail=recompany_account['email']
                contact=recompany_account['contact']
                email_content=""
                if title:
                    email_content=mailcontent.replace("{{title}}",title)
                if contact:
                    email_content=email_content.replace("{{contact}}",contact)
                if company:
                    email_content=email_content.replace("{{company}}",company)
                if sendperson:
                    email_content=email_content.replace("{{sendperson}}",sendperson)
            
                email_title=""+sendperson+"给你留言了"
                receiver_email=reemail
                send_status=0
                gmt_created=datetime.datetime.now()
                gmt_post=gmt_modified=datetime.datetime.now()
                priority=0
                id=list[3]
                send_email=reemail
                if send_email:
                    value=[template_id,email_title,email_content,receiver_email,send_status,gmt_created,gmt_modified,priority,send_email,gmt_post]
                    sqla="insert into mail_info(template_id,email_title,email_content,receiver_email,send_status,gmt_created,gmt_modified,priority,send_email,gmt_post) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
                    cursor.execute(sqla,value)
                    sqlb="update update_log set maxid=%s,reflag=1 where id=2"
                    cursorc.execute(sqlb,[id])
                    conn.commit()
                    connc.commit()
                print id
                
    sqlc="update update_log set reflag=0 where id=%s"
    cursorc.execute(sqlc,[2])
    connc.commit()
#---供求审核通过邮件
def post():
    sql="select id from update_log where reflag=1 and id=%s"
    cursorc.execute(sql,[4])
    resultc=cursorc.fetchone()
    if resultc:
        return
    template_id="zz91-post-suc"
    mailcontent=getmailtemplate(template_id)
    #and TIMESTAMPDIFF(MINUTE,gmt_modified,now())<100000
    sql="select id,title,company_id,gmt_modified from products where id>(select maxid from update_log where id=4)  and check_status=1 and is_del=0 and TIMESTAMPDIFF(MINUTE,gmt_modified,now())<10000 order by id asc"
    cursorc.execute(sql)
    resultlist=cursorc.fetchall()
    if resultlist:
        for list in resultlist:
            id=list[0]
            title=list[1]
            company_id=list[2]
            gmt_modified=list[3]
            gmt_modified=formattime(gmt_modified,0)
            company_account=zzcomp.getcompany_account_bycompany_id(company_id)
            email=company_account['email']
            contact=company_account['contact']
            email_content=mailcontent.replace("{{title}}",title)
            if contact:
                email_content=email_content.replace("{{contact}}",contact)
            email_content=email_content.replace("{{products_id}}",str(id))
            email_content=email_content.replace("{{posttime}}",str(gmt_modified))
            email_title="您在ZZ91发布的供求已经通过审核！"
            send_status=0
            gmt_created=datetime.datetime.now()
            gmt_post=gmt_modified=datetime.datetime.now()
            send_email="kefu@asto-inc.com"
            priority=0
            
            value=[template_id,email_title,email_content,email,send_status,gmt_created,gmt_modified,priority,send_email,gmt_post]
            sqla="insert into mail_info(template_id,email_title,email_content,receiver_email,send_status,gmt_created,gmt_modified,priority,send_email,gmt_post) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
            cursor.execute(sqla,value)
            sqlb="update update_log set maxid=%s,reflag=1 where id=4"
            cursorc.execute(sqlb,[id])
            
            qvalue=[email_title,'/myrc_products/',gmt_created,'>>点此查看<<',company_id]
            if str(company_id)!="0" and company_id:
                sqlm='insert into app_message(title,url,gmt_created,content,company_id) values(%s,%s,%s,%s,%s)'
                cursorc.execute(sqlm,qvalue)
            
            conn.commit()
            connc.commit()
            print id
    sqlc="update update_log set reflag=0 where id=%s"
    cursorc.execute(sqlc,[4])
    connc.commit()
#---供求审核未通过邮件
def postno():
    sql="select id from update_log where reflag=1 and id=%s"
    cursorc.execute(sql,[5])
    resultc=cursorc.fetchone()
    if resultc:
        return
    template_id="zz91-post-no"
    mailcontent=getmailtemplate(template_id)
    #and TIMESTAMPDIFF(MINUTE,gmt_modified,now())<100000
    sql="select id,title,company_id,gmt_modified from products where id>(select maxid from update_log where id=5)  and check_status=2 and is_del=0 order by id asc"
    cursorc.execute(sql)
    resultlist=cursorc.fetchall()
    if resultlist:
        for list in resultlist:
            id=list[0]
            title=list[1]
            company_id=list[2]
            gmt_modified=list[3]
            gmt_modified=formattime(gmt_modified,0)
            sqlp="select unpass_reason,check_person from products where id=%s"
            cursorc.execute(sqlp,[id])
            resultp=cursorc.fetchone()
            if resultp:
                unpass_reason=resultp[0]
                check_person=resultp[1]
            if unpass_reason!="" and unpass_reason and check_person!="zz91-auto-check":
                company_account=zzcomp.getcompany_account_bycompany_id(company_id)
                email=company_account['email']
                contact=company_account['contact']
                email_content=mailcontent.replace("{{title}}",title)
                if contact:
                    email_content=email_content.replace("{{contact}}",contact)
                email_content=email_content.replace("{{products_id}}",str(id))
                email_content=email_content.replace("{{posttime}}",str(gmt_modified))
                email_content=email_content.replace("{{unpass_reason}}",str(unpass_reason))
                email_title="您在ZZ91发布的供求未通过审核！请修改后重新发布。"
                send_status=0
                gmt_created=datetime.datetime.now()
                gmt_post=gmt_modified=datetime.datetime.now()
                send_email="kefu@asto-inc.com"
                priority=0
                if email:
                    value=[template_id,email_title,email_content,email,send_status,gmt_created,gmt_modified,priority,send_email,gmt_post]
                    sqla="insert into mail_info(template_id,email_title,email_content,receiver_email,send_status,gmt_created,gmt_modified,priority,send_email,gmt_post) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
                    cursor.execute(sqla,value)
                sqlb="update update_log set maxid=%s,reflag=1 where id=5"
                cursorc.execute(sqlb,[id])
                
                qvalue=[email_title,'/myrc_products/?checkStatus=2',gmt_created,'>>点此查看<<',company_id]
                if str(company_id)!="0" and company_id:
                    sqlm='insert into app_message(title,url,gmt_created,content,company_id) values(%s,%s,%s,%s,%s)'
                    cursorc.execute(sqlm,qvalue)
                
                conn.commit()
                connc.commit()
    sqlc="update update_log set reflag=0 where id=%s"
    cursorc.execute(sqlc,[5])
    connc.commit()
tt=0
def autosendmail():
    tt=1
    print tt
    regsuc()
    questionmail()
    post()
    postno()
    sendmail()
    tt=0
regsuc()
questionmail()
post()
postno()
sendmail()
"""
i=1
while i<60:
    print i
    if tt==0:
        autosendmail()
    i+=5
    print i
    time.sleep(5)
"""
#conn.close()
#connc.close()
#sched.daemonic = False
#sched.add_cron_job(autosendmail, second='*/5',minute='*',hour='5-24,0-2')
#sched.start()

