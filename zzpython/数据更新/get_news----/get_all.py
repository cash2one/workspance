#-*- coding:utf-8 -*-
from zhijin import test_zhijin_steel
from xiben import test_xiben
from zhonggang import test_china_steel
from fubao import test_f139
from ganglian import test_glinfo
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
from xiangjiao91 import test_xj91


#test_glinfo()
test_china_scrap()#需要触发js网络安全狗,现已不用
test_f139()
test_china_steel()
test_zhijin_steel()
test_xiben()
test_metal_waste()
test_quanqiujin()
test_info_21cp()
test_worldscrap()
test_sci99()
test_plastic()
test_zhongzheng()
test_hc360()
#test_xj91()
test_esuliao()
test_100ppi()
test_chinapaper()