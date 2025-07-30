
# 🔥 Burn After Reading

Ein sicheres Nachrichtensystem, bei dem Nachrichten **nur einmal gelesen** werden können – danach werden sie automatisch gelöscht. Ideal für vertrauliche Infos wie Passwörter, Geheimnisse oder private Botschaften.

---

## ✨ Features

- 📝 Einmal-Nachrichten verschicken
- 🔒 AES-verschlüsselte Speicherung
- ⏳ Selbstlöschung nach Aufruf oder Ablaufzeit
- 🌐 Minimalistisches HTML/CSS/JS-Frontend
- 🛡️ REST-API mit Swagger/OpenAPI
- 🗃️ PostgreSQL-Datenbank
- 🔁 Automatisches Löschen durch Scheduled Tasks

---

## ⚙️ Technologien

- Java 17+
- Spring Boot
- PostgreSQL
- Maven
- HTML/CSS/JS (Vanilla)
- Swagger/OpenAPI (SpringDoc)
- AES-Verschlüsselung via JCA
- Optional: Docker & CI/CD (Zukunft)

---

## 🛠 Projektstruktur

```
├── src/  
│ ├── main/  
│ │ ├── java/de/ba/bund/burnafterreading/  
│ │ │ ├── BurnAfterReadingApplication.java  
│ │ │ ├── model/Nachricht.java  
│ │ │ ├── repo/NachrichtRepository.java  
│ │ │ ├── service/AESEncryptionService.java  
│ │ │ ├── web/NachrichtController.java  
│ │ │ ├── web/ViewController.java  
│ │ │ └── scheduled/MyScheduledTasks.java  
│ │ └── resources/  
│ │ ├── application.yml.example  
│ │ ├── application.properties.example  
│ │ ├── static/  
│ │ │ ├── index.html  
│ │ │ ├── view.html  
│ │ │ ├── index.js  
│ │ │ ├── view.js  
│ │ │ ├── style.css  
│ │ │ └── favicon.ico  
├── nachricht.sql  
├── pom.xml  
└── README.md
````

---

## 🚀 Schnellstart

### 1. Projekt klonen

```bash
git clone https://github.com/dein-name/burn-after-reading.git
cd burn-after-reading
````

### 2. Konfiguration erstellen

Kopiere und bearbeite die Konfigurationsdateien:

```bash
cp src/main/resources/application.yml.example src/main/resources/application.yml
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

### 3. AES-Key generieren

```bash
openssl rand -base64 32
```

🔐 Füge den Key in `application.yml` ein:

```yaml
encryption:
  aes-key: "DEIN-AES-KEY-HIER"
```

### 4. PostgreSQL starten (lokal)

Stelle sicher, dass PostgreSQL läuft und ein Datenbank-User + DB existiert:

```sql
CREATE DATABASE burnafterreading;
CREATE USER burnuser WITH PASSWORD 'burnsecret';
GRANT ALL PRIVILEGES ON DATABASE burnafterreading TO burnuser;
```

### 5. Anwendung starten

```bash
mvn spring-boot:run
```

Anwendung ist erreichbar unter:  
📍 `http://localhost:8080`

Swagger/OpenAPI UI:  
📍 `http://localhost:8080/swagger-ui.html`

---

## 🧪 API-Übersicht

**Base URL:** `/api/v1/notes`

|Methode|Pfad|Beschreibung|
|--:|---|---|
|`POST`|`/`|Neue Nachricht speichern (verschlüsselt)|
|`GET`|`/{id}`|Nachricht abrufen (Debug-Modus, kein Delete)|
|`DELETE`|`/{id}`|Nachricht abrufen und löschen ("Burn")|

---

## 💬 Frontend-Verhalten

- `index.html`: Nachricht schreiben → Link generieren

- `view.html`: Link einfügen → Nachricht lesen → Selbstzerstörung

- Nachricht wird beim ersten Abruf gelöscht

- Nachricht verschwindet automatisch nach 5 Minuten


---

## 🗂️ Konfigurationsdateien

### `application.yml`

```yaml
server:
  port: 8080

encryption:
  aes-key: "DEIN-GENERIERTER-KEY"
```

### Beispiel für `application.properties`

```properties
spring.application.name=BurnAfterReading
logging.level.web=debug
logging.level.sql=debug
logging.level.org.hibernate.orm.jbdc.bind=trace

spring.datasource.url=jdbc:postgresql://localhost:5432/burnafterreading
spring.datasource.username=yourdbusername
spring.datasource.password=yourdbpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```