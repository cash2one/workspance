#encoding=utf-8
import time,random,threading
from Queue import Queue
from fubao import test_f139
from zhijin import test_zhijin_steel

class th1(threading.Thread):
   def __init__(self, threadname, queue):
       threading.Thread.__init__(self, name = threadname)
       self.sharedata = queue
   def run(self):
       print '--------1'
       test_f139()
class th2(threading.Thread):
   def __init__(self, threadname, queue):
       threading.Thread.__init__(self, name = threadname)
       self.sharedata = queue
   def run(self):
       print '--------2'
       test_zhijin_steel()
       
def main():

   queue = Queue()
   th1 = th1('th1', queue)
   th2 = th2('th2', queue)
   print 'Starting threads ...'
   th1.start()
   th2.start()
   th1.join()
   th2.join()
   print 'All threads have terminated.'

main()