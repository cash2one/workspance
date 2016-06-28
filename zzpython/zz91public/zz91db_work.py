#-*- coding:utf-8 -*-
from zz91conn import database_work
from zz91conn_read import database_work_read

#---work数据库
class workdb:
    #---初始化数据库连接
    def __init__(self):
        self.conn=database_work()
        self.cursor=self.conn.cursor()
        self.conn_read=database_work_read()
        self.cursor_read=self.conn_read.cursor()
    #----更新到数据库
    def updatetodb(self,sql,argument):
        self.cursor.execute(sql,argument)
        self.conn.commit()
    #----查询所有数据
    def fetchalldb(self,sql,argument=''):
        if argument:
            self.cursor_read.execute(sql,argument)
        else:
            self.cursor_read.execute(sql)
        resultlist=self.cursor_read.fetchall()
        if resultlist:
            return resultlist
        else:
            return []
    #----查询一条数据
    def fetchonedb(self,sql,argument=''):
        if argument:
            self.cursor_read.execute(sql,argument)
        else:
            self.cursor_read.execute(sql)
        result=self.cursor_read.fetchone()
        if result:
            return result
    #----查询所有数据(主数据库)
    def fetchalldbmain(self,sql,argument=''):
        if argument:
            self.cursor.execute(sql,argument)
        else:
            self.cursor.execute(sql)
        resultlist=self.cursor.fetchall()
        if resultlist:
            return resultlist
        else:
            return []
    #----查询一条数据(主数据库)
    def fetchonedbmain(self,sql,argument=''):
        if argument:
            self.cursor.execute(sql,argument)
        else:
            self.cursor.execute(sql)
        result=self.cursor.fetchone()
        if result:
            return result
    #----查询一条总数
    def fetchnumberdb(self,sql,argument=''):
        if argument:
            self.cursor_read.execute(sql,argument)
        else:
            self.cursor_read.execute(sql)
        result=self.cursor_read.fetchone()
        if result:
            return result[0]
        else:
            return 0
    #----关闭连接
    def closedb(self):
        self.conn.close()
        self.conn_read.close()
    #----数据事物回滚
    def rollbackdb(self):
        self.conn.rollback()
        self.conn_read.rollback()