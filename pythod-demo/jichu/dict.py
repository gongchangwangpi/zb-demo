#-*- coding = utf-8 -*-
# @author zhangbo
# @date 2021/3/27 18:59:20

zhang = {"name":"zhangsan", "age":18}
print(zhang)
print(zhang["name"])
# 直接访问不存在的key，报错，需要通过get访问
# print(zhang["sex"])
# 不存在，返回 None
print(zhang.get("sex"))
# 设置默认值
print(zhang.get("sex", "M"))

# 删除key，并返回指定key的value
# print(zhang.pop("age"))
# print(zhang)

print(zhang.keys())

print(zhang.items())

zhang.update({"age":20})
print(zhang)

zhang["sex"] = "M"
print(zhang)

zhang["sex"] = "F"
print(zhang)

del zhang["sex"]
print(zhang)

zhang.clear()
print(zhang)

# NameError: name 'zhang' is not defined
# del zhang
# print(zhang)
