package com.klikego;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/inscription")
public class InscriptionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

       String url = "jdbc:mysql://localhost:3306/inscription_course";
String user = "root";
String password = "rititeew";

        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        int courseId = Integer.parseInt(request.getParameter("courseId"));

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO inscriptions (nom, prenom, email, course_id) VALUES (?, ?, ?, ?)")) {

            stmt.setString(1, nom);
            stmt.setString(2, prenom);
            stmt.setString(3, email);
            stmt.setInt(4, courseId);
            stmt.executeUpdate();

            response.sendRedirect(request.getContextPath() + "/confirmation.jsp");

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/erreur.jsp");
        }
    }
}