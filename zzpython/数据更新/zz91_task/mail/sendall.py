#-*- coding:utf-8 -*-
from public import *
import os,json,requests
import urllib
import smtplibnew
import datetime,time
from email.mime.text import MIMEText
from sphinxapi import *
from xml.etree import ElementTree as ET
from apscheduler.scheduler import Scheduler
schedudler = Scheduler(daemonic = False)  
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
def validateEmail(email):
    if len(email) > 7:
        if re.match("^.+\\@(\\[?)[a-zA-Z0-9\\-\\.]+\\.([a-zA-Z]{2,3}|[0-9]{1,3})(\\]?)$", email) != None:
            return 1
#判断是否为HEX码
def gethextype(keywords):
    zwtype=0
    zwflag=0
    strvalue="abcdef0123456789"
    for a in keywords:
        if (a >= u'\u4e00' and a<=u'\u9fa5'):
            zwflag=zwflag+1
    if zwflag>0:
        zwtype=1
    zwflag=0
    if zwtype==0:
        for a in keywords:
            if (strvalue.find(a)==-1):
                zwflag=zwflag+1
        if zwflag>0:
            zwtype=1
    if zwtype==1:
        return False
    else:
        return True
def sendwebmail(mailto,sub,content,nickname):
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
#---获取邮箱
def getcompemail(company_id):
    sql="select email,qq,back_email,is_use_back_email from company_account where company_id=%s"
    cursorc.execute(sql,[company_id])
    resultlist=cursorc.fetchone()
    if resultlist:
        email=resultlist[0]
        if validateEmail(email)==None:
            return None
        if gethextype(email)==True:
            return None
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

#---所以塑料颗粒客户
def yangcompanylist(SPHINXCONFIG,keywords="塑料颗粒"):
    emailtemplate="zz91-yang"
    sql='insert into mail_temp(email,company_id,emailtemplate) values(%s,%s,%s)'
    cl = SphinxClient()
    cl.SetServer ( SPHINXCONFIG['serverid'], SPHINXCONFIG['port'] )
    cl.SetMatchMode ( SPH_MATCH_BOOLEAN )
    cl.SetSortMode( SPH_SORT_EXTENDED," company_id desc" )
    cl.SetGroupBy("company_id",SPH_GROUPBY_ATTR)
    cl.SetLimits (0,5000,50000)
    res = cl.Query ('@(title,label0,label1,label2,label3,label4,city,province,tags) '+keywords,'offersearch_new,offersearch_new_vip')
    if res:
#        print res['total_found']
        if res.has_key('matches'):
            itemlist=res['matches']
            for match in itemlist:
                id=match['id']
                attrs=match['attrs']
                company_id=attrs['company_id']
                email=getcompemail(company_id)
#                print company_id
#                print email
                #---存数据
                if email:
                    if '@' in email.replace('@','',1):
                        continue
                    sql2='select id from mail_temp where company_id=%s and emailtemplate=%s'
                    cursorc.execute(sql2,[company_id,emailtemplate])
                    result=cursorc.fetchone()
                    if not result:
                        cursorc.execute(sql,[email,company_id,emailtemplate])
                        connc.commit()

    cl2 = SphinxClient()
    cl2.SetServer ( SPHINXCONFIG['serverid'], SPHINXCONFIG['port'] )
    cl2.SetMatchMode ( SPH_MATCH_BOOLEAN )
    cl2.SetSortMode( SPH_SORT_EXTENDED,"id desc" )
    cl2.SetLimits (0,5000,50000)
    res2 = cl2.Query ('@(name,business,sale_details,buy_details,tags) '+keywords,'company')
    if res2:
        if res2.has_key('matches'):
            itemlist2=res2['matches']
            for match2 in itemlist2:
                company_id2=match2['id']
                email2=getcompemail(company_id2)
                print company_id2
                print email2
                #---存数据
                if email2:
                    if '@' in email2.replace('@','',1):
                        continue
                    sql2='select id from mail_temp where company_id=%s and emailtemplate=%s'
                    cursorc.execute(sql2,[company_id2,emailtemplate])
                    result2=cursorc.fetchone()
                    if not result2:
                        cursorc.execute(sql,[email2,company_id2,emailtemplate])
                        connc.commit()

def sendmailall():
    emailtemplate="zz91-yang"
    content=getmailtemplate(emailtemplate)
    sub="送5000元首页广告，ZZ91样品中心火爆招商"
    sql="select email,id from mail_temp where emailtemplate=%s and id >(select maxid from update_log where id=7)"
    cursorc.execute(sql,[emailtemplate])
    resultlist=cursorc.fetchall()
    for result in resultlist:
        email=result[0]
        id=result[1]
        if validateEmail(email)==None:
            email= None
        else:
            if gethextype(email)==True:
                email= None
        if email:
            result=sendwebmail(email,sub,content,"")
            print id
            sqlb="update update_log set maxid=%s where id=7"
            cursorc.execute(sqlb,[id])
            connc.commit()
            time.sleep(1)
    #mailto+="kangxy@asto.com.cn"
    
    #mailto="kangxy@asto-inc.com;jiaolj@asto-inc.com;kangxianyue@163.com;kangxy@asto.com.cn"
    
    
    #emails="13347959938.@163.COM, fs168168@yeah.ent, 477200wangdi.@sina.com, zhangyumeng311.@126.com, 00000@00.00, 724119956.@.cn.com, 794456102@.qq.com, m13292145681@163.con, yanyou137@163.con, myname@sghu.jik, 888888@.qq.com, 1482627036@qq.con, www.769583726@qq.con, 1361625125@qq.con, 4651324686@qq.cpm, 1161797132@QQ.CON, 186778888@qq.con, 1533020937@QQ.CON, mzsl2011.@126.com, 1090168083@qq.cou, caijingya4923@16..com, 756221347.@cn.com, zjwjz@163.con, 1119861603@qq.con, shanwen678@126.con, 361878690.@qq.com, zghzqt@.126.com, kaiyangzhangnan.@163.com, www.wangjinguihao @163.cn, 694044632@qq.con, 1289471611@.qq.com, 123456@qq.con, 1062861549 @qq.com, www.byjsml@126.con, guozhongpeng.@2006.163, syl8100387@163.con"
    #emails="fs168168@yeah.ent, 477200wangdi.@sina.com, zhangyumeng311.@126.com, 00000@00.00, 724119956.@.cn.com, 794456102@.qq.com, m13292145681@163.con, yanyou137@163.con, myname@sghu.jik, 888888@.qq.com, 1482627036@qq.con, www.769583726@qq.con, 1361625125@qq.con, 4651324686@qq.cpm, 1161797132@QQ.CON, 186778888@qq.con, 1533020937@QQ.CON, mzsl2011.@126.com, 1090168083@qq.cou, caijingya4923@16..com, 756221347.@cn.com, zjwjz@163.con, 1119861603@qq.con, shanwen678@126.con, 361878690.@qq.com, zghzqt@.126.com, kaiyangzhangnan.@163.com, www.wangjinguihao @163.cn, 694044632@qq.con, 1289471611@.qq.com, 123456@qq.con, 1062861549 @qq.com, www.byjsml@126.con, guozhongpeng.@2006.163, syl8100387@163.con"
    #emails="477200wangdi.@sina.com, zhangyumeng311.@126.com, 00000@00.00, 724119956.@.cn.com, 794456102@.qq.com, m13292145681@163.con, yanyou137@163.con, myname@sghu.jik, 888888@.qq.com, 1482627036@qq.con, www.769583726@qq.con, 1361625125@qq.con, 4651324686@qq.cpm, 1161797132@QQ.CON, 186778888@qq.con, 1533020937@QQ.CON, mzsl2011.@126.com, 1090168083@qq.cou, caijingya4923@16..com, 756221347.@cn.com, zjwjz@163.con, 1119861603@qq.con, shanwen678@126.con, 361878690.@qq.com, zghzqt@.126.com, kaiyangzhangnan.@163.com, www.wangjinguihao @163.cn, 694044632@qq.con, 1289471611@.qq.com, 123456@qq.con, 1062861549 @qq.com, www.byjsml@126.con, guozhongpeng.@2006.163, syl8100387@163.con"
    #emails="zhangyumeng311.@126.com, 00000@00.00, 724119956.@.cn.com, 794456102@.qq.com, m13292145681@163.con, yanyou137@163.con, myname@sghu.jik, 888888@.qq.com, 1482627036@qq.con, www.769583726@qq.con, 1361625125@qq.con, 4651324686@qq.cpm, 1161797132@QQ.CON, 186778888@qq.con, 1533020937@QQ.CON, mzsl2011.@126.com, 1090168083@qq.cou, caijingya4923@16..com, 756221347.@cn.com, zjwjz@163.con, 1119861603@qq.con, shanwen678@126.con, 361878690.@qq.com, zghzqt@.126.com, kaiyangzhangnan.@163.com, www.wangjinguihao @163.cn, 694044632@qq.con, 1289471611@.qq.com, 123456@qq.con, 1062861549 @qq.com, www.byjsml@126.con, guozhongpeng.@2006.163, syl8100387@163.con"
    #emails="00000@00.00, 724119956.@.cn.com, 794456102@.qq.com, m13292145681@163.con, yanyou137@163.con, myname@sghu.jik, 888888@.qq.com, 1482627036@qq.con, www.769583726@qq.con, 1361625125@qq.con, 4651324686@qq.cpm, 1161797132@QQ.CON, 186778888@qq.con, 1533020937@QQ.CON, mzsl2011.@126.com, 1090168083@qq.cou, caijingya4923@16..com, 756221347.@cn.com, zjwjz@163.con, 1119861603@qq.con, shanwen678@126.con, 361878690.@qq.com, zghzqt@.126.com, kaiyangzhangnan.@163.com, www.wangjinguihao @163.cn, 694044632@qq.con, 1289471611@.qq.com, 123456@qq.con, 1062861549 @qq.com, www.byjsml@126.con, guozhongpeng.@2006.163, syl8100387@163.con".replace(" ","")
    #emails="www.wangjinguihao @163.cn, 1062861549 @qq.com"
    
#----所有塑料的客户 2014中国塑料交易会
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
                
                listall_company.append(email)
                print email
                if email:
                    returnsms=sendwebmail(email,email_title,email_content,'')
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
#sendlogin()
#suliaocompany(SPHINXCONFIG,keywords="废塑料")
#time.sleep(2)
#yangcompanylist(SPHINXCONFIG,keywords="塑料颗粒")
sendmailall()
conn.close()
