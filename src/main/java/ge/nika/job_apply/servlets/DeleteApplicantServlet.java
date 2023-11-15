package ge.nika.job_apply.servlets;

import ge.nika.job_apply.util.DatabaseUtil;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "deleteApplicantServlet", value = "/delete-applicant")
public class DeleteApplicantServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(DeleteApplicantServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/print-applicants");
        try {
            int applicantID = Integer.parseInt(req.getParameter("applicantId"));

            logger.info("applicantID = " + applicantID);

            DatabaseUtil.DeleteApplicant(applicantID);

        } catch (NumberFormatException e) {
            // Handle the case where the "applicantId" parameter is not a valid integer
            logger.log(Level.SEVERE, "Error parsing applicantId", e);
            //redirect to an error page
            resp.sendRedirect(req.getContextPath() + "/errorPage.jsp");
            return; // Stop further execution
        } catch (Exception e) {
            // Handle other exceptions (e.g., database-related errors)
            logger.log(Level.SEVERE, "Error deleting applicant", e);
            resp.sendRedirect(req.getContextPath() + "/errorPage.jsp");
            return;
        }

        requestDispatcher.forward(req, resp);
    }

}