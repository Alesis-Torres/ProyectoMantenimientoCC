/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import logic.Diagnostico;
import logic.Servicio;
import logic.modeloTablaMantenimiento;

/**
 *
 * @author Migue
 */
public class DiagnosticoDAO {
    
    
    public int registrarDiagnostico(Diagnostico diagnostico) throws SQLException {

        int result = 0;
        String query = "insert into Diagnostico(tipoServicio,tipoProblema,descripcion, equipo, fechaProgramada) "
            + "values (?,?,?,?,?);";
        DataBaseManager dataBaseManager = new DataBaseManager();
        Connection connection = dataBaseManager.getConnection();
        
        try{
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, diagnostico.getServicio());
            statement.setInt(2, diagnostico.getProblema());
            statement.setString(3, diagnostico.getDiagnostico());
            statement.setInt(4, diagnostico.getNumeroEquipo()); 
            statement.setString(5, diagnostico.getFecha());
            result = statement.executeUpdate();
            connection.commit();
        } catch (SQLException sqlException) {
            connection.rollback();
            throw sqlException;
        } finally {
            dataBaseManager.closeConnection();
        }
        
        return result;
        
    }
    
    public int registrarServicio(int idEquipo) throws SQLException {

        int result = 0;
        String query = "insert into Servicio(estado, equipo) values (?,?);";
        DataBaseManager dataBaseManager = new DataBaseManager();
        Connection connection = dataBaseManager.getConnection();
        
        try{
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "Sin Asignar");
            statement.setInt(2, idEquipo);
            result = statement.executeUpdate();
            connection.commit();
        } catch (SQLException sqlException) {
            connection.rollback();
            throw sqlException;
        } finally {
            dataBaseManager.closeConnection();
        }
        
        return result;
        
    }
    
    public int registrarRefaccion(int idRefaccion, int idDiagnostico) throws SQLException {

        int result = 0;
        String query = "insert into refaccionesDiagnostico(refaccion, diagnostico) values (?,?);";
        DataBaseManager dataBaseManager = new DataBaseManager();
        Connection connection = dataBaseManager.getConnection();
        
        try{
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idRefaccion);
            statement.setInt(2, idDiagnostico);
            result = statement.executeUpdate();
            connection.commit();
        } catch (SQLException sqlException) {
            connection.rollback();
            throw sqlException;
        } finally {
            dataBaseManager.closeConnection();
        }
        
        return result;
        
    }
    
    public ArrayList<modeloTablaMantenimiento> llenarTabla() throws SQLException{
        
        ArrayList<modeloTablaMantenimiento> lista = new ArrayList<>();
        
        String query = "SELECT equipocomputo.numeroEquipo, tipoServicio.nombreServicio, diagnostico.descripcion, diagnostico.fechaProgramada, servicio.estado, servicio.fechaTermino, CONCAT(usuario.nombre, ' ', usuario.apellidoPaterno) AS encargado " +
                       "FROM equipocomputo INNER JOIN diagnostico ON equipocomputo.idequipocomputo = diagnostico.equipo INNER JOIN tipoServicio ON tipoServicio.idtipoServicio = diagnostico.tipoServicio " +
                       "INNER JOIN servicio ON equipocomputo.idequipocomputo = servicio.equipo LEFT JOIN usuario ON servicio.encargado = usuario.idUsuario;";
        
        DataBaseManager dataBaseManager = new DataBaseManager();
        Connection connection = dataBaseManager.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        
        while (resultSet.next()) {
            modeloTablaMantenimiento object = new modeloTablaMantenimiento();
            object.setNumeroSerie(resultSet.getInt("equipocomputo.numeroEquipo"));
            object.setNombreServicio(resultSet.getString("tipoServicio.nombreServicio"));
            object.setDiagnostico(resultSet.getString("diagnostico.descripcion"));
            object.setEstado(resultSet.getString("servicio.estado"));
            object.setEncargado(resultSet.getString("encargado"));
            object.setFechaProgramada(resultSet.getString("diagnostico.fechaProgramada"));
            object.setFechaTermino(resultSet.getString("servicio.fechaTermino"));
            lista.add(object);
        }
        
        connection.close();
        
        return lista;
    }
    
    public int getIdEquipo(int numeroEquipo) throws SQLException {
        String query = "Select idequipocomputo from equipocomputo where numeroEquipo = ?;";
        DataBaseManager dataBaseManager = new DataBaseManager();
        int idEquipo  = 0;
        
        try {
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, numeroEquipo);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                idEquipo = resultSet.getInt("idequipocomputo");
            }
        } catch (SQLException sqlException) {
            throw sqlException;
        } finally {
            dataBaseManager.closeConnection();
        }    
        
        return idEquipo;
    }
    
    
    
    public int terminarMantenimineto(int idEquipoComputo, String fechaTermino) throws SQLException {
        int result = 0;
        String query = "UPDATE servicio SET estado = 'concluido', fechaTermino = ? WHERE equipo = ? and estado = 'Sin Asignar';";
        DataBaseManager dataBaseManager = new DataBaseManager();
        Connection connection = dataBaseManager.getConnection();
        
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, fechaTermino);
            statement.setInt(2, idEquipoComputo);
            result = statement.executeUpdate();
            connection.commit();
        } catch (SQLException sqlException) {
            connection.rollback();
            throw sqlException;
        } finally {
            dataBaseManager.closeConnection();
        }
        
        return result;
    }
    
    public int ultimoID() throws SQLException{
        int result = 0;
        String query = "SELECT MAX(iddiagnostico) AS ultimo_id FROM diagnostico;";
        DataBaseManager dataBaseManager = new DataBaseManager();
        
        try {
            Connection connection = dataBaseManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                result = resultSet.getInt("ultimo_id");
             }
        } catch (SQLException sqlException) {
            throw sqlException;
        } finally {
            dataBaseManager.closeConnection();
        }
        
        return result;
    }
    
    public int obtenerIdRefaccion(String nombreRefaccion) throws SQLException {
      
        String query = "SELECT idRefaccion FROM refaccion WHERE nombre = ?;";
        int idNombreRefaccion = 0;
        DataBaseManager dataBaseManager = new DataBaseManager();
        
        try {
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nombreRefaccion);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
               idNombreRefaccion = resultSet.getInt("idRefaccion");
            }
        } catch (SQLException sqlException) {
            throw sqlException;
        } finally {
            dataBaseManager.closeConnection();
        }
       
        return idNombreRefaccion;
    }
}
