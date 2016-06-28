# -*- coding:utf-8 -*-
import datetime
import time
#时间撮转化为日期
def timestamp_to_date(timestamp):
    ltime=time.localtime(timestamp)
    timeStr=time.strftime("%Y-%m-%d %H:%M:%S", ltime)
    return timeStr
#格式化字符串
def formattime(value,flag=''):
    if value:
        if (flag==1):
            return value.strftime('%Y-%m-%d')
        else:
            return value.strftime('%Y-%m-%d %H:%M:%S')
    else:
        return ''