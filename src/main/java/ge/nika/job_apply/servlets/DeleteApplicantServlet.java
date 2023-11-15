package ge.nika.job_apply.servlets;

import ge.nika.job_apply.util.DatabaseUtil;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


@WebServlet(name = "deleteApplicantServlet", value = "/delete-applicant")
public class DeleteApplicantServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(DeleteApplicantServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/print-applicants");
        try {
            int applicantID = Integer.parseInt(req.getParameter("applicantId"));

            logger.info("Deleting applicant with ID: {}", + applicantID);

            DatabaseUtil.DeleteApplicant(applicantID);

        } catch (NumberFormatException e) {
            // Handle the case where the "applicantId" parameter is not a valid integer
            logger.error("Error parsing applicantId: {}", e.getMessage(), e);
            //redirect to an error page
            resp.sendRedirect(req.getContextPath() + "/errorPage.jsp");
            return; // Stop further execution
        } catch (Exception e) {
            // Handle other exceptions (e.g., database-related errors)
            logger.error("Error deleting applicant: {}", e.getMessage(), e);
            resp.sendRedirect(req.getContextPath() + "/errorPage.jsp");
            return;
        }

        requestDispatcher.forward(req, resp);
    }

}