##-*- coding:utf-8 -*- 
from public import *
from zz91conn import database_news,database_sex
#
def database():
    #conn = MySQLdb.connect(host=host, user='zz91news', passwd='4ReLhW3QLyaaECzU',db='zz91news',charset='utf8')
    conn=database_news()
    return conn

def databasesex():
    conn=database_sex()
    #conn = MySQLdb.connect(host=host, user='zjfriend', passwd='PqKYuNNqW9MZFMUc',db='zjfriend',charset='utf8')
    return conn

def close(conn,cursor):
    cursor.close()
    conn.close()
    
def npath():
    return "/mnt/phpcode/zz91news/getnews/"

def sexpath():
    return "/mnt/phpcode/zz91news/getnews/"