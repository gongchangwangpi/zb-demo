package com.zb.fund.tomcat;


import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

/**
 * @author zhangbo
 */
public class TomcatTest1 {

    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();//创建tomcat实例，用来启动tomcat

        tomcat.setHostname("localhost");//设置主机名

        tomcat.setPort(8080);//设置端口

        tomcat.setBaseDir(".");//tomcat存储自身信息的目录，比如日志等信息，根目录



        String DEFAULT_PROTOCOL = "org.apache.coyote.http11.Http11NioProtocol";

        Connector connector = new Connector(DEFAULT_PROTOCOL);//设置协议，默认就是这个协议connector.setURIEncoding("UTF-8");//设置编码

        connector.setPort(8080);//设置端口

        tomcat.getService().addConnector(connector);



        org.apache.catalina.Context ctx = tomcat.addContext("",null);//网络访问路径

        tomcat.addServlet(ctx,"myServlet",new HelloServlet()); //配置servlet

        ctx.addServletMappingDecoded("/messageServlet","myServlet");//配置servlet映射路径



//        StandardServer server = (StandardServer)tomcat.getServer();//添加监听器，不知何用
//
//        AprLifecycleListener listener = new AprLifecycleListener();
//
//        server.addLifecycleListener(listener);



//设置appBase为项目所在目录

        tomcat.getHost().setAppBase(System.getProperty("user.dir") + "/fund/src/main");

//设置WEB-INF文件夹所在目录

//该文件夹下包含web.xml

//当访问localhost:端口号时，会默认访问该目录下的index.html/jsp页面

        tomcat.addWebapp("/","webapp");


        tomcat.start();//启动tomcat

        tomcat.getServer().await();//维持tomcat服务，否则tomcat一启动就会关闭
    }
    
}
