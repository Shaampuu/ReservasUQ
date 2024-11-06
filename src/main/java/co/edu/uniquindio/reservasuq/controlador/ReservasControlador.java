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

    // Elementos de la interfaz (enlazados a través de FXML)
    @FXML
    private ComboBox<String> comboInstalacion;
    @FXML
    private DatePicker dateReserva;
    @FXML
    private TextField txtHora;

    // ListView para mostrar las reservas
    @FXML
    private ListView<Reserva> listViewReservas;

    // Servicio de reservas (instancia proporcionada por ControladorPrincipal)
    private final ServiciosReservasUQ servicioReservas;

    // Tipo de persona que realiza la reserva, con setter para inyección
    @Setter
    private TipoPersona tipoPersona;

    // Constructor para inicializar el servicio de reservas
    public ReservasControlador() {
        this.servicioReservas = ControladorPrincipal.getInstancia();
    }

    // Inicialización del controlador
    @FXML
    public void initialize() {
        listarReservas(); // Llenar la ListView al iniciar
    }

    // Acción al presionar el botón "Reservar"
    @FXML
    public void crearReserva(ActionEvent actionEvent) {
        try {
            // Obtener los valores ingresados
            String idInstalacion = comboInstalacion.getValue();
            LocalDate diaReserva = dateReserva.getValue();
            String horaReserva = txtHora.getText();

            // Crear la nueva reserva a través del servicio
            Reserva nuevaReserva = servicioReservas.crearReserva(idInstalacion, String.valueOf(tipoPersona), diaReserva, horaReserva);

            // Mostrar mensaje de éxito
            mostrarAlerta("Reserva creada con éxito. Tipo de persona: " + tipoPersona, "Éxito", Alert.AlertType.INFORMATION);

            // Actualizar la lista de reservas
            listarReservas();
        } catch (Exception e) {
            // Manejo de errores y muestra de alerta
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
