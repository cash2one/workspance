COMMAND="$1"
taskname=$COMMAND
cd /mnt/python/zz91_task/

pid=`ps -ef|grep "python"|grep "task.py"|grep "$taskname"|grep -v "grep"|awk '{print $2}'`
if [ "$pid" = "" ] ; then
  echo "have no pid"
  python task.py $taskname
else
  echo "have pid $pid now"
  echo $taskname
  #kill -9 $pid
fi
