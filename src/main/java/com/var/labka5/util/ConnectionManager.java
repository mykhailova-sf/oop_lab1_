package com.var.labka5.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private final static String DB_USER = "postgres";
    private final static String DB_URL = "jdbc:postgresql://host.docker.internal:5434/postgres";
    private final static String DB_PASSWORD = "root";

    public static Connection open() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(DB_URL, DB_USER,DB_PASSWORD);
    }
}
