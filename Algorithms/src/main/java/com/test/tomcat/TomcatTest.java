package com.test.tomcat;

import org.apache.catalina.Engine;
import org.apache.catalina.Server;
import org.apache.catalina.Service;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;

/**
 * @author zhangbo
 */
public class TomcatTest {

    public static void main(String[] args) throws Exception {
        
        String userDir = System.getProperty("user.dir");

        System.out.println(userDir);
        
        Tomcat tomcat = new Tomcat();
        
        Server server = tomcat.getServer();

        Connector connector = new Connector();
        connector.setPort(9090);

        Service service = tomcat.getService();
        service.addConnector(connector);

//        service.addExecutor();
//        Wrapper wrapper = context.createWrapper();
//        wrapper.setParent(context);
//        
//        wrapper.setServlet(new HelloServlet());
//        wrapper.addMapping("HelloServlet");
//        context.addChild(wrapper);

        StandardHost host = new StandardHost();

        StandardContext context = new StandardContext();
        context.getServletContext().addServlet("HelloServlet", new HelloServlet());
        context.addServletMappingDecoded("/hello", "HelloServlet");


        Engine engine = new StandardEngine();

//        context.setPath("/");

        engine.addChild(host);
        service.setContainer(engine);
        host.addChild(context);
        
        tomcat.start();
        
        // 阻塞
        tomcat.getServer().await();
    }
    
}
