pid=`ps -ef|grep "/usr/apps/python/get_zz91_price/autotask"|grep "$PYTHONTEMP"|grep -v "grep"|awk '{print $2}'`
if [ "$pid" = "" ] ; then
  echo "no tomcat pid alive"
else
  echo "kill pid $pid now"
  kill -9 $pid
fi
#nohup python -u /usr/apps/python/get_zz91_price/autotask.py &
