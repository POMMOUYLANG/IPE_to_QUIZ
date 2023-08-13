/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author hocin
 */
public class DbConnect {
    
    private static String HOST = "127.0.0.1";
        private static int PORT = 3306;
        private static String DB_NAME = "booklist";
        private static String USERNAME = "root";
        private static String PASSWORD = "";
        private static Connection connection ;
        
        
        public static Connection getConnect2 (){
        try {
            connection = DriverManager.getConnection(String.format("jdbc:mysql://%s:%d/%s", HOST,PORT,DB_NAME),USERNAME,PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(DbConnect.class.getName()).log(Level.SEVERE, null, ex);
        }

            return  connection;
        }
        public static Connection getConnect() throws SQLException {
            String url = "jdbc:mysql://localhost:3306/librarymanagement";
            String username = "root";
            String password = "";
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("connected");
            return connection;
        }


        
        
        

    
}
