package ge.nika.job_apply.servlets;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(name = "downloadResumeServlet", value = "/download-resume")
public class DownloadResumeServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(DownloadResumeServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        //RequestDispatcher requestDispatcher = req.getRequestDispatcher("/adminPanel.jsp");
        String fileName = req.getParameter("fileName");
        String fileType = req.getParameter("fileType");
        String fileSize = String.valueOf(req.getParameter("fileSize"));
//        logger.info("fileName=" + fileName + ", fileType=" + fileType + ", fileSize=" + fileSize + "kb");

        try {
            /*
            TODO: i need to create folder on tomcat server, where resumes will be saved and where from resumes will be downloaded for HRs
             */
            // Get the file path (adjust this based on your project structure)
            String filePath = "D:/IntelliJ Projects/Job_Apply/src/main/files/" + fileName;
            logger.info("File Path: " + filePath +  ", realpath: " + getServletContext().getRealPath("/main/files/" + fileName));

            // Set the content type and length
            resp.setContentType(getContentType(fileType));
            resp.setContentLengthLong((long) Double.parseDouble(fileSize));

            // Set the header for the file name
            String headerKey = "Content-disposition";
//            header value sets the name of file (in this case file will be named by the value of "fileName")
            String headerValue = String.format("attachment; filename=\"%s\"", fileName);
            resp.setHeader(headerKey, headerValue);

            File file = new File(filePath);
            try (FileInputStream fis = new FileInputStream(file);
                 ServletOutputStream os = resp.getOutputStream()) {

                // Read the file and write it to the output stream
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
            }
            logger.info("Successfully Downloaded: "+"'"+fileName+"'");
        } catch (Exception e) {
            e.printStackTrace(); // Add proper logging in a production environment
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error downloading resume");
        }

    }

    /**
     * Gets the MIME type (Content-Type) based on the file extension.
     *
     * @param fileType The file type or file extension.
     * @return The corresponding MIME type.
     */
    private String getContentType(String fileType) {
        switch (fileType) {
            case "pdf":
                return "application/pdf";
            case "doc":
            case "docx":
                return "application/msword";
            default:
//              generic MIME type for binary data when the specific type is unknown
                return "application/octet-stream";
        }
    }

}

