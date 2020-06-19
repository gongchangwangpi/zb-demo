package com.mybatis.test.cassandra.adpater;

import com.alibaba.fastjson.JSON;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.test.cassandra.CassandraPageTest;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.ByteBuffer;
import java.sql.*;
import java.time.Instant;
import java.util.Calendar;

/**
 * @author zhangbo
 * @date 2020/6/18
 */
public class CPrepareStatement implements PreparedStatement {


    private CqlSession cqlSession;
    private String sql;
    private BoundStatement boundStatement;
    private com.datastax.oss.driver.api.core.cql.ResultSet resultSet;
    private com.datastax.oss.driver.api.core.cql.PreparedStatement preparedStatement;

    public CPrepareStatement(CqlSession cqlSession, String sql) {
        this.cqlSession = cqlSession;
        this.preparedStatement = cqlSession.prepare(sql);
        boundStatement = preparedStatement.bind(new Object[0]);
        this.sql = sql;
    }

    private int getParameterIndex(int parameterIndex) {
        if (parameterIndex == 0) {
            return 0;
        }
        return parameterIndex - 1;
    }

    @Override
    public ResultSet executeQuery() throws SQLException {
        this.resultSet = cqlSession.execute(sql);
        return new CResultSet(resultSet);
    }

    @Override
    public int executeUpdate() throws SQLException {
        return 0;
    }

    @Override
    public void setNull(int parameterIndex, int sqlType) throws SQLException {
        boundStatement = boundStatement.setToNull(getParameterIndex(parameterIndex));
    }

    @Override
    public void setBoolean(int parameterIndex, boolean x) throws SQLException {
        boundStatement = boundStatement.setBoolean(getParameterIndex(parameterIndex), x);
    }

    @Override
    public void setByte(int parameterIndex, byte x) throws SQLException {
        boundStatement = boundStatement.setByte(getParameterIndex(parameterIndex), x);
    }

    @Override
    public void setShort(int parameterIndex, short x) throws SQLException {
        boundStatement = boundStatement.setShort(getParameterIndex(parameterIndex), x);
    }

    @Override
    public void setInt(int parameterIndex, int x) throws SQLException {
        boundStatement = boundStatement.setInt(getParameterIndex(parameterIndex), x);
    }

    @Override
    public void setLong(int parameterIndex, long x) throws SQLException {
        boundStatement = boundStatement.setLong(getParameterIndex(parameterIndex), x);
    }

    @Override
    public void setFloat(int parameterIndex, float x) throws SQLException {
        boundStatement = boundStatement.setFloat(getParameterIndex(parameterIndex), x);
    }

    @Override
    public void setDouble(int parameterIndex, double x) throws SQLException {
        boundStatement = boundStatement.setDouble(getParameterIndex(parameterIndex), x);
    }

    @Override
    public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
        boundStatement = boundStatement.setBigDecimal(getParameterIndex(parameterIndex), x);
    }

    @Override
    public void setString(int parameterIndex, String x) throws SQLException {
        boundStatement = boundStatement.setString(getParameterIndex(parameterIndex), x);
    }

    @Override
    public void setBytes(int parameterIndex, byte[] x) throws SQLException {
        boundStatement = boundStatement.setByteBuffer(getParameterIndex(parameterIndex), ByteBuffer.wrap(x));
    }

    @Override
    public void setDate(int parameterIndex, Date x) throws SQLException {
//        boundStatement = boundStatement.setLocalDate(getParameterIndex(parameterIndex), x);
    }

    @Override
    public void setTime(int parameterIndex, Time x) throws SQLException {
//        boundStatement = boundStatement.setLocalTime(getParameterIndex(parameterIndex), x);
    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
        boundStatement = boundStatement.setInstant(getParameterIndex(parameterIndex), Instant.ofEpochMilli(x.getTime()));
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
//        boundStatement = boundStatement.setByte(getParameterIndex(parameterIndex), x);
    }

    @Override
    public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {

    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {

    }

    @Override
    public void clearParameters() throws SQLException {

    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
        boundStatement = boundStatement.setString(getParameterIndex(parameterIndex), JSON.toJSONString(x));
    }

    @Override
    public void setObject(int parameterIndex, Object x) throws SQLException {
        boundStatement = boundStatement.setString(getParameterIndex(parameterIndex), JSON.toJSONString(x));
    }

    @Override
    public boolean execute() throws SQLException {
        this.resultSet = cqlSession.execute(boundStatement);
        return true;
    }

    @Override
    public void addBatch() throws SQLException {

    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {

    }

    @Override
    public void setRef(int parameterIndex, Ref x) throws SQLException {

    }

    @Override
    public void setBlob(int parameterIndex, Blob x) throws SQLException {

    }

    @Override
    public void setClob(int parameterIndex, Clob x) throws SQLException {

    }

    @Override
    public void setArray(int parameterIndex, Array x) throws SQLException {

    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return null;
    }

    @Override
    public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {

    }

    @Override
    public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {

    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {

    }

    @Override
    public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {

    }

    @Override
    public void setURL(int parameterIndex, URL x) throws SQLException {

    }

    @Override
    public ParameterMetaData getParameterMetaData() throws SQLException {
        return null;
    }

    @Override
    public void setRowId(int parameterIndex, RowId x) throws SQLException {

    }

    @Override
    public void setNString(int parameterIndex, String value) throws SQLException {

    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {

    }

    @Override
    public void setNClob(int parameterIndex, NClob value) throws SQLException {

    }

    @Override
    public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {

    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {

    }

    @Override
    public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {

    }

    @Override
    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {

    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {

    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {

    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {

    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {

    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {

    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {

    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {

    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {

    }

    @Override
    public void setClob(int parameterIndex, Reader reader) throws SQLException {

    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {

    }

    @Override
    public void setNClob(int parameterIndex, Reader reader) throws SQLException {

    }

    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        return null;
    }

    @Override
    public int executeUpdate(String sql) throws SQLException {
        return 0;
    }

    @Override
    public void close() throws SQLException {

    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        return 0;
    }

    @Override
    public void setMaxFieldSize(int max) throws SQLException {

    }

    @Override
    public int getMaxRows() throws SQLException {
        return 0;
    }

    @Override
    public void setMaxRows(int max) throws SQLException {

    }

    @Override
    public void setEscapeProcessing(boolean enable) throws SQLException {

    }

    @Override
    public int getQueryTimeout() throws SQLException {
        return 0;
    }

    @Override
    public void setQueryTimeout(int seconds) throws SQLException {

    }

    @Override
    public void cancel() throws SQLException {

    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return null;
    }

    @Override
    public void clearWarnings() throws SQLException {

    }

    @Override
    public void setCursorName(String name) throws SQLException {

    }

    @Override
    public boolean execute(String sql) throws SQLException {
        return false;
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        return new CResultSet(resultSet);
    }

    @Override
    public int getUpdateCount() throws SQLException {
        return 0;
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        return false;
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {

    }

    @Override
    public int getFetchDirection() throws SQLException {
        return 0;
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {

    }

    @Override
    public int getFetchSize() throws SQLException {
        return resultSet.all().size();
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        return 0;
    }

    @Override
    public int getResultSetType() throws SQLException {
        return 0;
    }

    @Override
    public void addBatch(String sql) throws SQLException {

    }

    @Override
    public void clearBatch() throws SQLException {

    }

    @Override
    public int[] executeBatch() throws SQLException {
        return new int[0];
    }

    @Override
    public Connection getConnection() throws SQLException {
        return null;
    }

    @Override
    public boolean getMoreResults(int current) throws SQLException {
        return false;
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        return null;
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        return 0;
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        return 0;
    }

    @Override
    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        return 0;
    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        return false;
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        return false;
    }

    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        return false;
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        return 0;
    }

    @Override
    public boolean isClosed() throws SQLException {
        return false;
    }

    @Override
    public void setPoolable(boolean poolable) throws SQLException {

    }

    @Override
    public boolean isPoolable() throws SQLException {
        return false;
    }

    @Override
    public void closeOnCompletion() throws SQLException {

    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}
