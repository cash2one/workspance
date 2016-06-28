export PYTHONTEMP="/usr/apps/python/zz91_task/sms/"
pid=`ps -ef|grep "$PYTHONTEMP"|grep -v "grep"|awk '{print $2}'`
if [ "$pid" = "" ] ; then
  echo "no pid alive"
else
  echo "kill pid $pid now"
  #kill -9 $pid
fi
#nohup python -u /usr/apps/python/zz91_task/sms/send.py &
#nohup python -u /usr/apps/python/zz91_task/sms/sendyx.py &
