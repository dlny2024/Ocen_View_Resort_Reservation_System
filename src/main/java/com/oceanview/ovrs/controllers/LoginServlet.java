
package com.oceanview.ovrs.controllers;

import com.oceanview.ovrs.dao.UserDAO;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * POST /api/login
 * Accepts either:
 *  - form: x-www-form-urlencoded  (username=..&password=..)
 *  - JSON: {"username":"..","password":".."}
 * Returns 200 with user info, or 401 on invalid credentials.
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/api/login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Ensure consistent encoding for form parameters
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");

        // 1) Read credentials (support both JSON and form)
        String username = null;
        String password = null;

        String contentType = req.getContentType();
        if (contentType != null && contentType.toLowerCase().contains("application/json")) {
            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = req.getReader()) {
                String line;
                while ((line = reader.readLine()) != null) sb.append(line);
            }
            String body = sb.toString();
            username = extractJsonField(body, "username");
            password = extractJsonField(body, "password");
        } else {
            username = req.getParameter("username");
            password = req.getParameter("password");
        }

        // Normalize inputs (trim to avoid trailing/leading spaces)
        if (username != null) username = username.trim();
        if (password != null) password = password.trim();

        // === DEBUG #1: after reading inputs ===
        System.out.println("[LOGIN] contentType=" + req.getContentType());
        System.out.println("[LOGIN] username=" + username);
        System.out.println("[LOGIN] username len=" + (username == null ? -1 : username.length()));
        System.out.println("[LOGIN] password len=" + (password == null ? -1 : password.length()));

        if (isBlank(username) || isBlank(password)) {
            sendError(resp, 400, "Missing username or password");
            return;
        }

        // 2) Lookup user
        UserDAO dao = new UserDAO();
        UserDAO.UserRecord user = dao.findByUsername(username);

        // === DEBUG #2: after DAO lookup ===
        System.out.println("[LOGIN] user exists? " + (user != null));
        if (user != null) {
            try {
                String prefix = (user.passwordHash != null && user.passwordHash.length() >= 7)
                        ? user.passwordHash.substring(0, 7)
                        : String.valueOf(user.passwordHash);
                System.out.println("[LOGIN] stored hash prefix=" + prefix); // expect $2a$10 or $2b$10
            } catch (Exception ignored) {}
        }

        if (user == null) {
            // Avoid username probing
            sendError(resp, 401, "Invalid username or password");
            return;
        }

        // 3) Verify password hash using BCrypt
        boolean ok;
        try {
            ok = BCrypt.checkpw(password, user.passwordHash);
        } catch (Exception e) {
            ok = false;
        }

        // === DEBUG #3: after bcrypt check ===
        System.out.println("[LOGIN] bcrypt matches? " + ok);

        if (!ok) {
            sendError(resp, 401, "Invalid username or password");
            return;
        }

        // 4) Success (JWT/session can be added later)
        // Optionally create a session you can use for server-side auth on subsequent requests.
        // Comment out if you plan to switch to JWT soon.
        HttpSession session = req.getSession(true);
        session.setAttribute("userId", user.id);
        session.setAttribute("role", user.role);
        // You can also set a short session timeout for dev if you wish:
        // session.setMaxInactiveInterval(30 * 60); // 30 minutes

        try (PrintWriter out = resp.getWriter()) {
            resp.setStatus(HttpServletResponse.SC_OK);
            out.print("{");
            out.print("\"status\":\"success\",");
            out.print("\"userId\":" + user.id + ",");
            out.print("\"username\":\"" + escapeJson(user.username) + "\",");
            out.print("\"role\":\"" + escapeJson(user.role) + "\"");
            out.print("}");
        }
    }

    /**
     * (Optional) If your frontend is ever served from a different origin/port,
     * this allows preflight. Not needed when UI and API share the same origin.
     */
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Minimal preflight handling for dev convenience (adjust for prod as needed)
        resp.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        // If you must allow cross-origin during dev, uncomment the next line and
        // replace * with a specific origin to be safer.
        // resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    /* ---------- Helpers ---------- */

    // Minimal JSON extractor (no external libs). Assumes {"field":"value"} and double quotes.
    private String extractJsonField(String json, String field) {
        if (json == null) return null;
        String needle = "\"" + field + "\"";
        int i = json.indexOf(needle);
        if (i < 0) return null;
        int colon = json.indexOf(':', i + needle.length());
        if (colon < 0) return null;

        // Skip whitespace
        int j = colon + 1;
        while (j < json.length() && Character.isWhitespace(json.charAt(j))) j++;

        if (j >= json.length() || json.charAt(j) != '"') return null;
        int firstQuote = j;
        int secondQuote = json.indexOf('"', firstQuote + 1);
        if (secondQuote < 0) return null;

        return json.substring(firstQuote + 1, secondQuote);
    }

    private String escapeJson(String s) {
        if (s == null) return "";
        // naive escape for quotes and backslashes (sufficient for this milestone)
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private void sendError(HttpServletResponse resp, int code, String message) throws IOException {
        resp.setStatus(code);
        try (PrintWriter out = resp.getWriter()) {
            out.print("{\"status\":\"error\",\"message\":\"" + escapeJson(message) + "\"}");
        }
    }
}
