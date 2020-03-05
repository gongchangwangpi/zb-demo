#/bin/bash
# 赋值等号两边不能有空格
# 字符串单引号里面不能使用变量
# 双引号里面可以用变量
your_name='zhangsan'
echo $your_name ${your_name}
hello='hello $your_name'
echo $hello
hello="hello $your_name"
echo $hello
# 可以用unset清除变量
# readonly 表示只读变量
readonly hello
#shell 报错  ./str.sh: line 14: hello: readonly variable
#hello="hi $your_name"

#获取字符串长度
echo "your_name length is ${#your_name}"
#提取子字符串
echo "your_name subString 1-4 is ${your_name:1:4}"
#查找子字符串
echo `expr index "$your_name" a`
