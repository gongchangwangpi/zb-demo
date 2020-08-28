package com.mq.activemq.spring;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

/**
 * Created by Administrator on 2018/3/5 0005.
 */
@Configuration
public class ActiveMQConfig {

    /*<!-- a pooling based JMS provider -->
  <bean id="jmsFactory" class="org.apache.activemq.pool.PooledConnectionFactory" destroy-method="stop">
    <property name="connectionFactory">
      <bean class="org.apache.activemq.ActiveMQConnectionFactory">
        <property name="brokerURL">
          <value>tcp://localhost:61616</value>
        </property>
      </bean>
    </property>
  </bean>

  <!-- Spring JMS Template -->
  <bean id="myJmsTemplate" class="org.springframework.jms.core.JmsTemplate">
    <property name="connectionFactory">
      <ref local="jmsFactory"/>
    </property>
  </bean>*/

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("tcp://192.168.0.103:61616");
        connectionFactory.setMaxThreadPoolSize(16);

        return connectionFactory;
    }

    @Bean
    public PooledConnectionFactory jmsFactory(ActiveMQConnectionFactory activeMQConnectionFactory) {
        PooledConnectionFactory jmsFactory = new PooledConnectionFactory();
        jmsFactory.setConnectionFactory(activeMQConnectionFactory);
        return jmsFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate(PooledConnectionFactory pooledConnectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(pooledConnectionFactory);
        return jmsTemplate;
    }
}
