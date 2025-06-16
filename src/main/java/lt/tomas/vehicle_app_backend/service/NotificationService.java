package lt.tomas.vehicle_app_backend.service;

import lt.tomas.vehicle_app_backend.entity.Department;
import lt.tomas.vehicle_app_backend.entity.EmailAddress;
import lt.tomas.vehicle_app_backend.entity.Vehicle;
import lt.tomas.vehicle_app_backend.repository.EmailAddressRepository;
import lt.tomas.vehicle_app_backend.repository.VehicleRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class NotificationService {

    private final VehicleRepository vehicleRepository;
    private final EmailAddressRepository emailAddressRepository;
    private final JavaMailSender mailSender;

    public NotificationService(VehicleRepository vehicleRepository, EmailAddressRepository emailAddressRepository, JavaMailSender mailSender) {
        this.vehicleRepository = vehicleRepository;
        this.emailAddressRepository = emailAddressRepository;
        this.mailSender = mailSender;
    }

    public void sendNotifications() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        for (Vehicle v : vehicles) {
            boolean sendWarning = false;
            StringBuilder message = new StringBuilder();
            LocalDate today = LocalDate.now();

            if (v.getInsuranceExpiry() != null) {
                if (v.getInsuranceExpiry().isBefore(today)) {
                    message.append("Draudimas PASIBAIGĘS\n");
                    sendWarning = true;
                } else if (!v.getInsuranceExpiry().isAfter(today.plusDays(30))) {
                    message.append("Draudimas greitai baigsis\n");
                    sendWarning = true;
                }
            }

            if (v.getTechnicalInspectionExpiry() != null) {
                if (v.getTechnicalInspectionExpiry().isBefore(today)) {
                    message.append("Technikinė PASIBAIGUSI\n");
                    sendWarning = true;
                } else if (!v.getTechnicalInspectionExpiry().isAfter(today.plusDays(30))) {
                    message.append("Technikinė greitai baigsis\n");
                    sendWarning = true;
                }
            }

            if (sendWarning && v.getDepartment() != null) {
                List<EmailAddress> emails = emailAddressRepository.findByDepartmentId(v.getDepartment().getId());
                for (EmailAddress email : emails) {
                    SimpleMailMessage mail = new SimpleMailMessage();
                    mail.setTo(email.getEmail());
                    mail.setSubject("Įspėjimas dėl transporto priemonės");
                    mail.setText("Automobilis: " + v.getBrand() + " " + v.getModel() + " (" + v.getRegistrationNumber() + ")\n" + message);
                    try {
                        mailSender.send(mail);
                    } catch (Exception e) {
                        System.out.println("[TEST MODE] Siunčiamas laiškas į " + email.getEmail());
                        System.out.println(mail.getText());
                    }
                }
            }
        }
    }

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    public void sendEmailToDepartment(Department department, String subject, String body) {
        List<EmailAddress> emails = department.getEmails();

        for (EmailAddress email : emails) {
            // čia galima naudoti JavaMailSender, bet kol kas tik imituojam
            System.out.println("SIUNČIU laišką 👉 " + email.getEmail());
            System.out.println("TEMA: " + subject);
            System.out.println("TURINYS: \n" + body);
            System.out.println("------------------------------------");
        }
    }
}
