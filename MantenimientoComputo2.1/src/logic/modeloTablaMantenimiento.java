/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logic;

import java.sql.SQLException;
import dataAccess.DataBaseManager;
import dataAccess.DiagnosticoDAO;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Migue
 */
public class modeloTablaMantenimiento {

    private int numeroSerie;
    private String nombreServicio;
    private String diagnostico;
    private String estado;
    private String encargado;
    private String fechaProgramada;
    private String fechaTermino;


    public modeloTablaMantenimiento(int numeroSerie, String nombreServicio, String diagnostico, String estado, String encargado, String fechaProgramada, String fechaTermino) {
        this.numeroSerie = numeroSerie;
        this.nombreServicio = nombreServicio;
        this.diagnostico = diagnostico;
        this.estado = estado;
        this.encargado = encargado;
        this.fechaProgramada = fechaProgramada;
        this.fechaTermino = fechaTermino;
    }
    
    
    public modeloTablaMantenimiento() {
    }



    public int getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(int numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }
    
    
    public String getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(String fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public String getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(String fechaTermino) {
        this.fechaTermino = fechaTermino;
    }
    
    public ObservableList<modeloTablaMantenimiento> getMantenimientos() throws SQLException {
        
        ObservableList<modeloTablaMantenimiento> studentsList = FXCollections.observableArrayList();
        DiagnosticoDAO diagnosticoDAO = new DiagnosticoDAO();

        try {
            ArrayList<modeloTablaMantenimiento> students = diagnosticoDAO.llenarTabla();
            for (modeloTablaMantenimiento student : students) {
                studentsList.add(new modeloTablaMantenimiento(student.getNumeroSerie(), student.getNombreServicio(), student.getDiagnostico(),
                    student.getEstado(), student.getEncargado(), student.getFechaProgramada(), student.getFechaTermino()));
            }
        } catch (SQLException exception) {
            Logger.getLogger(DataBaseManager.class.getName()).log(Level.SEVERE, null, exception);
        }

        return studentsList;
    }
    
}
