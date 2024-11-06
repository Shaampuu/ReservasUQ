package co.edu.uniquindio.reservasuq.controlador;

import co.edu.uniquindio.reservasuq.modelo.*;
import co.edu.uniquindio.reservasuq.modelo.enums.TipoPersona;
import co.edu.uniquindio.reservasuq.servicio.ServiciosReservasUQ;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class ControladorPrincipal implements ServiciosReservasUQ {

    private static ControladorPrincipal instancia;
    private final ReservasUQ reservasUQ;

    // Almacena el usuario actual logueado en el sistema
    @Getter
    private static Persona usuarioActual;

    // Constructor privado para implementar Singleton
    private ControladorPrincipal() {
        reservasUQ = new ReservasUQ();
    }

    // Método para obtener la instancia única de ControladorPrincipal (Singleton)
    public static ControladorPrincipal getInstancia() {
        if (instancia == null) {
            instancia = new ControladorPrincipal();
        }
        return instancia;
    }

    // Implementación de los métodos de ServiciosReservasUQ
    @Override
    public Persona login(String correo, String contrasena) throws Exception {
        Persona persona = reservasUQ.login(correo, contrasena);
        usuarioActual = persona; // Almacena el usuario actual después del login
        return persona;
    }

    @Override
    public void registrarPersona(String cedula, String nombre, TipoPersona tipoPersona, String email, String password) throws Exception {
        reservasUQ.registrarPersona(cedula, nombre, tipoPersona, email, password);
    }

    @Override
    public void crearInstalacion(String nombre, int aforo, float costo, List<Horario> horarios) {
        reservasUQ.crearInstalacion(nombre, aforo, costo, horarios);
    }

    @Override
    public Reserva crearReserva(String idInstalacion, String cedulaPersona, LocalDate diaReserva, String horaReserva) throws Exception {
        return reservasUQ.crearReserva(idInstalacion, cedulaPersona, diaReserva, horaReserva);
    }

    @Override
    public List<Reserva> listarTodasReservas() {
        return reservasUQ.listarTodasReservas();
    }

    @Override
    public List<Reserva> listarReservasPorPersona(String cedulaPersona) {
        return reservasUQ.listarReservasPorPersona(cedulaPersona);
    }

    @Override
    public List<Persona> obtenerPersonas() {
        return reservasUQ.obtenerPersonas();
    }

    @Override
    public List<String> obtenerNombresInstalaciones() {
        return reservasUQ.obtenerNombresInstalaciones();
    }

    @Override
    public void cancelarReserva(String idReserva) throws Exception {
        // Implementación del método para cancelar una reserva
        reservasUQ.cancelarReserva(idReserva);
    }

    // Métodos adicionales para la interfaz gráfica y la lógica de la aplicación

    // Método para mostrar alertas reutilizable
    public void mostrarAlerta(String mensaje, String titulo, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Método para abrir una nueva ventana en la aplicación
    public void navegarVentana(String nombreArchivoFxml, String tituloVentana) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreArchivoFxml));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(tituloVentana);
            stage.show();
        } catch (Exception e) {
            mostrarAlerta("No se pudo abrir la ventana solicitada.", "Error", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    // Método para cerrar la ventana actual
    public void cerrarVentana(Node node) {
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
}
