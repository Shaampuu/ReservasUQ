package co.edu.uniquindio.reservasuq.controlador;

import co.edu.uniquindio.reservasuq.modelo.Reserva;
import co.edu.uniquindio.reservasuq.servicio.ServiciosReservasUQ;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ClienteControlador {

    @FXML
    private TableView<Reserva> tablaReservas; // Tabla para mostrar reservas
    @FXML
    private TableColumn<Reserva, String> columnaInstalacion;
    @FXML
    private TableColumn<Reserva, String> columnaFecha;
    @FXML
    private TableColumn<Reserva, String> columnaHora;

    private final ServiciosReservasUQ servicioReservas;
    private final ObservableList<Reserva> reservasObservable;

    public ClienteControlador() {
        this.servicioReservas = ControladorPrincipal.getInstancia(); // Obtiene la instancia del servicio
        this.reservasObservable = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        configurarTabla(); // Configura las columnas de la tabla
        cargarReservas(); // Carga las reservas en la tabla
    }

    private void configurarTabla() {
        columnaInstalacion.setCellValueFactory(new PropertyValueFactory<>("idInstalacion"));
        columnaFecha.setCellValueFactory(new PropertyValueFactory<>("diaReserva"));
        columnaHora.setCellValueFactory(new PropertyValueFactory<>("horaInicio")); // Usa el campo de inicio de la hora

        tablaReservas.setItems(reservasObservable);
    }

    private void cargarReservas() {
        // Obtiene las reservas del cliente actual (se podría pasar la cédula o ID del cliente)
        List<Reserva> reservasCliente = servicioReservas.listarReservasPorPersona(ControladorPrincipal.getUsuarioActual().getCedula());
        reservasObservable.setAll(reservasCliente);
    }
}
