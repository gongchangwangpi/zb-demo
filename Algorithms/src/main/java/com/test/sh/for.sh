#!/bin/bash
s=0
for(( i=0;i < 100; i=i+1 ))
  do
    s=$(( $s + $i ))
  done
echo "sum = $s"

ls *.sh > sh.log
for j in $( cat sh.log )
  do
    echo "file name is : $j"
  done
