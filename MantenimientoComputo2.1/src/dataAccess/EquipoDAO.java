/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataAccess;

import java.util.ArrayList;
import logic.EquipoComputo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author samuel
 */
public class EquipoDAO {
    
    public ArrayList<EquipoComputo> equiposEnMantenimiento() throws SQLException{
        ArrayList<EquipoComputo> lista = new ArrayList<>();
        
        String query = "SELECT ec.*, d.descripcion FROM equipocomputo ec " +
                        "inner join diagnostico d on ec.idequipocomputo = d.equipo " +
                        "WHERE ec.estado = 'mantenimiento' and d.iddiagnostico > 0;";
        EquipoComputo object;
        DataBaseManager dataBaseManager = new DataBaseManager();
        Connection connection = dataBaseManager.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        
        while (resultSet.next()) {
            object = new EquipoComputo();
            object.setNumeroSerie(resultSet.getString("ec.numeroEquipo"));
            object.setMarca(resultSet.getString("ec.marca"));
            object.setModelo(resultSet.getString("ec.modelo"));
            object.setProcesador(resultSet.getString("ec.procesador"));
            object.setSistemaOperativo(resultSet.getString("ec.numeroInventario"));
            object.setDiagnostico(resultSet.getString("d.descripcion"));
            lista.add(object);
        }
        
        connection.close();
        
        return lista;
    }
}
