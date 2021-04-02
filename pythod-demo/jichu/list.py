#-*- coding = utf-8 -*-
# @author zhangbo
# @date 2021/3/27 18:18:54

movies = ["战狼", "哪吒", "风声"]

print(movies)

m = ["药神", "攀登者"]
movies.append(m)
print(movies)

# extend 将list展开，然后在添加进list
movies.extend(m)
print(movies)

# remove 异常，元素不在list中
# movies.remove("1")
# print(movies)

# del index超过长度，抛异常
del movies[5]
print(movies)

# insert的index超过最大长度，则直接插入list末尾
movies.insert(10, "111")
print(movies)
movies.insert(9, "112")
print(movies)

# if ele [not] in list, py 会主动帮我们遍历list判断
if "战狼" in movies:
    print("Yes")
else:
    print("No")


