#-*- coding:utf-8 -*-
#----获得手机钱包充值客户的手机号列表
from public import *
from zz91db_ast import companydb

dbc=companydb()

def getmobilelist():
    sql='select mobile from pay_order where paytype="qianbao"'
    resultlist=dbc.fetchalldb(sql)
    set1=set()
    for result in resultlist:
        mobile=result[0]
#        print mobile
        set1.add(mobile)
    sql1='select company_id from pay_mobileWallet where ftype=6'
    sql2='select mobile from company_account where company_id=%s'
    resultlist1=dbc.fetchalldb(sql1)
    for result1 in resultlist1:
        company_id=result1[0]
        result2=dbc.fetchonedb(sql2,company_id)
        if result2:
            mobile1=result2[0]
            mobile1=mobile1.strip()
            mobile1=mobile1[0:11]
            if mobile1.isdigit()==True:
                mobile1=int(mobile1)
#                print mobile1
                set1.add(mobile1)
    return set1

#----列表
listall=getmobilelist()
print len(listall)

#----输出为字符串
mobiletxt=''
for list in listall:
    mobiletxt+=str(list)+','
print mobiletxt
