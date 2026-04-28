package com.klikego;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.*;

@WebListener
public class DatabaseInit implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
       String url = "jdbc:postgresql://dpg-d7o9o1u7r5hc73bav0lg-a.ohio-postgres.render.com/klikego_db";
String user = "klikego_db_user";
String password = "U5DvT1GFOGGQEhqhSZJftsiHB7LD8FO6";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {

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
            }

            System.out.println("✅ Base de données initialisée !");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // rien à faire
    }
}