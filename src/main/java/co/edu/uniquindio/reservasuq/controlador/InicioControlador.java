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

    public void irIniciarSesion(ActionEvent actionEvent) {
        controladorPrincipal.navegarVentana("/login.fxml", "Iniciar Sesión");
    }

    public void irRegistroCliente(ActionEvent actionEvent) {
        controladorPrincipal.navegarVentana("/registro.fxml", "Registro Persona");
    }

    // Método para abrir la ventana de crear reserva
    public void abrirCrearReserva(ActionEvent event) {
        controladorPrincipal.navegarVentana("crearReserva.fxml", "Crear Reserva");
    }

    // Método para abrir la ventana de ver reservas
    public void verMisReservas(ActionEvent event) {
        try {
            controladorPrincipal.navegarVentana("misReservas.fxml", "Mis Reservas");
        } catch (Exception e) {
            controladorPrincipal.mostrarAlerta("Error al abrir la ventana de Mis Reservas.", "Error", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void cerrarSesion(ActionEvent event) {
        try {
            // Cerrar la ventana actual
            Stage stageActual = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
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
            controladorPrincipal.mostrarAlerta("Error al cerrar sesión y cargar la pantalla de Inicio de Sesión.", "Error", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
}
