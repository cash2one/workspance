import MySQLdb,os,time,datetime,shutil
import pymssql
connmy = MySQLdb.connect(host='192.168.10.3', user='root', passwd='zj88friend',db='freeiris2',charset='utf8')     
cursormy = connmy.cursor()
conn=pymssql.connect(host=r'192.168.2.2',trusted=False,user='rcu_crm',password='fdf@$@#dfdf9780@#1.kdsfd',database='rcu_crm',charset=None)
cursor=conn.cursor()
def formattime(value,flag):
    if value:
        if (flag==1):
            return value.strftime( '%-Y-%-m-%-d')
        if (flag==2):
            return value.strftime( '%-Y-%-m-%-d %-H:%-M')
        if (flag==3):
            return value.strftime( '%Y-%-m-%d &nbsp;%-H:%M')
        else:
            return value.strftime( '%-Y-%-m-%-d %-H:%-M:%-S')
    else:
        return ''
def formattime(value,flag):
    if value:
        if (flag==1):
            return value.strftime( '%Y-%m')
        if (flag==2):
            return value.strftime( '%d')
        else:
            return value.strftime( '%-Y-%-m-%-d %-H:%-M:%-S')
    else:
        return ''
#将字符串转换成datetime类型  
def strtodatetime(datestr,format):       
    return datetime.datetime.strptime(datestr,format)


fileml="/var/spool/asterisk/monitor/1/"
moveml="/var/spool/asterisk/monitor/"
#and DATEDIFF(CURDATE(),cretime)<=2
sqlr="select cretime,filename,id,extname,associate from voicefiles where label='sysautomon' and DATEDIFF(CURDATE(),cretime)<=1 order by id asc"
cursormy.execute(sqlr)
flist=cursormy.fetchall()
if flist:
    for list in flist:
        cretime=list[0]
        filename=list[1]
        mlname=formattime(cretime,1)+"/"+formattime(cretime,2)+"/"
        id=list[2]
        extname=list[3]
        associate=list[4]
        print id
        folder=moveml+mlname
        if not os.path.exists(folder):
            os.makedirs(folder)
        relfolder=mlname
        sqlc="update voicefiles set folder=%s where id=%s"
        cursormy.execute(sqlc,[relfolder,id])
        if os.path.exists(fileml+filename+"."+extname):
            shutil.move(fileml+filename+"."+extname, folder+filename+"."+extname)
        
        sqli="update record_list set ml='"+relfolder+"' where monitorfile='"+str(filename)+"'"
        cursor.execute(sqli);
        conn.commit()