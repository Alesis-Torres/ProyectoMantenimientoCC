/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yusgu
 */
public class DataBaseManager {
  private Connection connection; 
    private final String DATABASE_NAME = "jdbc:mysql://127.0.0.1/mantenimientoequiposcomputo";
    private final String DATABASE_USER = "AdminCongress" ;
    private final String DATABASE_PASSWORD = "imAdmin@@Congressxx";
    
    public Connection getConnection() throws SQLException{
        connect();
        return connection;
    }
    
    private void connect() throws SQLException{
        connection = DriverManager.getConnection(DATABASE_NAME, DATABASE_USER, DATABASE_PASSWORD);
    }
    
    public void closeConnection(){
        if(connection!=null){
            try{
                if(!connection.isClosed()){
                    connection.close();
                }
            }catch(SQLException exception){
                   Logger.getLogger(DataBaseManager.class.getName()).log(Level.SEVERE, null, exception);
            }
         }
    }
}
