import os,sys,commands
#print os.system("ps -ef|grep python")
def getPid(process):
    cmd = "ps -ef|grep '%s' " % process
    #logging.info(cmd)
    info = commands.getoutput(cmd)
    infos = info.split()
    return infos
    if len(infos) > 1:
        return infos[1]
    else:
        return -1
    
print getPid("python|grep 'task.py'|grep '5s'")