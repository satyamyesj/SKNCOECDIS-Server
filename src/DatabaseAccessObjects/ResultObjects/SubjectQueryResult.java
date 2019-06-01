package DatabaseAccessObjects.ResultObjects;

import java.io.Serializable;

public class SubjectQueryResult implements Serializable {
    public int subject_id;
    public String subject_name;
    public String semester;
    public String academic_year;
    public String exam_pattern;
    public int domain_id;
    public String subject_abbreviation;

    public SubjectQueryResult(){
        subject_id=0;
        subject_name=null;
        semester=null;
        academic_year=null;
        exam_pattern=null;
        domain_id=0;
        subject_abbreviation=null;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getAcademic_year() {
        return academic_year;
    }

    public void setAcademic_year(String academic_year) {
        this.academic_year = academic_year;
    }

    public String getExam_pattern() {
        return exam_pattern;
    }

    public void setExam_pattern(String exam_pattern) {
        this.exam_pattern = exam_pattern;
    }

    public int getDomain_id() {
        return domain_id;
    }

    public void setDomain_id(int domain_id) {
        this.domain_id = domain_id;
    }

    public String getSubject_abbreviation() {
        return subject_abbreviation;
    }

    public void setSubject_abbreviation(String subject_abbreviation) {
        this.subject_abbreviation = subject_abbreviation;
    }
    
    
}
