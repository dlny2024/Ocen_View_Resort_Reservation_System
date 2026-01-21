
# Ocean View Resort – Online Room Reservation System   
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

Ocen_View_Resort_Reservation_System/
├─ src/
│  ├─ main/java/com/oceanview/ovrs/
│  │  ├─ controllers/
│  │  │  ├─ HealthCheckServlet.java
│  │  │  └─ LoginServlet.java           # (v1.1)
│  │  ├─ dao/
│  │  │  └─ UserDAO.java                # (v1.1)
│  │  └─ util/
│  │     └─ DBConnection.java
│  ├─ main/webapp/
│  │  ├─ index.html
│  │  ├─ login.html                     # wired to /api/login (v1.1)
│  │  ├─ assets/css/styles.css
│  │  ├─ assets/js/app.js
│  │  └─ WEB-INF/web.xml
│  └─ main/resources/                   # (if used)
├─ docs/
│  └─ sql/ (schema files)
├─ pom.xml
└─ README.md 
---

## 6. Milestones
✅ Milestone 1 (v1.0)

Static UI pages (HTML/CSS/JS)
Health endpoint (/health) shows App/DB status
JDBC connection via DBConnection singleton
Schema (users, guests, rooms, reservations)
Git: public repo, multiple branches, tag v1.0

✅ Milestone 2 – Step 6 (v1.1): Login API
Features

POST /api/login with BCrypt verification
UserDAO.findByUsername
JSON & form support; UTF‑8 request/response
JSON responses; HttpSession created on success
/login.html wired to use the API
DB URL params corrected in DBConnection (& used, not &amp;)

Endpoints
POST /ovrs/api/login
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
5. Set context path: `/ovrs`.  
6. Use **JDK 21** .

### Step 3 — Run
- Build → Build Artifacts → war exploded  
- Start Tomcat  
- Open in browser:

| Page | URL |
|------|-----|
| Home | http://localhost:8080/ |
| Health | http://localhost:8080/health |
| Login | http://localhost:8080/ovrs/login.html |
Expected output:
| Health | {"status":"UP","db":true} |
| Login | {"status":"success","userId":1,"username":"admin","role":"ADMIN"} |

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
main
feature/database, feature/backend, feature/ui, feature/testing
feature/milestone-2-login-api (merged)

### ✔ Commit Strategy
Frequent atomic commits with meaningful messages (recommended 3–4 per week).

### ✔ Release Tags
v1.0 (Milestone 1)
v0.2.0-login (Milestone 2 – Step 6) — Milestone 1 complete

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


## 12. Acknowledgements
This project is submitted as part of:  
**CIS6003 – Advanced Programming**  
School of Technologies  
Cardiff Metropolitan University.
