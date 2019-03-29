package com.springapp.multidatasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author zhangbo
 */

public class MultiDataSourceProvider extends AbstractRoutingDataSource {
    
    @Override
    protected Object determineCurrentLookupKey() {
        
        
        return null;
    }
}
