def database():
    conn = MySQLdb.connect(host='10.171.223.228', user='bsteelmanagement', passwd='b7TqMWqfr9nxQbEZ',db='bsteelmanagement',charset='utf8')
    return conn
    #conn = MySQLdb.connect(host='192.168.2.10', user='seocompany', passwd='Gs8FXT6szWNqDhG8',db='seocompany',charset='utf8')
    #cursor = conn.cursor()
    #return cursor
    

