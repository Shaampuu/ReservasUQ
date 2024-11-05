module co.edu.uniquindio.reservasuq {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.simplejavamail;
    requires org.simplejavamail.core;
    requires static lombok;


    opens co.edu.uniquindio.reservasuq to javafx.fxml;
    exports co.edu.uniquindio.reservasuq.App;
    opens co.edu.uniquindio.reservasuq.App to javafx.fxml;
}