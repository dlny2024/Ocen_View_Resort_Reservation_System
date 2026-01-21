
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
            "SELECT id, username, password_hash, role " +
                    "FROM users WHERE username = ? LIMIT 1";

    /**
     * Returns a single user row by username, or null if not found.
     */
    public UserRecord findByUsername(String username) {
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            try (PreparedStatement ps = conn.prepareStatement(SELECT_BY_USERNAME)) {
                ps.setString(1, username);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new UserRecord(
                                rs.getInt("id"),
                                rs.getString("username"),
                                rs.getString("password_hash"),
                                rs.getString("role")
                        );
                    }
                }
            }
        } catch (Exception e) {
            // Log properly in real app; for now print to console for visibility
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
