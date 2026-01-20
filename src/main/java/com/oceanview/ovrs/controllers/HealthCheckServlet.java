
package com.oceanview.ovrs.controllers;

import com.oceanview.ovrs.util.DBConnection;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class HealthCheckServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/plain; charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("App: OK");
            Connection c = DBConnection.getInstance().getConnection();

            // Stronger DB check: SELECT 1
            try (PreparedStatement ps = c.prepareStatement("SELECT 1");
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    out.println("DB: OK");
                } else {
                    out.println("DB: NOT OK (no result)");
                }
            }
        } catch (Throwable t) {
            // TEMPORARY: show exact reason in response for quick debugging
            // (Remove this in production)
            resp.getWriter().println("DB: NOT OK (" + t.getClass().getSimpleName() + ": " + t.getMessage() + ")");
            // Also log to server console
            t.printStackTrace();
        }
    }
}
