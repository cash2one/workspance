#-*- coding:utf-8 -*-
#----废金属走势图8张
from public import *
from apscheduler.scheduler import Scheduler
from zz91conn import database_comp
from zz91tools import get_url_content,filter_tags
import datetime,time,re
from bs4 import BeautifulSoup

conn=database_comp()
cursor = conn.cursor()

def chart_luanma1(content):
    soup = BeautifulSoup(content)
    tablestr=""
    for table in soup.findAll('table'):
#        tablestr+="<table border=1>"
        i=1
        js=''
        for row in table.findAll('tr'):
            tablestr+="<tr>"
            j=1
            for tr in row.findAll('td'):
                textname=tr.text.encode("utf-8")
                if j<13:
                    if i==1 and textname:
                        js=1
                    if js:
                        if i>1:
        #                    print textname
                            tablestr+="<td >"
                            tablestr+=textname
                            tablestr+="</td>"
                    else:
                        if i>2:
        #                    print textname
                            tablestr+="<td >"
                            tablestr+=textname
                            tablestr+="</td>"
                j+=1
            i+=1
    return tablestr

def chart_luanma():
    gmt_created=time.strftime('%Y-%m-%d',time.localtime(time.time()))
    sql='select id,title,content from price where gmt_created>=%s and assist_type_id=53'
    cursor.execute(sql,[gmt_created])
    resultlist=cursor.fetchall()
    if resultlist:
        for result in resultlist:
            id=result[0]
            title=result[1]
            content=result[2]
            if u'最新报价' in title:
                if u'���' in content:
                    content1=chart_luanma1(content)
                        
                    content2='<tr><td > 合约名</td><td > 最新价</td><td > 涨跌</td><td > 持仓量</td><td > 成交量</td><td > 成交金额</td><td > 买卖价</td><td > 昨结算</td><td > 开盘</td><td > 最低</td><td > 最高</td><td > 现手</td></tr>'
#                    print ntitle
                    content3='<table>'+content2+content1+'</table>'
                    content3=content3.replace('<td >������ͼ��</td>','')
                    
                    sql3='update price set content=%s,is_checked=1 where id=%s'
                    cursor.execute(sql3,[content3,id])
                    conn.commit()
                else:
                    sql9='update price set is_checked=1 where id=%s'
                    cursor.execute(sql9,[id])
                    conn.commit()


def getpricetable5(content,numb1='',numb2=''):
    soup = BeautifulSoup(content)
    listall=[]
    listall2=[]
    for table in soup.findAll('table'):
        i=1
        for row in table.findAll('tr'):
            j=1
            for tr in row.findAll('td'):
                textname=tr.text
                if j == numb1:
                    textname=re.findall('[\d]+',textname)
                    if textname:
                        textname=int(textname[0])
                        listall.append(textname)
                elif j == numb2:
                    textname=re.findall('[\d]+',textname)
                    if textname:
                        textname=int(textname[0])
                        listall2.append(textname)
                j+=1
            i+=1
    
#    print listall
#    print listall2
    js=0
    listall3=[]
    for list in listall:
        numb=(list+listall2[js])/2
        listall3.append(numb)
        js=js+1
    return listall3

def getpricetable2(content,numb1='',numb2=''):
    soup = BeautifulSoup(content)
    tablestr=""
    for table in soup.findAll('table'):
        tablestr+="<table border=1>"
        i=1
        for row in table.findAll('tr'):
            tablestr+="<tr>"
            j=1
            for tr in row.findAll('td'):
                textname=tr.text
                if i==numb1 and j==numb2:
#                    tablestr+="<td >"
#                    tablestr+=textname
#                    tablestr+="</td>"
                    textname=re.findall('[\d]+',textname)
                    if textname:
                        textname=int(textname[0])
                    else:
                        textname=0
#                    print textname
#                    print numb1
#                    print numb2
                    return textname
                j+=1
            i+=1
    return tablestr

def getpricetable4(content):
    soup = BeautifulSoup(content)
    tablestr=""
    listall=[]
    js=0
    for table in soup.findAll('table'):
        tablestr+="<table border=1>"
        i=1
        for row in table.findAll('tr'):
            tablestr+="<tr>"
            j=1
            for tr in row.findAll('td'):
                textname=tr.text
                if j==5:
                    textname=re.findall('[\d]+',textname)
                    if textname:
                        textname=int(textname[0])
                        listall.append(textname)
                j+=1
            i+=1
    if listall:
        max_a = max(listall)
        return max_a


def getpricetable3(content):
    max_a=getpricetable4(content)
#    print max_a
    if max_a:
        soup = BeautifulSoup(content)
        for table in soup.findAll('table'):
            i=1
            for row in table.findAll('tr'):
                j=1
                for tr in row.findAll('td'):
                    textname=tr.text
                    if j==5:
                        textname=re.findall('[\d]+',textname)
                        if textname:
                            textname=int(textname[0])
                            if max_a==textname:
                                return i
                    j+=1
                i+=1




def getchart2(title,arg=''):
    gmt_created=time.strftime('%Y-%m-%d',time.localtime(time.time()))
    sql='select id,content from price where title=%s and gmt_created>=%s'
    cursor.execute(sql,[title,gmt_created])
    result=cursor.fetchone()
    if result:
#        print title
        id=result[0]
        content=result[1]
        if arg==1:
            listall=[]
            baojialist=re.findall('[\d]+',content)
            for baojia in baojialist:
                if len(baojia)>3:
                    listall.append(int(baojia))
            if len(listall)==6:
                return listall
#            print baojialist
        elif arg==2:
#            print id
            if '锌和铅' in title:
                listall=getpricetable5(content,3,4)
                listall2=[listall[4],listall[5],listall[0],listall[1]]
                return listall2
            if '不锈钢' in title:
                listall=getpricetable5(content,3,4)
                if '江浙沪' in title:
                    listall2=[listall[1],listall[0]]
                if '广东南海' in title:
                    listall2=[listall[3],listall[2]]
                return listall2
            if '铜' in title:
                listall=getpricetable5(content,3,4)
                if '江浙沪' in title:
                    listall2=[listall[0],listall[4]]
                if '广东南海' in title:
                    listall2=[listall[1],listall[16]]
                return listall2
        else:
            max_a=getpricetable3(content)
#            print max_a
            if '09:30' in title:
                price_a=getpricetable2(content,max_a,9)
            else:
                price_a=getpricetable2(content,max_a,2)
#            print price_a
            return price_a

def getchart(charttime,arg='',name=''):
    gmt_created=time.strftime('%Y-%m-%d',time.localtime(time.time()))
    gmt_created2=datetime.datetime.now()
    timedate=time.strftime('%m月%d日',time.localtime(time.time()))
    chart_info_id=0
    if arg==1:
        title_main='伦敦LME期货价格'
        chart_category_id=1
        sql4='select id from charts_info where chart_category_id=%s and gmt_date=%s'
        cursor.execute(sql4,[chart_category_id,gmt_created])
        result=cursor.fetchone()
        if result:
            chart_info_id=result[0]
        else:
            argument=[title_main,chart_category_id,gmt_created,gmt_created2,gmt_created2]
            sql='insert into charts_info(title,chart_category_id,gmt_date,gmt_created,gmt_modified) values(%s,%s,%s,%s,%s)'
            cursor.execute(sql,argument)
            conn.commit()
            sql2='select max(id) from charts_info'
            cursor.execute(sql2)
            result=cursor.fetchone()
            if result:
                chart_info_id=result[0]
#        print chart_info_id

        lundun_title=timedate+'伦敦LME基本金属最新报价'+charttime
        lundun_price=getchart2(lundun_title,arg)
#        print lundun_price
        category_id=6
        if lundun_price:
            for value in lundun_price:
                category_id=category_id+1
                
                sql5='select id from chart_data where chart_category_id=%s and value=%s and name=%s and chart_info_id=%s'
                cursor.execute(sql5,[category_id,value,name,chart_info_id])
                result5=cursor.fetchone()
                if not result5:
                    argument2=[category_id,value,name,gmt_created2,gmt_created2,chart_info_id]
                    sql3='insert into chart_data(chart_category_id,value,name,gmt_created,gmt_modified,chart_info_id ) values(%s,%s,%s,%s,%s,%s)'
                    cursor.execute(sql3,argument2)
                    conn.commit()
    elif arg==2:
        jzh_price=[]
        title_main=charttime
        if '锌/铅' in charttime:
            jzh_title=timedate+'江浙沪废锌和铅价格'
            chart_category_id=6
            category_idlist=[19,20,21,22]
            jzh_price=getchart2(jzh_title,arg)
        if '铜' in charttime:
            chart_category_id=28
            category_idlist=[13,14,15,16]
            jzh_title1=timedate+'江浙沪废铜价格'
            jzh_price1=getchart2(jzh_title1,arg)
            jzh_title2=timedate+'广东南海废铜价格'
            jzh_price2=getchart2(jzh_title2,arg)
            if jzh_price2 and jzh_price1:
                jzh_price=jzh_price1+jzh_price2
        if '不锈钢' in charttime:
            chart_category_id=29
            category_idlist=[17,18,23,24]
            jzh_title1=timedate+'江浙沪废不锈钢价格'
            jzh_price1=getchart2(jzh_title1,arg)
            jzh_title2=timedate+'广东南海废不锈钢价格'
            jzh_price2=getchart2(jzh_title2,arg)
            if jzh_price2 and jzh_price1:
                jzh_price=jzh_price2+jzh_price1
#        print jzh_price
        sql4='select id from charts_info where chart_category_id=%s and gmt_date=%s'
        cursor.execute(sql4,[chart_category_id,gmt_created])
        result=cursor.fetchone()
        if result:
            chart_info_id=result[0]
        else:
            argument=[title_main,chart_category_id,gmt_created,gmt_created2,gmt_created2]
            sql='insert into charts_info(title,chart_category_id,gmt_date,gmt_created,gmt_modified) values(%s,%s,%s,%s,%s)'
            cursor.execute(sql,argument)
            conn.commit()
            sql2='select max(id) from charts_info'
            cursor.execute(sql2)
            result=cursor.fetchone()
            if result:
                chart_info_id=result[0]
#        print chart_info_id
        if jzh_price:
            numb=0
            for value in jzh_price:
                category_id=category_idlist[numb]
                numb=numb+1
                sql5='select id from chart_data where chart_category_id=%s and value=%s and name=%s and chart_info_id=%s'
                cursor.execute(sql5,[category_id,value,'',chart_info_id])
                result5=cursor.fetchone()
                if not result5:
                    argument2=[category_id,value,name,gmt_created2,gmt_created2,chart_info_id]
                    sql3='insert into chart_data(chart_category_id,value,name,gmt_created,gmt_modified,chart_info_id ) values(%s,%s,%s,%s,%s,%s)'
                    cursor.execute(sql3,argument2)
                    conn.commit()
                    '''
                    '''
    else:            
        title=timedate+'沪铜最新报价'+charttime
        tong_price=getchart2(title)
#        print tong_price
        title=timedate+'沪铝最新报价'+charttime
        lv_price=getchart2(title)
#        print lv_price
        title=timedate+'沪锌最新报价'+charttime
        xin_price=getchart2(title)
#        print xin_price
        title=timedate+'沪钢最新报价'+charttime
        gang_price=getchart2(title)
#        print gang_price
        listall=[tong_price,lv_price,xin_price,gang_price]
        title_main='期货价格'
        chart_category_id=30
        
        if tong_price and lv_price and xin_price and gang_price:
#            print chart_category_id 
#            print listall
    
            sql4='select id from charts_info where chart_category_id=%s and gmt_created=%s'
            cursor.execute(sql4,[chart_category_id,gmt_created+' '+charttime])
            result=cursor.fetchone()
            if not result:
                argument=[title_main,chart_category_id,gmt_created,gmt_created+' '+charttime,gmt_created2]
                sql='insert into charts_info(title,chart_category_id,gmt_date,gmt_created,gmt_modified) values(%s,%s,%s,%s,%s)'
                cursor.execute(sql,argument)
                conn.commit()
                sql2='select max(id) from charts_info'
                cursor.execute(sql2)
                result=cursor.fetchone()
                if result:
                    chart_info_id=result[0]
#                    print chart_info_id
                    category_id=30
                    for value in listall:
                        category_id=category_id+1
#                        print value
#                        print category_id
                        sql5='select id from chart_data where chart_category_id=%s and value=%s and chart_info_id=%s'
                        cursor.execute(sql5,[category_id,value,chart_info_id])
                        result5=cursor.fetchone()
                        if not result5:
                            argument2=[category_id,value,'',gmt_created2,gmt_created2,chart_info_id]
                            sql3='insert into chart_data(chart_category_id,value,name,gmt_created,gmt_modified,chart_info_id) values(%s,%s,%s,%s,%s,%s)'
                            cursor.execute(sql3,argument2)
                            conn.commit()
                            '''
                            '''

def chartall():
    chart_luanma()
    timeall=time.strftime('%H:%M:%S',time.localtime(time.time()))
    listall=[]
#    listall.append('废金属走势图')
    if timeall>'09:30:00' and timeall<='09:50:00':
        getchart('09:30')
        listall.append(5) 
    if timeall>'11:30:00' and timeall<='11:50:00':
        getchart('10:30')
        listall.append(22)
    if timeall>'14:30:00' and timeall<='14:50:00':
        getchart('13:30')
        listall.append(24)
    if timeall>'15:30:00' and timeall<='15:50:00':
        getchart('14:30')
        listall.append(26)
    if timeall>'09:50:00' and timeall<='10:10:00':
        getchart('9:50',1,'金属价格（10：00）')
        listall.append(6)
    if timeall>'11:50:00' and timeall<='12:10:00':
        getchart('09:30')
        getchart('11:50',1,'金属价格（11：40）')
        listall.append(23)
    if timeall>'14:50:00' and timeall<='15:10:00':
        getchart('14:50',1,'金属价格（14：55）')
        listall.append(25)
    if timeall>='14:30:00':
        getchart('江浙沪废锌/铅价格',2)
        getchart('废不锈钢价格',2)
        getchart('废铜价格',2)
        listall.append(27)
        listall.append(28)
        listall.append(29)
    return listall

if __name__=="__main__":
    chartall()