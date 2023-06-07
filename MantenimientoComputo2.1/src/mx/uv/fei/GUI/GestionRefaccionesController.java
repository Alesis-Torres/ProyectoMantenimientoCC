/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.uv.fei.GUI;

import dataAccess.EquipoDAO;
import dataAccess.RefaccionDAO;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import logic.AlertGenerator;
import logic.EquipoComputo;
import logic.Refaccion;

public class GestionRefaccionesController implements Initializable{
    EquipoComputo equipoActual;
    
    @FXML
    private TextField textFieldNumeroSerie;
    
    @FXML
    private ComboBox<String> comboBoxProveedor;
    
    @FXML
    private TableColumn columMarca;

    @FXML
    private TableColumn columMarcaAsignada;

    @FXML
    private TableColumn columModelo;

    @FXML
    private TableColumn columNumInvent;

    @FXML
    private TableColumn columNumeroSerie;

    @FXML
    private TableColumn columPiezas;

    @FXML
    private TableColumn columPiezasStock;

    @FXML
    private TableColumn columProcesador;

    @FXML
    private TableColumn columRAM;

    @FXML
    private TableColumn columRefaccion;

    @FXML
    private TableColumn columRefaccionAsignada;

    @FXML
    private ComboBox<String> comboBoxMarca;

    @FXML
    private Label labelEstadoEquipo;

    @FXML
    private Label labelNombreEquipo;

    @FXML
    private TabPane tabPaneRefacciones;

    @FXML
    private TableView<EquipoComputo> tableEquipos;

    @FXML
    private TableView<?> tableRefaccionesAsignadas;

    @FXML
    private TableView<?> tableRefaccionesSugeridas;

    @FXML
    private TextArea textAreaDescripcion;

    @FXML
    private TextArea textAreaDiagnostico;

    @FXML
    private TextField textFieldCantidadStock;

    @FXML
    private TextField textFieldModelo;

    @FXML
    private TextField textFieldNombreRefaccion;

    @FXML
    private TextField textFieldPrecio;
    
    
    @FXML
    void eventAñadirRefaccion(ActionEvent event) {

    }

    @FXML
    void eventEliminarRefaccion(ActionEvent event) {

    }

    @FXML
    void eventMostrarAsignar(ActionEvent event) {
        if(tableEquipos.getSelectionModel().getSelectedItem() != null){
            llenarDatosEquipo();
            tabPaneRefacciones.getSelectionModel().select(1);
        }else{
            AlertGenerator.showWarningAlert("Equipo no seleccionado", "Selecciona un equipo para continuar.");
        }
        
    }

    @FXML
    void eventMostrarRegistrar(ActionEvent event) {
        tabPaneRefacciones.getSelectionModel().select(0);
    }

    @FXML
    void eventRegistrarRefaccion(ActionEvent event) {
        if(checkFields()){
            AlertGenerator.showWarningAlert("Campos sin llenas", "Llene todos los campos para continuar");
        }else{
           Refaccion refaccion = new Refaccion();
           refaccion.setNombre(textFieldNombreRefaccion.getText());
           refaccion.setNumeroSerie(textFieldNumeroSerie.getText());
           refaccion.setMarca(comboBoxMarca.getSelectionModel().getSelectedItem());
           refaccion.setModelo(textFieldModelo.getText());
           refaccion.setPrecio(Integer.parseInt(textFieldPrecio.getText()));
           refaccion.setProveedor(comboBoxProveedor.getSelectionModel().getSelectedItem());
           refaccion.setCantidad(Integer.parseInt(textFieldCantidadStock.getText()));
           refaccion.setDescripcion(textAreaDescripcion.getText());
           RefaccionDAO refaccionDao = new RefaccionDAO();
           int success = 0;
           try{
               success = refaccionDao.registrarRefaccion(refaccion);
           }catch(SQLException ex){
               AlertGenerator.showErrorAlert("Error", "Se produjo un error al intentar registrar la nueva refaccion.");
               Logger.getLogger(GestionRefaccionesController.class.getName()).log(Level.SEVERE, null, ex);
           }   
           if(success == 1){ 
               AlertGenerator.showInformationAlert("Refaccion registrada", "¡La refaccion se ha registrado correctamente!");
               clearFields();
           }
        }
    }

    @FXML
    void eventSalir(ActionEvent event) {

    }

    @FXML
    void eventTerminarAsignacion(ActionEvent event) {
        
    }
    
    @FXML
    void eventMostrarEquipos(ActionEvent event) {
        tabPaneRefacciones.getSelectionModel().select(2);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assignNumberInputFilter(textFieldCantidadStock);
        assignNumberInputFilterWithPonit(textFieldPrecio);
        tabPaneRefacciones.getSelectionModel().select(2);
        comboBoxMarca.setItems(FXCollections.observableArrayList("Logitech", "Kingston", "Samsung","Dell","TP-Link"));
        comboBoxProveedor.setItems(FXCollections.observableArrayList("Proveedor A", "Proveedor B", "Proveedor C",  "Proveedor D"));
        try {
            llenarTablaEquipos();
        } catch (SQLException ex) {
            Logger.getLogger(GestionRefaccionesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void llenarDatosEquipo(){
        equipoActual = tableEquipos.getSelectionModel().getSelectedItem();
        textAreaDiagnostico.setText(equipoActual.getDiagnostico());
        labelNombreEquipo.setText(equipoActual.getModelo());
    }
    
    public boolean checkFields(){
        return "".equals(textFieldNombreRefaccion.getText()) || "".equals(textFieldNumeroSerie.getText()) || comboBoxMarca.getSelectionModel().getSelectedItem() == null || "".equals(textFieldModelo.getText()) || "".equals(textFieldPrecio.getText()) || comboBoxProveedor.getSelectionModel().getSelectedItem() == null ||"".equals(textFieldCantidadStock.getText()) || "".equals(textFieldNumeroSerie.getText()) || "".equals(textAreaDescripcion.getText());
    }
    
    private void assignNumberInputFilter(TextField textField) {
        textField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            String input = event.getCharacter();
            if (!input.matches("[0-9]")) {
                event.consume();
            }
        });
    }
    
    private void assignNumberInputFilterWithPonit(TextField textField) {
        textField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            String input = event.getCharacter();
            if (!input.matches("[0-9.]")) {
                event.consume();
            }
        });
    }
    
    public void clearFields(){
        textFieldNombreRefaccion.clear();
        textFieldNumeroSerie.clear();
        comboBoxMarca.setValue("Seleccione marca");
        textFieldModelo.clear();
        textFieldPrecio.clear();
        comboBoxProveedor.setValue("Seleccione proveedor");
        textFieldCantidadStock.clear();
        textAreaDescripcion.clear();
    }
    
    private void llenarTablaEquipos() throws SQLException{
        this.columNumeroSerie.setCellValueFactory(new PropertyValueFactory<>("marca"));
        this.columMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        this.columModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        this.columProcesador.setCellValueFactory(new PropertyValueFactory<>("procesador"));
        this.columNumInvent.setCellValueFactory(new PropertyValueFactory<>("numeroInventario"));
        EquipoDAO equipoDao = new EquipoDAO();
        List<EquipoComputo> listEquipos;
        listEquipos = equipoDao.equiposEnMantenimiento();
        ObservableList<EquipoComputo> list = FXCollections.observableArrayList(listEquipos);
        tableEquipos.setItems(list);
    }
}
