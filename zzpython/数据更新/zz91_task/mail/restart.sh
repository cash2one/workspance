export PYTHONTEMP="/usr/apps/python/zz91_task/mail/"
pid=`ps -ef|grep "python"|grep "$PYTHONTEMP"|grep -v "grep"|awk '{print $2}'`
if [ "$pid" = "" ] ; then
  echo "no pid alive"
else
  echo "kill pid $pid now"
  kill -9 $pid
fi
#nohup python -u /usr/apps/python/zz91_task/mail/send.py &
#nohup python -u /usr/apps/python/zz91_task/mail/sendsystem.py &
