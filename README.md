# Ocen View Resort Reservation System (v1.0)
Java Servlets (no framework) + HTML/CSS/JS on Tomcat 9, MySQL (XAMPP), Maven (WAR), Java 21.

## What's in v1.0 (Milestone 1)
- Maven webapp scaffold (WAR) targeting Java 21
- Basic UI pages: Home (`index.html`), Login, Help
- `DBConnection` Singleton (JDBC) for MySQL
- `HealthCheckServlet` mapped at `/health` that prints `App: OK` and DB connectivity status
- Web descriptor `web.xml` (Servlet 4.0 / javax)
- SQL schema under `docs/sql/schema_v1_0.sql`

## How to run (Windows + IntelliJ Ultimate 2024.1.2)
1. Start XAMPP → **MySQL**. Open phpMyAdmin and execute `docs/sql/schema_v1_0.sql`.
2. In IntelliJ, open this project (it's Maven). Let dependencies download.
3. Configure Tomcat 9: *Run ▶ Edit Configurations ▶ + ▶ Tomcat Local*. Choose your Tomcat 9 folder.
   - Deployment: Add **`ovrs:war exploded`**, Context path: `/ovrs`.
   - JRE: Try **JDK 21**. If Tomcat fails to start on your install, switch Tomcat's JRE to **JDK 17** only for running. Project still compiles with 21.
4. Build the artifact and Run Tomcat.
5. Open http://localhost:8080/ovrs/ and http://localhost:8080/ovrs/health

## DB credentials
- URL: `jdbc:mysql://localhost:3306/ocean_view_resort?useSSL=false&serverTimezone=UTC`
- user: `root`
- pass: *(empty)* (XAMPP default). Change in `DBConnection` if needed.

## Next (v1.1)
- Models (User, Guest, Room, Reservation), DAO interfaces + CRUD
- Login servlet (validate against `user`)
- Reservation endpoints skeleton
