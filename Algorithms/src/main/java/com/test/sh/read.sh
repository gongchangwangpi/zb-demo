#/bin/bash

# -p 提示消息 后面跟变量名
read -p "please input your name: " name
echo "your name is : $name"
# -t 等待秒数
read -t 10 -p "please input your age: " age
echo "your age is : $age"
# -s 不显示在屏幕上，可用于密码等
# -n 限制输入字符个数
read -s -n 6 -p "please input youre password, 6 charcters: \\n" password
echo "your password is : $password"
