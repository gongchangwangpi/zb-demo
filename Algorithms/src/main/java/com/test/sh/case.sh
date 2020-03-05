#!/bin/bash
# case 
echo "please input [1/2/3]"
read -p "input your choice: " -t 30 choice
case "$choice" in 
  "1")
    echo "you input 1"
    ;;
  "2")
    echo "input 2"
    ;;
  "3")
    echo "input 3"
    ;;
  *)
    echo "Error...please input [1/2/3]"
    ;;
esac
