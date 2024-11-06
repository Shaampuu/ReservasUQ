module co.edu.uniquindio.reservasuq {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.simplejavamail;
    requires org.simplejavamail.core;
    requires static lombok;


    opens co.edu.uniquindio.reservasuq to javafx.fxml;
    opens co.edu.uniquindio.reservasuq.controlador;
    opens co.edu.uniquindio.reservasuq.modelo;
    exports co.edu.uniquindio.reservasuq;
}