import operator,datetime,time
from public import *
from zz91conn import database_other
from zz91tools import getYesterday
from zz91settings import logpath

#gmt_created=getYesterday()

def getfilelist(nowdate):
    list1=[]
#    gettoday=datetime.date.today()
#    format="%Y-%m-%d";
#    gettoday=strtodatetime("2014-4-28",format)
#    nowdate=gettoday-datetime.timedelta(1)
#    nowdate1=nowdate.strftime("%Y-%m-%d")
    datalist=open(logpath+'run.'+nowdate)
    for line in datalist:
        linedir=eval(line)
        ip=linedir['ip']
        datadir=eval(linedir['data'])
        account=datadir['account']
        countdate=datadir['date']
        url=datadir['url']
        lt1={'ip':ip,'account':account,'countdate':countdate,'url':url}
        list1.append(lt1)
    datalist.close()
    return list1

url_list=[
      {'typeid':4,'type':'tradedetail','url':'http://trade.zz91.com/productdetails'},
      {'typeid':3,'type':'tradelist','url':'http://trade.zz91.com/trade/s'},
      {'typeid':2,'type':'trade','url':'http://trade.zz91.com/'},
      {'typeid':7,'type':'pricedetail','url':'http://price.zz91.com/priceDetails'},
      {'typeid':6,'type':'pricelist','url':'http://price.zz91.com/priceSearch.htm'},
      {'typeid':5,'type':'price','url':'http://price.zz91.com/'},
      {'typeid':8,'type':'tags','url':'http://tags.zz91.com'},
      {'typeid':9,'type':'daohang','url':'http://daohang.zz91.com'},
      {'typeid':10,'type':'huzhu','url':'http://huzhu.zz91.com'},
      {'typeid':13,'type':'newsdetail','url':'newsdetail'},
      {'typeid':12,'type':'newslist','url':'list/tags'},
      {'typeid':11,'type':'news','url':'http://news.zz91.com/'},
      {'typeid':15,'type':'myrc','url':'http://myrc.zz91.com/'},
      {'typeid':18,'type':'company','url':'http://company.zz91.com/'},
      {'typeid':21,'type':'map','url':'http://map.zz91.com/'},
      {'typeid':22,'type':'zhanhui','url':'http://zhanhui.zz91.com/'},
      {'typeid':20,'type':'tech','url':'http://tech.zz91.com/'},
      {'typeid':99,'type':'other','url':'baidu.com'},
      {'typeid':1,'type':'www','url':'http://www.zz91.com'},
      {'typeid':1,'type':'www','url':'http://jinshu.zz91.com/'},
      {'typeid':1,'type':'www','url':'http://china.zz91.com/'},
      ]
