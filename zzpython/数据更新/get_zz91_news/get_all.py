from apscheduler.scheduler import Scheduler
import zhonggang,conghui,chinapaper,zhongzheng,jinfei,zhongsu_e,zhongsuxian,ruida,ali88,fubao,ugang


#schedudler = Scheduler(daemonic = False)  
#@schedudler.cron_schedule(second='1', minute='1', day_of_week='0-6', hour='0,4,8,12,16,20') 
#def getbaidu():
zhonggang.test_china_steel()
chinapaper.test_chinapaper()
conghui.test_hc360()
jinfei.test_metal_waste()
zhongsu_e.test_esuliao()
zhongsuxian.test_info_21cp()
ruida.test_ruida()
ali88.test_ali88()
zhongzheng.test_zhongzheng()
ugang.test_usteel()
fubao.test_f139()
#schedudler.start()

#getbaidu()
'''
test_china_steel()
test_hc360()
test_chinapaper()
test_zhongzheng()
test_metal_waste()
test_esuliao()
test_ruida()
test_ali88()
test_f139()
sched = Scheduler()
sched.daemonic = False
sched.add_interval_job(zhonggang.test_china_steel,minutes=30)
sched.add_interval_job(chinapaper.test_chinapaper,minutes=30)
sched.add_interval_job(zhongzheng.test_zhongzheng,minutes=30)
sched.add_interval_job(conghui.test_hc360,minutes=30)
sched.add_interval_job(jinfei.test_metal_waste,minutes=30)
sched.add_interval_job(zhongsu_e.test_esuliao,minutes=30)
sched.add_interval_job(fubao.test_f139,minutes=30)
sched.add_interval_job(ruida.test_ruida,minutes=30)
sched.add_interval_job(ali88.test_ali88,minutes=30)
sched.start()
'''