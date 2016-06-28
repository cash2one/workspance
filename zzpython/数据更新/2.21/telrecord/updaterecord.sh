#!/bin/sh
python /usr/apps/python/telrecord/tongji.py > /usr/apps/python/telrecord/log.log
chmod -R 0744 /usr/apps/python/telrecord/

pid=`ps -ef|grep "callre.py"|grep "/usr/apps/python/telrecord"|grep -v "grep"|awk '{print $2}'`
if [ "$pid" = "" ] ; then
	setsid python /usr/apps/python/telrecord/callre.py &
	echo "start callre suc"
else
	echo "callre started $pid"
fi

pid1=`ps -ef|grep "calllist.py"|grep "/usr/apps/python/telrecord"|grep -v "grep"|awk '{print $2}'`
if [ "$pid1" = "" ] ; then
	setsid python /usr/apps/python/telrecord/calllist.py &
	echo "start calllist suc"
else
	echo "calllist started $pid1"
fi