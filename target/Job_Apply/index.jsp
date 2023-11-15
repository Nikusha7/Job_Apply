<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Apply Your Resume</title>
    <meta charset="UTF-8"/>
    <link rel="stylesheet" type="text/css" href="CSS/header-footer.css">
    <link rel="stylesheet" type="text/css" href="CSS/styles.css">
    <link rel="icon" href="Images/bog-logo-favicon.png" type="image/x-icon" id="favicon">
</head>

<body>
<%@include file="jsp/header/header.jsp" %>

<div class="container">

    <%--job description displayed left side of the page--%>
    <%@include file="jsp/job-description/article.jsp" %>

    <%--vertical line separator--%>
    <div class="divider"></div>

    <%--input fields for applicant to fill the required data--%>
    <%@include file="jsp/applicant-input/user-input.jsp" %>

</div>

<%@include file="jsp/footer/footer.jsp" %>
</body>
</html>