import MySQLdb
import pymongo

def database_news():
    conn_news = MySQLdb.connect(host='192.168.110.2', user='zz91news', passwd='4ReLhW3QLyaaECzU',db='zz91news',charset='utf8')
    return conn_news

def database_sex():
    conn_news = MySQLdb.connect(host='192.168.110.2', user='zjfriend', passwd='PqKYuNNqW9MZFMUc',db='zjfriend',charset='utf8')
    return conn_news

def database_huanbao():
    conn_huanbao = MySQLdb.connect(host='192.168.110.2', user='jiaolj', passwd='jiaolj345',db='ep',charset='utf8')
    return conn_huanbao

def database_other():
    conn_other = MySQLdb.connect(host='122.225.11.195', port=7005, user='zzother', passwd='jmLt9zGdXa59vrLM',db='zzother',charset='utf8')
    return conn_other

def database_pycms():
    conn_pycms = MySQLdb.connect(host='192.168.110.130', user='jiaolj', passwd='jiaolj123',db='webcms',charset='utf8')
    return conn_pycms

def database_comp():
    conn_comp=MySQLdb.connect(host='192.168.110.118', user='ast', passwd='astozz91jiubao',db='ast',charset='utf8')
    return conn_comp

def database_tags():
    conn_comp=MySQLdb.connect(host='192.168.110.118', user='ast', passwd='astozz91jiubao',db='zztags',charset='utf8')
    return conn_comp

def database_ads():
    conn_ads = MySQLdb.connect(host='192.168.110.118', user='zzads', passwd='aJuUVbChYJ57t2SX',db='zzads',charset='utf8')
    return conn_ads

def database_log():
    conn_log = MySQLdb.connect(host='192.168.110.118', user='ast', passwd='astozz91jiubao',db='zzlog',charset='utf8')
    return conn_log

def database_aqsiq():
    conn_aqsiq = MySQLdb.connect(host='192.168.110.130', user='aqsiq', passwd='6JGnmy8mEJv7yBhd',db='aqsiq',charset='utf8')
    return conn_aqsiq

def database_sms():
    conn_sms = MySQLdb.connect(host='192.168.110.130', user='reborn', passwd='vfBNMAp9VpNXAWwV',db='zzsms',charset='utf8')
    return conn_sms

def database_work():
    conn_comp=MySQLdb.connect(host='192.168.110.118', user='ast', passwd='astozz91jiubao',db='ast',charset='utf8')
    return conn_comp

def database_mongodb():
    #导入mangodb
    conn = pymongo.Connection("192.168.110.112",27017)
    #-----连接mydb库
    db = conn.applog
    return db