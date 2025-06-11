package lt.tomas.vehicle_app_backend.config;

import jakarta.mail.internet.MimeMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import java.io.InputStream;



@Configuration
public class MailTestConfig {


    @Bean
    public JavaMailSender javaMailSender() {
        return new JavaMailSender() {


            @Override
            public void send(SimpleMailMessage simpleMessage) throws MailException {
                System.out.println("📨 FAKE EMAIL SENT TO: " + String.join(", ", simpleMessage.getTo()));
                System.out.println("📨 SUBJECT: " + simpleMessage.getSubject());
                System.out.println("📨 TEXT: " + simpleMessage.getText());
            }


            @Override
            public void send(SimpleMailMessage... simpleMessages) throws MailException {
                for (SimpleMailMessage msg : simpleMessages) {
                    send(msg);
                }
            }


            @Override public MimeMessage createMimeMessage() { return null; }

            @Override public MimeMessage createMimeMessage(InputStream contentStream) { return null; }

            @Override public void send(MimeMessage mimeMessage) { }

            @Override public void send(MimeMessage... mimeMessages) { }

            @Override public void send(MimeMessagePreparator mimeMessagePreparator) { }

            @Override public void send(MimeMessagePreparator... mimeMessagePreparators) { }
        };
    }
}