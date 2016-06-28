#-*- coding:utf-8 -*-
from zz91conn import database_other
from zz91conn_read import database_other_read
import time

class otherdb:
    #----初始化数据库连接
    def __init__(self):
        self.conn_other=database_other()
        self.cursor_other=self.conn_other.cursor()
        self.conn_other_read=database_other_read()
        self.cursor_other_read=self.conn_other_read.cursor()
    #----更新到数据库
    def updatetodb(self,sql,argument):
        self.cursor_other.execute(sql,argument)
        self.cursor_other.execute('SELECT last_insert_id()')
        result=self.cursor_other.fetchone()
        self.conn_other.commit()
        if result:
            return result
    #----查询所有数据
    def fetchalldb(self,sql,argument=''):
        if argument:
            self.cursor_other_read.execute(sql,argument)
        else:
            self.cursor_other_read.execute(sql)
        resultlist=self.cursor_other_read.fetchall()
        if resultlist:
            return resultlist
        else:
            return []
    #----查询一条数据
    def fetchonedb(self,sql,argument=''):
        if argument:
            self.cursor_other_read.execute(sql,argument)
        else:
            self.cursor_other_read.execute(sql)
        result=self.cursor_other_read.fetchone()
        if result:
            return result
    #----查询一条总数
    def fetchnumberdb(self,sql,argument=''):
        if argument:
            self.cursor_other_read.execute(sql,argument)
        else:
            self.cursor_other_read.execute(sql)
        result=self.cursor_other_read.fetchone()
        if result:
            return result[0]
        else:
            return 0
    #----关闭连接
    def closedb(self):
        self.conn_other.close()
        self.conn_other_read.close()
    #----数据事物回滚
    def rollbackdb(self):
        self.conn_other.rollback()
        self.conn_other_read.rollback()
    #----查询号码的归属地
    def getphonedetail(self,numb):
        province=''
        city=''
        if numb[0]=='0':
            parg=numb[:4]
            parg2=numb[:3]
            sql='select id,province,city from tel_guoneinumb where numb=%s'
            result=self.fetchonedb(sql,[parg])
            if result:
                province=result[1]
                city=result[2]
            else:
                result2=self.fetchonedb(sql,[parg2])
                if result2:
                    province=result2[1]
                    city=result2[2]
        else:
            marg=numb[:7]
            sql='select id,province,city from mobile_number where numb=%s'
            result=self.fetchonedb(sql,[marg])
            if result:
                province=result[1]
                city=result[2]
        return {'province':province,'city':city}

class pycmsdb:
    #----初始化数据库连接
    def __init__(self):
        from zz91conn import database_pycms
        self.conn_other=database_pycms()
        self.cursor_other=self.conn_other.cursor()
        """
        try:
            self.conn_other.ping()
        except Exception,e:
            while True:
                try:
                    self.conn_other=database_pycms()
                except Exception,e:
                    time.sleep(1)
                    continue
            self.cursor_other=self.conn_other.cursor()
        """
    #----更新到数据库
    def updatetodb(self,sql,argument):
        self.cursor_other.execute(sql,argument)
        self.conn_other.commit()
    #----查询所有数据
    def fetchalldb(self,sql,argument=''):
        if argument:
            self.cursor_other.execute(sql,argument)
        else:
            self.cursor_other.execute(sql)
        resultlist=self.cursor_other.fetchall()
        if resultlist:
            return resultlist
        else:
            return []
    #----查询一条数据
    def fetchonedb(self,sql,argument=''):
        if argument:
            self.cursor_other.execute(sql,argument)
        else:
            self.cursor_other.execute(sql)
        result=self.cursor_other.fetchone()
        if result:
            return result
    #----查询一条总数
    def fetchnumberdb(self,sql,argument=''):
        if argument:
            self.cursor_other.execute(sql,argument)
        else:
            self.cursor_other.execute(sql)
        result=self.cursor_other.fetchone()
        if result:
            return result[0]
        else:
            return 0