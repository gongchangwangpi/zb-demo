package com.mq.kafka.d1;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.KafkaAdminClient;

import java.util.Properties;

/**
 * @author bo6.zhang
 * @date 2021/2/7
 */
public class AdminTest {

    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.setProperty(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "");


        AdminClient adminClient = KafkaAdminClient.create(properties);

    }

}
