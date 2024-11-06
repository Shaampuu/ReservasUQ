package co.edu.uniquindio.reservasuq.controlador;

import co.edu.uniquindio.reservasuq.modelo.Reserva;
import co.edu.uniquindio.reservasuq.modelo.enums.TipoPersona;
import co.edu.uniquindio.reservasuq.servicio.ServiciosReservasUQ;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

public class ReservasControlador {

    @FXML
    private ComboBox<String> comboInstalacion;
    @FXML
    private DatePicker dateReserva;
    @FXML
    private TextField txtHora;
    @FXML
    private ListView<Reserva> listViewReservas;

    private final ServiciosReservasUQ servicioReservas;

    @Setter
    private TipoPersona tipoPersona;

    public ReservasControlador() {
        this.servicioReservas = ControladorPrincipal.getInstancia();
    }

    @FXML
    public void initialize() {
        if (tipoPersona == null) {
            tipoPersona = ControladorPrincipal.getUsuarioActual().getTipoPersona(); // Asignar el tipo si no se ha hecho
        }
        llenarComboInstalaciones();
        listarReservas();
        System.out.println("TipoPersona en ReservasControlador: " + tipoPersona); // Depuración
    }

    private void llenarComboInstalaciones() {
        List<String> instalaciones = servicioReservas.obtenerNombresInstalaciones();
        comboInstalacion.getItems().setAll(instalaciones);
    }

    @FXML
    public void crearReserva(ActionEvent actionEvent) {
        if (comboInstalacion.getValue() == null || dateReserva.getValue() == null || txtHora.getText().isEmpty()) {
            mostrarAlerta("Todos los campos son obligatorios.", "Error de validación", Alert.AlertType.ERROR);
            return;
        }

        try {
            String idInstalacion = comboInstalacion.getValue();
            LocalDate diaReserva = dateReserva.getValue();
            String horaReserva = txtHora.getText();

            Reserva nuevaReserva = servicioReservas.crearReserva(idInstalacion, ControladorPrincipal.getUsuarioActual().getCedula(), diaReserva, horaReserva);
            mostrarAlerta("Reserva creada con éxito. Tipo de persona: " + tipoPersona, "Éxito", Alert.AlertType.INFORMATION);

            listarReservas();
        } catch (Exception e) {
            mostrarAlerta(e.getMessage(), "Error", Alert.AlertType.ERROR);
        }
    }

    private void listarReservas() {
        listViewReservas.getItems().clear();
        List<Reserva> reservas = servicioReservas.listarTodasReservas();
        listViewReservas.getItems().addAll(reservas);
    }

    private void mostrarAlerta(String mensaje, String titulo, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
