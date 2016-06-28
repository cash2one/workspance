#conn = MySQLdb.connect(host='192.168.110.130', user='astoweb', passwd='q3W6rzTMtKfxJMXB',db='astoweb',charset='utf8')   
#cursor = conn.cursor()
config={'host':'7006.zz91server.com', 'user':'astoweb', 'passwd':'q3W6rzTMtKfxJMXB','db':'astoweb','charset':'utf8','connect_timeout':10,'compress':True}
conn = MySQLdb.connect(host=config['host'], user=config['user'], passwd=config['passwd'],db=config['db'],charset=config['charset'])   
cursor = conn.cursor()
def closeconn():
	cursor.close()
def autoReconnect():
    global cursor
    global conn
    try:
        conn.ping()
    except Exception,e:
        while True:
            try:
                conn = MySQLdb.connect(host=config['host'], user=config['user'], passwd=config['passwd'],db=config['db'],charset=config['charset'])   
                break
            except Exception,e:
                time.sleep(2)
                continue
        cursor=conn.cursor()
    return cursor