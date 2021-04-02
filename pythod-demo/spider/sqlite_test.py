#-*- coding = utf-8 -*-
# @author zhangbo
# @date 2021/3/28 14:06:26

import sqlite3

# 创建并连接数据库
connection = sqlite3.connect("sqlite_test.db")

ddl = """
create table if not exists student(
id integer primary key autoincrement,
'name' varchar ,
age integer  ,
score float 
)
"""
# 建表
connection.execute(ddl)

print("create table success")

insertSql = """
insert into student
('name', age, score)
values 
('zhangsan', 18, 90),
('lisi', 17, 80)
"""
connection.execute(insertSql)
connection.commit()
print("insert data success")

selectSql = """
select * from student
"""
result = connection.execute(selectSql)

for row in result:
    print(row)
    print(type(row))

connection.close()