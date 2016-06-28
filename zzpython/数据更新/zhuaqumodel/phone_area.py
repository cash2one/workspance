#-*- coding:utf-8 -*-
#----抓取手机号码所属地区
from public import *
from zz91conn import database_comp,database_other
from zz91tools import getToday,date_to_str,get_url_content,get_content,get_inner_a,get_a_url,getdatelist
import datetime,time,re
conn=database_comp()
cursor=conn.cursor()
conn_other=database_other()
cursor_other=conn_other.cursor()

#----抓取百度搜索结果
def getphonearea(mobile,id):
    geturlone='http://www.baidu.com/s?rn=1&q1='+mobile
    html_area=get_url_content(geturlone)
    area_content=get_content('<div class="op_mobilephone_r">(.*?)</div>',html_area)
    if area_content:
        arealist=re.findall('<span>(.*?)</span>',area_content)
        if arealist:
            areas=arealist[1]
            areasl=areas.split('&nbsp;')
            province=areasl[0]
            city=areasl[1]
            if not province:
                province=city
            if city:
    #            print mobile
    #            print province+' '+city
                sql='update phone_log set province=%s,city=%s where id=%s'
                cursor.execute(sql,[province,city,id])
                conn.commit()

#----查询数据库结果
def getphonearea2(mobile,id):
    sql3='update phone_log set province=%s,city=%s where id=%s'
    if mobile[0]=='0':
        parg=mobile[:4]
        parg2=mobile[:3]
#        print parg
        sql='select id,province,city from tel_guoneinumb where numb=%s'
        cursor_other.execute(sql,[parg])
        result=cursor_other.fetchone()
        if result:
            province=result[1]
            city=result[2]
#            print province+' '+city
            cursor.execute(sql3,[province,city,id])
            conn.commit()
        else:
            cursor_other.execute(sql,[parg2])
            result2=cursor_other.fetchone()
            if result2:
                province=result2[1]
                city=result2[2]
#                print province+' '+city
                cursor.execute(sql3,[province,city,id])
                conn.commit()
    else:
        marg=mobile[:7]
        sql='select id,province,city from mobile_number where numb=%s'
        cursor_other.execute(sql,[marg])
        result=cursor_other.fetchone()
        if result:
            province=result[1]
            city=result[2]
#            print province+' '+city
        cursor.execute(sql3,[province,city,id])
        conn.commit()

#----查出数据库的手机号码,获得城市名为null的数据的id
def getphoneareall():
    timelist=getdatelist(7)
    for timeall in timelist:
        gmt_begin=timeall['gmt_begin']
        gmt_end=timeall['gmt_end']
        sql='select id,caller_id,province from phone_log where start_time>=%s and start_time<%s'
        cursor.execute(sql,[gmt_begin,gmt_end])
        resultlist=cursor.fetchall()
        if resultlist:
            for result in resultlist:
                id=result[0]
                mobile=result[1]
                province=result[2]
                if not province:
                    print id
                    try:
                        getphonearea2(mobile,id)
                    except:
                        errors='error '+mobile
#                        print errors

if __name__=="__main__":
    getphoneareall()