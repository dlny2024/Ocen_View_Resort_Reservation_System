
package com.oceanview.ovrs.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DBConnection
 * -----------
 * Singleton JDBC connection manager for the Ocean View Resort system.
 *
 * Environment:
 *  - Java 21
 *  - Tomcat 9 (Servlet 4.0 / javax)
 *  - MySQL 8.x on XAMPP (localhost:3306)
 *  - Maven WAR packaging
 *
 * Notes:
 *  - We explicitly load the MySQL driver (Class.forName) because some
 *    servlet container classloader setups do not trigger JDBC auto-loading reliably.
 *  - The JDBC URL includes allowPublicKeyRetrieval/useSSL/serverTimezone parameters
 *    to avoid common local dev issues with MySQL 8.
 *  - Keep the MySQL driver (mysql-connector-j-*.jar) on the runtime classpath.
 *    For exploded deployment, ensure it appears under WEB-INF/lib.
 */
public final class DBConnection {

    // ----- Adjust these to match your local MySQL credentials -----

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/ocean_view_resort"
                    + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8";

    private static final String DB_USER = "root";
    private static final String DB_PASS = ""; // XAMPP default is empty. Set if you created a password.
    // ---------------------------------------------------------------

    // Singleton instance (volatile + double-checked locking)
    private static volatile DBConnection instance;

    // The single Connection shared by the app.
    // If you later need pooling, swap to a DataSource or a pool library.
    private final Connection connection;

    private DBConnection() {
        try {
            // Defensive: ensure driver is registered with DriverManager
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open the connection once
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            // Optional: quick ping to validate right away
            try (Statement st = connection.createStatement()) {
                st.execute("SELECT 1");
            }
        } catch (Exception ex) {
            throw new RuntimeException("Cannot connect to MySQL: " + ex.getMessage(), ex);
        }
    }

    /**
     * Thread-safe lazy singleton access.
     */
    public static DBConnection getInstance() {
        DBConnection localRef = instance;
        if (localRef == null) {
            synchronized (DBConnection.class) {
                localRef = instance;
                if (localRef == null) {
                    instance = localRef = new DBConnection();
                }
            }
        }
        return localRef;
    }

    /**
     * Returns the live JDBC Connection.
     * Do NOT close this connection per request; the singleton owns it.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Convenience method for health checks (e.g., /health servlet).
     * Returns true if the connection is open and responds to a simple query.
     */
    public boolean ping() {
        try {
            if (connection == null || connection.isClosed()) return false;
            try (Statement st = connection.createStatement()) {
                return st.execute("SELECT 1");
            }
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Close the singleton connection gracefully (e.g., on app shutdown).
     * Typically invoked from a ServletContextListener if you add one later.
     */
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException ignored) {
        }
    }
}

