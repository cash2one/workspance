#-*- coding:utf-8 -*-
#统计目录
logpath="/mnt/data/log4z/zz91Analysis/"
logpath_myrc="/mnt/data/log4z/myrc/"
logpath_zz91="/mnt/data/log4z/zz91/"

limitpath="/mnt/data/keylimit/limit"

imgpath='/mnt/phpcode/zz91news/'
newspath='/mnt/var/log/newsprint/'
pyuploadpath="/mnt/data/resources/pyuploadimages/"
huanbaopyuploadpath="/mnt/data/huanbao/resources/pyuploadimages/"
pyimgurl="http://img1.zz91.com/pyuploadimages/"
huanbaopyimgurl="http://img1.huanbao.com/pyuploadimages/"

CACHE_BACKEND = 'memcached://127.0.0.1:11211;10.51.24.198:11211/?max_entries=2048&timeout=900&cull_percentage=10'

sph_name={'pricelist':{'name':'pricelist,pricelisttoday',
                       'serverid':'100.98.115.142',
                       'port':9317
                       },
         'offersearch':'offersearch_new,offersearch_new_vip',
         'price_data':{'name':'price_data',
                       'serverid':'100.98.115.142',
                       'port':9317
                       },
         'price':{'name':'price',
                       'serverid':'100.98.115.142',
                       'port':9317
                       },
         'company_price':{'name':'company_price',
                       'serverid':'100.98.115.142',
                       'port':9317
                       },
		 'news':{'name':'news',
                       'serverid':'100.98.115.142',
                       'port':9317
                       },
          'sex':{'name':'sex',
                       'serverid':'100.98.115.142',
                       'port':9317
                       },
		 'newstags':{'name':'newstags',
                       'serverid':'100.98.115.142',
                       'port':9317
                       }
         }
SPHINXCONFIG = SPHINXCONFIG_news={'serverid':'100.98.115.142','port':9315,'name':sph_name}

SESSION_ENGINE = (
    'django.contrib.sessions.backends.cache'
)
CACHES = {
    'default': {
        'BACKEND': 'django.core.cache.backends.memcached.MemcachedCache',
        'LOCATION': [
            '127.0.0.1:11211',
            '10.171.223.228:11211',
        ]
    }
}
#cms客户目录
pycmspath = '/mnt/pythoncode/webcms/webcms/templates/media/web/'
#ftp上传目录
ftpath={'pycms':'web/rzotc'}
#----杭州婚纱ftp
#ftpconn={'ip':'209.73.152.184','uname':'kxzjzjzzkx','pword':'asto123456','ftpath':ftpath}
ftpconn={'ip':'103.228.28.144','uname':'kangxianyue','pword':'ACAE414261ce76','ftpath':ftpath}

pycmsurl='http://webcms.zz91.com/'
