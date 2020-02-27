//package com.zb.springboot.demo.ftp;
//
//import com.jcraft.jsch.ChannelSftp;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.expression.common.LiteralExpression;
//import org.springframework.integration.annotation.Gateway;
//import org.springframework.integration.annotation.IntegrationComponentScan;
//import org.springframework.integration.annotation.MessagingGateway;
//import org.springframework.integration.annotation.ServiceActivator;
//import org.springframework.integration.file.remote.FileInfo;
//import org.springframework.integration.file.remote.session.CachingSessionFactory;
//import org.springframework.integration.file.remote.session.SessionFactory;
//import org.springframework.integration.sftp.gateway.SftpOutboundGateway;
//import org.springframework.integration.sftp.outbound.SftpMessageHandler;
//import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
//import org.springframework.integration.sftp.session.SftpRemoteFileTemplate;
//import org.springframework.messaging.MessageHandler;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.messaging.handler.annotation.Payload;
//
//import java.io.File;
//import java.util.List;
//
///**
// * spring-integration-sftp
// * 基础及代理配置
// *
// * @author lee
// */
//@Configuration
//@EnableConfigurationProperties(SftpProperties.class)
//public class SftpConfiguration {
//
//    private SftpProperties properties;
//
//    @Autowired
//    public SftpConfiguration(SftpProperties properties) {
//        this.properties = properties;
//    }
//
//    @Bean
//    public SessionFactory<ChannelSftp.LsEntry> sftpSessionFactory() {
//        DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory(true);
//        factory.setHost(properties.getHost());
//        factory.setPort(properties.getPort());
//        factory.setUser(properties.getUser());
//        factory.setPassword(properties.getSftpQueue());
//        factory.setAllowUnknownKeys(true);
//        return new CachingSessionFactory<>(factory);
//    }
//
//    @Bean
//    @ServiceActivator(inputChannel = "toSftpChannel")
//    public MessageHandler handler() {
//        SftpMessageHandler handler = new SftpMessageHandler(sftpSessionFactory());
//        handler.setRemoteDirectoryExpression(new LiteralExpression(properties.getDirectory()));
//        handler.setFileNameGenerator(message -> {
//            if (message.getPayload() instanceof File) {
//                return ((File) message.getPayload()).getName();
//            } else {
//                throw new IllegalArgumentException("File expected as payload.");
//            }
//        });
//        return handler;
//    }
//
//    @Bean
//    @ServiceActivator(inputChannel = "toSftpByteChannel")
//    public MessageHandler byteHandler() {
//        SftpMessageHandler handler = new SftpMessageHandler(sftpSessionFactory());
//        handler.setRemoteDirectoryExpression(new LiteralExpression(properties.getDirectory()));
//        handler.setFileNameGenerator(message -> {
//            if (message.getPayload() instanceof byte[]) {
//                return (String) message.getHeaders().get("name");
//            } else {
//                throw new IllegalArgumentException("byte[] expected as payload.");
//            }
//        });
//        return handler;
//    }
//
//    @Bean
//    @ServiceActivator(inputChannel = "lsChannel")
//    public MessageHandler lsHandler() {
//        SftpOutboundGateway sftpOutboundGateway = new SftpOutboundGateway(
//                sftpSessionFactory(), "ls", "payload");
//        //配置项,实际执行命令为:ls -1,该命令检索文件名列表,默认为检索FileInfo对象列表
//        sftpOutboundGateway.setOptions("-1");
//        return sftpOutboundGateway;
//    }
//
//    @Bean
//    public SftpRemoteFileTemplate template() {
//        return new SftpRemoteFileTemplate(sftpSessionFactory());
//    }
//
//    @MessagingGateway
//    public interface SftpGateway {
//
//        /**
//         * 上传文件
//         * @param file
//         */
//        @Gateway(requestChannel = "toSftpChannel")
//        void upload(File file);
//
//        /**
//         * 字节流形式上传文件
//         * @param payload
//         * @param name
//         */
//        @Gateway(requestChannel = "toSftpByteChannel")
//        void upload(@Payload byte[] payload, @Header("name") String name);
//
//        /**
//         * 查看文件是否存在
//         * @param dir
//         * @return
//         */
//        @Gateway(requestChannel = "lsChannel")
//        List<FileInfo> listFile(String dir);
//    }
//}
