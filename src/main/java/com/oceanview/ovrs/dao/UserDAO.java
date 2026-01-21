
package com.oceanview.ovrs.dao;

import com.oceanview.ovrs.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * UserDAO
 * -------
 * Data Access Object for the 'users' table.
 * Current scope: only what's required for login (findByUsername).
 */
public class UserDAO {


    private static final String SELECT_BY_USERNAME =
            "SELECT id, username, password_hash, role FROM users WHERE username = ? LIMIT 1";


    /**
     * Returns a single user row by username, or null if not found.
     */

    public UserRecord findByUsername(String username) {
        // Normalize input defensively (same as we did in the servlet)
        if (username != null) username = username.trim();

        final String sql = "SELECT id, username, password_hash, role FROM users WHERE username = ? LIMIT 1";

        try {
            Connection conn = DBConnection.getInstance().getConnection();

            // ---- Diagnostics: which schema? how many users? (TEMP for debugging) ----
            try (PreparedStatement psDb = conn.prepareStatement("SELECT DATABASE() AS db");
                 ResultSet rsDb = psDb.executeQuery()) {
                if (rsDb.next()) {
                    System.out.println("[DAO] DATABASE() = " + rsDb.getString("db"));
                }
            }

            try (PreparedStatement psCnt = conn.prepareStatement("SELECT COUNT(*) AS c FROM users");
                 ResultSet rsCnt = psCnt.executeQuery()) {
                if (rsCnt.next()) {
                    System.out.println("[DAO] users table count = " + rsCnt.getInt("c"));
                }
            }

            try (PreparedStatement psOne = conn.prepareStatement("SELECT COUNT(*) AS c FROM users WHERE username=?")) {
                psOne.setString(1, username);
                try (ResultSet rsOne = psOne.executeQuery()) {
                    if (rsOne.next()) {
                        System.out.println("[DAO] count for username '" + username + "' = " + rsOne.getInt("c"));
                    }
                }
            }
            // ------------------------------------------------------------------------

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, username);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String pref = rs.getString("password_hash");
                        String prefix = (pref != null && pref.length() >= 7) ? pref.substring(0, 7) : String.valueOf(pref);
                        System.out.println("[DAO] fetched row: id=" + rs.getInt("id")
                                + ", username=" + rs.getString("username")
                                + ", hashPrefix=" + prefix
                                + ", role=" + rs.getString("role"));

                        return new UserRecord(
                                rs.getInt("id"),
                                rs.getString("username"),
                                rs.getString("password_hash"),
                                rs.getString("role")
                        );
                    } else {
                        System.out.println("[DAO] no row returned for username=" + username);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Simple immutable DTO/record to carry user data to upper layers.
     */
    public static class UserRecord {
        public final int id;
        public final String username;
        public final String passwordHash;
        public final String role;

        public UserRecord(int id, String username, String passwordHash, String role) {
            this.id = id;
            this.username = username;
            this.passwordHash = passwordHash;
            this.role = role;
        }
    }
}
