package co.edu.uniquindio.reservasuq.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class Instalacion {
    @Setter
    private String nombre;
    @Setter
    private int aforoMaximo;
    @Setter
    private float costo;
    @Setter
    private List<Horario> horariosDisponibles;
    private List<Reserva> reservas;

    // Constructor
    public Instalacion(String nombre, int aforoMaximo, float costo, List<Horario> horariosDisponibles) {
        this.nombre = nombre;
        this.aforoMaximo = aforoMaximo;
        this.costo = costo;
        this.horariosDisponibles = horariosDisponibles != null ? horariosDisponibles : new ArrayList<>();
        this.reservas = new ArrayList<>();
    }

    // Método para verificar si la instalación está disponible en un horario solicitado
    public boolean estaDisponible(Horario horarioSolicitado) {
        return horariosDisponibles.stream().anyMatch(h -> h.estaDisponible(horarioSolicitado));
    }

    // Método para realizar una reserva si hay disponibilidad y no se excede la capacidad
    public boolean realizarReserva(Reserva reserva) {
        if (estaDisponible(reserva.getHorarioReserva()) && capacidadNoExcedida(reserva)) {
            reservas.add(reserva);
            return true;
        }
        return false;
    }

    // Método para verificar si la capacidad máxima ha sido alcanzada para una reserva específica
    private boolean capacidadNoExcedida(Reserva reserva) {
        long reservasSimultaneas = reservas.stream()
                .filter(r -> r.getDiaReserva().equals(reserva.getDiaReserva()) &&
                        r.getHorarioReserva().equals(reserva.getHorarioReserva()))
                .count();

        return reservasSimultaneas < aforoMaximo;
    }

    // Método para añadir un horario disponible
    public void añadirHorario(Horario horario) {
        if (!horariosDisponibles.contains(horario)) {
            horariosDisponibles.add(horario);
        }
    }

    // Método para cancelar una reserva
    public boolean cancelarReserva(Reserva reserva) {
        return reservas.remove(reserva);
    }

    // Método para listar todas las reservas de un día específico
    public List<Reserva> listarReservasPorDia(LocalDate dia) {
        List<Reserva> reservasPorDia = new ArrayList<>();
        for (Reserva reserva : reservas) {
            if (reserva.getDiaReserva().equals(dia)) {
                reservasPorDia.add(reserva);
            }
        }
        return reservasPorDia;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public int getAforoMaximo() {
        return aforoMaximo;
    }

    public float getCosto() {
        return costo;
    }

    public List<Horario> getHorariosDisponibles() {
        return horariosDisponibles;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    // Método para obtener la disponibilidad de la instalación en un día específico
    public boolean verificarDisponibilidad(LocalDate dia, Horario horario) {
        // Comprueba si ya existen reservas en el horario y día especificados
        return reservas.stream()
                .noneMatch(r -> r.getDiaReserva().equals(dia) && r.getHorarioReserva().equals(horario));
    }
}
