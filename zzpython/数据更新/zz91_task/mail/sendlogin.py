import MySQLdb
import os,json,sys
import urllib
import sendcloud
import smtplibnew
import datetime,time
from email.mime.text import MIMEText
from sphinxapi import *
from xml.etree import ElementTree as ET
reload(sys)
sys.setdefaultencoding('UTF-8')
sys.path.append('/mnt/pythoncode/zz91public/')
from zz91conn import database_mail
from apscheduler.scheduler import Scheduler
schedudler = Scheduler(daemonic = False)  
conn = database_mail()
cursor=conn.cursor()
from zz91db_company import functiondb
from zz91settings import SPHINXCONFIG
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
def cloudsend(mailto,sub,content,nickname):
    
    # 不同于登录SendCloud站点的帐号，您需要登录后台创建发信域名，获得对应发信域名下的帐号和密码才可以进行邮件的发送。
    server = sendcloud.SendCloud('postmaster@zz91.sendcloud.org', 'WVp8m2oMcPgXdhO2', tls=False) # 目前tls 不可用
    message = sendcloud.Message(("kefu@asto-inc.com", "ZZ91客服中心"), sub, html=content)
    message.add_to(mailto)

    return server.smtp.send(message)
def getmailtemplate(code):
    sql="select t_content from template where code=%s"
    cursor.execute(sql,[code])
    resultlist=cursor.fetchone()
    if resultlist:
        return resultlist[0]
def getmailmaxid(id):
    sql="select maxid from update_log where id=%s"
    cursorc.execute(sql,[id])
    resultlist=cursorc.fetchone()
    if resultlist:
        return resultlist[0]
def send_mail(mailto,sub,content,nickname):   
    mail_host=u'smtp.exmail.qq.com'
    mail_user=u'system@asto-inc.com'
    mail_pass=u'zj88friend'
    mail_postfix=u'asto-inc.com'
    ######################    
    if nickname==None or nickname=="":
        nickname="ZZ91再生网"
    me="<system@asto-inc.com>"
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
def getcompemail(company_id):
    sql="select email,qq,back_email,is_use_back_email from company_account where company_id=%s"
    cursorc.execute(sql,[company_id])
    resultlist=cursorc.fetchone()
    if resultlist:
        email=resultlist[0]
        qq=resultlist[1]
        back_email=resultlist[1]
        is_use_back_email=resultlist[1]
        if str(is_use_back_email)=="1" and back_email!="" and back_email!=None and "@" in back_email:
            return back_email
        else:
            if (email!="" and email!=None and "@" in email and "zz91.com" not in email):
                return email
            else:
                if (qq!=None and qq!=""):
                    return qq+"@qq.com"
                else:
                    return None
    else:
        return None
            
            
#----所有塑料的客户
def suliaocompany(SPHINXCONFIG,keywords=""):
    maxid=getmailmaxid(6)
    email_content=getmailtemplate("zz91-2014zhanhui")
    email_title="欢迎您来参加第十四届中国塑料交易会-ZZ91再生网"
    cl = SphinxClient()
    cl.SetServer ( SPHINXCONFIG['serverid'], SPHINXCONFIG['port'] )
    cl.SetMatchMode ( SPH_MATCH_BOOLEAN )
    cl.SetSortMode( SPH_SORT_EXTENDED," company_id desc" )
    if maxid!=0:
        cl.SetFilterRange('company_id',1,int(maxid)-1)
    print maxid
    cl.SetGroupBy("company_id",SPH_GROUPBY_ATTR)
    cl.SetLimits (0,5000,50000)
    res = cl.Query ('@(title,label0,label1,label2,label3,label4,city,province,tags) '+keywords,'offersearch_new,offersearch_new_vip')
    if res:
        print res['total_found']
        if res.has_key('matches'):
            itemlist=res['matches']
            listall_company=[]
            for match in itemlist:
                id=match['id']
                attrs=match['attrs']
                company_id=attrs['company_id']
                email=getcompemail(company_id)
                #email="kangxy@asto.com.cn"
                listall_company.append(email)
                print email
                if email:
                    returnsms=send_mail(email,email_title,email_content,'')
                    print returnsms
                sqlb="update update_log set maxid=%s where id=6"
                cursorc.execute(sqlb,[company_id])
                connc.commit()
                time.sleep(2)
            #cloudsend(listall_company,email_title,email_content,'')
            #print listall_company
        
#----长期未登录客户发邮件
def sendlogin():
    template_id="zz91-login"
    mailcontent=getmailtemplate(template_id)
    sql="select account,company_id,DATEDIFF(CURDATE(),gmt_last_login),contact,id,email,qq from company_account where DATEDIFF(CURDATE(),gmt_last_login)>=90 and DATEDIFF(CURDATE(),gmt_last_login)<=100 and id>(select maxid from update_log where id=3) order by id asc"
    cursorc.execute(sql)
    resultlist=cursorc.fetchall()
    if resultlist:
        for list in resultlist:
            account=list[0]
            contact=list[3]
            company_id=list[1]
            numloginday=list[2]
            id=list[4]
            email=list[5]
            qq=list[6]
            #
            qcount=0
            pcount=0
            bcount=0
            rcount=0
            if email!="" or (qq!="" and qq):
                if email=="" and qq:
                    email=str(qq)+"@qq.com"
            if email==None:
                email=""
            if email!="" and "zz91.com" not in (email) and email:
                sqlq="select count(0) from inquiry where receiver_account=%s "
                cursorc.execute(sqlq,[account])
                resultq=cursorc.fetchone()
                if resultq:
                    qcount=resultq[0]
                sqlq="select count(0) from products where account=%s "
                cursorc.execute(sqlq,[account])
                resultq=cursorc.fetchone()
                if resultq:
                    pcount=resultq[0]
                sqlq="select count(0) from bbs_post where account=%s "
                cursorc.execute(sqlq,[account])
                resultq=cursorc.fetchone()
                if resultq:
                    bcount=resultq[0]
                sqlq="select count(0) from bbs_post_reply where account=%s "
                cursorc.execute(sqlq,[account])
                resultq=cursorc.fetchone()
                if resultq:
                    rcount=resultq[0]
                email_content=mailcontent.replace("{{qcount}}",str(qcount))
                email_content=email_content.replace("{{pcount}}",str(pcount))
                email_content=email_content.replace("{{bcount}}",str(bcount))
                email_content=email_content.replace("{{rcount}}",str(rcount))
                email_content=email_content.replace("{{contact}}",str(contact))
                email_content=email_content.replace("{{numloginday}}",str(numloginday))
                email_title="您已经很久没有来登录了，ZZ91再生网有客户给你留言了。"
                returnsms=send_mail(email,email_title,email_content,'')
                #returnsms=cloudsend("kangxy@asto.com.cn",email_title,mailcontent,'')
                print id
            sqlb="update update_log set maxid=%s where id=3"
            cursorc.execute(sqlb,[id])
            connc.commit()
            time.sleep(1)
#---供求审核未通过邮件
def postno():
    template_id="zz91-post-no"
    mailcontent=getmailtemplate(template_id)
    sql="select id,title,company_id,gmt_modified from products_s where id>(select maxid from update_log where id=5) and TIMESTAMPDIFF(MINUTE,gmt_modified,now())<100 and check_status=2 order by id asc"
    cursorc.execute(sql)
    resultlist=cursorc.fetchall()
    if resultlist:
        for list in resultlist:
            id=list[0]
            title=list[1]
            company_id=list[2]
            gmt_modified=list[3]
            gmt_modified=formattime(gmt_modified,0)
            sqlp="select unpass_reason from products where id=%s"
            cursorc.execute(sqlp,[id])
            resultp=cursorc.fetchone()
            if resultp:
                unpass_reason=resultp[0]
            if unpass_reason!="" and unpass_reason:
                company_account=zzcomp.getcompany_account_bycompany_id(company_id)
                email=company_account['email']
                contact=company_account['contact']
                email_content=mailcontent.replace("{{title}}",title)
                email_content=email_content.replace("{{contact}}",contact)
                email_content=email_content.replace("{{products_id}}",str(id))
                email_content=email_content.replace("{{posttime}}",str(gmt_modified))
                email_content=email_content.replace("{{unpass_reason}}",str(unpass_reason))
                email_title="您在ZZ91发布的供求已经未通过审核！请修改后重新发布。"
                send_status=0
                gmt_created=datetime.datetime.now()
                gmt_post=gmt_modified=datetime.datetime.now()
                send_email="kefu@asto-inc.com"
                priority=0
                
                value=[template_id,email_title,email_content,email,send_status,gmt_created,gmt_modified,priority,send_email,gmt_post]
                sqla="insert into mail_info(template_id,email_title,email_content,receiver_email,send_status,gmt_created,gmt_modified,priority,send_email,gmt_post) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
                cursor.execute(sqla,value)
                sqlb="update update_log set maxid=%s where id=5"
                cursorc.execute(sqlb,[id])
                conn.commit()
                connc.commit()
                print id
def sendsystemmail():
    send_mail("kangxy@asto.com.cn","test","xitong",'')
sendsystemmail()
conn.close()
#sendlogin()
#suliaocompany(SPHINXCONFIG,keywords="废塑料")
#time.sleep(2)
                    
