# -*- coding:utf-8 -*-
import time
"""

current_date=time.strftime("%Y-%m-%d")
print current_date
"""
newlist=[]
a=0
b=[0,1,0,2,0,3,0,4,0,5]
for a in range(0,10):
    if b[a]!=0:
         newlist.append(b[a])
for list in newlist:
    print list

