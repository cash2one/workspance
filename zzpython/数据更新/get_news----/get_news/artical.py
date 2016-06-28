from conn import database,close
from bs4 import BeautifulSoup
import time

time1=time.time()


#id=27627
conn=database()
cursor=conn.cursor()
sql='select aid,body from dede_addonarticle where aid>0'
#cursor.execute(sql,[id])
cursor.execute(sql)
resultlist=cursor.fetchall()
if resultlist:
    listall=[]
    for result in resultlist:
        id=result[0]
        content=result[1]
        soup=BeautifulSoup(content)
        description=soup.get_text()
        sql="update dede_archives set description=%s where id=%s"
        cursor.execute(sql,[description,id])
        conn.commit()
close(conn,cursor)
