package DatabaseAccessObjects.QueryObjects;

import java.io.Serializable;

public class AddReportTitleAttributes implements Serializable {
    public int report_id;
    public String report_title;
    public int domain_id;
    public int availability_status;
    public int cupboard_no;
    public int shelf_no;

    public AddReportTitleAttributes(){
        report_id=0;
        report_title=null;
        domain_id=0;
        availability_status=0;
        cupboard_no=0;
        shelf_no=0;
    }
}
