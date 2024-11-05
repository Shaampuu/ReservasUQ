package co.edu.uniquindio.reservasuq.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class PanelAdminControlador {

    // Este método gestiona la lógica para abrir la pantalla de "Gestionar Instalaciones"
    @FXML
    private void gestionarInstalaciones(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/reservasuq/vista/gestionarInstalaciones.fxml"));
            Pane root = loader.load();

            // Crear una nueva ventana o cargar el contenido en la ventana actual
            Stage stage = new Stage();
            stage.setTitle("Gestión de Instalaciones");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            mostrarError("Error al cargar la pantalla de Gestión de Instalaciones.");
            e.printStackTrace();
        }
    }

    // Este método gestiona la lógica para abrir la pantalla de "Ver Reservas"
    @FXML
    private void verReservas(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/reservasuq/vista/verReservas.fxml"));
            Pane root = loader.load();

            // Crear una nueva ventana o cargar el contenido en la ventana actual
            Stage stage = new Stage();
            stage.setTitle("Ver Reservas");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            mostrarError("Error al cargar la pantalla de Ver Reservas.");
            e.printStackTrace();
        }
    }

    // Este método cierra la sesión y vuelve a la pantalla de inicio de sesión
    @FXML
    private void cerrarSesion(ActionEvent event) {
        try {
            // Cerrar la ventana actual (la pantalla de administración)
            Stage stageActual = (Stage)((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stageActual.close();

            // Cargar la pantalla de inicio de sesión
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/reservasuq/vista/inicioSesion.fxml"));
            Pane root = loader.load();

            // Abrir la pantalla de inicio de sesión en una nueva ventana
            Stage stage = new Stage();
            stage.setTitle("Inicio de Sesión");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            mostrarError("Error al cerrar sesión y cargar la pantalla de Inicio de Sesión.");
            e.printStackTrace();
        }
    }

    // Método auxiliar para mostrar mensajes de error
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
