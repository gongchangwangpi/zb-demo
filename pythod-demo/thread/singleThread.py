#-*- coding = utf-8 -*-
# @author zhangbo
# @date 2021/3/28 18:49:39

import time

def singe():
    for i in range(3):
        print("唱歌 %d"%i)
        time.sleep(0.5)

def dance():
    for i in range(3):
        print("跳舞 %d"%i)
        time.sleep(0.5)

if __name__ == '__main__':
    singe()
    dance()
