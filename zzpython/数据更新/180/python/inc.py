def updatelastid(tab,id):
	#sql="update import_table set lastid="+id+" where tablename='"+tab+"'"
	#cursor1.execute(sql)
	f = open("/usr/apps/python/text/"+tab+".txt",'w')
	f.write(id)
	f = open("/usr/apps/python/text/"+tab+".txt",'r')
	f.close()
def getlastid(tab):
	if not (os.path.exists("/usr/apps/python/text/"+tab+".txt")):
		f = open("/usr/apps/python/text/"+tab+".txt",'w')
		f.write('0')
	f = open("/usr/apps/python/text/"+tab+".txt",'r')
	return f.read()
	f.close()
def changezhongwen(strvalue):
	if(strvalue == None):
		tempstr=""
	else:
		
		if str(strvalue).isalnum():
			tempstr=strvalue
		else:
			if isNum(strvalue):
				tempstr=strvalue
			else:
				tempstr=strvalue.decode('GB18030','ignore').encode('utf-8')
		
		#tempstr=strvalue.decode('GB18030','ignore').encode('utf-8')
		#tempstr=strvalue.decode('GB18030').encode('utf-8')
	return tempstr

		
def changezw(strvalue):
	if(strvalue == None):
		tempstr=""
	else:
		tempstr=str(strvalue).encode('gbk','ignore').replace("'","").replace(",",".")
	return tempstr
def isNum2(value):
	try:
		x=int(value)
	except TypeError:
		return False
	except ValueError:
		return False
	except Exception,e:
		return False
	else:
		return True

def isNum(value):
	try:
		value + 1
	except TypeError:
		return False
	else:
		return True
def is_valid_date(stree):
	print stree
	try:
		datetime.datetime.strptime(str(stree),"%Y-%m-%d")
		return True
	except:
		return False
