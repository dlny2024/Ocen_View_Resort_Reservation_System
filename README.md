
# Ocean View Resort – Online Room Reservation System  
**Version:** v1.0 (Milestone 1)  
**Module:** CIS6003 – Advanced Programming  
**Student:** Dilini Subodha Fernando 
**GIT:** https://github.com/dlny2024/Ocen_View_Resort_Reservation_System
**Student ID:** CL/BSCSD/33/126
**University ID:** 20306001
---

## 1. Introduction
The **Ocean View Resort Reservation System** is a Java‑based distributed application developed to automate room reservations for a hotel environment.  
The system modernizes the manual reservation workflow using a structured 3‑tier architecture, database‑driven backend, and web‑based user interfaces.

This project is built using:  
- Java Servlets (no frameworks)  
- HTML, CSS, JavaScript  
- MySQL via XAMPP  
- Apache Tomcat 9  
- Maven (WAR packaging)  
- Git & GitHub for version control  

This repository also demonstrates industry‑standard Git workflows as required for **Task D**.

---

## 2. Purpose of the System
The system aims to:
- Support hotel staff in managing reservations efficiently  
- Provide a simple, browser‑based user interface  
- Store reservation details in a MySQL database  
- Enable system health checking and DB connectivity verification  

Future versions (v1.1+) will include:  
✔ Full login system  
✔ Reservation CRUD  
✔ Reports  
✔ Data validation  
✔ Improved UI  

---

## 3. Technology Stack

| Layer | Technology |
|------|------------|
| Frontend | HTML, CSS, JavaScript |
| Backend | Java Servlets (MVC) |
| Database | MySQL (XAMPP) |
| Server | Apache Tomcat 9 |
| Build Tool | Maven (WAR) |
| Version Control | Git + GitHub |
| OS / IDE | Windows 11 / IntelliJ IDEA 2024.1.2 Ultimate |

---

## 4. Architecture Overview (3‑Tier Model)

### ▶ Presentation Layer  
Static UI pages (HTML/CSS/JS) used to interact with the system.

### ▶ Business Logic Layer  
Java Servlets handling:  
- Requests  
- Validation  
- Health checks  

### ▶ Data Layer  
MySQL database accessed via:  
- JDBC  
- Singleton DBConnection class  

---

## 5. Folder Structure (v1.0)
Ocen_View_Resort_Reservation_System/
├── src/
│   ├── main/java/com/oceanview/ovrs/
│   │   ├── controllers/
│   │   │   └── HealthCheckServlet.java
│   │   └── util/
│   │       └── DBConnection.java
│   └── main/webapp/
│       ├── index.html
│       ├── login.html
│       ├── help.html
│       ├── assets/css/styles.css
│       ├── assets/js/app.js
│       └── WEB-INF/web.xml
│
├── docs/sql/schema_v1_0.sql
├── pom.xml
└── README.md
---

## 6. Milestone 1 Features (v1.0)

### ✔ Functional  
- Basic UI pages  
- Health endpoint `/health`  
- Displays:  

App: OK
DB: OK

### ✔ Backend  
- Tomcat 9 deployment  
- JDBC connection working  
- Singleton DBConnection  
- Basic MVC structure  

### ✔ Database  
- Schema for users, guests, rooms, reservations  
- SQL provided in `/docs/sql/`

### ✔ Git Requirements  
- Public GitHub repository  
- Multiple meaningful commits  
- Branches:  
- feature/database  
- feature/backend  
- feature/ui  
- feature/testing  
- Release tag: **v1.0**

---

## 7. How to Run the Project

### Step 1 — Database Setup (XAMPP)
1. Start **MySQL** from XAMPP.  
2. Open **phpMyAdmin**.  
3. Import:  

docs/sql/schema_v1_0.sql

### Step 2 — Configure Tomcat in IntelliJ
1. Open project in IntelliJ.  
2. Maven loads dependencies automatically.  
3. Run → Edit Configurations → Tomcat Local  
4. Add artifact:  

ovrs:war exploded
5. Set context path: `/` or `/ovrs`.  
6. Use **JDK 21** (or switch Tomcat runtime to **JDK 17** if needed).

### Step 3 — Run
- Build → Build Artifacts → war exploded  
- Start Tomcat  
- Open in browser:

| Page | URL |
|------|-----|
| Home | http://localhost:8080/ |
| Health | http://localhost:8080/health |

Expected output:

App: OK
DB: OK

---

## 8. Design Patterns Used

### ⭐ Singleton  
For database connection handling.

### ⭐ MVC (partial in v1.0)  
- Controller: Servlets  
- View: HTML/CSS  
- Model: Database tables  

### ⭐ DAO (coming in v1.1)  

---

## 9. Version Control (Task D Requirements)

### ✔ Branching Strategy
- main  
- feature/database  
- feature/backend  
- feature/ui  
- feature/testing  

### ✔ Commit Strategy
Frequent atomic commits with meaningful messages (recommended 3–4 per week).

### ✔ Release Tags
- **v1.0** — Milestone 1 complete

### ✔ Repository Link  
https://github.com/dlny2024/Ocen_View_Resort_Reservation_System

---

## 10. v1.0 Deliverables Summary

| Deliverable | Status |
|------------|--------|
| UI (HTML/CSS/JS) | ✔ |
| Tomcat deployment | ✔ |
| JDBC connection | ✔ (DB: OK) |
| SQL schema | ✔ |
| HealthCheck Servlet | ✔ |
| Public repository | ✔ |
| Branches (4+) | ✔ |
| v1.0 Tag | ✔ |

---

## 11. Next Milestone (v1.1)

Planned improvements:
- Login & authentication  
- Reservation management (CRUD)  
- Room availability checks  
- Reporting module  
- DAO layer  
- More UI pages  
- More commits & branches  

---

## 12. Acknowledgements
This project is submitted as part of:  
**CIS6003 – Advanced Programming**  
School of Technologies  
Cardiff Metropolitan University.
