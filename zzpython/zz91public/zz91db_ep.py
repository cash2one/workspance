#-*- coding:utf-8 -*-
from zz91conn import database_huanbao

#---ast数据库
class adshuanbao:
    #---初始化数据库连接
    def __init__(self):
        self.conn_comp=database_huanbao()
        self.cursor_comp=self.conn_comp.cursor()
    #----更新到数据库
    def updatetodb(self,sql,argument):
        self.cursor_comp.execute(sql,argument)
        self.conn_comp.commit()
    #----查询所有数据
    def fetchalldb(self,sql,argument=''):
        if argument:
            self.cursor_comp.execute(sql,argument)
        else:
            self.cursor_comp.execute(sql)
        resultlist=self.cursor_comp.fetchall()
        if resultlist:
            return resultlist
        else:
            return []
    #----查询一条数据
    def fetchonedb(self,sql,argument=''):
        if argument:
            self.cursor_comp.execute(sql,argument)
        else:
            self.cursor_comp.execute(sql)
        result=self.cursor_comp.fetchone()
        if result:
            return result
    #----查询一条总数
    def fetchnumberdb(self,sql,argument=''):
        if argument:
            self.cursor_comp.execute(sql,argument)
        else:
            self.cursor_comp.execute(sql)
        result=self.cursor_comp_read.fetchone()
        if result:
            return result[0]
        else:
            return 0
    #----关闭连接
    def closedb(self):
        self.conn_comp.close()
    #----数据事物回滚
    def rollbackdb(self):
        self.conn_comp.rollback()
        
        