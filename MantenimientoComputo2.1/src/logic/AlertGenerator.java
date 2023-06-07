/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logic;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertGenerator {

    public static void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static void showInformationAlert(String title, String message) {
        showAlert(AlertType.INFORMATION, title, message);
    }
    
    public static void showWarningAlert(String title, String message) {
        showAlert(AlertType.WARNING, title, message);
    }
    
    public static void showErrorAlert(String title, String message) {
        showAlert(AlertType.ERROR, title, message);
    }
}
