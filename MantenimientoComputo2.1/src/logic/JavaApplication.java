/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.stage.Modality;



public class JavaApplication extends Application {
    private static Scene scene;
    private static Stage currentStage;
    public static Stage secundaryStage;

    
    public static void main(String[] args) {
        launch();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(JavaApplication.class.getResource(fxml));
        return fxmlLoader.load();
    }


    public static void changeView(String url, int width, int height) throws IOException {
        currentStage = (Stage) scene.getWindow();
        configureStage(currentStage, width, height);
        JavaApplication.setRoot(url);
    }
    public static void showSecondView(String url,int width, int height) throws IOException {
        secundaryStage = new Stage();
        secundaryStage.setTitle("Ventana Secundaria");
        secundaryStage.initModality(Modality.WINDOW_MODAL);
        secundaryStage.initOwner(currentStage);
        Scene scene1 = new Scene(loadFXML(url), width, height);
        secundaryStage.setScene(scene1);
        secundaryStage.setResizable(false);
        secundaryStage.showAndWait(); 
        
    }
    public static void closeSecondView() throws IOException{
        secundaryStage.close();
    }

    private static void configureStage(Stage stage, int width, int height) {
        stage.setWidth(width);
        stage.setHeight(height);
        stage.centerOnScreen();
    }

    @Override
    public void start(Stage stage) throws IOException {
        //Image icon = new Image(getClass().getResourceAsStream("C:/Users/hp/Desktop/Tareas cuarto semestre/Proyecto construccion/ProyectoFinalCons/src/views/images/iconREMSGP.jpg"));
        scene = new Scene(loadFXML("../views/VentanaMantenimientos.fxml"), 820, 630);
        stage.setTitle("Mantenimiento");
        stage.setScene(scene);
        stage.setResizable(false);
        //stage.getIcons().add(icon);
        stage.show();
    }
}