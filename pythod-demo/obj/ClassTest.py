# coding=utf-8
class Test:
  # 类变量，类共享
  count = 0

  # 构造器
  def __init__(self, name, age):
    self.name = name
    self.age = age
    Test.count += 1

  # 实例方法
  def sayHello(self):
    print("hello: ", self.name)

# 实例化对象
t = Test("张三", 17)
t2 = Test("李四", 18)

# 调用对象的属性和方法
print(t)
print(t.name)
print(t.age)
print("count = ", t.count)
t.sayHello()
t2.sayHello()