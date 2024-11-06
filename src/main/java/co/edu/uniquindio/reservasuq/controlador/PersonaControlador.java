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
    private ComboBox<TipoPersona> comboTipoPersona;

    @FXML
    private ListView<Persona> listViewPersonas;

    private final ServiciosReservasUQ servicioReservas;

    public PersonaControlador() {
        this.servicioReservas = ControladorPrincipal.getInstancia();
    }

    @FXML
    public void initialize() {
        listarPersonas();
        inicializarComboBox();
    }

    private void inicializarComboBox() {
        comboTipoPersona.getItems().addAll(TipoPersona.values());
    }

    @FXML
    public void registrarPersona(ActionEvent actionEvent) {
        try {
            if (!validarCampos()) return;

            String cedula = txtCedula.getText();
            String nombre = txtNombre.getText();
            String email = txtEmail.getText();
            String password = txtPassword.getText();
            TipoPersona tipoPersona = comboTipoPersona.getValue();

            servicioReservas.registrarPersona(cedula, nombre, tipoPersona, email, password);
            mostrarAlerta("Registro exitoso.", "Éxito", Alert.AlertType.INFORMATION);

            listarPersonas();
            navegarAReservas(tipoPersona);
        } catch (Exception e) {
            mostrarAlerta(e.getMessage(), "Error", Alert.AlertType.ERROR);
        }
    }

    private void listarPersonas() {
        listViewPersonas.getItems().clear();
        List<Persona> personas = servicioReservas.obtenerPersonas();
        listViewPersonas.getItems().addAll(personas);
    }

    private boolean validarCampos() {
        if (txtCedula.getText().isEmpty() || txtNombre.getText().isEmpty() || txtEmail.getText().isEmpty() || txtPassword.getText().isEmpty()) {
            mostrarAlerta("Todos los campos son obligatorios.", "Error de validación", Alert.AlertType.ERROR);
            return false;
        }
        if (comboTipoPersona.getValue() == null) {
            mostrarAlerta("Debes seleccionar un tipo de persona.", "Error de validación", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    private void navegarAReservas(TipoPersona tipoPersona) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ruta/a/reservas.fxml"));
            Pane root = loader.load();
            ReservasControlador reservasControlador = loader.getController();
            reservasControlador.setTipoPersona(tipoPersona);

            Stage stage = new Stage();
            stage.setTitle("Reservas");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            mostrarAlerta("No se pudo cargar la ventana de reservas.", "Error", Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String mensaje, String titulo, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
