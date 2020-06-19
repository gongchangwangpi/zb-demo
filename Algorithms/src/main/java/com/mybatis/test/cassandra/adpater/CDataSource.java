package com.mybatis.test.cassandra.adpater;

import com.datastax.oss.driver.api.core.CqlSession;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * @author zhangbo
 * @date 2020/6/18
 */
public class CDataSource implements DataSource {

    private CqlSession cqlSession;

    public CDataSource(CqlSession cqlSession) {
        this.cqlSession = cqlSession;
    }

    public void close() {
        cqlSession.close();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return new CConnection(cqlSession);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
