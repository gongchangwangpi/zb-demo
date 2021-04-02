#-*- coding = utf-8 -*-
# @author zhangbo
# @date 2021/3/27 22:15:32

import urllib.request

baidu = "http://www.baidu.com"

resp = urllib.request.urlopen(baidu)
# print(resp.read().decode("UTF-8"))
print(resp.getheaders())
print(resp.getheader("Server"))
print(resp.getheader("Content-Type"))

data = bytes(urllib.parse.urlencode({"kw":"指数"}), encoding="utf-8")
req = urllib.request.Request(url=baidu, method="post", data=data, headers={})
resp = urllib.request.urlopen(req)


