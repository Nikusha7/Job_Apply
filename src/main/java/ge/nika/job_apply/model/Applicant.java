package ge.nika.job_apply.model;

import java.sql.Date;

public class Applicant {
    //attributes of applicants data
    private int id;
    private String jobCategory;
    private String name;
    private String address;
    private String previousOrCurrentCompany;
    private String previousOrCurrentPosition;
    private String email;
    private String phoneNumber;
    private Date dateOfSubmission;

    public Applicant() {

    }

    public Applicant(String jobCategory, String firstName, String lastName, String country, String cityRegion, String previousOrCurrentCompany, String previousOrCurrentPosition,
                     String email, String phoneNumber) {
        this.jobCategory = jobCategory;
        this.name = firstName + " " + lastName;
        this.address = country + "/" + cityRegion;
        this.previousOrCurrentCompany = previousOrCurrentCompany;
        this.previousOrCurrentPosition = previousOrCurrentPosition;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfSubmission = new Date(System.currentTimeMillis()); //current date of submission

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJobCategory(){return jobCategory;}

    public void setJobCategory(String jobCategory){this.jobCategory = jobCategory;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPreviousOrCurrentCompany() {
        return previousOrCurrentCompany;
    }

    public void setPreviousOrCurrentCompany(String previousOrCurrentCompany) {
        this.previousOrCurrentCompany = previousOrCurrentCompany;
    }

    public String getPreviousOrCurrentPosition() {
        return previousOrCurrentPosition;
    }

    public void setPreviousOrCurrentPosition(String previousOrCurrentPosition) {
        this.previousOrCurrentPosition = previousOrCurrentPosition;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getDateOfSubmission() {
        return dateOfSubmission;
    }

    public void setDateOfSubmission(Date dateOfSubmission) {
        this.dateOfSubmission = dateOfSubmission;
    }

    @Override
    public String toString() {
        return " Applicant{" +
                "id=" + id +
                ", Job Category=" + jobCategory +
                ", Name=" + name +
                ", Address=" + address +
                ", Previous or current company=" + previousOrCurrentCompany +
                ", Previous or current position=" + previousOrCurrentPosition +
                ", Email=" + email +
                ", Phone number=" + phoneNumber +
                ", Date of submission=" + dateOfSubmission +
                " }";
    }

}
