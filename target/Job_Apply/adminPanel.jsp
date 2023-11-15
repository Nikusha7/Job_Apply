<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Panel</title>
    <meta charset="UTF-8">
    <%--    <link rel="stylesheet" type="text/css" href="CSS/styles.css">--%>
    <link rel="stylesheet" type="text/css" href="CSS/header-footer.css">
    <link rel="stylesheet" type="text/css" href="CSS/print-applicants-content.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="icon" href="Images/bog-logo-favicon.png" type="image/x-icon" id="favicon">
</head>

<body>
<jsp:include page="jsp/header/header.jsp"/>
<%--<%@include file="jsp/header/header.jsp" %>--%>

<jsp:include page="jsp/print-applicants/print-applicants-table.jsp"/>

<%--<jsp:include page="printApplicants.jsp"/>--%>

<jsp:include page="jsp/footer/footer.jsp"/>
<%--<%@include file="jsp/footer/footer.jsp" %>--%>
</body>

</html>