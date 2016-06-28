#-*- coding:utf-8 -*- 
from conn import database,close

conn=database()
cursor=conn.cursor()
sql="delete from dede_arctiny where id>1"
cursor.execute(sql)
sql="delete from dede_archives where id>1"
cursor.execute(sql)
sql="delete from dede_addonarticle where aid>1"
cursor.execute(sql)
close(conn,cursor)
