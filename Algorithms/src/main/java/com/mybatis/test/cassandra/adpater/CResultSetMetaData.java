package com.mybatis.test.cassandra.adpater;

import com.datastax.oss.driver.api.core.cql.ColumnDefinitions;
import com.datastax.oss.driver.api.core.cql.ResultSet;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * @author zhangbo
 * @date 2020/6/19
 */
public class CResultSetMetaData implements ResultSetMetaData {

    private com.datastax.oss.driver.api.core.cql.ResultSet resultSet;
    private ColumnDefinitions columnDefinitions;

    public CResultSetMetaData(ResultSet resultSet) {
        this.resultSet = resultSet;
        this.columnDefinitions = resultSet.getColumnDefinitions();
    }

    @Override
    public int getColumnCount() throws SQLException {
        return columnDefinitions.size();
    }

    @Override
    public boolean isAutoIncrement(int column) throws SQLException {
        return false;
    }

    @Override
    public boolean isCaseSensitive(int column) throws SQLException {
        return false;
    }

    @Override
    public boolean isSearchable(int column) throws SQLException {
        return false;
    }

    @Override
    public boolean isCurrency(int column) throws SQLException {
        return false;
    }

    @Override
    public int isNullable(int column) throws SQLException {
        return 0;
    }

    @Override
    public boolean isSigned(int column) throws SQLException {
        return false;
    }

    @Override
    public int getColumnDisplaySize(int column) throws SQLException {
        return 0;
    }

    @Override
    public String getColumnLabel(int column) throws SQLException {
        return columnDefinitions.get(column - 1).getName().asInternal();
    }

    @Override
    public String getColumnName(int column) throws SQLException {
        return columnDefinitions.get(column - 1).getName().asInternal();
    }

    @Override
    public String getSchemaName(int column) throws SQLException {
        return columnDefinitions.get(column - 1).getTable().asInternal();
    }

    @Override
    public int getPrecision(int column) throws SQLException {
        return 0;
    }

    @Override
    public int getScale(int column) throws SQLException {
        return 0;
    }

    @Override
    public String getTableName(int column) throws SQLException {
        return columnDefinitions.get(column - 1).getTable().asInternal();
    }

    @Override
    public String getCatalogName(int column) throws SQLException {
        return null;
    }

    @Override
    public int getColumnType(int column) throws SQLException {
        return columnDefinitions.get(column - 1).getType().getProtocolCode();
    }

    @Override
    public String getColumnTypeName(int column) throws SQLException {
        return null;
    }

    @Override
    public boolean isReadOnly(int column) throws SQLException {
        return false;
    }

    @Override
    public boolean isWritable(int column) throws SQLException {
        return false;
    }

    @Override
    public boolean isDefinitelyWritable(int column) throws SQLException {
        return false;
    }

    @Override
    public String getColumnClassName(int column) throws SQLException {
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
}
