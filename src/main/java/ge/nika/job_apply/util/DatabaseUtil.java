package ge.nika.job_apply.util;

import ge.nika.job_apply.model.Applicant;
import ge.nika.job_apply.model.ApplicantsWithResumes;
import ge.nika.job_apply.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class DatabaseUtil {
    private static final Logger logger = Logger.getLogger(DatabaseUtil.class.getName());

    //writeApplicantAndResumeToDB writes applicants data and resume meta-data to database
    public static int writeApplicantAndResumeToDB(Applicant applicant, Resume resume) {

        //creating DatabaseProperties object to get real values(url, username, password)
        //readDataBaseProperties() returns-> new DatabaseProperties(url, username, password)
        DatabaseProperties databaseProperties = readDataBaseProperties();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        boolean transactionSuccess = false;// A flag to track transaction success
        int rowsAffected = 0;
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
                } else {
                    connection.rollback(); // Rollback the transaction if any operation failed
                }
            } catch (SQLException e) {
                connection.rollback(); // Rollback if an exception occurred within the transaction
                e.printStackTrace();
            }

            logger.info(rowsAffected + " Rows has been affected in JobApplyWebAppDB"+"\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (!transactionSuccess) {
            logger.info("Transaction failed; neither applicant data nor resume metadata was written to the database.");
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

            logger.info(rowsAffected + " Row has been affected in applicant table, " + "Applicant data written in db: " + applicant);
        } catch (SQLException e) {
            //if there is problem writing applicant data into database stacktrace will be printed
            e.printStackTrace();
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
        try {
            String getApplicantIdQuery = "SELECT applicant_id FROM applicant";
            Statement statement = conn.createStatement();

            ResultSet applicantId = statement.executeQuery(getApplicantIdQuery);

            while (applicantId.next()) {
                resumeId = applicantId.getInt("applicant_id");
            }
//            setting same id as applicant_id has generated in db
            resume.setResumeId(resumeId);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
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

            logger.info(rowsAffected + " Row has been affected in resume table, " + "Resume meta-data written in db: " +  resume + " Applicant_id=" + resumeId);
        } catch (SQLException e) {
            //if there is problem writing resume meta-data into database stacktrace will be printed
            e.printStackTrace();
        }
        return rowsAffected;
    }

    public static List<ApplicantsWithResumes> getApplicantsAndResumes() {
        //creating DatabaseProperties object to get real values(url, username, password)
        //readDataBaseProperties() -> new DatabaseProperties(url, username, password)
        DatabaseProperties databaseProperties = readDataBaseProperties();


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Establish a database connection using the provided database properties.
        // since it's written in try it will be closed automatically

        List<ApplicantsWithResumes> applicantsWithResumesList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(
                databaseProperties.getUrl(), databaseProperties.getUsername(), databaseProperties.getPassword())
        ) {
            //mostly statement is used for reading queries(without parameters)
            // Create a Statement for executing SQL queries within the database connection.
            try (Statement statement = connection.createStatement()) {

                //Execute a SQL query to select all applicants and resumes from the database, ResultSet contains the rows and columns of data
                ResultSet allApplicantAndResume = statement.executeQuery("SELECT * FROM applicant JOIN resume ON applicant.applicant_id = resume.resume_id");

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
                logger.info("Applicants and Resumes List: " + applicantsWithResumesList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return applicantsWithResumesList;
    }

    public static void DeleteApplicant(int applicantID) {
        DatabaseProperties databaseProperties = readDataBaseProperties();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(
                databaseProperties.getUrl(), databaseProperties.getUsername(), databaseProperties.getPassword())
        ) {

            String deleteApplicantQuery = "DELETE FROM applicant where applicant_id = ?";
            try (PreparedStatement deleteApplicantStatement = connection.prepareStatement(deleteApplicantQuery)) {

                deleteApplicantStatement.setInt(1, applicantID);

                int rowsAffected = deleteApplicantStatement.executeUpdate();
                logger.info(rowsAffected + " Row has been affected, applicant_id=" + applicantID + " Has been DELETED");

            } catch (SQLException e) {
                e.printStackTrace();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static DatabaseProperties readDataBaseProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = DatabaseUtil.class.getResourceAsStream("/db.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load db.properties", e);
        }

        //database connection parameters
        String url = properties.getProperty("db.url");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");

        return new DatabaseProperties(url, username, password);
    }


}