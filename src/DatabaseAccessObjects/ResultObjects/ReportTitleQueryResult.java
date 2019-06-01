package DatabaseAccessObjects.ResultObjects;

import java.io.Serializable;

public class ReportTitleQueryResult implements Serializable {
    public int report_id;
    public String report_title;
    public int domain_id;
    public String domain_name;
    public int availability_status;
    public int cupboard_no;
    public int shelf_no;

    public ReportTitleQueryResult(){
        report_id=0;
        report_title=null;
        domain_id=0;
        domain_name=null;
        availability_status=0;
        cupboard_no=0;
        shelf_no=0;
    }
}
