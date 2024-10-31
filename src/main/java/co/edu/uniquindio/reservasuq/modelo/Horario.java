package co.edu.uniquindio.reservasuq.modelo;

import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class Horario {
    private LocalTime horaInicio;
    private LocalTime horaFin;


    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    // Método para verificar si un horario está disponible
    public boolean estaDisponible(Horario otroHorario) {
        return horaFin.isBefore(otroHorario.horaInicio) || horaInicio.isAfter(otroHorario.horaFin);
    }
}
