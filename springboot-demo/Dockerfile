FROM openjdk:8
MAINTAINER liaohw liaohw@163.com

#create a main fold
RUN mkdir -p /opt/springboot-demo
RUN mkdir -p /opt/springboot-demo/config
RUN mkdir -p /opt/springboot-demo/logs
RUN echo "" > /opt/springboot-demo/logs/gc.log
RUN echo "" > /opt/springboot-demo/logs/heap.dump
#set workdir
WORKDIR /opt/springboot-demo

ENV APPLOGDIR=/opt/springboot-demo/logs \
    APPCONFDIR=/opt/springboot-demo/config/application.properties \
    JAVA_OPTS="\
    -XX:ParallelGCThreads=8 \
    -XX:-DisableExplicitGC \
    -XX:+UseCompressedOops \
    -XX:+UseConcMarkSweepGC \
    -XX:+PrintGC \
    -XX:+PrintGCDetails \
    -XX:+PrintGCDateStamps \
    -XX:+PrintGCTimeStamps \
    -Xloggc:./logs/gc.log \
    -XX:+HeapDumpOnOutOfMemoryError \
    -XX:HeapDumpPath=./logs/heap.dump \
    -XX:+CMSParallelRemarkEnabled"

#copy you programme to image
COPY target/springboot-demo.jar /opt/springboot-demo

ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8
# +x
#start command
ENTRYPOINT java -XX:ParallelGCThreads=8 -XX:-DisableExplicitGC -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps -Xloggc:/opt/springboot-demo/logs/gc.log -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/opt/springboot-demo/logs/heap.dump -XX:+CMSParallelRemarkEnabled -jar springboot-demo.jar