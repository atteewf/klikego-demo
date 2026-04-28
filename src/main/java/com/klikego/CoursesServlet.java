package com.klikego;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/courses")
public class CoursesServlet extends HttpServlet {

    private static final String URL = "jdbc:postgresql://dpg-d7o9o1u7r5hc73bav0lg-a.ohio-postgres.render.com/klikego_db";
    private static final String USER = "klikego_db_user";
    private static final String PASSWORD = "U5DvT1GFOGGQEhqhSZJftsiHB7LD8FO6";

    @Override
    public void init() throws ServletException {
        System.out.println("🚀 CoursesServlet init !");
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("✅ Driver PostgreSQL chargé !");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Driver non trouvé : " + e.getMessage());
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            System.out.println("✅ Connexion BDD OK !");

            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS courses (" +
                "id SERIAL PRIMARY KEY," +
                "nom VARCHAR(100) NOT NULL," +
                "date_course DATE NOT NULL," +
                "distance VARCHAR(20) NOT NULL," +
                "ville VARCHAR(100) NOT NULL)"
            );

            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS inscriptions (" +
                "id SERIAL PRIMARY KEY," +
                "nom VARCHAR(100) NOT NULL," +
                "prenom VARCHAR(100) NOT NULL," +
                "email VARCHAR(150) NOT NULL," +
                "course_id INT NOT NULL," +
                "date_inscription TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (course_id) REFERENCES courses(id))"
            );

            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM courses");
            rs.next();
            if (rs.getInt(1) == 0) {
                stmt.executeUpdate(
                    "INSERT INTO courses (nom, date_course, distance, ville) VALUES " +
                    "('Course du Pays de Rennes', '2026-06-15', '10 km', 'Rennes')," +
                    "('Semi-marathon de Bretagne', '2026-07-20', '21 km', 'Brest')," +
                    "('Trail des Landes', '2026-08-10', '15 km', 'Vannes')"
                );
                System.out.println("✅ Courses insérées !");
            }

        } catch (SQLException e) {
            System.err.println("❌ ERREUR SQL : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<String[]> courses = new ArrayList<>();
        List<String[]> inscriptions = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery("SELECT * FROM courses");
            while (rs.next()) {
                courses.add(new String[]{
                    rs.getString("id"),
                    rs.getString("nom"),
                    rs.getString("date_course"),
                    rs.getString("distance"),
                    rs.getString("ville")
                });
            }

            ResultSet rs2 = stmt.executeQuery(
                "SELECT i.id, i.nom, i.prenom, i.email, c.nom as course_nom, i.date_inscription " +
                "FROM inscriptions i JOIN courses c ON i.course_id = c.id " +
                "ORDER BY i.date_inscription DESC"
            );
            while (rs2.next()) {
                inscriptions.add(new String[]{
                    rs2.getString("id"),
                    rs2.getString("nom"),
                    rs2.getString("prenom"),
                    rs2.getString("email"),
                    rs2.getString("course_nom"),
                    rs2.getString("date_inscription")
                });
            }

        } catch (SQLException e) {
            System.err.println("❌ ERREUR doGet : " + e.getMessage());
            e.printStackTrace();
        }

        request.setAttribute("courses", courses);
        request.setAttribute("inscriptions", inscriptions);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}