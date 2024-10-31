package co.edu.uniquindio.reservasuq.modelo;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class Reserva {
    private String idInstalacion;
    private String cedulaPersona;
    private LocalDate diaReserva;
    private Horario horario;

}
