package co.edu.uniquindio.reservasuq.controlador;

import co.edu.uniquindio.reservasuq.modelo.Persona;
import co.edu.uniquindio.reservasuq.modelo.enums.TipoPersona;
import co.edu.uniquindio.reservasuq.servicio.ServiciosReservasUQ;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PersonaControlador {

    @FXML
    private TextField txtCedula;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPassword;

    @FXML
    private ComboBox<TipoPersona> comboTipoPersona; // ComboBox para el tipo de persona

    @FXML
    private ListView<Persona> listViewPersonas;

    private final List<Persona> personas; // Lista para almacenar las personas
    private final ServiciosReservasUQ servicioReservas; // Servicio para gestionar reservas

    public PersonaControlador() {
        this.personas = new ArrayList<>();
        this.servicioReservas = ControladorPrincipal.getInstancia(); // Obtener la instancia del servicio
    }

    @FXML
    public void initialize() {
        listarPersonas(); // Llenar la ListView al iniciar
        inicializarComboBox(); // Inicializar el ComboBox con los tipos de persona
    }

    // Método para inicializar el ComboBox
    private void inicializarComboBox() {
        comboTipoPersona.getItems().addAll(TipoPersona.values());
    }

    // Método para registrar una nueva persona
    @FXML
    public void registrarPersona(ActionEvent actionEvent) {
        try {
            String cedula = txtCedula.getText();
            String nombre = txtNombre.getText();
            String email = txtEmail.getText();
            String password = txtPassword.getText();

            // Obtener el tipo de persona seleccionado
            TipoPersona tipoPersona = comboTipoPersona.getValue();
            if (tipoPersona == null) {
                throw new Exception("Debes seleccionar un tipo de persona.");
            }

            // Registrar usando el servicio
            servicioReservas.registrarPersona(cedula, nombre, tipoPersona, email, password);
            mostrarAlerta("Registro exitoso.", "Éxito", Alert.AlertType.INFORMATION);
            listarPersonas(); // Actualizar la lista
            navegarAReservas(tipoPersona); // Navegar a reservas
        } catch (Exception e) {
            mostrarAlerta(e.getMessage(), "Error", Alert.AlertType.ERROR);
        }
    }

    // Método para listar todas las personas en la ListView
    private void listarPersonas() {
        listViewPersonas.getItems().clear();
        listViewPersonas.getItems().addAll(personas);
    }

    // Método para navegar a la ventana de reservas
    private void navegarAReservas(TipoPersona tipoPersona) {
        ReservasControlador reservasControlador = new ReservasControlador();
        reservasControlador.setTipoPersona(tipoPersona); // Establecer el tipo de persona
        ControladorPrincipal.getInstancia().navegarVentana("reservas.fxml", "Reservas"); // Asume que tienes este método
    }

    // Método para mostrar alertas
    private void mostrarAlerta(String mensaje, String titulo, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
