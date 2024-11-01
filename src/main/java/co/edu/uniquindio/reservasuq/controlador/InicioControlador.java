package co.edu.uniquindio.reservasuq.controlador;

import javafx.event.ActionEvent;

public class InicioControlador {

    private final ControladorPrincipal controladorPrincipal;

    public InicioControlador() {
        this.controladorPrincipal = ControladorPrincipal.getInstancia();
    }

    public void irIniciarSesion(ActionEvent actionEvent) {
        controladorPrincipal.navegarVentana("/login.fxml","Iniciar Sesión");
    }

    public void irRegistroCliente(ActionEvent actionEvent) {
        controladorPrincipal.navegarVentana("/registro.fxml", "Registro Persona");
    }

    // Método para abrir la ventana de crear reserva (migrado desde ClienteControlador)
    public void abrirCrearReserva(ActionEvent event) {
        controladorPrincipal.navegarVentana("crearReserva.fxml", "Crear Reserva");
    }
}
