# 🚗 Vehicle Park Backend

Tai yra Spring Boot backend dalis sistemos, kuri leidžia valdyti įmonės automobilių parką. Backend dalis bendrauja su frontend'u per REST API ir saugo duomenis MySQL duomenų bazėje.

---

## 🔧 Naudotos technologijos

- ✅ Java 21
- ✅ Spring Boot 3
- ✅ Spring Security + JWT autentifikacija
- ✅ JPA / Hibernate
- ✅ MySQL
- ✅ Email siuntimo testavimas (Fake MailSender)

---

## 🛠️ Kaip paleisti

### 1. ✅ Reikalavimai

- Java 21
- Maven
- MySQL (turi būti paleista)

### 2. ✅ Duomenų bazės nustatymai

`application.properties` faile turi būti nurodyti tavo duomenų bazės duomenys, pvz.:
spring.datasource.url=jdbc:mysql://localhost:3306/vehicle_park_db
spring.datasource.username=root
spring.datasource.password=slaptazodis

### 3. ✅ Paleidimas

mvn spring-boot:run

Backend startuoja adresu:
📍 http://localhost:8080

🔐 Prisijungimas

Backend’as turi autentifikaciją su JWT tokenais.

Numatyti prisijungimo duomenys:
•	Vartotojas: admin
•	Slaptažodis: 0000

Šie duomenys sukuriami automatiškai pirmą kartą paleidus programą (žr. DataInitializer klasę).

📄 Endpointai
•	GET /api/departments – visi padaliniai (leidžiama visiems)
•	POST /api/departments – sukurti padalinį (reikia prisijungimo)
•	GET /api/emails/department/{id} – padalinio el. paštai
•	POST /api/emails – pridėti el. paštą (reikia prisijungimo)
•	DELETE /api/emails/{id} – trinti el. paštą
•	GET /api/vehicles/department/{id} – padalinio automobiliai
•	POST /api/vehicles – pridėti naują automobilį
•	PUT /api/vehicles/{id} – atnaujinti automobilio info
•	GET /api/vehicles/search?query=ABC123 – paieška pagal numerį
•	POST /api/notifications/send – testinis laiškų siuntimas



Šiuo metu el. laiškai nesiunčiami realiai. Vietoje to, jie atvaizduojami konsolėje, kad būtų lengviau testuoti.

Kodas tam yra MailTestConfig.java. Jei norėsi siųsti realius laiškus – gali tai pakeisti vėliau.


Frontend šiam projektui:
🔗 https://github.com/TomasKaGit/vehicle-park-frontend-jun4
