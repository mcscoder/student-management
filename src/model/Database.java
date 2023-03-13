/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mcsmuscle
 */
public class Database {
    private Connection conn;
    
    public Database() {
        conn = getDBConnection();
    }
    
    
    public static Connection getDBConnection() {
        try {
            Connection conn = null;
            
            final String HOST = "localhost:3306";
            final String DB_URL = String.format("jdbc:mysql://%s/student_management", HOST);
            final String DB_USER = "root";
            final String DB_PASSWORD = "mcs";
//            final String DB_URL = "jdbc:mysql://sql.freedb.tech:3306/freedb_student_mngt";
//            final String DB_USER = "freedb_mcsmuscle";
//            final String DB_PASSWORD = "aSP%UThbn8mXECF";
            
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            return conn;
        } catch (Exception ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Connection database failed");
        }
        return null;
    }
    
    public ResultSet executeQuery(String sqlStatement, Object... objects) {
        ResultSet rs = null;
        try {
            PreparedStatement stmt = conn.prepareStatement(sqlStatement);
            
            for(int i = 1; i <= objects.length; i++) {
                stmt.setObject(i, objects[i - 1]);
            }
            
            rs = stmt.executeQuery();
            
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
    
    public void execute(String sqlStatement, Object... objects) {
        try {
            PreparedStatement stmt = conn.prepareStatement(sqlStatement);
            
            for(int i = 1; i <= objects.length; i++) {
                stmt.setObject(i, objects[i - 1]);
            }
            
            stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        Database.getDBConnection();
    }
}
