package co.edu.uniquindio.reservasuq.controlador;

import co.edu.uniquindio.reservasuq.modelo.Persona;
import co.edu.uniquindio.reservasuq.modelo.Sesion;
import co.edu.uniquindio.reservasuq.modelo.enums.TipoPersona;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class LoginControlador {

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtPassword;

    private final ControladorPrincipal controladorPrincipal;

    public LoginControlador() {
        this.controladorPrincipal = ControladorPrincipal.getInstancia();
    }

    public void login(ActionEvent actionEvent) {
        try {
            String email = txtCorreo.getText();
            String password = txtPassword.getText();

            // Intentar autenticarse
            Persona persona = controladorPrincipal.login(email, password);

            // Establecer la persona en la sesión
            Sesion sesion = Sesion.getInstancia();
            sesion.setPersona(persona);

            // Navegar según el tipo de usuario
            if (persona.getTipoPersona() != TipoPersona.ADMIN) {
                controladorPrincipal.navegarVentana("/panelCliente.fxml", "Panel Usuario");
            } else {
                controladorPrincipal.navegarVentana("/panelAdmin.fxml", "Panel Administrador");
            }

            // Cerrar la ventana de login
            controladorPrincipal.cerrarVentana(txtCorreo);

        } catch (Exception e) {
            controladorPrincipal.mostrarAlerta(e.getMessage(), "Error", Alert.AlertType.ERROR);
        }
    }
}
