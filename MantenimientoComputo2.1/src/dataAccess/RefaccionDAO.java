/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataAccess;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logic.Refaccion;

/**
 *
 * @author Migue
 */
public class RefaccionDAO {
    
    public ArrayList<String> getRefaciones() throws SQLException {
        ArrayList<String> lista = new ArrayList<>();
        String query = "SELECT nombre FROM refaccion";
        Connection connection = new DataBaseManager().getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String aux = resultSet.getString("nombre");
            lista.add(aux);
        }
        connection.close();
        return lista;
    }
    

    public int registrarRefaccion(Refaccion refaccion) throws SQLException {
        int result = 0;
            String query = "insert into refaccion(nombre,numeroSerie,marca,modelo, precio, proveedor,  cantidad, descripcion) values(?,?,?,?,?,?,?,?)";
            DataBaseManager dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, refaccion.getNombre());
            statement.setString(2, refaccion.getNumeroSerie());
            statement.setString(3, refaccion.getMarca());
            statement.setString(4, refaccion.getModelo());
            statement.setDouble(5, refaccion.getPrecio());
            statement.setString(6, refaccion.getProveedor());
            statement.setInt(7, refaccion.getCantidad());
            statement.setString(8, refaccion.getDescripcion());
            result = statement.executeUpdate();
            connection.close();
            return result;
    }
    
}
