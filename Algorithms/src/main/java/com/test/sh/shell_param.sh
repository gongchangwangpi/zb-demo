#/bin/bash
# $0 $n表示传递给shell的第几个参数
echo "\$0 is $0"
echo "\$1 is $1"
echo "\$2 is $2"
#
echo "脚本运行的当前进程ID号\$$ is $S"
echo "参数个数\$# is $#"
echo "后台运行的最后一个进程的ID号\$! is $!"

for p in "$@"
do 
    echo $p
done

for pp in "$*"
do echo $pp
done
