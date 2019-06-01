package DatabaseAccessObjects.QueryObjects;

import java.io.Serializable;

public class ReportSearchAttributes implements Serializable {
    public String report_title;
    public String domain_name;

    public ReportSearchAttributes(){
        report_title=null;
        domain_name=null;
    }
}
