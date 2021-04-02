#-*- coding = utf-8 -*-
# @author zhangbo
# @date 2021/3/28 14:15:22

import pymysql

connection = pymysql.connect(user="root", password="123456", host="127.0.0.1", port=3306, database="mytest")

cursor = connection.cursor()
result = cursor.execute("select * from t_test")

print(type(result))

for row in cursor:
    print(row)

