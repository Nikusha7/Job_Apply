package ge.nika.job_apply.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "AdminPanel", value = "/admin-panel")
public class AdminPanel extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*
        1. When a GET request is made to the "/admin-panel" URL, the doGet method is executed.
        2. Inside the doGet method, it includes another JSP (JavaServer Pages) page
        using the req.getRequestDispatcher("/adminPanel.jsp").include(req, resp) line.
        This means that it includes the content of the "adminPanel.jsp" page in the response.
         */
        req.getRequestDispatcher("/adminPanel.jsp").include(req, resp);

    }

}