import os
import fileinput
import re,time
import sys
import urllib
import json
import pymongo
def savelog(logfile,website,dbtable):
    '''使用的nginx默认日志格式$remote_addr - $remote_user 
    [$time_local] "$request" $status $body_bytes_sent 
    "$http_referer" "$http_user_agent" "$http_x_forwarded_for"'''
    
    
    
    #日志分析正则表达式
    #203.208.60.230
    ipP = r"?P<ip>[\d.]*"
    
    '''以[开始,除[]以外的任意字符 防止匹配上下个[]项目
    (也可以使用非贪婪匹配*?) 不在中括号里的.可以匹配换行外的任意字符
    *这样地重复是"贪婪的“ 表达式引擎会试着重复尽可能多的次数。#以]结束'''
    #[21/Jan/2011:15:04:41 +0800]
    timeP = r"""?P<time>\[[^\[\]]*\]"""
    
    '''以"开始, #除双引号以外的任意字符 防止匹配上下个""项目
    (也可以使用非贪婪匹配*?),#以"结束'''
    #"GET /EntpShop.do?method=view&shop_id=391796 HTTP/1.1"
    #"GET /EntpShop.do?method=view&shop_id=391796 HTTP/1.1"
    requestP = r"""?P<request>\"[^\"]*\""""
    
    statusP = r"?P<status>\d+"
    
    bodyBytesSentP = r"?P<bodyByteSent>\d+"
    
    '''以"开始, 除双引号以外的任意字符 防止匹配上下个""项目\
    (也可以使用非贪婪匹配*?),#以"结束'''
    #"http://test.myweb.com/myAction.do?method=view&mod_id=&id=1346"
    referP = r"""?P<refer>\"[^\"]*\""""
    
    '''以"开始, 除双引号以外的任意字符 防止匹配上下个""项目
    (也可以使用非贪婪匹配*?),以"结束'''
    '''"Mozilla/5.0 (compatible; Googlebot/2.1; 
    +http://www.google.com/bot.html)"\''''
    userAgentP = r"""?P<userAgent>\"[^\"]*\""""
    
    '''以(开始, 除双引号以外的任意字符 防止匹配上下个()
    项目(也可以使用非贪婪匹配*?),以"结束'''
    '''(compatible; Googlebot/2.1; 
    +http://www.google.com/bot.html)"\''''
    userSystems = re.compile(r'\([^\(\)]*\)')
    
    '''以"开始，除双引号以外的任意字符防止匹配上下个""项目
    (也可以使用非贪婪匹配*?),以"结束'''
    userlius = re.compile(r'[^\)]*\"')
    
    #原理：主要通过空格和-来区分各不同项目，各项目内部写各自的匹配表达式
    nginxLogPattern = re.compile(r"(%s)\ -\ -\ (%s)\ (%s)\ (%s)\ (%s)\ (%s)\ (%s)" %(ipP, timeP, requestP, statusP, bodyBytesSentP,referP, userAgentP), re.VERBOSE)
    
    #导入mangodb
    conn = pymongo.MongoClient("182.254.148.31",27017)
    #-----连接mydb库
    db = conn.zz91log
    #-----链接表
    collection=db[dbtable]
    #数据库连接信息
    #conn=MySQLdb.connect(host='192.168.110.118', user='kang', passwd='astozz91jiubao',db='zzlog',charset='utf8')
    #cur=conn.cursor()
    #sql = "INSERT INTO nginx_log(ipP, timeP, requestP, statusP, bodyBytesSentP, referP, userAgentP,uesreP,syeP,province,website) VALUES \
    #(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
    def ip_location(ip):
        url = "http://ip.taobao.com/service/getIpInfo.php?ip="
        data = urllib.urlopen(url + ip).read()
        datadict=json.loads(data)
        for oneinfo in datadict:
            if "code" == oneinfo:
                if datadict[oneinfo] == 0:
                    return datadict["data"]["region"] + datadict["data"]["city"]
    while True:
        line = logfile.readline()
        if not line:break
        matchs = nginxLogPattern.match(line)
        value=[]
        if matchs != None:
            allGroup = matchs.groups()
            ip = allGroup[0]
            time1 = allGroup[1]
            request = allGroup[2]
            status = allGroup[3]
            bodyBytesSent = allGroup[4]
            refer = allGroup[5]
            userAgent = allGroup[6]
            
            Time = time1.replace('T',' ')[1:-7]
            Time=time.strptime(Time,"%d/%b/%Y:%H:%M:%S")
            Time=time.strftime("%Y-%m-%d %H:%M:%S", Time) 
            #Time=time.mktime(Time)
            url=""
            province=""
            if request:
                requestlist=str(request).replace('"','').split(' ')
                if len(requestlist)>1:
                    url=website+requestlist[1]
            
            if len(userAgent) > 20:
                userinfo = userAgent.split(' ')
                userkel =  userinfo[0]
                try:
                    usersystem = userSystems.findall(userAgent)
                    usersystem = usersystem[0]
                    userliu = userlius.findall(userAgent)
                    
    
                    value = {'ip':ip,'Time':Time,'url':url,'request':request,'status':status, \
    'bodyBytesSent':bodyBytesSent,'refer':refer,'userkel':userkel,'usersystem':usersystem,'userliu':userliu[1],'province':province,'website':website}
                    #conn.commit()
                    #print value
    
                except IndexError:
                    userinfo = userAgent
                    value = {'ip':ip,'Time':Time,'url':url,'request':request,'status':status, \
    'bodyBytesSent':bodyBytesSent,'refer':refer,'userkel':userinfo,'usersystem':'','userliu':'','province':province,'website':website}
                    
            else:
                useraa = userAgent
                value = {'ip':ip,'Time':Time,'url':url,'request':request,'status':status, \
    'bodyBytesSent':bodyBytesSent,'refer':refer,'userkel':useraa,'usersystem':'','userliu':'','province':province,'website':website}
                
        try:
            if status in ['404','500']:
                collection.insert(value)
            else:
                print None
        except:
            print 'err'