package co.edu.uniquindio.reservasuq.controlador;

import co.edu.uniquindio.reservasuq.modelo.*;
import co.edu.uniquindio.reservasuq.modelo.enums.TipoPersona;
import co.edu.uniquindio.reservasuq.servicio.ServiciosReservasUQ;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class    ReservasControlador {

    @FXML
    private TextField txtInstalacion;

    @FXML
    private TextField txtCedulaPersona;

    @FXML
    private TextField txtDiaReserva;

    @FXML
    private TextField txtHoraReserva;

    @FXML
    private ListView<Reserva> listViewReservas;

    private final ServiciosReservasUQ servicioReservas;
    // Método para establecer el tipo de persona
    @Setter
    private TipoPersona tipoPersona; // Campo para almacenar el tipo de persona

    public ReservasControlador() {
        this.servicioReservas = ControladorPrincipal.getInstancia(); // Obtener la instancia del servicio
    }

    @FXML
    public void initialize() {
        listarReservas(); // Llenar la ListView al iniciar
    }

    // Método para crear una nueva reserva
    @FXML
    public void crearReserva(ActionEvent actionEvent) {
        try {
            String idInstalacion = txtInstalacion.getText();
            String cedulaPersona = txtCedulaPersona.getText();
            LocalDate diaReserva = LocalDate.parse(txtDiaReserva.getText());
            String horaReserva = txtHoraReserva.getText();

            Reserva nuevaReserva = servicioReservas.crearReserva(idInstalacion, cedulaPersona, diaReserva, horaReserva);
            mostrarAlerta("Reserva creada con éxito. Tipo de persona: " + tipoPersona, "Éxito", Alert.AlertType.INFORMATION);
            listarReservas(); // Actualizar la lista
        } catch (Exception e) {
            mostrarAlerta(e.getMessage(), "Error", Alert.AlertType.ERROR);
        }
    }

    // Método para listar todas las reservas en la ListView
    private void listarReservas() {
        listViewReservas.getItems().clear();
        List<Reserva> reservas = servicioReservas.listarTodasReservas();
        listViewReservas.getItems().addAll(reservas);
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
