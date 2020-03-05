#!/bin/bash
# kill java process
if [ $# != 1 ] 
  then
    echo "usage: ./kill_process.sh  process_name"
    exit 1
fi

process_name=$1
pid=`jps | grep $process_name | awk '{print $1}'`

if [ "$pid" = "" ]
  then 
    echo "the process $process_name may not running"
    exit 1
fi

echo "the ${process_name}'s pid is $pid"
read -p "kill? [y/n]" -n 1 -s confirm
if [ "$confirm" = "y" ];then
  kill -9 $pid
  echo "\nkill $pid success" 
else 
  echo "\nnot kill"
fi
