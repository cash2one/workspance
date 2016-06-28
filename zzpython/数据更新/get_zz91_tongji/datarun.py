#-*- coding:utf-8 -*-
#----页面跟踪情况analyse_page.html
from public import *
from data_public import url_list
import datetime,MySQLdb
from zz91conn import database_other,database_comp
from zz91settings import logpath
from zz91tools import date_to_str
import operator

conn=database_other()
cursor = conn.cursor()

def getagindata(gmt_created,arg=''):
    set1 = set()
    list1=[]
    datalist=open(logpath+'run.'+gmt_created)
    for line in datalist:
        linedir=eval(line)
        ip=linedir['ip']
        datadir=eval(linedir['data'])
        account=datadir['account']
        countdate=datadir['date']
        url=datadir['url']
        lt1={'ip':ip,'account':account,'countdate':countdate,'url':url}
        list1.append(lt1)
        set1.add(ip)
    datalist.close()
    if arg==1:
        list1.sort(key=operator.itemgetter('countdate'),reverse=True)
    else:
        list1.sort(key=operator.itemgetter('countdate'),reverse=False)
    return {'list1':list1,'set1':set1}

def geturlin(gmt_created,agindata,arg=''):
    list1=agindata['list1']
    set1=agindata['set1']
    set2=set()
    listall=[]
    shop_ip=[]
    if arg==2:
        for s1 in set1:
            js1=0
            js2=0
            for l1 in list1:
                ip1=l1['ip']
                url1=l1['url']
                account=l1['account']
                if s1==ip1:
                    if js1==1:
                        for ult1 in url_list:
                            if ult1['url'] in url1:
                                js2=js2+1
                                list={'ip':ip1,'url':url1,'type':ult1['type'],'typeid':ult1['typeid']}
                                listall.append(list)
                                set2.add(ult1['type'])
                                break
                        if js2==1:
                            break
                        shop_ip.append(s1)
                    if account:
                        js1=js1+1
    elif arg==3:
        for s1 in set1:
            js1=0
            js2=0
            for l1 in list1:
                ip1=l1['ip']
                url1=l1['url']
                account=l1['account']
                if s1==ip1:
                    if js1==1:
                        for ult1 in url_list:
                            if ult1['url'] in url1:
                                js2=js2+1
                                list={'ip':ip1,'url':url1,'type':ult1['type'],'typeid':ult1['typeid']}
                                listall.append(list)
                                set2.add(ult1['type'])
                                break
                        if js2==1:
                            break
                        shop_ip.append(s1)
                    if 'http://www.zz91.com/register/register_stp1.htm' in url1:
                        js1=js1+1
    else:
        for s1 in set1:
            js1=0
            js2=0
            for l1 in list1:
                ip1=l1['ip']
                url1=l1['url']
                if s1==ip1:
                    if js1==0:
                        for ult1 in url_list:
                            if ult1['url'] in url1:
                                js2=js2+1
                                list={'ip':ip1,'url':url1,'type':ult1['type'],'typeid':ult1['typeid']}
                                listall.append(list)
                                set2.add(ult1['type'])
                                break
                        if js2==1:
                            break
                        shop_ip.append(s1)
                    js1=js1+1
    return {'list':listall,'set':set2,'shop_ip':shop_ip}

def getinpage2(listall):
#    listall=geturlin(gmt_created,agindata)
#    print listall
    list2=listall['list']
    setlist2=listall['set']
    shop_ip=listall['shop_ip']
    listall2=[]
    for st2 in setlist2:
        js1=0
        set1=set()
        for lt2 in list2:
            if st2==lt2['type']:
                set1.add(lt2['ip'])
            list3={'type':st2,'iplist':set1,'numb':len(set1)}
            listall2.append(list3)
    return {'list':listall2,'shop_ip':shop_ip}

def getinpage(listall):
#    listall=geturlin(gmt_created,agindata)
    list2=listall['list']
    listall2=[]
    setlist2=listall['set']
    for st2 in setlist2:
        js1=0
        js2=0
        for lt2 in list2:
            if st2==lt2['type']:
                if js1==0 and js2==0:
                    js1=js1+1
                    list={'url':lt2['url'],'type':st2,'typeid':lt2['typeid']}
                    listall2.append(list)
                js2=js2+1
    return listall2

pagelist=[1,2,3,4,5,6,7,8,9,10,11,12,13,15,18,19,20,21,22,99,100]
def page_init(gmt_created):
    listall=[]
    for pg in pagelist:
        sql='insert into dataanalysis_page(typeid,gmt_created,in_page,in_page_ip,out_page,log_page,reg_page) values(%s,%s,%s,%s,%s,%s,%s)'
        cursor.execute(sql,[pg,gmt_created,0,'',0,0,0])
        conn.commit()
        sql='select max(id) from dataanalysis_page'
        cursor.execute(sql)
        result=cursor.fetchone()[0]
        list={'typeid':pg,'upid':result}
        listall.append(list)
    return listall

def regetupid(list_dir,typeid):
    for list in list_dir:
        if typeid==list['typeid']:
            upid=list['upid']
            return upid

def in_page(gmt_created,list_dir,agindata,arg=''):
    listall1=geturlin(gmt_created,agindata,arg)
    list1=getinpage(listall1)
    listall2=getinpage2(listall1)
    list2=listall2['list']
    shop_ip=listall2['shop_ip']
    allnumb=0
    if arg==1:
        sql='update dataanalysis_page set typeid=%s,out_page=%s where id=%s'
    elif arg==2:
        sql='update dataanalysis_page set typeid=%s,log_page=%s where id=%s'
    elif arg==3:
        sql='update dataanalysis_page set typeid=%s,reg_page=%s where id=%s'
    else:
        sql='update dataanalysis_page set typeid=%s,in_page=%s,in_page_ip=%s where id=%s'
    listall=[]
    upid=0
    for lt1 in list1:
        js1=0
        for lt2 in list2:
            if lt1['type']==lt2['type']:
                if js1==0:
                    typeid=lt1['typeid']
                    upid=regetupid(list_dir,typeid)
                    iplist=lt2['iplist']
                    numb=len(iplist)
                    iplist=','.join(iplist)
                    if iplist[:1]==u',':
                        iplist=iplist[1:]
                    allnumb=allnumb+numb
                    list={'type':lt2['type'],'typeid':typeid,'iplist':iplist,'numb':numb,'upid':upid}
#                    print numb
                    if arg in [1,2,3]:
                        cursor.execute(sql,[typeid,numb,upid])
                    else:
                        cursor.execute(sql,[typeid,numb,iplist,upid])
                    listall.append(list)
                js1=js1+1
#    print allnumb
    shop_numb=len(shop_ip)
#    print shop_ip
    allnumb=allnumb+shop_numb
    shop_ip=','.join(shop_ip)
    if shop_ip[:1]==u',':
        shop_ip=shop_ip[1:]
        
    shop_id=regetupid(list_dir,19)
    allnumb_id=regetupid(list_dir,100)
    list_shop={'type':'shop','typeid':19,'iplist':shop_ip,'numb':shop_numb,'upid':shop_id}
    list_all={'type':'all_numb','typeid':100,'iplist':'','numb':allnumb,'upid':allnumb_id}
    listall.append(list_shop)
    listall.append(list_all)
    if arg in [1,2,3]:
        cursor.execute(sql,[19,shop_numb,shop_id])
        cursor.execute(sql,[100,allnumb,allnumb_id])
    else:
        cursor.execute(sql,[19,shop_numb,shop_ip,shop_id])
        cursor.execute(sql,[100,allnumb,'',allnumb_id])
    conn.commit()
    return listall

from datarun2 import getinpage33
def getcycle(agindata,gmt_created,listall,page):
    listall2=[]
    for list in listall:
        iplist=list['iplist']
        iplist=iplist.split(',')
        upid=list['upid']
        listall=getinpage33(agindata,upid,gmt_created,iplist,page)
        listall2.append(listall)
    return listall2

def flat(tree):
    res = []
    for i in tree:
        if isinstance(i, list):
            res.extend(flat(i))
        else:
            res.append(i)
    return res

def getinpage3(gmt_created):
    list_dir=page_init(gmt_created)
    agindata=getagindata(gmt_created)
    listall=in_page(gmt_created,list_dir,agindata)

#    listall2=listall
#    for i in xrange(1,3):
    listall2=getcycle(agindata,gmt_created,listall,1)
    list2=flat(listall2)
    listall3=getcycle(agindata,gmt_created,list2,2)
    list3=flat(listall3)
    listall4=getcycle(agindata,gmt_created,list3,3)
    list4=flat(listall4)
    listall5=getcycle(agindata,gmt_created,list4,4)
    list5=flat(listall5)
    listall6=getcycle(agindata,gmt_created,list5,5)
    list6=flat(listall6)
    listall7=getcycle(agindata,gmt_created,list6,6)
    list7=flat(listall7)
    listall8=getcycle(agindata,gmt_created,list7,7)
    in_page(gmt_created,list_dir,agindata,2)
    in_page(gmt_created,list_dir,agindata,3)
    agindata=getagindata(gmt_created,1)
    in_page(gmt_created,list_dir,agindata,1)

def datarunall():
    from zz91tools import getpastday
    try:
        daylist=getpastday(2)
        for dday in daylist:
            dday_str=date_to_str(dday)
            sql='select id from dataanalysis_page where gmt_created=%s'
            cursor.execute(sql,[dday_str])
            result=cursor.fetchone()
            if not result:
                getinpage3(dday_str)
    except IOError as e:
        print e
    return '页面访问行为统计'

if __name__=="__main__":
    datarunall()