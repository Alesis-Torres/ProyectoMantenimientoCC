/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mx.uv.fei.GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import mantenimientocomputo.MantenimientoComputo;

/**
 * FXML Controller class
 *
 * @author Migue
 */
public class MainPrincipalController implements Initializable {



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void hacerDiagnostico(ActionEvent event) throws IOException {
        MantenimientoComputo.setRoot("/mx/uv/fei/GUI/VentanaMantenimientos");
    }

    @FXML
    private void eventRefacciones(ActionEvent event) throws IOException {
        MantenimientoComputo.setRoot("/mx/uv/fei/GUI/GestionRefacciones");
        //MantenimientoComputo.changeView("../GUI/ventanaDiagnostico.fxml", 830, 640);
    }    
    
}
