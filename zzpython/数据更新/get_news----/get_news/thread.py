#-*- coding:utf-8 -*-
from time import sleep,ctime
import thread,threading

from zhijin import test_zhijin_steel
from xiben import test_xiben
from zhonggang import test_china_steel
from fubao import test_f139
from jinfei import test_metal_waste
from zhongzheng import test_zhongzheng
from zhongjinfei import test_china_scrap
from quanqiujin import test_quanqiujin
from conghui import test_hc360
from zhongsuxian import test_info_21cp
from zhongsu import test_plastic
from zhongsu_e import test_esuliao
from chinapaper import test_chinapaper
from worldscrap import test_worldscrap
from shengyishe import test_100ppi
from zhuochuang import test_sci99
from zhuoshang import test_w7000
from zhongsuji import test_86pla

    
def mainnews():
    print 'starting at:',ctime()
    thread.start_new_thread(test_zhijin_steel,())
    thread.start_new_thread(test_xiben,())
    thread.start_new_thread(test_china_steel,())
    thread.start_new_thread(test_f139,())
    thread.start_new_thread(test_metal_waste,())
    thread.start_new_thread(test_zhongzheng,())
    thread.start_new_thread(test_china_scrap,())
    thread.start_new_thread(test_quanqiujin,())
    thread.start_new_thread(test_hc360,())
    thread.start_new_thread(test_info_21cp,())
    thread.start_new_thread(test_plastic,())
    thread.start_new_thread(test_esuliao,())
    thread.start_new_thread(test_chinapaper,())
    thread.start_new_thread(test_worldscrap,())
    thread.start_new_thread(test_100ppi,())
    thread.start_new_thread(test_sci99,())
    thread.start_new_thread(test_w7000,())
    thread.start_new_thread(test_86pla,())
    sleep(180)
    print 'all DONE at:',ctime()

mainnews()