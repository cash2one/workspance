#-*- coding:utf-8 -*-
#---工具函数
from public import *
from data_public import url_list
import datetime,MySQLdb
from zz91conn import database_other,database_comp
from zz91settings import logpath
from zz91tools import getpastday,date_to_str

conn=database_other()
cursor = conn.cursor()

def geturlin(agindata,gmt_created,iplist,page):
    set1 = iplist
    list1=agindata['list1']
    set2=set()
    listall=[]
    shop_ip=[]
    for s1 in set1:
        js1=0
        js2=0
        for l1 in list1:
            ip1=l1['ip']
            url1=l1['url']
            if s1==ip1:
                if js1==page:
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
#                            print list
                js1=js1+1
#    print shop_ip
    return {'list':listall,'set':set2,'shop_ip':shop_ip}

def getinpage2(listall):
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

def getinpage33(agindata,upid,gmt_created,iplist,page):
    listall1=geturlin(agindata,gmt_created,iplist,page)
    list1=getinpage(listall1)
    listall2=getinpage2(listall1)
    list2=listall2['list']
    shop_ip=listall2['shop_ip']
    allnumb=0
    sql='insert into data_pagerun(upid,typeid,iplist,numb,gmt_created) values(%s,%s,%s,%s,%s)'
    sql2='select max(id) from data_pagerun'
    listall=[]
    for lt1 in list1:
        js1=0
        for lt2 in list2:
            if lt1['type']==lt2['type']:
                if js1==0:
                    typeid=lt1['typeid']
                    iplist=lt2['iplist']
                    numb=len(iplist)
                    iplist=','.join(iplist)
                    allnumb=allnumb+numb
#                    print numb
                    cursor.execute(sql,[upid,typeid,iplist,numb,gmt_created])
                    conn.commit()
                    cursor.execute(sql2)
                    result=cursor.fetchone()[0]
                    list={'typeid':typeid,'iplist':iplist,'numb':numb,'upid':result}
                    listall.append(list)
                js1=js1+1
#    print allnumb
    shop_numb=len(shop_ip)
    allnumb=allnumb+shop_numb
    shop_ip=','.join(shop_ip)
    cursor.execute(sql,[upid,19,shop_ip,shop_numb,gmt_created])
    conn.commit()
    cursor.execute(sql2)
    result=cursor.fetchone()[0]
    list={'typeid':19,'iplist':shop_ip,'numb':shop_numb,'upid':result}
    listall.append(list)
    cursor.execute(sql,[upid,100,'',allnumb,gmt_created])
    conn.commit()
    cursor.execute(sql2)
    result=cursor.fetchone()[0]
    list={'typeid':100,'iplist':'','numb':allnumb,'upid':result}
    listall.append(list)
    return listall
#i=getinpage3('2014-05-15')
#print i
'''
iplist='220.181.108.110,218.93.73.165,110.230.211.174,116.27.213.180,218.93.18.114,14.211.18.81'
iplist=iplist.split(',')
print iplist
listall=getinpage33('2014-05-15',iplist,2)
print listall
'''