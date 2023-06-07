/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mx.uv.fei.GUI;

import dataAccess.DataBaseManager;
import dataAccess.DiagnosticoDAO;
import dataAccess.RefaccionDAO;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ToggleGroup;
import logic.Diagnostico;
import mantenimientocomputo.MantenimientoComputo;

/**
 * FXML Controller class
 *
 * @author Migue
 */
public class VentanaDiagnosticoController implements Initializable {

    @FXML
    private RadioButton rbSoftware;
    @FXML
    private RadioButton rbHardware;
    @FXML
    private TextArea txtaDiagnostico;
    @FXML
    private TextField txtNumeroEquipo;
    @FXML
    private RadioButton rbPreventivo;
    @FXML
    private RadioButton rbCorrectivo;
    @FXML
    private ComboBox<String> cbRefaciones;
    @FXML
    private Button butRegistrar;
    @FXML
    private Button butSalir;
    @FXML
    private Button butAgregarRefaccion;
    @FXML
    private DatePicker dtpFecha;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillResponsibleComboBoxes();
        agruparRafioButtons();
    }
    
    ToggleGroup toggleGroup = new ToggleGroup();
    ToggleGroup toggleGroup2 = new ToggleGroup();

    public void fillResponsibleComboBoxes() {
        
        RefaccionDAO userDao = new RefaccionDAO();
        ArrayList<String> teacherList = null;
        ObservableList<String> listaRefaciones;
        
        try {
            teacherList = userDao.getRefaciones();
        } catch (SQLException exception) {

        }
        listaRefaciones = FXCollections.observableArrayList(teacherList);
        cbRefaciones.setItems(listaRefaciones);
        
    }
    
    ArrayList<String> listaRefaciones = new ArrayList<>();
        
    @FXML
    private void agregarRefaciones(ActionEvent event) throws IOException, SQLException {
       
        if(!cbRefaciones.getSelectionModel().isEmpty()){
            String responsible = cbRefaciones.getSelectionModel().getSelectedItem().toString();
        
            if(!listaRefaciones.contains(responsible)){
                listaRefaciones.add(responsible);
            }
            
        }
    }
    
    public void agruparRafioButtons(){
        rbSoftware.setToggleGroup(toggleGroup);
        rbHardware.setToggleGroup(toggleGroup);
        
        rbPreventivo.setToggleGroup(toggleGroup2);
        rbCorrectivo.setToggleGroup(toggleGroup2);
    }
    
    private int obtenerTipoProblema(){
        int result = 0;
        if(rbSoftware.isSelected()){
            result = 1;
        } else if (rbHardware.isSelected()){
            result = 2;
        }
        
        return result;
    }
    
    private int obtenerTipoMantenimiento(){
        int result = 0;
        if(rbPreventivo.isSelected()){
            result = 1;
        } else if (rbCorrectivo.isSelected()){
            result = 2;
        }
        
        return result;
    }
    
    private Diagnostico prepararDiagnostico() throws SQLException{
        DiagnosticoDAO diagnosticoDAO = new DiagnosticoDAO();
        Diagnostico nuevoDiagnostico = new Diagnostico();
        int id = diagnosticoDAO.getIdEquipo(Integer.parseInt(txtNumeroEquipo.getText()));
        nuevoDiagnostico.setDiagnostico(txtaDiagnostico.getText());
        nuevoDiagnostico.setNumeroEquipo(id);
        nuevoDiagnostico.setProblema(obtenerTipoProblema());
        nuevoDiagnostico.setServicio(obtenerTipoMantenimiento());
        LocalDate selectedDate = dtpFecha.getValue();
        String formattedDate = selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        nuevoDiagnostico.setFecha(formattedDate);
        
        return nuevoDiagnostico;
    }
    
    @FXML
    private void registrarDiagnostico(ActionEvent event) throws IOException {
        DiagnosticoDAO diagnosticoDAO = new DiagnosticoDAO();
        try {
            diagnosticoDAO.registrarDiagnostico(prepararDiagnostico());
            Diagnostico nuevoDiagnostico = new Diagnostico();
            int idEquipo = diagnosticoDAO.getIdEquipo(Integer.parseInt(txtNumeroEquipo.getText()));
            diagnosticoDAO.registrarServicio(idEquipo);
            
            int idDiagnostico = diagnosticoDAO.ultimoID();
            
            for(int i = 0; i < listaRefaciones.size(); i++) {
                int idRefaccion = diagnosticoDAO.obtenerIdRefaccion(listaRefaciones.get(i));
                diagnosticoDAO.registrarRefaccion(idRefaccion, idDiagnostico);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(VentanaDiagnosticoController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    
}
