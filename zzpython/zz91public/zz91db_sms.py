#-*- coding:utf-8 -*-
from zz91conn import database_sms
from zz91conn_read import database_sms_read

#---ast数据库
class smsdb:
    #---初始化数据库连接
    def __init__(self):
        self.conn_sms=database_sms()
        self.cursor_sms=self.conn_sms.cursor()
        self.conn_sms_read=database_sms_read()
        self.cursor_sms_read=self.conn_sms_read.cursor()
    #----更新到数据库
    def updatetodb(self,sql,argument):
        self.cursor_sms.execute(sql,argument)
        self.conn_sms.commit()
    #----查询所有数据
    def fetchalldb(self,sql,argument=''):
        if argument:
            self.cursor_sms_read.execute(sql,argument)
        else:
            self.cursor_sms_read.execute(sql)
        resultlist=self.cursor_sms_read.fetchall()
        if resultlist:
            return resultlist
        else:
            return []
    #----查询一条数据
    def fetchonedb(self,sql,argument=''):
        if argument:
            self.cursor_sms_read.execute(sql,argument)
        else:
            self.cursor_sms_read.execute(sql)
        result=self.cursor_sms_read.fetchone()
        if result:
            return result
    #----查询一条数据
    def fetchonedbmain(self,sql,argument=''):
        if argument:
            self.cursor_sms.execute(sql,argument)
        else:
            self.cursor_sms.execute(sql)
        result=self.cursor_sms.fetchone()
        if result:
            return result
    #----查询一条总数
    def fetchnumberdb(self,sql,argument=''):
        if argument:
            self.cursor_sms_read.execute(sql,argument)
        else:
            self.cursor_sms_read.execute(sql)
        result=self.cursor_sms_read.fetchone()
        if result:
            return result[0]
        else:
            return 0
    #----关闭连接
    def closedb(self):
        self.conn_sms.close()
        self.conn_sms_read.close()
    #----数据事物回滚
    def rollbackdb(self):
        self.conn_sms.rollback()
        self.conn_sms_read.rollback()
        