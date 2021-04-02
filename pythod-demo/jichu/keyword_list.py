#-*- coding = utf-8 -*-
# @author zhangbo
# @date 2021/3/26 22:52:42
import keyword
print(keyword.kwlist)

age = 18
name = "李四"
print("我的姓名是：%s, 我的家乡是%s"%(name, "中国"))
print("我的年纪是：%d岁"%age)

# 分隔符,默认为空格，结束符默认为回车换行
print("www", "baidu", "com", sep=".")
print("www", "baidu", "com", end=".")

# ps = input("请输入密码：")
# print("请确认你的密码：", ps)

a = 10
print(type(a))
a = "hello"
print(type(a))

# 不管输入什么，类型都是字符串
s = input("请输入：")
print(type(s))
# 转型为int,英文等转型失败，抛 ValueError: invalid literal for int() with base 10: 'qwe'
s = int(s)
print(type(s))