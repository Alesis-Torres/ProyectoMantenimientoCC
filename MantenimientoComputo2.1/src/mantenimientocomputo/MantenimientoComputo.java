/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package mantenimientocomputo;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Migue
 */
public class MantenimientoComputo extends Application{

    /**
     * @param args the command line arguments
     */
    
    private static Scene scenePr;
    
    @Override
    public void start(Stage stage) throws IOException {
        scenePr = new Scene(loadFXML("/mx/uv/fei/GUI/MainPrincipal"));
        stage.setScene(scenePr);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scenePr.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MantenimientoComputo.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    public static void changeView(String url, int width, int height) throws IOException {
        Stage currentStage = (Stage) scenePr.getWindow();
        configureStage(currentStage, width, height);
        MantenimientoComputo.setRoot(url);
    }
    
    private static void configureStage(Stage stage, int width, int height) {
        stage.setWidth(width);
        stage.setHeight(height);
        stage.centerOnScreen();
    }
    
    public static void main(String[] args) {
        launch();
    }
    
}
