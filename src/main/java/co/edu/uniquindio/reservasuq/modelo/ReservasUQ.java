package co.edu.uniquindio.reservasuq.modelo;

import co.edu.uniquindio.reservasuq.modelo.enums.TipoPersona;
import co.edu.uniquindio.reservasuq.servicio.ServiciosReservasUQ;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ReservasUQ implements ServiciosReservasUQ {

    private List<Persona> personas;
    private List<Instalacion> instalaciones;
    private List<Reserva> reservas;

    public ReservasUQ() {
        personas = new ArrayList<>();
        instalaciones = new ArrayList<>();
        reservas = new ArrayList<>();

        // Añadir instalaciones predefinidas
        crearInstalacion("Piscina", 30, 0, new ArrayList<>());
        crearInstalacion("Gimnasio", 20, 0, new ArrayList<>());
        crearInstalacion("Cancha de fútbol", 50, 0, new ArrayList<>());
        crearInstalacion("Cancha de baloncesto", 30, 0, new ArrayList<>());
        crearInstalacion("Aulas de estudio grupal", 10, 0, new ArrayList<>());
        crearInstalacion("Salones de eventos", 100, 0, new ArrayList<>());
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

        float costoReserva = persona.getTipoPersona() == TipoPersona.EXTERNO
                ? instalacion.getCosto()
                : instalacion.getCosto() * 0.8f;

        // Generar un ID único para la reserva
        String idReserva = "R-" + System.currentTimeMillis();

        Reserva nuevaReserva = new Reserva(idReserva, idInstalacion, cedulaPersona, diaReserva, horarioReserva, costoReserva);
        reservas.add(nuevaReserva);

        System.out.println("El costo de la reserva es: " + costoReserva);

        return nuevaReserva;
    }


    @Override
    public List<Reserva> listarTodasReservas() {
        return new ArrayList<>(reservas);
    }

    @Override
    public List<Reserva> listarReservasPorPersona(String cedulaPersona) {
        return reservas.stream()
                .filter(reserva -> reserva.getCedulaPersona().equals(cedulaPersona))
                .collect(Collectors.toList());
    }

    @Override
    public void cancelarReserva(String idReserva) throws Exception {
        Reserva reserva = reservas.stream()
                .filter(r -> r.getIdInstalacion().equals(idReserva)) // Asegúrate de usar el identificador correcto
                .findFirst()
                .orElseThrow(() -> new Exception("Reserva no encontrada."));

        reservas.remove(reserva);
        System.out.println("La reserva con ID " + idReserva + " ha sido cancelada.");
    }

    // Método adicional para obtener la lista de personas registradas
    public List<Persona> obtenerPersonas() {
        return new ArrayList<>(personas); // Devuelve una copia de la lista de personas
    }

    // Método adicional para obtener la lista de nombres de las instalaciones
    public List<String> obtenerNombresInstalaciones() {
        return instalaciones.stream()
                .map(Instalacion::getNombre)
                .collect(Collectors.toList());
    }

    // Método para obtener todas las instalaciones
    public List<Instalacion> getInstalaciones() {
        return new ArrayList<>(instalaciones);
    }
}
