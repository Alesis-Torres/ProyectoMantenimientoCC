/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mx.uv.fei.GUI;

import dataAccess.DiagnosticoDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.Diagnostico;
import logic.modeloTablaMantenimiento;
import mantenimientocomputo.MantenimientoComputo;

/**
 * FXML Controller class
 *
 * @author Migue
 */
public class VentanaMantenimientosController implements Initializable {

    @FXML
    private TableView<modeloTablaMantenimiento> tblMantenimientos;
    @FXML
    private TableColumn<modeloTablaMantenimiento, String> tblNumEquipo;
    @FXML
    private TableColumn<modeloTablaMantenimiento, String> tblTipoMantenimiento;
    @FXML
    private TableColumn<modeloTablaMantenimiento, String> tblDiagnostico;
    @FXML
    private TableColumn<modeloTablaMantenimiento, String> tblEstado;
    @FXML
    private TableColumn<modeloTablaMantenimiento, String> tblTecnico;
    @FXML
    private Button butMantenimiento;
    @FXML
    private Button butTerminar;
    @FXML
    private Button butSalir;
    @FXML
    private TableColumn<modeloTablaMantenimiento, String> tblFechaProgramada;
    @FXML
    private TableColumn<modeloTablaMantenimiento, String> tblFechaTermino;
    @FXML
    private Button butSalir1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modeloTablaMantenimiento modeloTablaMantenimiento1 = new modeloTablaMantenimiento();
        try {
            addStudentsTable(modeloTablaMantenimiento1.getMantenimientos());
        } catch (SQLException ex) {
            Logger.getLogger(VentanaMantenimientosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addStudentsTable(ObservableList<modeloTablaMantenimiento> studentslist){

        this.tblNumEquipo.setCellValueFactory(new PropertyValueFactory<>("numeroSerie"));
        this.tblTipoMantenimiento.setCellValueFactory(new PropertyValueFactory<>("nombreServicio"));
        this.tblDiagnostico.setCellValueFactory(new PropertyValueFactory<>("diagnostico"));
        this.tblEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        this.tblTecnico.setCellValueFactory(new PropertyValueFactory<>("encargado"));
        this.tblFechaProgramada.setCellValueFactory(new PropertyValueFactory<>("fechaProgramada"));
        this.tblFechaTermino.setCellValueFactory(new PropertyValueFactory<>("fechaTermino"));
        tblMantenimientos.setItems(studentslist);
    }
    
    
    public int getSelectedItemId(){
        
        TablePosition position = tblMantenimientos.getSelectionModel().getSelectedCells().get(0);
        int row = position.getRow();
        modeloTablaMantenimiento item = tblMantenimientos.getItems().get(row);
        int courseNrc = (Integer) tblMantenimientos.getColumns().get(0).getCellObservableValue(item).getValue();

        return courseNrc;
        
    }
    
    @FXML
    private void concluirMantenimiento(ActionEvent event) throws IOException, SQLException {
        DiagnosticoDAO diagnosticoDAO = new DiagnosticoDAO();
        int id = diagnosticoDAO.getIdEquipo(getSelectedItemId());
        LocalDate fechaActual = LocalDate.now();
        String fechaActualString = fechaActual.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        diagnosticoDAO.terminarMantenimineto(id, fechaActualString);
        modeloTablaMantenimiento modeloTablaMantenimiento1 = new modeloTablaMantenimiento();
        addStudentsTable(modeloTablaMantenimiento1.getMantenimientos());
    }
    
    @FXML
    private void hacerDiagnostico(ActionEvent event) throws IOException {
        MantenimientoComputo.setRoot("/mx/uv/fei/GUI/ventanaDiagnostico");
    }

    
}
