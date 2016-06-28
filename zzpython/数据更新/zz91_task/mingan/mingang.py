import sys
reload(sys) 
sys.setdefaultencoding('utf-8') 
#获得明感字符
def getmingganword(s):
    file = open("/mnt/data/keylimit/limit")
    list=[]
    lines = file.readlines(100000)
    if lines:
        for line in lines:
            line=line.strip('\n').strip()
            if line in s:
                return line
                break
            list.append(line)
    if s in list:
        return s
    return None

print getmingganword("开发票")