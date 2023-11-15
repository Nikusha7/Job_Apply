package ge.nika.job_apply.util;

import ge.nika.job_apply.model.Applicant;
import ge.nika.job_apply.model.ApplicantsWithResumes;
import ge.nika.job_apply.model.Resume;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class DatabaseUtil {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtil.class);

    //writeApplicantAndResumeToDB writes applicants data and resume meta-data to database
    public static int writeApplicantAndResumeToDB(Applicant applicant, Resume resume) {
        boolean transactionSuccess = false;// A flag to track transaction success
        int rowsAffected = 0;
        try {
            //creating DatabaseProperties object to get real values(url, username, password)
            //readDataBaseProperties() returns-> new DatabaseProperties(url, username, password)
            DatabaseProperties databaseProperties = readDataBaseProperties();

            loadMySQLJdbcDriver();

            try (Connection connection = DriverManager.getConnection(
                    databaseProperties.getUrl(), databaseProperties.getUsername(), databaseProperties.getPassword())
            ) {

                connection.setAutoCommit(false); // Start a transaction

                try {
                /*
                IN Database are 2 tables "applicant" and "resume"
                writeApplicant writes applicant to db and returns number of rows affected in applicant table
                writeResume writes resume meta-data to db and returns number of rows affected in resume table
                In this case, either both will be executed or neither. (transaction roll back will happen if one of them is unsuccessful)
                */
                    int applicantRowsAffected = writeApplicant(connection, applicant);
                    int resumeRowsAffected = writeResume(connection, resume);

                    if (applicantRowsAffected > 0 && resumeRowsAffected > 0) {
                        connection.commit(); // Commit the transaction if both operations were successful
                        transactionSuccess = true;
                        rowsAffected = applicantRowsAffected + resumeRowsAffected;
                        logger.info("{} Rows affected in JobApplyWebAppDB. Transaction committed.", rowsAffected);
                    } else {
                        connection.rollback(); // Rollback the transaction if any operation failed
                        logger.warn("Transaction rolled back. Either applicant data or resume metadata was not written to the database.");
                    }
                } catch (SQLException e) {
                    connection.rollback(); // Rollback if an exception occurred within the transaction
                    logger.error("Error within transaction: {}", e.getMessage(), e);
                }
            } catch (SQLException e) {
                logger.error("Error establishing connection: {}", e.getMessage(), e);
            }

            if (!transactionSuccess) {
                logger.warn("Transaction failed; neither applicant data nor resume metadata was written to the database.");
            }

        } catch (RuntimeException e) {
            // Handle exceptions thrown by readDataBaseProperties()
            logger.error("Error loading database properties: {}", e.getMessage(), e);
        }
        return rowsAffected;
    }


    //writes applicant data to the database
    private static int writeApplicant(Connection conn, Applicant applicant) {
        int rowsAffected = 0;
        String writeApplicantQuery = "INSERT INTO applicant(job_category, applicant_name, address, previous_company, previous_position, email, phone_number, submission_datetime) Values(?,?,?,?,?,?,?,?)";
//        PreparedStatement preparedStatement;
        try (PreparedStatement preparedStatement = conn.prepareStatement(writeApplicantQuery)) {

            preparedStatement.setString(1, applicant.getJobCategory());
            preparedStatement.setString(2, applicant.getName());
            preparedStatement.setString(3, applicant.getAddress());
            preparedStatement.setString(4, applicant.getPreviousOrCurrentCompany());
            preparedStatement.setString(5, applicant.getPreviousOrCurrentPosition());
            preparedStatement.setString(6, applicant.getEmail());
            preparedStatement.setString(7, applicant.getPhoneNumber());
            preparedStatement.setDate(8, applicant.getDateOfSubmission());

            rowsAffected = preparedStatement.executeUpdate();

            logger.info("{} Row(s) affected in applicant table. Applicant data written in db: {}. Operation: INSERT.", rowsAffected, applicant);
        } catch (SQLException e) {
            logger.error("Error writing applicant data into the database: {}", e.getMessage(), e);
        }

        return rowsAffected;
    }

    //writes meta-data of resume in database(name, directory/path of file, type of file, size of file)
    private static int writeResume(Connection conn, Resume resume) {
        //Getting applicant_id and passing its value to resume_id, so there won't be "SQLIntegrityConstraintViolationException"
        //so when user enters information and uploads resume, in applicant table data will be written and applicant_id will be automatically generated
        //this generated applicant_id will be resume_id, it ensures that the "resume_id" in the "resume" table
        // is associated with an existing "applicant_id" from the "applicant" table.
        int resumeId = 0;
        String getApplicantIdQuery = "SELECT applicant_id FROM applicant";
        try (Statement statement = conn.createStatement();
             ResultSet applicantId = statement.executeQuery(getApplicantIdQuery)) {

            while (applicantId.next()) {
                resumeId = applicantId.getInt("applicant_id");
            }
//            setting same id as applicant_id has generated in db
            resume.setResumeId(resumeId);
        } catch (SQLException e) {
            logger.error("Error retrieving applicant ID in writeResume(): {}", e.getMessage(), e);
        }

        int rowsAffected = 0;
        String writeResumeQuery = "INSERT INTO resume(resume_id, resume_name, resume_directory_path, resume_document_extension, resume_size, applicant_id) Values(?,?,?,?,?,?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(writeResumeQuery)) {

            preparedStatement.setInt(1, resume.getResumeId());
            preparedStatement.setString(2, resume.getResumeFileName());
            preparedStatement.setString(3, resume.getResumeFileDirectoryPath());
            preparedStatement.setString(4, resume.getResumeFileExtension());
            preparedStatement.setDouble(5, resume.getResumeFileSize());
            preparedStatement.setInt(6, resume.getResumeId());

            rowsAffected = preparedStatement.executeUpdate();

            logger.info("{} Row(s) affected in resume table. Resume meta-data written in db: {}. Operation: INSERT. Applicant_id: {}", rowsAffected, resume, resumeId);
        } catch (SQLException e) {
            logger.error("Error writing resume meta-data into the database: {}", e.getMessage(), e);
        }
        return rowsAffected;
    }

    public static List<ApplicantsWithResumes> getApplicantsAndResumes() {
        List<ApplicantsWithResumes> applicantsWithResumesList = new ArrayList<>();
        try {
            //creating DatabaseProperties object to get real values(url, username, password)
            //readDataBaseProperties() -> new DatabaseProperties(url, username, password)
            DatabaseProperties databaseProperties = readDataBaseProperties();

            loadMySQLJdbcDriver();

            // Establish a database connection using the provided database properties.
            // since it's written in try it will be closed automatically
            try (Connection connection = DriverManager.getConnection(
                    databaseProperties.getUrl(), databaseProperties.getUsername(), databaseProperties.getPassword());
                 Statement statement = connection.createStatement()) {

                //Execute a SQL query to select all applicants and resumes from the database, ResultSet contains the rows and columns of data
                String sqlQuery = "SELECT * FROM applicant JOIN resume ON applicant.applicant_id = resume.resume_id";
                try (ResultSet allApplicantAndResume = statement.executeQuery(sqlQuery)) {

                    // Process the result set to retrieve applicant and resume information.
                    while (allApplicantAndResume.next()) {
                        ApplicantsWithResumes applicantsWithResumes = new ApplicantsWithResumes();

                        // Set applicant details
                        applicantsWithResumes.getApplicants().setId(allApplicantAndResume.getInt("applicant_id"));
                        applicantsWithResumes.getApplicants().setJobCategory(allApplicantAndResume.getString("job_category"));
                        applicantsWithResumes.getApplicants().setName(allApplicantAndResume.getString("applicant_name"));
                        applicantsWithResumes.getApplicants().setAddress(allApplicantAndResume.getString("address"));
                        applicantsWithResumes.getApplicants().setPreviousOrCurrentCompany(allApplicantAndResume.getString("previous_company"));
                        applicantsWithResumes.getApplicants().setPreviousOrCurrentPosition(allApplicantAndResume.getString("previous_position"));
                        applicantsWithResumes.getApplicants().setEmail(allApplicantAndResume.getString("email"));
                        applicantsWithResumes.getApplicants().setPhoneNumber(allApplicantAndResume.getString("phone_number"));
                        applicantsWithResumes.getApplicants().setDateOfSubmission(allApplicantAndResume.getDate("submission_datetime"));

                        // Set resume details
                        applicantsWithResumes.getResumes().setResumeId(allApplicantAndResume.getInt("resume_id"));
                        applicantsWithResumes.getResumes().setResumeFileName(allApplicantAndResume.getString("resume_name"));
                        applicantsWithResumes.getResumes().setResumeFileDirectoryPath(allApplicantAndResume.getString("resume_directory_path"));
                        applicantsWithResumes.getResumes().setResumeFileExtension(allApplicantAndResume.getString("resume_document_extension"));
                        applicantsWithResumes.getResumes().setResumeFileSize(allApplicantAndResume.getDouble("resume_size"));

                        // Add the object to the list
                        applicantsWithResumesList.add(applicantsWithResumes);
                    }
                    logger.info("Applicants and Resumes List: {}", applicantsWithResumesList);
                }
            } catch (SQLException e) {
                logger.error("Error getting applicants and resumes: {}", e.getMessage(), e);
            }

        } catch (RuntimeException e) {
            // Handle exceptions thrown by readDataBaseProperties() and loadMySQLJdbcDriver()
            logger.error("Error loading database properties or JDBC driver: {}", e.getMessage(), e);
        }
        return applicantsWithResumesList;
    }

    public static void DeleteApplicant(int applicantID) {

        try {
            DatabaseProperties databaseProperties = readDataBaseProperties();

            loadMySQLJdbcDriver();

            try (Connection connection = DriverManager.getConnection(
                    databaseProperties.getUrl(), databaseProperties.getUsername(), databaseProperties.getPassword())
            ) {


                String deleteApplicantQuery = "DELETE FROM applicant where applicant_id = ?";
                try (PreparedStatement deleteApplicantStatement = connection.prepareStatement(deleteApplicantQuery)) {

                    deleteApplicantStatement.setInt(1, applicantID);

                    int rowsAffected = deleteApplicantStatement.executeUpdate();
                    logger.info("{} row(s) affected, applicant_id={} deleted", rowsAffected, applicantID);

                } catch (SQLException e) {
                    logger.error("Error executing deleteApplicantStatement: {}", e.getMessage(), e);

                }
            } catch (SQLException e) {
                logger.error("Error establishing database connection: {}", e.getMessage(), e);
            }
        } catch (RuntimeException e) {
            logger.error("Exception in deleting applicant: {}", e.getMessage(), e);
        }
    }


    /**
     * Loads the MySQL JDBC driver. This method should be called
     * before attempting to establish a connection to a MySQL database.
     * If the driver is not found, an error is logged using SLF4J.
     */
    private static void loadMySQLJdbcDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("MySQL JDBC driver not found: {}", e.getMessage(), e);
        }
    }


    private static DatabaseProperties readDataBaseProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = DatabaseUtil.class.getResourceAsStream("/db.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("Failed to load db.properties: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to load db.properties", e);
        }

        //database connection parameters
        String url = properties.getProperty("db.url");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");

        return new DatabaseProperties(url, username, password);
    }


}