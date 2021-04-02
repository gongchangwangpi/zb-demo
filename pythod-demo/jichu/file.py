# -*- coding: UTF-8 -*-
print("hello python")

test = open("/test.txt", mode="r+", encoding="utf8")
print("文件名:" + test.name)
print("RW:", test.mode)
print("read:", test.read())

len = test.writelines("\ncode write")
print("write length: ", len)
print("read:", test.read())

test.close()