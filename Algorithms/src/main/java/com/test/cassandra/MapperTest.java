package com.test.cassandra;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.mapper.MapperBuilder;

/**
 * @author zhangbo
 * @date 2020/5/25
 */
public class MapperTest {

    public static void main(String[] args) {
        CqlSession session = CassandraTest.getSession();

        CustomerMapper build = new MapperBuilder<CustomerMapper>(session) {
            @Override
            public CustomerMapper build() {
                return null;
            }
        }.build();
    }

}
