package ge.nika.job_apply.servlets;

import ge.nika.job_apply.model.ApplicantsWithResumes;
import ge.nika.job_apply.util.DatabaseUtil;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "printApplicants", value = "/print-applicants")
public class PrintApplicantsServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(PrintApplicantsServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/adminPanel.jsp");
        try {
            //getting list from database util class
            List<ApplicantsWithResumes> applicantsAndResumes = DatabaseUtil.getApplicantsAndResumes();

            //passing list to print-applicants-table.jsp
            req.setAttribute("applicantsAndResumes", applicantsAndResumes);

            //checking what we have received from database util class
            logger.info("ApplicantWithResumes list has been passed to print-applicants-table.jsp\n");
        } catch (Exception e) {
            // Handle exceptions (e.g., database-related errors)
            logger.log(Level.SEVERE, "Error retrieving applicants", e);
            // Redirect to an error page using an absolute path
            resp.sendRedirect(req.getContextPath() + "/errorPage.jsp");
            return;
        }
        requestDispatcher.forward(req, resp);
    }

}