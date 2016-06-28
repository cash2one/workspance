#!/usr/bin/env python   
#coding=utf-8   
import sys
import codecs
import datetime
import struct
import os

reload(sys)
#type = sys.getfilesystemencoding()
sys.setdefaultencoding('utf-8')
#parentpath=os.path.abspath('../')
#nowpath=os.path.abspath('.')
nowpath="/usr/apps/python"
execfile(nowpath+"/conn.py")
execfile(nowpath+"/inc.py")

sql="select com_id,personid from v_tempaaa where com_id not in (select com_id from crm_assign)"
cursor.execute(sql)
result=cursor.fetchall()
for list in result:
    sqlc="select com_id from crm_assign where com_id="+str(list[0])
    cursor.execute(sqlc)
    resultone=cursor.fetchone()
    if not resultone:
        sqlu="insert into crm_assign(com_id,personid) values("+str(list[0])+","+str(list[1])+")"
        cursor.execute(sqlu)
        conn.commit()
        print list[0]
    