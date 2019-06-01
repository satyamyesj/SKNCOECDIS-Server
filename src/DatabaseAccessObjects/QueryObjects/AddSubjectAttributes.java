package DatabaseAccessObjects.QueryObjects;

import java.io.Serializable;

public class AddSubjectAttributes implements Serializable {
    public int subject_id;
    public String subject_name;
    public String semester;
    public String academic_year;
    public String exam_pattern;
    public int domain_id;
    public String subject_abbreviation;

    public AddSubjectAttributes(){
        subject_id=0;
        subject_name=null;
        semester=null;
        academic_year=null;
        exam_pattern=null;
        domain_id=0;
        subject_abbreviation=null;
    }
}
