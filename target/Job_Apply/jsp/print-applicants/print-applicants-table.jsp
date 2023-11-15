<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="ge.nika.job_apply.model.ApplicantsWithResumes" %>
<%@ page import="java.util.List" %>

<div class="content">
    <form method="post" action="print-applicants">
        <button type="submit" name="printAllApplicants">Show All Applicants</button>
    </form>

    <%
        List<ApplicantsWithResumes> applicantsWithResumesList = (List<ApplicantsWithResumes>) request.getAttribute("applicantsAndResumes");
    %>

    <table class="table">
        <thead>
        <tr>
            <th>#</th>
            <th>Job Category</th>
            <th>Name</th>
            <th>Phone</th>
            <th>Email</th>
            <th>Address</th>
            <th>Previous/Current Position and Company</th>
            <th>Resume Name</th>
            <th>Resume</th>
            <th>Date of Submission</th>
            <!-- Add more table headers as needed -->
            <th>Action</th> <!-- Add a new column for the Delete button -->
        </tr>
        </thead>
        <tbody>
        <%if (applicantsWithResumesList != null && !applicantsWithResumesList.isEmpty()) {%>
        <% for (ApplicantsWithResumes applicantAndResume : applicantsWithResumesList) { %>
        <tr>
            <th scope="row"><%=applicantAndResume.getApplicants().getId()%>
            </th>
            <td><%=applicantAndResume.getApplicants().getJobCategory()%>
            </td>
            <td><%=applicantAndResume.getApplicants().getName()%>
            </td>
            <td><%=applicantAndResume.getApplicants().getPhoneNumber()%>
            </td>
            <td class="email"><%=applicantAndResume.getApplicants().getEmail()%>
            </td>
            <td>
                <%=applicantAndResume.getApplicants().getAddress()%>
            </td>
            <td><%=applicantAndResume.getApplicants().getPreviousOrCurrentPosition() + " / " +
                    applicantAndResume.getApplicants().getPreviousOrCurrentCompany()%>
            </td>
            <td><%=applicantAndResume.getResumes().getResumeFileName()%>
            </td>
            <%--        TODO:hr should be able to download resume of the exact applicant she chooses--%>
            <%--            "./src/files/<%=applicantAndResume.getResumes().getResumeFileName()%>"--%>
            <td class="nowrap">
                <a href="download-resume?fileName=<%=applicantAndResume.getResumes().getResumeFileName()%>&fileType=<%=applicantAndResume.getResumes().getResumeFileExtension()%>&fileSize=<%=applicantAndResume.getResumes().getResumeFileSize()%>"
                   class="download-button" download>Download CV
                    <i class="fa fa-download"></i>
                </a>
            </td>
            <td><%=applicantAndResume.getApplicants().getDateOfSubmission()%>
            </td>
            <!-- Add more table cells as needed -->


            <!-- Add a "Delete" button that submits the applicant's ID to a servlet or JSP page for deletion -->
            <td class="nowrap">
                <form method="post" action="delete-applicant">
                    <input type="hidden" name="applicantId" value="<%=applicantAndResume.getApplicants().getId()%>">
                    <button type="submit" class="delete-button" name="deleteAction" value="delete">
                        Delete <i class="fa fa-trash"></i>
                    </button>
                </form>
            </td>
        </tr>
        <% } %>
        <%} else { %>
        <td>0 Applicants Has Applied!</td>
        <%}%>
        </tbody>
    </table>
</div>