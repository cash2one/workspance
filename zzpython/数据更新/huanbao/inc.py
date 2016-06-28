def updatelastid(tab,id):
	#sql="update import_table set lastid="+id+" where tablename='"+tab+"'"
	#cursor1.execute(sql)
	f = open(""+tab+".txt",'w')
	f.write(id)
	f = open(""+tab+".txt",'r')
	f.close()
def getlastid(tab):
	if not (os.path.exists(""+tab+".txt")):
		f = open(""+tab+".txt",'w')
		f.write('0')
	f = open(""+tab+".txt",'r')
	return f.read()
	f.close()
def changezhongwen(strvalue):
	if(strvalue == None):
		tempstr=""
	else:
		tempstr=strvalue.decode('GB18030','ignore').encode('utf-8')
		#tempstr=strvalue.decode('GB18030').encode('utf-8')
	return tempstr
def changezw(strvalue):
	if(strvalue == None):
		tempstr=""
	else:
		tempstr=str(strvalue).encode('gbk','ignore').replace("'","").replace(",",".")
	return tempstr