#-*- coding:utf-8 -*-
#----抓取和讯网汇率
from public import *
from zz91db_ast import companydb
#from zz91db_130 import otherdb
from zz91tools import date_to_str,get_content,get_inner_a,get_a_url
import datetime,time,re,urllib2

dbc=companydb()
#dbc=otherdb()

#----获得html
def get_url_content(url,arg=''):#突破网站防爬
    print url
    try:
        i_headers = {"User-Agent": "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1) Gecko/20090624 Firefox/3.5",\
                     "Referer": 'http://www.baidu.com'}
        req = urllib2.Request(url, headers=i_headers)
        html=urllib2.urlopen(req).read()
        return html
    except:
        return ''
    
def getallhuilv(fromc,toc,name):
    time.sleep(1)
    html=get_url_content('http://qq.ip138.com/hl.asp?from='+fromc+'&to='+toc+'&q=1')
    huilv=re.findall('</td><td>(.*?)</td></tr></table>',html)
    if huilv:
        huilv1=huilv[0].split('</td><td>')[0]
        sql1='select id,rate from exchange_rate where miancountry=%s and country=%s'
        result1=dbc.fetchonedb(sql1,['人民币',name])
        if result1:
            id=result1[0]
            rate=result1[1]
            if not float(huilv1)==rate:
                sql2='update exchange_rate set rate=%s where id=%s'
                dbc.updatetodb(sql2,[huilv1,id])
        else:
            sql='insert into exchange_rate(miancountry,country,rate) values(%s,%s,%s)'
            dbc.updatetodb(sql,['人民币',name,huilv1])

#----美元/人民币汇率
def getUSD_CNY():
    huilv=getallhuilv('USD','CNY','美元')
#----欧元/人民币汇率
def getEUR_CNY():
    huilv=getallhuilv('EUR','CNY','欧元')
#----英镑/人民币汇率
def getGBP_CNY():
    huilv=getallhuilv('GBP','CNY','英镑')
#----港元/人民币汇率
def getHKD_CNY():
    huilv=getallhuilv('HKD','CNY','港元')
#----台币/人民币汇率
def getTWD_CNY():
    huilv=getallhuilv('TWD','CNY','台币')
#----澳元/人民币汇率
def getAUD_CNY():
    huilv=getallhuilv('AUD','CNY','澳元')
#----韩元/人民币汇率
def getKRW_CNY():
    huilv=getallhuilv('KRW','CNY','韩元')
#----日元/人民币汇率
def getJPY_CNY():
    huilv=getallhuilv('JPY','CNY','日元')

def getallhuihuan2():
    sql='select country,rate from exchange_rate where miancountry=%s'
    resultlist=dbc.fetchalldb(sql,'人民币')
    listall=[]
    sql1='select id from exchange_rate where miancountry=%s and country=%s'
    sql='insert into exchange_rate(miancountry,country,rate) values(%s,%s,%s)'
    sql2='update exchange_rate set rate=%s where id=%s'
    for result in resultlist:
        country=result[0]
        rate=result[1]
        daorate=1/rate
        result1=dbc.fetchonedb(sql1,[country,'人民币'])
        if result1:
            id1=result1[0]
            dbc.updatetodb(sql2,[daorate,id1])
        else:
            dbc.updatetodb(sql,[country,'人民币',daorate])
        list={'country':country,'rate':rate}
        listall.append(list)
    for list1 in listall:
        country1=list1['country']
        rate1=list1['rate']
        for list2 in listall:
            country2=list2['country']
            rate2=list2['rate']
            if not country1==country2:
                rate3=rate1/rate2
                result3=dbc.fetchonedb(sql1,[country2,country1])
                if result3:
                    id3=result3[0]
#                    print country1
#                    print country2
#                    print rate3
#                    print '---------'
                    dbc.updatetodb(sql2,[rate3,id3])
                else:
                    dbc.updatetodb(sql,[country2,country1,rate3])

def getallhuihuan():
    getUSD_CNY()
    getEUR_CNY()
    getGBP_CNY()
    getHKD_CNY()
    #getTWD_CNY()
    getAUD_CNY()
    getKRW_CNY()
    getJPY_CNY()
    getallhuihuan2()
    return 1

def getallhuilv2(fromc,toc):
    time.sleep(2)
    html=get_url_content('http://qq.ip138.com/hl.asp?from='+fromc+'&to='+toc+'&q=1')
    huilv=re.findall('</td><td>(.*?)</td></tr></table>',html)
    if huilv:
        huilv1=huilv[0].split('</td><td>')[0]
        huilv1=huilv1.replace(',','')
        return huilv1

countrydir={'美元':'USD','欧元':'EUR','英镑':'GBP','港元':'HKD','人民币':'CNY','台币':'TWD','澳元':'AUD','韩元':'KRW','日元':'JPY'}
def getallhuihuan3():
    sql='select id,miancountry,country from exchange_rate'
    sql1='update exchange_rate set rate=%s where id=%s'
    resultlist=dbc.fetchalldb(sql)
    for result in resultlist:
        id=result[0]
        miancountry=countrydir[result[1].encode('utf-8')]
        country=countrydir[result[2].encode('utf-8')]
        huilv=getallhuilv2(country,miancountry)
#        if '.' in huilv:
#            huilvlist=huilv.split('.')
#            huilv1=huilvlist[0]
#            huilv2=huilvlist[1][:4]
#            huilv=huilv1+huilv2
        dbc.updatetodb(sql1,[huilv,id])
        

if __name__=="__main__":
    getallhuihuan3()