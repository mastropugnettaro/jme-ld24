package com.teamjmonkey.file;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public final class DbConnection {

    private Connection connection;

    public DbConnection(String url) throws Exception {
       createConnection(url);
    }

    public void createConnection(String url) throws Exception {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection(url);
    }

    public Connection getConnection(){
        return connection;
    }

    public void closeCon(){
        try {
            connection.close();
        }
        catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

}