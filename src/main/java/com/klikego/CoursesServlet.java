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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

       String url = "postgresql://klikego_db_user:U5DvT1GFOGGQEhqhSZJftsiHB7LD8FO6@dpg-d7o9o1u7r5hc73bav0lg-a/klikego_db";
String user = "klikego_db_user";
String password = "U5DvT1GFOGGQEhqhSZJftsiHB7LD8FO6";

        List<String[]> courses = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM courses")) {

            while (rs.next()) {
                courses.add(new String[]{
                    rs.getString("id"),
                    rs.getString("nom"),
                    rs.getString("date_course"),
                    rs.getString("distance"),
                    rs.getString("ville")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("courses", courses);
        // Après request.setAttribute("courses", courses);

List<String[]> inscriptions = new ArrayList<>();

try (Connection conn2 = DriverManager.getConnection(url, user, password);
     Statement stmt2 = conn2.createStatement();
     ResultSet rs2 = stmt2.executeQuery(
         "SELECT i.id, i.nom, i.prenom, i.email, c.nom as course_nom, i.date_inscription " +
         "FROM inscriptions i JOIN courses c ON i.course_id = c.id " +
         "ORDER BY i.date_inscription DESC")) {

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
    e.printStackTrace();
}

request.setAttribute("inscriptions", inscriptions);
request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}