#-*- coding:utf-8 -*-
import os
import fileinput
import re,time
import sys
import MySQLdb
import urllib
import json
from function import savelog
reload(sys) 
sys.setdefaultencoding('utf-8')
def getlog(logname,website):
    logfile=open("/var/log/nginx/"+logname)
    dbtable="wwwlog"
    savelog(logfile,website,dbtable)
    os.system("mv  /var/log/nginx/"+logname+" /var/log/nginx/"+logname+"_`date +%Y%m%d`.log")
    os.system("[ ! -f /var/run/nginx.pid ] || kill -USR1 `cat /var/run/nginx.pid`")
    os.system("rm /var/log/nginx/"+logname+"_`date +%Y%m%d`.log")

getlog('m.zz91.access.log','m.zz91.com')
getlog('tags.zz91.access.log','tags.zz91.com')
getlog('daohang.zz91.access.log','daohang.zz91.com')
getlog('mobileapp.access.log','app.zz91.com')
getlog('pyapp.access.log','pyapp.zz91.com')
getlog('news.zz91.access.log','news.zz91.com')
getlog('www.zz91.cp.log','www.zz91.com/cp')
getlog('www.zz91.index.log','www.zz91.com/zz91index')
getlog('www.zz91.ppc.log','www.zz91.com/ppc')
getlog('myrc.zz91.access.log','myrc.zz91.com')
getlog('yang.zz91.access.log','yang.zz91.access.log')
getlog('y.zz91.access.log','y.zz91.com')
getlog('company.zz91.access.log','company.zz91.com')
getlog('trade.zz91.access.log','trade.zz91.com')
getlog('esite.zz91.access.log','esite.zz91.com')
getlog('huzhu.zz91.access.log','huzhu.zz91.com')
getlog('caigou.zz91.access.log','caigou.zz91.com')
getlog('zhanhui.zz91.access.log','zhanhui.zz91.com')

getlog('access.log','www.zz91.com')
getlog('error.log','www.zz91.com')








