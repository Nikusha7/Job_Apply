package ge.nika.job_apply.servlets;

import ge.nika.job_apply.model.ApplicantsWithResumes;
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
import java.util.List;


@WebServlet(name = "printApplicants", value = "/print-applicants")
public class PrintApplicantsServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(PrintApplicantsServlet.class);

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
            logger.error(e.getMessage(), e);
            // Redirect to an error page using an absolute path
            requestDispatcher = req.getRequestDispatcher("/errorPage.jsp");
            requestDispatcher.forward(req, resp);
            return;
        }
        requestDispatcher.forward(req, resp);
    }

}