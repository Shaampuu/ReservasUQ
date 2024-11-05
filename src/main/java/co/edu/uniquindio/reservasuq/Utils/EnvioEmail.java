package co.edu.uniquindio.reservasuq.Utils;

import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;


public class EnvioEmail {


    public static void enviarNotificacion(String destinatario, String asunto, String mensaje) {


        Email email = EmailBuilder.startingBlank()
                .from("juanc.corralesc@uqvirtual.edu.co")
                .to(destinatario)
                .withSubject(asunto)
                .withPlainText(mensaje)
                .buildEmail();


        try (Mailer mailer = MailerBuilder
                .withSMTPServer("smtp.gmail.com", 587, "juanc.corralesc@uqvirtual.edu.co", "hngm knvf pvud juoi\n")
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .withDebugLogging(true)
                .buildMailer()) {


            mailer.sendMail(email);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
