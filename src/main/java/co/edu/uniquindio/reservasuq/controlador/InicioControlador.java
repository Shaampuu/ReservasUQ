package co.edu.uniquindio.reservasuq.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class InicioControlador {

    private final ControladorPrincipal controladorPrincipal;

    public InicioControlador() {
        this.controladorPrincipal = ControladorPrincipal.getInstancia();
    }

    // Método para navegar a la ventana de inicio de sesión
    public void irIniciarSesion(ActionEvent actionEvent) {
        controladorPrincipal.navegarVentana("/InicioSesion.fxml", "Iniciar Sesión");
    }

    // Método para navegar a la ventana de registro de persona
    public void irRegistroCliente(ActionEvent actionEvent) {
        controladorPrincipal.navegarVentana("/registro.fxml", "Registro Persona");
    }

    // Método para abrir la ventana de creación de reserva
    public void abrirCrearReserva(ActionEvent event) {
        controladorPrincipal.navegarVentana("/CrearReserva.fxml", "Crear Reserva");
    }

    // Método para abrir la ventana de visualización de reservas
    public void verMisReservas(ActionEvent event) {
        controladorPrincipal.navegarVentana("/Principal.fxml", "Mis Reservas");
    }

    // Método para cerrar sesión y regresar a la pantalla de inicio de sesión
    @FXML
    private void cerrarSesion(ActionEvent event) {
        try {
            // Cierra la ventana actual
            Stage stageActual = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stageActual.close();

            // Carga la pantalla de inicio de sesión
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/InicioSesion.fxml"));
            Pane root = loader.load();

            // Abre la pantalla de inicio de sesión en una nueva ventana
            Stage stage = new Stage();
            stage.setTitle("Inicio de Sesión");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            controladorPrincipal.mostrarAlerta("Error al cerrar sesión y cargar la pantalla de Inicio de Sesión.", "Error", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
}
