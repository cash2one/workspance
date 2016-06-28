import MySQLdb   
import pymssql
import sys
import codecs
import datetime
import struct
import os

reload(sys)
#type = sys.getfilesystemencoding()
#sys.setdefaultencoding('utf-8')
#---数据库连接和配置
conn=pymssql.connect(host=r'192.168.2.2',trusted=False,user='rcu_crm',password='fdf@$@#dfdf9780@#1.kdsfd',database='rcu_crm',charset=None)
cursor=conn.cursor()
connmy = MySQLdb.connect(host='192.168.2.10', user='seocompany', passwd='Gs8FXT6szWNqDhG8',db='seocompany',charset='utf8')     
cursormy = connmy.cursor()
execfile("/usr/apps/python/inc.py")

def upseocompany():
    sql="select com_id,com_email,com_mobile,com_name,com_contactperson,gmt_created,target_assure,seo_start,target_time from v_seolist"
    cursor.execute(sql)
    results = cursor.fetchall()
    if results:
        for list in results:
            com_id=list[0]
            name=changezhongwen(list[3])
            email=changezhongwen(list[1])
            mobile=changezhongwen(list[2])
            contact=changezhongwen(list[4])
            gmt_created=list[5]
            isdelete=0
            islost=0
            sqlp="select id from seo_company where mail=%s"
            cursormy.execute(sqlp,[email])
            seoc = cursormy.fetchone()
            if seoc==None:
                valued=[name,email,mobile,contact,gmt_created,isdelete,islost,com_id]
                sqlc="insert into seo_company(name,mail,mobile,contact,gmt_created,isdelete,islost,com_id) values(%s,%s,%s,%s,%s,%s,%s,%s)"
                cursormy.execute(sqlc,valued)
def getseocomplist(sid):
    sql="select com_id,com_email,com_mobile,com_name,com_contactperson,gmt_created,target_assure,seo_start,target_time,price,waste,personid from v_seolist where id=%s"
    cursor.execute(sql,sid)
    list = cursor.fetchone()
    if list:
        com_id=list[0]
        name=changezhongwen(list[3])
        email=changezhongwen(list[1])
        mobile=changezhongwen(list[2])
        contact=changezhongwen(list[4])
        gmt_created=list[5]
        seo_start=list[7]
        target_time=list[8]
        price=list[9]
        waste=list[10]
        seouser_id=list[11]
        return {'com_id':com_id,'name':name,'email':email,'mobile':mobile,'contact':contact,'target_time':target_time,'seo_start':seo_start,'price':price,'waste':waste,'seouser_id':seouser_id}
def getmcompanyid(email):
    sqlp="select id from seo_company where mail=%s"
    cursormy.execute(sqlp,[email])
    seoc = cursormy.fetchone()
    if seoc:
        return seoc[0]
def getmkeyid(kid):
    sqlp="select id from seo_keywords where oldid=%s"
    cursormy.execute(sqlp,[kid])
    seoc = cursormy.fetchone()
    if seoc:
        return seoc[0]
def upseokeywords():
    sql="select sid,keywords,com_msb,target_require,id from seo_keywordslist"
    cursor.execute(sql)
    results = cursor.fetchall()
    if results:
        for list in results:
            sid=list[0]
            keywords=changezhongwen(list[1])
            seocomplist=getseocomplist(sid)
            if seocomplist:
                email=seocomplist['email']
                company_id=getmcompanyid(email)
                standarddemand=list[3]
                if standarddemand==None:
                    standarddemand=1
                gmt_created=datetime.datetime.now()
                begintime=seocomplist['seo_start']
                standardtime=seocomplist['target_time']
                shopsaddress=changezhongwen(list[2])
                chargetype=1
                price=seocomplist['price']
                isdelete=0
                years=1
                islost=seocomplist['waste']
                seouser_id=1
                oldid=list[4]
                valued=[company_id, keywords, standarddemand,  gmt_created, begintime, standardtime,  shopsaddress, chargetype, price, years,  isdelete, islost,oldid]
                sqlc="insert into seo_keywords(company_id, keywords, standarddemand,  gmt_created, begintime, standardtime,  shopsaddress, chargetype, price, years,  isdelete, islost,oldid) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
                cursormy.execute(sqlc,valued)
            
def uphistorykeywords():
    sql="select kid,sid,baidu_ranking,kdate from seo_keywords_history where ktype='check_pai'"
    cursor.execute(sql)
    results = cursor.fetchall()
    if results:
        for list in results: 
            kid=list[0]
            sid=list[1]
            baidu_ranking=list[2]
            kdate=list[3]
            update_time=kdate
            gmt_created=datetime.datetime.now()
            seocompanylist=getseocomplist(sid)
            if seocompanylist:
                company_id=getmcompanyid(seocompanylist['email'])
            else:
                company_id=0
            keywords_id=getmkeyid(kid)
            isstandard=0
            valued=[company_id,keywords_id,gmt_created,baidu_ranking,update_time,isstandard]
            sqlc="insert into seo_rankinghistory(company_id,keywords_id,gmt_created,baidu_ranking,update_time,isstandard) values(%s,%s,%s,%s,%s,%s)"
            cursormy.execute(sqlc,valued)
            
uphistorykeywords()
            