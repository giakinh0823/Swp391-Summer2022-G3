/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.context;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author giaki
 */
public abstract class DBContext<T> {
    protected Connection connection;

    public DBContext() {
        try {
//            String url = "jdbc:sqlserver://swp391.c8xia3l9exxk.ap-southeast-1.rds.amazonaws.com:1433;databaseName=swp_learning";
            
            String username = "sa";
            String password = "12345678";
            String url = "jdbc:sqlserver://localhost:1433;databaseName=swp_learning";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public abstract ArrayList<T> list();

    public abstract T get(int id);

    public abstract T insert(T model);

    public abstract void update(T model);

    public abstract void delete(int id);
}
