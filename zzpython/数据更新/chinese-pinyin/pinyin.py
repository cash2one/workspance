#-*- coding:utf-8 -*-
import re

def convert(ch):#获取一个汉字转为英文
    length = 3 #utf-8，汉字占用3字节
    with open(r'convert-utf-8.txt') as f:
        for line in f:
            if ch in line:
                return line[length:len(line)-2]
            
def split_str(chinese):#传入的汉字分成一个一个的
    c=[]
    lc=1
    len_c=range(0,len(chinese)/lc)
    for lenn in len_c:
        ag=lenn*lc
        ed=lenn*lc+lc
        c.append(chinese[ag:ed])
    return c

def is_chinese(uchar):
        if uchar >= u'\u4e00' and uchar<=u'\u9fa5':
            return True
        else:
            return False

def chinese_abstract(str):#每个汉字转为拼音,再拼接起来
    ddd=''
    cccl=split_str(str)
    for ccc in cccl:
        if is_chinese(ccc)==True:
            ss=convert(ccc)
        else:
            ss=ccc
        if ',' in ss:
            ss=re.sub(',.*','',ss)[:-1]
        ddd=ddd+ss
    return ddd
