#-*- coding:utf-8 -*-
from zz91conn import database_sex

class newsdb:
    #----初始化数据库连接
    def __init__(self):
        self.conn_news=database_sex()
        self.cursor_news=self.conn_news.cursor()
    #----更新到数据库
    def updatetodb(self,sql,argument):
        self.cursor_news.execute(sql,argument)
        self.conn_news.commit()
    #----查询所有数据
    def fetchalldb(self,sql,argument=''):
        if argument:
            self.cursor_news.execute(sql,argument)
        else:
            self.cursor_news.execute(sql)
        resultlist=self.cursor_news.fetchall()
        if resultlist:
            return resultlist
        else:
            return []
    #----查询一条数据
    def fetchonedb(self,sql,argument=''):
        if argument:
            self.cursor_news.execute(sql,argument)
        else:
            self.cursor_news.execute(sql)
        result=self.cursor_news.fetchone()
        if result:
            return result
    #----查询一条总数
    def fetchnumberdb(self,sql,argument=''):
        if argument:
            self.cursor_news.execute(sql,argument)
        else:
            self.cursor_news.execute(sql)
        result=self.cursor_news.fetchone()
        if result:
            return result[0]
        else:
            return 0
    #----关闭连接
    def closedb(self):
        self.conn_news.close()
    #----数据事物回滚
    def rollbackdb(self):
        self.conn_news.rollback()
        #--获取资讯url
    def get_newstype(self,id):
        sql='select typeid,typeid2 from dede_archives where id=%s'
        self.cursor_news.execute(sql,[id])
        result=self.cursor_news.fetchone()
        if result:
            typeid=result[0]
            typeid2=result[1]
            sql2='select typename,keywords from dede_arctype where id=%s'
            self.cursor_news.execute(sql2,[typeid])
            result2=self.cursor_news.fetchone()
            if result2:
                list={'typename':result2[0],'url':result2[1],'typeid':typeid,'typeid2':typeid2,'url2':''}
                if typeid2!='0':
                    sql3='select keywords from dede_arctype where id=%s'
                    self.cursor_news.execute(sql3,[typeid2])
                    result3=self.cursor_news.fetchone()
                    if result3:
                        list['url2']=result3[0]
                return list