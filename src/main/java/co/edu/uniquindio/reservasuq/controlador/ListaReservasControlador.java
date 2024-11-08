package co.edu.uniquindio.reservasuq.controlador;

import co.edu.uniquindio.reservasuq.modelo.Reserva;
import co.edu.uniquindio.reservasuq.servicio.ServiciosReservasUQ;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;

public class ListaReservasControlador {

    public Button bttCancelarReserva;
    @FXML
    private TableColumn<Reserva, String> idColumn;

    @FXML
    private TableColumn<Reserva, String> fechaColumn;

    @FXML
    private TableColumn<Reserva, String> instalacionColumn;

    @FXML
    private TableView<Reserva> tablaServicios;

    private ServiciosReservasUQ sistemaReservas;

    // Este método se ejecuta cuando el controlador es inicializado
    @FXML
    public void initialize() {
        // Configura las columnas de la tabla con los nombres de las propiedades de la clase Reserva
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idReserva"));
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fechaReserva"));
        instalacionColumn.setCellValueFactory(new PropertyValueFactory<>("instalacionNombre"));

        // Intenta actualizar la lista de reservas (si sistemaReservas ya está inicializado)
        if (sistemaReservas != null) {
            actualizarListaReservas();
        }
    }

    // Método para actualizar la lista de reservas en la tabla
    public void actualizarListaReservas() {
        if (sistemaReservas != null) {
            // Obtiene todas las reservas del sistema
            ObservableList<Reserva> listaReservas = FXCollections.observableArrayList(sistemaReservas.listarTodasReservas());
            tablaServicios.setItems(listaReservas);  // Establece los items de la tabla
            tablaServicios.refresh(); // Refresca la tabla para asegurarse de que los datos se actualicen visualmente
        } else {
            System.err.println("La instancia de ServiciosReservasUQ no ha sido inicializada.");
        }
    }

    // Establecer el servicio de reservas para este controlador
    public void setSistemaReservas(ServiciosReservasUQ sistemaReservas) {
        this.sistemaReservas = sistemaReservas;
        actualizarListaReservas(); // Llama a la actualización de la lista de reservas una vez que se asigna el sistema de reservas
    }

    // Método para cancelar la reserva seleccionada
    @FXML
    public void cancelarReserva(ActionEvent event) throws Exception {
        Reserva reservaSeleccionada = tablaServicios.getSelectionModel().getSelectedItem();
        if (reservaSeleccionada != null) {
            // Cancela la reserva seleccionada
            sistemaReservas.cancelarReserva(reservaSeleccionada.getIdReserva()); // Método cancelarReserva en sistemaReservas

            // Actualiza la lista de reservas
            actualizarListaReservas();

            // Muestra un mensaje de éxito
            mostrarAlerta("Reserva cancelada correctamente", Alert.AlertType.INFORMATION);
        } else {
            // Muestra un mensaje de advertencia si no se selecciona ninguna reserva
            mostrarAlerta("No se ha seleccionado ninguna reserva para cancelar", Alert.AlertType.WARNING);
        }
    }

    // Método para mostrar una alerta
    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }
}
