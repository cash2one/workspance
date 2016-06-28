#-*- coding:utf-8 -*-
import re,datetime,time
from zz91tools import int_to_str,formattime,getnub_tostr,str_to_date
from zz91db_ast import companydb

class payorder:
    def __init__(self):
        self.dbc=companydb()
    def getpay_orderlist(self,frompageCount,limitNum):
        sql1='select count(0) from pay_order'
        sql='select id,buyer_email,buyer_id,is_success,notify_id,notify_time,out_trade_no,subject,total_fee,trade_no,trade_status,contact,mobile from pay_order'
        sql+=' order by id desc limit '+str(frompageCount)+','+str(limitNum)
        count=self.dbc.fetchnumberdb(sql1)
        resultlist=self.dbc.fetchalldb(sql)
        listall=[]
        for result in resultlist:
            id=result[0]
            buyer_email=result[1]
            buyer_id=result[2]
            is_success=result[3]
            notify_id=result[4]
            notify_time=result[5]
            out_trade_no=result[6]
            subject=result[7]
            total_fee=result[8]
            trade_no=result[9]
            trade_status=result[10]
            contact=result[11]
            mobile=result[12]
            list={'id':id,'buyer_email':buyer_email,'buyer_id':buyer_id,'is_success':is_success,'notify_id':notify_id,'notify_time':notify_time,'out_trade_no':out_trade_no,'subject':subject,'total_fee':total_fee,'trade_no':trade_no,'trade_status':trade_status,'contact':contact,'mobile':mobile}
            listall.append(list)
        return {'list':listall,'count':count}
