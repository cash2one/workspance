#-*- coding:utf-8 -*-
import os
import fileinput
import re,time
import sys
import MySQLdb
import urllib
import json
execfile("/mnt/python/zz91_task/log/function.py")
reload(sys) 
sys.setdefaultencoding('utf-8')

logfile=open("/mnt/var/log/nginx/app.access.log")
website="app.zz91.com"
savelog(logfile,website)

