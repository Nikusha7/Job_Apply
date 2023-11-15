# Job_Apply
A Web Application designed for job seekers that can view and apply for open positions, while HR professionals can efficiently manage applications through an intuitive admin panel.

# Job Application Management System
# Overview:
The Job Application Management System is a comprehensive web application designed to simplify and enhance the job application process for companies. 
This system provides a user-friendly interface for job seekers to view and apply for open positions, while offering recruiters and human resources (HR) professionals a centralized platform to 
manage(get all the applicants information and download their resumes) and evaluate applicants(delete unwanted applications).

#Usage:
Run sql script to create database and its tables.
Specify your database properties in properties file url, username and password. (so that program will connect to your real MySQL db)
Use intellij to run it on tomcat server.
In model package, in Resume class specify folder path where you want to be saved uploaded resumes:  this.resumeFileDirectoryPath = "D:\\IntelliJ Projects\\Job_Apply\\src\\main\\files"; //it is just a demonstration path, change it to your preffered path, since in database i am writting just meta-data of uploaded file, using that meta-data hr will download resumes.
