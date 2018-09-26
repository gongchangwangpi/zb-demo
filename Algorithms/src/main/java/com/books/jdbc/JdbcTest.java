package com.books.jdbc;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @author zhangbo
 */
public class JdbcTest {

    public static void main(String[] args) throws Exception {

        Context ctx = new InitialContext();
// Arguments for the database connection
        String dsName = "jdbc/DB";
// Get the data source and connection
        DataSource ds = (DataSource) ctx.lookup(dsName);
        Connection con = ds.getConnection("root", "root");
        
    }
    
}
