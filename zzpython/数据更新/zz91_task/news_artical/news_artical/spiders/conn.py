import MySQLdb,time,os

def opendb():
    db = MySQLdb.connect(user='zz91news', db='zz91news', passwd='4ReLhW3QLyaaECzU', host='rdsuo5342fhte95enp5i.mysql.rds.aliyuncs.com', charset="utf8")
    return db
def img_path():
    time2=time.strftime('%Y-%m-%d',time.localtime(time.time()))
    path="/mnt/data/resources/pyuploadimages/img_news/"+str(time2)+"/"
    weburl="http://img1.zz91.com/pyuploadimages/img_news/"+str(time2)+"/"
    if not os.path.isdir(path):
        os.makedirs(path)
    return {'path':path,'weburl':weburl}
    