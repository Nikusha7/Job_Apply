package ge.nika.job_apply.servlets;

import ge.nika.job_apply.model.Applicant;
import ge.nika.job_apply.model.Resume;
import ge.nika.job_apply.util.DatabaseUtil;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "MainServlet", value = "/main-servlet")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class UserInputServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UserInputServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/index.jsp");

        String jobCategory = req.getParameter("articleTitle");

        String userName = req.getParameter("name").trim();
        String userLastName = req.getParameter("last-name").trim();
        String cityOrRegion = req.getParameter("city").trim();
        String country = req.getParameter("country").trim();
        String previousCompany = req.getParameter("previous-company").trim();
        String previousJobPosition = req.getParameter("previous-position").trim();
        String email = req.getParameter("email").trim();
        String phoneNumber = req.getParameter("phone-number").trim();

        logger.info("UserInputServlet Values: " + jobCategory + " " + userName + " " + userLastName + " " + cityOrRegion
                + " " + country + " " + previousCompany + " " + previousJobPosition + " " + email + " " + phoneNumber);

        String errorMessage = "გთხოვთ შეავსოთ ველი";
//        tracing if any input is false, then applicant object won't be created and values will not be passed, until all user inputs are valid
        boolean userInputsAreValid = true;

//        FIRST NAME
        String warningMessage = "მიუთითეთ სწორი სახელი და გვარი";
        if (userName.isEmpty()) {
            req.setAttribute("name-error", errorMessage);
            userInputsAreValid = false;
        } else if (isNotValidString(userName) || isNotValidSizeName(userName)) {
            req.setAttribute("name-error", warningMessage);
            userInputsAreValid = false;
        }

//        LAST NAME
        if (userLastName.isEmpty()) {
            req.setAttribute("name-error", errorMessage);
            userInputsAreValid = false;
        } else if (isNotValidString(userLastName) || isNotValidSizeName(userLastName)) {
            req.setAttribute("name-error", warningMessage);
            userInputsAreValid = false;
        }

//        CITY
        if (cityOrRegion.isEmpty()) {
            req.setAttribute("city-error", errorMessage);
            userInputsAreValid = false;
        } else if (isNotValidString(cityOrRegion) || !isValidCityOrRegionSize(cityOrRegion)) {
            req.setAttribute("city-error", "მიუთითეთ სწორი ქალაქი/რეგიონი");
            userInputsAreValid = false;
        }

//        COUNTRY
        if (country.isEmpty()) {
            req.setAttribute("country-error", errorMessage);
            userInputsAreValid = false;
        } else if (isNotValidString(country) || !isValidCountry(country)) {
            req.setAttribute("country-error", warningMessage);
            userInputsAreValid = false;
        }

//        PREVIOUS OR CURRENT COMPANY
        if (previousCompany.isEmpty()) {
            req.setAttribute("previous-company-error", errorMessage);
            userInputsAreValid = false;
        } else if (!isValidPreviousOrCurrentCompanyNameSize(previousCompany)) {
            req.setAttribute("previous-company-error", "გთხოვთ მიუთითეთ მოკლე დასახელება");
        }
        // since company names might be very diverse, I won't have validations/restrictions for it(just for size)

//        PREVIOUS OR CURRENT POSITION
        if (previousJobPosition.isEmpty()) {
            req.setAttribute("previous-position-error", errorMessage);
            userInputsAreValid = false;
        } else if (!isValidPreviousOrCurrentPositionString(previousJobPosition)) {
            req.setAttribute("previous-position-error", warningMessage);
            userInputsAreValid = false;
        }

//        EMAIL
        if (email.isEmpty() || req.getParameter("email") == null) {
            req.setAttribute("email-error", errorMessage);
            userInputsAreValid = false;
        } else if (!isValidEmailSize(email)) {
            req.setAttribute("email-error", "შეიყვანეთ მოკლე ელ-ფოსტა");
            userInputsAreValid = false;
        }

//        PHONE NUMBER
        if (phoneNumber.isEmpty()) {
            req.setAttribute("phone-number-error", errorMessage);
            userInputsAreValid = false;
        } else if (!isValidPhoneNumber(phoneNumber)) {
            req.setAttribute("phone-number-error", "გთხოვთ მიუთითეთ სწორი ნომერი");
            userInputsAreValid = false;
        }


        //Retrieve the uploaded file
        Part filePart = req.getPart("file-input");
        //check if the file input is empty
        if (filePart == null || filePart.getSize() == 0) {
            req.setAttribute("file-input-error", errorMessage);
            requestDispatcher.forward(req, resp);
            return;
        }

        //checking if resume type and size is valid, if it's valid code will continue otherwise error message will be sent for file input
        Resume resume = new Resume(filePart);
        if (!resume.isValidFileType() || !resume.isValidFileSize()) {
            req.setAttribute("file-input-error", "გთხოვთ მიუთითეთ სწორი ფაილი");
            requestDispatcher.forward(req, resp);
            return;
        } else if (!resume.isValidFileNameSize()) {
            req.setAttribute("file-input-error", "გთხოვთ შეამოკლოთ ფაილის დასახელება");
            requestDispatcher.forward(req, resp);
            return;
        }

//        If user inputs are all valid then applicant object will be initialized and values will be passed
        if (!userInputsAreValid) {
            requestDispatcher.forward(req, resp);
//            return;
        } else {

            //Creating object of Applicant and passing applicant information to our Applicant class
            Applicant applicant = new Applicant(jobCategory, userName, userLastName, country, cityOrRegion,
                    previousCompany, previousJobPosition, email, phoneNumber);
            //checking what we have passed
            logger.info("Applicant Data = " + applicant);
            logger.info("Resume Data = " + resume+"\n");
            //Invoking writeApplicantAndResumeToDataBase method, it returns number of rows that has been affected in database
            //generally it should return 2 (1 row in applicant table, and 1 row in resume table)
            if (DatabaseUtil.writeApplicantAndResumeToDB(applicant, resume) > 0) {
                //Saving uploaded resume in file directory(TODO:it should be saved on server, tomcat's folder)
                resume.saveResumeFile();
                //req.setAttribute("success", "აპლიკაცია წარმატებით გაიგზავნა!");
                resp.sendRedirect(req.getContextPath() + "/index.jsp?success=true");
//                return;
            } else {
                resp.sendRedirect(req.getContextPath() + "/index.jsp?success=false");
//                return;
            }

        }

//        requestDispatcher.forward(req, resp);
    }

    //string inputs validation
    public boolean isNotValidString(String stringInput) {
        // Define a regular expression pattern for the validation
        String pattern = "(\\s*\\p{IsAlphabetic}+\\s*)+";

        // Use the Pattern and Matcher classes to check if the input matches the pattern
        Pattern regexPattern = Pattern.compile(pattern);
        Matcher matcher = regexPattern.matcher(stringInput);

        return !matcher.matches();
    }

    //    Checking input Strings sizes, since they have restrictions in database
    public boolean isValidJobCategorySize(String stringInput) {
        return stringInput.length() <= 150;
    }

    public boolean isNotValidSizeName(String stringInput) {
        return stringInput.length() > 35;
    }

    public boolean isValidPreviousOrCurrentCompanyNameSize(String stringInput) {
        return stringInput.length() <= 100;
    }

    public boolean isValidCityOrRegionSize(String stringInput) {
        return stringInput.length() <= 80;
    }

    //    country in this case is always "საქართველო"
    public boolean isValidCountry(String stringInput) {
        return stringInput.length() <= 10;
    }

    public boolean isValidEmailSize(String stringInput) {
        return stringInput.length() <= 75;
    }

    public boolean isValidPreviousOrCurrentPositionString(String stringInput) {
        // Check if the length of the input string is not more than 80 characters(considering database restrictions)
        if (stringInput.length() > 80) {
            return false;
        }

        String pattern = "(?i:N/A|n/a|n/A|N/a|n/|N/|/a|/A|na|NA|/|(\\s*[\\p{IsAlphabetic}\\p{Digit}]+\\s*)+)";

        Pattern regexPattern = Pattern.compile(pattern);
        Matcher matcher = regexPattern.matcher(stringInput);

        return matcher.matches();
    }

    //phone number validation(must be positive integers and size must 9 or 12)
    public boolean isValidPhoneNumber(String phoneNumber) {
        // Define the regular expression pattern for a valid phone number.
        String pattern = "[95]\\d{8}|[95]\\d{11}";

        // Compile the regular expression pattern.
        Pattern regexPattern = Pattern.compile(pattern);

        // Create a Matcher to match the input phone number against the pattern.
        Matcher matcher = regexPattern.matcher(phoneNumber);

        // Check if the phone number matches the pattern and its length is not more than 12 digits.
        return matcher.matches();
    }


}