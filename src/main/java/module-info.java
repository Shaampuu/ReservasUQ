module co.edu.uniquindio.reservasuq {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires org.simplejavamail;
    requires org.simplejavamail.core;


    opens co.edu.uniquindio.reservasuq to javafx.fxml;
    exports co.edu.uniquindio.rese  rvasuq;
    exports co.edu.uniquindio.reservasuq.App;
    opens co.edu.uniquindio.reservasuq.App to javafx.fxml;
}