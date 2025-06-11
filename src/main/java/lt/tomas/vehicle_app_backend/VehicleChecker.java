package lt.tomas.vehicle_app_backend;

import lt.tomas.vehicle_app_backend.entity.Department;
import lt.tomas.vehicle_app_backend.entity.Vehicle;
import lt.tomas.vehicle_app_backend.repository.VehicleRepository;
import lt.tomas.vehicle_app_backend.service.NotificationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class VehicleChecker {

    private final VehicleRepository vehicleRepository;
    private final NotificationService notificationService;

    public VehicleChecker (VehicleRepository vehicleRepository, NotificationService notificationService){
        this.vehicleRepository = vehicleRepository;
        this.notificationService = notificationService;
    }

    @Scheduled(cron = "0 0 8 * * *") // kiekvieną dieną 8:00
    public void checkExpirations() {
        List<Vehicle> allVehicles = vehicleRepository.findAll();
        LocalDate today = LocalDate.now();

        for (Vehicle v : allVehicles) {
            Department dept = v.getDepartment();
            if (dept == null || dept.getEmail() == null) continue;

            long daysToInsurance = ChronoUnit.DAYS.between(today, v.getInsuranceExpiry());
            long daysToTech = ChronoUnit.DAYS.between(today, v.getTechnicalInspectionExpiry());

            StringBuilder warning = new StringBuilder();

            if (daysToInsurance <= 30 && daysToInsurance >= 0) {
                warning.append(" Draudimo galiojimas baigsis už ").append(daysToInsurance).append(" d.\n");
            } else if (daysToInsurance < 0) {
                warning.append(" Draudimo galiojimas jau PASIBAIGĖ!\n");
            }

            if (daysToTech <= 30 && daysToTech >= 0) {
                warning.append(" Techninės apžiūros galiojimas baigsis už ").append(daysToTech).append(" d.\n");
            } else if (daysToTech < 0) {
                warning.append(" Techninės apžiūros galiojimas jau PASIBAIGĘS!\n");
            }

            if (!warning.isEmpty()) {
                notificationService.sendEmail(
                        dept.getEmail(),
                        " Transporto priemonės įspėjimai",
                        "Automobilis: " + v.getBrand() + " " + v.getModel() +
                                " (" + v.getRegistrationNumber() + ")\n\n" +
                                warning.toString()
                );
            }
        }
    }
}
