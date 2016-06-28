#!/usr/bin/env python   
#coding=utf-8   
  
import MySQLdb   
import pymssql
import sys
import codecs
import time,datetime
import struct
import os
try:
    import cPickle as pickle
except ImportError:
    import pickle
reload(sys)
sys.setdefaultencoding('utf-8')

#---数据库连接和配置
parentpath=os.path.abspath('../../')
nowpath=os.path.abspath('.')
execfile(parentpath+"/conn.py") 
execfile(parentpath+"/inc.py")
#--------------------------------------
def getsearcher():
	purl = '/usr/data/offerlist/'
	if not (os.path.exists(purl+"searcher.txt")):
		f = open(purl+"searcher.txt",'w')
		f.write('offersearch_new')
	f = open(purl+"searcher.txt",'r')
	return f.read()
	f.close()
def updatesearcher(value):
	purl = '/usr/data/offerlist/'
	f = open(purl+"searcher.txt",'w')
	f.write(value)
	f = open(purl+"searcher.txt",'r')
	f.close()
print getsearcher()
nowsearcher=getsearcher()
if (nowsearcher=='offersearch_new_b'):
	print os.system("/usr/local/web/coreseek/bin/indexer --config /usr/apps/coreseek/etc/huzhu_mysql.conf --rotate offersearch_new_b")
	updatesearcher('offersearch_new')
else:
	print os.system("/usr/local/web/coreseek/bin/indexer --config /usr/apps/coreseek/etc/huzhu_mysql.conf --rotate offersearch_new_b")
	updatesearcher('offersearch_new_b')