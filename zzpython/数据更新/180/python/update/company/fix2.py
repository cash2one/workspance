import MySQLdb  
import sys
from datetime import datetime
from time import strftime
reload(sys)
sys.setdefaultencoding('utf-8')

conn_to=None
cursor_to=None
bool_true=1
bool_false=0
now=None

def __init():
	global conn_to
	global cursor_to
	global now
	conn_to=MySQLdb.connect(host='192.168.110.118',user='reborn',passwd='zz918090zz91',db='reborn',charset='utf8')
	cursor_to=conn_to.cursor()
	now=datetime.now().strftime('%Y-%m-%d %H:%M:%S')

#############################import country#################################
def country():
	__init()
	sql="select id,gmt_created,gmt_modified,gmt_register,source_type_code from company where ((source_type_code<'10041010' or  source_type_code>'10041016') or source_type_code is null)"
	cursor_to.execute(sql)
	results=cursor_to.fetchall()
	for row in results:
		id2=row[0]
		gmt_created=str(row[1])
		gmt_modified=str(row[2])
		gmt_register=str(row[3])
		source_type_code=row[4]
		sql="update company_account set company_id="+str(id2)+" where gmt_created='"+gmt_created+"' and gmt_register='"+gmt_register+"' and company_id=0 "
		cursor_to.execute(sql)
		print id2
	#__close()
country()
def __close():
	cursor_to.close()
	conn_to.close()
