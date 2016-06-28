pid=`ps -ef|grep "app_auto"|grep "$PYTHONTEMP"|grep -v "grep"|awk '{print $2}'`
if [ "$pid" = "" ] ; then
  echo "no tomcat pid alive"
else
  echo "kill pid $pid now"
  kill -9 $pid
fi
nohup python -u app_auto.py &
ps -ef|grep app_auto