package ge.nika.job_apply.model;

public class ApplicantsWithResumes {
    private Applicant applicants;
    private Resume resumes;

    public ApplicantsWithResumes(){
        this.applicants = new Applicant();
        this.resumes = new Resume();
    }

    public Applicant getApplicants(){
        return applicants;
    }

    public void setApplicants(Applicant applicants){
        this.applicants = applicants;
    }

    public Resume getResumes() {
        return resumes;
    }

    public void setResumes(Resume resumes) {
        this.resumes = resumes;
    }

    @Override
    public String toString() {
        return ""+applicants + resumes;

    }

}
