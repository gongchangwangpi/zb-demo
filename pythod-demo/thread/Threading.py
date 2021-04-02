#-*- coding = utf-8 -*-
# @author zhangbo
# @date 2021/3/28 21:02:03

import os
import threading
import time


def singe(num):
    pid = os.getpid()
    ppid = os.getppid()
    print("single pid : %d"%pid)
    print("single ppid : %d"%ppid)
    print("single thread: ",threading.currentThread().name)

    start = time.time()
    for i in range(num):
        print("唱歌 %d"%i)
        time.sleep(0.5)
    end = time.time()
    print("唱歌结束, cost: %d s"%(end-start))

def dance(num):
    pid = os.getpid()
    ppid = os.getppid()
    print("dance pid : %d"%pid)
    print("dance ppid : %d"%ppid)
    print("dance thread: ",threading.currentThread().name)

    start = time.time()
    for i in range(num):
        print("跳舞 %d"%i)
        time.sleep(0.5)
    end = time.time()
    print("跳舞结束, cost: %d s"%(end-start))


def eat():
    pid = os.getpid()
    ppid = os.getppid()
    print("eat pid : %d"%pid)
    print("eat ppid : %d"%ppid)
    print("eat thread: ",threading.currentThread().name)

    start = time.time()
    for i in range(3):
        print("吃饭 %d"%i)
        time.sleep(0.5)
    end = time.time()
    print("吃饭结束, cost: %d s"%(end-start))


if __name__ == '__main__':
    pid = os.getpid()
    ppid = os.getppid()
    print("__main__ pid : %d"%pid)
    print("__main__ ppid : %d"%ppid)
    print("__main__ thread: ",threading.currentThread().name)
    # 使用args元祖传参，kwargs字典传参，以及无需传参的

    singe_process = threading.Thread(target=singe, name="single-thread", args=(3,))
    dance_process = threading.Thread(target=dance, name="dance-thread", kwargs={"num": 5})
    eat_process = threading.Thread(target=eat, name="eat-thread")

    eat_process.daemon = True

    singe_process.start()
    dance_process.start()
    eat_process.start()

