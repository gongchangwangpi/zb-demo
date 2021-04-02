#-*- coding = utf-8 -*-
# @author zhangbo
# @date 2021/3/27 14:58:56

'''
# range
for i in range(0, 5, 1):
    print(i)


for i in "hello python":
    print(i, end=" ")

print("")

a = ["a1", "b2", "c3"]
print(len(a))
for i in range(len(a)):
    print(i, a[i])


# while else
i = 0
while i < 5:
    print(i)
    i += 1
else:
    print("%d >= 5"%i)
'''

# 99乘法表
for i in range(1, 10):
    for j in range(1, 10):
        if j <= i:
            print("%d*%d=%d"%(i, j, i*j), sep="\t", end="\t")
        else:
            print()
            break

