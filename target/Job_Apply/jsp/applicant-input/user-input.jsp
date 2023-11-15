<%@ page import="java.util.logging.Logger" %>
<%@ page import="ge.nika.job_apply.servlets.UserInputServlet" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="user-input">
    <%
        String successfulSubmission = "";
//        if (!String.valueOf(request.getAttribute("success")).equals("null")) {
//            successfulSubmission = String.valueOf(request.getAttribute("success"));
//        }
        String successParam = request.getParameter("success");
        if("true".equals(successParam)){
            successfulSubmission = "აპლიკაცია წარმატებით გაიგზავნა";
        }else if("false".equals(successParam)){
            successfulSubmission = "აპლიკაციის გაგზავნა ვერ მოხერხდა";
        }
    %>
    <h4 class="successful-upload"><%=successfulSubmission%></h4>

    <h2 class="user-input-title">გამოაგზავნეთ განაცხადი</h2>

    <form method="post" action="main-servlet" enctype="multipart/form-data">
        <input type="hidden" name="articleTitle" value="<%=request.getAttribute("articleTitle")%>">

        <div class="text-name">
            <label for="name">სახელი</label>
            <label for="last-name">გვარი</label>
        </div>
        <div class="input-name">
            <%
                String nameValue = "";
                if (!String.valueOf(request.getAttribute("success")).equals("null")) {
                    nameValue = "";
                } else if (request.getParameter("name") != null) {
                    nameValue = request.getParameter("name").trim();
                }
                String lastNameValue = "";
                if (!String.valueOf(request.getAttribute("success")).equals("null")) {
                    lastNameValue = "";
                } else if (request.getParameter("last-name") != null) {
                    lastNameValue = request.getParameter("last-name").trim();
                }
            %>
            <input type="text" id="name" name="name" value="<%=nameValue%>">
            <input type="text" id="last-name" name="last-name" value="<%=lastNameValue%>">
        </div>
        <div class="name-error">
            <%--@declare id="error-message"--%>
            <%
                String nameError = " ";
                if (!String.valueOf(request.getAttribute("name-error")).equals("null")) {
                    nameError = String.valueOf(request.getAttribute("name-error"));
                }
            %>
            <label for="name" class="error-label"><%=nameError%>
            </label>
            <%--გთხოვთ შეავსოთ ველი--%>
        </div>

        <%
            String cityError = " ";
            if (!String.valueOf(request.getAttribute("city-error")).equals("null")) {
                cityError = String.valueOf(request.getAttribute("city-error"));
            }

            String cityValue = "";
            if (!String.valueOf(request.getAttribute("success")).equals("null")) {
                cityValue = "";
            } else if (request.getParameter("city") != null) {
                cityValue = request.getParameter("city").trim();
            }
        %>
        <label for="city">ქალაქი/რეგიონი</label>
        <input type="text" id="city" name="city" value="<%=cityValue%>">
        <div class="name-error"><label for="error-message"><%=cityError%>
        </label></div>

        <%
        String countryError = " ";
        if(!String.valueOf(request.getAttribute("country")).equals("null")){
            countryError = String.valueOf(request.getAttribute("country"));
        }
        String countryValue = "საქართველო";
        %>
        <label for="country">ქვეყანა</label>
        <input type="text" id="country" name="country" value="<%=countryValue%>">
        <div class="name-error"><label for="error-message"><%=countryError%></label></div>


        <%
            String previousCompanyError = " ";
            if (!String.valueOf(request.getAttribute("previous-company-error")).equals("null")) {
                previousCompanyError = String.valueOf(request.getAttribute("previous-company-error"));
            }
            String previousCompanyValue = "";
            if (!String.valueOf(request.getAttribute("success")).equals("null")) {
                previousCompanyValue = "";
            } else if (request.getParameter("previous-company") != null) {
                previousCompanyValue = request.getParameter("previous-company");
            }
        %>
        <label for="previous-company">მიმდინარე/ბოლო დამსაქმებელი მიუთითეთ "N/A" თუ არ გაქვთ სამუშაო
            გამოცდილება</label>
        <input type="text" id="previous-company" name="previous-company" value="<%=previousCompanyValue%>">
        <div class="name-error"><label for="error-message"><%=previousCompanyError%>
        </label></div>

        <%
            String previousPositionError = " ";
            if (!String.valueOf(request.getAttribute("previous-position-error")).equals("null")) {
                previousPositionError = String.valueOf(request.getAttribute("previous-position-error"));
            }
            String previousPositionValue = "";
            if (!String.valueOf(request.getAttribute("success")).equals("null")) {
                previousPositionValue = "";
            } else if (request.getParameter("previous-position") != null) {
                previousPositionValue = request.getParameter("previous-position");
            }
        %>
        <label for="previous-position">მიმდინარე/ბოლო პოზიცია მიუთითეთ "N/A" თუ არ გაქვთ სამუშაო გამოცდილება</label>
        <input type="text" id="previous-position" name="previous-position" value="<%=previousPositionValue%>">
        <div class="name-error"><label for="error-message"><%=previousPositionError%>
        </label></div>

        <%
            String emailError = " ";
            if (!String.valueOf(request.getAttribute("email-error")).equals("null")) {
                emailError = String.valueOf(request.getAttribute("email-error"));
            }
            String emailValue = "";
            if (!String.valueOf(request.getAttribute("success")).equals("null")) {
                emailValue = "";
            } else if (request.getParameter("email") != null) {
                emailValue = request.getParameter("email");
            }
        %>
        <label for="email">პირადი ელ-ფოსტა</label>
        <input type="email" id="email" name="email" value=<%=emailValue%>>
        <div class="name-error"><label for="error-message"><%=emailError%>
        </label></div>

        <%
            String phoneNumberError = " ";
            if (!String.valueOf(request.getAttribute("phone-number-error")).equals("null")) {
                phoneNumberError = String.valueOf(request.getAttribute("phone-number-error"));
            }

            String phoneValue = "";
            if (!String.valueOf(request.getAttribute("success")).equals("null")) {
                phoneValue = "";
            } else if (request.getParameter("phone-number") != null) {
                phoneValue = request.getParameter("phone-number");
            }
        %>
        <label for="phone-number">ტელეფონი (ფორმატი: 9955XXXXXXXX)</label>
        <input type="number" id="phone-number" name="phone-number" value=<%=phoneValue%>>
        <div class="name-error"><label for="error-message"><%=phoneNumberError%>
        </label></div>

        <%
            String fileInputError = " ";
            if (!String.valueOf(request.getAttribute("file-input-error")).equals("null")) {
                fileInputError = String.valueOf(request.getAttribute("file-input-error"));
            }
        %>
        <label>ატვირთეთ თქვენი რეზიუმე/CV</label>
        <div class="fileInput">
            <label for="file-input">Upload a file:</label>
            <input type="file" id="file-input" name="file-input" accept=".pdf, .doc, .docx, .txt, .rtf, .odt, .html, .jpeg, .jpg, .png, .md, .tex"
                   onchange="displayFileName()">
            <span id="fileNameDisplay"></span>
        </div>
        <script>
            function displayFileName() {
                const fileInput = document.getElementById("file-input");
                const fileNameDisplay = document.getElementById("fileNameDisplay");
                // Check if a file is selected
                if (fileInput.files.length > 0) {
                    const fileName = fileInput.files[0].name;
                    fileNameDisplay.textContent = "Uploaded File: " + fileName;
                } else {
                    fileNameDisplay.textContent = "No file selected";
                }
            }
        </script>
        <div class="name-error"><label for="error-message"></label><%=fileInputError%>

        </div>

        <jsp:include page="jsp/check-box-article/checkbox.jsp"/>
        <button type="submit" class="submit-button">გამოაგზავნეთ განაცხადი</button>
    </form>
</div>