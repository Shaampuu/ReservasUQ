package co.edu.uniquindio.reservasuq.modelo;

import co.edu.uniquindio.reservasuq.modelo.enums.TipoPersona;
import co.edu.uniquindio.reservasuq.servicio.ServiciosReservasUQ;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReservasUQ implements ServiciosReservasUQ {

    private List<Persona> personas;
    private List<Instalacion> instalaciones;
    private List<Reserva> reservas;

    public ReservasUQ() {
        personas = new ArrayList<>();
        instalaciones = new ArrayList<>();
        reservas = new ArrayList<>();
    }

    @Override
    public Persona login(String correo, String contrasena) throws Exception {
        Optional<Persona> persona = personas.stream()
                .filter(p -> p.getEmail().equals(correo) && p.getPassword().equals(contrasena))
                .findFirst();

        if (persona.isPresent()) {
            return persona.get();
        } else {
            throw new Exception("Correo o contraseña incorrectos.");
        }
    }

    @Override
    public void registrarPersona(String cedula, String nombre, TipoPersona tipoPersona, String email, String password) throws Exception {
        if (personas.stream().anyMatch(p -> p.getEmail().equals(email))) {
            throw new Exception("El correo electrónico ya está registrado.");
        }

        if (personas.stream().anyMatch(p -> p.getCedula().equals(cedula))) {
            throw new Exception("La cédula ya está registrada.");
        }

        Persona nuevaPersona = new Persona(cedula, nombre, tipoPersona, email, password);
        personas.add(nuevaPersona);
    }

    @Override
    public void crearInstalacion(String nombre, int aforo, float costo, List<Horario> horarios) {
        if (aforo <= 0) {
            throw new IllegalArgumentException("El aforo debe ser mayor que cero.");
        }
        if (costo < 0) {
            throw new IllegalArgumentException("El costo no puede ser negativo.");
        }

        Instalacion nuevaInstalacion = new Instalacion(nombre, aforo, costo, horarios);
        instalaciones.add(nuevaInstalacion);
    }

    @Override
    public Reserva crearReserva(String idInstalacion, String cedulaPersona, LocalDate diaReserva, String horaReserva) throws Exception {
        if (diaReserva.isBefore(LocalDate.now().plusDays(2))) {
            throw new Exception("Las reservas deben hacerse con al menos 2 días de anticipación.");
        }

        Instalacion instalacion = instalaciones.stream()
                .filter(i -> i.getNombre().equals(idInstalacion))
                .findFirst()
                .orElseThrow(() -> new Exception("Instalación no encontrada."));

        Persona persona = personas.stream()
                .filter(p -> p.getCedula().equals(cedulaPersona))
                .findFirst()
                .orElseThrow(() -> new Exception("Persona no encontrada."));

        LocalTime horaInicio = LocalTime.parse(horaReserva);
        Horario horarioReserva = new Horario(horaInicio, horaInicio.plusHours(1));

        if (!instalacion.verificarDisponibilidad(diaReserva, horarioReserva)) {
            throw new Exception("El horario no está disponible.");
        }

        // Calcular el costo
        float costoReserva = 0.0f;
        if (persona.getTipoPersona() == TipoPersona.EXTERNO) {
            costoReserva = instalacion.getCosto(); // Costo para usuarios externos
        } else {
            costoReserva = instalacion.getCosto() * 0.8f; // Ejemplo: 20% de descuento para internos
        }

        // Crear la nueva reserva con el costo calculado
        Reserva nuevaReserva = new Reserva(idInstalacion, cedulaPersona, diaReserva, horarioReserva, costoReserva);
        reservas.add(nuevaReserva);

        // Informar del costo de la reserva
        System.out.println("El costo de la reserva es: " + costoReserva);

        return nuevaReserva;
    }

    @Override
    public List<Reserva> listarTodasReservas() {
        return new ArrayList<>(reservas);
    }

    @Override
    public List<Reserva> listarReservasPorPersona(String cedulaPersona) {
        List<Reserva> reservasPersona = new ArrayList<>();
        for (Reserva reserva : reservas) {
            if (reserva.getCedulaPersona().equals(cedulaPersona)) {
                reservasPersona.add(reserva);
            }
        }
        return reservasPersona;
    }
}
