package co.edu.uniquindio.reservasuq.servicio;

import co.edu.uniquindio.reservasuq.modelo.Horario;
import co.edu.uniquindio.reservasuq.modelo.Persona;
import co.edu.uniquindio.reservasuq.modelo.Reserva;
import co.edu.uniquindio.reservasuq.modelo.enums.TipoPersona;

import java.time.LocalDate;
import java.util.List;

public interface ServiciosReservasUQ {

    // Métodos para la gestión de personas
    Persona login(String correo, String contrasena) throws Exception;

    void registrarPersona(String cedula, String nombre, TipoPersona tipoPersona, String email, String password) throws Exception;

    // Métodos para la gestión de instalaciones
    void crearInstalacion(String nombre, int aforo, float costo, List<Horario> horarios);

    // Métodos para la gestión de reservas
    Reserva crearReserva(String idInstalacion, String cedulaPersona, LocalDate diaReserva, String horaReserva) throws Exception;

    List<Reserva> listarTodasReservas();  // Obtiene todas las reservas

    List<Reserva> listarReservasPorPersona(String cedulaPersona);  // Obtiene las reservas de una persona específica

    void cancelarReserva(String idReserva) throws Exception;  // Cancela una reserva por su ID

    // Métodos adicionales
    List<Persona> obtenerPersonas();  // Devuelve la lista de personas

    List<String> obtenerNombresInstalaciones();  // Devuelve los nombres de todas las instalaciones
}
