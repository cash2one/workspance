import MySQLdb

def database():
    #conn = MySQLdb.connect(host='192.168.2.40', user='root', passwd='10534jun',db='feiliao123',charset='utf8')
    #return conn
    conn = MySQLdb.connect(host='192.168.110.130', user='zzother', passwd='jmLt9zGdXa59vrLM',db='zzother',charset='utf8')
    return conn
    

